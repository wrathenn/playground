package com.wrathenn.exp
package lab4.algs.cnf

import com.wrathenn.exp.lab4.model
import com.wrathenn.exp.lab4.model.Disjunct
import com.wrathenn.exp.lab4.model.Disjunct._

import scala.collection.mutable

/**
 * В нескольких разных Disjunct могут быть одинаковые имена переменных.
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

  private def purifyWithContext(disjunct: Disjunct)(implicit context: mutable.Map[Variable, Variable]): Disjunct = {
    val newPredicates = for {
      predicate <- disjunct.over
      newArgs = predicate.args.map {
        case a: Variable => getOrAdd(a)
        case a: Const => a
      }
    } yield predicate.copy(args = newArgs)

    disjunct.copy(over = newPredicates)
  }

  def purify(expr: Disjunct): Disjunct = {
    implicit val context: mutable.Map[Variable, Variable] = mutable.Map()
    purifyWithContext(expr)
  }
}
