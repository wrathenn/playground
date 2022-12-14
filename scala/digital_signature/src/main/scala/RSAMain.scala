import rsa.Algorithm._
import cats.effect.{Blocker, ContextShift, ExitCode, IO, IOApp, Resource, Sync}
import cats.syntax.all._
import io.FileIO.{readAll, readAndDecode}
import rsa.{Algorithm, Key}
import scopt.OParser
import io.circe.syntax._

import java.io.{FileInputStream, FileOutputStream }
import scala.annotation.tailrec
import scala.reflect.ClassTag

/*
 * Пример запуска:
 * @java-stuff@ generate --key test
 * @java-stuff@ cypher --input test.txt --output test-cy.txt --key test.pub
 * @java-stuff@ decipher --input test-cy.txt --output test-decy.txt --key test
 */
object RSAMain extends IOApp {
  private case class RSAConfig(
    mode: Option[RSAConfigModes] = None,
    keyFile: String = "",
    inputFile: String = "",
    outputFile: String = "",
  )

  private sealed trait RSAConfigModes
  private case object ConfigModeCypher extends RSAConfigModes
  private case object ConfigModeDecipher extends RSAConfigModes
  private case object ConfigModeGenerate extends RSAConfigModes

  private val builder = OParser.builder[RSAConfig]
  private val argParser = {
    import builder._
    val fileInputArg = opt[String]('i', "input")
      .required()
      .action((a, c) => c.copy(inputFile = a))
      .text("Input file")
    val fileOutputArg = opt[String]('o', "output")
      .required()
      .action((a, c) => c.copy(outputFile = a))
      .text("Output file")
    val keyArg = opt[String]('k', "key")
      .required()
      .action((a, c) => c.copy(keyFile = a))
      .text("Key filename")

    OParser.sequence(
      programName("rsa"),
      cmd("cypher")
        .action((_, c) => c.copy(mode = Some(ConfigModeCypher)))
        .text("run rsa to cypher or decipher information")
        .children(
          fileInputArg
            .text("File to read pure info"),
          fileOutputArg
            .text("File to write cyphered info"),
        ),
      cmd("decipher")
        .action((_, c) => c.copy(mode = Some(ConfigModeDecipher)))
        .text("run rsa to cypher or decipher information")
        .children(
          fileInputArg
            .text("File to read cyphered info"),
          fileOutputArg
            .text("File to write pure info"),
        ),
      cmd("generate")
        .action((_, c) => c.copy(mode = Some(ConfigModeGenerate)))
        .text("generate new key-pair"),
      keyArg
    )
  }

  def run(args: List[String]): IO[ExitCode] = {
    for {
      config <- OParser.parse(argParser, args, RSAConfig())
      mode <- config.mode
    } yield
      Blocker[IO].use { blocker =>
        mode match {
          case ConfigModeCypher => runCypher[IO](blocker, config.keyFile, config.inputFile, config.outputFile)
          case ConfigModeDecipher => runDecipher[IO](blocker, config.keyFile, config.inputFile, config.outputFile)
          case ConfigModeGenerate => generateNewKeys[IO](blocker, config.keyFile)
        }
      }
  }.getOrElse(IO.pure(ExitCode.Error))

  // ГЕНЕРАЦИЯ
  private def generateNewKeys[F[_]: Sync](blocker: Blocker, output: String)
    (implicit contextShift: ContextShift[F]): F[ExitCode] = { for {
    outPublic <- Resource.fromAutoCloseable(blocker.delay { new FileOutputStream(s"$output.pub") })
    outPrivate <- Resource.fromAutoCloseable(blocker.delay { new FileOutputStream(output) })
    } yield (outPublic, outPrivate)
  }.use { case (outPublicStream, outPrivateStream) => for {
      key <- blocker.delay { generateKeys() }
      _ <- blocker.delay { outPrivateStream.write(key._1.asJson.noSpaces.getBytes()) }
      _ <- blocker.delay { outPublicStream.write(key._2.asJson.noSpaces.getBytes()) }
    } yield ExitCode.Success
  }

