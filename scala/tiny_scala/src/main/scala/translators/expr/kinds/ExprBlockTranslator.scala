package com.wrathenn.compilers
package translators.expr.kinds

import context.TranslationContext
import models.{CodeTarget, ReturnedValue}
import translators.Translator
import translators.expr.ExprTranslator
import translators.variables.ExprPatVarDefTranslator
import com.wrathenn.compilers.models.`type`.Type

import scala.jdk.CollectionConverters.CollectionHasAsScala

class ExprBlockTranslator(val target: CodeTarget) extends Translator[TinyScalaParser.ExprBlockContext, ReturnedValue] {

  override def translate(node: TinyScalaParser.ExprBlockContext)(implicit context: TranslationContext): ReturnedValue = {
    val stats = node.exprBlockStat().asScala.toList
    stats.map {
        blockStat => new ExprBlockStatTranslator(target).translate(blockStat)
    }.lastOption
      .getOrElse(ReturnedValue(llvmName = "unit_do_not_access", _type = Type.Primitive._Unit))
  }
}
