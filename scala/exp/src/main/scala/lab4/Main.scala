package com.wrathenn.exp
package lab4

import lab4.algs.{Dbg, Resolution}
import lab4.model.Expr.Term.StringInterpolation
import lab4.model.Expr._

object Main extends App {

  val data = List(
    ~~(const"A") | Predicate(id = "P", args = List(termVar"a")),
    const"A",
  )
  val target = Predicate(id = "P", args = List(termVar"x"))
  val resolutionResult = Resolution.resolve(data, target)

  if (resolutionResult) {
    Dbg.debugLn("Цель доказана")
  } else {
    Dbg.debugLn("Не удалось доказать цель")
  }
}
