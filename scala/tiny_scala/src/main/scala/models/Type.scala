package com.wrathenn.compilers
package models

import scala.util.matching.Regex

/**
 * Array[Int]
 * %Array.Int = type {
 *   i32, ; len
 *   i32* ; content
 * }
 *
 * Str == i8*
 *
 * Array[Str]
 * %Array.Str = type {
 *   i32, ; len
 *   i8** ; content
 * }
 *
 * Array[MyClass]
 * %Array.MyClass = type {
 *   i32, ; len
 *   %MyClass** ; content
 * }
 */
sealed class Type(
  val tinyScalaRepr: String,
  val llvmRepr: String
)

object Type {
  sealed class Primitive(override val tinyScalaRepr: String, override val llvmRepr: String) extends Type(tinyScalaRepr, llvmRepr)
  object Primitive {
    case object _Int extends Primitive("Int", "i32")
    case object _Long extends Primitive("Long", "i64")
    case object _Float extends Primitive("Float", "float")
    case object _Double extends Primitive("Double", "double")
    case object _Chr extends Primitive("Chr", "i8")
  }

  case object _String extends Primitive("String", "ptr")
  case class Array(override val tinyScalaRepr: String, _type: Type)
    extends Type(tinyScalaRepr, "ptr")
  case class Struct(override val tinyScalaRepr: String)
    extends Type(tinyScalaRepr, "ptr")
  case object _Unit extends Type("Unit", "void")

  def fromRepr(str: String): Type = {
    str match {
      case Primitive._Int.tinyScalaRepr => Primitive._Int
      case Primitive._Long.tinyScalaRepr => Primitive._Long
      case Primitive._Float.tinyScalaRepr => Primitive._Float
      case Primitive._Double.tinyScalaRepr => Primitive._Double
      case Primitive._Chr.tinyScalaRepr => Primitive._Chr
      case _String.tinyScalaRepr => _String
      case _Unit.tinyScalaRepr => _Unit
      case str if str.matches("Array\\[(.*)]") => {
        val nestedTypeRepr = "Array\\[(.*)]".r.findFirstMatchIn(str).get.group(1)
        val nestedType = fromRepr(nestedTypeRepr)
        Type.Array(tinyScalaRepr = str, _type = nestedType)
      }
      case _ => Type.Struct(str)
    }
  }
}
