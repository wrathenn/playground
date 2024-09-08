package com.wrathenn.compilers
package models

case class StructDef(
  tinyScalaRepr: String,
  properties: List[StructDef.Property],
) {
  val llvmRepr = s"%$tinyScalaRepr"
}

object StructDef {
  case class Property(
    name: String,
    _type: Type,
    index: Int,
  )
}
