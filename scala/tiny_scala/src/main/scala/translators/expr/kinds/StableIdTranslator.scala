package com.wrathenn.compilers
package translators.expr.kinds

import models.{CodeTarget, VariableDecl, VariableDef}

import com.wrathenn.compilers.context.TranslationContext
import com.wrathenn.compilers.models.`type`.Type
import com.wrathenn.compilers.translators.Translator
import com.wrathenn.compilers.util.Aliases.TinyScalaName
import com.wrathenn.compilers.util.TypeResolver

import scala.annotation.tailrec

class StableIdTranslator(target: CodeTarget) extends Translator[TinyScalaParser.StableIdContext, VariableDef] {

  @tailrec
  private def collectStableIds(
    stableId: TinyScalaParser.StableIdContext,
    acc: List[TinyScalaName] = List(),
  ): List[TinyScalaName] = {
    if (stableId == null) acc
    else collectStableIds(stableId.stableId, stableId.Id.getText +: acc)
  }

  override def translate(node: TinyScalaParser.StableIdContext)(implicit context: TranslationContext): VariableDef = {
    val stableIds = collectStableIds(node)
    var currentVariable: VariableDef = null

    stableIds.foreach { stableId =>
      if (currentVariable == null) {
        currentVariable = context.findVariableById(stableId).getOrElse {
          throw new IllegalArgumentException(s"Unknown variable $stableId")
        }
      }
      else {
        currentVariable._type match {
          case struct: Type.Ref.Struct => {
            val structDef = struct.structDef
            val property = structDef.properties.find(_.name == stableId).getOrElse {
              throw new IllegalArgumentException(s"${currentVariable.tinyScalaName} doesn't have field \"$stableId\"")
            }
            val propertyType = TypeResolver.resolveType(property._type)
            val tempVarName = context.genLocalVariableName(target)

            // super crutch
            if (currentVariable.llvmNameRepr.startsWith("@")) {
              context.writeCodeLn(target) { s"$tempVarName.ptr = load ptr, ptr ${currentVariable.llvmNameRepr}" }
              context.writeCodeLn(target) {
                s"$tempVarName = getelementptr ${structDef.llvmName}, ptr $tempVarName.ptr, i32 0, i32 ${property.index}"
              }
            } else {
              context.writeCodeLn(target) {
                s"$tempVarName = getelementptr ${structDef.llvmName}, ptr ${currentVariable.llvmNameRepr}, i32 0, i32 ${property.index}"
              }
            }
            currentVariable = VariableDef(
              tinyScalaName = stableId,
              llvmNameRepr = tempVarName,
              _type = propertyType,
              decl = VariableDecl.VAL,
              isFunctionParam = false,
            )
          }
          case _ => throw new IllegalArgumentException(s"${currentVariable.tinyScalaName} is not a case class and doesn't have any fields")
        }
      }
    }

    currentVariable
  }
}
