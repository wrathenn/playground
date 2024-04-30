package com.wrathenn.compilers
package algs

import models.Grammar

private[algs] object DestroyUselessSymbols {
  def execute(grammar: Grammar): Grammar = {
    val `N_e` = RecCollectIds.grammarCollectLeftWhereAllRightInN(grammar, grammar.terminalsMap.keySet)

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
    DestroyUnreachableSymbols.execute(`G_1`)
  }
}
