package com.wrathenn.compilers
package models

case class ReturnedValue(
  llvmName: String,
  _type: Option[Type],
)
