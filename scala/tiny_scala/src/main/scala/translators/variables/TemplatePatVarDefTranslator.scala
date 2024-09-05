package com.wrathenn.compilers
package translators.variables

import models.{CodeTarget, VariableDef}
import translators.Translator

import com.wrathenn.compilers.context.TranslationContext

// todo super old
object TemplatePatVarDefTranslator extends Translator[TinyScalaParser.PatVarDefContext, Unit] {
  override def translate(context: TranslationContext, node: TinyScalaParser.PatVarDefContext): Unit = {
    val incomplete = PatVarDefTranslatorHelper.getVariableDef(node)

    if (context.localContext.variables.contains(incomplete.name))
      throw new IllegalStateException(s"Redeclaration of ${incomplete.name}")

    val tinyScalaRepr = incomplete.name

    val variableDef = VariableDef(tinyScalaRepr, s"%$tinyScalaRepr", incomplete._type, incomplete.decl)
    context.localContext.variables.addOne(tinyScalaRepr -> variableDef)
    context.writeCodeLn(CodeTarget.GLOBAL) { s"${variableDef.llvmNameRepr} = alloca ${variableDef._type.llvmRepr}" } // TODO
  }
}
