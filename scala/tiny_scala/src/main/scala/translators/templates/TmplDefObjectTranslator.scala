package com.wrathenn.compilers
package translators.templates

import translators.Translator
import cats.syntax.all._
import com.wrathenn.compilers.context.TranslationContext
import scala.jdk.CollectionConverters.CollectionHasAsScala

object TmplDefObjectTranslator extends Translator[TinyScalaParser.TmplDefObjectContext, Unit] {
  override def translate(context: TranslationContext, node: TinyScalaParser.TmplDefObjectContext): Unit = {
    val id = node.Id.getText
    val body = node.templateBody

    val templateStats = body.templateStat.asScala

    context.inLocalContext {
      templateStats.foreach { ts =>
        new DefTranslator(objectName = id).translate(context, ts.def_)
      }
    }
  }
}
