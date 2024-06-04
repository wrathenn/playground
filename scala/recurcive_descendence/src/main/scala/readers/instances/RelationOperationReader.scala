package com.wrathenn.compilers
package readers.instances

import models.G5.{NonTerminal, Terminal}
import readers.NonTerminalReader.readTerminal
import readers.{InputPointer, NonTerminalReader}
import cats.syntax.all._

object RelationOperationReader extends NonTerminalReader[NonTerminal.RelationOperation] {
  override def read(ip: InputPointer): Either[Exception, (NonTerminal.RelationOperation, InputPointer)] = {
    val firstApplicableTerminal = List(
      () => readTerminal(Terminal.<, ip),
      () => readTerminal(Terminal.<=, ip),
      () => readTerminal(Terminal.==, ip),
      () => readTerminal(Terminal./>, ip),
      () => readTerminal(Terminal.>, ip),
      () => readTerminal(Terminal.>=, ip),
    ).view.flatMap(_.apply()).headOption

    firstApplicableTerminal match {
      case Some((value, ip)) => (NonTerminal.RelationOperation(value) -> ip).asRight
      case None => new IllegalStateException("Error: NT relation operation").asLeft
    }
  }
}
