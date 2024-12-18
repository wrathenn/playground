package com.wrathenn.exp
package lab4.model

import lab4.model.Disjunct.Predicate

import cats.Order
import cats.data.{NonEmptyList, NonEmptySet}
import cats.syntax.all._

case class Disjunct(
  over: NonEmptyList[Predicate]
) {
  override def toString: String = over.toNonEmptyList.toList.mkString(" ∧ ")
  def |(other: Disjunct): Disjunct = Disjunct(this.over concatNel other.over)
}

/**
 * Subclasses do not include &, as disjuncts should be stored in List.
 * This is an output of CNF conversion algorithm.
 */
object Disjunct {
  sealed trait Term
  case class Variable(id: String) extends Term { override def toString: String = id }
  case class Const(id: String, isNegative: Boolean) extends Term { override def toString: String = if (isNegative) "¬" else "" + id }

  implicit val predicateOrder: Order[Predicate] =
    (x: Predicate, y: Predicate) => x.id.compareTo(y.id)

  case class Predicate(id: String, args: List[Term], isNegative: Boolean) {
    override def toString: String = {
      val negPart = if (isNegative) "¬" else ""
      val argsPart = if (args.isEmpty) "" else args.mkString(", ")
      s"$negPart$id($argsPart)"
    }
    def toDisjunct: Disjunct = Disjunct(over = NonEmptyList.of(this))
  }
}
