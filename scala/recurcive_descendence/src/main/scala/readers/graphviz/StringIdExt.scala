package com.wrathenn.compilers
package readers.graphviz

object StringIdExt {
  implicit class IdInc(val id: String) extends AnyVal {
    def inc: String = {
      if (id.head.toInt >= 'Z'.toInt) return 'A' +: id
      (id.head.toInt + 1).toChar +: id.tail
    }
  }
}
