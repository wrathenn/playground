package com.wrathenn.compilers
package readers

import models.G5
import models.G5.Terminal

import cats.syntax.all._

import scala.annotation.tailrec

trait NonTerminalReader[NT <: G5.NonTerminal] {
  def read(ip: InputPointer): Either[Exception, (NT, InputPointer)]
  protected def readOneContinuation[
    Splitter <: G5.NonTerminal,
    Target <: G5.NonTerminal,
  ](
    inputPointer: InputPointer,
    splitterReader: NonTerminalReader[Splitter],
    targetReader: NonTerminalReader[Target],
  ): Option[((Splitter, Target), InputPointer)] = for {
    ip0 <- inputPointer.skipEmpty().some
    (splitOp, ip1) <- splitterReader.read(ip0).toOption
    ip2 = ip1.skipEmpty()
    (target, ip3) <- targetReader.read(ip2).toOption
  } yield (splitOp, target) -> ip3

  @tailrec
  final protected def readContinuations[
    Splitter <: G5.NonTerminal,
    Target <: G5.NonTerminal,
  ](
    inputPointer: InputPointer,
    splitterReader: NonTerminalReader[Splitter],
    targetReader: NonTerminalReader[Target],
    acc: List[(Splitter, Target)],
  ): (List[(Splitter, Target)], InputPointer) = {
    readOneContinuation(inputPointer, splitterReader, targetReader) match {
      case Some((group, ipNext)) =>
        readContinuations(ipNext, splitterReader, targetReader, acc :+ group)
      case None => acc -> inputPointer
    }
  }
}

object NonTerminalReader {
  def readTerminal[T <: Terminal](terminalObject: T, ip: InputPointer): Option[(T, InputPointer)] = {
    val text = ip.getNextN(terminalObject.repr.length)

    if (text == terminalObject.repr) (terminalObject -> ip.step(terminalObject.repr.length)).some
    else None
  }
}
