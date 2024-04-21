package com.wrathenn.compilers
package algs

import models.Grammar

import scala.annotation.tailrec

object IsLanguageEmpty {
  implicit class IsEmptySyntax(val grammar: Grammar) extends AnyVal {
    // Useful means that it can generate some terminal chains
    // На первом шаге найдутся все правила, которые выводят терминалы
    def getUsefulNonTerminals: Set[Grammar.ID] = {
      @tailrec
      def _rec(`N_i-1`: Set[Grammar.ID]): Set[Grammar.ID] = {
        val prevPlusTerminals = grammar.terminalsMap.keySet ++ `N_i-1`
        val matchedProductionsLeft = grammar.productions.filter { p =>
          p.right.forall { rPart => prevPlusTerminals.contains(rPart.id) }
        }.map(_.left.id).toSet
        val `N_i` = matchedProductionsLeft | `N_i-1`
        if (`N_i-1` != `N_i`) {
          _rec(`N_i`)
        } else {
          `N_i-1`
        }
      }

      _rec(Set())
    }

    def isLanguageEmpty: Boolean = {
      val all = getUsefulNonTerminals
      all.contains(grammar.start)
    }
  }
}
