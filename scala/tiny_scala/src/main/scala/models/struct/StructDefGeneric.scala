package com.wrathenn.compilers
package models.struct

import models.GenericProperty
import util.Aliases.TinyScalaName

case class StructDefGeneric(
  tinyScalaName: TinyScalaName,
  typeParamAliases: List[TinyScalaName],
  properties: List[GenericProperty],
)
