import RSA._
import cats.effect.{ExitCode, IO, IOApp, Resource}
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
 * DesObject.scala -- тайп-класс с описанием поведения объекта, используемого Des.
 *    Первая реализация объекта -- на основе Array[Byte]. Стоит добавить реализацию на BitSet.
 * Des.scala -- константы Des, класс Des, включающий себя алгоритм шифрования (и дешифрования, см. конструктор класса)
 *
 * Пример запуска:
 * @java-stuff@ cypher --input test-input.txt --output test-output.txt --key FFFFAAAA11110000
 * @java-stuff@ decipher --input test-output.txt --output test-output-deciphered.txt --key FFFFAAAA11110000
 *
 * Особенности:
 * - входной ключ считывается в формате FFFFAAAA11110000
 * - отсутствуют проверки, так что почти ничто не оборачивается в Option или Either :(
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

  private def readKey(file: InputStream): IO[Option[RSAKey]] = for {
    s <- IO { new String(file.readAllBytes()) }
  } yield RSAKey.fromString(s)

  private def generateNewKeys(output: String): IO[ExitCode] = { for {
    outPublic <- Resource.fromAutoCloseable(IO { new FileOutputStream(s"$output.pub") })
    outPrivate <- Resource.fromAutoCloseable(IO { new FileOutputStream(output) })
    key = generateKeys()
    _ <- Resource.eval(IO { outPrivate.write(key._1.toString.getBytes()) })
    _ <- Resource.eval( IO { outPublic.write(key._2.toString.getBytes()) })
  } yield () }.use(_ => IO(ExitCode.Success))

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

  @tailrec
  private def cutBlock(block: Array[Byte]): Array[Byte] =
    block match {
      case Array(1, _*) => block.tail
      case Array(0, _*) => cutBlock(block.tail)
      case _ => block
    }

  private def decipherFile(inputStream: FileInputStream, outputStream: FileOutputStream, key: RSAKey): IO[ExitCode] = {
    val decipheredBlockSize = key.blockByteSize()

    def decipherRec: IO[ExitCode] = for {
      input <- IO { inputStream.readNBytes(256) }
      res <- if (input.nonEmpty) writeBlock(input) *> decipherRec
      else IO(ExitCode.Success)
    } yield res

    def writeBlock(block: Array[Byte]): IO[Unit] = {
      val deciphered = RSA.cypher(BigInt(block), key).toByteArray
      val cutted = cutBlock(deciphered)
      IO { outputStream.write(cutted) }
    }

    decipherRec
  }

  // Зашифровать или расшифровать всю информацию в файле
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

  private def extendArray[A: ClassTag](array: Array[A], toSize: Int): Array[A] =
    Array.ofDim[A](toSize - array.length) ++ array

  private def extendBlock(array: Array[Byte], toSize: Int): Array[Byte] =
    Array.ofDim[Byte](toSize - array.length - 1) ++ (1.toByte +: array)

  private def cypherFile(inputStream: FileInputStream, outputStream: FileOutputStream, key: RSAKey): IO[ExitCode] = {
    val blockSize = key.blockByteSize()

    def cypherRec: IO[ExitCode] = for {
      input <- IO { inputStream.readNBytes(blockSize) }
      res <- if (input.nonEmpty) writeBlock(input) *> cypherRec
      else IO(ExitCode.Success)
    } yield res

    def writeBlock(block: Array[Byte]): IO[Unit] = {
      val extended = extendBlock(block, 256)
      val cyphered = RSA.cypher(BigInt(extended), key).toByteArray
      val zeroExtended = extendArray(cyphered, 256)
      IO { outputStream.write(zeroExtended) }
    }

    cypherRec
  }
}
