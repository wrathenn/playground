package com.wrathenn.compilers
package translators.expr.kinds

import context.TranslationContext
import models.Type.Primitive._Unit
import models.{CodeTarget, ReturnedValue, Type}
import translators.Translator
import translators.expr.ExprTranslator

import com.wrathenn.compilers.util.TypeResolver

class ExprReturnTranslator(target: CodeTarget) extends Translator[TinyScalaParser.ExprContext, ReturnedValue] {
  private val dummyReturnValue = ReturnedValue(llvmName = "RETURNED", _Unit)

  override def translate(node: TinyScalaParser.ExprContext)(implicit context: TranslationContext): ReturnedValue = {
    val returnExpr = node.expr(0)
    val definingFunction = context.getDefiningFunction.getOrElse {
      throw new IllegalStateException("Return statement not in a scope of a function")
    }.functionDef
    val shouldReturn = TypeResolver.resolveType(definingFunction.returns)

    if (returnExpr == null) {
      if (shouldReturn != _Unit) {
        throw new IllegalStateException(s"Expected return type is $shouldReturn, actual: Unit")
      }
      context.writeCodeLn(target) { "ret void" }
      return dummyReturnValue
    }

    val returnedValue = new ExprTranslator(target).translate(returnExpr)
    if (shouldReturn.isInstanceOf[Type.Primitive] && returnedValue._type.isInstanceOf[Type.Ref]) {
      throw new IllegalStateException(s"TODO: unboxing")
    }
    else if (shouldReturn.isInstanceOf[Type.Ref] && returnedValue._type.isInstanceOf[Type.Primitive]) {
      throw new IllegalStateException(s"TODO: boxing")
    }
    else if (shouldReturn != returnedValue._type) {
      throw new IllegalStateException(s"Type mismatch. Expected: $shouldReturn, actual: ${returnedValue._type}")
    }

    context.writeCodeLn(target) { s"ret ${shouldReturn.llvmRepr} ${returnedValue.llvmName}" }
    ReturnedValue(llvmName = "RETURNS_NOTHING", _type = Type._Nothing)
  }
}
