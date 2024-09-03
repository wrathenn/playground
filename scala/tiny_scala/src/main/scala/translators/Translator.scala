package com.wrathenn.compilers
package translators

import models.ReturnedValue

import org.antlr.v4.runtime.ParserRuleContext

trait Translator[A <: ParserRuleContext, R] {
  def translate(context: TranslationContext, node: A): R
}
