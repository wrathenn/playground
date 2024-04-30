package com.wrathenn.compilers
package util

import algs.GrammarExt.grammarShow
import converters.ConfigToModel
import models.Grammar

import cats.effect.IO
import cats.syntax.all._

object TestGrammarReader {
  def getGrammar(filename: String): IO[Grammar] = for {
    config <- ConfigReader.readConfig(filename)
    grammar <- IO fromEither ConfigToModel.validateAndConvert(config)
    _ <- IO println grammar.show
  } yield grammar
}
