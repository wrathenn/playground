package com.wrathenn.compilers
package regex.dfa

import scala.collection.mutable

object DFA {
  sealed trait State {
    def positions: Set[Int]
    // just for debug
    override def toString: String = this match {
      case FinalState => s"DFAState(FINAL)"
      case state: SomeState => s"DFAState(DFAState(p=${state.positions.mkString("")}, ts=[${state.transitions.values.map {
        case FinalState => "FINAL"
        case state: SomeState => state.positions.mkString("")
      }.mkString(",")}]"
    }
  }

  object State {
    def apply(positions: Set[Int]): State = {
      if (positions.isEmpty) FinalState else new SomeState(positions)
    }
  }

  object FinalState extends State {
    override val positions: Set[Int] = Set()
  }

  class SomeState private[DFA](
    override val positions: Set[Int],
    val transitions: mutable.Map[Char, State] = mutable.Map()
  ) extends State
}
