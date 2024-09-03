package com.wrathenn.compilers
package translators.variables

import models.VariableDef
import translators.{TranslationContext, Translator}

object TemplatePatVarDefTranslator extends Translator[TinyScalaParser.PatVarDefContext, Unit] {
  override def translate(context: TranslationContext, node: TinyScalaParser.PatVarDefContext): Unit = {
    val incomplete = PatVarDefTranslatorHelper.getVariableDef(node)

    if (context.localVariables.contains(incomplete.name)) throw new IllegalStateException(s"Redeclaration of ${incomplete.name}")

    val tinyScalaRepr = incomplete.name

    val variableDef = VariableDef(tinyScalaRepr, s"%$tinyScalaRepr", incomplete._type, incomplete.decl)
    context.localVariables.addOne(tinyScalaRepr -> variableDef)
    context.globalCode.append(s"${variableDef.llvmNameRepr} = alloca ${variableDef._type.llvmRepr}\n") // todo
  }
}
