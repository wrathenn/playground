package com.wrathenn.compilers
package translators

import models.StructDef

import scala.collection.mutable

class TranslationContext(
  val structDefinitions: mutable.Map[String, StructDef]
)

object TranslationContext {
  def apply() = new TranslationContext(
    structDefinitions = mutable.HashMap()
  )
}
