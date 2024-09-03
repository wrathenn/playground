package com.wrathenn.compilers
package translators.templates

import translators.{TranslationContext, Translator}

object TmplDefTranslator extends Translator[TinyScalaParser.TmplDefContext, Unit] {
  override def translate(context: TranslationContext, node: TinyScalaParser.TmplDefContext): Unit = {
    if (node.tmplDefCaseClass != null) TmplDefCaseClassTranslator.translate(context, node.tmplDefCaseClass)
    else TmplDefObjectTranslator.translate(context, node.tmplDefObject)
  }
}

