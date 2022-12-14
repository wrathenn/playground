import cats.effect.{Blocker, ContextShift, ExitCode, IO, IOApp, Resource, Sync}
import cats.implicits._
import io.circe.syntax._
import rsa.{Algorithm, Key}
import hash.MD5.md5
import io.FileIO._
import signature.Signature
import scopt.OParser

import java.io.{FileInputStream, FileOutputStream}

private case class SignatureConfig(
  mode: Option[SignatureConfigModes] = None,
  keyFile: String = "",
  inputFile: String = "",
  certificateFile: String = ""
)

private sealed trait SignatureConfigModes
private case object ConfigModeCreateSign extends SignatureConfigModes
private case object ConfigModeCheckSignature extends SignatureConfigModes

object SignatureMain extends IOApp {
  private val builder = OParser.builder[SignatureConfig]
  private val argParser = {
    import builder._
    val fileInputArg = opt[String]('i', "input")
      .required()
      .action((a, c) => c.copy(inputFile = a))
      .text("Input file")
    val certificateArg = opt[String]('c', "certificate")
      .required()
      .action((a, c) => c.copy(certificateFile = a))
      .text("Certificate filename")
    val keyArg = opt[String]('k', "key")
      .required()
      .action((a, c) => c.copy(keyFile = a))
      .text("Key filename")

    OParser.sequence(
      programName("signature"),
      cmd("sign")
        .action((_, c) => c.copy(mode = Some(ConfigModeCreateSign)))
        .text("sign input file")
        .children(certificateArg),
      cmd("check-sign")
        .action((_, c) => c.copy(mode = Some(ConfigModeCheckSignature)))
        .text("check signature and rewrite file without signature"),
      fileInputArg,
      keyArg
    )
  }

  def run(args: List[String]): IO[ExitCode] = {
    for {
      config <- OParser.parse(argParser, args, SignatureConfig())
      mode <- config.mode
    } yield
      Blocker[IO].use { blocker =>
        mode match {
          case ConfigModeCreateSign => signFile[IO](blocker, config.inputFile, config.keyFile, config.certificateFile)
          case ConfigModeCheckSignature => checkSignedFile[IO](blocker, config.inputFile, config.keyFile)
        }
      }
  }.getOrElse(IO.pure(ExitCode.Error))


  def signFile[F[_]: Sync](blocker: Blocker, filename: String, keyFilename: String, certificateFilename: String)
    (implicit contextShift: ContextShift[F]): F[ExitCode] = {
    for {
      inputFileStream <- Resource.fromAutoCloseable(blocker.delay { new FileInputStream(filename) })
      inputFileStreamCopy <- Resource.fromAutoCloseable(blocker.delay { new FileInputStream(filename) })
      keyFileStream <- Resource.fromAutoCloseable(blocker.delay { new FileInputStream(keyFilename) })
      certificateFileStream <- Resource.fromAutoCloseable(blocker.delay { new FileInputStream(certificateFilename) })
      outFileStream <- Resource.fromAutoCloseable(blocker.delay { new FileOutputStream(s"signed.$filename") })
    } yield (inputFileStream, inputFileStreamCopy, keyFileStream, certificateFileStream, outFileStream)
  }.use { case (inputStream, inputStreamCopy, keyStream, credsStream, outStream) => for {
    inputMd5Sum <- md5(blocker, inputStream)

    key <- readAndDecode[F, Key](readAll(blocker, keyStream)).flatMap(_.liftTo[F])
    cypheredMd5Sum = Algorithm.cypher(BigInt(inputMd5Sum.getBytes()), key)

    certificate <- readAndDecode[F, Signature](readAll(blocker, credsStream)).flatMap(_.liftTo[F])
    signature = Signature(certificate.name, certificate.org, cypheredMd5Sum.toString)
    _ <- blocker.delay { outStream.write((signature.asJson.noSpaces + "\n").getBytes) }
    _ <- copyBuffered(blocker, inputStreamCopy, outStream)
  } yield ExitCode.Success
  }

  def checkSignedFile[F[_]: Sync](blocker: Blocker, filename: String, keyFilename: String)
    (implicit contextShift: ContextShift[F]): F[ExitCode] = {
    for {
      inputFileStream <- Resource.fromAutoCloseable(blocker.delay { new FileInputStream(filename) })
      inputFileStreamCopy <- Resource.fromAutoCloseable(blocker.delay { new FileInputStream(filename) })
      keyFileStream <- Resource.fromAutoCloseable(blocker.delay { new FileInputStream(keyFilename) })
      outFileStream <- Resource.fromAutoCloseable(blocker.delay { new FileOutputStream(s"unsigned.$filename") })
    } yield (inputFileStream, inputFileStreamCopy, keyFileStream, outFileStream)
  }.use { case (inputStream, inputStreamCopy, keyStream, outStream) => for {
    signature <- readAndDecode[F, Signature](readLine(blocker, inputStream)).flatMap(_.liftTo[F])
    key <- readAndDecode[F, Key](readAll(blocker, keyStream)).flatMap(_.liftTo[F])

    decipheredMd5Bytes = new String(Algorithm.cypher(BigInt(signature.hash), key).toByteArray)
    actualMd5 <- md5(blocker, inputStream)

    _ <- Sync[F].delay(println(decipheredMd5Bytes))
    _ <- Sync[F].delay(println(actualMd5))

    res <- if (decipheredMd5Bytes != actualMd5) Sync[F].pure(ExitCode.Error)
      else readLine(blocker, inputStreamCopy) *>
      Sync[F].delay(println("Equal hashes, creating file without signature")) *>
      copyBuffered(blocker, inputStreamCopy, outStream).as(ExitCode.Success)
  } yield res
  }
}
