package com.wrathenn.compilers
package models

import cats.Show

import scala.collection.immutable.SortedSet
import scala.collection.mutable

class Grammar(
  val name: String,
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
//  val productions
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

object implicits {
  implicit val grammarShow: Show[Grammar] = (g: Grammar) => {
    val name = g.name
    val terminals = g.terminals.map(_.spell).mkString(", ")
    val nonTerminals = g.nonTerminals.map(_.id).mkString(", ")

    val productions = g.productions.map { p =>
      val left = g.nonTerminalsMap(p.left.id)
      val rights = p.right.map {
        case Grammar.Terminal(_, spell) => spell
        case Grammar.NonTerminal(id) => id
      }.mkString("")
      s"${left.id}-->$rights"
    }.mkString("\n")

    s"""|Name: $name
        |Terminals: $terminals
        |NonTerminals: $nonTerminals
        |Productions:
        |$productions
        |StartSymbol: ${g.start}
        |""".stripMargin
  }
}
