package com.wrathenn.compilers
package translators.expr

import models.{Operator, ReturnedValue, Type}
import models.Type.Primitive
import translators.{TranslationContext, Translator}
import util.Util
import cats.syntax.all._
import scala.jdk.CollectionConverters.CollectionHasAsScala

class PrefixExprTranslator(isGlobal: Boolean) extends Translator[TinyScalaParser.PrefixExprContext, ReturnedValue] {

  override def translate(context: TranslationContext, node: TinyScalaParser.PrefixExprContext): ReturnedValue = {
    if (node.simpleExpr1 != null) {
      val res = new SimpleExpr1Translator(isGlobal).translate(context, node.simpleExpr1)
      if (node.opNoPrecedence == null) return res

      context.writeCode(isGlobal) { "; applying prefix op\n" }

      if (res._type.isEmpty) throw new IllegalStateException(s"Type unknown, can't use operator ${node.opNoPrecedence.children.get(1).getText}")
      val resType = res._type.get
      val prefixOp = Util.getOperator(node.opNoPrecedence.getText) match {
        case prefix: Operator.Prefix => prefix
        case _ => throw new IllegalStateException(s"Operator not a prefix operator ${node.opNoPrecedence.getText}")
      }

      val primitiveType = resType match {
        case primitive: Type.Primitive => primitive
        case _ => throw new IllegalStateException(s"No prefix operators for non-primitive type $resType")
      }

      val retValue = prefixOp match {
        case Operator.Plus => {
          primitiveType match {
            case Primitive._Int | Primitive._Long | Primitive._Float | Primitive._Double => {}
            case _ => throw new IllegalStateException(s"Unapplicable operator $prefixOp for type $primitiveType")
          }
          res
        }
        case Operator.Minus => {
          val tempVal = s"%v_${context.localCounter}"
          context.localCounter += 1

          val (sub, zero) = primitiveType match {
            case Primitive._Int => "sub" -> "0"
            case Primitive._Long => "sub" -> "0"
            case Primitive._Float => "fsub" -> "0.0"
            case Primitive._Double => "fsub" ->"0.0f"
            case _ => throw new IllegalStateException(s"Unapplicable operator $prefixOp for type $primitiveType")
          }

          context.writeCode(isGlobal) { s"$tempVal = $sub ${primitiveType.llvmRepr} $zero, ${res.llvmName}\n" }
          ReturnedValue(llvmName = tempVal, _type = primitiveType.some)
        }
        case Operator.Not => {
          ???
        }
      }

      context.writeCode(isGlobal) { "; applied prefix op\n" }
      return retValue
    }
    else {
      val newClassExpr = node.newClassExpr
      val argumentExpressions = newClassExpr.argumentExprs.exprs.expr().asScala
      val expressions = argumentExpressions.map { e => new ExprTranslator(isGlobal).translate(context, e) }


      ???
    }
  }
}
