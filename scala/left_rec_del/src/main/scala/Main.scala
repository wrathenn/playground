package com.wrathenn.compilers

import algs.DestroyUnreachableSymbols.DestroyUnreachableSyntax
import algs.IsLanguageEmpty.IsEmptySyntax
import converters.ConfigToModel
import com.wrathenn.compilers.models.Config.GrammarConfig
import com.wrathenn.compilers.models.Config.GrammarConfig._
import models.implicits.grammarShow

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import com.wrathenn.compilers.algs.DestroyUselessSymbols.DestroyUselessSyntax
import io.circe.jawn.decode

import scala.io.Source

object Main extends IOApp {
  private def readConfig(path: String): IO[GrammarConfig] = for {
    configText <- IO blocking { Source.fromResource(path).mkString }
    config: GrammarConfig <- IO fromEither decode(configText)
  } yield config

  override def run(args: List[String]): IO[ExitCode] = for {
    config <- readConfig("useless-test.json")
    _ <- IO println config
    grammar <- IO fromEither ConfigToModel.validateAndConvert(config)
    _ <- IO println grammar.show

//    isEmptyLanguage <- IO delay grammar.isLanguageEmpty
//    _ <- IO println isEmptyLanguage

//    grammarNoUnreachable <- IO delay grammar.destroyUnreachableSymbols
//    _ <- IO println grammarNoUnreachable.show

    grammarNoUseless <- IO delay grammar.destroyUselessSymbols
    _ <- IO println grammarNoUseless.show
  } yield ExitCode.Success
}