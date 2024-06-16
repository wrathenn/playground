package com.wrathenn.compilers
package readers.instances

import models.G5.NonTerminal
import readers.{InputPointer, NonTerminalReader}

import cats.syntax.all._

object OperatorTailReader extends NonTerminalReader[NonTerminal.OperatorTail] {
  def _read(ip: InputPointer): Either[Exception, (NonTerminal.OperatorTail, InputPointer)] = for {
    first <- ip.getNextN(1).asRight
    res <- if (first == "" || !NonTerminal.OperatorTail.first(first.charAt(0))) Right(NonTerminal.OperatorTail.Nil -> ip)
    else for {
      ip0 <- ip.step(1).asRight
      ip1 <- ip0.skipEmpty().asRight
      (operator, ip2) <- OperatorReader.read(ip1)
      ip3 = ip2.skipEmpty()
      (tail, ip4) <- OperatorTailReader.read(ip3)
    } yield NonTerminal.OperatorTail.ListCell(car = operator, cdr = tail) -> ip4
  } yield res

  override def read(ip: InputPointer): Either[Exception, (NonTerminal.OperatorTail, InputPointer)] = for {
    first <- ip.getFirstEither
    ip0 <- if (NonTerminal.OperatorTail.first(first)) ip.step(1).skipEmpty().asRight
           else new IllegalStateException(s"Wrong first for OperatorTail: $ip").asLeft
    res <- ip0.getFirstEither match {
      case Left(_) => (NonTerminal.OperatorTail.Nil -> ip0).asRight
      case Right(firstOp) => if (NonTerminal.Operator.first(firstOp)) for {
        (operator, ip1) <- OperatorReader.read(ip0)
        ip2 = ip1.skipEmpty()
        (tail, ip3) <- OperatorTailReader.read(ip2)
      } yield NonTerminal.OperatorTail.ListCell(car = operator, cdr = tail) -> ip3
      else (NonTerminal.OperatorTail.Nil -> ip0).asRight
    }
  } yield res
}
