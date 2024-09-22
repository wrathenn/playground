package com.wrathenn.compilers

import translators.CompilationUnitTranslator
import com.wrathenn.compilers.context.TranslationContext

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}

object Main {
  def main(args: Array[String]): Unit = {
    val text = {
       """|
          |object Test extends App {
          |  val a: Int = if (1 == 2) 2 else 3
          |}
          |""".stripMargin
    }
    val charStream = CharStreams.fromString(text)
    val lexer = new TinyScalaLexer(charStream)
    val tokenStream = new CommonTokenStream(lexer)
    val parser = new TinyScalaParser(tokenStream)
    val res = parser.compilationUnit()

    val translationContext = TranslationContext.create
    CompilationUnitTranslator.translate(translationContext, res)
    val program = ProgramBuilder.buildProgram(context = translationContext)
    println(program)
  }
}