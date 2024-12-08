package com.wrathenn.exp
package lab4.algs.cnf

import com.wrathenn.exp.lab4.model.Expr
import com.wrathenn.exp.lab4.model.Expr.Term
import com.wrathenn.exp.lab4.model.Expr.Term.Variable

import scala.collection.mutable

/**
 * В нескольких разных Expr могут быть одинаковые имена переменных.
 * В процессе унификации при резолюции распространение значений
 *   должно происходить по всем переменных среди всех дизъюнктов,
 *   поэтому при работе алгоритма все переменнные из разных Expr
 *   должны быть названы по-разному.
 */
class NamePurifier {
  // Название переменной к количеству выражений, в которых она уже была
  private val usedNames: mutable.Map[Variable, Int] = mutable.Map[Variable, Int]().withDefault(_ => 0)

  private def addToContext(variable: Variable)(implicit context: mutable.Map[Variable, Variable]): Variable = {
    val usedCount = usedNames(variable)
    val newName = s"${variable.id}_$usedCount"
    val res = Variable(newName)
    context.addOne(variable -> res)
    res
  }

  private def getOrAdd(variable: Variable)(implicit context: mutable.Map[Variable, Variable]): Variable =
    context.getOrElse(variable, addToContext(variable))

  private def purifyWithContext(expr: Expr)(implicit context: mutable.Map[Variable, Variable]): Expr = {
    expr match {
      case e: Expr.Const => e
      case Expr.Predicate(id, args) => {
        val newArgs = args.map {
          case a: Variable => getOrAdd(a)
          case a: Term.Const => a
        }
        Expr.Predicate(id, newArgs)
      }
      case Expr.~~(e) => Expr.~~(purifyWithContext(e))
      case Expr.|(e1, e2) => Expr.|(purifyWithContext(e1), purifyWithContext(e2))
      case Expr.&(e1, e2) => Expr.&(purifyWithContext(e1), purifyWithContext(e2))
      case Expr.->(e1, e2) => Expr.->(purifyWithContext(e1), purifyWithContext(e2))
      case Expr.<->(e1, e2) => Expr.<->(purifyWithContext(e1), purifyWithContext(e2))
    }
  }

  def purify(expr: Expr): Expr = {
    implicit val context: mutable.Map[Variable, Variable] = mutable.Map()
    purifyWithContext(expr)
  }
}
