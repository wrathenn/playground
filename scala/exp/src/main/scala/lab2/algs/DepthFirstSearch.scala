package com.wrathenn.exp
package lab2.algs

import lab2.model.{Node, Rule}

import com.wrathenn.exp.lab2.model.Node.ID

import scala.collection.mutable

class DepthFirstSearch(
  private val nodes: List[Node],
  private val rules: List[Rule],
) {
  private val nodesMap = nodes.map(n => n.id -> n).toMap
  private val rulesReverseMap: Map[ID, List[Rule]] = rules.groupBy(_.to)

  private case class Context(
    closedNodes: mutable.Set[Node.ID],
    unreachableNodes: mutable.Set[Node.ID],
    closedRules: mutable.Set[Rule.ID],
    unreachableRules: mutable.Set[Rule.ID],
    var indent: Int,
  )

  private def debugLn(str: String)(implicit context: Context): Unit = println(" ".repeat(context.indent * 2) + str)

  private def proveRule(rule: Rule)(implicit context: Context): Boolean = {
    debugLn(s"Доказательство правила ${rule.id}:")
    context.indent += 1

    if (context.closedRules contains rule.id) {
      debugLn(s"✅ Правило ${rule.id} в списке закрытых")
      context.indent -= 1
      return true
    }
    if (context.unreachableRules contains rule.id) {
      debugLn(s"❌ Правило ${rule.id} в списке невыполнимых")
      context.indent -= 1
      return false
    }

    rule.from.foreach { nodeId =>
      val nodeProved = proveNode(nodeId)
      if (!nodeProved) {
        debugLn(s"❌ Правило ${rule.id} добавлено в список невыполнимых")
        context.unreachableRules += rule.id

        context.indent -= 1
        return false
      }
    }

    debugLn(s"✅ Правило ${rule.id} добавлено в список доказанных")
    context.closedRules += rule.id
    context.indent -= 1
    true
  }

  private def proveNode(nodeId: Node.ID)(implicit context: Context): Boolean = {
    debugLn(s"Вывод вершины $nodeId:")
    context.indent += 1

    if (context.closedNodes contains nodeId) {
      debugLn(s"✅ Вершина $nodeId в списке закрытых")
      context.indent -= 1
      return true
    }
    if (context.unreachableNodes contains nodeId) {
      debugLn(s"❌ Вершина $nodeId в списке недостижимых")
      context.indent -= 1
      return false
    }

    val rulesToThisNode = rulesReverseMap.getOrElse(nodeId, {
      context.unreachableNodes += nodeId

      debugLn(s"❌ Нет правил, которые ведут в вершину $nodeId")
      context.indent -= 1
      return false
    })

    rulesToThisNode.foreach { r =>
      if (proveRule(r)) {
        context.closedNodes += nodeId

        debugLn(s"✅ Правило  ${r.id}: (${r.from.mkString(" & ")})->${r.to} выполняется, вершина $nodeId добавлена в список закрытых")
        context.indent -= 1
        return true
      }
    }

    debugLn(s"❌ Не удалось доказать правила, ведущие в вершину $nodeId, она будет добавлена в список недостижимых")
    context.unreachableNodes += nodeId

    context.indent -= 1
    false
  }


  def search(start: List[Node.ID], target: Node.ID): Boolean = {
    implicit val context = Context(
      closedNodes = mutable.Set.from(start),
      unreachableNodes = mutable.Set.empty,
      closedRules = mutable.Set.empty,
      unreachableRules = mutable.Set.empty,
      indent = 0,
    )

    proveNode(target)
  }
}
