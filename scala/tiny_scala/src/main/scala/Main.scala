package com.wrathenn.compilers

import context.TranslationContext

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}

object Main {
  def main(args: Array[String]): Unit = {
    val text = {
       """|
          |object Test extends App {
          |  val a: Int = if (1 == 2) 2 else 3
          |  val b: Int = 3 - 2 * 6 + 8 / 2
          |  print("%d\n", 3 + 2 - 1)
          |}
          |""".stripMargin
    }
    val charStream = CharStreams.fromString(text)
    val lexer = new TinyScalaLexer(charStream)
    val tokenStream = new CommonTokenStream(lexer)
    val parser: TinyScalaParser = new TinyScalaParser(tokenStream)

    val translationContext = TranslationContext.create
    val program = ProgramBuilder.buildProgram(context = translationContext, parser = parser)
    println(program)
  }
}