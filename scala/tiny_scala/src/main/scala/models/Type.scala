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
    // case object _UnitBox extends Ref("b_Unit")
    // case object _BooleanBox extends Ref("b_Boolean")
    // case object _ChrBox extends Ref("b_Chr")
    // case object _IntBox extends Ref("b_Int")
    // case object _LongBox extends Ref("b_Long")
    // case object _FloatBox extends Ref("b_Float")
    // case object _DoubleBox extends Ref("b_Double")

    case object _Any extends Ref("Any")
    case object _Null extends Ref("Null")
    case object _String extends Ref("String")
    case class Struct(override val tinyScalaRepr: String, val structDef: StructDef)
      extends Ref(tinyScalaRepr)
  }

  case object _Nothing extends Type(tinyScalaRepr = "Nothing", llvmRepr = "nothing_type_error_if_in_llvm")
}
