package com.wrathenn.compilers
package models.function

import models.VariableDef
import models.`type`.{Type, TypeName}
import util.Aliases.{LlvmName, TinyScalaName}

case class FunctionDef(
  tinyScalaName: TinyScalaName,
  llvmName: LlvmName,
  concreteGenericTypes: Map[TinyScalaName, TypeName],
  params: List[VariableDef],
  returns: Type,
  isVarArg: Boolean,
) {
  val key: FunctionDef.Key = FunctionDef.Key(
    tinyScalaName = tinyScalaName,
    params = params,
    returnsType = returns,
  )
}

object FunctionDef {
  case class Key(
    tinyScalaName: TinyScalaName,
    params: List[VariableDef],
    returnsType: Type,
  ) {
    override def toString: String = s"fkey $tinyScalaName(${params.map(_._type).mkString(", ")}: $returnsType "
  }
}
