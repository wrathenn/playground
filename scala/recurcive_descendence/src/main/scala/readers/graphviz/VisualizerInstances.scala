package com.wrathenn.compilers
package readers.graphviz

import models.G5.NonTerminal
import models.G5.NonTerminal.Primary
import readers.graphviz.G5GraphvizVisualizer.getSimpleVisualizer
import readers.graphviz.StringIdExt.IdInc
import readers.graphviz.VisualizerInstances.{visualizerMultiplicativeOperation, visualizerNumericLiteral, visualizerVariable}

import berlin.softwaretechnik.graphviz.attributes.{NodeAttributes, Plain}
import berlin.softwaretechnik.graphviz.{Edge, Node}

object VisualizerInstances {
  val visualizerMultiplicativeOperation = getSimpleVisualizer[NonTerminal.MultiplicativeOperation](_.terminal.repr)
  val visualizerUnaryAdditiveOperation = getSimpleVisualizer[NonTerminal.UnaryAdditiveOperation](_.terminal.repr)
  val visualizerBinaryAdditiveOperation = getSimpleVisualizer[NonTerminal.BinaryAdditiveOperation](_.terminal.repr)
  val visualizerRelationOperation = getSimpleVisualizer[NonTerminal.RelationOperation](_.terminal.repr)
  val visualizerLogicalOperation = getSimpleVisualizer[NonTerminal.LogicalOperation](_.terminal.repr)
  val visualizerNumericLiteral = getSimpleVisualizer[NonTerminal.NumericLiteral](_.terminal.repr)
  val visualizerVariable = getSimpleVisualizer[NonTerminal.Variable](_.terminal.repr)
}

object visualizerPrimary extends G5GraphvizVisualizer[NonTerminal.Primary] {
  override def getElement(nt: NonTerminal.Primary, lastId: String): (VizResult, String) = {
    val myId = lastId.inc

    val ((childrenRes, lastUsedId), label) = nt match {
      case Primary.NumericLiteral(nonTerminal) => visualizerNumericLiteral.getElement(nonTerminal, myId) -> "P_num"
      case Primary.Variable(nonTerminal) => visualizerVariable.getElement(nonTerminal, myId) -> "P_var"
      case Primary.BracketExpr(expr) => ???
    }
    val node = Node(myId, NodeAttributes(label = Plain(label)))

    val edges = childrenRes.newElements.map { case (_, elId) => Edge(myId, elId) }
    VizResult(
      newElements = List(node -> myId),
      acc = childrenRes.accumulate ++ edges,
    ) -> lastUsedId
  }
}

object visualizerMultiplier extends G5GraphvizVisualizer[NonTerminal.Multiplier] {
  override def getElement(nt: NonTerminal.Multiplier, lastId: String): (VizResult, String) = {
    val myId = lastId.inc

    val ((childrenRes, lastUsedId), label) = nt match {
      case NonTerminal.Multiplier.PrimaryPowers(p, other) =>
        foldVis(p +: other, myId) { visualizerPrimary.getElement } -> "Powers"
      case NonTerminal.Multiplier.Abs(p) => visualizerPrimary.getElement(p, myId) -> "Abs"
      case NonTerminal.Multiplier.Not(p) => visualizerPrimary.getElement(p, myId) -> "Not"
    }

    val node = Node(myId, NodeAttributes(label = Plain(label)))

    val edges = childrenRes.newElements.map { case (_, elId) => Edge(myId, elId) }
    VizResult(
      newElements = List(node -> myId),
      acc = childrenRes.accumulate ++ edges,
    ) -> lastUsedId
  }
}


object visualizerSummand extends G5GraphvizVisualizer[NonTerminal.Summand] {
  override def getElement(nt: NonTerminal.Summand, lastId: String): (VizResult, String) = {
    val myId = lastId.inc

    val (firstRes, firstId) = visualizerMultiplier.getElement(nt.firstMultiplier, myId)
    val (otherRes, otherId) = foldVis(nt.other, firstId) { case ((mOp, m), id) =>
      val (mOpRes, mOpId) = visualizerMultiplicativeOperation.getElement(mOp, id)
      val (mRes, mId) = visualizerMultiplier.getElement(m, mOpId)
      (mOpRes plus mRes) -> mId
    }
    val (childrenRes, label) = (firstRes plus otherRes) -> "Summand"

    val node = Node(myId, NodeAttributes(label = Plain(label)))

    val edges = childrenRes.newElements.map { case (_, elId) => Edge(myId, elId) }
    VizResult(
      newElements = List(node -> myId),
      acc = childrenRes.accumulate concat edges,
    ) -> otherId
  }
}
