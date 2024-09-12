package com.wrathenn.compilers
package translators.functions

import models.{CodeTarget, FunctionDef}
import translators.expr.ExprTranslator
import translators.Translator
import util.Util

import cats.syntax.all._
import com.wrathenn.compilers.context.TranslationContext

import scala.jdk.CollectionConverters.CollectionHasAsScala

class FunDefTranslator(val objectName: String) extends Translator[TinyScalaParser.FunDefContext, Unit] {
  private def collectParams(params: List[TinyScalaParser.ParamContext]): List[FunctionDef.Param] = {
    params.map { param =>
      val name = param.Id.getText
      val _type = Util.collectType(param.type_)
      FunctionDef.Param(tinyScalaName = name, llvmName = s"%$name" , _type = _type)
    }
  }

  private def getFunctionDefAndAddToContext(context: TranslationContext, node: TinyScalaParser.FunDefContext): FunctionDef = {
    val name = node.funSig.Id.getText
    val tinyScalaGlobalName = s"$objectName.$name"
    val llvmName = s"@$objectName.$name"

    val params = {
      val _params = node.funSig.params
      if (_params == null) List()
      else _params.param().asScala.toList
    }
    val paramsDef = collectParams(params)

    val returnType = Util.collectType(node.type_)

    val globalFunctionDef = FunctionDef(
      tinyScalaName = tinyScalaGlobalName,
      llvmName = llvmName,
      params = paramsDef,
      returns = returnType,
    )
    context.addGlobalFunction(globalFunctionDef)
    context.localContext.functions.addOne(name -> globalFunctionDef)

    globalFunctionDef
  }

  override def translate(context: TranslationContext, node: TinyScalaParser.FunDefContext): Unit = {
    val functionDef = getFunctionDefAndAddToContext(context, node)

    val returnsLlvm = functionDef.returns.llvmRepr
    val paramsLlvm = functionDef.params.map { p => s"${p._type.llvmRepr} ${p.llvmName}"}.mkString(", ")
    context.writeCodeLn(CodeTarget.LOCAL) { s"define $returnsLlvm ${functionDef.llvmName} ($paramsLlvm) {" }
    context.>>()

    context.inLocalContext(defining = functionDef.some) {
      val exprs = if (node.expr != null) List(node.expr) else node.block.expr.asScala.toList
      exprs.map { e =>
        // todo return value from a function
        new ExprTranslator(CodeTarget.LOCAL).translate(context, e)
      }
    }

    context.<<()
    context.writeCodeLn(CodeTarget.LOCAL) { "}" }
  }
}
