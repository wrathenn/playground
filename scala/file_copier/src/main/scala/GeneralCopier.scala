import cats.effect.concurrent.Semaphore
import cats.effect.{ Concurrent, ExitCode, IO, IOApp, Resource, Sync }
import cats.syntax.all._

import java.io.{ File, FileInputStream, FileOutputStream, InputStream, OutputStream }

object GeneralCopier extends IOApp {
  def streamResource[F[_] : Sync, A <: AutoCloseable](
    opener: () => A,
    closeSemaphore: Semaphore[F]
  ): Resource[F, A] =
    Resource.make {
      Sync[F].delay { opener() }
    } { stream =>
      closeSemaphore.withPermit {
        Sync[F].delay { stream.close() }.handleErrorWith(_ => Sync[F].unit)
      }
    }

  def copyAll[F[_] : Sync, S <: InputStream, D <: OutputStream](
    srcStream: S,
    dstStream: D,
    buffer: Array[Byte],
    sumCount: Long = 0
  ): F[Long] = for {
    count <- Sync[F].delay { srcStream.read(buffer) }
    resCount <- if (count != -1) for {
      _ <- Sync[F].delay(dstStream.write(buffer, 0, count))
      res <- copyAll(srcStream, dstStream, buffer, sumCount + count)
    } yield res
                else Sync[F].pure(sumCount)
  } yield resCount

  def copyBuffered[F[_]: Sync, S <: InputStream, D <: OutputStream](
    srcStream: S,
    dstStream: D
  ): F[Long] = for {
    buffer <- Sync[F].delay(Array.ofDim[Byte](1024))
    copiedCount <- copyAll(srcStream, dstStream, buffer)
  } yield copiedCount

  def copy[F[_] : Concurrent](srcFile: File, dstFile: File): F[Long] =
    for {
      semaphore <- Semaphore[F](1)
      streams = for {
        srcStream <- streamResource(() => new FileInputStream(srcFile), semaphore)
        dstStream <- streamResource(() => new FileOutputStream(dstFile), semaphore)
      } yield (srcStream, dstStream)
      res <- streams.use { case (src, dst) =>
        semaphore.withPermit { copyBuffered(src, dst) }
      }
    } yield res

  override def run(args: List[String]): IO[ExitCode] = for {
    _ <- if (args.length < 2)
           IO.raiseError(new IllegalArgumentException("1st arg is Source, 2nd arg is Destination"))
         else IO.unit
    src = new File(args(0))
    dst = new File(args(1))
    count <- copy[IO](src, dst)
    _ <- IO(println(s"Copied $count bytes"))
  } yield ExitCode.Success
}