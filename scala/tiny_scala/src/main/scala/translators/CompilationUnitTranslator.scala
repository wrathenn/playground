package com.wrathenn.compilers
package translators

import scala.jdk.CollectionConverters.CollectionHasAsScala


object CompilationUnitTranslator extends Translator[TinyScalaParser.CompilationUnitContext] {

  override def translate(context: TranslationContext, node: TinyScalaParser.CompilationUnitContext): String = {
    val tmplDefs = node.tmplDef()
    tmplDefs.asScala.map { tmplDef =>
      TmplDefTranslator.translate(context, tmplDef)
    }.mkString("\n")
  }
}
