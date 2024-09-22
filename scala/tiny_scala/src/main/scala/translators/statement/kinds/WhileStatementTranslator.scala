package com.wrathenn.compilers
package translators.statement.kinds

import context.TranslationContext
import models.CodeTarget
import translators.Translator

class WhileStatementTranslator(target: CodeTarget) extends Translator[TinyScalaParser.StatementContext, Unit] {

  override def translate(context: TranslationContext, node: TinyScalaParser.StatementContext): Unit = {
    val conditionNode = node.expr(0)
    val bodyNode = node.expr(1)


  }
}
