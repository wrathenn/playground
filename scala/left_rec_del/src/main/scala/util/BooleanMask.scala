package com.wrathenn.compilers
package util

object Mask {
  type BooleanMask = List[Boolean]
}

object BooleanMaskExt {
  import util.Mask.BooleanMask

  implicit class BooleanMaskSyntax(val mask: BooleanMask) extends AnyVal {

    def incremented: BooleanMask = {
      def _rec(_mask: BooleanMask): (BooleanMask, Boolean) = {
        if (_mask.size == 1) {
          return List(!_mask.head) -> _mask.head
        }
        val (tail, shouldIncrement) = _rec(_mask.tail)

        if (shouldIncrement) {
          return (!_mask.head +: tail) -> _mask.head
        } else {
          return (_mask.head +: tail) -> false
        }
      }

      _rec(mask)._1
    }
  }
}
