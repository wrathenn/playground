package com.wrathenn.compilers
package readers.instances

import models.G5.{NonTerminal, Terminal}
import readers.NonTerminalReader.readTerminal
import readers.{InputPointer, NonTerminalReader}
import cats.syntax.all._

object BinaryAdditiveOperationReader extends NonTerminalReader[NonTerminal.BinaryAdditiveOperation] {
    override def read(ip: InputPointer): Either[Exception, (NonTerminal.BinaryAdditiveOperation, InputPointer)] = {
      val firstApplicableTerminal = List(
        () => readTerminal(Terminal.+, ip),
        () => readTerminal(Terminal.-, ip),
        () => readTerminal(Terminal.&, ip),
      ).view.flatMap(_.apply()).headOption

      firstApplicableTerminal match {
        case Some((value, ip)) => (NonTerminal.BinaryAdditiveOperation(value) -> ip).asRight
        case None => new IllegalStateException("Error: NT binary additive operation").asLeft
      }
    }
}
