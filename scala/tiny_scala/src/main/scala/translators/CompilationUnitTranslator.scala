package com.wrathenn.compilers
package translators

import context.TranslationContext
import translators.templates.TmplDefTranslator

import scala.jdk.CollectionConverters.CollectionHasAsScala

object CompilationUnitTranslator extends Translator[TinyScalaParser.CompilationUnitContext, Unit] {

  override def translate(node: TinyScalaParser.CompilationUnitContext)(implicit context: TranslationContext): Unit = {
    val tmplDefs = node.tmplDef()

    tmplDefs.asScala.foreach { tmplDef =>
      TmplDefTranslator.translate(tmplDef)
    }
  }
}
