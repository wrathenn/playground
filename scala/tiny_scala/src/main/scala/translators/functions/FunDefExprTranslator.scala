package com.wrathenn.compilers
package translators.functions

import context.{LocalContext, TranslationContext}
import models.function.FunctionDef
import models.{CodeTarget, Type}
import translators.Translator
import translators.expr.ExprTranslator

import cats.syntax.all._
import com.wrathenn.compilers.util.TypeResolver

class FunDefExprTranslator(
  val functionDef: FunctionDef,
) extends Translator[TinyScalaParser.ExprContext, Unit]{

  override def translate(node: TinyScalaParser.ExprContext)(implicit context: TranslationContext): Unit = {
    val returnsType = TypeResolver.resolveType(functionDef.returns, prevResolvedGenerics = ???)
    val returnsLlvm = returnsType.llvmRepr
    val paramsLlvm = functionDef.params.map { p => s"${p._type.llvmRepr} ${p.llvmNameRepr}"}.mkString(", ")
    context.writeCodeLn(CodeTarget.LOCAL) { s"define $returnsLlvm ${functionDef.llvmName} ($paramsLlvm) {" }
    context.>>()

    val exprResult = context.inLocalContext(defining = LocalContext.Defining.Function(functionDef).some) {
      new ExprTranslator(CodeTarget.LOCAL).translate(node)
    }

    if (exprResult._type == Type._Nothing) {

    }
    else if (exprResult._type == returnsType) {
      context.writeCodeLn(CodeTarget.LOCAL) { s"ret ${exprResult._type.llvmRepr} ${exprResult.llvmName}" }
    }
    else {
      throw new IllegalStateException(s"Type mismatch for function ${functionDef.tinyScalaName}, expected: ${functionDef.returns}, actual: ${exprResult._type}")
    }

    context.<<()
    context.writeCodeLn(CodeTarget.LOCAL) { "}" }
  }
}
