package com.wrathenn.compilers
package translators

class DefTranslator(
  val objectName: String,
) extends Translator[TinyScalaParser.Def_Context] {
  override def translate(context: TranslationContext, node: TinyScalaParser.Def_Context): String = {
    if (node.patVarDef != null) new PatVarDefTranslator(objectName).translate(context, node.patVarDef)
    else ???
  }
}
