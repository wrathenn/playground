package com.wrathenn.compilers
package readers.instances

import models.G5.NonTerminal
import readers.{InputPointer, NonTerminalReader}

import cats.syntax.all._

object SimpleExprReader extends NonTerminalReader[NonTerminal.SimpleExpr] {
  override def read(ip: InputPointer): Either[Exception, (NonTerminal.SimpleExpr, InputPointer)] = for {
    first <- ip.getFirstEither
    _ <- if (!NonTerminal.SimpleExpr.first(first)) new IllegalStateException(s"Wrong first [SimpleExpr]: $ip").asLeft else ().asRight
    (unaryOp, ip0) <- UnaryAdditiveOperationReader.read(ip) match {
      case Left(_) => (None -> ip).asRight
      case Right((unary, ip)) => (unary.some -> ip).asRight
    }
    (firstSummand, ip1) <- SummandReader.read(ip0)
    (other, ip2) = readContinuations(ip1, BinaryAdditiveOperationReader, SummandReader, List())
  } yield NonTerminal.SimpleExpr(
    unaryAdditiveOperation = unaryOp,
    summand = firstSummand,
    otherSummands = other,
  ) -> ip2
}
