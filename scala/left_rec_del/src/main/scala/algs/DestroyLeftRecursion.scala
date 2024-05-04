package com.wrathenn.compilers
package algs

import models.Grammar

import cats.syntax.all._
import com.wrathenn.compilers.algs.GrammarExt.productionShow

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

private[algs] object DestroyLeftRecursion {
  private case class DeletionResult(
    newNonTerminal: Option[Grammar.NonTerminal],
    changedProductions: List[Grammar.Production],
  )

  private def deleteImmediate(
    curNonTerminal: Grammar.NonTerminal,
    productions: List[Grammar.Production],
  ): DeletionResult = {
    val immediateRecursiveProductions = ListBuffer[Grammar.Production]()
    val otherCurProductions = ListBuffer[Grammar.Production]()
    val productionsWithoutCur = ListBuffer[Grammar.Production]()

    productions.foreach { p =>
      val target = if (p.left != curNonTerminal) {
        productionsWithoutCur
      } else if (p.right.nonEmpty && p.right.head == curNonTerminal) {
        immediateRecursiveProductions
      } else {
        otherCurProductions
      }
      target.addOne(p)
    }

    // Если нет непосредственных рекурсий, то этот шаг выполнять нет смысла
    if (immediateRecursiveProductions.isEmpty) {
      return DeletionResult(
        newNonTerminal = None,
        changedProductions = productions,
      )
    }

    val newNonTerminal = Grammar.NonTerminal(
      id = curNonTerminal.id + "`"
    )

    val betaProductions = otherCurProductions ++ otherCurProductions.map { p =>
      Grammar.Production(
        left = p.left, // is curNonTerminal
        right = p.right :+ newNonTerminal
      )
    }
    val alphaSimpleProductions = immediateRecursiveProductions.map { p =>
      Grammar.Production(
        left = newNonTerminal,
        right = p.right.tail,
      )
    }
    val alphaRightRecProductions = alphaSimpleProductions.map { p =>
      Grammar.Production(
        left = p.left, // is curNonTerminal
        right = p.right :+ newNonTerminal
      )
    }

    DeletionResult(
      newNonTerminal = newNonTerminal.some,
      changedProductions = List(
        productionsWithoutCur,
        betaProductions,
        alphaSimpleProductions,
        alphaRightRecProductions
      ).flatten
    )
  }

  private def deleteIndirect(
    curNonTerminal: Grammar.NonTerminal,
    forNonTerminal: Grammar.NonTerminal,
    productions: List[Grammar.Production],
  ): List[Grammar.Production] = {
    val curImmediateForProductions = ListBuffer[Grammar.Production]()
    val forProductions = ListBuffer[Grammar.Production]()
    val otherProductions = ListBuffer[Grammar.Production]()

    productions.foreach { p =>
      val target = if (p.left == forNonTerminal) {
        forProductions
      } else if (p.left == curNonTerminal && p.right.nonEmpty && p.right.head == forNonTerminal) {
        curImmediateForProductions
      } else {
        otherProductions
      }
      target.addOne(p)
    }

    val newProductions = curImmediateForProductions.flatMap { curP =>
      forProductions.map { forP =>
        Grammar.Production(
          left = curP.left, // is curNonTerminal
          right = forP.right ++ curP.right.tail,
        )
      }
    }

    List(
      forProductions,
      otherProductions,
      newProductions,
    ).flatten
  }

  @tailrec
  private def deleteIndirectForAll(
    curNonTerminal: Grammar.NonTerminal,
    forNonTerminals: List[Grammar.NonTerminal],
    productions: List[Grammar.Production]
  ): List[Grammar.Production] = {
    if (forNonTerminals.isEmpty) {
      return productions
    }

    val newProductions = deleteIndirect(curNonTerminal, forNonTerminals.head, productions)
    deleteIndirectForAll(curNonTerminal, forNonTerminals.tail, newProductions)
  }

  private def deleteLeftRecForOne(
    prevNonTerminals: List[Grammar.NonTerminal],
    curNonTerminal: Grammar.NonTerminal,
    productions: List[Grammar.Production]
  ): DeletionResult = {
    val productionsAfterIndirectRecDeletion = deleteIndirectForAll(curNonTerminal, prevNonTerminals, productions)
    println(s"After indirect for ${curNonTerminal.id}:\n\t${productionsAfterIndirectRecDeletion.map(_.show).mkString("\n\t")}")
    val immediateDeletionResult = deleteImmediate(curNonTerminal, productionsAfterIndirectRecDeletion)
    println(s"After immediate for ${curNonTerminal.id} ${
      immediateDeletionResult.newNonTerminal match {
        case Some(value) => s"(added new non-terminal): ${value.id}"
        case None => ""
      }
    }:\n\t${immediateDeletionResult.changedProductions.map(_.show).mkString("\n\t")}")

    immediateDeletionResult
  }

  private case class AllDeletionResult(
    newNonTerminals: List[Grammar.NonTerminal],
    newProductions: List[Grammar.Production],
  )

  @tailrec
  private def deleteLeftRecForAll(
    grammarStartId: Grammar.ID,
    allNonTerminals: List[Grammar.NonTerminal],
    productions: List[Grammar.Production],
    // for tailrec:
    prevNonTerminals: List[Grammar.NonTerminal] = List(),
    addedNonTerminals: List[Grammar.NonTerminal] = List(),
  ): AllDeletionResult = {
    if (allNonTerminals.isEmpty) {
      return AllDeletionResult(newNonTerminals = addedNonTerminals, newProductions = productions)
    }

    val deletedForOne = deleteLeftRecForOne(
      prevNonTerminals,
      allNonTerminals.head,
      productions,
    )

    deleteLeftRecForAll(
      grammarStartId = grammarStartId,
      allNonTerminals = allNonTerminals.tail,
      productions = deletedForOne.changedProductions,
      prevNonTerminals = prevNonTerminals :+ allNonTerminals.head,
      addedNonTerminals = deletedForOne.newNonTerminal match {
        case Some(newNonTerminal) => addedNonTerminals :+ newNonTerminal
        case None => addedNonTerminals
      },
    )
  }

  private def sanitizeEps(startId: Grammar.ID, eps: Option[Grammar.ID], productions: List[Grammar.Production]): List[Grammar.Production] = {
    val epsId = eps.getOrElse(return productions)
    productions.flatMap { p =>
      if (p.right.head.id == epsId && (p.right.size != 1 || p.left.id != startId)) {
        val newRight = p.right.tail
        if (newRight.isEmpty) None
        else p.copy(right = newRight).some
      } else {
        p.some
      }
    }
  }

  def execute(grammar: Grammar): Grammar = {
    val deletionResult = deleteLeftRecForAll(grammar.start, grammar.nonTerminals, grammar.productions)

    val newProductionsSanitized = sanitizeEps(grammar.start, grammar.epsilon, deletionResult.newProductions)
//    val newProductionsSanitized = deletionResult.newProductions
//    DestroyEpsRules.execute(
    DestroyUselessSymbols.execute(
      new Grammar(
        name = s"${grammar.name} [left recursion deleted]",
        epsilon = grammar.epsilon,
        terminals = grammar.terminals,
        nonTerminals = grammar.nonTerminals ++ deletionResult.newNonTerminals,
        productions = newProductionsSanitized,
        start = grammar.start,
      )
    )
//    )
  }
}
