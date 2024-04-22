package com.wrathenn.compilers
package models

//import models.grammar.GrammarConfig._

import derevo.circe.codec
import derevo.derive

object Config {
  @derive(codec)
  case class Terminal(
    name: String,
    spell: String,
  )

  @derive(codec)
  case class NonTerminal(
    name: String,
  )

  @derive(codec)
  case class Production(
    left: NonTerminal,
    right: List[TermNonTermRef],
  )


  @derive(codec)
  case class TermNonTermRef(
    `type`: String,
    name: String,
  )

  @derive(codec)
  case class GrammarConfig(
    name: String,
    epsilon: Option[String],
    terminals: List[Terminal],
    nonTerminals: List[NonTerminal],
    productions: List[Production],
    startSymbol: NonTerminal,
  )
}
