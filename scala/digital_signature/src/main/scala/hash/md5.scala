package hash

import cats.effect.{Blocker, ContextShift, Sync}
import cats.syntax.all._

import java.io.{BufferedInputStream, InputStream}
import java.security.MessageDigest

object MD5 {
  def md5[F[_]: Sync](blocker: Blocker, inputStream: InputStream)
    (implicit contextShift: ContextShift[F]): F[String] = {
    def readIter(inputStream: BufferedInputStream, buf: Array[Byte], digest: MessageDigest): F[Unit] = for {
      i <- blocker.delay { inputStream.read(buf) }
      _ <- (Sync[F].delay { digest.update(buf, 0, i) } *> readIter(inputStream, buf, digest)).unlessA(i === -1)
    } yield ()

    val buffered = new BufferedInputStream(inputStream)
    val buffer = Array.ofDim[Byte](1024)
    for {
      md5 <- Sync[F].delay { MessageDigest.getInstance("MD5") }
      _ <- readIter(buffered, buffer, md5)
    } yield md5.digest().map { b => "%02x".format(0xFF & b) }.foldLeft("")(_ + _)
  }
}
