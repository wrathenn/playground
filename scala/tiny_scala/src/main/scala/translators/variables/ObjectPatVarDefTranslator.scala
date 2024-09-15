package com.wrathenn.compilers
package translators.variables

import models.{CodeTarget, VariableDef}
import translators.expr.ExprTranslator
import translators.Translator

import com.wrathenn.compilers.context.TranslationContext

class ObjectPatVarDefTranslator(
  val objectName: String,
) extends Translator[TinyScalaParser.PatVarDefContext, Unit] {

  override def translate(context: TranslationContext, node: TinyScalaParser.PatVarDefContext): Unit = {
    val incomplete = PatVarDefTranslatorHelper.getVariableDef(node)

    val tinyScalaRepr = s"${objectName}.${incomplete.name}"
    val llvmNameRepr = s"@${objectName}.${incomplete.name}"
    context.writeCode(CodeTarget.INIT)(s"; start of init for $tinyScalaRepr\n")

    val variableDef = VariableDef(tinyScalaRepr, llvmNameRepr, incomplete._type, incomplete.decl)
    context.addGlobalVariable(variableDef)
    context.localContext.variables.addOne(incomplete.name -> variableDef)
    context.writeCodeLn(CodeTarget.GLOBAL) { s"$llvmNameRepr = global ptr undef" }

    val expr = node.patDef.expr
    val exprValue = new ExprTranslator(CodeTarget.INIT).translate(context, expr)

    context.writeCode(CodeTarget.INIT)(
      s"store ${exprValue._type.llvmRepr} ${exprValue.llvmName}, ptr $llvmNameRepr ; init done\n\n"
    )
  }
}
