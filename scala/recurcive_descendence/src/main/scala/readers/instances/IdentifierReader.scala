package com.wrathenn.compilers
package readers.instances

import models.G5.{NonTerminal, Terminal}
import readers.NonTerminalReader.readTerminal
import readers.{InputPointer, NonTerminalReader}

import cats.syntax.all._

object IdentifierReader extends NonTerminalReader[NonTerminal.Identifier] {
  override def read(ip: InputPointer): Either[Exception, (NonTerminal.Identifier, InputPointer)] = for {
    first <- ip.getFirstEither
    _ <- if (!NonTerminal.Primary.first(first)) new IllegalStateException(s"Wrong first [Primary]: $ip").asLeft else ().asRight
    (decl, ip0) <- List(
      () => readTerminal(Terminal.`val`, ip),
      () => readTerminal(Terminal.`var`, ip),
    ).view.flatMap(_.apply()).headOption match {
      case Some(value) => value.asRight
      case None => new IllegalStateException(s"Couldn't read identifier declaration: $ip").asLeft
    }
    ip1 = ip0.skipEmpty()
    (variable, ip2) <- VariableReader.read(ip1)
  } yield NonTerminal.Identifier(
    decl = decl,
    variable = variable,
  ) -> ip2
}
