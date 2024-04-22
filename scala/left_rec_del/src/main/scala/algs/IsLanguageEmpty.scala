package com.wrathenn.compilers
package algs

import algs.RecCollectIds.RecCollectIdsSyntax
import models.Grammar

object IsLanguageEmpty {
  implicit class IsEmptySyntax(val grammar: Grammar) extends AnyVal {
    def isLanguageEmpty: Boolean = {
      val all = grammar.collectLeftWhereAllRightInN(grammar.terminalsMap.keySet)
      all.contains(grammar.start)
    }
  }
}
