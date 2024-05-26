package com.wrathenn.compilers

import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.all._

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = for {
    _ <- IO println ""
  } yield ExitCode.Success
}
