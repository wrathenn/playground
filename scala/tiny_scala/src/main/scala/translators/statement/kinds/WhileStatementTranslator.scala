package com.wrathenn.compilers
package translators.statement.kinds

import context.TranslationContext
import models.CodeTarget
import translators.Translator

class WhileStatementTranslator(target: CodeTarget) extends Translator[TinyScalaParser.StatementContext, Unit] {

  override def translate(node: TinyScalaParser.StatementContext)(implicit context: TranslationContext): Unit = {
    val conditionNode = node.expr(0)
    val bodyNode = node.expr(1)


  }
}
