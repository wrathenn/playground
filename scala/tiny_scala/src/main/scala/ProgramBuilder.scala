package com.wrathenn.compilers

import context.LocalContext.Defining
import context.TranslationContext
import models.CodeTarget
import models.function.FunctionDef
import translators.CompilationUnitTranslator
import translators.functions.FunDefExprTranslator

import scala.collection.mutable
import cats.syntax.all._

object ProgramBuilder {
  private def addDefaultFunctions(context: TranslationContext): Unit = {
    // add malloc
    context.writeCodeLn(target = CodeTarget.GLOBAL) { "declare ptr @malloc(i64)" }

    // add printf
    context.writeCodeLn(target = CodeTarget.GLOBAL) { "declare i32 @printf(ptr noundef, ...)" }
    context.addGenericFunctionDefinition(Defaults.printfFunctionDefGeneric)
    context.addFunctionDefinition(Defaults.printfFunctionDef)
  }

  def buildProgram(parser: TinyScalaParser)(implicit context: TranslationContext): String = {
    addDefaultFunctions(context)

    val treeRoot = parser.compilationUnit()
    CompilationUnitTranslator.translate(treeRoot)

    val sb = new StringBuilder()

    val definedFunctions = mutable.Set[FunctionDef.Key](Defaults.printfFunctionDef.key)
    while (definedFunctions != context.functionDefinitions.keySet) {
      val willDefineKey = context.functionDefinitions.keySet.diff(definedFunctions).head
      val functionDef = context.functionDefinitions(willDefineKey)

      context.inLocalContext(defining = Defining.Function(functionDef).some) {
        context.addFunctionDefinition(functionDef)
        new FunDefExprTranslator(functionDef).translate(functionDef.expression)
      }
      definedFunctions.add(willDefineKey)
    }

    sb.append("; --- GLOBAL CODE: ---\n")
    sb.append(context.code.readGlobalCode)
    sb.append("\n")

    sb.append("; --- LOCAL CODE: ---\n")
    sb.append(context.code.readLocalCode)
    sb.append("\n")

    sb.append("define i32 @main() #0 {\n")
    sb.append("; --- INIT CODE: ---\n")
    sb.append("entry:\n")
    sb.append(context.code.readInitCode)
    sb.append("\n")

    sb.append("; --- MAIN CODE: ---\n")
    sb.append(context.code.readMainCode)
    sb.append("ret i32 0\n")
    sb.append("}\n")

    sb.result()
  }
}
