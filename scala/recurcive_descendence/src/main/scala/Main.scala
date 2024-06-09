package com.wrathenn.compilers

import readers.InputPointer
import readers.instances.ProgramReader

import cats.effect.{ExitCode, IO, IOApp, Resource}

import scala.io.Source.fromFile

object Main extends IOApp {
  private def readFile(filename: String): IO[String] = {
    Resource.make(IO delay fromFile(filename)) {
      IO delay _.close()
    }.use { IO delay _.getLines().mkString }
  }

  override def run(args: List[String]): IO[ExitCode] = for {
    _ <- IO print "Enter your program filename: "
    filename <- IO.readLine
    program <- readFile(filename)
    ip = new InputPointer(program.toCharArray.toList)
    (program, _) <- IO fromEither ProgramReader.read(ip)
    _ <- IO println "Program parsed successfully:"
    _ <- IO blocking pprint.pprintln(program)
  } yield ExitCode.Success
}
