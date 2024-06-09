package com.wrathenn.compilers
package readers.instances

import models.G5.NonTerminal
import readers.{InputPointer, NonTerminalReader}

import cats.syntax.all._

object ProgramReader extends NonTerminalReader[NonTerminal.Program] {
  override def read(ip: InputPointer): Either[Exception, (NonTerminal.Program, InputPointer)] = for {
    ip <- ip.skipEmpty().asRight
    first <- ip.getFirstEither
    _ <- if (!NonTerminal.Program.first(first)) new IllegalStateException(s"Wrong first [Program]: $ip").asLeft else ().asRight

    (block, ip0) <- BlockReader.read(ip)
    ip1 = ip0.skipEmpty()
    _ <- ip1.getFirst() match {
      case Some(_) => new IllegalStateException(s"Some unneccessary symbols after Program: $ip1").asLeft
      case None => ().asRight
    }
  } yield NonTerminal.Program(block = block) -> ip0
}
