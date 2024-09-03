package com.wrathenn.compilers
package models

sealed trait Literal

object Literal {
  case class Value(
    llvmValue: String,
    _type: Type,
  ) extends Literal

  case object Null extends Literal
}

