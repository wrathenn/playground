package com.wrathenn.compilers
package translators.templates

import context.TranslationContext
import translators.Translator
import translators.functions.FunDefTranslator
import translators.variables.ObjectPatVarDefTranslator

object DefTranslator extends Translator[TinyScalaParser.Def_Context, Unit] {
  override def translate(context: TranslationContext, node: TinyScalaParser.Def_Context): Unit = {
    if (node.patVarDef != null) {
      val definingObject = context.getDefiningObjectOrDie
      new ObjectPatVarDefTranslator(definingObject.objectName).translate(context, node.patVarDef)
    }
    else {
      FunDefTranslator.translate(context, node.funDef)
    }
  }
}
