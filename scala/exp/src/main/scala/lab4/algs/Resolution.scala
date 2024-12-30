package com.wrathenn.exp
package lab4.algs

import Unification.{Substitution, Result => UniResult}
import lab4.algs.cnf.{AlgsCNF, NamePurifier}
import lab4.model.Disjunct.{Predicate, Atom}
import lab4.model.{Disjunct, Expr}

import cats.data.NonEmptyList
import cats.syntax.all._

import scala.annotation.tailrec

object Resolution {

  private sealed trait Result
  private object Result {
    object Success extends Result
    object Failure extends Result
  }

  private def applySubstitutions(predicate: Predicate, subtitutions: List[Substitution[Atom]]): Predicate = {
    val newArgs = predicate.args.map {
      case pred@(_: Disjunct.Const) => pred
      case pred@(_: Disjunct.Variable) =>
        subtitutions.foldLeft[Atom](pred) { case (acc, subst) =>
          acc match {
            case variable: Disjunct.Variable =>
              if (subst.from == variable) subst.to else acc
            case _: Disjunct.Const => acc
          }
        }
    }
    predicate.copy(args = newArgs)
  }

  private def spreadUnificationResult(disjunct: Disjunct, substitutions: List[Substitution[Atom]]): Disjunct = {
    val newOver = disjunct.over.map(applySubstitutions(_, substitutions))
    disjunct.copy(over = newOver)
  }

  private def findResolvent(
    d1: Disjunct, d2: Disjunct,
    p1: Predicate, p2: Predicate,
    substitutions: List[Substitution[Atom]],
  ): Option[Disjunct] = {
    val d1NoP1 = d1.over.filter(_ != p1)
    val d2NoP2 = d2.over.filter(_ != p2)

    val concatedSrcPreds = NonEmptyList.fromList(d1NoP1 ++ d2NoP2) match {
      case Some(value) => value
      case None => return None
    }
    val concated = Disjunct(over = concatedSrcPreds)

    val spreadedResult = spreadUnificationResult(concated, substitutions)
    spreadedResult.some
  }

  // Either doesn't carry error/correct meaning here
  private def resolveDisjuncts(d1: Disjunct, d2: Disjunct): Either[Result, Disjunct] = {
    for {
      p1 <- d1.over
      p2 <- d2.over

      _ = Dbg.debugLn(s"🔍 Унификация $p1 и $p2")

      notP2 = p2.copy(isNegative = !p2.isNegative)
      unificationResult = Dbg.indented { Unification.unify(p1, notP2) }
      _ = unificationResult match {
        case UniResult.Success(substitutions) => {
          return findResolvent(d1, d2, p1, p2, substitutions).toRight { Result.Success }
        }
        case UniResult.Failure => {}
      }
    } yield {}

    Result.Failure.asLeft
  }

  @tailrec
  private def resolution(
    // Дизъюнкты, для которых ранее уже пытались составить контрарные пары (они были в качестве currentDisjunct)
    processedDisjuncts: List[Disjunct],
    // Дизъюнкт, для которого сейчас составляется контрарная пара
    currentDisjunct: Disjunct,
    // Дизъюнкты, из которых уже пробовали составлять контрарную пару с currentDisjunct
    checkedDisjuncts: List[Disjunct],
    // Дизъюнкты, с которыми еще надо попробовать составить контрарную пару с currentDisjunct
    nextDisjuncts: List[Disjunct],
  ): Boolean = {
    if (nextDisjuncts.isEmpty && checkedDisjuncts.isEmpty) {
      Dbg.debugLn(s"Завершен поиск контрарных пар для последнего дизъюнкта $currentDisjunct")
      return false
    }

    if (nextDisjuncts.isEmpty) {
      Dbg.debugLn(s"Завершен поиск контрарных пар для дизъюнкта $currentDisjunct")
      dbgDisjuncts((processedDisjuncts :+ currentDisjunct) ++ checkedDisjuncts)
      resolution(processedDisjuncts :+ currentDisjunct, checkedDisjuncts.head, List(), checkedDisjuncts.tail)
    } else {

      val nextDisjunct = nextDisjuncts.head
      val resolventResult = Dbg.indented {
        resolveDisjuncts(currentDisjunct, nextDisjunct)
      }
      val addedDisjuncts: List[Disjunct] = resolventResult match {
        case Right(resolvent) => {
          Dbg.debugLn(s"Резольвента найдена: $resolvent, она будет добавлена во множество дизъюнктов")
          if (processedDisjuncts.contains(resolvent) || checkedDisjuncts.contains(resolvent) || nextDisjuncts.contains(resolvent)) {
            Dbg.debugLn(s"Дизъюнкт уже присутствует в множестве.")
            List()
          } else List(resolvent)
        }
        case Left(Result.Success) => {
          println(s"Выведен пустой дизъюнкт")
          return true
        }
        case Left(Result.Failure) => List()
      }

      resolution(processedDisjuncts, currentDisjunct, checkedDisjuncts :+ nextDisjunct, nextDisjuncts.tail ++ addedDisjuncts)
    }
  }

  private def dbgDisjuncts(disjuncts: List[Disjunct]): Unit = {
    disjuncts.toList.zipWithIndex.foreach { case (d, i) =>
      Dbg.debugLn(s"$i) $d")
    }
  }

  def resolve(data: List[Expr], conclusion: Expr): Boolean = {
    Dbg.debugLn("Начало резолюции")
    val notConclusion = Expr.~~(conclusion)
    Dbg.debugLn(s"Отрицание цели: $notConclusion")

    val disjunctNamePurifier = new NamePurifier()
    val disjuncts = (data :+ notConclusion)
      .flatMap { e => AlgsCNF.convertToCnf(e) }
      .map(disjunctNamePurifier.purify)
      .toNel.getOrElse { return false }

    Dbg.debugLn(s"Множество дизъюнктов:")
    dbgDisjuncts(disjuncts.toList)

    resolution(List(), disjuncts.head, List(), disjuncts.tail)
  }

}
