package com.wrathenn.compilers
package readers.postfix

import models.G5
import models.G5.NonTerminal.{Multiplier, OperatorTail, Primary}
import models.G5.{NonTerminal, Terminal}

import scala.annotation.tailrec

trait ASTWriter [NT <: G5.NonTerminal]{
  def write(nt: NT): String
}

object primaryPostfixWriter extends ASTWriter[NonTerminal.Primary] {
  override def write(nt: NonTerminal.Primary): String = nt match {
    case Primary.NumericLiteral(nonTerminal) => nonTerminal.terminal.repr
    case Primary.Variable(nonTerminal) => nonTerminal.terminal.repr
    case Primary.BracketExpr(expr) => expressionPostfixWriter.write(expr)
  }
}

object multiplierPostfixWriter extends ASTWriter[NonTerminal.Multiplier] {
  override def write(nt: NonTerminal.Multiplier): String = {
    nt match {
      case Multiplier.PrimaryPowers(p, other: List[Primary]) =>
        other.foldLeft(primaryPostfixWriter.write(p)) { case (res, otherP) =>
          s"""$res ${primaryPostfixWriter.write(otherP)} ${Terminal.**.repr}"""
        }
      case Multiplier.Abs(p) => primaryPostfixWriter.write(p) ++ " " ++ Terminal.Abs.repr
      case Multiplier.Not(p) => primaryPostfixWriter.write(p) ++ " " ++ Terminal.Not.repr
    }
  }
}

object summandPostfixWriter extends ASTWriter[NonTerminal.Summand] {
  override def write(nt: NonTerminal.Summand): String = {
    nt.other.foldLeft(multiplierPostfixWriter.write(nt.firstMultiplier)) { case (res, (mOp, otherM)) =>
      s"""$res ${multiplierPostfixWriter.write(otherM)} ${mOp.terminal.repr}"""
    }
  }
}

object simpleExprPostfixWriter extends ASTWriter[NonTerminal.SimpleExpr] {
  override def write(nt: NonTerminal.SimpleExpr): String = {
    val firstSummandRepr = nt.unaryAdditiveOperation match {
      case Some(unaryOp) => s"""0 ${summandPostfixWriter.write(nt.summand)} ${unaryOp.terminal.repr}"""
      case None => summandPostfixWriter.write(nt.summand)
    }
    nt.otherSummands.foldLeft(firstSummandRepr) { case (res, (baOp, summand)) =>
      s"""$res ${summandPostfixWriter.write(summand)} ${baOp.terminal.repr}"""
    }
  }
}

object relationPostfixWriter extends ASTWriter[NonTerminal.Relation] {
  override def write(nt: NonTerminal.Relation): String = {
    val firstSE = simpleExprPostfixWriter write nt.simpleExpr
    nt.otherSimpleExpr match {
      case Some((relOp, se)) => s"""$firstSE ${simpleExprPostfixWriter.write(se)} ${relOp.terminal.repr}"""
      case None => firstSE
    }
  }
}

object expressionPostfixWriter extends ASTWriter[NonTerminal.Expression] {
  override def write(nt: NonTerminal.Expression): String = {
    nt.otherRelations.foldLeft(relationPostfixWriter write nt.relation) { case(res, (lOp, r)) =>
      s"""$res ${relationPostfixWriter.write(r)} ${lOp.terminal.repr}"""
    }
  }
}

object identifierWriter extends ASTWriter[NonTerminal.Identifier] {
  override def write(nt: NonTerminal.Identifier): String = {
    s"${nt.decl.repr} ${nt.variable.terminal.repr}"
  }
}

object operatorWriter extends ASTWriter[NonTerminal.Operator] {
  override def write(nt: NonTerminal.Operator): String = {
    s"""${identifierWriter.write(nt.identifier)} ${Terminal.Assigment.repr} ${expressionPostfixWriter.write(nt.expr)}"""
  }
}

object operatorListWriter extends ASTWriter[NonTerminal.OperatorList] {
  @tailrec
  private def collect(tail: NonTerminal.OperatorTail, res: List[NonTerminal.Operator]): List[NonTerminal.Operator] = {
    tail match {
      case OperatorTail.Nil => res
      case OperatorTail.ListCell(car, cdr) => collect(cdr, res :+ car)
    }
  }

  override def write(nt: NonTerminal.OperatorList): String = {
    val collected = nt.operator +: collect(nt.operatorTail, List())
    collected.map { op =>
      s"""${operatorWriter.write(op)};\n"""
    }.mkString
  }
}

object blockWriter extends ASTWriter[NonTerminal.Block] {
  override def write(nt: NonTerminal.Block): String = {
    s"""{\n${operatorListWriter.write(nt.operatorList)}}"""
  }
}

object programWriter extends ASTWriter[NonTerminal.Program] {
  override def write(nt: NonTerminal.Program): String = blockWriter write nt.block
}
