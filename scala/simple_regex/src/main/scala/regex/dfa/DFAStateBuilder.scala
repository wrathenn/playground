package com.wrathenn.compilers
package regex.dfa

import regex.processors.RegexTreeFCalculator

import cats.implicits.catsSyntaxOptionId

import scala.collection.mutable

object DFAStateBuilder {
  def buildDFA(treeF: RegexTreeF): DFA.State = {
    val start = DFA.State(treeF.firstPos)

    val allStates = mutable.Map[Set[Int], DFA.State](start.positions -> start)
    val unmarkedStates = mutable.ListBuffer[DFA.State](start)

    val leafMap = RegexTreeFCalculator.collectNodes(treeF)
    def processUnmarked: Option[Unit] = for {
      unmarkedState <- unmarkedStates.headOption // will return None if none unmarked
      _ <- unmarkedStates.remove(0).some

      // Group by characters this state has
      transitionMap = unmarkedState.positions
        .groupMap(leafMap(_).symbol)(leafMap(_).followPos) // wont bother checking options
        .view.mapValues(_.flatten).toMap // Union of followPoses

      // For each symbol create link in [transitions] and create new State if neccessary
      _ = transitionMap.map { case (c, poses) =>
        val (newState, exists) = allStates.get(poses) match {
          case Some(state) => state -> true
          case None => DFA.State(poses) -> false
        }
        if (!exists) {
          unmarkedStates.addOne(newState)
          allStates.addOne(newState.positions -> newState)
        }
        unmarkedState match {
          case DFA.FinalState => ??? // Will never happen, as EMPTY doesn't have any [positions]
          case state: DFA.SomeState => state.transitions.addOne(c -> newState)
        }
      }
    } yield ()

    // call it until it returns None, very dirty
    while (processUnmarked.isDefined) {
    }

    start
  }
}
