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

    val params = genericFunction.params.map { genericParam =>
      val _type = resolveType(genericParam.typeName, resolvedGenerics)
      VariableDef(
        tinyScalaName = genericParam.name,
        llvmNameRepr = s"%${genericParam.name}",
        _type = _type,
        decl = VariableDecl.VAL,
        isFunctionParam = true,
      )
    }

    val returnsType = resolveType(genericFunction.returns, resolvedGenerics)

    val functionKey = FunctionDef.Key(tinyScalaName = genericFunction.tinyScalaName, params = params, returnsType = returnsType)
    context.findFunctionByKey(functionKey) match {
      case Some(value) => return value
      case None => {}
    }

    context.inLocalContext(
      defining = Defining.WithConcreteGenerics(resolvedGenerics).some
    ) {
      val functionDef = FunctionDef(
        tinyScalaName = genericFunction.tinyScalaName,
        llvmName = s"@${genericFunction.tinyScalaName}_${functionKey.hashCode().abs}", // so they are distinct
        concreteGenericTypes = resolvedGenerics,
        params = params,
        returns = returnsType,
        isVarArg = false,
      )
      context.addFunctionDefinition(functionDef)
      new FunDefExprTranslator(functionDef).translate(genericFunction.expression)

      functionDef
    }
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


  def resolvePrimitive(
    typeName: TypeName,
    prevResolvedGenerics: Map[TinyScalaName, TypeName] = Map(),
  ): Option[Type.Primitive] = {
    if (typeName.generics.isEmpty) {
      typeName.tinyScalaName match {
        case Primitive._Int.tinyScalaName => return Primitive._Int.some
        case Primitive._Long.tinyScalaName => return Primitive._Long.some
        case Primitive._Float.tinyScalaName => return Primitive._Float.some
        case Primitive._Double.tinyScalaName => return Primitive._Double.some
        case Primitive._Chr.tinyScalaName => return Primitive._Chr.some
        case Primitive._Boolean.tinyScalaName => return Primitive._Boolean.some
        case Primitive._Unit.tinyScalaName => return _Unit.some
        case _ => {}
      }
      if (prevResolvedGenerics.contains(typeName.tinyScalaName)) {
        return resolvePrimitive(
          typeName = prevResolvedGenerics(typeName.tinyScalaName),
          prevResolvedGenerics = prevResolvedGenerics,
        )
      }
    }
    None
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

  def finalizeTypeName(
    typeName: TypeName,
    prevResolvedGenerics: Map[TinyScalaName, TypeName],
  ): TypeName = {
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

    val resolvedGenerics = typeName.generics.map(g => finalizeTypeName(g, prevResolvedGenerics))
    TypeName(typeName.tinyScalaName, resolvedGenerics)
  }

  def resolveType(
    typeName: TypeName,
    prevResolvedGenerics: Map[TinyScalaName, TypeName],
  )(implicit context: TranslationContext): Type = {
    if (typeName.generics.isEmpty) {
      if (defaultTypes.contains(typeName.tinyScalaName)) {
        return defaultTypes(typeName.tinyScalaName)
      }
      if (prevResolvedGenerics.contains(typeName.tinyScalaName)) {
        return resolveType(
          typeName = prevResolvedGenerics(typeName.tinyScalaName),
          prevResolvedGenerics = prevResolvedGenerics
        )
      }
    }

    // todo move this after resolving generics?
    if (context.structDefinitions.contains(typeName)) {
      return Type.Ref.Struct(tinyScalaName = typeName.tinyScalaName, structDef = context.structDefinitions(typeName))
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
        alias -> finalizeTypeName(generic, prevResolvedGenerics)
      }

    val properties = genericStructDef.properties.zipWithIndex.map { case (p, i) =>
      StructDef.Property(
        name = p.name,
        _type = finalizeTypeName(p.typeName, prevResolvedGenerics ++ resolvedGenerics),
        index = i,
      )
    }

    val structDef = StructDef(
      tinyScalaName = typeName.tinyScalaName,
      llvmName = s"%${typeName.tinyScalaName}_${resolvedGenerics.hashCode().abs}",
      concreteGenericTypes = resolvedGenerics,
      properties = properties,
    )

    val newStructLlvmCode = TmplDefCaseClassTranslator.genStructRepr(structDef)
    context.writeCodeLn(CodeTarget.GLOBAL) { newStructLlvmCode }

    context.addStructDefinition(structDef)
    Type.Ref.Struct(typeName.tinyScalaName, structDef)
  }

  def getTypeFromDefinition(typeDefinition: TypeDefinitionContext)(implicit context: TranslationContext): Type = {
    val structKey = this.getStructTypeName(typeDefinition)
    resolveType(structKey, prevResolvedGenerics = Map())
  }

  def getStructFromDefinition(typeDefinition: TypeDefinitionContext)(implicit context: TranslationContext): Type.Ref.Struct = {
    val structKey = this.getStructTypeName(typeDefinition)
    resolveType(structKey, prevResolvedGenerics = Map()) match {
      case struct: Type.Ref.Struct => struct
      case _ => throw new IllegalStateException("")
    }
  }
}
