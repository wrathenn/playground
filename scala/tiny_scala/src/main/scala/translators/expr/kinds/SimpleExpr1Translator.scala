package com.wrathenn.compilers
package translators.expr.kinds

import context.TranslationContext

import com.wrathenn.compilers.models.`type`.Type.Ref._Null
import models.{CodeTarget, Literal, ReturnedValue}
import translators.Translator
import translators.expr.ExprTranslator
import util.{TypeResolver, Util}

import cats.syntax.all._
import com.wrathenn.compilers.models.`type`.{Type, TypeName}

import scala.jdk.CollectionConverters.CollectionHasAsScala

class SimpleExpr1Translator(target: CodeTarget) extends Translator[TinyScalaParser.SimpleExpr1Context, ReturnedValue] {
  private def translateLiteral(node: TinyScalaParser.LiteralContext)(
    implicit context: TranslationContext
  ): ReturnedValue = {
    val tempVal = context.genLocalVariableName(target)
    val literal = Util.getLiteral(node)

    literal match {
      case Literal.Value(litValue, _type) => {
        _type match {
          case _: Type.Ref._String.type => {
            val llvmStrGlobalName =  if (!context.stringLiterals.contains(litValue)) {
              val strLiteralName = s"@str.${context.stringLiterals.size}"

              context.addStringLiteral(litValue, strLiteralName)
              val (strValue, strSize) = Util.strToByteRepr(litValue.stripSuffix("\"").stripPrefix("\""))
              context.writeCodeLn(CodeTarget.GLOBAL)(s"$strLiteralName = global [$strSize x i8] c\"$strValue\"")

              strLiteralName
            } else context.stringLiterals(litValue)

            ReturnedValue(llvmName = llvmStrGlobalName, _type)
          }
          case _ => {
            context.writeCodeLn(target) { s"$tempVal = alloca ${_type.llvmRepr}" }
            context.writeCodeLn(target) { s"store ${_type.llvmRepr} ${litValue}, ptr $tempVal" }
            context.writeCodeLn(target) { s"$tempVal.value = load ${_type.llvmRepr}, ptr $tempVal" }
            ReturnedValue(llvmName = s"$tempVal.value", _type)
          }
        }
      }
      case Literal.Null => {
//        context.writeCodeLn(target) { s"$tempVal = ptr null\n" }
        ReturnedValue(llvmName = "null", _type = _Null)
      }
    }
  }

  private def translateSimpleStableId(node: TinyScalaParser.StableIdContext)(
    implicit context: TranslationContext
  ): ReturnedValue = {
    val variable = new StableIdTranslator(target).translate(node)

    if (variable.isFunctionParam) return ReturnedValue(llvmName = variable.llvmNameRepr, _type = variable._type)

    val tempVal = context.genLocalVariableName(target)

    context.writeCode(target) { s"$tempVal.value = load ${variable._type.llvmRepr}, ptr ${variable.llvmNameRepr}\n" }
    ReturnedValue(llvmName = s"$tempVal.value", _type = variable._type)
  }

  private def translateFunctionStableId(
    stableId: TinyScalaParser.StableIdContext,
    typeParamsBlock: Option[TinyScalaParser.TypeParamsBlockContext],
    argumentExprs: TinyScalaParser.ArgumentExprsContext,
  )(implicit context: TranslationContext): ReturnedValue = {
    val functionName = Util.collectStableIdRepr(stableId)
    val typeParamsTypeNames = typeParamsBlock.map(_.typeParams.typeDefinition().asScala.toList.map { t =>
      TypeResolver.getStructTypeName(t)
    }).getOrElse(List[TypeName]())

    val functionDef = TypeResolver.resolveFunction(functionName, typeParamsTypeNames)

    val argumentExpressions = argumentExprs.exprs.expr().asScala

    val res = context.inLocalContext(defining = None) {
      val expressions = argumentExpressions.map { e => new ExprTranslator(target).translate(e) }

      functionDef.params.zip(expressions).foreach { case (p, e) =>
        if (p._type != e._type && !(p._type.isInstanceOf[Type.Ref] && e._type == Type.Ref._Null)) {
          throw new IllegalStateException(s"Type mismatch. Expected: ${p._type}, actual: ${e._type}")
        }
      }

      if (functionDef.params.size < expressions.size && !functionDef.isVarArg) {
        throw new IllegalStateException(s"Too many arguments for function $functionName")
      }
      else if (functionDef.params.size > expressions.size) {
        throw new IllegalStateException(s"Too few arguments for function $functionName")
      }

      val tempVal = context.genLocalVariableName(target)
      val argumentsLlvm = {
        val simpleParams = functionDef.params.map(_._type.llvmRepr).mkString(", ")
        if (functionDef.isVarArg) s"($simpleParams, ...)" else s"($simpleParams)"
      }
      val paramsLlvm = "(" + expressions.map { e => s"${e._type.llvmRepr} ${e.llvmName}" }.mkString(", ") + ")"

      val returnsType = functionDef.returns

      if (returnsType == Type.Primitive._Unit) {
        context.writeCodeLn(target) {
          s"call ${returnsType.llvmRepr} $argumentsLlvm ${functionDef.llvmName}${paramsLlvm}"
        }
        ReturnedValue(llvmName = "", _type = returnsType)
      } else {
        context.writeCodeLn(target) {
          s"$tempVal = call ${returnsType.llvmRepr} $argumentsLlvm ${functionDef.llvmName}${paramsLlvm}"
        }
        ReturnedValue(llvmName = tempVal, _type = returnsType)
      }
    }
    res
  }

  override def translate(node: TinyScalaParser.SimpleExpr1Context)(implicit context: TranslationContext): ReturnedValue = {
    if (node.expr != null) {
      return new ExprTranslator(target).translate(node.expr)
    }

    if (node.literal != null) {
      return translateLiteral(node.literal)
    }

    val stableId = node.stableId
    val argumentExprs = node.argumentExprs

    if (argumentExprs == null) {
      translateSimpleStableId(stableId)
    } else {
      val typeParamsBlock = if (node.typeParamsBlock() == null) None else node.typeParamsBlock.some
      translateFunctionStableId(stableId, typeParamsBlock, argumentExprs)
    }
  }
}
