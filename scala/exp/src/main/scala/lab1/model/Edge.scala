package com.wrathenn.exp
package lab1.model

case class Edge(
  startNode: Node,
  endNode: Node,
  label: String,
  var used: Boolean
)

object Edge {
  def apply(s: Node.ID, e: Node.ID, l: String) = new Edge(Node(s), Node(e), l, used = false)
}
