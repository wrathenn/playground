package com.wrathenn.compilers
package models.`type`

import models.struct.StructDef

sealed class Type(
  val tinyScalaName: String,
  val llvmRepr: String
)

object Type {
  sealed class Primitive(override val tinyScalaName: String, override val llvmRepr: String)
    extends Type(tinyScalaName, llvmRepr)
  object Primitive {
    case object _Unit extends Primitive("Unit", "void")
    case object _Boolean extends Primitive("Boolean", "i1")
    case object _Chr extends Primitive("Chr", "i8")
    case object _Int extends Primitive("Int", "i32")
    case object _Long extends Primitive("Long", "i64")
    case object _Float extends Primitive("Float", "float")
    case object _Double extends Primitive("Double", "double")
  }

  sealed class Ref(override val tinyScalaName: String)
    extends Type(tinyScalaName = tinyScalaName, llvmRepr = "ptr")
  object Ref {
    case object _Any extends Ref("Any")
    case object _Null extends Ref("Null")
    case object _String extends Ref("String")
    case class Struct(override val tinyScalaName: String, val structDef: StructDef)
      extends Ref(tinyScalaName)
  }

  case object _Nothing extends Type(tinyScalaName = "Nothing", llvmRepr = "nothing_type_error_if_in_llvm")
}
