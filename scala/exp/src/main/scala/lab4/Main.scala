package com.wrathenn.exp
package lab4

import lab4.algs.{Dbg, Resolution}
import lab4.model.Expr.Atom.StringInterpolation
import lab4.model.Expr._

import com.wrathenn.exp.lab4.model.Expr

object Main extends App {

//  val data = List(
//    ~~(const"A") | Predicate(id = "P", args = List(atomVar"a")),
//    const"A",
//  )
//  val target = Predicate(id = "P", args = List(atomVar"x"))
//
//  val data = List(
//    ( Predicate(id = "Яблоко", args = List(atomVar"x")) & Predicate(id = "Цвет", args = List(atomVar"x", atomC"Red")) ) -> Predicate(id = "Вкусный", args = List(atomVar"x")),
//    Predicate(id = "Яблоко", args = List(atomC"A")),
//    Predicate(id = "Цвет", args = List(atomC"A", atomC"Red")),
//  )
//  val target = Predicate(id = "Вкусный", args = List(atomC"A"))

  val data = List(
    Predicate("S", List(atomVar"x1")) | Predicate(id = "M", List(atomVar"x1")),
    ~~(Predicate("M", List(atomVar"x2"))) | ~~(Predicate("L", List(atomVar"x2", atomC"дождь"))),
    ~~(Predicate("S", List(atomVar"x3"))) | Predicate("L", List(atomVar"x3", atomC"снег")),
    ~~(Predicate("L", List(atomC"Элен", atomVar"y1"))) | ~~(Predicate("L", List(atomC"Тони", atomVar"y1"))),
    Predicate("L", List(atomC"Элен", atomVar"y2")) | Predicate("L", List(atomC"Тони", atomVar"y2")),
    Predicate("L", List(atomC"Тони", atomC"дождь")),
    Predicate("L", List(atomC"Тони", atomC"снег")),
  )
  val target = ~~(
    ~~(Predicate("M", List(atomVar"x4"))) |
      Predicate("S", List(atomVar"v4"))
  )
  val resolutionResult = Resolution.resolve(data, target)

  if (resolutionResult) {
    Dbg.debugLn("Цель доказана")
  } else {
    Dbg.debugLn("Не удалось доказать цель")
  }
}
