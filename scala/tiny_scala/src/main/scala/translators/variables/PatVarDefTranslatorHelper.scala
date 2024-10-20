package com.wrathenn.compilers
package translators.variables

import context.TranslationContext
import models.VariableDecl
import util.TypeResolver
import com.wrathenn.compilers.models.`type`.Type

object PatVarDefTranslatorHelper {
  case class IncompleteVariableDef(
    name: String,
    _type: Type,
    decl: VariableDecl,
  )

  def getVariableDef(node: TinyScalaParser.PatVarDefContext)(implicit context: TranslationContext): IncompleteVariableDef = {
    val decl = node.children.get(0).getText match {
      case "val" => VariableDecl.VAL
      case "var" => VariableDecl.VAR
      case _ => throw new IllegalStateException("Variable declaration is neither val or var")
    }

    val patDef = node.patDef
    val variableId = patDef.Id.getText

    val typeNode = patDef.typeDefinition
    val _type = TypeResolver.getTypeFromDefinition(typeNode)

    IncompleteVariableDef(variableId, _type, decl)
  }
}
