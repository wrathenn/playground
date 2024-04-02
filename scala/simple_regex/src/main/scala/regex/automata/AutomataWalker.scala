package com.wrathenn.compilers
package regex.automata

object AutomataWalker {
  private def visit(str: String, automata: Automata, nodeId: Automata.StateId): Boolean = {
    val state = automata.states(nodeId)

    return if (str.nonEmpty) {
      val c = str.head
      val (_, nextStateId) = state.transitions.find { case (cc, _) => cc == c }.getOrElse(return false)
      visit(str.tail, automata, nextStateId)
    } else {
      state.isTerminal
    }
  }

  def matches(str: String, automata: Automata): Boolean = {
    return visit(str, automata, automata.beginningId)
  }
}
