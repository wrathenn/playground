package com.wrathenn.compilers
package models

class Grammar(
  val name: String,
  val epsilon: Option[Grammar.ID],
  val terminals: List[Grammar.Terminal],
  val nonTerminals: List[Grammar.NonTerminal],
  val productions: List[Grammar.Production],
  val start: Grammar.ID,
) {
  val terminalsMap: Map[Grammar.ID, Grammar.Terminal] =
    terminals.view.map { t => t.id -> t }.toMap
  val nonTerminalsMap: Map[Grammar.ID, Grammar.NonTerminal] =
    nonTerminals.view.map { nt => nt.id -> nt }.toMap
  val productionsLeftMap: Map[Grammar.ID, List[Grammar.Production]] =
    productions.groupBy(_.left.id)
}

object Grammar {
  type ID = String

  sealed trait Element {
    def id: ID
  }

  case class Terminal(
    id: ID,
    spell: String,
  ) extends Element

  case class NonTerminal(
    id: ID,
  ) extends Element

  case class Production(
    left: NonTerminal,
    right: List[Element]
  )
}
