package com.wrathenn.compilers
package translators.templates

import context.TranslationContext
import translators.Translator
import translators.functions.FunDefTranslator
import translators.variables.ObjectPatVarDefTranslator

object DefTranslator extends Translator[TinyScalaParser.Def_Context, Unit] {
  override def translate(node: TinyScalaParser.Def_Context)(implicit context: TranslationContext): Unit = {
    if (node.patVarDef != null) {
      val definingObject = context.getDefiningObjectOrDie
      new ObjectPatVarDefTranslator(definingObject.objectName).translate(node.patVarDef)
    }
    else {
      FunDefTranslator.translate(node.funDef)
    }
  }
}
