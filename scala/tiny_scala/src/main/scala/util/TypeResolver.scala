package com.wrathenn.compilers
package util

import TinyScalaParser.TypeDefinitionContext
import context.TranslationContext
import models.{GenericStructDef, StructDef, StructKey, Type}
import models.Type.Primitive._Unit
import models.Type.{Primitive, Ref}

import cats.syntax.all._

import scala.jdk.CollectionConverters.CollectionHasAsScala

object TypeResolver {

  def getStructKey(typeDefinition: TypeDefinitionContext)(implicit context: TranslationContext): StructKey = {
    if (typeDefinition.simpleType != null) StructKey(typeDefinition.simpleType.Id.getText, List())
    else {
      val className = typeDefinition.genericType.Id.getText
      val generics = typeDefinition.genericType.typeParams.typeDefinition().asScala.map { td => getStructKey(td) }
      StructKey(className, generics.toList)
    }
  }

  def resolveType(structKey: StructKey)(implicit context: TranslationContext): Type = {
    if (structKey.generics.isEmpty) {
      return structKey.tinyScalaName match {
        case Primitive._Int.tinyScalaRepr => Primitive._Int
        case Primitive._Long.tinyScalaRepr => Primitive._Long
        case Primitive._Float.tinyScalaRepr => Primitive._Float
        case Primitive._Double.tinyScalaRepr => Primitive._Double
        case Primitive._Chr.tinyScalaRepr => Primitive._Chr
        case Primitive._Boolean.tinyScalaRepr => Primitive._Boolean
        case Primitive._Unit.tinyScalaRepr => _Unit
        case Ref._String.tinyScalaRepr => Ref._String
        case _ => throw new IllegalStateException(s"Unknown simple type ${structKey.tinyScalaName}")
      }
    }

    if (context.structDefinitions.contains(structKey)) {
      return Type.Ref.Struct(structKey.tinyScalaName, context.structDefinitions(structKey))
    }

    val genericStructDef = context.genericStructDefinitions.getOrElse(structKey.tinyScalaName,
      throw new IllegalStateException(s"Generic class ${structKey.tinyScalaName} is not defined yet")
    )
    val genericTypes = structKey.generics.map { g => resolveType(g) }

    var genericTypeIndex = 0
    val completedType = StructDef(
      tinyScalaRepr = structKey.tinyScalaName,
      generics = structKey.generics,
      properties = genericStructDef.properties.map {
        case GenericStructDef.CompleteProperty(_name, _type, index) => StructDef.Property(_name, _type, index)
        case GenericStructDef.GenericProperty(_name, _, index) => {
          val genericType = genericTypes.get(genericTypeIndex).getOrElse {
            throw new IllegalStateException(s"Not enough generic types for ${structKey.tinyScalaName}")
          }
          genericTypeIndex += 1;
          StructDef.Property(_name, genericType, index)
        }
      },
    )

    context.addStructDefinition(completedType)
    Type.Ref.Struct(structKey.tinyScalaName, completedType)
  }

  def getTypeFromDefinition(typeDefinition: TypeDefinitionContext)(implicit context: TranslationContext): Type = {
    val structKey = this.getStructKey(typeDefinition)
    resolveType(structKey)
  }

  def getStructFromDefinition(typeDefinition: TypeDefinitionContext)(implicit context: TranslationContext): Type.Ref.Struct = {
    val structKey = this.getStructKey(typeDefinition)
    resolveType(structKey) match {
      case struct: Type.Ref.Struct => struct
      case _ => throw new IllegalStateException("")
    }
  }
}
