package com.wrathenn.compilers
package models

object G5 {
  sealed trait Element

  sealed class Terminal(val repr: String) extends Element
  object Terminal {
    sealed trait LogicalOperational
    case object And extends Terminal("and") with LogicalOperational
    case object Or extends Terminal("or") with LogicalOperational
    case object Xor extends Terminal("xor") with LogicalOperational

    sealed trait RelationOperational
    case object < extends Terminal("<") with RelationOperational
    case object <= extends Terminal("<=") with RelationOperational
    case object == extends Terminal("==") with RelationOperational
    case object /> extends Terminal("/>") with RelationOperational
    case object > extends Terminal(">") with RelationOperational
    case object >= extends Terminal(">=") with RelationOperational

    sealed trait BinaryAdditiveOperational
    sealed trait UnaryAdditiveOperational
    case object + extends Terminal("+") with BinaryAdditiveOperational with UnaryAdditiveOperational
    case object - extends Terminal("-") with BinaryAdditiveOperational with UnaryAdditiveOperational
    case object & extends Terminal("&") with BinaryAdditiveOperational

    sealed trait MultiplicativeOperational
    case object * extends Terminal("*") with MultiplicativeOperational
    case object / extends Terminal("/") with MultiplicativeOperational
    case object Mod extends Terminal("mod") with MultiplicativeOperational
    case object Rem extends Terminal("rem") with MultiplicativeOperational

    sealed trait HigherPriorityOperational
    case object ** extends Terminal("**") with HigherPriorityOperational
    case object Abs extends Terminal("abs") with HigherPriorityOperational
    case object Not extends Terminal("not") with HigherPriorityOperational

    case class NumericLiteral(override val repr: String) extends Terminal(repr)
    case class Variable(override val repr: String) extends Terminal(repr)
  }

  trait FirstPos { def first(c: Char): Boolean }
  sealed trait NonTerminal extends Element
  object NonTerminal {
    case class MultiplicativeOperation(
      terminal: Terminal with Terminal.MultiplicativeOperational,
    ) extends NonTerminal
    object MultiplicativeOperation extends FirstPos {
      def first(c: Char): Boolean = List(Terminal.*, Terminal./, Terminal.Mod, Terminal.Rem)
        .map(_.repr.head).contains(c)
    }

    case class UnaryAdditiveOperation(
      terminal: Terminal with Terminal.UnaryAdditiveOperational,
    ) extends NonTerminal
    object UnaryAdditiveOperation extends FirstPos {
      def first(c: Char): Boolean = List(Terminal.+, Terminal.-)
        .map(_.repr.head).contains(c)
    }

    case class BinaryAdditiveOperation(
      terminal: Terminal with Terminal.BinaryAdditiveOperational,
    ) extends NonTerminal
    object BinaryAdditiveOperation extends FirstPos {
      def first(c: Char): Boolean = List(Terminal.+, Terminal.-, Terminal.&)
        .map(_.repr.head).contains(c)
    }

    case class RelationOperation(
      terminal: Terminal with Terminal.RelationOperational,
    ) extends NonTerminal
    object RelationOperation extends FirstPos {
      def first(c: Char): Boolean = List(
        Terminal.<, Terminal.<=, Terminal.==,
        Terminal./>, Terminal.>, Terminal.>=
      ).map(_.repr.head).contains(c)
    }

    case class LogicalOperation(
      terminal: Terminal with Terminal.LogicalOperational,
    ) extends NonTerminal
    object LogicalOperation extends FirstPos {
      def first(c: Char): Boolean = List(Terminal.And, Terminal.Or, Terminal.Xor)
        .map(_.repr.head).contains(c)
    }

    case class NumericLiteral(
      terminal: Terminal.NumericLiteral,
    ) extends NonTerminal
    object NumericLiteral extends FirstPos {
      def first(c: Char): Boolean = c.isDigit || c == '-' || c == '+'
    }

    case class Variable(
      terminal: Terminal.Variable,
    ) extends NonTerminal
    object Variable extends FirstPos {
      def first(c: Char): Boolean = c.isLetterOrDigit
    }

    sealed trait Primary extends NonTerminal
    object Primary extends FirstPos {
      case class NumericLiteral(
        nonTerminal: NonTerminal.NumericLiteral
      ) extends Primary
      case class Variable(
        nonTerminal: NonTerminal.Variable,
      ) extends Primary
      case class BracketExpr(
        expr: Expression,
      ) extends Primary

      def first(c: Char): Boolean =
        NonTerminal.NumericLiteral.first(c) ||
        NonTerminal.Variable.first(c) ||
        List("(", ")").contains(c)
    }

    sealed trait Multiplier extends NonTerminal
    object Multiplier extends FirstPos {
      case class Powers(p: Primary, other: List[Primary]) extends Multiplier
      case class Abs(p: Primary) extends Multiplier
      case class Not(p: Primary) extends Multiplier

      def first(c: Char): Boolean =
        List(Terminal.Not, Terminal.Abs).map(_.repr.head).contains(c) ||
        NonTerminal.Primary.first(c)
    }

    case class Summand(
      firstMultiplier: Multiplier,
      other: List[(MultiplicativeOperation, Multiplier)],
    ) extends NonTerminal
    object Summand extends FirstPos {
      def first(c: Char): Boolean =
        NonTerminal.Multiplier.first(c)
    }

    case class SimpleExpr(
      unaryAdditiveOperation: Option[UnaryAdditiveOperation],
      summand: Summand,
      otherSummands: List[(BinaryAdditiveOperation, Summand)],
    ) extends NonTerminal
    object SimpleExpr extends FirstPos {
      def first(c: Char): Boolean =
        NonTerminal.UnaryAdditiveOperation.first(c) ||
        NonTerminal.Summand.first(c)
    }

    case class Relation(
      simpleExpr: SimpleExpr,
      otherSimpleExpr: Option[(RelationOperation, SimpleExpr)],
    ) extends NonTerminal
    object Relation extends FirstPos {
      def first(c: Char): Boolean =
        NonTerminal.SimpleExpr.first(c)
    }

    case class Expression(
      relation: Relation,
      otherExpressions: List[(LogicalOperation, Relation)],
    ) extends NonTerminal
    object Expression extends FirstPos {
      def first(c: Char): Boolean =
        NonTerminal.Relation.first(c)
    }
  }
}
