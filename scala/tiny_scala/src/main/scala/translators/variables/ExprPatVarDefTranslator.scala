package com.wrathenn.compilers
package translators.variables

import context.TranslationContext
import models.{CodeTarget, ReturnedValue, Type, VariableDef}
import translators.Translator
import translators.expr.ExprTranslator

class ExprPatVarDefTranslator(val target: CodeTarget) extends Translator[TinyScalaParser.PatVarDefContext, ReturnedValue] {
  override def translate(context: TranslationContext, node: TinyScalaParser.PatVarDefContext): ReturnedValue = {
    val incomplete = PatVarDefTranslatorHelper.getVariableDef(node)

    if (context.localContext.variables.contains(incomplete.name))
      throw new IllegalStateException(s"Redeclaration of ${incomplete.name}")

    val tinyScalaRepr = incomplete.name

    val variableDef = VariableDef(tinyScalaRepr, s"%$tinyScalaRepr", incomplete._type, incomplete.decl)
    context.localContext.variables.addOne(variableDef.tinyScalaRepr -> variableDef)
    context.writeCodeLn(target) { s"${variableDef.llvmNameRepr} = alloca ${variableDef._type.llvmRepr}" }

    val expr = node.patDef.expr
    val exprResult = new ExprTranslator(target).translate(context, expr)

    if (exprResult._type != variableDef._type) {
      throw new IllegalStateException(s"Type mismatch in variable declaration, expected: ${variableDef._type}, actual: ${exprResult._type}")
    }

    context.writeCodeLn(target) { s"store ${variableDef._type} ${exprResult._type}, ptr ${variableDef.llvmNameRepr}" }
    ReturnedValue(llvmName = "unit_value_unused", _type = Type.Primitive._Unit)
  }
}
