package com.wrathenn.compilers
package translators.templates

import context.{LocalContext, TranslationContext}
import models.CodeTarget
import translators.Translator

import cats.syntax.all._

import scala.jdk.CollectionConverters.CollectionHasAsScala

object TmplDefObjectTranslator extends Translator[TinyScalaParser.TmplDefObjectContext, Unit] {
  override def translate(node: TinyScalaParser.TmplDefObjectContext)(implicit context: TranslationContext): Unit = {
    val id = node.Id.getText
    val body = node.templateBody

    val templateStats = body.templateStat.asScala
    val codeTarget = if (node.objectIsMain != null) CodeTarget.MAIN else CodeTarget.INIT

    context.inLocalContext(defining = LocalContext.Defining.Object(objectName = id).some) {
      templateStats.foreach { ts =>
        new TemplateStatTranslator(target = codeTarget).translate(ts)
      }
    }
  }
}
