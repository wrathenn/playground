package com.wrathenn.compilers
package translators.statement

import context.TranslationContext
import models.{CodeTarget, Type}
import translators.Translator
import translators.expr.ExprTranslator

import java.util.UUID

class StatementTranslator(target: CodeTarget) extends Translator[TinyScalaParser.StatementContext, Unit] {
  private val branchId = UUID.randomUUID().toString.substring(0, 8)
  private val conditionLabel = s"loop.$branchId.condition"
  private val blockLabel = s"loop.$branchId.block"
  private val endLabel = s"loop.$branchId.end"

  private def translateCondition(context: TranslationContext, node: TinyScalaParser.ExprContext) = {
    context.writeCodeLn(target) { s"$conditionLabel:" }
    context.>>()
    context.inLocalContext(defining = None) {
      val conditionValue = new ExprTranslator(target).translate(context, node)
      if (conditionValue._type != Type.Primitive._Boolean) {
        throw new IllegalStateException(s"Loop condition value should be Boolean, actual: ${conditionValue._type}")
      }

      context.writeCodeLn(target) { s"br i1 ${conditionValue.llvmName}, label %$blockLabel, label %$endLabel" }
    }
    context.<<()
  }

  private def translateBlock(context: TranslationContext, node: TinyScalaParser.ExprContext) = {
    context.writeCodeLn(target) { s"$blockLabel:" }
    context.>>()
    context.inLocalContext(defining = None) {
      new ExprTranslator(target).translate(context, node)
      context.writeCodeLn(target) { s"br label %$conditionLabel" }
    }
    context.<<()
  }

  override def translate(context: TranslationContext, node: TinyScalaParser.StatementContext): Unit = {
    // could be same but ok
    if (node.children.get(0).getText == "while") {
      context.writeCodeLn(target) { s"br label %$conditionLabel" }
      translateCondition(context, node.expr(0))
      translateBlock(context, node.expr(1))
      context.writeCodeLn(target) { s"$endLabel:" }
    } else if (node.children.get(0).getText == "do") {
      context.writeCodeLn(target) { s"br label %$blockLabel" }
      translateBlock(context, node.expr(0))
      translateCondition(context, node.expr(1))
      context.writeCodeLn(target) { s"$endLabel:" }
    } else {
      throw new IllegalStateException("Unsupported statement, check grammar")
    }
  }
}
