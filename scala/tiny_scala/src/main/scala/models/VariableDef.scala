package com.wrathenn.compilers
package models

case class VariableDef(
  tinyScalaRepr: String,
  llvmNameRepr: String,
  _type: Type,
  decl: VariableDecl,
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
