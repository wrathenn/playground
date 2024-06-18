package com.wrathenn.compilers

import models.G5.NonTerminal
import readers.InputPointer
import readers.graphviz.visualizerProgram
import readers.instances.ProgramReader

import berlin.softwaretechnik.graphviz.Graph
import berlin.softwaretechnik.graphviz.attributes.{EdgeAttributes, GraphAttributes, NodeAttributes}
import cats.effect.{ExitCode, IO, IOApp, Resource}
import com.wrathenn.compilers.readers.postfix.programWriter

import scala.io.Source.fromFile

object Main extends IOApp {
  private def readFile(filename: String): IO[String] = {
    Resource.make(IO delay fromFile(filename)) {
      IO delay _.close()
    }.use { IO delay _.getLines().mkString }
  }

  private def getProgramGraph(program: NonTerminal.Program): IO[String] = {
    val dot = Graph(
      attributes = GraphAttributes(fontname = "Helvetica", fontsize = 16),
      nodeDefaults = NodeAttributes(fontname = "Helvetica", fontsize = 16),
      edgeDefaults = EdgeAttributes(fontname = "Helvetica", fontsize = 16),
      elements = visualizerProgram.getElement(program, ('A'.toInt - 1).toChar.toString)._1.accumulate
    )
    IO delay dot.render
  }

  override def run(args: List[String]): IO[ExitCode] = for {
    _ <- IO print "Путь к файлу программы: "
    filename <- IO.readLine
//    filename <- IO pure "examples/program1.txt"
    program <- readFile(filename)
    ip = new InputPointer(program.toCharArray.toList)
    (program, _) <- IO fromEither ProgramReader.read(ip)
    _ <- IO blocking pprint.pprintln(program)
    programGraph <- getProgramGraph(program)
    _ <- IO println "Посетите https://dreampuf.github.io/GraphvizOnline/ и скопируйте:"
    _ <- IO println programGraph
    _ <- IO println "В обратной польской нотации:"
    _ <- IO println programWriter.write(program)
  } yield ExitCode.Success
}
