package com.wrathenn.compilers

import regex.automata.{DFA, DFAStateBuilder, DFAWalker}
import regex.automata.RegexTreeF.terminalEndFShow
import regex.dbg.TreePrinter
import regex.models.RegexTree
import regex.models.RegexTree.regexTreeShow
import regex.processors._

import cats.effect.{ExitCode, IO, IOApp}
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

// TODO minimization
object Main extends IOApp {
  implicit def logger: Logger[IO] = Slf4jLogger.getLogger[IO]

  def tryStrings(dfa: DFA.State, srcRegex: String): IO[Unit] = for {
    _ <- IO.print("Строка для проверки: ")
    str <- IO.readLine
    matches = DFAWalker.matches(str, dfa)
    _ <- IO.println(if (matches) s"Строка $str подходит шаблону $srcRegex" else s"Строка $str НЕ подходит шаблону $srcRegex")
  } yield()

  override def run(args: List[String]): IO[ExitCode] = for {
    _ <- IO.println("Введите regex:")
    rawRegexString <- IO.readLine

    escaped <- IO fromEither RegexSymbolEscaper.escapeCharacters(rawRegexString)
    _ <- logger debug s"Escaped:\n${escaped.mkString(", ")}"

    symbols = RegexSymbolBuilder.buildSymbols(escaped)
    _ <- logger debug s"Symbols built:\n${symbols.mkString(", ")}"

    concatenated = RegexSymbolConcatenator.addConcatinations(symbols)
    _ <- logger debug s"Symbols concatenated:\n${concatenated.mkString(", ")}"

    postfixForm <- IO fromEither RegexSymbolPostfixConverter.convertToPostfixForm(concatenated)
    _ <- logger debug s"Postfix form of expression:\n${postfixForm.mkString(", ")}"

    tree <- IO fromEither RegexTreeBuilder.buildRegexTree(postfixForm)
    extraTree = RegexTree.Concatenation(left = tree, right = RegexTree.TerminalEnd)
    _ <- TreePrinter.print(extraTree) { logger debug _ }

    treeF = RegexTreeFCalculator.calculateFunctionValues(extraTree)
    _ <- TreePrinter.print(treeF) { logger debug _ }

    dfa = DFAStateBuilder.buildDFA(treeF)

    _ <- tryStrings(dfa, rawRegexString).foreverM
  } yield ExitCode.Success
}