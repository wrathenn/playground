package com.wrathenn.compilers
package translators.templates

import models.{CodeTarget, StructDef, Type}
import translators.Translator
import util.Util

import com.wrathenn.compilers.context.TranslationContext

object TmplDefCaseClassTranslator extends Translator[TinyScalaParser.TmplDefCaseClassContext, Unit] {
  override def translate(context: TranslationContext, node: TinyScalaParser.TmplDefCaseClassContext): Unit = {
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
    context.writeCodeLn(CodeTarget.GLOBAL) { structDef.llvm }
  }
}