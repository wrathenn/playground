package com.wrathenn.compilers
package readers.instances

import models.G5.NonTerminal
import readers.{InputPointer, NonTerminalReader}

import cats.syntax.all._

object RelationReader extends NonTerminalReader[NonTerminal.Relation] {
  override def read(ip: InputPointer): Either[Exception, (NonTerminal.Relation, InputPointer)] = for {
    first <- ip.getFirstEither
    _ <- if (!NonTerminal.Relation.first(first)) new IllegalStateException(s"Wrong first [SimpleExpr]: $ip").asLeft else ().asRight
    (simpleExpr, ip0) <- SimpleExprReader.read(ip)
    (cont, ip1) <- readOneContinuation(ip0, RelationOperationReader, SimpleExprReader) match {
      case Some((value, ip)) => (value.some -> ip).asRight
      case None => (None -> ip0).asRight
    }
  } yield NonTerminal.Relation(simpleExpr = simpleExpr, otherSimpleExpr = cont) -> ip1
}
