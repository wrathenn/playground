package com.wrathenn.compilers

import context.TranslationContext
import models.{CodeTarget, Type, TypeName, VariableDecl, VariableDef, function}

import com.wrathenn.compilers.models.function.{FunctionDef, FunctionDefGeneric}
import com.wrathenn.compilers.translators.CompilationUnitTranslator
import io.circe.parser

object ProgramBuilder {
  private def addDefaultFunctions(context: TranslationContext): Unit = {
    // add malloc
    context.writeCodeLn(target = CodeTarget.GLOBAL) { "declare ptr @malloc(i64)" }

    // add printf
    context.writeCodeLn(target = CodeTarget.GLOBAL) { "declare i32 @printf(ptr noundef, ...)" }
    val printfFunctionDef = FunctionDef(
      tinyScalaName = "print",
      llvmName = "@printf",
      params = List(
        VariableDef(
          tinyScalaName = "fmt",
          llvmNameRepr = "fmt",
          _type = Type.Ref._String,
          decl = VariableDecl.VAL,
          isFunctionParam = true,
        ),
      ),
      returns = TypeName(tinyScalaName = "Int", generics = List()),
      isVarArg = true,
      concreteGenericTypes = Map(),
    )
//    context.addFunctionDefinition(printfFunctionDef)
  }

  def buildProgram(parser: TinyScalaParser)(implicit context: TranslationContext): String = {
    addDefaultFunctions(context)

    val treeRoot = parser.compilationUnit()
    CompilationUnitTranslator.translate(treeRoot)

    val sb = new StringBuilder()

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
