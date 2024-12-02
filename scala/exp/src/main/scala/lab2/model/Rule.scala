package com.wrathenn.exp
package lab2.model

object Rule {
  type ID = Int
}

case class Rule(
  id: Rule.ID,
  from: Set[Node.ID],
  to: Node.ID
)
