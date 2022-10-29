import cats.effect.{ExitCode, IO, IOApp, Resource}
import scopt.OParser

import java.io.{FileInputStream, FileOutputStream}
import DesObjectSyntax._
import DesFInstances.desByteArrayInstance

case class Config(
  mode: Option[ConfigModes] = None,
  inputFile: String = "",
  outputFile: String = "",
  key: String = "",
)

sealed trait ConfigModes
case object ConfigModeCypher extends ConfigModes
case object ConfigModeDecipher extends ConfigModes

/*
 * DesObject.scala -- тайп-класс с описанием поведения объекта, используемого Des.
 *    Первая реализация объекта -- на основе Array[Byte]. Стоит добавить реализацию на BitSet.
 * Des.scala -- константы Des, класс Des, включающий себя алгоритм шифрования (и дешифрования, см. конструктор класса)
 *
 * Пример запуска:
 * @java-stuff@ cypher --input test-input.txt --output test-output.txt --key FFFFAAAA
 * @java-stuff@ decipher --input test-output.txt --output test-output-deciphered.txt --key FFFFAAAA
 *
 * Особенности:
 * - входной ключ считывается в кодировке UTF-8
 * - отсутствуют проверки, так что почти ничто не оборачивается в Option или Either :(
 */
object Main extends IOApp{
  private val builder = OParser.builder[Config]
  private val argParser = {
    import builder._
    OParser.sequence(
      programName("des"),
      cmd("cypher")
        .action((_, c) => c.copy(mode = Some(ConfigModeCypher)))
        .text("Cypher-mode"),
      cmd("decipher")
        .action((_, c) => c.copy(mode = Some(ConfigModeDecipher)))
        .text("Decipher-mode"),
      opt[String]('i', "input")
        .required()
        .action((a, c) => c.copy(inputFile = a))
        .text("Input file"),
      opt[String]('o', "output")
        .required()
        .action((a, c) => c.copy(outputFile = a))
        .text("Output file"),
      opt[String]('k', "key")
        .required()
        .action((a, c) => c.copy(key = a))
        .text("Key 8byte-size as utf-8")
    )
  }

  def run(args: List[String]): IO[ExitCode] = {
    for {
      config <- OParser.parse(argParser, args, Config())
      mode <- config.mode
    } yield mode match {
      case ConfigModeCypher => cypherFile(config.inputFile, config.outputFile, config.key, isDecipher = false)
      case ConfigModeDecipher => cypherFile(config.inputFile, config.outputFile, config.key, isDecipher = true)
      case _ => IO(ExitCode.Error)
    }
  }.getOrElse(IO(ExitCode.Error))

  private def cypherNext[A: DesObject](input: FileInputStream, output: FileOutputStream, des: Des[A]): IO[Unit] = for {
    bytes <- IO(input.readNBytes(8))
    cyphered = des.cypher(bytes)
    _ <- IO(output.write(cyphered.fromModel))
    _ <- if (bytes.length == 8) cypherNext(input, output, des) else IO.unit
  } yield ()

  private def decipherNext[A: DesObject](input: FileInputStream, output: FileOutputStream, des: Des[A],
    prevBlock: Option[A] = None): IO[Unit] = for {

    bytes <- IO(input.readNBytes(8))
    _ <- bytes.length match {
      case 8 =>
        for {
          _ <- prevBlock match {
            case Some(block) => IO(output.write(block.fromModel))
            case None => IO.unit
          }
          cyphered = des.cypher(bytes)
          _ <- decipherNext(input, output, des, Some(cyphered))
        } yield ()
      case _ =>
        prevBlock match {
          case Some(block) => IO(output.write(block.trunc.fromModel))
          case None => IO.unit
        }
    }
  } yield ()

  private def cypherFile(filenameIn: String, filenameOut: String, key: String, isDecipher: Boolean): IO[ExitCode] =
    (
      for {
        input <- Resource.fromAutoCloseable(IO(new FileInputStream(filenameIn)))
        output <- Resource.fromAutoCloseable(IO(new FileOutputStream(filenameOut)))
      } yield (input, output)
    ).use { case (input, output) =>
      val innerKey = toModel(key.getBytes())
      val des = new Des(innerKey, isDecipher)
      val start = if (isDecipher) decipherNext(input, output, des)
                  else cypherNext(input, output, des)
      start.as(ExitCode.Success)
    }
}