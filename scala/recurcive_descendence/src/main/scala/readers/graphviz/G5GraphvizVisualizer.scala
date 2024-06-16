package com.wrathenn.compilers
package readers.graphviz

import models.G5
import readers.InputPointer
import readers.instances.{ExpressionReader, IdentifierReader, MultiplierReader, OperatorReader, PrimaryReader, ProgramReader, RelationReader, SimpleExprReader, SummandReader}

import berlin.softwaretechnik.graphviz.attributes.{EdgeAttributes, GraphAttributes, NodeAttributes, Plain}
import berlin.softwaretechnik.graphviz.{Graph, Node}
import com.wrathenn.compilers.readers.graphviz.StringIdExt.IdInc

trait G5GraphvizVisualizer[A <: G5.NonTerminal] {
  // returns result and last used id
  def getElement(nt: A, lastId: String): (VizResult, String)

  protected def foldVis[B](elements: Seq[B], lastId: String)(
    visualize: (B, String) => (VizResult, String)
  ): (VizResult, String) =
    elements.foldLeft(VizResult.empty -> lastId) { case ((acc, lastUsedId), el) =>
      val (res, lastId) = visualize(el, lastUsedId.inc)
      acc.plus(res) -> lastId
    }

  protected def visualizeTwo[B <: G5.NonTerminal, C <: G5.NonTerminal](
    bVis: G5GraphvizVisualizer[B], cVis: G5GraphvizVisualizer[C]
  ): ((B, C), String) => (VizResult, String) = { case (bc: (B, C), id: String) =>
    val (b, c) = bc
    val (bRes, bId) = bVis.getElement(b, id)
    val (cRes, cId) = cVis.getElement(c, bId)
    (bRes plus cRes) -> cId
  }
}

object G5GraphvizVisualizer {
  def getSimpleVisualizer[A <: G5.NonTerminal](reprGetter: A => String): G5GraphvizVisualizer[A] =
    (a: A, lastId: String) => {
      val id = lastId.inc
      val node = Node(id, NodeAttributes(label = Plain(reprGetter(a))))
      VizResult(newElements = List(node -> id)) -> id
    }
}


object Test extends App {
  def ipFromString(data: String) = new InputPointer(data = data.toCharArray.toList)



  val ip = ipFromString("123 ** 3 ** 4 * not 12 * abs 727")
  val r = SimpleExprReader.read(ip).getOrElse(???)

  pprint.pprintln(r._1)
  val dot = Graph(
    attributes = GraphAttributes(fontname = "Helvetica", fontsize = 16),
    nodeDefaults = NodeAttributes(fontname = "Helvetica", fontsize = 16),
    edgeDefaults = EdgeAttributes(fontname = "Helvetica", fontsize = 16),
    elements = visualizerSimpleExpr.getElement(r._1, "A")._1.accumulate
  )

  println(dot.render)
  
}

object Test2 extends App {
  def ipFromString(data: String) = new InputPointer(data = data.toCharArray.toList)



  val ip = ipFromString("-asd & 2 & 3 + 132")
  val r = SimpleExprReader.read(ip).getOrElse(???)

  pprint.pprintln(r._1)
  val dot = Graph(
    attributes = GraphAttributes(fontname = "Helvetica", fontsize = 16),
    nodeDefaults = NodeAttributes(fontname = "Helvetica", fontsize = 16),
    edgeDefaults = EdgeAttributes(fontname = "Helvetica", fontsize = 16),
    elements = visualizerSimpleExpr.getElement(r._1, "A")._1.accumulate
  )

  println(dot.render)

}
object TestRelation extends App {
  def ipFromString(data: String) = new InputPointer(data = data.toCharArray.toList)



  val ip = ipFromString("2 ** 2 > 3 ** 3")
  val r = RelationReader.read(ip).getOrElse(???)

  pprint.pprintln(r._1)
  val dot = Graph(
    attributes = GraphAttributes(fontname = "Helvetica", fontsize = 16),
    nodeDefaults = NodeAttributes(fontname = "Helvetica", fontsize = 16),
    edgeDefaults = EdgeAttributes(fontname = "Helvetica", fontsize = 16),
    elements = visualizerRelation.getElement(r._1, "A")._1.accumulate
  )

  println(dot.render)

}
object TestExpression extends App {
  def ipFromString(data: String) = new InputPointer(data = data.toCharArray.toList)



  val ip = ipFromString("2 ** 2 > 3 ** 3 and 7 * 7 or 123")
  val r = ExpressionReader.read(ip).getOrElse(???)

  pprint.pprintln(r._1)
  val dot = Graph(
    attributes = GraphAttributes(fontname = "Helvetica", fontsize = 16),
    nodeDefaults = NodeAttributes(fontname = "Helvetica", fontsize = 16),
    edgeDefaults = EdgeAttributes(fontname = "Helvetica", fontsize = 16),
    elements = visualizerExpression.getElement(r._1, "A")._1.accumulate
  )

  println(dot.render)

}
object TestIdentifier extends App {
  def ipFromString(data: String) = new InputPointer(data = data.toCharArray.toList)



  val ip = ipFromString("val abc")
  val r = IdentifierReader.read(ip).getOrElse(???)

  pprint.pprintln(r._1)
  val dot = Graph(
    attributes = GraphAttributes(fontname = "Helvetica", fontsize = 16),
    nodeDefaults = NodeAttributes(fontname = "Helvetica", fontsize = 16),
    edgeDefaults = EdgeAttributes(fontname = "Helvetica", fontsize = 16),
    elements = visualizerIdentifier.getElement(r._1, "A")._1.accumulate
  )

  println(dot.render)

}
object TestOperator extends App {
  def ipFromString(data: String) = new InputPointer(data = data.toCharArray.toList)

  val ip = ipFromString("val abc = 123 * 43 ** 2")
  val r = OperatorReader.read(ip).getOrElse(???)

  pprint.pprintln(r._1)
  val dot = Graph(
    attributes = GraphAttributes(fontname = "Helvetica", fontsize = 16),
    nodeDefaults = NodeAttributes(fontname = "Helvetica", fontsize = 16),
    edgeDefaults = EdgeAttributes(fontname = "Helvetica", fontsize = 16),
    elements = visualizerOperator.getElement(r._1, "A")._1.accumulate
  )

  println(dot.render)

}
object TestProgram extends App {
  def ipFromString(data: String) = new InputPointer(data = data.toCharArray.toList)

  val ip = ipFromString(
    """{
      var a = 2 < 5 and 3 or 5;
      val b = 3;
     }""")
  val r = ProgramReader.read(ip).getOrElse(???)

  pprint.pprintln(r._1)
  val dot = Graph(
    attributes = GraphAttributes(fontname = "Helvetica", fontsize = 16),
    nodeDefaults = NodeAttributes(fontname = "Helvetica", fontsize = 16),
    edgeDefaults = EdgeAttributes(fontname = "Helvetica", fontsize = 16),
    elements = visualizerProgram.getElement(r._1, "A")._1.accumulate
  )

  println(dot.render)

}
