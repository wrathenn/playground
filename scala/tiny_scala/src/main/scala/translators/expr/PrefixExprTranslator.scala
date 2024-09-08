package com.wrathenn.compilers
package translators.expr

import context.TranslationContext
import models.Type.{Primitive, Struct}
import models._
import translators.Translator
import util.Util

import cats.syntax.all._

import scala.jdk.CollectionConverters.CollectionHasAsScala

class PrefixExprTranslator(target: CodeTarget) extends Translator[TinyScalaParser.PrefixExprContext, ReturnedValue] {

  override def translate(context: TranslationContext, node: TinyScalaParser.PrefixExprContext): ReturnedValue = {
    if (node.simpleExpr1 != null) translateSimpleExpr1(context, node)
    else translateNewExpr(context, node)
  }

  private def translateSimpleExpr1(context: TranslationContext, node: TinyScalaParser.PrefixExprContext): ReturnedValue = {
    val res = new SimpleExpr1Translator(target).translate(context, node.simpleExpr1)
    if (node.opNoPrecedence == null) return res

    context.writeCode(target) { "; applying prefix op\n" }

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
        val tempVal = context.genLocalVariableName(target)

        val (sub, zero) = primitiveType match {
          case Primitive._Int => "sub" -> "0"
          case Primitive._Long => "sub" -> "0"
          case Primitive._Float => "fsub" -> "0.0"
          case Primitive._Double => "fsub" ->"0.0f"
          case _ => throw new IllegalStateException(s"Unapplicable operator $prefixOp for type $primitiveType")
        }

        context.writeCode(target) { s"$tempVal = $sub ${primitiveType.llvmRepr} $zero, ${res.llvmName}\n" }
        ReturnedValue(llvmName = tempVal, _type = primitiveType.some)
      }
      case Operator.Not => {
        ???
      }
    }

    context.writeCode(target) { "; applied prefix op\n" }
    return retValue
  }

  private def translateNewExpr(context: TranslationContext, node: TinyScalaParser.PrefixExprContext): ReturnedValue = {
    val newClassExpr = node.newClassExpr
    val argumentExpressions = newClassExpr.argumentExprs.exprs.expr().asScala
    val expressions = argumentExpressions.map { e => new ExprTranslator(target).translate(context, e) }

    val exprVal = context.genLocalVariableName(target)

    val structName = newClassExpr.Id.getText
    val structDef = context.structDefinitions.get(structName).getOrElse {
      throw new IllegalStateException(s"Definition of struct $structName not found")
    }

    if (expressions.size != structDef.properties.size) {
      throw new IllegalStateException(
        s"Provided ${expressions.size} arguments for $structName, which has ${structDef.properties.size}"
      )
    }

    structDef.properties.zip(expressions).foreach { case (p, e) =>
      if (e._type.isDefined) {
        if (p._type != e._type.get)
          throw new IllegalStateException(s"Type mismatch: expected ${p._type}, got ${e._type}")
      }
      else if (p._type.isInstanceOf[Type.Primitive]) {
        throw new IllegalStateException(s"Type mismatch: expected ${p._type}, got null")
      }
    }

    allocateStruct(exprValName = exprVal, context, structDef)
    structDef.properties.zip(expressions).zipWithIndex.foreach { case ((prop, expr), i) =>
      // get pointer to first field
      val fieldPointer = s"$exprVal.f$i.ptr"
      context.writeCodeLn(target) { s"$fieldPointer = getelementptr ${structDef.llvmRepr}, ptr $exprVal, i32 0, i32 $i" }
      context.writeCodeLn(target) { s"store ${prop._type.llvmRepr} ${expr.llvmName}, ptr $fieldPointer" }
    }
    return ReturnedValue(llvmName = exprVal, _type = Struct(tinyScalaRepr = structName).some)
  }

  private def allocateStruct(exprValName: String, context: TranslationContext, structDef: StructDef): Unit = {
    val sizeValName = s"$exprValName.size"
    context.writeCodeLn(target) { s"$sizeValName = getelementptr ${structDef.llvmRepr}, ptr null, i32 1" }
    val sizeIntValName = s"$exprValName.size.i"
    context.writeCodeLn(target) { s"$sizeIntValName = ptrtoint ptr ${sizeValName} to i64" }
    context.writeCodeLn(target) { s"$exprValName = call ptr @malloc(i64 $sizeIntValName)" }
  }
}
