package com.wrathenn.compilers

import translators.CompilationUnitTranslator
import com.wrathenn.compilers.context.TranslationContext

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}

object Main {
  def main(args: Array[String]): Unit = {
    val text = {
       """case class A(v1: Int, v2: Double)
          |case class B(a: Long, aa: A)
          |
          |object Test extends App {
          |  val a: Int = 727
          |  val test_value: Int = a + 3 * 4 / 2
          |  def foo(a: Int): Int = {
          |    return a + 3
          |  }
          |}
          |
          |object A {
          |  val a: A = new A(1, 2.0)
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