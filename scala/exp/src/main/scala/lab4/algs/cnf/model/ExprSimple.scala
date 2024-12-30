package com.wrathenn.exp
package lab4.algs.cnf.model

import lab4.model.Expr


private[cnf] sealed trait ExprSimple {
  def |(e2: ExprSimple): ExprSimple.| = ExprSimple.|(this, e2)
  def &(e2: ExprSimple): ExprSimple.& = ExprSimple.&(this, e2)
}

/**
 * Wide range of subclasses for all expressions.
 *
 * These classes correspond to initial view of Expressions and "removeComplexes" step
 * of CNF conversion algorithm (do not use -> and <-> on step after that, as there's no separate Expr type for it).
 */
private[cnf] object ExprSimple {
  case class Const(id: String) extends ExprSimple { override def toString = id }
  case class Predicate(id: String, args: List[Expr.Atom]) extends ExprSimple { override def toString = s"$id(${args.mkString(", ")})" }

  case class ~~(e: ExprSimple) extends ExprSimple { override def toString = s"¬$e" }
  case class |(e1: ExprSimple, e2: ExprSimple) extends ExprSimple { override def toString = s"($e1 ∨ $e2)" }
  case class &(e1: ExprSimple, e2: ExprSimple) extends ExprSimple { override def toString = s"($e1 ∧ $e2)"}
}
