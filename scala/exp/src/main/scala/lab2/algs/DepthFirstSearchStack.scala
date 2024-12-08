package com.wrathenn.exp
package lab2.algs

import lab2.model.{Node, Rule}

import scala.collection.mutable

class DepthFirstSearchStack(
  private val nodes: List[Node],
  private val rules: List[Rule],
) {
  private def debugLn(str: String)(implicit indent: Int): Unit = println(" ".repeat(indent * 2) + str)


  def search(start: List[Node.ID], target: Node.ID): Boolean = {
    val openNodes = mutable.Stack[Node.ID](target)
    val openRules = mutable.Stack[Rule]()

    val closedNodes = mutable.Set.from(start)
    val closedRules = mutable.Set.empty[Rule]
    val unreachableRules = mutable.Set.empty[Rule]
    implicit var indent: Int = 0

    var solutionWasFound = false
    while (!solutionWasFound && openNodes.nonEmpty) {
      val openNode = openNodes.top
//      debugLn(s"Текущая подцель: $openNode")
//      debugLn(s"Открытые вершины: ${openNodes.mkString(", ")}")
//      debugLn(s"Открытые правила: ${openRules.mkString(", ")}")
//      debugLn(s"Закрытые вершины: ${closedNodes.mkString(", ")}")
//      debugLn(s"Закрытые правила: ${closedRules.map(_.id).mkString(", ")}")
//      debugLn(s"Запрещенные правила: ${unreachableRules.map(_.id).mkString(", ")}")

      var ruleWasAdded = false
      rules.takeWhile { rule =>
        if (rule.to == openNode && !closedRules.contains(rule) && !unreachableRules.contains(rule)) {
          openRules.push(rule)
          rule.from.toList.sorted.foreach(openNodes.push(_))
//          openNodes.pushAll(rule.from)

          ruleWasAdded = true
          debugLn(s"Правило ${rule.id} добавлено в стек открытых")
          indent += 1
          false
        }
        else true
      }

      if (!ruleWasAdded) {
        if (openRules.isEmpty) {
          // Последний раз
          openNodes.pop()
        } else {
          val openRule = openRules.top

          if (closedNodes.contains(openNode)) {
            // Подцель уже доказана:
            debugLn(s"✅ Вершина $openNode в списке закрытых")
            indent -= 1

            openNodes.pop();
            if (openRule.from.subsetOf(closedNodes)) {
              debugLn(s"✅ Правило ${openRule.id} доказано")
              closedNodes.add(openRule.to)
              closedRules.add(openRule)
              openRules.pop()
              solutionWasFound = closedNodes.contains(target)
            }
          } else {
            // Подцель не доказана => открытое правило не может быть доказано
            debugLn(s"❌Правило ${openRule} будет добавлено в список недостижимых")
            indent -= 1

            unreachableRules.add(openRule)
            openRules.pop()
            openRule.from.toList.sorted.foreach { nodeId =>
              if (openNodes.top == nodeId) openNodes.pop()
            }
//            openRule.from.foreach { nodeId =>
//            }
          }
        }
      }
    }

    solutionWasFound
  }
}
