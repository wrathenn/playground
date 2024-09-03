package com.wrathenn.compilers

import translators.TranslationContext

object ProgramBuilder {
  def buildProgram(context: TranslationContext): String = {
    val sb = new StringBuilder()

    sb.append("declare ptr @malloc(i64)\n")
    sb.append("declare i32 @printf(ptr noundef, ...)\n")

    sb.append(context.globalCode)
    sb.append("\n")

    sb.append("define i32 @main() #0 {\n")
    sb.append("entry:\n")
    sb.append(context.initCode)
    sb.append("\n")
    sb.append(context.mainCode)
    sb.append("ret i32 0\n")
    sb.append("}\n")

    sb.result()
  }
}
