package com.wrathenn.compilers
package context

import models.{FunctionDef, VariableDef}
import util.Aliases._

import com.wrathenn.compilers.context.LocalContext.Defining

import scala.collection.mutable

class LocalContext(
  val variables: mutable.Map[TinyScalaName, VariableDef],
  var counter: Int,
  val functions: mutable.Map[TinyScalaName, FunctionDef],
  var defining: Option[LocalContext.Defining],
)

object LocalContext {
  def apply(definingFunction: Option[LocalContext.Defining]) = new LocalContext(
    variables = mutable.Map(),
    counter = 0,
    functions = mutable.Map(),
    defining = definingFunction,
  )

  sealed trait Defining
  object Defining {
    case class Object(val objectName: String) extends Defining
    case class Function(val functionDef: FunctionDef) extends Defining
  }
}
