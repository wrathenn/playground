package com.wrathenn.compilers
package context

import models.{FunctionDef, VariableDef}
import util.Aliases._

import scala.collection.mutable

class LocalContext(
  val variables: mutable.Map[TinyScalaName, VariableDef],
  var counter: Int,
  val functions: mutable.Map[TinyScalaName, FunctionDef],
  var definingFunction: Option[FunctionDef],
)

object LocalContext {
  def apply(definingFunction: Option[FunctionDef]) = new LocalContext(
    variables = mutable.Map(),
    counter = 0,
    functions = mutable.Map(),
    definingFunction = definingFunction,
  )
}
