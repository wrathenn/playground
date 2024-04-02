package com.wrathenn.compilers
package regex.dfa

import regex.models.Tree
import regex.models.Tree.{Tree0, Tree1, Tree2}

import cats.Show

import scala.collection.mutable

sealed trait RegexTreeF extends Tree[RegexTreeF] {
  def nullable: Boolean
  def firstPos: Set[Int]
  def lastPos: Set[Int]
  // will be calculated later
  val followPos: mutable.Set[Int] = mutable.Set()
}

case class StarF(on: RegexTreeF) extends RegexTreeF with Tree1[RegexTreeF] {
  override val nullable: Boolean = true
  override lazy val firstPos: Set[Int] = on.firstPos
  override lazy val lastPos: Set[Int] = on.lastPos
}

case class OrF(left: RegexTreeF, right: RegexTreeF) extends RegexTreeF with Tree2[RegexTreeF] {
  override val nullable: Boolean = left.nullable || right.nullable
  override lazy val firstPos: Set[Int] = left.firstPos | right.firstPos
  override lazy val lastPos: Set[Int] = left.lastPos | right.lastPos
}

case class ConcatenationF(left: RegexTreeF, right: RegexTreeF) extends RegexTreeF with Tree2[RegexTreeF] {
  override val nullable: Boolean = left.nullable && right.nullable
  override lazy val firstPos: Set[Int] = if (left.nullable) left.firstPos | right.firstPos else left.firstPos
  override lazy val lastPos: Set[Int] = if (right.nullable) left.firstPos | right.firstPos else right.firstPos
}

case class TerminalF(number: Int, symbol: Char) extends RegexTreeF with Tree0[RegexTreeF] {
  override val nullable: Boolean = false
  override val firstPos: Set[Int] = Set(number)
  override val lastPos: Set[Int] = Set(number)
}

class TerminalEndF(override val number: Int) extends TerminalF(number, '#')

object RegexTreeF {
  private def functionsRepr(f: RegexTreeF) =
    s"[n=${if (f.nullable) "T" else "F"},fp=${f.firstPos.mkString("{", ",", "}")},lp=${f.lastPos.mkString("{", ",", "}")},folp=${f.followPos.mkString("{", ",", "}")}]"
  implicit val terminalEndFShow: Show[Tree[RegexTreeF]] = {
    case it: StarF => s"(*)${functionsRepr(it)}━"
    case it: OrF => s"(|)${functionsRepr(it)}"
    case it: ConcatenationF => s"(o)${functionsRepr(it)}"
    case it: TerminalEndF => s"CUSTOM_ENDING(№ ${it.number})${functionsRepr(it)}"
    case it: TerminalF => s"\"${it.symbol}\"(№ ${it.number})${functionsRepr(it)}"
  }
}
