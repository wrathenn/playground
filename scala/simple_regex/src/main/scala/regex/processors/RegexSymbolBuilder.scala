package com.wrathenn.compilers
package regex.processors

import regex.models.{RawRegexChar, RegexExpressionSymbol, RegexOperandSymbol, RegexSymbol}

object RegexSymbolBuilder {
  def buildSymbols(escapedRawRegex: List[RawRegexChar]): List[RegexSymbol] = {
    escapedRawRegex.map { case RawRegexChar(c, isEscaped) =>
      if (isEscaped) RegexOperandSymbol(c)
      else RegexExpressionSymbol.charMap.getOrElse(c, RegexOperandSymbol(c))
    }
  }
}
