package com.wrathenn.compilers
package readers.instances

import models.G5.{NonTerminal, Terminal}
import readers.NonTerminalReader.readTerminal
import readers.{InputPointer, NonTerminalReader}

import cats.syntax.all._

object OperatorReader extends NonTerminalReader[NonTerminal.Operator] {
  override def read(ip: InputPointer): Either[Exception, (NonTerminal.Operator, InputPointer)] = for {
    first <- ip.getFirstEither
    _ <- if (!NonTerminal.Operator.first(first)) new IllegalStateException(s"Wrong first [Operator]: $ip").asLeft else ().asRight
    (identifier, ip0) <- IdentifierReader.read(ip)
    ip1 = ip0.skipEmpty()

    (_, ip2) <- readTerminal(Terminal.Assigment, ip1) match {
      case Some(value) => value.asRight
      case None => new IllegalStateException(s"Assigment operator nor found [Operator]: $ip1").asLeft
    }
    ip3 = ip2.skipEmpty()

    (expr, ip4) <- ExpressionReader.read(ip3)
  } yield NonTerminal.Operator(identifier = identifier, expr = expr) -> ip4
}