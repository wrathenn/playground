package Ext

import scala.language.reflectiveCalls

object Files {
  def using[A <: {def close(): Unit}, B](r: A)(f: A => B): B =
    try {
      f(r)
    }
    finally {
      r.close()
    }
}

trait Fileable[A] {
  def read(filename: String): Option[A]

  def write(value: A, filename: String): Unit
}

object FileableSyntax {
  implicit def fromFile[A](filename: String)(implicit fromFileInstance: Fileable[A]): Option[A] = {
    fromFileInstance.read(filename)
  }

  implicit class ToFileOps[A](value: A) {
    def toFile(filename: String)(implicit fileableInstance: Fileable[A]): Unit = {
      fileableInstance.write(value, filename)
    }
  }
}
