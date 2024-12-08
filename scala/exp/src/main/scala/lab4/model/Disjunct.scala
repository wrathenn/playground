package com.wrathenn.exp
package lab4.model

sealed trait Disjunct {
  def |(e2: Disjunct): Disjunct.| = (this -> e2) match {
    case (e1: Disjunct.Atom, e2: Disjunct.Atom) => Disjunct.|(Set(e1, e2))
    case (e1: Disjunct.Atom, e2: Disjunct.|) => e2.copy(over = e2.over + e1)
    case (e1: Disjunct.|, e2: Disjunct.Atom) => e1.copy(over = e1.over + e2)
    case (e1: Disjunct.|, e2: Disjunct.|) => Disjunct.|(e1.over ++ e2.over)
  }
}

/**
 * Subclasses do not include &, as disjuncts should be stored in List.
 * This is an output of CNF conversion algorithm.
 */
object Disjunct {
  sealed class Atom(val isNegative: Boolean) extends Disjunct

  sealed trait Term
  case class Variable(id: String) extends Term

  case class Const(id: String, override val isNegative: Boolean) extends Atom(isNegative) with Term
  case class Predicate(id: String, args: List[Term], override val isNegative: Boolean) extends Atom(isNegative)

  case class |(over: Set[Atom]) extends Disjunct {
    override def toString: String = over.mkString("∨")
  }
}
