package com.wrathenn.compilers
package translators.templates

import translators.Translator
import context.TranslationContext

object TmplDefTranslator extends Translator[TinyScalaParser.TmplDefContext, Unit] {
  override def translate(node: TinyScalaParser.TmplDefContext)(implicit context: TranslationContext): Unit = {
    if (node.tmplDefCaseClass != null) TmplDefCaseClassTranslator.translate(node.tmplDefCaseClass)
    else TmplDefObjectTranslator.translate(node.tmplDefObject)
  }
}

