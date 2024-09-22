package com.wrathenn.compilers
package translators.expr

import models.{CodeTarget, Operator, ReturnedValue, Type}
import models.Type.Primitive
import translators.Translator
import util.Util

import cats.syntax.all._
import com.wrathenn.compilers.context.TranslationContext

class InfixExprTranslator(target: CodeTarget) extends Translator[TinyScalaParser.InfixExprContext, ReturnedValue] {

  private def castTwoResults(context: TranslationContext, e1: ReturnedValue, e2: ReturnedValue): (String, String, Primitive) = {
    val e1Type = e1._type match {
      case primitive: Primitive => primitive
      case _ => throw new IllegalStateException("Infix operator between non-primitives")
    }
    val e2Type = e2._type match {
      case primitive: Primitive => primitive
      case _ => throw new IllegalStateException("Infix operator between non-primitives")
    }

    if (e1Type == e2Type) return (e1.llvmName, e2.llvmName, e1Type)

    val tempVal = context.genLocalVariableName(target)

    // im sorry ok
    e1Type match {
      case Primitive._Int => {
        e2Type match {
          case Primitive._Long => {
            context.writeCode(target) { s"$tempVal = zext i32 ${e1.llvmName} to i64\n" }
            return (tempVal, e2.llvmName, e2Type)
          }
          case Primitive._Float => {
            context.writeCode(target) { s"$tempVal = sitofp i32 ${e1.llvmName} to float\n" }
            return (tempVal, e2.llvmName, e2Type)
          }
          case Primitive._Double => {
            context.writeCode(target) { s"$tempVal = sitofp i32 ${e1.llvmName} to double\n" }
            return (tempVal, e2.llvmName, e2Type)
          }
          case Primitive._Chr => {
            context.writeCode(target) { s"$tempVal = zext i8 ${e2.llvmName} to i32\n" }
            return (e1.llvmName, tempVal, e1Type)
          }
          case _ => ???
        }
      }
      case Primitive._Long => {
        e2Type match {
          case Primitive._Int => {
            context.writeCode(target) { s"$tempVal = zext i32 ${e2.llvmName} to i64\n" }
            return (e1.llvmName, tempVal, e1Type)
          }
          case Primitive._Float => {
            context.writeCode(target) { s"$tempVal = sitofp i64 ${e1.llvmName} to float\n" }
            return (tempVal, e2.llvmName, e2Type)
          }
          case Primitive._Double => {
            context.writeCode(target) { s"$tempVal = sitofp i64 ${e1.llvmName} to double\n" }
            return (tempVal, e2.llvmName, e2Type)
          }
          case Primitive._Chr => {
            context.writeCode(target) { s"$tempVal = zext i8 ${e2.llvmName} to i64\n" }
            return (e1.llvmName, tempVal, e1Type)
          }
          case _ => ???
        }
      }
      case Primitive._Float => {
        e2Type match {
          case Primitive._Int => {
            context.writeCode(target) { s"$tempVal = sitofp i32 ${e2.llvmName} to float\n" }
            return (e1.llvmName, tempVal, e1Type)
          }
          case Primitive._Long => {
            context.writeCode(target) { s"$tempVal = sitofp i64 ${e2.llvmName} to float\n" }
            return (e1.llvmName, tempVal, e1Type)
          }
          case Primitive._Double => {
            context.writeCode(target) { s"$tempVal = fpext float ${e1.llvmName} to double\n" }
            return (tempVal, e2.llvmName, e2Type)
          }
          case Primitive._Chr => {
            context.writeCode(target) { s"$tempVal = sitofp i8 ${e2.llvmName} to float\n" }
            return (e1.llvmName, tempVal, e1Type)
          }
          case _ => ???
        }
      }
      case Primitive._Double => {
        e2Type match {
          case Primitive._Int => {
            context.writeCode(target) { s"$tempVal = sitofp i32 ${e2.llvmName} to double\n" }
            return (e1.llvmName, tempVal, e1Type)
          }
          case Primitive._Long => {
            context.writeCode(target) { s"$tempVal = sitofp i64 ${e2.llvmName} to double\n" }
            return (e1.llvmName, tempVal, e1Type)
          }
          case Primitive._Float => {
            context.writeCode(target) { s"$tempVal = fpext float ${e2.llvmName} to double\n" }
            return (e1.llvmName, tempVal, e1Type)
          }
          case Primitive._Chr => {
            context.writeCode(target) { s"$tempVal = sitofp i8 ${e2.llvmName} to double\n" }
            return (e1.llvmName, tempVal, e1Type)
          }
          case _ => ???
        }
      }
      case Primitive._Chr => {
        e2Type match {
          case Primitive._Int => {
            context.writeCode(target) { s"$tempVal = zext i8 ${e1.llvmName} to i32\n" }
            return (tempVal, e2.llvmName, e2Type)
          }
          case Primitive._Long => {
            context.writeCode(target) { s"$tempVal = zext i8 ${e1.llvmName} to i64\n" }
            return (tempVal, e2.llvmName, e2Type)
          }
          case Primitive._Float => {
            context.writeCode(target) { s"$tempVal = sitofp i8 ${e1.llvmName} to float\n" }
            return (tempVal, e2.llvmName, e2Type)
          }
          case Primitive._Double => {
            context.writeCode(target) { s"$tempVal = sitofp i8 ${e1.llvmName} to double\n" }
            return (tempVal, e2.llvmName, e2Type)
          }
          case _ => ???
        }
      }
      case _ => ???
    }
  }

  override def translate(context: TranslationContext, node: TinyScalaParser.InfixExprContext): ReturnedValue = {
    if (node.prefixExpr != null) { return new PrefixExprTranslator(target).translate(context, node.prefixExpr) }

    val expr1 = this.translate(context, node.infixExpr(0))
    val expr2 = this.translate(context, node.infixExpr(1))
    val infixOp = Util.getOperator(node.children.get(1).getText) match {
      case infix: Operator.Infix => infix
      case _ => throw new IllegalStateException(s"Not an infix operator ${node.children.get(1).getText}")
    }

    val (e1, e2, commonType) = castTwoResults(context, expr1, expr2)

    val tempVal = context.genLocalVariableName(target)

    val llvmF = Util.getOperatorLlvm(infixOp, commonType)
    context.writeCode(target) { s"$tempVal = $llvmF ${commonType.llvmRepr} $e1, $e2\n" }
    val retType = if (infixOp.isInstanceOf[Operator.BooleanOp]) {
      Type.Primitive._Boolean
    } else commonType
    ReturnedValue(llvmName = tempVal, _type = retType)
  }
}
