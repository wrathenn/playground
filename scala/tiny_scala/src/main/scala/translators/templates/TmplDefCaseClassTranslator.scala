package com.wrathenn.compilers
package translators.templates

import models.{CodeTarget, GenericProperty, Type}
import translators.Translator
import util.{TypeResolver, Util}
import context.TranslationContext

import com.wrathenn.compilers.models.struct.{StructDef, StructDefGeneric}

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
    val typeParamAliases = node.sigTypeParams.sigTypeParam.asScala.map(_.Id.getText).toList
    val classParams = node.classParamClause.classParams.classParam.asScala.toList

    val properties = classParams.map { p =>
      val name = p.Id.getText
      val key = TypeResolver.getStructKey(p.typeDefinition)
      GenericProperty(name, key)
    }

    val genericStructDef = StructDefGeneric(
      tinyScalaName = className,
      typeParamAliases = typeParamAliases,
      properties = properties,
    )

    context.addGenericStructDefinition(genericStructDef)
  }
}
