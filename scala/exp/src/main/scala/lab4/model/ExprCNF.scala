package com.wrathenn.exp
package lab4.model

sealed trait ExprCNF {
  def |(e2: ExprCNF): ExprCNF.| = ExprCNF.|(this, e2)
  def &(e2: ExprCNF): ExprCNF.& = ExprCNF.&(this, e2)
}

/**
 * Subclasses do not include ~~ as separate expression, as it can only be applied to Atom now
 * after negation step of CNF conversion algorithm.
 *
 * So these classes correspond to "moveNegations" step.
 */
object ExprCNF {
  case class Term(id: String, isNegated: Boolean) extends ExprCNF { override def toString = s"${ if (isNegated) "¬" else "" }$id" }
  case class |(e1: ExprCNF, e2: ExprCNF) extends ExprCNF { override def toString = s"($e1 ∨ $e2)" }
  case class &(e1: ExprCNF, e2: ExprCNF) extends ExprCNF { override def toString = s"($e1 ∧ $e2)" }
}
