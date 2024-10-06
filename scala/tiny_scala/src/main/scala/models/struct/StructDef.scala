package com.wrathenn.compilers
package models.struct

import models.{GenericKey, Type}
import util.Aliases.TinyScalaName

import com.wrathenn.compilers.models


case class StructDef(
  tinyScalaName: TinyScalaName,
  generics: List[GenericKey],
  properties: List[StructDef.Property],
) {
  val key: GenericKey = models.GenericKey(tinyScalaName, generics)
  val llvmRepr: String = s"%$tinyScalaName"
}

object StructDef {
  case class Property(
    name: TinyScalaName,
    _type: Type,
    index: Int,
  )
}
