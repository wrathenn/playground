package com.wrathenn.exp
package lab2

import lab2.algs.{BreadthFirstSearch, DepthFirstSearch}
import lab2.model.{Node, Rule}

object Main extends App {
  val rules = List(
    Rule(id = 101, to = 3, from = Set(1, 2)),
    Rule(id = 102, to = 7, from = Set(2, 3, 4)),
    Rule(id = 103, to = 4, from = Set(5, 6)),
    Rule(id = 104, to = 3, from = Set(8, 31)),
    Rule(id = 105, to = 14, from = Set(7, 9)),
    Rule(id = 106, to = 9, from = Set(4, 10, 11)),
    Rule(id = 107, to = 11, from = Set(12, 13)),
    Rule(id = 108, to = 33, from = Set(15, 34)),
    Rule(id = 109, to = 15, from = Set(16, 17)),
    Rule(id = 110, to = 14, from = Set(9, 34)),
    Rule(id = 111, to = 9, from = Set(18, 32)),
    Rule(id = 112, to = 34, from = Set(19, 20)),
  )

  val nodes = List(
    Node(id = 1),
    Node(id = 2),
    Node(id = 3),
    Node(id = 4),
    Node(id = 5),
    Node(id = 6),
    Node(id = 7),
    Node(id = 8),
    Node(id = 9),
    Node(id = 10),
    Node(id = 11),
    Node(id = 12),
    Node(id = 13),
    Node(id = 14),
    Node(id = 15),
    Node(id = 16),
    Node(id = 17),
    Node(id = 18),
    Node(id = 19),
    Node(id = 20),
    Node(id = 31),
    Node(id = 32),
    Node(id = 33),
    Node(id = 34),
  )

  val from = List(18, 32, 19, 20)
  val to = 14

  val bfs = new BreadthFirstSearch(nodes, rules)
  val bfsResult = bfs.search(from, to)
  println(s"В ширину: ${if (bfsResult) "достижимо" else "не достижимо"}")

  val dfs = new DepthFirstSearch(nodes, rules)
  val dfsResult = dfs.search(from, to)
  println(s"В глубину: ${if (dfsResult) "достижимо" else "не достижимо"}")
}
