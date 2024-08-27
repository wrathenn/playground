package com.wrathenn.compilers
package translators

import models.{StructDef, VariableDef}

import scala.collection.mutable

class TranslationContext(
  val structDefinitions: mutable.Map[String, StructDef],
  val globalVariables: mutable.Map[String, VariableDef],
)

object TranslationContext {
  def apply() = new TranslationContext(
    structDefinitions = mutable.HashMap(),
    globalVariables = mutable.HashMap(),
  )
}
