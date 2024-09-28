package com.wrathenn.compilers

import context.TranslationContext

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}

object Main {
  def main(args: Array[String]): Unit = {
    val text = {
       """|
          |case class Test(a: Int, b: Double)
          |
          |object Test extends App {
          |  val c: Test = new Test(1, 2.0)
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