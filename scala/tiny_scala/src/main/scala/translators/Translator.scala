package com.wrathenn.compilers
package translators

import org.antlr.v4.runtime.ParserRuleContext

trait Translator[A <: ParserRuleContext] {
  def translate(context: TranslationContext, node: A): String
}
