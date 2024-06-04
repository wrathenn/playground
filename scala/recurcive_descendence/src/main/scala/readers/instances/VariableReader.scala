package com.wrathenn.compilers
package readers.instances

import models.G5.{NonTerminal, Terminal}
import readers.{InputPointer, NonTerminalReader}
import cats.syntax.all._

object VariableReader extends NonTerminalReader[NonTerminal.Variable] {
  override def read(ip: InputPointer): Either[Exception, (NonTerminal.Variable, InputPointer)] = {
    val repr = ip.takeWhile { c => c != '\n' && c != ' ' && c != '\t' }
    if (repr.forall { _.isLetterOrDigit } && repr.exists { _.isLetter }) (NonTerminal.Variable(Terminal.Variable(repr)) -> ip.step(repr.length)).asRight
    else new IllegalStateException("Error: NT variable").asLeft
  }
}
