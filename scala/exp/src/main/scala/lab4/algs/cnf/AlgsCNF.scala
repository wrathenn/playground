package com.wrathenn.exp
package lab4.algs.cnf

import lab4.algs.cnf.model.ExprMvNeg._
import lab4.algs.cnf.model.{ExprMvNeg, ExprSimple}
import lab4.model.{Disjunct, Expr}

import com.wrathenn.exp.lab4.model.Expr.Term

object AlgsCNF {
  private def removeComplexes(e: Expr): ExprSimple = {
    e match {
      case e: Expr.Const => ExprSimple.Const(e.id)
      case e: Expr.Predicate => ExprSimple.Predicate(e.id, e.args)
      case Expr.~~(e) => ExprSimple.~~(removeComplexes(e))
      case Expr.|(e1, e2) => removeComplexes(e1) | removeComplexes(e2)
      case Expr.&(e1, e2) => removeComplexes(e1) & removeComplexes(e2)
      case Expr.->(e1, e2) => ExprSimple.~~(removeComplexes(e1)) | removeComplexes(e2)
      case Expr.<->(e1, e2) => removeComplexes(e1 -> e2) & removeComplexes(e2 -> e1)
    }
  }

  private def moveNegations(e: ExprSimple): ExprMvNeg = moveNegations(e, negate = false)
  private def moveNegations(e: ExprSimple, negate: Boolean): ExprMvNeg = {
    e match {
      case e: ExprSimple.Const => ExprMvNeg.Const(e.id, negate)
      case e: ExprSimple.Predicate => ExprMvNeg.Predicate(e.id, e.args, negate)
      case ExprSimple.~~(ExprSimple.|(e1, e2)) => moveNegations(e1, !negate) & moveNegations(e2, !negate)
      case ExprSimple.~~(ExprSimple.&(e1, e2)) => moveNegations(e1, !negate) | moveNegations(e2, !negate)
      case ExprSimple.~~(e) => moveNegations(e, !negate)
      case ExprSimple.|(e1, e2) => moveNegations(e1, negate) | moveNegations(e2, negate)
      case ExprSimple.&(e1, e2) => moveNegations(e1, negate) & moveNegations(e2, negate)
    }
  }

  private def applyDistribution(e: ExprMvNeg): List[Disjunct] = {
    e match {
      case Const(id, isNegative) => List(Disjunct.Const(id, isNegative))
      case Predicate(id, args, isNegative) => {
        val disjArgs = args.map {
          case Term.Const(id, isNegative) => Disjunct.Const(id, isNegative)
          case Term.Variable(id) => Disjunct.Variable(id)
        }
        List(Disjunct.Predicate(id, disjArgs, isNegative))
      }
      case |(e1, &(e21, e22)) => applyDistribution(e1 | e21) ++ applyDistribution(e1 | e22)
      case |(&(e11, e12), e2) => applyDistribution(e11 | e2) ++ applyDistribution(e12 | e2)
      case |(e1, e2) => {
        val e1List = applyDistribution(e1)
        val e2List = applyDistribution(e2)
        e1List.flatMap { e1Disjunct => e2List.map { e2Disjunct => e1Disjunct | e2Disjunct } }
      }
      case &(e1, e2) => applyDistribution(e1) ++ applyDistribution(e2)
    }
  }

  def convertToCnf(e: Expr): List[Disjunct] = {
    val f = removeComplexes _ andThen moveNegations andThen applyDistribution
    f(e)
  }
}

object Test {
  import lab4.model.Expr.Term.StringInterpolation

  import com.wrathenn.exp.lab4.model.Expr.~~

  def main(args: Array[String]): Unit = {
    val t = const"X" -> const"Y" & ((~~(const"Y") -> const"Z") -> ~~(const"X"))
    println(t)
    println(AlgsCNF.convertToCnf(t).mkString("  ∧  "))
  }
}
