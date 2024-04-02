package com.wrathenn.compilers
package regex.processors

import regex.models.RegexTree
import com.wrathenn.compilers.regex.dfa.{ConcatenationF, OrF, RegexTreeF, StarF, TerminalEndF, TerminalF}

import scala.collection.mutable

object RegexTreeFCalculator {
  private implicit class RegexTreeOps(val node: RegexTree) {
    protected def toTreeFWithNumber(n: Int): (RegexTreeF, Int) = {
      node match {
        case RegexTree.Star(on) =>
          val (node, newN) = on.toTreeFWithNumber(n)
          StarF(node) -> newN
        case RegexTree.Concatenation(left, right) =>
          val (leftNode, leftN) = left.toTreeFWithNumber(n)
          val (rightNode, rightN) = right.toTreeFWithNumber(leftN)
          ConcatenationF(leftNode, rightNode) -> rightN
        case RegexTree.Or(el1, el2) =>
          val (el1Node, el1N) = el1.toTreeFWithNumber(n)
          val (el2Node, el2N) = el2.toTreeFWithNumber(el1N)
          OrF(el1Node, el2Node) -> el2N
        case _: RegexTree.TerminalEnd.type =>
          new TerminalEndF(n) -> (n + 1)
        case RegexTree.Terminal(symbol) =>
          TerminalF(n, symbol) -> (n + 1)
      }
    }

    def toTreeF(): RegexTreeF = {
      val (root, _) = node.toTreeFWithNumber(1)
      root
    }
  }

  // recusively collect all leaf nodes
  def collectNodes(root: RegexTreeF): Map[Int, TerminalF] = {
    val result = mutable.Map[Int, TerminalF]()
    def collectR(node: RegexTreeF): Unit = node match {
      case StarF(on) => collectR(on)
      case OrF(left, right) => collectR(left); collectR(right)
      case ConcatenationF(left, right) => collectR(left); collectR(right)
      case terminal: TerminalF => result.addOne(terminal.number, terminal)
    }
    collectR(root)
    result.toMap
  }

  def fillFollowPos(node: RegexTreeF, numberToLeaf: Map[Int, TerminalF]): Unit = {
    node match {
      case StarF(on) => {
        node.lastPos.foreach { i =>
          numberToLeaf.get(i).map {
            _.followPos.addAll(node.firstPos)
          }
        }
        fillFollowPos(on, numberToLeaf)
      }
      case OrF(left, right) => fillFollowPos(left, numberToLeaf); fillFollowPos(right, numberToLeaf)
      case ConcatenationF(left, right) => {
        left.lastPos.foreach { i =>
          numberToLeaf.get(i).map {
            _.followPos.addAll(right.firstPos)
          }
        }
        fillFollowPos(left, numberToLeaf)
        fillFollowPos(right, numberToLeaf)
      }
      case _ => {}
    }
  }

  def calculateFunctionValues(simpleRoot: RegexTree): RegexTreeF = {
    val root = simpleRoot.toTreeF()

    val numberToLeaf = collectNodes(root)
    fillFollowPos(root, numberToLeaf)

    root
  }
}
