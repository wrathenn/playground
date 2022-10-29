import cats.effect.{IO, Resource}
import cats.effect.implicits._
import scopt.OParser

import java.io.{FileInputStream, FileOutputStream, FileReader}
import Des.Des.{cypher, cypherInner}
import Des.DesFSyntax._
import Des.DesFInstances.desByteArrayInstance

case class Config(
  mode: Option[ConfigModes] = None,
  inputFile: String = "",
  outputFile: String = "",
  key: String = "",
)

sealed trait ConfigModes

case object ConfigModeCypher extends ConfigModes

case object ConfigModeDecipher extends ConfigModes

object Main {
  private val builder = OParser.builder[Config]
  private val argParser = {
    import builder._
    OParser.sequence(
      programName("des"),
      cmd("cypher")
        .action((_, c) => c.copy(mode = Some(ConfigModeCypher)))
        .text(""),
      cmd("decipher")
        .action((_, c) => c.copy(mode = Some(ConfigModeDecipher)))
        .text("Use machine to encrypt messages"),
      opt[String]('i', "input")
        .required()
        .action((a, c) => c.copy(inputFile = a))
        .text("File to cypher"),
      opt[String]('o', "output")
        .required()
        .action((a, c) => c.copy(outputFile = a))
        .text("File to decipher"),
      opt[String]('k', "key")
        .required()
        .action((a, c) => c.copy(key = a))
        .text("Key 8byte-size")
    )
  }

  def main(args: Array[String]): Unit =
    for {
      config <- OParser.parse(argParser, args, Config())
      mode <- config.mode
    } yield (mode match {
      case ConfigModeCypher => cypherFile(config.inputFile, config.outputFile, config.key)
      case ConfigModeDecipher => cypherFile(config.inputFile, config.outputFile, config.key, isDecipher = true)
      case _ => IO {println("Unknown mode")}
    }).unsafeRunSync()

  def cypherFile(filenameIn: String, filenameOut: String, key: String, isDecipher: Boolean = false): IO[Unit] =
    (
      for {
        input <- Resource.fromAutoCloseable(IO(new FileInputStream(filenameIn)))
        output <- Resource.fromAutoCloseable(IO(new FileOutputStream(filenameOut)))
      } yield (input, output)
    ).use { case (input, output) =>
      val innerKey = toInner(key.getBytes())

      def cypherNextBlock(): IO[Unit] = for {
        bytes <- IO(input.readNBytes(8))
        cyphered = cypher(bytes, innerKey, isDecipher)
        _ <- IO(output.write(cyphered.toArray))
        _ <- if (bytes.length == 8) cypherNextBlock()
             else IO.unit
      } yield ()

      def decipherNextBlock(prevBlock: Option[Array[Byte]] = None): IO[Unit] = for {
        bytes <- IO(input.readNBytes(8))
        _ <- bytes.length match {
          case 8 => for {
            _ <- prevBlock match {
              case Some(block) => IO(output.write(block))
              case None => IO.unit
            }
            cyphered = cypher(bytes, innerKey, isDecipher)
            _ <- decipherNextBlock(Some(cyphered.toArray))
          } yield ()
          case _ =>
            prevBlock match {
              case Some(block) => IO {
                val a = toInner(block).trunc.toOuter.toArray
                (output.write(a))
              }
              case None => IO.unit
            }
        }
      } yield ()

      if (isDecipher) decipherNextBlock() else cypherNextBlock()
    }
}