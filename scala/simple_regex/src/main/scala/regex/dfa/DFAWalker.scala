package com.wrathenn.compilers
package regex.dfa

object DFAWalker {
  def matches(str: String, dfaInitialState: DFA.State): Boolean = {
    dfaInitialState match {
      case DFA.FinalState => ???
      case state: DFA.SomeState =>
        if (str.isEmpty && state.transitions.exists(_._2.isInstanceOf[DFA.FinalState.type])) {
          return true
        } else if (str.isEmpty) {
          return false
        } else {
          return state.transitions.get(str.head) match {
            case Some(nextState) => matches(str.tail, nextState)
            case None => false
          }
        }
    }
  }
}
