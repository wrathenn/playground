package com.wrathenn.compilers
package algs

import models.Grammar

private[algs] object IsLanguageEmpty {
  def execute(grammar: Grammar): Boolean = {
    val all = RecCollectIds.grammarCollectLeftWhereAllRightInN(grammar, grammar.terminalsMap.keySet)
    !all.contains(grammar.start)
  }
}
