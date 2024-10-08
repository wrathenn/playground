package com.wrathenn.compilers
package translators.functions

import context.TranslationContext
import models.GenericProperty
import models.function.FunctionDefGeneric
import translators.Translator
import util.TypeResolver

import scala.jdk.CollectionConverters.CollectionHasAsScala

object FunDefSignatureTranslator extends Translator[TinyScalaParser.FunDefContext, Unit] {

  private def collectParams(params: List[TinyScalaParser.ParamContext]): List[GenericProperty] = {
    params.map { param =>
      val name = param.Id.getText
      val typeKey = TypeResolver.getStructKey(param.typeDefinition)

      GenericProperty(name, typeKey)
    }
  }

  override def translate(node: TinyScalaParser.FunDefContext)(implicit context: TranslationContext): Unit = {
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

}
