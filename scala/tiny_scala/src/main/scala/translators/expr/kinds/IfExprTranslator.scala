package com.wrathenn.compilers
package translators.expr.kinds

import context.TranslationContext
import models.Type.Primitive._Unit
import models.{CodeTarget, ReturnedValue, Type}
import translators.Translator
import translators.expr.ExprTranslator

import cats.syntax.all._

class IfExprTranslator(target: CodeTarget) extends Translator[TinyScalaParser.ExprContext, ReturnedValue] {
  override def translate(node: TinyScalaParser.ExprContext)(implicit context: TranslationContext): ReturnedValue = {
    val conditionExpr = node.expr(0)
    val thenExpr = node.expr(1)
    val elseExpr = if (node.expr(2) != null) node.expr(2).some else None

    val conditionResult = new ExprTranslator(target).translate(conditionExpr)
    if (conditionResult._type != Type.Primitive._Boolean) {
      throw new IllegalStateException(s"Condition type should be boolean, got ${conditionResult._type}")
    }

    val thenLabel = conditionResult.llvmName.stripPrefix("%") ++ ".then_branch"
    val elseLabel = conditionResult.llvmName.stripPrefix("%") ++ ".else_branch"
    val thenEndLabel = conditionResult.llvmName.stripPrefix("%") ++ ".end_branch"

    context.writeCodeLn(target) { s"br i1 ${conditionResult.llvmName}, label %$thenLabel, label %$elseLabel" }

    // will need to insert code before generated in then/else result
    context.writeCodeLn(target) { s"$thenLabel:" }
    val thenResult = new ExprTranslator(target).translate(thenExpr)
    context.writeCodeLn(target) { s"br label %$thenEndLabel" }

    // will need to insert code before generated in then/else result
    context.writeCodeLn(target) { s"$elseLabel:" }
    val elseResult = elseExpr.map { elseE =>
      new ExprTranslator(target).translate(elseE)
    }.getOrElse(ReturnedValue(llvmName = "UNIT", _type = _Unit))
    context.writeCodeLn(target) { s"br label %$thenEndLabel" }

    context.writeCodeLn(target) { s"$thenEndLabel:" }

    val tempVal = context.genLocalVariableName(target)

    /**
     * then=?,else=? => Any
     */
    if (thenResult._type.isInstanceOf[Type.Primitive] && elseResult.isInstanceOf[Type.Ref]) {
      val thenType = thenResult._type.asInstanceOf[Type.Primitive]
      throw new IllegalStateException("todo boxing")
    }
    else if (thenResult._type.isInstanceOf[Type.Ref] && elseResult.isInstanceOf[Type.Primitive]) {
      throw new IllegalStateException("todo boxing")
    }
    else if (thenResult._type == elseResult._type) {
      if (thenResult._type != Type.Primitive._Unit) {
        context.writeCodeLn(target) {
          s"$tempVal = phi ${thenResult._type.llvmRepr} [${thenResult.llvmName}, %$thenLabel], [${elseResult.llvmName}, %$elseLabel]"
        }
      }
      ReturnedValue(llvmName = tempVal, _type = thenResult._type)
    }
    else if (thenResult._type == Type._Nothing) {
      ReturnedValue(llvmName = elseResult.llvmName, _type = elseResult._type)
    }
    else if (elseResult._type == Type._Nothing) {
      ReturnedValue(llvmName = thenResult.llvmName, _type = thenResult._type)
    }
    else {
      throw new IllegalStateException(s"Type mismatch. Then: ${thenResult._type}. Else: ${elseResult._type}")
    }
  }
}
