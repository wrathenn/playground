package huffman

import scala.annotation.tailrec
import cats.syntax.all._
import huffman.BTreeFuncsSyntax.bTreeSyntax
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

import scala.language.implicitConversions

// Use this constructor to create with already sorted seq
class SortedSeq[A](val seq: Seq[A])(val comparator: (A, A) => Boolean) {
  def added(elem: A): SortedSeq[A] = {
    val appendAfterIndex: Int = seq.indexWhere(it => !comparator(it, elem))
    new SortedSeq[A](seq.take(appendAfterIndex) :+ elem :++ seq.takeRight(seq.length - appendAfterIndex))(comparator)
  }
}

object SortedSeq {
  // If you want to sort it before creation
  def apply[A](seq: Seq[A])(comparator: (A, A) => Boolean): SortedSeq[A] =
    new SortedSeq[A](seq.sortWith(comparator))(comparator)
}

trait BTreeFuncs[A] {
  def aFunc(a: A): A
  def bFunc(b: A): A
}

object BTreeInstances {
  implicit val BTreeFuncsByteArray = new BTreeFuncs[Array[Byte]] {
    override def aFunc(a: Array[Byte]): Array[Byte] = a.appended(0)
    override def bFunc(b: Array[Byte]): Array[Byte] = b.appended(1)
  }
}

object BTreeFuncsSyntax {
  implicit class bTreeSyntax[A](val v: A) extends AnyVal {
    def goA(implicit bTreeFuncs: BTreeFuncs[A]): A = bTreeFuncs.aFunc(v)
    def goB(implicit bTreeFuncs: BTreeFuncs[A]): A = bTreeFuncs.bFunc(v)
  }
}

sealed trait HuffmanTree[+A] {
  def weight: Int
  def toMap[K >: A, V : BTreeFuncs](prev: Map[K, V] = Map.empty, code: V): Map[K, V]
}

case class HuffmanTreeLeaf[+A](value: A, weight: Int) extends HuffmanTree[A] {
  def toMap[K >: A, V : BTreeFuncs](prev: Map[K, V] = Map.empty, code: V): Map[K, V] = prev.updated(value, code)
}

case class HuffmanTreeBranch[+A](a: HuffmanTree[A], b: HuffmanTree[A], weight: Int) extends HuffmanTree[A] {
  def toMap[K >: A, V : BTreeFuncs](prev: Map[K, V] = Map.empty, code: V): Map[K, V] = {
    val withA = a.toMap(prev, code.goA)
    b.toMap(withA, code.goB)
  }
}

object HuffmanTree {
  implicit def leafEncoder[A: Encoder]: Encoder[HuffmanTreeLeaf[A]] = deriveEncoder
  implicit def branchEncoder[A: Encoder]: Encoder[HuffmanTreeBranch[A]] = deriveEncoder
  implicit def treeEncoder[A: Encoder]: Encoder[HuffmanTree[A]] = Encoder.instance {
    case leaf: HuffmanTreeLeaf[A] => deriveEncoder[HuffmanTreeLeaf[A]].apply(leaf)
    case branch: HuffmanTreeBranch[A] => deriveEncoder[HuffmanTreeBranch[A]].apply(branch)
  }

  implicit def leafDecoder[A : Decoder]: Decoder[HuffmanTreeLeaf[A]] = deriveDecoder
  implicit def branchDecoder[A : Decoder]: Decoder[HuffmanTreeBranch[A]] = deriveDecoder
  implicit def treeDecoder[A : Decoder]: Decoder[HuffmanTree[A]] =
    deriveDecoder[HuffmanTreeLeaf[A]]
    .or(deriveDecoder[HuffmanTreeBranch[A]].widen)

  def apply[A](seq: Seq[(A, Int)]): HuffmanTree[A] = {
    @tailrec
    def iter(layer: SortedSeq[HuffmanTree[A]]): HuffmanTree[A] = {
      if (layer.seq.size === 1) return layer.seq.head
      val newBranch: HuffmanTreeBranch[A] = layer.seq.slice(0, 2) match {
        case a :: b :: Nil => HuffmanTreeBranch(a, b, a.weight + b.weight)
      }

      val nextLayer = new SortedSeq(layer.seq.slice(2, layer.seq.size))(layer.comparator).added(newBranch)
      iter(nextLayer)
    }

    iter(SortedSeq[HuffmanTree[A]](seq.map {
      case (value, count) => HuffmanTreeLeaf(value, count)
    })(_.weight < _.weight))
  }
}
