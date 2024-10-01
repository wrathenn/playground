package com.wrathenn.compilers
package translators.variables

import models.{CodeTarget, VariableDef}
import translators.expr.ExprTranslator
import translators.Translator

import context.TranslationContext

class ObjectPatVarDefTranslator(
  val objectName: String,
) extends Translator[TinyScalaParser.PatVarDefContext, Unit] {

  override def translate(node: TinyScalaParser.PatVarDefContext)(implicit context: TranslationContext): Unit = {
    val incomplete = PatVarDefTranslatorHelper.getVariableDef(node)

    val tinyScalaRepr = s"${objectName}.${incomplete.name}"
    val llvmNameRepr = s"@${objectName}.${incomplete.name}"
    context.writeCode(CodeTarget.INIT)(s"; start of init for $tinyScalaRepr\n")

    val variableDef = VariableDef(
      tinyScalaRepr = tinyScalaRepr,
      llvmNameRepr = llvmNameRepr,
      _type = incomplete._type,
      decl = incomplete.decl,
      isFunctionParam = false,
    )
    context.addGlobalVariable(variableDef)
    context.addLocalVariable(variableDef.copy(tinyScalaRepr = incomplete.name))
    context.writeCodeLn(CodeTarget.GLOBAL) { s"$llvmNameRepr = global ptr undef" }

    val expr = node.patDef.expr
    val exprValue = new ExprTranslator(CodeTarget.INIT).translate(expr)

    context.writeCode(CodeTarget.INIT)(
      s"store ${exprValue._type.llvmRepr} ${exprValue.llvmName}, ptr $llvmNameRepr ; init done\n\n"
    )
  }
}
