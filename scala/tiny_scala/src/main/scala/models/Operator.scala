package com.wrathenn.compilers
package models

sealed class Operator(val repr: String)
object Operator {
  sealed trait Prefix
  sealed trait Infix
  sealed trait BooleanOp

  case object Not extends Operator("!") with Prefix
  case object Plus extends Operator("+") with Prefix with Infix
  case object Minus extends Operator("-") with Prefix with Infix
  case object Mul extends Operator("*") with Infix
  case object Div extends Operator("/") with Infix
  case object Mod extends Operator("%") with Infix
  case object And extends Operator("&&") with Infix with BooleanOp
  case object Or extends Operator("||") with Infix with BooleanOp
  case object Less extends Operator("<") with Infix with BooleanOp
  case object LessOrEq extends Operator("<=") with Infix with BooleanOp
  case object Greater extends Operator(">") with Infix with BooleanOp
  case object GreaterOrEq extends Operator(">=") with Infix with BooleanOp
  case object Equals extends Operator("==") with Infix with BooleanOp
}

