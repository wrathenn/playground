package com.wrathenn.compilers
package translators.variables

import context.TranslationContext
import models.{CodeTarget, ReturnedValue, VariableDef}
import translators.Translator
import translators.expr.ExprTranslator
import com.wrathenn.compilers.models.`type`.Type

class ExprPatVarDefTranslator(val target: CodeTarget) extends Translator[TinyScalaParser.PatVarDefContext, ReturnedValue] {
  override def translate(node: TinyScalaParser.PatVarDefContext)(implicit context: TranslationContext): ReturnedValue = {
    val incomplete = PatVarDefTranslatorHelper.getVariableDef(node)

    if (context.localContainsVariable(incomplete.name))
      throw new IllegalStateException(s"Redeclaration of ${incomplete.name}")

    val tinyScalaName = incomplete.name

    val variableDef = VariableDef(
      tinyScalaName = tinyScalaName,
      llvmNameRepr =  s"%$tinyScalaName",
      _type = incomplete._type,
      decl = incomplete.decl,
      isFunctionParam = false,
    )
    context.addLocalVariable(variableDef)
    context.writeCodeLn(target) { s"${variableDef.llvmNameRepr} = alloca ${variableDef._type.llvmRepr}" }

    val expr = node.patDef.expr
    val exprResult = new ExprTranslator(target).translate(expr)

    if (exprResult._type != variableDef._type) {
      throw new IllegalStateException(s"Type mismatch in variable declaration, expected: ${variableDef._type}, actual: ${exprResult._type}")
    }

    context.writeCodeLn(target) { s"store ${variableDef._type.llvmRepr} ${exprResult.llvmName}, ptr ${variableDef.llvmNameRepr}" }
    ReturnedValue(llvmName = "unit_value_unused", _type = Type.Primitive._Unit)
  }
}
