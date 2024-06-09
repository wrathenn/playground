package com.wrathenn.compilers
package readers.instances

import models.G5.{NonTerminal, Terminal}
import readers.{InputPointer, NonTerminalReader}

import cats.syntax.all._
import com.wrathenn.compilers.models.G5

object NumericLiteralReader extends NonTerminalReader[NonTerminal.NumericLiteral] {
  override def read(ip: InputPointer): Either[Exception, (NonTerminal.NumericLiteral, InputPointer)] = for {
    first <- ip.getFirstEither
    (minusOrPlus, ip0) = if (Set('-', '+').contains(first)) first.toString -> ip.step(1) else "" -> ip
    repr = ip0.takeWhile { _.isDigit }
    _ <- if (repr.isEmpty) new IllegalStateException(s"Error: numeric literal is empty").asLeft else ().asRight
  } yield NonTerminal.NumericLiteral(Terminal.NumericLiteral(minusOrPlus + repr)) -> ip0.step(repr.length)
}
