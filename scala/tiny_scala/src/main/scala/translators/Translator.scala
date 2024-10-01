package com.wrathenn.compilers
package translators

import models.ReturnedValue

import context.TranslationContext
import org.antlr.v4.runtime.ParserRuleContext

trait Translator[A <: ParserRuleContext, R] {
  def translate(node: A)(implicit context: TranslationContext): R
}
