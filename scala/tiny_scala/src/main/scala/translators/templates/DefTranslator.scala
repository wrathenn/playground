package com.wrathenn.compilers
package translators.templates

import context.TranslationContext
import translators.Translator
import translators.functions.FunDefTranslator
import translators.variables.ObjectPatVarDefTranslator

class DefTranslator(
  val objectName: String,
) extends Translator[TinyScalaParser.Def_Context, Unit] {
  // todo clear context or even make it stack-like (please)
  override def translate(context: TranslationContext, node: TinyScalaParser.Def_Context): Unit = {
    if (node.patVarDef != null) {
      new ObjectPatVarDefTranslator(objectName).translate(context, node.patVarDef)
    }
    else {
      val functionContext = context.createNewContext()
      new FunDefTranslator(objectName).translate(context, node.funDef)
      context.finishLocalContext()
    }
  }
}
