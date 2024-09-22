package com.wrathenn.compilers
package translators

import context.TranslationContext
import translators.templates.TmplDefTranslator

import scala.jdk.CollectionConverters.CollectionHasAsScala

object CompilationUnitTranslator extends Translator[TinyScalaParser.CompilationUnitContext, Unit] {

  override def translate(context: TranslationContext, node: TinyScalaParser.CompilationUnitContext): Unit = {
    val tmplDefs = node.tmplDef()

    tmplDefs.asScala.foreach { tmplDef =>
      TmplDefTranslator.translate(context, tmplDef)
    }
  }
}
