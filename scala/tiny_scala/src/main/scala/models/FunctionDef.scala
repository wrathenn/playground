package com.wrathenn.compilers
package models

case class FunctionDef(
  tinyScalaName: String,
  llvmName: String,
  params: List[FunctionDef.Param],
  returns: Option[Type],
)

object FunctionDef {
  case class Param(
    name: String,
    _type: Type,
  )
}
