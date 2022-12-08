import rsa.Algorithm._
import cats.effect.{ExitCode, IO, IOApp, Resource}
import rsa.{Algorithm, Key}
import scopt.OParser

import java.io.{FileInputStream, FileOutputStream, InputStream}
import scala.annotation.tailrec
import scala.reflect.ClassTag

case class Config(
  mode: Option[ConfigModes] = None,
  keyFile: String = "",
  inputFile: String = "",
  outputFile: String = "",
)

sealed trait ConfigModes
case object ConfigModeCypher extends ConfigModes
case object ConfigModeDecipher extends ConfigModes
case object ConfigModeGenerate extends ConfigModes

/*
 * Пример запуска:
 * @java-stuff@ generate --key test
 * @java-stuff@ cypher --input test.txt --output test-cy.txt --key test.pub
 * @java-stuff@ decipher --input test-cy.txt --output test-decy.txt --key test
 */
object Main extends IOApp {
  private val builder = OParser.builder[Config]
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
      config <- OParser.parse(argParser, args, Config())
      mode <- config.mode
    } yield mode match {
      case ConfigModeCypher => runCypher(config.keyFile, config.inputFile, config.outputFile)
      case ConfigModeDecipher => runDecipher(config.keyFile, config.inputFile, config.outputFile)
      case ConfigModeGenerate => generateNewKeys(config.keyFile)
      case _ => IO(ExitCode.Error)
    }
  }.get

  private def readKey(file: InputStream): IO[Option[Key]] = for {
    s <- IO { new String(file.readAllBytes()) }
  } yield Key.fromString(s)

  // ГЕНЕРАЦИЯ
  private def generateNewKeys(output: String): IO[ExitCode] = { for {
    outPublic <- Resource.fromAutoCloseable(IO { new FileOutputStream(s"$output.pub") })
    outPrivate <- Resource.fromAutoCloseable(IO { new FileOutputStream(output) })
    key = generateKeys()
    _ <- Resource.eval(IO { outPrivate.write(key._1.toString.getBytes()) })
    _ <- Resource.eval( IO { outPublic.write(key._2.toString.getBytes()) })
  } yield () }.use(_ => IO(ExitCode.Success))

  // ДЕШИФРОВКА
  private def runDecipher(keyFilename: String, inputFilename: String, outputFilename: String): IO[ExitCode] = {
    for {
      outputStream <- Resource.fromAutoCloseable(IO { new FileOutputStream(outputFilename) })
      inputStream <- Resource.fromAutoCloseable(IO { new FileInputStream(inputFilename) })
      keyStream <- Resource.fromAutoCloseable(IO { new FileInputStream(keyFilename) })
    } yield (inputStream, outputStream, keyStream)
  }.use { case (inputStream, outputStream, keyStream) => for {
      key <- readKey(keyStream)
      res <- key match {
        case Some(key) => decipherFile(inputStream, outputStream, key)
        case None => IO(ExitCode.Error)
      }
    } yield res
  }

  private def decipherFile(inputStream: FileInputStream, outputStream: FileOutputStream, key: Key): IO[ExitCode] = {
    @tailrec
    def cutBlock(block: Array[Byte]): Array[Byte] =
      block match {
        case Array(1, _*) => block.tail
        case Array(0, _*) => cutBlock(block.tail)
        case _ => block
      }

    def decipherRec: IO[ExitCode] = for {
      input <- IO { inputStream.readNBytes(256) }
      res <- if (input.nonEmpty) writeBlock(input) *> decipherRec
      else IO(ExitCode.Success)
    } yield res

    def writeBlock(block: Array[Byte]): IO[Unit] = {
      val deciphered = Algorithm.cypher(BigInt(block), key).toByteArray
      val cutted = cutBlock(deciphered)
      IO { outputStream.write(cutted) }
    }

    decipherRec
  }

  // ШИФРОВАНИЕ
  private def runCypher(keyFilename: String, inputFilename: String, outputFilename: String): IO[ExitCode] = {
    for {
      outputStream <- Resource.fromAutoCloseable(IO { new FileOutputStream(outputFilename) })
      inputStream <- Resource.fromAutoCloseable(IO { new FileInputStream(inputFilename) })
      keyStream <- Resource.fromAutoCloseable(IO { new FileInputStream(keyFilename) })
    } yield (inputStream, outputStream, keyStream)
  }.use { case (inputStream, outputStream, keyStream) => for {
      key <- readKey(keyStream)
      res <- key match {
        case Some(key) => cypherFile(inputStream, outputStream, key)
        case None => IO(ExitCode.Error)
      }
    } yield res
  }

  private def cypherFile(inputStream: FileInputStream, outputStream: FileOutputStream, key: Key): IO[ExitCode] = {
    val blockSize = key.blockByteSize()

    def extendArray[A: ClassTag](array: Array[A], toSize: Int): Array[A] =
      Array.ofDim[A](toSize - array.length) ++ array

    def extendBlock(array: Array[Byte], toSize: Int): Array[Byte] =
      Array.ofDim[Byte](toSize - array.length - 1) ++ (1.toByte +: array)

    def cypherRec: IO[ExitCode] = for {
      input <- IO { inputStream.readNBytes(blockSize) }
      res <- if (input.nonEmpty) writeBlock(input) *> cypherRec
      else IO(ExitCode.Success)
    } yield res

    def writeBlock(block: Array[Byte]): IO[Unit] = {
      val extended = extendBlock(block, 256)
      val cyphered = Algorithm.cypher(BigInt(extended), key).toByteArray
      val zeroExtended = extendArray(cyphered, 256)
      IO { outputStream.write(zeroExtended) }
    }

    cypherRec
  }
}
