package com.wrathenn.compilers
package regex.models

import regex.models.Tree.{Tree0, Tree1, Tree2}

import cats.Show

trait Tree[+A]
object Tree {
  trait Tree0[A] extends Tree[A]
  trait Tree1[A] extends Tree[A] { val on: Tree[A] }
  trait Tree2[A] extends Tree[A] { val left: Tree[A]; val right: Tree[A] }
}

sealed trait RegexTree extends Tree[RegexTree]

object RegexTree {
  case class Star(on: RegexTree) extends RegexTree with Tree1[RegexTree]
  case class Or(left: RegexTree, right: RegexTree) extends RegexTree with Tree2[RegexTree]
  case class Concatenation(left: RegexTree, right: RegexTree) extends RegexTree with Tree2[RegexTree]
  case class Terminal(symbol: Char) extends RegexTree with Tree0[RegexTree]
  object TerminalEnd extends Terminal('#') with Tree0[RegexTree]

  implicit val regexTreeShow: Show[Tree[RegexTree]] = {
    case RegexTree.Star(_) => "(*)━"
    case RegexTree.Or(_, _) => "(|)━"
    case RegexTree.Concatenation(_, _) => "(o)"
    case _: RegexTree.TerminalEnd.type => "CUSTOM_ENDING"
    case RegexTree.Terminal(symbol) => s"\"$symbol\""
  }
}
