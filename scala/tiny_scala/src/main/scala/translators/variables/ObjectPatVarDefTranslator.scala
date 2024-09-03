package com.wrathenn.compilers
package translators.variables

import models.VariableDef
import translators.expr.ExprTranslator
import translators.{TranslationContext, Translator}

class ObjectPatVarDefTranslator(
  val objectName: String,
) extends Translator[TinyScalaParser.PatVarDefContext, Unit] {

  override def translate(context: TranslationContext, node: TinyScalaParser.PatVarDefContext): Unit = {
    val incomplete = PatVarDefTranslatorHelper.getVariableDef(node)

    val tinyScalaRepr = s"${objectName}.${incomplete.name}"
    val llvmNameRepr = s"@${objectName}.${incomplete.name}"
    context.initCode.append(s"; start of init for $tinyScalaRepr\n")

    val variableDef = VariableDef(tinyScalaRepr, llvmNameRepr, incomplete._type, incomplete.decl)
    context.globalVariables.addOne(tinyScalaRepr -> variableDef)
    context.localVariables.addOne(incomplete.name -> variableDef)
    context.globalCode.append(s"$llvmNameRepr = global ptr undef\n")

    val expr = node.patDef.expr
    val exprValue = new ExprTranslator(isGlobal = true).translate(context, expr)

    context.initCode.append(s"store ${exprValue._type.getOrElse(incomplete._type).llvmRepr} ${exprValue.llvmName}, ptr $llvmNameRepr ; init done\n\n")
  }
}
