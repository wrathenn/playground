package com.wrathenn.exp
package lab4.algs.cnf.model

import lab4.model.Expr

private[cnf] sealed trait ExprMvNeg {
  def |(e2: ExprMvNeg): ExprMvNeg.| = ExprMvNeg.|(this, e2)
  def &(e2: ExprMvNeg): ExprMvNeg.& = ExprMvNeg.&(this, e2)
}

/**
 * Subclasses do not include ~~ as separate expression, as it can only be applied to Constant now
 * after negation step of CNF conversion algorithm.
 *
 * So these classes correspond to "moveNegations" step.
 */
private[cnf] object ExprMvNeg {
  case class Const(id: String, isNegative: Boolean) extends ExprMvNeg { override def toString = id }
  case class Predicate(id: String, args: List[Expr.Atom], isNegative: Boolean) extends ExprMvNeg { override def toString = s"$id(${args.mkString(", ")})" }

  case class |(e1: ExprMvNeg, e2: ExprMvNeg) extends ExprMvNeg { override def toString = s"($e1 ∨ $e2)" }
  case class &(e1: ExprMvNeg, e2: ExprMvNeg) extends ExprMvNeg { override def toString = s"($e1 ∧ $e2)" }
}
