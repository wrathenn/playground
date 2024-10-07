package com.wrathenn.compilers
package models.function

import models.{CompletedKey, Type, VariableDef}
import util.Aliases.{LlvmName, TinyScalaName}

case class FunctionDef(
  tinyScalaName: TinyScalaName,
  llvmName: LlvmName,
  concreteGenericTypes: Map[TinyScalaName, Type],
  params: List[VariableDef],
  returns: Type,
  isVarArg: Boolean,
) {
  val key: CompletedKey = CompletedKey(tinyScalaName = tinyScalaName, concreteGenericTypes = concreteGenericTypes)
}
