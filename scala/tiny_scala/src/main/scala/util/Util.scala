package com.wrathenn.compilers
package util

import com.wrathenn.compilers.models.Type

import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer

object Util {
  def collect[C](collector: Int => C): List[C] = {
    val result = ArrayBuffer[C]()
    var i = 0

    var next = collector(0)
    while (next != null) {
      result.addOne(next)
      i += 1
      next = collector(i)
    }

    result.toList
  }


  def collectTypeRepr(node: TinyScalaParser.Type_Context): String = {
    if (node.simpleType != null) collectStableIdRepr(node.simpleType.stableId)
    else "Array[" ++ collectTypeRepr(node.arrayType.type_) ++ "]"
  }

  def collectStableIdRepr(node: TinyScalaParser.StableIdContext): String = {
    if (node.stableId == null) node.Id.getText
    else collectStableIdRepr(node.stableId) ++ s".${node.Id.getText}"

  }
}
