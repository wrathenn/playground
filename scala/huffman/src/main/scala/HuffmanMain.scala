import cats.effect.{Blocker, ContextShift, ExitCode, IO, IOApp, Resource, Sync}
import cats.implicits._
import huffman.Huffman.{cypherFile, decipherFile, processFile}
import io.circe.syntax._
import io.FileIO._
import scopt.OParser

import java.io.{FileInputStream, FileOutputStream}

private case class HuffmanConfig(
  mode: Option[HuffmanConfigModes] = None,
  inputFile: String = "",
  outputFile: String = ""
)

private sealed trait HuffmanConfigModes
private case object ConfigModeZip extends HuffmanConfigModes
private case object ConfigModeUnzip extends HuffmanConfigModes

object HuffmanMain extends IOApp {
  private val builder = OParser.builder[HuffmanConfig]
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

    OParser.sequence(
      programName("huffman"),
      cmd("zip")
        .action((_, c) => c.copy(mode = Some(ConfigModeZip)))
        .text("zip file"),
      cmd("unzip")
        .action((_, c) => c.copy(mode = Some(ConfigModeUnzip)))
        .text("unzip file"),
      fileInputArg,
      fileOutputArg
    )
  }

  def run(args: List[String]): IO[ExitCode] = {
    for {
      config <- OParser.parse(argParser, args, HuffmanConfig())
      mode <- config.mode
    } yield
      Blocker[IO].use { blocker =>
        mode match {
          case ConfigModeZip => zipFile[IO](blocker, config.inputFile, config.outputFile)
          case ConfigModeUnzip => unzipFile[IO](blocker, config.inputFile, config.outputFile)
        }
      }
  }.getOrElse(IO.pure(ExitCode.Error))


  def zipFile[F[_]: Sync](blocker: Blocker, inputFilename: String, outFilename: String)
    (implicit contextShift: ContextShift[F]): F[ExitCode] = {
    for {
      inputFileStream <- Resource.fromAutoCloseable(blocker.delay { new FileInputStream(inputFilename) })
      inputFileStreamCopy <- Resource.fromAutoCloseable(blocker.delay { new FileInputStream(inputFilename) })
      outFileStream <- Resource.fromAutoCloseable(blocker.delay { new FileOutputStream(outFilename) })
    } yield (inputFileStream, inputFileStreamCopy, outFileStream)
  }.use { case (inputStream, inputStreamCopy, outStream) => for {
    tree <- processFile(blocker, inputStream)
    _ <- blocker.delay { println(tree.asJson) }
    _ <- cypherFile(blocker, inputStreamCopy, outStream, tree)
  } yield ExitCode.Success
  }

  def unzipFile[F[_]: Sync](blocker: Blocker, inputFilename: String, outFilename: String)
    (implicit contextShift: ContextShift[F]): F[ExitCode] = {
    for {
      inputFileStream <- Resource.fromAutoCloseable(blocker.delay { new FileInputStream(inputFilename) })
      outFileStream <- Resource.fromAutoCloseable(blocker.delay { new FileOutputStream(outFilename) })
    } yield (inputFileStream, outFileStream)
  }.use { case (inputStream, outStream) => for {
    _ <- decipherFile(blocker, inputStream, outStream)
  } yield ExitCode.Success
  }
}
