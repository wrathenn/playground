package com.wrathenn.compilers
package models

import com.wrathenn.compilers.util.Aliases.{LlvmName, TinyScalaName}

case class FunctionDef(
  tinyScalaName: TinyScalaName,
  llvmName: LlvmName,
  params: List[FunctionDef.Param],
  returns: Option[Type],
)

object FunctionDef {
  case class Param(
    tinyScalaName: TinyScalaName,
    llvmName: LlvmName,
    _type: Type,
  )
}
