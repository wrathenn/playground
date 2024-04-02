package com.wrathenn.compilers
package regex.models

sealed class RegexSymbol(val precedence: Int)

case class RegexOperandSymbol(value: Char)
  extends RegexSymbol(precedence = -1)

sealed class RegexExpressionSymbol(override val precedence: Int)
  extends RegexSymbol(precedence)

object RegexExpressionSymbol {
  case object StarOperator extends RegexExpressionSymbol(3)
  case object Concatenation extends RegexExpressionSymbol(2)
  case object OrOperator extends RegexExpressionSymbol(1)
  case object LeftParenthesis extends RegexExpressionSymbol(-1)
  case object RightParenthesis extends RegexExpressionSymbol(-1)

  val charMap: Map[Char, RegexExpressionSymbol] = Map(
    '*' -> StarOperator,
    '|' -> OrOperator,
    '(' -> LeftParenthesis,
    ')' -> RightParenthesis,
  )
}
