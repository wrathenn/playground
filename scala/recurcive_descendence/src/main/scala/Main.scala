package com.wrathenn.compilers

import readers.InputPointer
import readers.instances.ProgramReader

import berlin.softwaretechnik.graphviz.attributes.RankType.same
import berlin.softwaretechnik.graphviz.attributes.html.{Align, Cell, StyleTag, Table, TableAttributes, TableCellAttributes, TextList, b, textAttr, u}
import berlin.softwaretechnik.graphviz.{Edge, Graph, Node, SubGraph}
import berlin.softwaretechnik.graphviz.attributes.{Color, EdgeAttributes, GraphAttributes, NodeAttributes, Plain, SubgraphAttributes}
import cats.effect.{ExitCode, IO, IOApp, Resource}
import berlin.softwaretechnik.graphviz.attributes.Shape.{box, none}
import berlin.softwaretechnik.graphviz.attributes.Style.dashed

import scala.io.Source.fromFile

object Main extends IOApp {
  private def readFile(filename: String): IO[String] = {
    Resource.make(IO delay fromFile(filename)) {
      IO delay _.close()
    }.use { IO delay _.getLines().mkString }
  }

  override def run(args: List[String]): IO[ExitCode] = for {
//    _ <- IO print "Enter your program filename: "
//    filename <- IO.readLine
    filename <- IO pure "examples/program1.txt"
    program <- readFile(filename)
    ip = new InputPointer(program.toCharArray.toList)
    (program, _) <- IO fromEither ProgramReader.read(ip)
    dot <- IO delay Graph(
      attributes = GraphAttributes(fontname = "Helvetica", fontsize = 16),
      nodeDefaults = NodeAttributes(fontname = "Helvetica", fontsize = 16),
      edgeDefaults = EdgeAttributes(fontname = "Helvetica", fontsize = 16),
      elements = Seq(
        Node("A", NodeAttributes(
          shape = none,
          label = Table(
            TableAttributes(color = Color("gray"), border = 0, cellSpacing = 0, cellBorder = 1, cellPadding = 3),
            Seq(
              Seq(Cell(TextList(Seq(Plain("Good "), StyleTag("I", Plain("bye!")))))),
              Seq(Cell(TableCellAttributes(align = Align.Left), StyleTag("B", StyleTag("U", Plain("Hello!"))))),
              Seq(Cell(TableCellAttributes(align = Align.Left), b(u("Hello!"),"No underline."))),
              Seq(Cell(TableCellAttributes(align = Align.Left), textAttr(bold = true, color = Color("#00D000"))("Does this work?"))),
            )))),
        Node("C"),
        SubGraph(attributes = SubgraphAttributes(rank = same), elements = Seq(
          Node("B", NodeAttributes(shape = box, label = Plain("Two\nLines"))),
          Node("D", NodeAttributes(color = Color("#FF0000"))),
        )),
        Edge("A", "B", EdgeAttributes(label = new Plain("This is a label"))),
        Edge("A", "C"),
        Edge("B", "C"),
        Edge("B", "D", EdgeAttributes(style = dashed)),
      )
    )
    dotString <- IO delay dot.render
    _ <- IO delay os.write.over(os.pwd / "results" / "test.dot", dotString)
    _ <- IO println "Program parsed successfully:"
    _ <- IO println dotString
    _ <- IO blocking pprint.pprintln(program)
  } yield ExitCode.Success
}
