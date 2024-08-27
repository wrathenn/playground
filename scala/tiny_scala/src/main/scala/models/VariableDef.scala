package com.wrathenn.compilers
package models

case class VariableDef(
  tinyScalaRepr: String,
  llvmNameRepr: String,
  _type: Type,
  decl: VariableDecl,
)

sealed trait VariableDecl
object VariableDecl {
  case object VAL extends VariableDecl
  case object VAR extends VariableDecl
}
