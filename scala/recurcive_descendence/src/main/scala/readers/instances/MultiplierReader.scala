package com.wrathenn.compilers
package readers.instances

import models.G5.{NonTerminal, Terminal}
import readers.{InputPointer, NonTerminalReader}
import readers.NonTerminalReader.readTerminal

import scala.annotation.tailrec
import cats.syntax.all._

object MultiplierReader extends NonTerminalReader[NonTerminal.Multiplier] {
  private def readAbs(inputPointer: InputPointer): Option[(NonTerminal.Multiplier.Abs, InputPointer)] = for {
    (_, ip0) <- readTerminal(Terminal.Abs, inputPointer)
    ip1 <- ip0.skipEmptyNeccessarily().toOption
    (primary, ip2) <- PrimaryReader.read(ip1).toOption
  } yield NonTerminal.Multiplier.Abs(primary) -> ip2

  private def readNot(inputPointer: InputPointer): Option[(NonTerminal.Multiplier.Not, InputPointer)] = for {
    (_, ip0) <- readTerminal(Terminal.Not, inputPointer)
    ip1 <- ip0.skipEmptyNeccessarily().toOption
    (primary, ip2) <- PrimaryReader.read(ip1).toOption
  } yield NonTerminal.Multiplier.Not(primary) -> ip2

  private def readOnePower(inputPointer: InputPointer): Option[(NonTerminal.Primary, InputPointer)] = {
    val ip0 = inputPointer.skipEmpty()
    for {
      (_, ip1) <- readTerminal(Terminal.**, ip0)
      ip2 = ip1.skipEmpty()
      (primary, ip3) <- PrimaryReader.read(ip2).toOption
    } yield primary -> ip3
  }

  @tailrec
  private def readAllPowers(inputPointer: InputPointer, acc: List[NonTerminal.Primary]): (List[NonTerminal.Primary], InputPointer) = {
    readOnePower(inputPointer) match {
      case Some((primary, ipNext)) => readAllPowers(ipNext, acc :+ primary)
      case None => acc -> inputPointer
    }
  }

  private def readPowers(inputPointer: InputPointer): Option[(NonTerminal.Multiplier.PrimaryPowers, InputPointer)] = for {
    (p1, ip0) <- PrimaryReader.read(inputPointer).toOption
    (otherPowers, ip1) = readAllPowers(ip0, List())
  } yield NonTerminal.Multiplier.PrimaryPowers(p = p1, other = otherPowers) -> ip1

  override def read(ip: InputPointer): Either[Exception, (NonTerminal.Multiplier, InputPointer)] = for {
    first <- ip.getFirstEither
    _ <- if (!NonTerminal.Multiplier.first(first)) new IllegalStateException(s"Wrong first [Multiplier]: $ip").asLeft else ().asRight
    multiplier <- List(
      () => readAbs(ip),
      () => readNot(ip),
      () => readPowers(ip),
    ).view.flatMap(_.apply()).headOption match {
      case Some(value) => value.asRight
      case None => new IllegalStateException(s"Error on reading [Multiplier]: $ip").asLeft
    }
  } yield multiplier
}
