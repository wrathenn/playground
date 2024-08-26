package com.wrathenn.compilers
package models

case class StructDef(
  tinyScalaRepr: String,
  properties: List[StructDef.Property],
) {
  def llvm: String = {
    val types = properties.map { prop =>
      (prop._type match {
        case primitive: Type.Primitive => primitive.llvmRepr
        case _ => s"${prop._type.llvmRepr}*"
      }) -> prop.name
    }.reverse

    val last = types.head
    val rest = types.tail

    val lastRepr = s"  ${last._1} ; ${last._2}\n"
    val restReprs = rest.map { case (r, n) => s"  ${r}, ; ${n}\n" }

    s"%$tinyScalaRepr = type {\n" ++
      restReprs.reverse.mkString("") ++
      lastRepr ++
      "}\n"
  }
}

object StructDef {
  case class Property(
    name: String,
    _type: Type,
    index: Int,
  )
}
