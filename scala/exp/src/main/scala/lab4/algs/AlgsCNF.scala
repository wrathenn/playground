package com.wrathenn.exp
package lab4.algs

import lab4.model.{Disjunct, Expr, ExprCNF}
import lab4.model.ExprCNF._

object AlgsCNF {
  private def removeComplexes(e: Expr): Expr = {
    e match {
      case e: Expr.Term => e
      case Expr.~~(e) => Expr.~~(removeComplexes(e))
      case Expr.|(e1, e2) => removeComplexes(e1) | removeComplexes(e2)
      case Expr.&(e1, e2) => removeComplexes(e1) & removeComplexes(e2)
      case Expr.->(e1, e2) => Expr.~~(removeComplexes(e1)) | removeComplexes(e2)
      case Expr.<->(e1, e2) => removeComplexes(e1 -> e2) & removeComplexes(e2 -> e1)
    }
  }

  private def moveNegations(e: Expr): ExprCNF = moveNegations(e, negate = false)
  private def moveNegations(e: Expr, negate: Boolean): ExprCNF = {
    e match {
      case e: Expr.Term => Term(e.id, isNegated = negate)
      case Expr.~~(Expr.Term(id)) => moveNegations(Expr.Term(id), !negate)
      case Expr.~~(Expr.|(e1, e2)) => moveNegations(e1, !negate) & moveNegations(e2, !negate)
      case Expr.~~(Expr.&(e1, e2)) => moveNegations(e1, !negate) | moveNegations(e2, !negate)
      case Expr.|(e1, e2) => moveNegations(e1, negate) | moveNegations(e2, negate)
      case Expr.&(e1, e2) => moveNegations(e1, negate) & moveNegations(e2, negate)
      case _ => throw new IllegalStateException(s"Expr ${e} shouldn't be present on negation stage")
    }
  }

  private def applyDistribution(e: ExprCNF): List[Disjunct] = {
    e match {
      case e: Term => List(Disjunct.Term(e.id, e.isNegated))
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

object Main {
  import lab4.model.Expr.Term.StringInterpolation

  import com.wrathenn.exp.lab4.model.Expr.~~

  def main(args: Array[String]): Unit = {
    val t = term"X" -> term"Y" & ((~~(term"Y") -> term"Z") -> ~~(term"X"))
    println(t)
    println(AlgsCNF.convertToCnf(t).mkString("  ∧  "))
  }
}
