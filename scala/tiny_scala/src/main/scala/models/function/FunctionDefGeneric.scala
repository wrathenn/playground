package com.wrathenn.compilers
package models.function

import models.{GenericKey, GenericProperty, Type, VariableDef}
import util.Aliases.TinyScalaName

case class FunctionDefGeneric(
  tinyScalaName: TinyScalaName,
  typeParamAliases: List[TinyScalaName],
  params: List[GenericProperty],
  returns: GenericKey,
  expression: TinyScalaParser.ExprContext,
)
