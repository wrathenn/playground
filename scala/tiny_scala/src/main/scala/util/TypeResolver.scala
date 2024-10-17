package com.wrathenn.compilers
package util

import TinyScalaParser.TypeDefinitionContext
import context.TranslationContext
import models.{CodeTarget, CompletedKey, GenericKey, Type, VariableDecl, VariableDef}
import models.Type.Primitive._Unit
import models.Type.{Primitive, Ref}

import cats.syntax.all._
import com.wrathenn.compilers.context.LocalContext.Defining
import com.wrathenn.compilers.models.function.FunctionDef
import com.wrathenn.compilers.models.struct.{StructDef, StructDefGeneric}
import com.wrathenn.compilers.translators.functions.FunDefExprTranslator
import com.wrathenn.compilers.translators.templates.TmplDefCaseClassTranslator
import com.wrathenn.compilers.util.Aliases.{LlvmName, TinyScalaName}

import java.util.UUID
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
      val generics = typeDefinition.genericType.typeParamsBlock.typeParams.typeDefinition().asScala.map { td => getStructKey(td) }
      GenericKey(className, generics.toList)
    }
  }

  def resolveFunction(functionName: TinyScalaName, concreteGenericTypes: List[Type])(implicit context: TranslationContext): FunctionDef = {
    if (concreteGenericTypes.isEmpty) {
      val completedKey = CompletedKey(functionName, Map())
      context.findFunctionByKey(completedKey) match {
        case Some(value) => return value
        case None => {}
      }
    }

    val genericFunctionDef = context.findGenericFunctionByName(functionName).getOrElse {
      throw new IllegalStateException(s"Unknown function ${functionName}")
    }

    if (genericFunctionDef.typeParamAliases.size != concreteGenericTypes.size) {
      throw new IllegalStateException(
        s"${functionName} accepts ${genericFunctionDef.typeParamAliases.size} type parameters," +
          s"${concreteGenericTypes.size} were provided"
      )
    }
    val resolvedGenerics = genericFunctionDef.typeParamAliases.zip(concreteGenericTypes).toMap
    val completedKey = CompletedKey(functionName, resolvedGenerics)

    context.findFunctionByKey(completedKey) match {
      case Some(value) => return value
      case None => {}
    }

    context.inLocalContext(
      defining = Defining.WithConcreteGenerics(completedKey.concreteGenericTypes).some
    ) {
      val params = genericFunctionDef.params.map { p =>
        val _type = this.resolveType(p.genericKey, resolvedGenerics)
        VariableDef(
          tinyScalaName = p.name,
          llvmNameRepr = s"%${p.name}",
          _type = _type,
          decl = VariableDecl.VAL,
          isFunctionParam = true,
        )
      }
      val returnsType = resolveType(genericFunctionDef.returns, resolvedGenerics)

      val functionDef = FunctionDef(
        tinyScalaName = genericFunctionDef.tinyScalaName,
        llvmName = s"@${genericFunctionDef.tinyScalaName}_${concreteGenericTypes.hashCode()}", // so they are distinct
        concreteGenericTypes = completedKey.concreteGenericTypes,
        params = params,
        returns = returnsType,
        isVarArg = false,
      )
      context.addFunctionDefinition(functionDef)
      new FunDefExprTranslator(functionDef).translate(genericFunctionDef.expression)

      functionDef
    }
  }

  private def resolveType(
    genericKey: GenericKey,
    prevResolvedGenerics: Map[TinyScalaName, Type]
  )(implicit context: TranslationContext): Type = {
    if (genericKey.generics.isEmpty) {
      genericKey.tinyScalaName match {
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
      if (prevResolvedGenerics.contains(genericKey.tinyScalaName)) {
        return prevResolvedGenerics(genericKey.tinyScalaName)
      }
    }

    val genericStructDef: StructDefGeneric = context.genericStructDefinitions.get(genericKey.tinyScalaName).getOrElse {
      if (genericKey.generics.nonEmpty) {
        throw new IllegalStateException(s"No ${genericKey.tinyScalaName} type defined with generics [${genericKey.generics.mkString(", ")}]")
      }
      val allConcreteGenerics = context.collectDefiningWithConcreteGenerics()
      if (allConcreteGenerics.contains(genericKey.tinyScalaName)) {
        return allConcreteGenerics(genericKey.tinyScalaName)
      } else {
        throw new IllegalStateException(s"No ${genericKey.tinyScalaName} type defined")
      }
    }

    if (genericStructDef.typeParamAliases.size != genericKey.generics.size) {
      throw new IllegalStateException(
        s"${genericStructDef.tinyScalaName} has${genericStructDef.typeParamAliases.size} type parameters," +
          s"${genericKey.generics.size} were provided"
      )
    }

    val resolvedGenerics = prevResolvedGenerics concat
      genericStructDef.typeParamAliases
        .zip(genericKey.generics)
        .map { case (alias, gk) =>
          alias -> resolveType(gk, prevResolvedGenerics)
        }.toMap

    val completedKey = CompletedKey(tinyScalaName = genericKey.tinyScalaName, concreteGenericTypes = resolvedGenerics)

    if (context.structDefinitions.contains(completedKey)) {
      return Type.Ref.Struct(genericKey.tinyScalaName, context.structDefinitions(completedKey))
    }

    val structDef = StructDef(
      tinyScalaName = genericKey.tinyScalaName,
      llvmName = s"%${genericKey.tinyScalaName}_${completedKey.concreteGenericTypes.hashCode()}",
      concreteGenericTypes = completedKey.concreteGenericTypes,
      properties = genericStructDef.properties.zipWithIndex.map { case (p, i) =>
        val _type = resolveType(p.genericKey, resolvedGenerics)
        StructDef.Property(
          name = p.name,
          _type = _type,
          index = i,
        )
      },
    )
    val newStructLlvmCode = TmplDefCaseClassTranslator.genStructRepr(structDef)
    context.writeCodeLn(CodeTarget.GLOBAL) { newStructLlvmCode }

    context.addStructDefinition(structDef)
    Type.Ref.Struct(genericKey.tinyScalaName, structDef)
  }

  def getTypeFromDefinition(typeDefinition: TypeDefinitionContext)(implicit context: TranslationContext): Type = {
    val structKey = this.getStructKey(typeDefinition)
    resolveType(structKey, Map())
  }

  def getStructFromDefinition(typeDefinition: TypeDefinitionContext)(implicit context: TranslationContext): Type.Ref.Struct = {
    val structKey = this.getStructKey(typeDefinition)
    resolveType(structKey, Map()) match {
      case struct: Type.Ref.Struct => struct
      case _ => throw new IllegalStateException("")
    }
  }
}
