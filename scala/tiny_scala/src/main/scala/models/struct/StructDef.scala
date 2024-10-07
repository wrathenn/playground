package com.wrathenn.compilers
package models.struct

import models.{CompletedKey, Type}
import util.Aliases.{LlvmName, TinyScalaName}


case class StructDef(
  tinyScalaName: TinyScalaName,
  llvmName: LlvmName,
  concreteGenericTypes: Map[TinyScalaName, Type],
  properties: List[StructDef.Property],
) {
  val key: CompletedKey = CompletedKey(tinyScalaName, concreteGenericTypes)
}

object StructDef {
  case class Property(
    name: TinyScalaName,
    _type: Type,
    index: Int,
  )
}
