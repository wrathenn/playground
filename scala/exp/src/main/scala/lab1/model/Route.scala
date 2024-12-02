package com.wrathenn.exp
package lab1.model

object Route {
  case class Element(
    from: Node.ID,
    to: Node.ID,
    via: String,
  )

  def routeRepresentation(route: List[Route.Element]): String = {
    if (route.isEmpty) return "Empty Route"

    val result = new StringBuilder()
    val last = route.last

    route.foreach { e =>
      result.append(s"${e.from} --${e.via}--> ")
    }
    result.append(s"${last.to}")
    result.result()
  }
}
