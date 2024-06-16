package com.wrathenn.compilers
package readers.graphviz

import models.G5.NonTerminal
import models.G5.NonTerminal.{OperatorTail, Primary}
import readers.graphviz.G5GraphvizVisualizer.getSimpleVisualizer
import readers.graphviz.StringIdExt.IdInc
import readers.graphviz.VisualizerInstances.{visualizerBinaryAdditiveOperation, visualizerLogicalOperation, visualizerMultiplicativeOperation, visualizerNumericLiteral, visualizerRelationOperation, visualizerUnaryAdditiveOperation, visualizerVariable}

import berlin.softwaretechnik.graphviz.attributes.{NodeAttributes, Plain}
import berlin.softwaretechnik.graphviz.{Edge, Node}

import scala.annotation.tailrec

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
    val (otherRes, otherId) = foldVis(nt.other, firstId) { visualizeTwo(visualizerMultiplicativeOperation, visualizerMultiplier) }
    val (childrenRes, label) = (firstRes plus otherRes) -> "Summand"

    val node = Node(myId, NodeAttributes(label = Plain(label)))

    val edges = childrenRes.newElements.map { case (_, elId) => Edge(myId, elId) }
    VizResult(
      newElements = List(node -> myId),
      acc = childrenRes.accumulate ++ edges,
    ) -> otherId
  }
}

object visualizerSimpleExpr extends G5GraphvizVisualizer[NonTerminal.SimpleExpr] {
  override def getElement(nt: NonTerminal.SimpleExpr, lastId: String): (VizResult, String) = {
    val myId = lastId.inc

    val (unaryRes, unaryId) = nt.unaryAdditiveOperation match {
      case Some(un) => visualizerUnaryAdditiveOperation.getElement(un, myId)
      case None => VizResult.empty -> myId
    }

    val (summandRes, summandId) = visualizerSummand.getElement(nt.summand, unaryId)
    val (otherRes, otherId) = foldVis(nt.otherSummands, summandId) { visualizeTwo(visualizerBinaryAdditiveOperation, visualizerSummand) }
    val (childrenRes, label) = (unaryRes plus summandRes plus otherRes) -> "SimpleExpr"

    val node = Node(myId, NodeAttributes(label = Plain(label)))

    val edges = childrenRes.newElements.map { case (_, elId) => Edge(myId, elId) }
    VizResult(
      newElements = List(node -> myId),
      acc = childrenRes.accumulate ++ edges,
    ) -> otherId
  }
}

object visualizerRelation extends G5GraphvizVisualizer[NonTerminal.Relation] {
  override def getElement(nt: NonTerminal.Relation, lastId: String): (VizResult, String) = {
    val myId = lastId.inc

    val (simpleExprRes, simpleExprId) = visualizerSimpleExpr.getElement(nt.simpleExpr, myId)
    val (otherRes, otherId) = nt.otherSimpleExpr match {
      case Some((rOp, sExpr)) => visualizeTwo(visualizerRelationOperation, visualizerSimpleExpr)((rOp, sExpr), simpleExprId)
      case None => VizResult.empty -> simpleExprId
    }
    val (childrenRes, label) = (simpleExprRes plus otherRes) -> "Relation"

    val node = Node(myId, NodeAttributes(label = Plain(label)))

    val edges = childrenRes.newElements.map { case (_, elId) => Edge(myId, elId) }
    VizResult(
      newElements = List(node -> myId),
      acc = childrenRes.accumulate ++ edges,
    ) -> otherId
  }
}

object visualizerExpression extends G5GraphvizVisualizer[NonTerminal.Expression] {
  override def getElement(nt: NonTerminal.Expression, lastId: String): (VizResult, String) = {
    val myId = lastId.inc

    val (firstRes, firstId) = visualizerRelation.getElement(nt.relation, myId)
    val (otherRes, otherId) = foldVis(nt.otherRelations, firstId) { visualizeTwo(visualizerLogicalOperation, visualizerRelation) }
    val (childrenRes, label) = (firstRes plus otherRes) -> "Expression"

    val node = Node(myId, NodeAttributes(label = Plain(label)))

    val edges = childrenRes.newElements.map { case (_, elId) => Edge(myId, elId) }
    VizResult(
      newElements = List(node -> myId),
      acc = childrenRes.accumulate ++ edges,
    ) -> otherId
  }
}

