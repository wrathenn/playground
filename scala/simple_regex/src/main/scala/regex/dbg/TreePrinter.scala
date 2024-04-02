package com.wrathenn.compilers
package regex.dbg

import regex.models.{RegexTree, Tree}

import cats.Show
import cats.effect.IO
import cats.syntax.all._

object TreePrinter {
  def strTree[A](
    tree: Tree[A],
    prefix: String = "", downLevel: String = "", upperLevel: String = ""
  )(implicit show: Show[Tree[A]]): List[String] = {
    tree match {
      case _: Tree.Tree0[_] => List(prefix + tree.show)
      case tree1: Tree.Tree1[A] =>
        val thisShow = tree.show
        val suffix = " " * thisShow.length
        strTree(tree1.on, prefix + thisShow, downLevel + suffix, upperLevel + suffix)
      case tree2: Tree.Tree2[A] =>
        val upperTree = strTree(tree2.right, upperLevel + " ┏[R]=", upperLevel + " ┃    ", upperLevel + "      ")
        val cur = prefix + tree.show
        val lowerTree = strTree(tree2.left, downLevel + " ┗[L]=", downLevel + "      ", downLevel + " ┃    ")
        (upperTree :+ cur) ++ lowerTree
    }
  }

  def print[A](tree: Tree[A])(printer: String => IO[Unit])
    (implicit show: Show[Tree[A]]): IO[Unit] = for {
    lines <- IO pure strTree(tree)
    _ <- lines.traverse(printer(_))
  } yield ()
}
