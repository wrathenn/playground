package com.wrathenn.compilers

import context.TranslationContext

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}

object Main {
  def main(args: Array[String]): Unit = {
    val text = {
       """|
          |object FibTest {
          |
          |  def fib(a: Int): Int = {
          |    if (a == 1) return 1
          |    if (a == 2) return 1
          |    val res: Int = fib(a - 1) + fib(a - 2)
          |    res
          |  }
          |}
          |
          |object Test extends App {
          |  val f: Int = FibTest.fib(10)
          |  print("%d\n", f)
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