package com.wrathenn.compilers
package translators.expr.kinds

import context.TranslationContext
import models.{CodeTarget, ReturnedValue, Type}
import translators.Translator
import translators.expr.ExprTranslator
import translators.variables.ExprPatVarDefTranslator

import scala.jdk.CollectionConverters.CollectionHasAsScala

class ExprBlockStatTranslator(val target: CodeTarget) extends Translator[TinyScalaParser.ExprBlockStatContext, ReturnedValue] {

  override def translate(context: TranslationContext, node: TinyScalaParser.ExprBlockStatContext): ReturnedValue = {
    if (node.expr() != null) {
      new ExprTranslator(target).translate(context, node.expr)
    } else {
      new ExprPatVarDefTranslator(target).translate(context, node.patVarDef)
    }
  }
}

class ExprBlockTranslator(val target: CodeTarget) extends Translator[TinyScalaParser.ExprBlockContext, ReturnedValue] {

  override def translate(context: TranslationContext, node: TinyScalaParser.ExprBlockContext): ReturnedValue = {
    val stats = node.exprBlockStat().asScala.toList
    stats.map {
        blockStat => new ExprBlockStatTranslator(target).translate(context, blockStat)
    }.lastOption
      .getOrElse(ReturnedValue(llvmName = "unit_do_not_access", _type = Type.Primitive._Unit))
  }
}
