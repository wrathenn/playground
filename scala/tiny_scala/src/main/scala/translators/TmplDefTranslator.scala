package com.wrathenn.compilers
package translators

import models.{StructDef, Type}
import util.Util

object TmplDefTranslator extends Translator[TinyScalaParser.TmplDefContext] {
  override def translate(context: TranslationContext, node: TinyScalaParser.TmplDefContext): String = {
    val caseClassNode = node.tmplDefCaseClass()
    TmplDefCaseClassTranslator.translate(context, caseClassNode)
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
      paramTinyScalaTypeRepr = param.paramType.type_.simpleType.stableId.getText
      paramType = Type.fromRepr(paramTinyScalaTypeRepr)
    } yield StructDef.Property(name = paramId, _type = paramType, index = i)

    val structDef = StructDef(tinyScalaRepr = id, properties = properties)
    context.structDefinitions.addOne(id -> structDef)

    structDef.llvm
  }
}
