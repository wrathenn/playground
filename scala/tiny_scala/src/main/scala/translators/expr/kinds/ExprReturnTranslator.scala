package com.wrathenn.compilers
package translators.expr.kinds

import context.TranslationContext
import models.{CodeTarget, ReturnedValue, Type}
import translators.Translator
import translators.expr.ExprTranslator
import cats.syntax.all._

class ExprReturnTranslator(target: CodeTarget) extends Translator[TinyScalaParser.ExprContext, ReturnedValue] {
  private val dummyReturnValue = ReturnedValue(llvmName = "RETURNED", Type._Unit.some)

  override def translate(context: TranslationContext, node: TinyScalaParser.ExprContext): ReturnedValue = {
    val returnExpr = node.expr(0)
    val definingFunction = context.localContext.definingFunction.getOrElse {
      throw new IllegalStateException("Return statement not in a scope of a function")
    }
    val shouldReturn = definingFunction.returns

    if (returnExpr == null) {
      if (shouldReturn != Type._Unit) {
        throw new IllegalStateException(s"Expected return type is $shouldReturn, actual: Unit")
      }
      context.writeCodeLn(target) { "ret void" }
      return dummyReturnValue
    }

    val returnedValue = new ExprTranslator(target).translate(context, returnExpr)
    if (returnedValue._type.isDefined) {
      if (shouldReturn != returnedValue._type.get) {
        throw new IllegalStateException(s"Expected return type is $shouldReturn, actual: ${returnedValue._type.get}")
      }
    }
    else if (shouldReturn.isInstanceOf[Type.Primitive]) {
      throw new IllegalStateException(s"Expected return type is primitive $shouldReturn, actual is null")
    }

    context.writeCodeLn(target) { s"ret ${shouldReturn.llvmRepr} ${returnedValue.llvmName}" }
    dummyReturnValue
  }
}
