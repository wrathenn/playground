package com.wrathenn.compilers
package regex.processors

import regex.errors.{IncorrectRegexError, RegexError}
import regex.models.{Alphabet, RawRegexChar}

import cats.implicits._

import scala.collection.mutable.ListBuffer

object RegexSymbolEscaper {
  def escapeCharacters(rawRegex: String): Either[RegexError, List[RawRegexChar]] = {
    // Sry for mutability, but its more efficient
    val result = ListBuffer[RawRegexChar]()
    var isEscaped = false

    rawRegex.foreach { c =>
      // 2nd condition so that "//" would be possible
      if (c == Alphabet.ESCAPE_CHAR && !isEscaped) {
        isEscaped = true
      } else {
        result addOne RawRegexChar(c, isEscaped)
        isEscaped = false
      }
    }

    if (isEscaped) IncorrectRegexError("Последний символ - символ экранирования").asLeft
    else result.toList.asRight
  }
}
