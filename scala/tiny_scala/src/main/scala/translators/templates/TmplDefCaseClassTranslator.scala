package com.wrathenn.compilers
package translators.templates

import models.{CodeTarget, StructDef, Type}
import translators.Translator
import util.Util

import com.wrathenn.compilers.context.TranslationContext

object TmplDefCaseClassTranslator extends Translator[TinyScalaParser.TmplDefCaseClassContext, Unit] {
  private def genStructRepr(structDef: StructDef): String = {
    val types = structDef.properties.map { prop =>
      (prop._type match {
        case primitive: Type.Primitive => primitive.llvmRepr
        case _ => s"ptr"
      }) -> prop.name
    }.reverse

    val last = types.head
    val rest = types.tail

    val lastRepr = s"  ${last._1} ; ${last._2}"
    val restReprs = rest.map { case (r, n) => s"  ${r}, ; ${n}\n" }

    s"""
       |${structDef.llvmRepr} = type {
       |  i32 ; type tag
       |${restReprs.reverse.mkString("")}$lastRepr
       |}
       |""".stripMargin
  }

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
    context.addStructDefinition(structDef)
    context.writeCodeLn(CodeTarget.GLOBAL) { genStructRepr(structDef) }
  }
}
