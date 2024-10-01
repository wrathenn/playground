package com.wrathenn.compilers
package translators.templates

import context.TranslationContext
import models.CodeTarget
import translators.Translator
import translators.expr.ExprTranslator
import translators.statement.StatementTranslator

class TemplateStatTranslator(target: CodeTarget) extends Translator[TinyScalaParser.TemplateStatContext, Unit] {
  override def translate(node: TinyScalaParser.TemplateStatContext)(implicit context: TranslationContext): Unit = {
    if (node.def_ != null) {
      DefTranslator.translate(node.def_)
    }
    else if (node.expr != null) {
      new ExprTranslator(target = target).translate(node.expr)
    }
    else if (node.statement != null) {
      new StatementTranslator(target = target).translate(node.statement)
    }
  }
}
