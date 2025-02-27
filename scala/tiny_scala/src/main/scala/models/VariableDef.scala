package com.wrathenn.compilers
package models

import util.Aliases.{LlvmName, TinyScalaName}

import com.wrathenn.compilers.models.`type`.Type

case class VariableDef(
  tinyScalaName: TinyScalaName,
  llvmNameRepr: LlvmName,
  _type: Type,
  decl: VariableDecl,
  isFunctionParam: Boolean, // crutch
)

sealed trait VariableDecl
object VariableDecl {
  case object VAL extends VariableDecl
  case object VAR extends VariableDecl
}
