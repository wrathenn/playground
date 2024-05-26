package com.wrathenn.compilers
package models

trait Tree[+A]
object Tree {
  trait Tree0[A] extends Tree[A]
  trait Tree1[A] extends Tree[A] { val _1: Tree[A] }
  trait Tree2[A] extends Tree[A] { val _1: Tree[A]; val _2: Tree[A] }
  trait Tree3[A] extends Tree[A] { val _1: Tree[A]; val _2: Tree[A]; val _3: Tree[A] }
}
