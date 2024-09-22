package com.wrathenn.compilers

import context.TranslationContext

object ProgramBuilder {
  def buildProgram(context: TranslationContext): String = {
    val sb = new StringBuilder()

    sb.append("declare ptr @malloc(i64)\n")
    sb.append("declare i32 @printf(ptr noundef, ...)\n")

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
