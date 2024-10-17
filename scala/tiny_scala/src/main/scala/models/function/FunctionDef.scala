package com.wrathenn.compilers
package models.function

import models.{Type, TypeName, VariableDef}
import util.Aliases.{LlvmName, TinyScalaName}

case class FunctionDef(
  tinyScalaName: TinyScalaName,
  llvmName: LlvmName,
  concreteGenericTypes: Map[TinyScalaName, TypeName],
  params: List[VariableDef],
  returns: TypeName,
  isVarArg: Boolean,
)
