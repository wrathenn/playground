package com.wrathenn.compilers
package translators

import models.{StructDef, Type}
import util.Util

import java.util
import scala.jdk.CollectionConverters.CollectionHasAsScala

object TmplDefTranslator extends Translator[TinyScalaParser.TmplDefContext] {
  override def translate(context: TranslationContext, node: TinyScalaParser.TmplDefContext): String = {
    if (node.tmplDefCaseClass != null) TmplDefCaseClassTranslator.translate(context, node.tmplDefCaseClass)
    else TmplDefObjectTranslator.translate(context, node.tmplDefObject)
  }
}

object TmplDefCaseClassTranslator extends Translator[TinyScalaParser.TmplDefCaseClassContext] {
  override def translate(context: TranslationContext, node: TinyScalaParser.TmplDefCaseClassContext): String = {
    val id = node.Id.getText
    val classParams = node.classParamClause.classParams
    val collectedParams = Util.collect(i => classParams.classParam(i))

    val properties = for {
      (param, i) <- collectedParams.zipWithIndex
      paramId = param.Id.getText
      paramTinyScalaTypeRepr = param.type_.simpleType.stableId.getText
      paramType = Type.fromRepr(paramTinyScalaTypeRepr)
    } yield StructDef.Property(name = paramId, _type = paramType, index = i)

    val structDef = StructDef(tinyScalaRepr = id, properties = properties)
    context.structDefinitions.addOne(id -> structDef)

    structDef.llvm
  }
}

object TmplDefObjectTranslator extends Translator[TinyScalaParser.TmplDefObjectContext] {
  override def translate(context: TranslationContext, node: TinyScalaParser.TmplDefObjectContext): String = {
    val id = node.Id.getText
    val body = node.templateBody

    val templateStats = body.templateStat.asScala
    templateStats.map { ts =>
      new DefTranslator(objectName = id).translate(context, ts.def_)
    }.mkString("")
  }
}
