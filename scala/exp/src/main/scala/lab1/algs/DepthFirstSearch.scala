package com.wrathenn.exp
package lab1.algs

import scala.collection.mutable
import cats.syntax.all._
import com.wrathenn.exp.lab1.model.{Edge, Node, Route}

class DepthFirstSearch(
  private val edges: List[Edge],
) {

  private case class Memory(
    opened: mutable.Stack[Node] = mutable.Stack(),
    path: mutable.Stack[(Node, Option[Edge])] = mutable.Stack(),
    closed: mutable.Set[Node.ID] = mutable.Set(),
    var solutionFound: Boolean = false,
    var moreChildren: Boolean = true,
  )

  def search(startId: Node.ID, goal: Node.ID): Option[List[Route.Element]] = {
    val mem = Memory()
    mem.opened.push(Node(startId))
    mem.path.push(Node(startId) -> None)

    while (mem.moreChildren && !mem.solutionFound) {
      println(s"Current stack: ${mem.opened.mkString(", ")}")
      sampleSearch(goal, mem)

      if (!mem.solutionFound && !mem.moreChildren && mem.opened.size > 1) {
        val currentNode = mem.opened.pop()
        mem.path.pop()
        mem.closed.add(currentNode.id)
        mem.moreChildren = true
      }
    }

    if (!mem.solutionFound) None
    else makeResult(mem.path.reverse).some
  }

  private def sampleSearch(goal: Node.ID, mem: Memory): Unit = {
    mem.moreChildren = false

    edges.view
      .filter(e => e.startNode.id == mem.opened.top.id)
      .filter(e => !e.used)
      .filterNot(e => mem.opened contains e.endNode)
      .filterNot(e => mem.closed contains e.endNode.id)
      .foreach { e =>
        e.used = true
        mem.opened.push(e.endNode)
        mem.path.push(e.endNode -> e.some)
        mem.moreChildren = true

        if (e.endNode.id == goal) {
          mem.solutionFound = true
        }
        return
      }
  }



  private def makeResult(route: collection.Iterable[(Node, Option[Edge])]): List[Route.Element] = {
    val result = mutable.ListBuffer[Route.Element]()
    route.reduce[(Node, Option[Edge])] { case (e1, e2) =>
      result.addOne(Route.Element(from = e1._1.id, to = e2._1.id, via = e2._2.get.label))
      e2
    }
    result.toList
  }
}
