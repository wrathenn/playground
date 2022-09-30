package Ext

// Type class to reverse array by index
trait ReversibleByIndex[T] {
  def reverseByIndex(v: T): T
}

object ReversibleByIndex {
  implicit val CharArrayReversibleByIndex: ReversibleByIndex[Array[Char]] = {
    value => value.zipWithIndex.foldLeft(Array.ofDim[Char](value.length)) {(res: Array[Char], ei: (Char, Int)) =>
      res(ei._1) = ei._2.toChar
      res
    }
  }

  implicit val ByteArrayReversibleByIndex: ReversibleByIndex[Array[Byte]] = {
    value => value.zipWithIndex.foldLeft(Array.ofDim[Byte](value.length)) {(res: Array[Byte], ei: (Byte, Int)) =>
      res(ei._1 - Byte.MinValue) = (ei._2 + Byte.MinValue).toByte
      res
    }
  }

  implicit class _ReversibleByIndex[A](v: A) {
    def reversedByIndex(implicit reverser: ReversibleByIndex[A]): A =
      reverser.reverseByIndex(v)
  }
}
