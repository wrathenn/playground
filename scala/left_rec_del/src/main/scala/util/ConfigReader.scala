package com.wrathenn.compilers
package util

import models.Config.GrammarConfig
import models.Config.GrammarConfig._

import cats.effect.IO
import io.circe.jawn.decode

import scala.io.Source

object ConfigReader {
  def readConfig(path: String): IO[GrammarConfig] = for {
    configText <- IO blocking { Source.fromResource(path).mkString }
    config: GrammarConfig <- IO fromEither decode(configText)
  } yield config
}
