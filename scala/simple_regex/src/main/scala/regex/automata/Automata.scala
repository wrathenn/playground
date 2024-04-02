package com.wrathenn.compilers
package regex.automata

import regex.dfa.DFA

import cats.implicits.catsSyntaxOptionId

import scala.collection.mutable

object Automata {
  type StateId = Int

  case class State(
    id: StateId,
    name: Option[String],
    transitions: List[(Char, StateId)],
    isTerminal: Boolean,
  )
}

class Automata(
  val states: Map[Automata.StateId, Automata.State],
  val beginningId: Automata.StateId,
)

object AutomataConverter {
  type DFAStateKey = Set[Int]
  def fromDFA(beginning: DFA.State): Automata = {
    val visitedToTransitionsAndIsTerminal = mutable.Map[DFAStateKey, (List[(Char, DFAStateKey)], Boolean)]()
    val dfaKeyToAutoKey = mutable.Map[DFAStateKey, Automata.StateId]()

    var currentNumber = 0
    def collect(node: DFA.State): Unit = {
      if (visitedToTransitionsAndIsTerminal.contains(node.positions)) return

      node match {
        case DFA.FinalState => {}
        case state: DFA.SomeState => {
          visitedToTransitionsAndIsTerminal.addOne(
            state.positions -> (
            state.transitions.toList.map { case (c, st) => c -> st.positions },
            state.transitions.exists { case (_, st) => st.isInstanceOf[DFA.FinalState.type] }
          ))
          dfaKeyToAutoKey.addOne(node.positions -> currentNumber)
          currentNumber += 1

          state.transitions.foreach { case (_, transitionsTo) => collect(transitionsTo) }
        }
      }
    }
    collect(beginning)

    val states = visitedToTransitionsAndIsTerminal.map { case (dfaKey, (transitions, isTerminal)) =>
      val newTransitions = transitions.filter(_._2.nonEmpty).map { case (c, oldKey) => c -> dfaKeyToAutoKey(oldKey) }
      val id = dfaKeyToAutoKey(dfaKey)
      id -> Automata.State(id = id, name = dfaKey.mkString("").some, transitions = newTransitions, isTerminal = isTerminal)
    }

    val beginnningId = dfaKeyToAutoKey(beginning.positions)

    new Automata(states = states.toMap, beginningId = beginnningId)
  }
}

object Ext {
  implicit class AutomataExt(private val automata: Automata) extends AnyVal {
    def impureMap[T](f: Automata.State => T): Set[T] = {
      val visited = mutable.Set[Automata.StateId]()
      val res = mutable.Set[T]()

      def visit(nodeId: Automata.StateId): Unit = {
        if (visited.contains(nodeId)) return
        visited.add(nodeId)
        val state = automata.states(nodeId)
        res addOne f(state)
        state.transitions.foreach { case (_, id) => visit(id)}
      }
      automata.states.foreach { s => visit(s._1)}

      res.toSet
    }
  }
}
