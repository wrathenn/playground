package com.wrathenn.compilers
package readers.graphviz

import models.G5
import readers.InputPointer
import readers.instances.{MultiplierReader, PrimaryReader, SummandReader}

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
  val r = SummandReader.read(ip).getOrElse(???)

  pprint.pprintln(r._1)
  val dot = Graph(
    attributes = GraphAttributes(fontname = "Helvetica", fontsize = 16),
    nodeDefaults = NodeAttributes(fontname = "Helvetica", fontsize = 16),
    edgeDefaults = EdgeAttributes(fontname = "Helvetica", fontsize = 16),
    elements = visualizerSummand.getElement(r._1, "A")._1.accumulate
  )

  println(dot.render)
  
}
