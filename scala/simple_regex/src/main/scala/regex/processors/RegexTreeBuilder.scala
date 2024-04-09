package com.wrathenn.compilers
package regex.processors

import regex.errors.{IncorrectRegexError, RegexError}
import regex.models.{RegexExpressionSymbol, RegexOperandSymbol, RegexSymbol, RegexTree}

import cats.implicits._

import scala.collection.mutable

object RegexTreeBuilder {
  def buildRegexTree(postfixForm: List[RegexSymbol]): Either[RegexError, RegexTree] = {
    val stack = mutable.Stack[RegexTree]()

    postfixForm.foreach {
      case _: RegexExpressionSymbol.StarOperator.type =>
        val left = stack.removeHeadOption().getOrElse(throw IncorrectRegexError("Перед оператором \"*\" нет выражения"))
        stack.push(RegexTree.Star(left))
      case _: RegexExpressionSymbol.Concatenation.type =>
        val right = stack.removeHeadOption().getOrElse(throw IncorrectRegexError("Справа от оператора конкатенации нет выражения"))
        val left = stack.removeHeadOption().getOrElse(throw IncorrectRegexError("Слева от оператора конкатенации нет выражения"))
        stack.push(RegexTree.Concatenation(left, right))
      case _: RegexExpressionSymbol.OrOperator.type =>
        val el2 = stack.removeHeadOption().getOrElse(throw IncorrectRegexError("Справа от оператора \"|\" нет выражения"))
        val el1 = stack.removeHeadOption().getOrElse(throw IncorrectRegexError("Слева от оператора \"|\" нет выражения"))
        stack.push(RegexTree.Or(el1, el2))
      case s: RegexOperandSymbol =>
        stack.push(RegexTree.Terminal(s.value))
      case err =>
        return IncorrectRegexError(s"Некорректное выражение, не получилось обработать символ \"${err}\"").asLeft
    }

    stack.pop().asRight
  }
}
