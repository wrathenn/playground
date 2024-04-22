package com.wrathenn.compilers
package algs

import cats.syntax.all._
import algs.DestroyUnreachableSymbols.DestroyUnreachableSyntax
import algs.IsLanguageEmpty.IsEmptySyntax
import models.Grammar
import models.implicits.grammarShow

import com.wrathenn.compilers.algs.RecCollectIds.RecCollectIdsSyntax

object DestroyUselessSymbols {
  implicit class DestroyUselessSyntax(val grammar: Grammar) extends AnyVal {
    def destroyUselessSymbols: Grammar = {
      val `N_e` = grammar.collectLeftWhereAllRightInN(grammar.terminalsMap.keySet)

      val usefulSymbols = `N_e` | grammar.terminalsMap.keySet

      val `G_1` = new Grammar(
        name = s"${grammar.name}[only-useful-non-terms]",
        epsilon = grammar.epsilon,
        terminals = grammar.terminals,
        nonTerminals = `N_e`.flatMap { ne => grammar.nonTerminalsMap.get(ne) }.toList,
        productions = grammar.productions.filter { p =>
          usefulSymbols.contains(p.left.id) && p.right.forall { el => usefulSymbols.contains(el.id) }
        },
        start = grammar.start,
      )
      `G_1`.destroyUnreachableSymbols
    }
  }
}
