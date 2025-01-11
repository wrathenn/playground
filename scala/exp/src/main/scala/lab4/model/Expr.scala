package com.wrathenn.exp
package lab4.model


sealed trait Expr {
  def |(e2: Expr): Expr.| = Expr.|(this, e2)
  def &(e2: Expr): Expr.& = Expr.&(this, e2)
  def ->(e2: Expr): Expr.-> = Expr.->(this, e2)
  def <->(e2: Expr): Expr.<-> = Expr.<->(this, e2)
}

/**
 * Wide range of subclasses for all expressions.
 * These classes correspond to initial view of Expressions of CNF conversion algorithm.
 */
object Expr {
  case class Const(id: String) extends Expr { override def toString = id }
  case class Predicate(id: String, args: List[Term]) extends Expr { override def toString = s"$id(${args.mkString(", ")})" }

  case class ~~(e: Expr) extends Expr { override def toString = s"¬$e" }
  case class |(e1: Expr, e2: Expr) extends Expr { override def toString = s"($e1 ∨ $e2)" }
  case class &(e1: Expr, e2: Expr) extends Expr { override def toString = s"($e1 ∧ $e2)"}
  case class ->(e1: Expr, e2: Expr) extends Expr { override def toString = s"($e1 -> $e2)" }
  case class <->(e1: Expr, e2: Expr) extends Expr { override def toString = s"($e1 <-> $e2)" }

  sealed trait Term
  object Term {
    case class Const(id: String, isNegative: Boolean) extends Term
    case class Variable(id: String) extends Term

    implicit class StringInterpolation(val sc: StringContext) extends AnyVal {
      def const(args: Any*): Expr.Const = Expr.Const(sc.s(args:_*))
      def termVar(args: Any*): Term.Variable = Term.Variable(sc.s(args:_*))
      def termC(args: Any*): Term.Const = Term.Const(sc.s(args:_*), isNegative = false)
      def termNotC(args: Any*): Term.Const = Term.Const(sc.s(args:_*), isNegative = true)
    }
  }
}
