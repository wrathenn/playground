package com.wrathenn.compilers
package translators.templates

import translators.{TranslationContext, Translator}
import cats.syntax.all._
import scala.jdk.CollectionConverters.CollectionHasAsScala

object TmplDefObjectTranslator extends Translator[TinyScalaParser.TmplDefObjectContext, Unit] {
  override def translate(context: TranslationContext, node: TinyScalaParser.TmplDefObjectContext): Unit = {
    val id = node.Id.getText
    val body = node.templateBody

    val templateStats = body.templateStat.asScala

    context.localObject = id.some
    templateStats.foreach { ts =>
      new DefTranslator(objectName = id).translate(context, ts.def_)
    }
    context.localObject = None
    context.localVariables.clear()
  }
}
