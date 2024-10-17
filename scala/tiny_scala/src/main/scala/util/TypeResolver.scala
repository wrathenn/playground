package com.wrathenn.compilers
package util

import TinyScalaParser.TypeDefinitionContext
import context.LocalContext.Defining
import context.TranslationContext
import models.Type.Primitive._Unit
import models.Type.{Primitive, Ref}
import models.function.FunctionDef
import models.struct.{StructDef, StructDefGeneric}
import models._
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

  def resolveFunction(functionName: TinyScalaName, concreteGenericTypes: List[Type])(implicit context: TranslationContext): FunctionDef = {
    ???
//    if (concreteGenericTypes.isEmpty) {
//      val completedKey = CompletedKey(functionName, Map())
//      context.findFunctionByKey(completedKey) match {
//        case Some(value) => return value
//        case None => {}
//      }
//    }
//
//    val genericFunctionDef = context.findGenericFunctionByName(functionName).getOrElse {
//      throw new IllegalStateException(s"Unknown function ${functionName}")
//    }
//
//    if (genericFunctionDef.typeParamAliases.size != concreteGenericTypes.size) {
//      throw new IllegalStateException(
//        s"${functionName} accepts ${genericFunctionDef.typeParamAliases.size} type parameters," +
//          s"${concreteGenericTypes.size} were provided"
//      )
//    }
//    val resolvedGenerics = genericFunctionDef.typeParamAliases.zip(concreteGenericTypes).toMap
//    val completedKey = CompletedKey(functionName, resolvedGenerics)
//
//    context.findFunctionByKey(completedKey) match {
//      case Some(value) => return value
//      case None => {}
//    }
//
//    context.inLocalContext(
//      defining = Defining.WithConcreteGenerics(completedKey.concreteGenericTypes).some
//    ) {
//      val params = genericFunctionDef.params.map { p =>
//        val _type = this.resolveType(p.genericKey, resolvedGenerics)
//        VariableDef(
//          tinyScalaName = p.name,
//          llvmNameRepr = s"%${p.name}",
//          _type = _type,
//          decl = VariableDecl.VAL,
//          isFunctionParam = true,
//        )
//      }
//      val returnsType = resolveType(genericFunctionDef.returns, resolvedGenerics)
//
//      val functionDef = FunctionDef(
//        tinyScalaName = genericFunctionDef.tinyScalaName,
//        llvmName = s"@${genericFunctionDef.tinyScalaName}_${concreteGenericTypes.hashCode()}", // so they are distinct
//        concreteGenericTypes = completedKey.concreteGenericTypes,
//        params = params,
//        returns = returnsType,
//        isVarArg = false,
//      )
//      context.addFunctionDefinition(functionDef)
//      new FunDefExprTranslator(functionDef).translate(genericFunctionDef.expression)
//
//      functionDef
//    }
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

  def resolveType(
    typeName: TypeName,
    prevResolvedGenerics: Map[TinyScalaName, TypeName] = Map(),
  )(implicit context: TranslationContext): Type = {
    if (typeName.generics.isEmpty) {
      typeName.tinyScalaName match {
        case Primitive._Int.tinyScalaName => return Primitive._Int
        case Primitive._Long.tinyScalaName => return Primitive._Long
        case Primitive._Float.tinyScalaName => return Primitive._Float
        case Primitive._Double.tinyScalaName => return Primitive._Double
        case Primitive._Chr.tinyScalaName => return Primitive._Chr
        case Primitive._Boolean.tinyScalaName => return Primitive._Boolean
        case Primitive._Unit.tinyScalaName => return _Unit
        case Ref._String.tinyScalaName => return Ref._String
        case _ => {}
      }
      if (prevResolvedGenerics.contains(typeName.tinyScalaName)) {
        return resolveType(
          typeName = prevResolvedGenerics(typeName.tinyScalaName),
          prevResolvedGenerics = prevResolvedGenerics
        )
      }
    }

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

    val properties = genericStructDef.properties.zipWithIndex.map { case (p, i) =>
      StructDef.Property(
        name = p.name,
        _type = p.genericKey,
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
