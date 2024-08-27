package com.wrathenn.compilers

import translators.{CompilationUnitTranslator, TranslationContext}

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}

object Main {
  def main(args: Array[String]): Unit = {
    val text = {
       """case class A(v1: Int, v2: Double)
          |case class B(a: Long, aa: A)
          |
          |object Test extends App {
          |  val test_value: Int = 2
          |  val test_a: A = new A()
          |  var test_b: Test.B = Test.A
          |  var test_arr: Array[B] = 123
          |}
          |""".stripMargin
    }
    val charStream = CharStreams.fromString(text)
    val lexer = new TinyScalaLexer(charStream)
    val tokenStream = new CommonTokenStream(lexer)
    val parser = new TinyScalaParser(tokenStream)
    val res = parser.compilationUnit()

    val translationContext = TranslationContext()
    val llvm = CompilationUnitTranslator.translate(translationContext, res)
    println(llvm)
  }
}