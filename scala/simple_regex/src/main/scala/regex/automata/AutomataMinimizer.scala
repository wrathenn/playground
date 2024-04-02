package com.wrathenn.compilers
package regex.automata

import regex.automata.Automata.StateId
import regex.automata.Ext.AutomataExt

import cats.implicits.catsSyntaxOptionId

import scala.collection.mutable

object AutomataMinimizer {
  private type CMap[K, V] = collection.Map[K, V]
  private type CSet[A] = collection.Set[A]
  private type BackwardEdges = CMap[Automata.StateId, CMap[Char, CSet[Automata.StateId]]]

  // Instead of reverse-transitions table, as i dont know how big is alphabet
  private def backwardEdges(automata: Automata): BackwardEdges = {
    val res = mutable.Map[Automata.StateId, mutable.Map[Char, mutable.Set[Automata.StateId]]]()
      .withDefault(_ => mutable.Map().withDefault(_ => mutable.Set()))

    automata.states.foreach { case (stateId, state) =>
      state.transitions.foreach { case (c, transitionsToId) =>
        res(transitionsToId) = res(transitionsToId) // for defaults to work properly
        res(transitionsToId)(c) = res(transitionsToId)(c) addOne stateId
      }
    }

    res
  }

  private def buildReachable(automata: Automata): CSet[Automata.StateId] = {
    val res = mutable.Set[Automata.StateId]()
    automata.impureMap { state =>
      res addAll state.transitions.map(_._2)
    }
    res
  }

  private def buildTable(count: Int, backwards: BackwardEdges, automata: Automata): List[List[Boolean]] = {
    val queue = mutable.Queue[(Automata.StateId, Automata.StateId)]()
    val res: mutable.ArrayBuffer[mutable.ArrayBuffer[Boolean]] = mutable.ArrayBuffer.fill(count, count)(false)

    for {
      i <- 0 until count
      j <- 0 until count
    } {
      if (!res(i)(j) && (automata.states(i).isTerminal != automata.states(j).isTerminal)) {
        res(i)(j) = true
        res(j)(i) = true
        queue append i -> j
      }
    }

    while (queue.nonEmpty) {
      val (u: StateId, v: StateId) = queue.dequeue()
      backwards(u).foreach { case (c, stateIds) =>
        stateIds.foreach { r =>
          backwards(v)(c).foreach { s =>
            if (!res(r)(s)) {
              res(r)(s) = true
              res(s)(r) = true
              queue.addOne((r, s))
            }
          }
        }
      }
    }

    res.map(_.toList).toList
  }

  def getAplphabet(automata: Automata): CSet[Char] = {
    val res = mutable.Set[Char]()
    automata.impureMap { s =>
      res.addAll(s.transitions.map(_._1))
    }
    res
  }

  private def buildComponents(marked: List[List[Boolean]], reachable: CSet[StateId], automata: Automata): List[Int] = {
    val count = automata.states.size
    val res = mutable.ArrayBuffer.fill[StateId](count)(-1)
    for ((bool, i) <- marked.head.zipWithIndex) {
      if (!bool) res(i) = 0
    }

    var componentsCount = 0
    for {
      i <- 1 until count
    } {
      if (reachable.contains(i) && res(i) == -1) {
        componentsCount += 1
        res(i) = componentsCount
        for (j <- i + 1 until count) {
          if (!marked(i)(j)) {
            res(j) = componentsCount
          }
        }
      }
    }

    res.toList
  }

  // not needed, but described in article
  private def incrementAll(automata: Automata): Automata = {
    val incrementedStates = automata.impureMap { state =>
      Automata.State(state.id + 1, state.name, state.transitions.map { case (c, id) => c -> (id + 1) }, state.isTerminal)
    }.map { s => s.id -> s }.toMap

    new Automata(incrementedStates, automata.beginningId + 1)
  }

  // not needed, but described in article
  private def addZero(automata: Automata): Automata = {
    val alphabet = getAplphabet(automata)
    val allToZero = alphabet.map(_ -> 0).toList

    val zeroTransitions = automata.impureMap { s =>
      Automata.State(
        s.id,
        s.name,
        (allToZero.toMap ++ s.transitions.toMap).toList,
        s.isTerminal

      )
    }.map { s => s.id -> s }.toMap
    val zero = Automata.State(
      0, None, allToZero, isTerminal = false
    )
    new Automata(zeroTransitions + (0 -> zero), automata.beginningId)
  }

  private def buildMinimized(automata: Automata, components: List[Int]): Automata = {
    val equivGroups: Map[Int, List[StateId]] = components.zipWithIndex.tail.groupMap(_._1)(_._2).filter(_._1 != -1)
    val oldIdToNewId = equivGroups.flatMap { case (id, states) => states.map(_ -> id) }

    val resStates = equivGroups.map { case (newId, oldIds) =>
      val oldStates = oldIds.map(automata.states(_))
      val oldTransitions = oldStates.flatMap(_.transitions)

      val newTranstions = oldTransitions.flatMap { case (c, oldStateId) =>
        val newId = oldIdToNewId.get(oldStateId)
        newId.map(c -> _)
      }.distinct
      val newName = oldStates.map(_.name.getOrElse("")).mkString("{", ", ", "}").some
      val newIsTerminal = oldStates.exists(_.isTerminal)

      newId -> Automata.State(newId, newName, newTranstions, newIsTerminal)
    }

    val beginningId = oldIdToNewId(automata.beginningId)

    new Automata(resStates, beginningId)
  }

  def minimize(automata: Automata): Automata = {
    val incremented = incrementAll(automata)
    val withZero = addZero(incremented)
//    val withZero = automata
    val backwards = backwardEdges(withZero)
    val marked = buildTable(withZero.states.size, backwards, withZero)
    val reachable = buildReachable(withZero) + withZero.beginningId
    val components = buildComponents(marked, reachable, withZero)
    buildMinimized(withZero, components)
  }
}

object Test extends App {
  val testStates = List(
    Automata.State(
      id = 0,
      name = "A".some,
      transitions = List(('1', 1), ('0', 7)),
      isTerminal = false,
    ),
    Automata.State(
      id = 1,
      name = "B".some,
      transitions = List(('1', 0), ('0', 7)),
      isTerminal = false,
    ),
    Automata.State(
      id = 2,
      name = "C".some,
      transitions = List(('0', 4), ('1', 5)),
      isTerminal = false,
    ),
    Automata.State(
      id = 3,
      name = "D".some,
      transitions = List(('0', 4), ('1', 5)),
      isTerminal = false,
    ),
    Automata.State(
      id = 4,
      name = "E".some,
      transitions = List(('0', 5), ('1', 6)),
      isTerminal = false,
    ),
    Automata.State(
      id = 5,
      name = "F".some,
      transitions = List(('0', 5), ('1', 5)),
      isTerminal = true,
    ),
    Automata.State(
      id = 6,
      name = "G".some,
      transitions = List(('0', 6), ('1', 5)),
      isTerminal = true,
    ),
    Automata.State(
      id = 7,
      name = "H".some,
      transitions = List(('0', 2), ('1', 2)),
      isTerminal = false,
    ),
  )
  val automata = new Automata(
    states = testStates.map( it => it.id -> it).toMap,
    beginningId = 0,
  )

  val alphabet = AutomataMinimizer.getAplphabet(automata)

  val t = AutomataMinimizer.minimize(automata)
}
