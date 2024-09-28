package com.wrathenn.compilers
package translators.templates

import context.TranslationContext
import models.CodeTarget
import translators.Translator
import translators.expr.ExprTranslator
import translators.statement.StatementTranslator

class TemplateStatTranslator(objectName: String) extends Translator[TinyScalaParser.TemplateStatContext, Unit] {
  override def translate(context: TranslationContext, node: TinyScalaParser.TemplateStatContext): Unit = {
    if (node.def_ != null) {
      new DefTranslator(objectName).translate(context, node.def_)
    }
    else if (node.expr != null) {
      new ExprTranslator(target = CodeTarget.INIT).translate(context, node.expr)
    }
    else if (node.statement != null) {
      new StatementTranslator(target = CodeTarget.INIT).translate(context, node.statement)
    }
  }
}
