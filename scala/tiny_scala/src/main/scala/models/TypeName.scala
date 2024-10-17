package com.wrathenn.compilers
package models

import util.Aliases.TinyScalaName

case class TypeName(
  tinyScalaName: TinyScalaName,
  generics: List[TypeName],
)

case class GenericProperty(
  name: TinyScalaName,
  genericKey: TypeName,
)
