package com.wrathenn.exp
package lab4.algs

object Dbg {
  private var indent: Int = 0

  def debugLn(s: String) = println(s"${" ".repeat(indent * 2)}$s")
  def ++(): Unit = { indent += 1 }
  def --(): Unit = { indent -= 1 }

  def indented[A](f: => A): A = {
    ++()
    val res = f
    --()
    res
  }
}
