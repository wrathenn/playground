package com.wrathenn.exp
package lab1

import lab1.algs.{BreadthFirstSearch, DepthFirstSearch}
import lab1.model.{Edge, Route}

object Main extends App {

  def testEdges(): List[Edge] = List(
    Edge(0, 1, "101"),
    Edge(0, 2, "102"),
    Edge(0, 3, "103"),
    Edge(1, 4, "104"),
    Edge(2, 4, "105"),
    Edge(2, 5, "106"),
    Edge(3, 5, "107"),
    Edge(3, 6, "108"),
    Edge(4, 8, "109"),
    Edge(5, 4, "110"),
    Edge(5, 7, "111"),
    Edge(5, 9, "112"),
    Edge(6, 7, "113"),
    Edge(7, 9, "114"),
    Edge(9, 8, "115"),
  )

  println("Depth First Search:")
  val dfsEdges = testEdges()
  val dfsResult = new DepthFirstSearch(dfsEdges).search(0, 7)
  dfsResult match {
    case Some(value) => println(s"Route is: ${Route.routeRepresentation(value)}")
    case None => println("Route not found")
  }

  println("Breadth First Search:")
  val bfsEdges = testEdges()
  val bfsResult = new BreadthFirstSearch(bfsEdges).search(0, 7)
  bfsResult match {
    case Some(value) => println(s"Route is: ${Route.routeRepresentation(value)}")
    case None => println("Route not found")
  }
}