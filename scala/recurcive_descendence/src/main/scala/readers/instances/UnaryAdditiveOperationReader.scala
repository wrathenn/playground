package com.wrathenn.compilers
package readers.instances

import models.G5.{NonTerminal, Terminal}
import readers.NonTerminalReader.readTerminal
import readers.{InputPointer, NonTerminalReader}

import cats.syntax.all._

object UnaryAdditiveOperationReader extends NonTerminalReader[NonTerminal.UnaryAdditiveOperation] {
  override def read(ip: InputPointer): Either[Exception, (NonTerminal.UnaryAdditiveOperation, InputPointer)] = {
    val firstApplicableTerminal = List(
      () => readTerminal(Terminal.+, ip),
      () => readTerminal(Terminal.-, ip),
    ).view.flatMap(_.apply()).headOption

    firstApplicableTerminal match {
      case Some((value, ip)) => (NonTerminal.UnaryAdditiveOperation(value) -> ip).asRight
      case None => new IllegalStateException("Error: NT unary additive operation").asLeft
    }
  }
}
