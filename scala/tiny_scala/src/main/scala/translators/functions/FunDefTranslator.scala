package com.wrathenn.compilers
package translators.functions

import models.FunctionDef
import translators.expr.ExprTranslator
import translators.{TranslationContext, Translator}
import util.Util

import cats.syntax.all._

import scala.jdk.CollectionConverters.CollectionHasAsScala

class FunDefTranslator(val objectName: String) extends Translator[TinyScalaParser.FunDefContext, Unit] {
  private def collectParams(params: List[TinyScalaParser.ParamContext]): List[FunctionDef.Param] = {
    params.map { param =>
      val name = param.Id.getText
      val _type = Util.collectType(param.type_)
      FunctionDef.Param(name = name, _type = _type)
    }
  }

  private def getFunctionDefAndAddDoContext(context: TranslationContext, node: TinyScalaParser.FunDefContext): FunctionDef = {
    val name = node.funSig.Id.getText
    val tinyScalaGlobalName = s"$objectName.$name"
    val llvmName = s"@$objectName.$name"

    val params = {
      val _params = node.funSig.params
      if (_params == null) List()
      else _params.param().asScala.toList
    }
    val paramsDef = collectParams(params)

    val returnType = if (node.type_ != null) Util.collectType(node.type_).some else None

    val globalFunctionDef = FunctionDef(
      tinyScalaName = tinyScalaGlobalName,
      llvmName = llvmName,
      params = paramsDef,
      returns = returnType,
    )
    context.globalFunctionsDefinitions.addOne(tinyScalaGlobalName -> globalFunctionDef)
    context.localFunctionsDefinitions.addOne(name -> globalFunctionDef)

    globalFunctionDef
  }

  override def translate(context: TranslationContext, node: TinyScalaParser.FunDefContext): Unit = {
    val functionDef = getFunctionDefAndAddDoContext(context, node)

    val returnsLlvm = functionDef.returns.map(_.llvmRepr).getOrElse("void")
    val paramsLlvm = functionDef.params.map { p => s"${p._type.llvmRepr} ${p.name}"}.mkString(", ")
    context.writeCodeLn(isGlobal = false) { s"define $returnsLlvm ${functionDef.llvmName} ($paramsLlvm) {" }

    {
      if (node.expr != null) {
        // todo return this value
        val ret = new ExprTranslator(isGlobal = false).translate(context, node.expr)
      }
      else {
        // todo not that easy ofc
        node.block.expr.asScala.map { e =>
          new ExprTranslator(isGlobal = false).translate(context, e)
        }
      }
    }

    context.writeCodeLn(isGlobal = false) { "}" }
  }
}
