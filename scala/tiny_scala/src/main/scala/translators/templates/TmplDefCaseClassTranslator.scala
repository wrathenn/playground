package com.wrathenn.compilers
package translators.templates

import models.{CodeTarget, StructDef, Type}
import translators.Translator
import util.{TypeResolver, Util}
import context.TranslationContext

import scala.jdk.CollectionConverters.CollectionHasAsScala

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

  override def translate(node: TinyScalaParser.TmplDefCaseClassContext)(implicit context: TranslationContext): Unit = {
    val className = node.Id.getText
    if (node.sigTypeParams != null) {
      val typeParams = node.sigTypeParams().sigTypeParam().asScala.map(_.Id.getText).toSet
      val classParams = node.classParamClause.classParams.classParam().asScala.toList

      val properties = for {
        (param, i) <- classParams.zipWithIndex
        paramId = param.Id.getText
        paramType = TypeResolver.getTypeFromDefinition(param.typeDefinition)
      } yield StructDef.Property(name = paramId, _type = paramType, index = i)
    }


    val structDef = StructDef(tinyScalaRepr = className, properties = properties)
    context.addStructDefinition(structDef)
    context.writeCodeLn(CodeTarget.GLOBAL) { genStructRepr(structDef) }
  }
}
