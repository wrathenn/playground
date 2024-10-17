package com.wrathenn.compilers
package context

import models.function.{FunctionDef, FunctionDefGeneric}
import models.{Type, VariableDef}
import util.Aliases._

import scala.collection.mutable

class LocalContext(
  val variables: mutable.Map[TinyScalaName, VariableDef],
  var counter: Int,
  val functions: mutable.Map[Nothing, FunctionDef], // TODO
  val genericFunctions: mutable.Map[TinyScalaName, FunctionDefGeneric],
  var defining: Option[LocalContext.Defining],
)

object LocalContext {
  def apply(definingFunction: Option[LocalContext.Defining]) = new LocalContext(
    variables = mutable.Map(),
    counter = 0,
    functions = mutable.Map(),
    genericFunctions = mutable.Map(),
    defining = definingFunction,
  )

  sealed trait Defining
  object Defining {
    case class Object(objectName: String) extends Defining
    case class Function(functionDef: FunctionDef) extends Defining
    case class WithConcreteGenerics(genericAliases: Map[TinyScalaName, Type]) extends Defining
  }
}
