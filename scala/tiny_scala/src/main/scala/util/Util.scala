package com.wrathenn.compilers
package util

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
}
