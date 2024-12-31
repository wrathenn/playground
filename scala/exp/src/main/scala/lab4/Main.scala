package com.wrathenn.exp
package lab4

import lab4.algs.{Dbg, Resolution}
import lab4.model.Expr.Term.StringInterpolation
import lab4.model.Expr._

import com.wrathenn.exp.lab4.model.Expr

object Main extends App {

//  val data = List(
//    ~~(const"A") | Predicate(id = "P", args = List(termVar"a")),
//    const"A",
//  )
//  val target = Predicate(id = "P", args = List(termVar"x"))

//  val data = List(
//    Predicate(id = "Apple", args = List(termVar"x")),
//    Predicate(id = "OfColor", args = List(termVar"x", termVar"y")),
//    Predicate(id = "Tasty", args = List(termVar"x")),
//    ( Predicate(id = "Apple", args = List(termVar"x")) & Predicate(id = "OfColor", args = List(termVar"x", termC"Red")) ) -> Predicate(id = "Tasty", args = List(termVar"x")),
//    Predicate(id = "Apple", args = List(termC"A")),
//    Predicate(id = "OfColor", args = List(termC"A", termC"Red")),
//  )
//  val target = Predicate(id = "Tasty", args = List(termC"A"))

//  val data = List(
//    ( Predicate(id = "Яблоко", args = List(termVar"x")) & Predicate(id = "Цвет", args = List(termVar"x", termC"Red")) ) -> Predicate(id = "Вкусный", args = List(termVar"x")),
//    Predicate(id = "Яблоко", args = List(termC"A")),
//    Predicate(id = "Цвет", args = List(termC"A", termC"Red")),
//  )
//  val target = Predicate(id = "Вкусный", args = List(termC"A"))

  val data = List(
    Predicate("S", List(termVar"x1")) | Predicate(id = "M", List(termVar"x1")),
    ~~(Predicate("M", List(termVar"x2"))) | ~~(Predicate("L", List(termVar"x2", termC"дождь"))),
    ~~(Predicate("S", List(termVar"x3"))) | Predicate("L", List(termVar"x3", termC"снег")),
    ~~(Predicate("L", List(termC"Элен", termVar"y1"))) | ~~(Predicate("L", List(termC"Тони", termVar"y1"))),
    Predicate("L", List(termC"Элен", termVar"y2")) | Predicate("L", List(termC"Тони", termVar"y2")),
    Predicate("L", List(termC"Тони", termC"дождь")),
    Predicate("L", List(termC"Тони", termC"снег")),
  )
  val target = ~~(
    ~~(Predicate("M", List(termVar"x4"))) |
      Predicate("S", List(termVar"v4"))
  )
  val resolutionResult = Resolution.resolve(data, target)

  if (resolutionResult) {
    Dbg.debugLn("Цель доказана")
  } else {
    Dbg.debugLn("Не удалось доказать цель")
  }
}
