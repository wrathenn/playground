package com.wrathenn.compilers
package converters

import models.Grammar
import models.Grammar.ID
import models.config.Config.GrammarConfig

import cats.implicits._

object ConfigToModel {
  def validateAndConvert(config: GrammarConfig): Either[Exception, Grammar] = {
    val terminals = config.terminals.map { t => t.name -> Grammar.Terminal(t.name, t.spell) }.toMap
    val terminalIds = terminals.keySet

    val nonTerminals = config.nonTerminals.map { nt => nt.name -> Grammar.NonTerminal(nt.name) }.toMap
    val nonTerminalIds = nonTerminals.keySet

    if ((terminalIds & nonTerminalIds).nonEmpty) {
      return (new IllegalStateException(s"Terminals and NonTerminals share same ids: ${terminalIds & nonTerminalIds}")).asLeft
    }

    val all: Map[ID, Grammar.Element] = terminals ++ nonTerminals
    val allIds: Set[Grammar.ID] = terminalIds | nonTerminalIds

    val productions = config.productions.map { p /* why can't case? */ =>
      val left = p.left
      val right = p.right
      if (!nonTerminals.contains(left.name)) {
        return new IllegalStateException(s"Undefined NonTerminal in left side of production $p").asLeft
      }

      val rightNames = right.map(_.name).toSet
      if ((rightNames &~ allIds).nonEmpty) {
        return new IllegalStateException(s"Undefined symbols ${rightNames &~ allIds} in right side of production $p").asLeft
      }

      val leftG = nonTerminals.getOrElse(
        left.name,
        { return new IllegalStateException(s"Element $left not found among nonTerminals").asLeft }
      )
      val rightG = right.map { r =>
        val foundElement = r.`type` match {
          case "term" => terminals.get(r.name)
          case "nonterm" => nonTerminals.get(r.name)
          case _ => None
        }
        foundElement match {
          case Some(value) => value
          case None => return new IllegalStateException(s"Element $r not found in its group").asLeft
        }
      }

      Grammar.Production(
        leftG,
        rightG
      )
    }

    if (!nonTerminals.contains(config.startSymbol.name)) {
      return new IllegalStateException(s"Start symbole ${config.startSymbol.name} not found among nonTerminals").asLeft
    }

    new Grammar(
      name = config.name,
      terminals = terminals.values.toList,
      nonTerminals = nonTerminals.values.toList,
      productions = productions,
      start = config.startSymbol.name,
    ).asRight
  }
}
