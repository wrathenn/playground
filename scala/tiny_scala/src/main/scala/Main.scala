package com.wrathenn.compilers

import org.antlr.v4.runtime.{ANTLRInputStream, CharStream, CharStreams, CommonTokenStream, UnbufferedCharStream}

object Main {
  def main(args: Array[String]): Unit = {
    val text =
      """object Test extends App {
        |}
        |""".stripMargin
    val charStream = CharStreams.fromString(text)
    val lexer = new TinyScalaLexer(charStream)
    val tokenStream = new CommonTokenStream(lexer)
    val parser = new TinyScalaParser(tokenStream)
    val res = parser.compilationUnit()
    println("Hello world!")
  }
}