  // ДЕШИФРОВКА
  private def runDecipher[F[_]: Sync](blocker: Blocker, keyFilename: String, inputFilename: String, outputFilename: String)
    (implicit contextShift: ContextShift[F]): F[ExitCode] = {
    for {
      outputStream <- Resource.fromAutoCloseable(blocker.delay { new FileOutputStream(outputFilename) })
      inputStream <- Resource.fromAutoCloseable(blocker.delay { new FileInputStream(inputFilename) })
      keyStream <- Resource.fromAutoCloseable(blocker.delay { new FileInputStream(keyFilename) })
    } yield (inputStream, outputStream, keyStream)
  }.use { case (inputStream, outputStream, keyStream) => for {
      key <- readAndDecode[F, Key](readAll(blocker, keyStream)).flatMap(_.liftTo[F])
      res <- decipherFile(blocker, inputStream, outputStream, key)
    } yield res
  }

  private def decipherFile[F[_]: Sync](blocker: Blocker, inputStream: FileInputStream, outputStream: FileOutputStream, key: Key)
    (implicit contextShift: ContextShift[F]): F[ExitCode] = {
    @tailrec
    def cutBlock(block: Array[Byte]): Array[Byte] =
      block match {
        case Array(1, _*) => block.tail
        case Array(0, _*) => cutBlock(block.tail)
        case _ => block
      }

    def decipherRec: F[ExitCode] = for {
      input <- blocker.delay { inputStream.readNBytes(256) }
      res <- if (input.nonEmpty) writeBlock(input) *> decipherRec
        else Sync[F].pure(ExitCode.Success)
    } yield res

    def writeBlock(block: Array[Byte]): F[Unit] = {
      val deciphered = Algorithm.cypher(BigInt(block), key).toByteArray
      val cutted = cutBlock(deciphered)
      blocker.delay { outputStream.write(cutted) }
    }

    decipherRec
  }

  // ШИФРОВАНИЕ
  private def runCypher[F[_]: Sync](blocker: Blocker, keyFilename: String, inputFilename: String, outputFilename: String)
    (implicit contextShift: ContextShift[F]): F[ExitCode] = {
    for {
      outputStream <- Resource.fromAutoCloseable(blocker.delay { new FileOutputStream(outputFilename) })
      inputStream <- Resource.fromAutoCloseable(blocker.delay { new FileInputStream(inputFilename) })
      keyStream <- Resource.fromAutoCloseable(blocker.delay { new FileInputStream(keyFilename) })
    } yield (inputStream, outputStream, keyStream)
  }.use { case (inputStream, outputStream, keyStream) =>
    readAndDecode[F, Key](readAll(blocker, keyStream))
      .flatMap(_.liftTo[F])
      .flatMap(key => cypherFile(blocker, inputStream, outputStream, key))
  }

  private def cypherFile[F[_]: Sync](blocker: Blocker, inputStream: FileInputStream, outputStream: FileOutputStream, key: Key)
    (implicit contextShift: ContextShift[F]): F[ExitCode] = {
    val blockSize = key.blockByteSize()

    def extendArray[A: ClassTag](array: Array[A], toSize: Int): Array[A] =
      Array.ofDim[A](toSize - array.length) ++ array

    def extendBlock(array: Array[Byte], toSize: Int): Array[Byte] =
      Array.ofDim[Byte](toSize - array.length - 1) ++ (1.toByte +: array)

    def cypherRec: F[ExitCode] = for {
      input <- blocker.delay { inputStream.readNBytes(blockSize) }
      res <- if (input.nonEmpty) writeBlock(input) *> cypherRec
        else Sync[F].pure(ExitCode.Success)
    } yield res

    def writeBlock(block: Array[Byte]): F[Unit] = {
      val extended = extendBlock(block, 256)
      val cyphered = Algorithm.cypher(BigInt(extended), key).toByteArray
      val zeroExtended = extendArray(cyphered, 256)
      blocker.delay { outputStream.write(zeroExtended) }
    }

    cypherRec
  }
}
