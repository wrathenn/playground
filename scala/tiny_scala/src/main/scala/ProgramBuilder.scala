package com.wrathenn.compilers

import context.TranslationContext

object ProgramBuilder {
  def buildProgram(context: TranslationContext): String = {
    val sb = new StringBuilder()

    sb.append("declare ptr @malloc(i64)\n")
    sb.append("declare i32 @printf(ptr noundef, ...)\n")

    sb.append("; --- GLOBAL CODE: ---\n")
    sb.append(context.readGlobalCode)
    sb.append("\n")

    sb.append("; --- LOCAL CODE: ---\n")
    sb.append(context.readLocalCode)
    sb.append("\n")

    sb.append("define i32 @main() #0 {\n")
    sb.append("; --- INIT CODE: ---\n")
    sb.append("entry:\n")
    sb.append(context.readInitCode)
    sb.append("\n")

    sb.append("; --- MAIN CODE: ---\n")
    sb.append(context.readMainCode)
    sb.append("ret i32 0\n")
    sb.append("}\n")

    sb.result()
  }
}
