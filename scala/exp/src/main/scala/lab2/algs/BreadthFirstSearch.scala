package com.wrathenn.exp
package lab2.algs

import lab2.model.{Node, Rule}

import scala.annotation.tailrec
import scala.collection.mutable

class BreadthFirstSearch(
  private val nodes: List[Node],
  private val rules: List[Rule],
) {
  private val nodesMap = nodes.map(n => n.id -> n).toMap
  private val rulesMap = rules.map(r => r.id -> r).toMap

  private case class Context(
    closedNodes: mutable.Set[Node.ID],
    closedRules: mutable.Set[Rule.ID],
  )

  private def searchIteration(target: Node.ID)(implicit context: Context): Boolean = {
    println("--- Контекст ---")
    println(s"Закрытые вершины: [${context.closedNodes.mkString(", ")}]")
    println(s"Закрытые правила: [${context.closedRules.mkString(", ")}]")
    println("----------------")
    var somethingChanged = false

    (rulesMap -- context.closedRules).foreach { case (_, rule) =>
      if (rule.from.subsetOf(context.closedNodes)) {
        println(s"✅ Правило ${rule.id}: (${rule.from.mkString(" & ")})->${rule.to} выполняется.")
        println(s"✅ Правило ${rule.id} добавлено в список закрытых правил. Вершина ${rule.to} добавлена в список закрытых вершин.")
        somethingChanged = true
        context.closedRules += rule.id
        context.closedNodes += rule.to

        if (rule.to == target) {
          return true
        }
      }
    }

    if (!somethingChanged) {
      println(s"❌ На очередной итерации поиска в ширину ни одно правило не было закрыто, вершину $target найти не удалось.")
      return false
    }
    searchIteration(target)
  }

  def search(start: List[Node.ID], target: Node.ID): Boolean = {
    implicit val context = Context(
      closedNodes = mutable.Set.from(start),
      closedRules = mutable.Set.empty,
    )

    searchIteration(target)
  }
}
