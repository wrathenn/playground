package com.wrathenn.compilers
package translators.expr

import models.{CodeTarget, ReturnedValue, VariableDecl}
import translators.Translator

import context.TranslationContext
import translators.expr.kinds.{ExprBlockTranslator, ExprReturnTranslator, IfExprTranslator, InfixExprTranslator}
import util.Util

class ExprTranslator(target: CodeTarget) extends Translator[TinyScalaParser.ExprContext, ReturnedValue] {

  override def translate(node: TinyScalaParser.ExprContext)(implicit context: TranslationContext): ReturnedValue = {
    if (node.infixExpr != null) {
      return new InfixExprTranslator(target).translate(node.infixExpr)
    }

    if (node.exprBlock != null) {
      return new ExprBlockTranslator(target).translate(node.exprBlock)
    }

    if (node.children.get(0).getText == "return") {
      return new ExprReturnTranslator(target).translate(node)
    }

    if (node.children.get(0).getText == "if") {
      return new IfExprTranslator(target).translate(node)
    }

    if (node.stableId != null) {
      val variableName = Util.collectStableIdRepr(node.stableId)
      val variable = context.findVariableById(variableName).getOrElse {
        throw new IllegalStateException(s"Undefined variable $variableName")
      }
      variable.decl match {
        case VariableDecl.VAL => {
          throw new IllegalStateException(s"Cannot redefine VAL $variableName")
        }
        case VariableDecl.VAR => {}
      }

      val exprValue = this.translate(node)
      if (exprValue._type != variable._type) {
        throw new IllegalStateException(s"Type mismatch for assignment expression to ${variable.tinyScalaName} -- expected: ${variable._type}, actual: ${exprValue._type}")
      }


      context.writeCode(target) {
        s"store ${exprValue._type.llvmRepr} ${exprValue.llvmName}, ptr ${variable.llvmNameRepr}\n"
      }
    }

    throw new IllegalStateException("Unknown expression definition, check grammar")
  }
}
