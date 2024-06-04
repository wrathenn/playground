package com.wrathenn.compilers
package readers.instances

import models.G5.NonTerminal
import readers.{InputPointer, NonTerminalReader}
import cats.syntax.all._

object PrimaryReader extends NonTerminalReader[NonTerminal.Primary] {
  private def readNumeric(inputPointer: InputPointer): Option[(NonTerminal.Primary.NumericLiteral, InputPointer)] = for {
    (numeric, ip0) <- NumericLiteralReader.read(inputPointer).toOption
  } yield NonTerminal.Primary.NumericLiteral(numeric) -> ip0

  private def readVariable(inputPointer: InputPointer): Option[(NonTerminal.Primary.Variable, InputPointer)] = for {
    (variable, ip0) <- VariableReader.read(inputPointer).toOption
  } yield NonTerminal.Primary.Variable(variable) -> ip0

  private def readBracketExpr(inputPointer: InputPointer): Option[(NonTerminal.Primary.BracketExpr, InputPointer)] = for {
    firstChar <- inputPointer.getFirst()
    _ <- if (firstChar == '(') Some() else None
    ip0 = inputPointer.step(1)
    ip1 = ip0.skipEmpty()
    (expr, ip2) <- ExpressionReader.read(ip1).toOption
    ip3 = ip2.skipEmpty()
    lastChar <- ip3.getFirst()
    _ <- if (lastChar == ')') Some() else None
    ip4 = ip3.step(1)
  } yield NonTerminal.Primary.BracketExpr(expr) -> ip4

  override def read(ip: InputPointer): Either[Exception, (NonTerminal.Primary, InputPointer)] = for {
    first <- ip.getFirstEither
    _ <- if (!NonTerminal.Primary.first(first)) new IllegalStateException(s"Wrong first [Primary]: $ip").asLeft else ().asRight
    primary <- List(
      () => readNumeric(ip),
      () => readVariable(ip),
      () => readBracketExpr(ip),
    ).view.flatMap(_.apply()).headOption match {
      case Some(value) => value.asRight
      case None => new IllegalStateException(s"Error on reading [Primary]: $ip").asLeft
    }
  } yield primary
}
