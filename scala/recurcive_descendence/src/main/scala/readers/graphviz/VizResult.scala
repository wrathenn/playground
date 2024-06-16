package com.wrathenn.compilers
package readers.graphviz

import berlin.softwaretechnik.graphviz.GraphElement

case class VizResult(
  newElements: List[(GraphElement, String)],
  acc: List[GraphElement] = List(),
) {
  def accumulate: List[GraphElement] = newElements.map(_._1).concat(acc)
  def plus(other: VizResult): VizResult = VizResult(
    newElements = this.newElements concat other.newElements,
    acc = this.acc concat other.acc,
  )
}

object VizResult {
  def empty: VizResult = VizResult(List(), List())
}
