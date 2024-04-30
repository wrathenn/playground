package com.wrathenn.compilers
package algs

import models.Grammar

import scala.annotation.tailrec

private[algs] object RecCollectIds {
  @tailrec
  def grammarCollectLeftWhereAllRightInN(grammar: Grammar, `N_i-1`: Set[Grammar.ID]): Set[Grammar.ID] = {
    val matchedProductionsLeft = grammar.productions.filter { p =>
      p.right.forall { rPart => `N_i-1`.contains(rPart.id) }
    }.map(_.left.id).toSet
    val `N_i` = matchedProductionsLeft | `N_i-1`
    if (`N_i-1` != `N_i`) {
      grammarCollectLeftWhereAllRightInN(grammar, `N_i`)
    } else {
      `N_i-1`
    }
  }
}
