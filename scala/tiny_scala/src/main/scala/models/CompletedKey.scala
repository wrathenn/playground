package com.wrathenn.compilers
package models

import util.Aliases.TinyScalaName

case class CompletedKey(
  tinyScalaName: TinyScalaName,
  concreteGenericTypes: Map[TinyScalaName, Type],
)
