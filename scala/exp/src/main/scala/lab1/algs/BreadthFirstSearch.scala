package com.wrathenn.exp
package lab1.algs

import scala.collection.mutable
import cats.syntax.all._
import com.wrathenn.exp.lab1.model.{Edge, Node, Route}

class BreadthFirstSearch(
  private val edges: List[Edge],
) {

  private case class Memory(
    opened: mutable.Queue[Node] = mutable.Queue(),
    closed: mutable.Set[Node.ID] = mutable.Set(),
    var solutionFound: Boolean = false,
    var moreChildren: Boolean = true,
    resultPath: mutable.Map[Node.ID, (Node, Option[Edge])] = mutable.Map(),
  )

  def search(start: Node.ID, goal: Node.ID): Option[List[Route.Element]] = {
    val mem = Memory()
    mem.opened.append(Node(start))

    while (mem.moreChildren && !mem.solutionFound) {
      println(s"Current Queue: ${mem.opened.mkString(", ")}")
      sampleSearch(goal, mem)

      if (!mem.solutionFound) {
        val currentNode = mem.opened.dequeue()
        mem.closed.addOne(currentNode.id)

        if (mem.opened.nonEmpty) {
          mem.moreChildren = true
        }
      }
    }

    if (!mem.solutionFound) None
    else getResultPath(start, goal, mem).some
  }

  private def sampleSearch(goal: Node.ID, mem: Memory): Unit = {
    mem.moreChildren = false

    edges.view
      .filter(e => e.startNode.id == mem.opened.head.id)
      .filter(e => !e.used)
      .filterNot(e => mem.opened contains e.endNode)
      .filterNot(e => mem.closed contains e.endNode.id)
      .foreach { e =>
        e.used = true
        mem.opened.append(e.endNode)
        mem.resultPath.addOne(e.endNode.id -> (e.startNode -> e.some))
        mem.moreChildren = true

        if (e.endNode.id == goal) {
          mem.solutionFound = true
          return
        }
      }
  }

  private def getResultPath(start: Node.ID, goal: Node.ID, mem: Memory): List[Route.Element] = {
    val result = mutable.ListBuffer[Route.Element]()
    var current: (Node, Option[Edge]) = Node(goal) -> None

    while (current._1.id != start) {
      val prev = mem.resultPath(current._1.id)
      result.prepend(Route.Element(from = prev._1.id, to = current._1.id, via = prev._2.get.label))
      current = prev
    }

    result.toList
  }
}
