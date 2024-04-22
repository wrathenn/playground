package com.wrathenn.compilers
package algs

import models.Grammar

import cats.Id
import cats.effect.{IO, Ref}
import com.wrathenn.compilers.algs.RecCollectIds.RecCollectIdsSyntax
import com.wrathenn.compilers.models.Grammar.Production

import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object DestroyEpsRules {
  implicit class DestroyEpsRulesSyntax(val grammar: Grammar) extends AnyVal {
    private def applyMask(
      ruleRightSrc: List[Grammar.Element],
      nonTerms: List[(Grammar.Element, Int)],
      nonTermsMask: List[Boolean]
    ): List[Grammar.Element] = {
      val wholeMask = ListBuffer.fill(ruleRightSrc.size)(true)
      nonTerms.zip(nonTermsMask).foreach { case ((_, i), isActive) =>
        wholeMask(i) = isActive
      }
      ruleRightSrc.zip(wholeMask).filter(_._2).map(_._1)
    }

    private def incrementMask(mask: List[Boolean]): List[Boolean] = {
      def _rec(_mask: List[Boolean]): (List[Boolean], Boolean) = {
        if (_mask.size == 1) {
          return List(!_mask.head) -> _mask.head
        }
        val (tail, shouldIncrement) = _rec(_mask.tail)

        if (shouldIncrement) {
          return (!_mask.head +: tail) -> _mask.head
        } else {
          return (_mask.head +: tail) -> false
        }
      }
      _rec(mask)._1
    }

    private def getAllCombinations(
      production: Grammar.Production,
      allEpsNonTerms: Set[Grammar.ID]
    ): List[Grammar.Production] = {
      val productionEpsNonTerms: List[(Grammar.Element, Int)] = production.right
        .zipWithIndex
        .filter { case (tnt, _) => allEpsNonTerms.contains(tnt.id) }
      // Все элементы не ведут в eps -- оставляем правило как оно есть
      if (productionEpsNonTerms.isEmpty)
        return List(production)

      var nonTermsMask = List.fill(productionEpsNonTerms.size)(false)
      // Если все элементы в правой части -- нетерминалы, ведущие в eps, то проигнорить случай, когда все они отсутствуют
      if (production.right.size == productionEpsNonTerms.size) {
        nonTermsMask = nonTermsMask.dropRight(0).appended(true)
      }

      val resProductions = ListBuffer[Grammar.Production]()
      // hate on do-whiles
      @tailrec
      def _collectResWithIncrementMask(): Unit = {
        val newRule = Grammar.Production(
          left = production.left,
          right = applyMask(production.right, productionEpsNonTerms, nonTermsMask)
        )
        resProductions.addOne(newRule)
        if (nonTermsMask.forall { _ })
          return
        nonTermsMask = incrementMask(nonTermsMask)
        _collectResWithIncrementMask()
      }
      _collectResWithIncrementMask()


      ???
    }

    def destroyEpsRules(): Grammar = {
      val newGrammar = for {
        eps <- grammar.epsilon

        allEpsRulesLeft = grammar.productions.view.filter { p =>
          p.right.forall(_.id == eps)
        }.map(_.left.id).toSet
        allNonTerminalIdsLeadingToEps = grammar.collectLeftWhereAllRightInN(allEpsRulesLeft)

        _ = grammar.productions.map { p =>
          val nonTerminalsLeadingToEps = p.right.filter { tnt => allNonTerminalIdsLeadingToEps.contains(tnt.id) }

        }
        newGrammar: Grammar = ???
      } yield newGrammar

      newGrammar match {
        case Some(value) => value
        case None => grammar
      }
    }
  }
}
