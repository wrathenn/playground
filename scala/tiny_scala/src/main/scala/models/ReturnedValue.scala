package com.wrathenn.compilers
package models

import models.`type`.Type

case class ReturnedValue(
  llvmName: String,
  _type: Type,
)
