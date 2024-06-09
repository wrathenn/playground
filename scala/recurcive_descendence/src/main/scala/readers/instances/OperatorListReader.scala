package com.wrathenn.compilers
package readers.instances

import models.G5.NonTerminal
import readers.{InputPointer, NonTerminalReader}
import cats.syntax.all._

object OperatorListReader extends NonTerminalReader[NonTerminal.OperatorList] {
  override def read(ip: InputPointer): Either[Exception, (NonTerminal.OperatorList, InputPointer)] = for {
    first <- ip.getFirstEither
    _ <- if (!NonTerminal.OperatorList.first(first)) new IllegalStateException(s"Wrong first [OperatorList]: $ip").asLeft else ().asRight

    (operator, ip0) <- OperatorReader.read(ip)
    ip1 = ip0.skipEmpty()

    (operatorTail, ip2) <- OperatorTailReader.read(ip1)
  } yield NonTerminal.OperatorList(operator = operator, operatorTail = operatorTail) -> ip2
}
