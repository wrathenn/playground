package com.wrathenn.compilers
package translators.functions

import models.{CodeTarget, FunctionDef, Type, VariableDecl, VariableDef}
import translators.expr.ExprTranslator
import translators.Translator
import util.{TypeResolver, Util}

import cats.syntax.all._
import context.{LocalContext, TranslationContext}

import scala.jdk.CollectionConverters.CollectionHasAsScala

object FunDefTranslator extends Translator[TinyScalaParser.FunDefContext, Unit] {
  private def collectParams(params: List[TinyScalaParser.ParamContext])(
    implicit context: TranslationContext,
  ): List[VariableDef] = {
    params.map { param =>
      val name = param.Id.getText
      val _type = TypeResolver.getTypeFromDefinition(param.typeDefinition)

      VariableDef(
        tinyScalaRepr = name,
        llvmNameRepr = s"%$name" ,
        _type = _type,
        decl = VariableDecl.VAL,
        isFunctionParam = true,
      )
    }
  }

  private def getFunctionDefAndAddToContext(node: TinyScalaParser.FunDefContext)(
    implicit context: TranslationContext,
  ): FunctionDef = {
    val name = node.funSig.Id.getText

    val definingObject = context.getDefiningObjectOrDie
    val objectName = definingObject.objectName
    val tinyScalaGlobalName = s"$objectName.$name"
    val llvmName = s"@$objectName.$name"

    val params = {
      val _params = node.funSig.params
      if (_params == null) List()
      else _params.param().asScala.toList
    }
    val paramsDef = collectParams(params)

    val returnType = TypeResolver.getTypeFromDefinition(node.typeDefinition)

    val globalFunctionDef = FunctionDef(
      tinyScalaName = tinyScalaGlobalName,
      llvmName = llvmName,
      params = paramsDef,
      returns = returnType,
      isVarArg = false,
    )
    context.addGlobalFunction(globalFunctionDef)

    val localFunctionDef = globalFunctionDef.copy(tinyScalaName = name)
    context.addLocalFunction(localFunctionDef)

    globalFunctionDef
  }

  override def translate(node: TinyScalaParser.FunDefContext)(implicit context: TranslationContext): Unit = {
    val functionDef = getFunctionDefAndAddToContext(node)

    val returnsLlvm = functionDef.returns.llvmRepr
    val paramsLlvm = functionDef.params.map { p => s"${p._type.llvmRepr} ${p.llvmNameRepr}"}.mkString(", ")
    context.writeCodeLn(CodeTarget.LOCAL) { s"define $returnsLlvm ${functionDef.llvmName} ($paramsLlvm) {" }
    context.>>()

    val exprResult = context.inLocalContext(defining = LocalContext.Defining.Function(functionDef).some) {
      new ExprTranslator(CodeTarget.LOCAL).translate(node.expr)
    }

    if (exprResult._type == Type._Nothing) {

    }
    else if (exprResult._type == functionDef.returns) {
      context.writeCodeLn(CodeTarget.LOCAL) { s"ret ${exprResult._type.llvmRepr} ${exprResult.llvmName}" }
    }
    else {
      throw new IllegalStateException(s"Type mismatch for function ${functionDef.tinyScalaName}, expected: ${functionDef.returns}, actual: ${exprResult._type}")
    }

    context.<<()
    context.writeCodeLn(CodeTarget.LOCAL) { "}" }
  }
}
