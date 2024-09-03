package com.wrathenn.compilers
package translators.expr

import models.ReturnedValue
import translators.{TranslationContext, Translator}

class ExprTranslator(isGlobal: Boolean) extends Translator[TinyScalaParser.ExprContext, ReturnedValue] {

  override def translate(context: TranslationContext, node: TinyScalaParser.ExprContext): ReturnedValue = {
    if (node.infixExpr != null) {
      return new InfixExprTranslator(isGlobal).translate(context, node.infixExpr)
    }

    ???
  }
}
