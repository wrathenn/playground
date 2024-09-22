package com.wrathenn.compilers
package translators.expr.kinds

import context.TranslationContext
import models.Type.Ref._Null
import models.{CodeTarget, Literal, ReturnedValue, Type}
import translators.Translator
import translators.expr.ExprTranslator
import util.Util

import scala.jdk.CollectionConverters.CollectionHasAsScala

class SimpleExpr1Translator(target: CodeTarget) extends Translator[TinyScalaParser.SimpleExpr1Context, ReturnedValue] {
  private def translateLiteral(context: TranslationContext, node: TinyScalaParser.LiteralContext): ReturnedValue = {
    val tempVal = context.genLocalVariableName(target)
    val literal = Util.getLiteral(node)

    literal match {
      case Literal.Value(litValue, _type) => {
        _type match {
          case _: Type.Ref._String.type => {
            val llvmStrGlobalName =  if (!context.stringLiterals.contains(litValue)) {
              val strLiteralName = s"@str.${context.stringLiterals.size}"

              context.addStringLiteral(litValue, strLiteralName)
              val strBytes = litValue.stripSuffix("\"").stripPrefix("\"").map { b =>
                if ((b >= 'a' && b <= 'z') || (b >= 'A' && b <= 'Z')) b.toString
                else f"\\$b%X"
              }
              val strSize = strBytes.size + 1
              val strValue = s"""c"${strBytes.mkString("")}\\00""""
              context.writeCodeLn(CodeTarget.GLOBAL)(s"$strLiteralName = global [$strSize x i8] $strValue")

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
        context.writeCodeLn(target) { s"$tempVal = ptr null\n" }
        ReturnedValue(llvmName = tempVal, _type = _Null)
      }
    }
  }

  private def translateSimpleStableId(context: TranslationContext, node: TinyScalaParser.StableIdContext): ReturnedValue = {
    val scalaName = Util.collectStableIdRepr(node)
    val variable = context.findVariableById(scalaName)
      .getOrElse { throw new IllegalStateException(s"Cant find $scalaName at this point") }

    val tempVal = context.genLocalVariableName(target)

    context.writeCode(target) { s"$tempVal.value = load ${variable.llvmNameRepr}, ptr ${variable.llvmNameRepr}\n" }
    ReturnedValue(llvmName = s"$tempVal.value", _type = variable._type)
  }

  private def translateFunctionStableId(
    context: TranslationContext,
    idNode: TinyScalaParser.StableIdContext,
    argumentExprs: TinyScalaParser.ArgumentExprsContext,
  ): ReturnedValue = {
    val argumentExpressions = argumentExprs.exprs.expr().asScala
    val expressions = argumentExpressions.map { e => new ExprTranslator(target).translate(context, e) }
    ???
  }

  override def translate(context: TranslationContext, node: TinyScalaParser.SimpleExpr1Context): ReturnedValue = {
    if (node.expr != null) {
      return new ExprTranslator(target).translate(context, node.expr)
    }

    if (node.literal != null) {
      return translateLiteral(context, node.literal)
    }

    val stableId = node.stableId
    val argumentExprs = node.argumentExprs

    if (argumentExprs == null) {
      translateSimpleStableId(context, stableId)
    } else {
      translateFunctionStableId(context, stableId, argumentExprs)
    }
  }
}