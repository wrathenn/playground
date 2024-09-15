package com.wrathenn.compilers
package translators.expr.kinds

import context.TranslationContext
import models.{CodeTarget, ReturnedValue, Type}
import translators.Translator
import translators.expr.ExprTranslator

import cats.syntax.all._
import com.wrathenn.compilers.models.Type.Primitive._Unit

//class IfExprTranslator(target: CodeTarget) extends Translator[TinyScalaParser.ExprContext, ReturnedValue] {
//  private def translateOnlyThen(
//    context: TranslationContext,
//    conditionResult: ReturnedValue,
//    thenExpr: TinyScalaParser.ExprContext,
//  ): ReturnedValue = {
//    val thenLabel = conditionResult.llvmName.stripPrefix("%") ++ ".then_branch"
//    val ifRetLabel = conditionResult.llvmName.stripPrefix("%") ++ ".if_ret_branch"
//    context.writeCodeLn(target) { s"br i1 ${conditionResult.llvmName}, label $thenLabel, label $ifRetLabel" }
//
//  }
//
//  override def translate(context: TranslationContext, node: TinyScalaParser.ExprContext): ReturnedValue = {
//    val conditionExpr = node.expr(0)
//    val thenExpr = node.expr(1)
//    val elseExpr = if (node.expr(2) != null) node.expr(2).some else None
//
//    val conditionResult = new ExprTranslator(target).translate(context, conditionExpr)
//    if (conditionResult._type.isEmpty || conditionResult._type.get != Type.Primitive._Boolean) {
//      throw new IllegalStateException("Condition type should be boolean")
//    }
//    // todo
//
//    val thenLabel = conditionResult.llvmName.stripPrefix("%") ++ ".then_branch"
//    val elseLabel = conditionResult.llvmName.stripPrefix("%") ++ ".else_branch"
//    val ifRetLabel = conditionResult.llvmName.stripPrefix("%") ++ ".if_ret_branch"
//
//    context.writeCodeLn(target) { s"br i1 ${conditionResult.llvmName}, label %$thenLabel, label %$elseLabel" }
//
//    // will need to insert code before generated in then/else result
//    val thenContext = context.copy(
//      initCode = new StringBuilder(),
//      localCode = new StringBuilder(),
//      globalCode = new StringBuilder(),
//      mainCode = new StringBuilder(),
//    )
//    thenContext.createNewContext(defining = None)
//
//    thenContext.writeCodeLn(target) { s"$thenLabel:" }
//    val thenResult = new ExprTranslator(target).translate(thenContext, thenExpr)
//    thenContext.writeCodeLn(target) { s"br label %$ifRetLabel" }
//
//    // will need to insert code before generated in then/else result
//    val elseContext = context.copy(
//      initCode = new StringBuilder(),
//      localCode = new StringBuilder(),
//      globalCode = new StringBuilder(),
//      mainCode = new StringBuilder(),
//    )
//    elseContext.createNewContext(defining = None)
//
//    elseContext.writeCodeLn(target) { s"$elseLabel:" }
//    val elseResult = elseExpr.map { elseE =>
//      new ExprTranslator(target).translate(elseContext, elseE)
//    }.getOrElse(ReturnedValue(llvmName = "UNIT", _type = _Unit))
//
//    val tempVal = context.genLocalVariableName(target)
//    /**
//     * then=?,else=? => Any
//     */
//    val commonType = thenResult._type match {
//      case thenPrimitive: Type.Primitive => elseResult._type match {
//        case elsePrimitive: Type.Primitive => {
//          if (thenPrimitive != elsePrimitive) {
//            throw new IllegalArgumentException(s"Boxing primitives is not allowed. Then type is $thenPrimitive, else type is $elsePrimitive")
//          }
//          thenPrimitive
//        }
//        case elseRef: Type.Ref => {
//          throw new IllegalArgumentException(s"Mixing primitives with refs is not allowed. Then type is $thenPrimitive, else type is $elseRef")
//        }
//      }
//      case thenRef: Type.Ref => elseResult._type match {
//        case elsePrimitive: Type.Primitive => {
//          throw new IllegalArgumentException(s"Mixing primitives with refs is not allowed. Then type is $thenRef, else type is $elsePrimitive")
//        }
//        case elseRef: Type.Ref => {
//          if ()
//        }
//      }
//    }
//
//  }
//}

object Test extends App {
  val a = new StringBuilder()
  val b = new StringBuilder()
  a.append(b)
  a.append("should be last\n")
  b.append("should be first\n")
  a.append(b)
  println(a)
}
