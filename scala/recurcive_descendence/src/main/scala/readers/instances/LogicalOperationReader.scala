package com.wrathenn.compilers
package readers.instances

import models.G5.{NonTerminal, Terminal}
import readers.{InputPointer, NonTerminalReader}
import readers.NonTerminalReader.readTerminal
import cats.syntax.all._

object LogicalOperationReader extends NonTerminalReader[NonTerminal.LogicalOperation] {
  override def read(ip: InputPointer): Either[Exception, (NonTerminal.LogicalOperation, InputPointer)] = {
    val firstApplicableTerminal = List(
      () => readTerminal(Terminal.And, ip),
      () => readTerminal(Terminal.Or, ip),
      () => readTerminal(Terminal.Xor, ip),
    ).view.flatMap(_.apply()).headOption

    firstApplicableTerminal match {
      case Some((value, ip)) => (NonTerminal.LogicalOperation(value) -> ip).asRight
      case None => new IllegalStateException("Error: NT relation operation").asLeft
    }
  }
}
