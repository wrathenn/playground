package com.wrathenn.compilers
package readers.instances

import models.G5.NonTerminal
import readers.{InputPointer, NonTerminalReader}
import cats.syntax.all._

object SummandReader extends NonTerminalReader[NonTerminal.Summand] {
  override def read(ip: InputPointer): Either[Exception, (NonTerminal.Summand, InputPointer)] = for {
    first <- ip.getFirstEither
    _ <- if (!NonTerminal.Summand.first(first)) new IllegalStateException(s"Wrong first [Summand]: $ip").asLeft else ().asRight
    (multiplier0, ip0) <- MultiplierReader.read(ip)
    (other, ip1) = readContinuations(ip0, MultiplicativeOperationReader, MultiplierReader, List())
  } yield NonTerminal.Summand(firstMultiplier = multiplier0, other = other) -> ip1
}
