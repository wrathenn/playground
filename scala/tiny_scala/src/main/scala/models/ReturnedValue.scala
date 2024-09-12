package com.wrathenn.compilers
package models

/**
 * @param _type None means unknown (in case of null pointer)
 */
case class ReturnedValue(
  llvmName: String,
  _type: Type,
)
