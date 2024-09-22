package com.wrathenn.compilers
package translators.expr

import models.{CodeTarget, ReturnedValue, VariableDecl}
import translators.Translator

import com.wrathenn.compilers.context.TranslationContext
import com.wrathenn.compilers.translators.expr.kinds.{ExprBlockTranslator, ExprReturnTranslator, IfExprTranslator, InfixExprTranslator}
import com.wrathenn.compilers.util.Util

class ExprTranslator(target: CodeTarget) extends Translator[TinyScalaParser.ExprContext, ReturnedValue] {

  override def translate(context: TranslationContext, node: TinyScalaParser.ExprContext): ReturnedValue = {
    if (node.infixExpr != null) {
      return new InfixExprTranslator(target).translate(context, node.infixExpr)
    }

    if (node.exprBlock != null) {
      return new ExprBlockTranslator(target).translate(context, node.exprBlock)
    }

    if (node.children.get(0).getText == "return") {
      return new ExprReturnTranslator(target).translate(context, node)
    }

    if (node.children.get(0).getText == "if") {
      return new IfExprTranslator(target).translate(context, node)
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

      val exprValue = this.translate(context, node)
      if (exprValue._type != variable._type) {
        throw new IllegalStateException(s"Type mismatch for assignment expression to ${variable.tinyScalaRepr} -- expected: ${variable._type}, actual: ${exprValue._type}")
      }


      context.writeCode(target) {
        s"store ${exprValue._type.llvmRepr} ${exprValue.llvmName}, ptr ${variable.llvmNameRepr}\n"
      }
    }

    throw new IllegalStateException("Unknown expression definition, check grammar")
  }
}
