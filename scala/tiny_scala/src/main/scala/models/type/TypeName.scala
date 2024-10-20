package com.wrathenn.compilers
package models.`type`

import util.Aliases.TinyScalaName

/**
 * Can be both generic and not, depends on place in code :(
 */
case class TypeName(
  tinyScalaName: TinyScalaName,
  generics: List[TypeName],
) {
  override def toString: String = s"`$tinyScalaName${ if (generics.nonEmpty) "[" ++ generics.mkString(", ") ++ "]" else ""}"
}

case class GenericProperty(
  name: TinyScalaName,
  typeName: TypeName,
)
