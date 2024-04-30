package com.wrathenn.compilers
package algs

import models.Grammar

import scala.annotation.tailrec

private[algs] object DestroyUnreachableSymbols {
  def execute(grammar: Grammar): Grammar = {
    @tailrec
    def _rec(
      `V_i-1`: Set[Grammar.ID],
      // just an optimization to not calculate all previous things again
      `V_i-1_added`: Set[Grammar.ID],
    ): Set[Grammar.ID] = {
      println("aaa  " + `V_i-1`.mkString(", "))
      val `V_i_added` = `V_i-1_added`.view.flatMap { prevA =>
          grammar.productionsLeftMap.get(prevA) // find production with this as left
        }.flatten
        .flatMap { p =>
          p.right.map(_.id) // get all elements from production's right part
        }.toSet

      val `V_i` = `V_i-1` | `V_i_added`

      if (`V_i-1` == `V_i`) {
        `V_i`
      } else {
        _rec(`V_i`, `V_i_added`)
      }
    }

    val v = _rec(Set(grammar.start), Set(grammar.start))

    new Grammar(
      name = grammar.name + "[no-unreachable]",
      epsilon = grammar.epsilon,
      terminals = grammar.terminals.filter { t => v.contains(t.id) },
      nonTerminals = grammar.nonTerminals.filter { nt => v.contains(nt.id) },
      productions = grammar.productions.filter { p =>
        v.contains(p.left.id) && p.right.forall { el => v.contains(el.id) }
      },
      start = grammar.start,
    )
  }
}
