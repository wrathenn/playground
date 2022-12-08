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
 * @java-stuff@ cypher --input test-input.txt --output test-output.txt --key FFFFAAAA11110000
 * @java-stuff@ decipher --input test-output.txt --output test-output-deciphered.txt --key FFFFAAAA11110000
 *
 * Особенности:
 * - входной ключ считывается в формате FFFFAAAA11110000
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
        .validate { x =>
          if (x.length == 16
            && x.count { c =>
              ('0' <= c && c <= '9') ||
              ('a' <= c && c <= 'f') ||
              ('A' <= c && c <= 'F')
            } == 16
          ) success
          else failure("Incorrect key format")
        }
    )
  }

  def run(args: List[String]): IO[ExitCode] = {
    for {
      config <- OParser.parse(argParser, args, Config())
      mode <- config.mode
    } yield mode match {
      case ConfigModeCypher => translateKey(config.key); cypherFile(config.inputFile, config.outputFile, config.key, isDecipher = false)
      case ConfigModeDecipher => cypherFile(config.inputFile, config.outputFile, config.key, isDecipher = true)
      case _ => IO(ExitCode.Error)
    }
  }.getOrElse(IO(ExitCode.Error))

  // Преобразовать ключ из строки в набор байтов
  private def translateKey(strKey: String): Array[Byte] = {
    def charMap(c: Char): Byte = (c match {
      case _ if '0' <= c && c <= '9' => c - '0'
      case _ if 'a' <= c && c <= 'f' => c - 'a' + 10
      case _ if 'A' <= c && c <= 'F' => c - 'A' + 10
    }).toByte

    val res = for {
      pair <- strKey.getBytes().sliding(2, 2).toList
      c1 = charMap(pair(0).toChar)
      c2 = charMap(pair(1).toChar)
    } yield ((c1 << 4) + c2).toByte
    res.toArray
  }

  // Рекурсивное шифрование следующего блока
  private def cypherNext[A: DesObject](input: FileInputStream, output: FileOutputStream, des: Des[A]): IO[Unit] = for {
    bytes <- IO(input.readNBytes(8))
    cyphered = des.cypher(bytes)
    _ <- IO(output.write(cyphered.fromModel))
    _ <- if (bytes.length == 8) cypherNext(input, output, des) else IO.unit
  } yield ()

  // Рекурсивная дешифрока следующего блока
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

  // Зашифровать или расшифровать всю информацию в файле
  private def cypherFile(filenameIn: String, filenameOut: String, key: String, isDecipher: Boolean): IO[ExitCode] =
    (
      for {
        input <- Resource.fromAutoCloseable(IO(new FileInputStream(filenameIn)))
        output <- Resource.fromAutoCloseable(IO(new FileOutputStream(filenameOut)))
      } yield (input, output)
    ).use { case (input, output) =>
      val innerKey = toModel(translateKey(key))
      val des = new Des(innerKey, isDecipher)
      val start = if (isDecipher) decipherNext(input, output, des)
                  else cypherNext(input, output, des)
      start.as(ExitCode.Success)
    }
}