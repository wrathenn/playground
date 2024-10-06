package com.wrathenn.compilers
package util

import TinyScalaParser.TypeDefinitionContext
import context.TranslationContext
import models.{GenericKey, Type}
import models.Type.Primitive._Unit
import models.Type.{Primitive, Ref}

import cats.syntax.all._
import com.wrathenn.compilers.models.struct.{StructDef, StructDefGeneric}
import com.wrathenn.compilers.util.Aliases.TinyScalaName

import scala.jdk.CollectionConverters.CollectionHasAsScala

object TypeResolver {

  /**
   * type
   * List[
   *   Test[A],
   *   B,
   *   Lol[
   *     Kek[C]
   *   ],
   * ]
   * Should be =
   * GenericKey("List", [
   *   GenericKey("Test", [ GenericKey("A", []) ]),
   *   GenericKey("B", []),
   *   GenericKey("Lol", [
   *     GenericKey("Kek", [ GenericKey("C", []) ])
   *   ])
   * ])
   */
  def getStructKey(typeDefinition: TypeDefinitionContext): GenericKey = {
    if (typeDefinition.simpleType != null) GenericKey(typeDefinition.simpleType.Id.getText, List())
    else {
      val className = typeDefinition.genericType.Id.getText
      val generics = typeDefinition.genericType.typeParams.typeDefinition().asScala.map { td => getStructKey(td) }
      GenericKey(className, generics.toList)
    }
  }

  def resolveType(genericKey: GenericKey)(implicit context: TranslationContext): Type = {
    if (genericKey.generics.isEmpty) {
      val simpleType = genericKey.tinyScalaName match {
        case Primitive._Int.tinyScalaName => Primitive._Int
        case Primitive._Long.tinyScalaName => Primitive._Long
        case Primitive._Float.tinyScalaName => Primitive._Float
        case Primitive._Double.tinyScalaName => Primitive._Double
        case Primitive._Chr.tinyScalaName => Primitive._Chr
        case Primitive._Boolean.tinyScalaName => Primitive._Boolean
        case Primitive._Unit.tinyScalaName => _Unit
        case Ref._String.tinyScalaName => Ref._String
        case _ => null
      }
      if (simpleType != null) return simpleType
    }

    if (context.structDefinitions.contains(genericKey)) {
      return Type.Ref.Struct(genericKey.tinyScalaName, context.structDefinitions(genericKey))
    }

    val genericStructDef = context.genericStructDefinitions.getOrElse(genericKey.tinyScalaName,
      throw new IllegalStateException(s"Generic class ${genericKey.tinyScalaName} is not defined yet")
    )
    val genericTypes = genericKey.generics.map { g => resolveType(g) }

    var genericTypeIndex = 0
    val completedType = StructDef(
      tinyScalaName = genericKey.tinyScalaName,
      generics = genericKey.generics,
      properties = genericStructDef.properties.map {
        case StructDefGeneric.CompleteProperty(_name, _type, index) => StructDef.Property(_name, _type, index)
        case StructDefGeneric.GenericProperty(_name, _, index) => {
          val genericType = genericTypes.get(genericTypeIndex).getOrElse {
            throw new IllegalStateException(s"Not enough generic types for ${genericKey.tinyScalaName}")
          }
          genericTypeIndex += 1;
          StructDef.Property(_name, genericType, index)
        }
      },
    )

    context.addStructDefinition(completedType)
    Type.Ref.Struct(genericKey.tinyScalaName, completedType)
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
