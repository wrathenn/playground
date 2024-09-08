package com.wrathenn.compilers
package translators.expr.kinds

import context.TranslationContext
import models.{CodeTarget, ReturnedValue, Type}
import translators.Translator
import translators.expr.ExprTranslator

import cats.syntax.all._

class IfExprTranslator(target: CodeTarget) extends Translator[TinyScalaParser.ExprContext, ReturnedValue] {

  override def translate(context: TranslationContext, node: TinyScalaParser.ExprContext): ReturnedValue = {
    val conditionExpr = node.expr(0)
    val thenExpr = node.expr(1)
    val elseExpr = if (node.expr(2) != null) node.expr(2).some else None

    val conditionResult = new ExprTranslator(target).translate(context, conditionExpr)
    if (conditionResult._type.isEmpty || conditionResult._type.get != Type.Primitive._Boolean) {
      throw new IllegalStateException("Condition type should be boolean")
    }

    ???
  }
}
