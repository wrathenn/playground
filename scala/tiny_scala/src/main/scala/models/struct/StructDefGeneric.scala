package com.wrathenn.compilers
package models.struct

import util.Aliases.TinyScalaName
import com.wrathenn.compilers.models.`type`.GenericProperty

case class StructDefGeneric(
  tinyScalaName: TinyScalaName,
  typeParamAliases: List[TinyScalaName],
  properties: List[GenericProperty],
)
