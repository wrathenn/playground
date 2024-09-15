package com.wrathenn.compilers
package models

import util.Aliases.TinyScalaName

case class StructDef(
  tinyScalaRepr: TinyScalaName,
  properties: List[StructDef.Property],
) {
  val llvmRepr = s"%$tinyScalaRepr"
}

object StructDef {
  case class Property(
    name: TinyScalaName,
    _type: Type,
    index: Int,
  )
}
