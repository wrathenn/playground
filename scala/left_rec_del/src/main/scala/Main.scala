package com.wrathenn.compilers

import algs.GrammarExt._
import converters.ConfigToModel
import util.ConfigReader

import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.all._

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = for {
    config <- ConfigReader.readConfig("eps-destroy-test.json")
    _ <- IO println config
    grammar <- IO fromEither ConfigToModel.validateAndConvert(config)
    _ <- IO println grammar.show

    isEmptyLanguage <- IO delay grammar.isLanguageEmpty
    _ <- IO println isEmptyLanguage

    grammarNoUnreachable <- IO delay grammar.destroyUnreachableSymbols
    _ <- IO println grammarNoUnreachable.show

    grammarNoUseless <- IO delay grammar.destroyUselessSymbols
    _ <- IO println grammarNoUseless.show

    grammarNoEpsRules <- IO delay grammar.destroyEpsRules
    _ <- IO println grammarNoEpsRules.show
  } yield ExitCode.Success
}
