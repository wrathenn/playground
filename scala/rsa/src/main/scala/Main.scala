import RSA._
import cats.effect.{ExitCode, IO, IOApp, Resource}
import scopt.OParser

import java.io.{FileInputStream, FileOutputStream}
import scala.annotation.tailrec

case class Config(
  mode: Option[ConfigModes] = None,
  keyFile: String = "",
  inputFile: String = "",
  outpuFile: String = "",
)

sealed trait ConfigModes

case object ConfigModeRun extends ConfigModes

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
    OParser.sequence(
      programName("rsa"),
      cmd("run")
        .action((_, c) => c.copy(mode = Some(ConfigModeRun)))
        .text("run rsa to cypher or decipher information"),
      cmd("generate")
        .action((_, c) => c.copy(mode = Some(ConfigModeGenerate)))
        .text("generate new key-pair"),
      opt[String]('i', "input")
        .action((a, c) => c.copy(inputFile = a))
        .text("Input filename"),
      opt[String]('o', "output")
        .action((a, c) => c.copy(outpuFile = a))
        .text("Output filename"),
      opt[String]('k', "key")
        .required()
        .action((a, c) => c.copy(keyFile = a))
        .text("Key filename")
    )
  }

  def run(args: List[String]): IO[ExitCode] = {
    for {
      config <- OParser.parse(argParser, args, Config())
      mode <- config.mode
    } yield mode match {
      case ConfigModeRun => cypherMessage(config.keyFile, config.inputMessage)
      case ConfigModeGenerate => generateNewKeys(config.keyFile)
      case _ => IO(ExitCode.Error)
    }
  }.get

  private def readKey(file: FileInputStream): IO[Option[RSAKey]] = for {
    s <- IO { new String(file.readAllBytes()) }
  } yield RSAKey.fromString(s)

  private def msgToBigInt(message: String): BigInt =
  //    message.getBytes().foldLeft(BigInt(0)) { (res, byte) => (res + byte) << 8 }
    BigInt(message)

  private def msgFromBigInt(msg: BigInt): String = {
    return s"$msg"
    @tailrec
    def recConvert(msg: BigInt, res: Array[Byte]): Array[Byte] =
      if (msg > 0) recConvert(msg >> 8,  (msg & 257).toByte +: res)
      else res

    new String(recConvert(msg, Array()))
  }

  private def generateNewKeys(output: String): IO[ExitCode] = { for {
    outPublic <- Resource.fromAutoCloseable(IO { new FileOutputStream(s"$output.pub") })
    outPrivate <- Resource.fromAutoCloseable(IO { new FileOutputStream(output) })
    key = generateKeys()
    _ <- Resource.eval(IO { outPrivate.write(key._1.toString.getBytes()) })
    _ <- Resource.eval( IO { outPublic.write(key._2.toString.getBytes()) })
  } yield () }.use(_ => IO(ExitCode.Success))

  // Зашифровать или расшифровать всю информацию в файле
  private def cypherMessage(keyFilename: String, message: String): IO[ExitCode] =
    Resource.fromAutoCloseable(IO { new FileInputStream(keyFilename) })
      .use { keyFile =>
        for {
          key <- readKey(keyFile)
          msg = msgToBigInt(message)
          res = RSA.cypher(msg, key.get)
          decoded = msgFromBigInt(res)
          _ <- IO { println(s"Result is:\n\t$decoded") }
        } yield ExitCode.Success
      }
}