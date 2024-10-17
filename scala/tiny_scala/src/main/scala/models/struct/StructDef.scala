package com.wrathenn.compilers
package models.struct

import models.TypeName
import util.Aliases.{LlvmName, TinyScalaName}


case class StructDef(
  tinyScalaName: TinyScalaName,
  llvmName: LlvmName,
  concreteGenericTypes: List[(TinyScalaName, TypeName)],
  properties: List[StructDef.Property],
) {
  val typeName: TypeName = TypeName(tinyScalaName = tinyScalaName, generics = concreteGenericTypes.map(_._2))
}

object StructDef {
  case class Property(
    name: TinyScalaName,
    _type: TypeName,
    index: Int,
  )
}
