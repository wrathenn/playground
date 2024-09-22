package com.wrathenn.compilers
package translators.expr.kinds

import context.TranslationContext
import models.{CodeTarget, ReturnedValue, Type}
import translators.Translator
import translators.expr.ExprTranslator
import translators.statement.StatementTranslator
import translators.variables.ExprPatVarDefTranslator

class ExprBlockStatTranslator(val target: CodeTarget) extends Translator[TinyScalaParser.ExprBlockStatContext, ReturnedValue] {
  override def translate(context: TranslationContext, node: TinyScalaParser.ExprBlockStatContext): ReturnedValue = {
    if (node.expr != null) {
      new ExprTranslator(target).translate(context, node.expr)
    } else if (node.patVarDef != null) {
      new ExprPatVarDefTranslator(target).translate(context, node.patVarDef)
    } else if (node.statement != null) {
      new StatementTranslator(target).translate(context, node.statement)
      ReturnedValue("unit from expr block stat", Type.Primitive._Unit)
    } else {
      throw new IllegalStateException("Unknow exprBlockStat, check grammar")
    }
  }
}
