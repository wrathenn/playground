package com.wrathenn.compilers
package translators.templates

import translators.Translator
import com.wrathenn.compilers.context.TranslationContext

object TmplDefTranslator extends Translator[TinyScalaParser.TmplDefContext, Unit] {
  override def translate(context: TranslationContext, node: TinyScalaParser.TmplDefContext): Unit = {
    if (node.tmplDefCaseClass != null) TmplDefCaseClassTranslator.translate(context, node.tmplDefCaseClass)
    else TmplDefObjectTranslator.translate(context, node.tmplDefObject)
  }
}

