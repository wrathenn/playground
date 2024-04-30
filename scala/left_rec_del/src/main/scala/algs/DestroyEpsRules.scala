package com.wrathenn.compilers
package algs

import models.Grammar
import util.Mask.BooleanMask
import util.BooleanMaskExt.BooleanMaskSyntax

import cats.syntax.all._

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

private[algs] object DestroyEpsRules {
  /**
   * Применить маску nonTermsMask к nonTerms,
   * преобразовать правую часть правила ruleRightSrc в соответствии с маской
   *
   * @param ruleRightSrc правая часть изначального правила
   * @param nonTerms     нетерминалы в этом правиле
   * @param nonTermsMask маска для нетерминалов
   * @return новую правую часть правила
   */
  private def applyMask(
    ruleRightSrc: List[Grammar.Element],
    nonTerms: List[(Grammar.Element, Int)],
    nonTermsMask: BooleanMask,
  ): List[Grammar.Element] = {
    val wholeMask = ListBuffer.fill(ruleRightSrc.size)(true)
    nonTerms.zip(nonTermsMask).foreach { case ((_, i), isActive) =>
      wholeMask(i) = isActive
    }
    ruleRightSrc.zip(wholeMask).filter(_._2).map(_._1)
  }

  /**
   * Вывести все правила, которые могут получиться из production
   * с учетом всех возможных комбинаций нетерминалов, ведущих к eps.
   *
   * @param production                    изначальное правило
   * @param allNonTerminalIdsLeadingToEps множество нетерминалов, которые приводят к eps
   * @param epsId                         идентификатор eps
   * @return множество правил, получившихся из production
   */
  private def getAllCombinations(
    production: Grammar.Production,
    allNonTerminalIdsLeadingToEps: Set[Grammar.ID],
    epsId: Grammar.ID,
  ): List[Grammar.Production] = {
    // Если все элементы -- eps, правило не берем
    if (production.right.forall { r => r.id == epsId }) {
      return List()
    }

    val allEpsNonTermsInProduction = production.right.filter {
      tnt => allNonTerminalIdsLeadingToEps.contains(tnt.id)
    }.map(_.id)

    val productionEpsNonTerms: List[(Grammar.Element, Int)] = production.right
      .zipWithIndex
      .filter { case (tnt, _) => allEpsNonTermsInProduction.contains(tnt.id) }
    // Все элементы не ведут в eps -- оставляем правило как оно есть
    if (productionEpsNonTerms.isEmpty)
      return List(production)

    var nonTermsMask: BooleanMask = List.fill(productionEpsNonTerms.size)(false)
    // Если все элементы в правой части -- нетерминалы, ведущие в eps, то проигнорить случай, когда все они отсутствуют
    if (production.right.size == productionEpsNonTerms.size) {
      nonTermsMask = nonTermsMask.dropRight(0).appended(true)
    }

    val resProductions = ListBuffer[Grammar.Production]()

    /**
     * Собирает все правила, пока маска не станет полностью состоять из 1.
     */
    // hate on do-whiles
    @tailrec
    def _collectResWithIncrementMask(): Unit = {
      val newRule = Grammar.Production(
        left = production.left,
        right = applyMask(production.right, productionEpsNonTerms, nonTermsMask)
      )
      resProductions.addOne(newRule)
      if (nonTermsMask.forall { p => p })
        return
      nonTermsMask = nonTermsMask.incremented
      _collectResWithIncrementMask()
    }

    _collectResWithIncrementMask()

    resProductions.toList
  }

  /**
   * Устранить все eps-правила из грамматики grammar
   *
   * @return новая грамматика без eps-правил
   */
  def execute(grammar: Grammar): Grammar = {
    val newGrammar = for {
      eps <- grammar.epsilon

      // нетерминалы из левой части всех правил, у которых правая часть состоит из eps
      allEpsRulesLeft = grammar.productions.view.filter { p =>
        p.right.forall(_.id == eps)
      }.map(_.left.id).toSet
      // все правила, у которых правая часть состоит из нетерминалов выше
      allNonTerminalIdsLeadingToEps = RecCollectIds.grammarCollectLeftWhereAllRightInN(grammar, allEpsRulesLeft)

      // новые правила с устраненными eps
      noEpsProductions = grammar.productions.flatMap { p =>
        getAllCombinations(p, allNonTerminalIdsLeadingToEps, eps)
      }

      // Если изначальная грамматика среди нетерминалов, которые ведут в eps, содержала начальный нетерминал,
      // то необходимо сделать новое начало S'
      (newStart, sRules) = if (allNonTerminalIdsLeadingToEps.contains(grammar.start)) {
        val oldStart = grammar.nonTerminalsMap(grammar.start)
        val newStartId = grammar.start + "'"
        val newStart = Grammar.NonTerminal(newStartId)
        val epsTerm = grammar.terminals.find(_.id == eps).get

        newStart.some ->
          List(
            Grammar.Production(left = newStart, right = List(epsTerm)),
            Grammar.Production(left = newStart, right = List(oldStart)),
          )
      } else {
        None -> List[Grammar.Production]()
      }

      // Строим новую грамматику
      newGrammar: Grammar = new Grammar(
        name = grammar.name + "[removed-eps-rules]",
        epsilon = grammar.epsilon,
        terminals = grammar.terminals,
        nonTerminals = newStart match {
          case Some(value) => grammar.nonTerminals :+ value
          case None => grammar.nonTerminals
        },
        productions = noEpsProductions ++ sRules,
        start = newStart match {
          case Some(value) => value.id
          case None => grammar.start
        }
      )
    } yield newGrammar

    // newGrammar мог быть None, если в грамматике не был определен eps.
    // В таком случае возвращается изначальная грамматика.
    newGrammar match {
      case Some(value) => value
      case None => grammar
    }
  }
}