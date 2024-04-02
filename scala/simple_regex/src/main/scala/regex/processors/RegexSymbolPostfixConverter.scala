package com.wrathenn.compilers
package regex.processors

import regex.models.{RegexExpressionSymbol, RegexOperandSymbol, RegexSymbol}

import cats.implicits._
import regex.errors.{IncorrectRegexError, RegexError}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object RegexSymbolPostfixConverter {
  def convertToPostfixForm(concatenated: List[RegexSymbol]): Either[RegexError, List[RegexSymbol]] = {
    val stack = mutable.Stack[RegexSymbol]()
    val result = ListBuffer[RegexSymbol]()

    concatenated.foreach {
      case s: RegexOperandSymbol => result.addOne(s)
      case lp: RegexExpressionSymbol.LeftParenthesis.type => stack.push(lp)
      case _: RegexExpressionSymbol.RightParenthesis.type  =>
        val symbolsUntilLeftParenthesis = stack.popWhile { !_.isInstanceOf[RegexExpressionSymbol.LeftParenthesis.type] }
        result.addAll(symbolsUntilLeftParenthesis)
        if (stack.isEmpty)
          return IncorrectRegexError("Неправильная скобочная конструкция (не найдена открывающая скобка)").asLeft
        stack.pop()
      case it =>
        val lessPrecedenceSymbols = stack.popWhile { s => it.precedence <= s.precedence }
        result.addAll(lessPrecedenceSymbols)
        stack.push(it)
    }
    result.addAll(stack.popAll())

    result.toList.asRight
  }
}
