package com.wrathenn.compilers
package models

import util.Aliases.{LlvmName, TinyScalaName}

case class VariableDef(
  tinyScalaRepr: TinyScalaName,
  llvmNameRepr: LlvmName,
  _type: Type,
  decl: VariableDecl,
  isFunctionParam: Boolean, // crutch
)

//case class LocalVariableDef(
//  tinyScalaRepr: String,
//  _type: Type,
//  decl: VariableDecl,
//  var lastValueIndex: Int,
//) {
//  def llvmNameRepr = s"%${tinyScalaRepr}.$lastValueIndex"
//}

sealed trait VariableDecl
object VariableDecl {
  case object VAL extends VariableDecl
  case object VAR extends VariableDecl
}
