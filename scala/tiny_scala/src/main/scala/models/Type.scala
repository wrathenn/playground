package com.wrathenn.compilers
package models

import models.Type.Primitive._Unit

sealed class Type(
  val tinyScalaRepr: String,
  val llvmRepr: String
)

object Type {
  sealed class Primitive(override val tinyScalaRepr: String, override val llvmRepr: String)
    extends Type(tinyScalaRepr, llvmRepr)
  object Primitive {
    case object _Unit extends Primitive("Unit", "void")
    case object _Boolean extends Primitive("Boolean", "i1")
    case object _Chr extends Primitive("Chr", "i8")
    case object _Int extends Primitive("Int", "i32")
    case object _Long extends Primitive("Long", "i64")
    case object _Float extends Primitive("Float", "float")
    case object _Double extends Primitive("Double", "double")
  }

  sealed class Ref(override val tinyScalaRepr: String)
    extends Type(tinyScalaRepr = tinyScalaRepr, llvmRepr = "ptr")
  object Ref {
    // tinyScalaRepr of these should be irrelevant
    case object _BooleanBox extends Ref("b_Boolean")
    case object _ChrBox extends Ref("b_Chr")
    case object _IntBox extends Ref("b_Int")
    case object _LongBox extends Ref("b_Long")
    case object _FloatBox extends Ref("b_Float")
    case object _DoubleBox extends Ref("b_Double")

    case object _Any extends Ref("Any")
    case object _Null extends Ref("Null")
    case object _String extends Ref("String")
    case class Array(override val tinyScalaRepr: String, _type: Type)
      extends Ref(tinyScalaRepr)
    case class Struct(override val tinyScalaRepr: String)
      extends Ref(tinyScalaRepr)
  }

  def fromRepr(str: String): Type = {
    str match {
      case Primitive._Int.tinyScalaRepr => Primitive._Int
      case Primitive._Long.tinyScalaRepr => Primitive._Long
      case Primitive._Float.tinyScalaRepr => Primitive._Float
      case Primitive._Double.tinyScalaRepr => Primitive._Double
      case Primitive._Chr.tinyScalaRepr => Primitive._Chr
      case Primitive._Boolean.tinyScalaRepr => Primitive._Boolean
      case Primitive._Unit.tinyScalaRepr => _Unit
      case Ref._String.tinyScalaRepr => Ref._String
      case str if str.matches("Array\\[(.*)]") => {
        val nestedTypeRepr = "Array\\[(.*)]".r.findFirstMatchIn(str).get.group(1)
        val nestedType = fromRepr(nestedTypeRepr)
        Ref.Array(tinyScalaRepr = str, _type = nestedType)
      }
      case _ => Ref.Struct(str)
    }
  }
}
