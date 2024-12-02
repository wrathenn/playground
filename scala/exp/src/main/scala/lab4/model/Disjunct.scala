package com.wrathenn.exp
package lab4.model

sealed trait Disjunct {
  def |(e2: Disjunct): Disjunct.| = (this -> e2) match {
    case (e1: Disjunct.Term, e2: Disjunct.Term) => Disjunct.|(Set(e1, e2))
    case (e1: Disjunct.Term, e2: Disjunct.|) => e2.copy(over = e2.over + e1)
    case (e1: Disjunct.|, e2: Disjunct.Term) => e1.copy(over = e1.over + e2)
    case (e1: Disjunct.|, e2: Disjunct.|) => Disjunct.|(e1.over ++ e2.over)
  }
}

/**
 * Subclasses do not include &, as disjuncts should be stored in List.
 * This is an output of CNF conversion algorithm.
 */
object Disjunct {
  case class Term(id: String, isNegated: Boolean) extends Disjunct { override def toString = s"${ if (isNegated) "¬" else "" }$id" }
  case class |(over: Set[Term]) extends Disjunct {
    override def toString: String = over.mkString("∨")
  }
}
