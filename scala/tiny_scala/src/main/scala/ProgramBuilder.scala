package com.wrathenn.compilers

import context.TranslationContext
import models.{CodeTarget, FunctionDef, Type, VariableDecl, VariableDef}

import com.wrathenn.compilers.translators.CompilationUnitTranslator
import io.circe.parser

object ProgramBuilder {
  private def addDefaultFunctions(context: TranslationContext): Unit = {
    // add malloc
    context.writeCodeLn(target = CodeTarget.GLOBAL) { "declare ptr @malloc(i64)" }

    // add printf
    context.writeCodeLn(target = CodeTarget.GLOBAL) { "declare i32 @printf(ptr noundef, ...)" }
    context.addGlobalFunction(FunctionDef(
      tinyScalaName = "print",
      llvmName = "@printf",
      params = List(
        VariableDef(
          tinyScalaRepr = "fmt",
          llvmNameRepr = "fmt",
          _type = Type.Ref._String,
          decl = VariableDecl.VAL,
          isFunctionParam = true,
        ),
      ),
      returns = Type.Primitive._Int,
      isVarArg = true,
    ))
  }

  def buildProgram(parser: TinyScalaParser, context: TranslationContext): String = {
    addDefaultFunctions(context)

    val treeRoot = parser.compilationUnit()
    CompilationUnitTranslator.translate(context, treeRoot)

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
