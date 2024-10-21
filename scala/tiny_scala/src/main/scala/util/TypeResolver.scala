package com.wrathenn.compilers
package util

import TinyScalaParser.TypeDefinitionContext
import context.LocalContext.Defining
import context.TranslationContext
import models._
import models.`type`.Type.Primitive._Unit
import models.`type`.Type.{Primitive, Ref}
import models.`type`.{Type, TypeName}
import models.function.{FunctionDef, FunctionDefGeneric}
import models.struct.{StructDef, StructDefGeneric}
import translators.functions.FunDefExprTranslator
import translators.templates.TmplDefCaseClassTranslator
import util.Aliases.TinyScalaName

import cats.syntax.all._

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
  def getStructTypeName(typeDefinition: TypeDefinitionContext): TypeName = {
    if (typeDefinition.simpleType != null) TypeName(typeDefinition.simpleType.Id.getText, List())
    else {
      val className = typeDefinition.genericType.Id.getText
      val generics = typeDefinition.genericType.typeParamsBlock.typeParams.typeDefinition().asScala.map { td => getStructTypeName(td) }
      TypeName(className, generics.toList)
    }
  }

  private def completeGenericFunction(
    genericFunction: FunctionDefGeneric,
    concreteGenerics: List[TypeName],
  )(implicit context: TranslationContext): FunctionDef = {
    if (genericFunction.typeParamAliases.size != concreteGenerics.size) {
      throw new IllegalStateException(s"Expected ${genericFunction.typeParamAliases.size} type parameters, got ${concreteGenerics.size}")
    }

    val resolvedGenerics = genericFunction.typeParamAliases.zip(concreteGenerics).toMap

    val functionDef = context.inLocalContext(defining = Defining.WithConcreteGenerics(resolvedGenerics).some) {
      val params = genericFunction.params.map { genericParam =>
        val _type = resolveType(genericParam.typeName)
        VariableDef(
          tinyScalaName = genericParam.name,
          llvmNameRepr = s"%${genericParam.name}",
          _type = _type,
          decl = VariableDecl.VAL,
          isFunctionParam = true,
        )
      }

      val returnsType = resolveType(genericFunction.returns)

      val functionKey = FunctionDef.Key(tinyScalaName = genericFunction.tinyScalaName, params = params, returnsType = returnsType)
      context.findFunctionByKey(functionKey) match {
        case Some(value) => return value
        case None => {}
      }

      FunctionDef(
        tinyScalaName = genericFunction.tinyScalaName,
        llvmName = s"@${genericFunction.tinyScalaName}_${functionKey.hashCode().abs}", // so they are distinct
        concreteGenericTypes = resolvedGenerics,
        params = params,
        returns = returnsType,
        isVarArg = false,
      )
    }

    context.inLocalContext(defining = Defining.Function(functionDef).some) {
      context.addFunctionDefinition(functionDef)
      new FunDefExprTranslator(functionDef).translate(genericFunction.expression)
    }

    functionDef
  }

  def resolveFunction(
    functionName: TinyScalaName,
    concreteGenericTypes: List[TypeName]
  )(implicit context: TranslationContext): FunctionDef = {
    val genericFunction = context.findGenericFunctionByName(functionName).getOrElse {
      throw new IllegalArgumentException(s"Function $functionName not found")
    }

    completeGenericFunction(genericFunction, concreteGenericTypes)
  }

  private val defaultTypes = Map(
    Primitive._Int.tinyScalaName -> Primitive._Int,
    Primitive._Long.tinyScalaName -> Primitive._Long,
    Primitive._Float.tinyScalaName -> Primitive._Float,
    Primitive._Double.tinyScalaName -> Primitive._Double,
    Primitive._Chr.tinyScalaName -> Primitive._Chr,
    Primitive._Boolean.tinyScalaName -> Primitive._Boolean,
    Primitive._Unit.tinyScalaName -> Primitive._Unit,
    Ref._String.tinyScalaName -> Ref._String,
    Ref._Null.tinyScalaName -> Ref._Null,
    Ref._Any.tinyScalaName -> Ref._Any,
  )

  private def finalizeTypeName(typeName: TypeName)(implicit context: TranslationContext): TypeName = {
    val prevResolvedGenerics = context.prevResolvedGenerics
    // No generics -- search default or previously resolved generics.
    // Otherwise this typeName is finalized
    if (typeName.generics.isEmpty) {
      if (defaultTypes.contains(typeName.tinyScalaName)) {
        return typeName
      }
      if (prevResolvedGenerics.contains(typeName.tinyScalaName)) {
        return prevResolvedGenerics(typeName.tinyScalaName)
      }
      return typeName
    }

    val resolvedGenerics = typeName.generics.map(g => finalizeTypeName(g))
    TypeName(typeName.tinyScalaName, resolvedGenerics)
  }

  def resolveType(typeName: TypeName)(implicit context: TranslationContext): Type = {
    val prevResolvedGenerics = context.prevResolvedGenerics

    if (typeName.generics.isEmpty) {
      if (defaultTypes.contains(typeName.tinyScalaName)) {
        return defaultTypes(typeName.tinyScalaName)
      }
      if (prevResolvedGenerics.contains(typeName.tinyScalaName)) {
        return resolveType(prevResolvedGenerics(typeName.tinyScalaName))
      }
    }

    // Now will try to define type if it wasn't defined yet. Will add it to context.structDefinitions
    val genericStructDef: StructDefGeneric = context.genericStructDefinitions.get(typeName.tinyScalaName).getOrElse {
      throw new IllegalStateException(s"No ${typeName.tinyScalaName} type was defined")
    }

    if (genericStructDef.typeParamAliases.size != typeName.generics.size) {
      throw new IllegalStateException(
        s"${genericStructDef.tinyScalaName} has${genericStructDef.typeParamAliases.size} type parameters," +
          s"${typeName.generics.size} were provided"
      )
    }

    val resolvedGenerics = genericStructDef.typeParamAliases
      .zip(typeName.generics)
      .map { case (alias, generic) =>
        alias -> finalizeTypeName(generic)
      }

    val structDef = context.inLocalContext(defining = Defining.WithConcreteGenerics(resolvedGenerics.toMap).some) {
      val properties = genericStructDef.properties.zipWithIndex.map { case (p, i) =>
        StructDef.Property(
          name = p.name,
          _type = finalizeTypeName(p.typeName),
          index = i,
        )
      }

      val structDef = StructDef(
        tinyScalaName = typeName.tinyScalaName,
        llvmName = s"%${typeName.tinyScalaName}_${resolvedGenerics.hashCode().abs}",
        concreteGenericTypes = resolvedGenerics,
        properties = properties,
      )

      if (context.structDefinitions.contains(structDef.typeName)) {
        return Type.Ref.Struct(tinyScalaName = typeName.tinyScalaName, structDef = context.structDefinitions(structDef.typeName))
      }
      context.addStructDefinition(structDef)

      val newStructLlvmCode = TmplDefCaseClassTranslator.genStructRepr(structDef)
      context.writeCodeLn(CodeTarget.GLOBAL) { newStructLlvmCode }

      structDef
    }

    Type.Ref.Struct(typeName.tinyScalaName, structDef)
  }

  def getTypeFromDefinition(typeDefinition: TypeDefinitionContext)(implicit context: TranslationContext): Type = {
    val structKey = this.getStructTypeName(typeDefinition)
    resolveType(structKey)
  }

  def getStructFromDefinition(typeDefinition: TypeDefinitionContext)(implicit context: TranslationContext): Type.Ref.Struct = {
    val structKey = this.getStructTypeName(typeDefinition)
    resolveType(structKey) match {
      case struct: Type.Ref.Struct => struct
      case _ => throw new IllegalStateException("")
    }
  }
}
