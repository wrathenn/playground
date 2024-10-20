package com.wrathenn.compilers
package models.function

import models.`type`.{GenericProperty, TypeName}
import util.Aliases.TinyScalaName

case class FunctionDefGeneric(
  tinyScalaName: TinyScalaName,
  typeParamAliases: List[TinyScalaName],
  params: List[GenericProperty],
  returns: TypeName,
  expression: TinyScalaParser.ExprContext,
)
