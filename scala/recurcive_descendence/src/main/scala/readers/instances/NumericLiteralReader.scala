package com.wrathenn.compilers
package readers.instances

import models.G5.{NonTerminal, Terminal}
import readers.{InputPointer, NonTerminalReader}
import cats.syntax.all._

object NumericLiteralReader extends NonTerminalReader[NonTerminal.NumericLiteral] {
  override def read(ip: InputPointer): Either[Exception, (NonTerminal.NumericLiteral, InputPointer)] = {
    val repr = ip.takeWhile { c => c != '\n' && c != ' ' && c != '\t' }
    if (repr.isEmpty) return new IllegalStateException(s"Error: NT numeric literal is empty").asLeft
    if (repr.forall { _.isDigit } || ((repr.head == '-' || repr.head == '+') && repr.tail.forall { _.isDigit }) ) (NonTerminal.NumericLiteral(Terminal.NumericLiteral(repr)) -> ip.step(repr.length)).asRight
    else new IllegalStateException(s"Error: NT numeric literal incorrect $repr").asLeft
  }
}