object visualizerIdentifier extends G5GraphvizVisualizer[NonTerminal.Identifier] {
  override def getElement(nt: NonTerminal.Identifier, lastId: String): (VizResult, String) = {
    val myId = lastId.inc

    val declId = myId.inc
    val declNode = Node(declId, NodeAttributes(label = Plain(nt.decl.repr)))

    val (varRes, varId) = visualizerVariable.getElement(nt.variable, declId)
    val (childrenRes, label) = varRes.copy(newElements = (declNode -> declId) +: varRes.newElements) -> "Identifier"

    val node = Node(myId, NodeAttributes(label = Plain(label)))

    val edges = childrenRes.newElements.map { case (_, elId) => Edge(myId, elId) }
    VizResult(
      newElements = List(node -> myId),
      acc = childrenRes.accumulate ++ edges,
    ) -> varId
  }
}

object visualizerOperator extends G5GraphvizVisualizer[NonTerminal.Operator] {
  override def getElement(nt: NonTerminal.Operator, lastId: String): (VizResult, String) = {
    val myId = lastId.inc

    val (childrenRes, childrenResId) = visualizeTwo(visualizerIdentifier, visualizerExpression)(nt.identifier -> nt.expr, myId)

    val node = Node(myId, NodeAttributes(label = Plain("Operator")))

    val edges = childrenRes.newElements.map { case (_, elId) => Edge(myId, elId) }
    VizResult(
      newElements = List(node -> myId),
      acc = childrenRes.accumulate ++ edges,
    ) -> childrenResId
  }
}

object visualizerOperatorList extends G5GraphvizVisualizer[NonTerminal.OperatorList] {
  @tailrec
  private def collectTail(tail: NonTerminal.OperatorTail, res: List[NonTerminal.Operator]): List[NonTerminal.Operator] =
    tail match {
      case OperatorTail.Nil => res
      case OperatorTail.ListCell(car, cdr) => collectTail(cdr, res :+ car)
    }

  override def getElement(nt: NonTerminal.OperatorList, lastId: String): (VizResult, String) = {
    val myId = lastId.inc

    val operators = nt.operator +: collectTail(nt.operatorTail, List())
    val (childrenRes, childrenResId) = foldVis(operators, myId) { visualizerOperator.getElement }
    val node = Node(myId, NodeAttributes(label = Plain("OperatorList")))

    val edges = childrenRes.newElements.map { case (_, elId) => Edge(myId, elId) }
    VizResult(
      newElements = List(node -> myId),
      acc = childrenRes.accumulate ++ edges,
    ) -> childrenResId
  }
}

object visualizerBlock extends G5GraphvizVisualizer[NonTerminal.Block] {
  override def getElement(nt: NonTerminal.Block, lastId: String): (VizResult, String) = {
    val myId = lastId.inc
    val node = Node(myId, NodeAttributes(label = Plain("Block")))

    val (opListRes, opListId) = visualizerOperatorList.getElement(nt.operatorList, myId)

    val edges = opListRes.newElements.map { case (_, elId) => Edge(myId, elId) }
    VizResult(
      newElements = List(node -> myId),
      acc = opListRes.accumulate ++ edges,
    ) -> opListId
  }
}

object visualizerProgram extends G5GraphvizVisualizer[NonTerminal.Program] {
  override def getElement(nt: NonTerminal.Program, lastId: String): (VizResult, String) = {
    val myId = lastId.inc
    val node = Node(myId, NodeAttributes(label = Plain("Program")))

    val (opListRes, opListId) = visualizerBlock.getElement(nt.block, myId)

    val edges = opListRes.newElements.map { case (_, elId) => Edge(myId, elId) }
    VizResult(
      newElements = List(node -> myId),
      acc = opListRes.accumulate ++ edges,
    ) -> opListId
  }
}
