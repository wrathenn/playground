package io

import cats.effect.{Blocker, ContextShift, Sync}
import cats.syntax.all._
import io.circe.Decoder
import io.circe.jawn.decode

import java.io.{InputStream, OutputStream}

object FileIO {
  def readLine[F[_]: Sync](blocker: Blocker, stream: InputStream)
    (implicit contextShift: ContextShift[F]): F[Array[Byte]] = {
    def iter(res: List[Byte] = List()): F[List[Byte]] = for {
      i <- blocker.delay { stream.read() }
      r <- if (i === -1 || i === '\n'.toByte) Sync[F].pure(res.reverse) else iter(i.toByte +: res)
    } yield r

    iter().map(_.toArray)
  }

  def readAll[F[_]: Sync](blocker: Blocker, stream: InputStream)
    (implicit contextShift: ContextShift[F]): F[Array[Byte]] = blocker.delay {
    stream.readAllBytes()
  }

  def readAndDecode[F[_]: Sync, A: Decoder](readEffect: F[Array[Byte]]): F[Either[io.circe.Error, A]] =
    readEffect.flatMap { bytes =>
      Sync[F].delay { decode[A](new String(bytes)) }
    }

  def copyAll[F[_] : Sync, S <: InputStream, D <: OutputStream](
    blocker: Blocker,
    srcStream: S,
    dstStream: D,
    buffer: Array[Byte],
    sumCount: Long = 0
  )(implicit contextShift: ContextShift[F]): F[Long] = for {
    count <- Sync[F].delay {
      srcStream.read(buffer)
    }
    resCount <- if (count != -1) for {
      _ <- Sync[F].delay(dstStream.write(buffer, 0, count))
      res <- copyAll(blocker, srcStream, dstStream, buffer, sumCount + count)
    } yield res
    else Sync[F].pure(sumCount)
  } yield resCount


  def copyBuffered[F[_] : Sync, S <: InputStream, D <: OutputStream](
    blocker: Blocker,
    srcStream: S,
    dstStream: D
  )(implicit contextShift: ContextShift[F]): F[Long] = for {
    buffer <- Sync[F].delay(Array.ofDim[Byte](1024))
    copiedCount <- copyAll(blocker, srcStream, dstStream, buffer)
  } yield copiedCount
}
