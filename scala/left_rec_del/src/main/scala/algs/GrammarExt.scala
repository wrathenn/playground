package com.wrathenn.compilers
package algs

import models.Grammar

import cats.Show

object GrammarExt {
  implicit class GrammarExtSyntax(val grammar: Grammar) extends AnyVal {
    def isLanguageEmpty: Boolean = IsLanguageEmpty.execute(grammar)
//    def collectLeftWhereAllRightInN(`N_i-1`: Set[Grammar.ID]): Set[Grammar.ID] = RecCollectIds.grammarCollectLeftWhereAllRightInN(grammar, `N_i-1`)
    def destroyUselessSymbols: Grammar = DestroyUselessSymbols.execute(grammar)
    def destroyUnreachableSymbols: Grammar = DestroyUnreachableSymbols.execute(grammar)
    def destroyEpsRules: Grammar = DestroyEpsRules.execute(grammar)
    def destroyLeftRecursion: Grammar = DestroyLeftRecursion.execute(grammar)
  }

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
