package com.wrathenn.compilers
package models.function

import models.{GenericKey, Type, VariableDef}
import util.Aliases.{LlvmName, TinyScalaName}

case class FunctionDef(
  tinyScalaName: TinyScalaName,
  llvmName: LlvmName,
  generics: List[GenericKey],
  params: List[VariableDef],
  returns: Type,
  isVarArg: Boolean,
) {
  val key: GenericKey = GenericKey(tinyScalaName = tinyScalaName, generics = generics)
}
