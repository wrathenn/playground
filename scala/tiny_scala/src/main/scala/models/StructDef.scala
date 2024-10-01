package com.wrathenn.compilers
package models

import util.Aliases.TinyScalaName

case class StructKey(
  tinyScalaName: TinyScalaName,
  generics: List[StructKey],
)

case class GenericStructDef(
  tinyScalaName: TinyScalaName,
  properties: List[GenericStructDef.Property],
)

object GenericStructDef {
  sealed trait Property { val _name: TinyScalaName; val index: Int }

  case class CompleteProperty(
    _name: TinyScalaName,
    _type: Type,
    index: Int,
  ) extends Property

  case class GenericProperty(
    _name: TinyScalaName,
    _typeAlias: TinyScalaName,
    index: Int,
  ) extends Property
}

case class StructDef(
  tinyScalaRepr: TinyScalaName,
  generics: List[StructKey],
  properties: List[StructDef.Property],
) {
  val key: StructKey = StructKey(tinyScalaRepr, generics)
  val llvmRepr: String = s"%$tinyScalaRepr"
}

object StructDef {
  case class Property(
    name: TinyScalaName,
    _type: Type,
    index: Int,
  )
}
