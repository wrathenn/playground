package com.wrathenn.compilers
package translators.expr.kinds

import context.TranslationContext

import com.wrathenn.compilers.models.`type`.Type.Primitive
import com.wrathenn.compilers.models.`type`.Type.Ref.Struct
import models._
import translators.Translator
import translators.expr.ExprTranslator
import util.{TypeResolver, Util}

import com.wrathenn.compilers.models.`type`.Type
import com.wrathenn.compilers.models.struct.StructDef

import scala.jdk.CollectionConverters.CollectionHasAsScala

class PrefixExprTranslator(target: CodeTarget) extends Translator[TinyScalaParser.PrefixExprContext, ReturnedValue] {

  override def translate(node: TinyScalaParser.PrefixExprContext)(implicit context: TranslationContext): ReturnedValue = {
    if (node.simpleExpr1 != null) translateSimpleExpr1(node)
    else translateNewExpr(node)
  }

  private def translateSimpleExpr1(node: TinyScalaParser.PrefixExprContext)(implicit context: TranslationContext): ReturnedValue = {
    val res = new SimpleExpr1Translator(target).translate(node.simpleExpr1)
    if (node.opNoPrecedence == null) return res

    context.writeCode(target) { "; applying prefix op\n" }

    val prefixOp = Util.getOperator(node.opNoPrecedence.getText) match {
      case prefix: Operator.Prefix => prefix
      case _ => throw new IllegalStateException(s"Operator not a prefix operator ${node.opNoPrecedence.getText}")
    }

    val primitiveType = res._type match {
      case primitive: Type.Primitive => primitive
      case _ => throw new IllegalStateException(s"No prefix operators for non-primitive type $res._type")
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
        ReturnedValue(llvmName = tempVal, _type = primitiveType)
      }
      case Operator.Not => {
        ???
      }
    }

    context.writeCode(target) { "; applied prefix op\n" }
    return retValue
  }

  private def translateNewExpr(node: TinyScalaParser.PrefixExprContext)(implicit context: TranslationContext): ReturnedValue = {
    val newClassExpr = node.newClassExpr
    val argumentExpressions = newClassExpr.argumentExprs.exprs.expr().asScala
    val expressions = argumentExpressions.map { e => new ExprTranslator(target).translate(e) }

    val exprVal = context.genLocalVariableName(target)

    val struct = TypeResolver.getStructFromDefinition(newClassExpr.typeDefinition)
    val structDef: StructDef = struct.structDef

    if (expressions.size != structDef.properties.size) {
      throw new IllegalStateException(
        s"Provided ${expressions.size} arguments for ${structDef.tinyScalaName}, which has ${structDef.properties.size}"
      )
    }

    structDef.properties.zip(expressions).foreach { case (p, e) =>
      val pType = TypeResolver.resolveType(p._type, prevResolvedGenerics = structDef.concreteGenericTypes.toMap)
      if (pType.isInstanceOf[Type.Primitive] && e._type.isInstanceOf[Type.Ref]) {
        throw new IllegalStateException(s"Property is Primitive, expression is Ref")
      }
      else if (pType.isInstanceOf[Type.Ref] && e._type.isInstanceOf[Type.Primitive]) {
        throw new IllegalStateException(s"Property is Ref, expression is Primitive")
      }
      else if (pType != e._type && pType.isInstanceOf[Type.Ref]) {
        if (e._type != Type.Ref._Null) {
          throw new IllegalStateException(s"Type mismatch: expected ${p._type}, got ${e._type}")
        }
      }
      else if (pType != e._type) {
        throw new IllegalStateException(s"Type mismatch: expected ${p._type}, got ${e._type}")
      }
    }

    allocateStruct(exprValName = exprVal, context, structDef)
    structDef.properties.zip(expressions).zipWithIndex.foreach { case ((prop, expr), i) =>
      val propType = TypeResolver.resolveType(prop._type, prevResolvedGenerics = structDef.concreteGenericTypes.toMap)
      // get pointer to first field
      val fieldPointer = s"$exprVal.f$i.ptr"
      context.writeCodeLn(target) { s"$fieldPointer = getelementptr ${structDef.llvmName}, ptr $exprVal, i32 0, i32 $i" }
      context.writeCodeLn(target) { s"store ${propType.llvmRepr} ${expr.llvmName}, ptr $fieldPointer" }
    }
    return ReturnedValue(llvmName = exprVal, _type = struct)
  }

  private def allocateStruct(exprValName: String, context: TranslationContext, structDef: StructDef): Unit = {
    val sizeValName = s"$exprValName.size"
    context.writeCodeLn(target) { s"$sizeValName = getelementptr ${structDef.llvmName}, ptr null, i32 1" }
    val sizeIntValName = s"$exprValName.size.i"
    context.writeCodeLn(target) { s"$sizeIntValName = ptrtoint ptr ${sizeValName} to i64" }
    context.writeCodeLn(target) { s"$exprValName = call ptr @malloc(i64 $sizeIntValName)" }
  }
}
