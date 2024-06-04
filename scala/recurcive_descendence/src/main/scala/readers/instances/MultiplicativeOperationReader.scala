package com.wrathenn.compilers
package readers.instances

import models.G5.{NonTerminal, Terminal}
import readers.NonTerminalReader.readTerminal
import readers.{InputPointer, NonTerminalReader}

import cats.syntax.all._

object MultiplicativeOperationReader extends NonTerminalReader[NonTerminal.MultiplicativeOperation]{
  override def read(ip: InputPointer): Either[Exception, (NonTerminal.MultiplicativeOperation, InputPointer)] = {
    val firstApplicableTerminal = List(
      () => readTerminal(Terminal.*, ip),
      () => readTerminal(Terminal./, ip),
      () => readTerminal(Terminal.Mod, ip),
      () => readTerminal(Terminal.Rem, ip),
    ).view.flatMap(_.apply()).headOption

    firstApplicableTerminal match {
      case Some((value, ip)) => (NonTerminal.MultiplicativeOperation(value) -> ip).asRight
      case None => new IllegalStateException("Error: NT multiplicative operation").asLeft
    }
  }
}
