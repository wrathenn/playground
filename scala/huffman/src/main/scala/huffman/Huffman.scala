package huffman

import cats.Monoid
import cats.effect.{Blocker, ContextShift, ExitCode, IO, IOApp, Resource, Sync}
import cats.syntax.all._

import java.io.{FileInputStream, FileOutputStream, InputStream, OutputStream}
import io.circe.syntax._
import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.{Map => MutMap}
import io.FileIO.{readLine, readAndDecode}
import BTreeInstances.BTreeFuncsByteArray


object Huffman extends IOApp {
  implicit class MutMapExtension[K, V : Monoid](map: MutMap[K, V]) {
    def putOrAdd(k: K, v: V): Unit = map.get(k) match {
      case Some(value) => map.update(k, value |+| v)
      case None => map.put(k, v); ()
    }
  }

  def processFile[F[_]: Sync](blocker: Blocker, input: InputStream)
    (implicit contextShift: ContextShift[F]): F[HuffmanTree[Byte]] = {

    def iter(table: MutMap[Byte, Int]): F[MutMap[Byte, Int]] = for {
      i <- blocker.delay(input.read())
      r <- if (i == -1) Sync[F].pure(table)
        else Sync[F].delay(table.putOrAdd(i.toByte, 1)) *> iter(table)
    } yield r

    iter(MutMap.empty[Byte, Int]).map(mutMap => HuffmanTree(mutMap.toSeq))
  }

  @tailrec
  def byteArrayToByte(array: Array[Byte], res: Byte = 0): Byte = array match {
    case Array(a, _*) => byteArrayToByte(array.tail, res = ((res << 1) + a).toByte)
    case _ => res
  }

  @tailrec
  def byteToByteArray(byte: Byte, res: Array[Byte] = Array.empty): Array[Byte] = if (res.length == 8) res else byte match {
    case 0 => if (res.length != 8) byteToByteArray(byte, res.prepended(0)) else res
    case _ => byteToByteArray((byte >> 1).toByte, res.prepended((byte & 1).toByte))
  }

  def cypherFile[F[_]: Sync](blocker: Blocker, input: InputStream, output: OutputStream, huffmanTree: HuffmanTree[Byte])
    (implicit contextShift: ContextShift[F]) : F[Unit] = {
    import BTreeInstances.BTreeFuncsByteArray

    val symbolMap: Map[Byte, Array[Byte]] = huffmanTree.toMap[Byte, Array[Byte]](prev = Map.empty, code = Array.empty)
    symbolMap.foreach{case (b, arr) => println(s"${b.toChar} --> ${arr.mkString("Array(", ", ", ")")}")}

    def writeAllPossible(remainder: Array[Byte], output: OutputStream): F[Array[Byte]] =
      if (remainder.length >= 8) blocker.delay {
       output.write(byteArrayToByte(remainder.slice(0, 8)))
     } *> writeAllPossible(remainder.slice(8, remainder.length), output)
      else Sync[F].pure(remainder)

    def iter(remainder: Array[Byte] = Array.empty): F[Unit] = for {
      b <- blocker.delay(input.read())
      res <- if (b == -1) {
        remainder.length match {
          case 7 => blocker.delay {
            output.write(byteArrayToByte(remainder :+ 1))
            output.write(byteArrayToByte(Array.ofDim(8)))
          }
          case _ => blocker.delay {
            output.write(byteArrayToByte(remainder :+ 1.toByte :++ Array.ofDim(7 - remainder.length)))
          }
        }
      } else {
        val newRemainder = remainder :++ symbolMap(b.toByte)
        if (newRemainder.length >= 8)
          writeAllPossible(newRemainder, output).flatMap(arr => iter(arr))
        else iter(newRemainder)
      }
    } yield res

    blocker.delay(output.write((huffmanTree.asJson.noSpaces + '\n').getBytes)) *>
    iter()
  }

  def decipherFile[F[_] : Sync](blocker: Blocker, input: InputStream, output: OutputStream)
    (implicit contextShift: ContextShift[F]): F[Unit] = {

    @tailrec
    def goThroughTree(block: Array[Byte], curNode: HuffmanTree[Byte]): (Option[Byte], Array[Byte], HuffmanTree[Byte]) = {
      if (block.isEmpty) return (None, block, curNode)
      curNode match {
        case HuffmanTreeLeaf(value, _) => (value.some, block, curNode)
        case HuffmanTreeBranch(a, b, _) => if (block.head == 0) goThroughTree(block.tail, a) else goThroughTree(block.tail, b)
      }
    }

    @tailrec
    def decodeBlock(
      block: Array[Byte],
      srcTree: HuffmanTree[Byte], // always set here a default full tree
      curTree: HuffmanTree[Byte], // set to previous tree state, because info may not be fully decoded
      resArray: Array[Byte] = Array.empty): (Array[Byte], HuffmanTree[Byte]) = {
      val (nextByte, blockRemainder, curNode) = goThroughTree(block, curTree)
      nextByte match {
        case Some(value) => decodeBlock(blockRemainder, srcTree, srcTree, resArray :+ value)
        case None => (resArray, curNode)
      }
    }

    def iter(
      input: InputStream,
      output: OutputStream,
      srcTree: HuffmanTree[Byte],
      curNode: HuffmanTree[Byte],
      prev2Blocks: (Array[Byte], Array[Byte]),
    ): F[Unit] = for {
      b <- blocker.delay { input.read() }
      res <-
        if (b == -1) {
          val finalBlock = if (prev2Blocks._2.forall(_ === 0.toByte)) prev2Blocks._1.slice(0, 7)
          else {
            val endingSize = prev2Blocks._2.reverse.takeWhile(_ == 0).length + 1
            val end = prev2Blocks._2.slice(0, prev2Blocks._2.length - endingSize + 1)
            prev2Blocks._1 :++ end
          }
          val (bytes, _) = decodeBlock(finalBlock, srcTree, curNode)
          blocker.delay { output.write(bytes) }
        } else {
          val byteArray = byteToByteArray(b.toByte)
          val (bytes, newNode) = decodeBlock(prev2Blocks._1, srcTree, curNode)
          blocker.delay { output.write(bytes) } *> iter(input, output, srcTree, newNode, (prev2Blocks._2, byteArray))
        }
    } yield res

    for {
      decodedTree <- readAndDecode[F, HuffmanTree[Byte]](readLine(blocker, input)).flatMap(_.liftTo[F])
      prev2Blocks <- blocker.delay { val arr = input.readNBytes(2); (byteToByteArray(arr(0)), byteToByteArray(arr(1))) }
      res <- iter(input, output, decodedTree, decodedTree, prev2Blocks)
    } yield res
  }

  override def run(args: List[String]): IO[ExitCode] = {
    val input = new FileInputStream("test-input.txt")
    val inputCopy = new FileInputStream("test-input.txt")
    val output = new FileOutputStream("test-output.txt")
    Blocker[IO].use { blocker => for {
      tree <- processFile[IO](blocker, input)
      _ <- IO { println(tree.asJson) }
      _ <- cypherFile[IO](blocker, inputCopy, output, tree)
    } yield ExitCode.Success
    }
  }

//  override def run(args: List[String]): IO[ExitCode] = {
//    val input = new FileInputStream("test-output.txt")
//    val output = new FileOutputStream("test-result.jpg")
//    Blocker[IO].use { blocker => for {
//      _ <- decipherFile[IO](blocker, input, output)
//    } yield ExitCode.Success
//    }
//  }
}
