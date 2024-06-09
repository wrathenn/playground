package com.wrathenn.compilers
package readers.instances

import models.G5.NonTerminal
import readers.{InputPointer, NonTerminalReader}

import cats.syntax.all._

object BlockReader extends NonTerminalReader[NonTerminal.Block] {
  override def read(ip: InputPointer): Either[Exception, (NonTerminal.Block, InputPointer)] = for {
    first <- ip.getFirstEither
    _ <- if (!NonTerminal.Block.first(first)) new IllegalStateException(s"Wrong first [Block]: $ip").asLeft else ().asRight

    ip00 = ip.step(1).skipEmpty()

    (operatorList, ip0) <- OperatorListReader.read(ip00)
    ip1 = ip0.skipEmpty()

    last <- ip1.getFirstEither
    _ <- if (last != '}') new IllegalStateException(s"Block should be closed with }: $ip").asLeft else ().asRight
    ip2 = ip1.step(1)
  } yield NonTerminal.Block(operatorList = operatorList) -> ip2
}
