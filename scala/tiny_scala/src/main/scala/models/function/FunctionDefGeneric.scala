package com.wrathenn.compilers
package models.function

import models.{TypeName, GenericProperty, Type, VariableDef}
import util.Aliases.TinyScalaName

case class FunctionDefGeneric(
  tinyScalaName: TinyScalaName,
  typeParamAliases: List[TinyScalaName],
  params: List[GenericProperty],
  returns: TypeName,
  expression: TinyScalaParser.ExprContext,
)
