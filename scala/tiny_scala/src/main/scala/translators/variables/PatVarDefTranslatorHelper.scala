package com.wrathenn.compilers
package translators.variables

import models.{Type, VariableDecl}
import util.Util

object PatVarDefTranslatorHelper {
  case class IncompleteVariableDef(
    name: String,
    _type: Type,
    decl: VariableDecl,
  )

  def getVariableDef(node: TinyScalaParser.PatVarDefContext): IncompleteVariableDef = {
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

    IncompleteVariableDef(variableId, _type, decl)
  }
}
