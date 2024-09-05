package com.wrathenn.compilers
package models

sealed trait CodeTarget
object CodeTarget {
  case object LOCAL extends CodeTarget
  case object INIT extends CodeTarget
  case object GLOBAL extends CodeTarget
  case object Main extends CodeTarget
}