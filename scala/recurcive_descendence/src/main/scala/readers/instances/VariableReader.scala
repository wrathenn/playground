package com.wrathenn.compilers
package readers.instances

import models.G5.{NonTerminal, Terminal}
import readers.{InputPointer, NonTerminalReader}

import cats.syntax.all._

object VariableReader extends NonTerminalReader[NonTerminal.Variable] {
  override def read(ip: InputPointer): Either[Exception, (NonTerminal.Variable, InputPointer)] = for {
    first <- ip.getFirstEither
    _ <- if (first.isDigit) new IllegalStateException(s"Variable can't start with digit, $ip").asLeft else ().asRight
    repr = ip.takeWhile { _.isLetterOrDigit }
  } yield NonTerminal.Variable(Terminal.Variable(repr)) -> ip.step(repr.length)
}
