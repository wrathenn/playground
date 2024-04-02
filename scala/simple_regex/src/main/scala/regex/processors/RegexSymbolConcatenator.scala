package com.wrathenn.compilers
package regex.processors

import regex.models.{RegexExpressionSymbol, RegexOperandSymbol, RegexSymbol}

import scala.collection.mutable.ListBuffer

object RegexSymbolConcatenator {
  def addConcatinations(source: List[RegexSymbol]): List[RegexSymbol] = {
    if (source.isEmpty) return List()

    val result = ListBuffer[RegexSymbol](source.head)

    source.reduce { (prev, cur) =>
      val addConcat = (prev, cur) match {
        case (_: RegexOperandSymbol, _: RegexOperandSymbol) => true
        case (_: RegexExpressionSymbol.StarOperator.type, _: RegexOperandSymbol) => true
        case (_: RegexExpressionSymbol.StarOperator.type, _: RegexExpressionSymbol.LeftParenthesis.type) => true
        case (_: RegexExpressionSymbol.LeftParenthesis.type, _: RegexExpressionSymbol.RightParenthesis.type) => true
        case (_: RegexExpressionSymbol.RightParenthesis.type, _: RegexOperandSymbol) => true
        case (_: RegexOperandSymbol, _: RegexExpressionSymbol.LeftParenthesis.type) => true
        case _ => false
      }
      if (addConcat) {
        result.addOne(RegexExpressionSymbol.Concatenation)
        result.addOne(cur)
      } else {
        result.addOne(cur)
      }
      cur
    }

    result.toList
  }
}
