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
 *
 * These classes correspond to initial view of Expressions and "removeComplexes" step
 * of CNF conversion algorithm (do not use -> and <-> on step after that, as there's no separate Expr type for it).
 */
object Expr {
  case class Term(id: String) extends Expr { override def toString = id }
  case class ~~(e: Expr) extends Expr { override def toString = s"¬$e" }
  case class |(e1: Expr, e2: Expr) extends Expr { override def toString = s"($e1 ∨ $e2)" }
  case class &(e1: Expr, e2: Expr) extends Expr { override def toString = s"($e1 ∧ $e2)"}
  case class ->(e1: Expr, e2: Expr) extends Expr { override def toString = s"($e1 -> $e2)" }
  case class <->(e1: Expr, e2: Expr) extends Expr { override def toString = s"($e1 <-> $e2)" }

  object Term {
    implicit class StringInterpolation(val sc: StringContext) extends AnyVal {
      def term(args: Any*): Term = Term(sc.s(args:_*))
    }
  }

  object ~~ {
    def apply(e: Expr): Expr = e match {
      case e: ~~ => e.e
      case _ => new ~~(e)
    }
  }
}
