package com.wrathenn.compilers
package translators.functions

import context.{LocalContext, TranslationContext}
import models.function.FunctionDefGeneric
import models.{CodeTarget, GenericProperty, Type}
import translators.Translator
import translators.expr.ExprTranslator
import util.TypeResolver

import cats.syntax.all._

import scala.jdk.CollectionConverters.CollectionHasAsScala

object FunDefTranslator extends Translator[TinyScalaParser.FunDefContext, Unit] {

  private def collectParams(params: List[TinyScalaParser.ParamContext])(
    implicit context: TranslationContext,
  ): List[GenericProperty] = {
    params.map { param =>
      val name = param.Id.getText
      val typeKey = TypeResolver.getStructKey(param.typeDefinition)

      GenericProperty(name, typeKey)
    }
  }

  private def buildFunctionDefAndAddToContext(node: TinyScalaParser.FunDefContext)(
    implicit context: TranslationContext,
  ): Unit = {
    val name = node.funSig.Id.getText

    val definingObject = context.getDefiningObjectOrDie
    val objectName = definingObject.objectName
    val tinyScalaGlobalName = s"$objectName.$name"

    val typeParamAliases = node.funSig.sigTypeParams.sigTypeParam.asScala.map(_.Id.getText).toList
    val params = {
      val _params = node.funSig.params
      if (_params == null) List()
      else _params.param().asScala.toList
    }
    val paramsDef = collectParams(params)

    val returns = TypeResolver.getStructKey(node.typeDefinition)

    val localGenericFunctionDef = FunctionDefGeneric(
      tinyScalaName = name,
      typeParamAliases = typeParamAliases,
      params = paramsDef,
      returns = returns,
      expression = node.expr
    )
    context.addLocalGenericFunctionDefinition(localGenericFunctionDef)

    val globalGenericFunctionDef = localGenericFunctionDef.copy(tinyScalaName = tinyScalaGlobalName)
    context.addGenericFunctionDefinition(globalGenericFunctionDef)
  }

  override def translate(node: TinyScalaParser.FunDefContext)(implicit context: TranslationContext): Unit = {
    buildFunctionDefAndAddToContext(node)

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
