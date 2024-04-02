package com.wrathenn.compilers
package regex.errors

sealed class RegexError(message: String, cause: Option[Throwable] = None)
  extends Exception(message, cause.orNull)

case class IncorrectRegexError(message: String, cause: Option[Throwable] = None)
  extends RegexError(message, cause)
