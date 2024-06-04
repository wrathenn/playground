package com.wrathenn.compilers
package readers.instances

import models.G5.NonTerminal
import readers.{InputPointer, NonTerminalReader}
import cats.syntax.all._

object ExpressionReader extends NonTerminalReader[NonTerminal.Expression] {
  override def read(ip: InputPointer): Either[Exception, (NonTerminal.Expression, InputPointer)] = for {
    first <- ip.getFirstEither
    _ <- if (!NonTerminal.Expression.first(first)) new IllegalStateException(s"Wrong first [Expression]: $ip").asLeft else ().asRight
    (rel0, ip0) <- RelationReader.read(ip)
    (other, ip1) = readContinuations(ip0, LogicalOperationReader, RelationReader, List())
  } yield NonTerminal.Expression(relation = rel0, otherExpressions = other) -> ip1
}
