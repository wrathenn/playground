package com.wrathenn.compilers
package models

import util.Aliases.{LlvmName, TinyScalaName}

case class FunctionDef(
  tinyScalaName: TinyScalaName,
  llvmName: LlvmName,
  params: List[VariableDef],
  returns: Type,
  isVarArg: Boolean,
)
