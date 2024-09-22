package com.wrathenn.compilers
package translators.templates

import context.TranslationContext
import translators.Translator

import scala.jdk.CollectionConverters.CollectionHasAsScala

object TmplDefObjectTranslator extends Translator[TinyScalaParser.TmplDefObjectContext, Unit] {
  override def translate(context: TranslationContext, node: TinyScalaParser.TmplDefObjectContext): Unit = {
    val id = node.Id.getText
    val body = node.templateBody

    val templateStats = body.templateStat.asScala

    context.inLocalContext(defining = None) {
      templateStats.foreach { ts =>
        new TemplateStatTranslator(objectName = id).translate(context, ts)
      }
    }
  }
}
