package com.wrathenn.compilers
package translators.expr

import models.{CodeTarget, ReturnedValue}
import translators.Translator

import com.wrathenn.compilers.context.TranslationContext

class ExprTranslator(target: CodeTarget) extends Translator[TinyScalaParser.ExprContext, ReturnedValue] {

  override def translate(context: TranslationContext, node: TinyScalaParser.ExprContext): ReturnedValue = {
    if (node.infixExpr != null) {
      return new InfixExprTranslator(target).translate(context, node.infixExpr)
    }

    ???
  }
}
