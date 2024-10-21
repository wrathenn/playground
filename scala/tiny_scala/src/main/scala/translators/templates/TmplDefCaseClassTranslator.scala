package com.wrathenn.compilers
package translators.templates

import models.{CodeTarget, `type`}
import translators.Translator
import util.{TypeResolver, Util}
import context.TranslationContext

import com.wrathenn.compilers.models.`type`.{GenericProperty, Type}
import com.wrathenn.compilers.models.struct.{StructDef, StructDefGeneric}

import scala.jdk.CollectionConverters.CollectionHasAsScala

object TmplDefCaseClassTranslator extends Translator[TinyScalaParser.TmplDefCaseClassContext, Unit] {
  def genStructRepr(structDef: StructDef)(implicit context: TranslationContext): String = {
    val types = structDef.properties.map { prop =>
      val resolvedType = TypeResolver.resolveType(prop._type)
      resolvedType.llvmRepr -> prop.name
    }.reverse

    val last = types.head
    val rest = types.tail

    val lastRepr = s"  ${last._1} ; ${last._2}"
    val restReprs = rest.map { case (r, n) => s"  ${r}, ; ${n}\n" }

    s"""
       |${structDef.llvmName} = type {
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
      val key = TypeResolver.getStructTypeName(p.typeDefinition)
      `type`.GenericProperty(name, key)
    }

    val genericStructDef = StructDefGeneric(
      tinyScalaName = className,
      typeParamAliases = typeParamAliases,
      properties = properties,
    )

    context.addGenericStructDefinition(genericStructDef)
  }
}
