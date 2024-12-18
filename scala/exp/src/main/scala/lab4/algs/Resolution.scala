package com.wrathenn.exp
package lab4.algs

import Unification.{Substitution, Result => UniResult}
import lab4.algs.cnf.{AlgsCNF, NamePurifier}
import lab4.model.Disjunct.{Predicate, Term}
import lab4.model.{Disjunct, Expr}

import cats.data.NonEmptyList
import cats.syntax.all._

object Resolution {

  private sealed trait Result
  private object Result {
    object Success extends Result
    object Failure extends Result
  }

  private def applySubstitutions(predicate: Predicate, subtitutions: List[Substitution[Term]]): Predicate = {
    val newArgs = predicate.args.map {
      case pred@(_: Disjunct.Const) => pred
      case pred@(_: Disjunct.Variable) =>
        subtitutions.foldLeft[Term](pred) { case (acc, subst) =>
          acc match {
            case variable: Disjunct.Variable =>
              if (subst.from == variable) subst.to else acc
            case _: Disjunct.Const => acc
          }
        }
    }
    predicate.copy(args = newArgs)
  }

  private def spreadUnificationResult(disjunct: Disjunct, substitutions: List[Substitution[Term]]): Disjunct = {
    val newOver = disjunct.over.map(applySubstitutions(_, substitutions))
    disjunct.copy(over = newOver)
  }

  private def findResolvent(
    d1: Disjunct, d2: Disjunct,
    p1: Predicate, p2: Predicate,
    substitutions: List[Substitution[Term]],
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

  // Either doesn't carry error-correct meaning here
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

  private def resolution(disjuncts: NonEmptyList[Disjunct]): Boolean = {
    for {
      d1 <- disjuncts.toList
      d2 <- disjuncts.tail

      _ = if (d1 != d2) {
        println(s"Поиск резольвенты для $d1 и $d2:")
        Dbg.indented { resolveDisjuncts(d1, d2) } match {
          case Right(resolvent) => {
            println(s"Резольвента найдена: $resolvent")
            val resolventList = NonEmptyList.of(resolvent)
            val nextDisjuncts = if (disjuncts.tail.isEmpty) resolventList else resolventList ++ disjuncts.tail.tail
            println("Новое множество дизъюнктов:")
            dbgDisjuncts(nextDisjuncts)

            return resolution(nextDisjuncts)
          }
          case Left(Result.Success) => {
            println(s"Выведен пустой дизъюнкт")
            return true
          }
          case Left(Result.Failure) => {}
        }
      }
    } yield {}

    false
  }

  private def dbgDisjuncts(disjuncts: NonEmptyList[Disjunct]): Unit = {
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
    dbgDisjuncts(disjuncts)

    resolution(disjuncts)
  }

}
