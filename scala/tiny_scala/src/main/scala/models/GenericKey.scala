package com.wrathenn.compilers
package models

import util.Aliases.TinyScalaName

case class GenericKey(
  tinyScalaName: TinyScalaName,
  generics: List[GenericKey],
)

case class GenericProperty(
  name: TinyScalaName,
  genericKey: GenericKey,
)
