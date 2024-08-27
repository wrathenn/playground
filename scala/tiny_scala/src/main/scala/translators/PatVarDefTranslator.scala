package com.wrathenn.compilers
package translators

import models.{Type, VariableDecl, VariableDef}

import com.wrathenn.compilers.util.Util

class PatVarDefTranslator(
  val objectName: String,
) extends Translator[TinyScalaParser.PatVarDefContext] {

  override def translate(context: TranslationContext, node: TinyScalaParser.PatVarDefContext): String = {
    val decl = node.children.get(0).getText match {
      case "val" => VariableDecl.VAL
      case "var" => VariableDecl.VAR
      case _ => throw new IllegalStateException("Variable declaration is neither val or var")
    }

    val patDef = node.patDef
    val variableId = patDef.Id.getText

    val typeNode = patDef.type_
    val typeRepr = Util.collectTypeRepr(typeNode)
    val _type = Type.fromRepr(typeRepr)

    val tinyScalaRepr = s"${objectName}.${variableId}"
    val llvmNameRepr = s"@${objectName}.${variableId}"

    val variableDef = VariableDef(tinyScalaRepr, llvmNameRepr, _type, decl)
    context.globalVariables.addOne(tinyScalaRepr -> variableDef)

    return s"$llvmNameRepr = global ${_type.llvmRepr} undef\n"
    // TODO add loading value depending on expression
  }
}
