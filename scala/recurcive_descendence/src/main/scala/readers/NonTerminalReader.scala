package com.wrathenn.compilers
package readers

import models.G5
import models.G5.{NonTerminal, Terminal}

import cats.syntax.all._

import scala.annotation.tailrec

trait NonTerminalReader[NT <: G5.NonTerminal] {
  def read(ip: InputPointer): Either[Exception, (NT, InputPointer)]
  protected def readOneContinuation[
    Splitter <: G5.NonTerminal,
    Target <: G5.NonTerminal,
  ](
    inputPointer: InputPointer,
    splitterReader: NonTerminalReader[Splitter],
    targetReader: NonTerminalReader[Target],
  ): Option[((Splitter, Target), InputPointer)] = for {
    ip0 <- inputPointer.skipEmpty().some
    (splitOp, ip1) <- splitterReader.read(ip0).toOption
    ip2 = ip1.skipEmpty()
    (target, ip3) <- targetReader.read(ip2).toOption
  } yield (splitOp, target) -> ip3

  @tailrec
  final protected def readContinuations[
    Splitter <: G5.NonTerminal,
    Target <: G5.NonTerminal,
  ](
    inputPointer: InputPointer,
    splitterReader: NonTerminalReader[Splitter],
    targetReader: NonTerminalReader[Target],
    acc: List[(Splitter, Target)],
  ): (List[(Splitter, Target)], InputPointer) = {
    readOneContinuation(inputPointer, splitterReader, targetReader) match {
      case Some((group, ipNext)) =>
        readContinuations(ipNext, splitterReader, targetReader, acc :+ group)
      case None => acc -> inputPointer
    }
  }
}

object NonTerminalReader {
  def readTerminal[T <: Terminal](terminalObject: T, ip: InputPointer): Option[(T, InputPointer)] = {
    val text = ip.getNextN(terminalObject.repr.length)

    if (text == terminalObject.repr) (terminalObject -> ip.step(terminalObject.repr.length)).some
    else None
  }

  val multiplicativeOperationReader: NonTerminalReader[NonTerminal.MultiplicativeOperation] = (ip: InputPointer) => {
    val firstApplicableTerminal = List(
      () => readTerminal(Terminal.*, ip),
      () => readTerminal(Terminal./, ip),
      () => readTerminal(Terminal.Mod, ip),
      () => readTerminal(Terminal.Rem, ip),
    ).view.flatMap(_.apply()).headOption

    firstApplicableTerminal match {
      case Some((value, ip)) => (NonTerminal.MultiplicativeOperation(value) -> ip).asRight
      case None => new IllegalStateException("Error: NT multiplicative operation").asLeft
    }
  }

  val unaryAdditiveOperationReader: NonTerminalReader[NonTerminal.UnaryAdditiveOperation] = new NonTerminalReader[NonTerminal.UnaryAdditiveOperation] {
    override def read(ip: InputPointer): Either[Exception, (NonTerminal.UnaryAdditiveOperation, InputPointer)] = {
      val firstApplicableTerminal = List(
        () => readTerminal(Terminal.+, ip),
        () => readTerminal(Terminal.-, ip),
      ).view.flatMap(_.apply()).headOption

      firstApplicableTerminal match {
        case Some((value, ip)) => (NonTerminal.UnaryAdditiveOperation(value) -> ip).asRight
        case None => new IllegalStateException("Error: NT unary additive operation").asLeft
      }
    }
  }

  val binaryAdditiveOperationReader: NonTerminalReader[NonTerminal.BinaryAdditiveOperation] = new NonTerminalReader[NonTerminal.BinaryAdditiveOperation] {
    override def read(ip: InputPointer): Either[Exception, (NonTerminal.BinaryAdditiveOperation, InputPointer)] = {
      val firstApplicableTerminal = List(
        () => readTerminal(Terminal.+, ip),
        () => readTerminal(Terminal.-, ip),
        () => readTerminal(Terminal.&, ip),
      ).view.flatMap(_.apply()).headOption

      firstApplicableTerminal match {
        case Some((value, ip)) => (NonTerminal.BinaryAdditiveOperation(value) -> ip).asRight
        case None => new IllegalStateException("Error: NT binary additive operation").asLeft
      }
    }
  }

  val relationOperationReader: NonTerminalReader[NonTerminal.RelationOperation] = new NonTerminalReader[NonTerminal.RelationOperation] {
    override def read(ip: InputPointer): Either[Exception, (NonTerminal.RelationOperation, InputPointer)] = {
      val firstApplicableTerminal = List(
        () => readTerminal(Terminal.<, ip),
        () => readTerminal(Terminal.<=, ip),
        () => readTerminal(Terminal.==, ip),
        () => readTerminal(Terminal./>, ip),
        () => readTerminal(Terminal.>, ip),
        () => readTerminal(Terminal.>=, ip),
      ).view.flatMap(_.apply()).headOption

      firstApplicableTerminal match {
        case Some((value, ip)) => (NonTerminal.RelationOperation(value) -> ip).asRight
        case None => new IllegalStateException("Error: NT relation operation").asLeft
      }
    }
  }

  val logicalOperationReader: NonTerminalReader[NonTerminal.LogicalOperation] = new NonTerminalReader[NonTerminal.LogicalOperation] {
    override def read(ip: InputPointer): Either[Exception, (NonTerminal.LogicalOperation, InputPointer)] = {
      val firstApplicableTerminal = List(
        () => readTerminal(Terminal.And, ip),
        () => readTerminal(Terminal.Or, ip),
        () => readTerminal(Terminal.Xor, ip),
      ).view.flatMap(_.apply()).headOption

      firstApplicableTerminal match {
        case Some((value, ip)) => (NonTerminal.LogicalOperation(value) -> ip).asRight
        case None => new IllegalStateException("Error: NT relation operation").asLeft
      }
    }
  }

  val numericLiteralReader: NonTerminalReader[NonTerminal.NumericLiteral] = new NonTerminalReader[NonTerminal.NumericLiteral] {
    override def read(ip: InputPointer): Either[Exception, (NonTerminal.NumericLiteral, InputPointer)] = {
      val repr = ip.takeWhile { c => c != '\n' && c != ' ' && c != '\t' }
      if (repr.isEmpty) return new IllegalStateException(s"Error: NT numeric literal is empty").asLeft
      if (repr.forall { _.isDigit } || ((repr.head == '-' || repr.head == '+') && repr.tail.forall { _.isDigit }) ) (NonTerminal.NumericLiteral(Terminal.NumericLiteral(repr)) -> ip.step(repr.length)).asRight
      else new IllegalStateException(s"Error: NT numeric literal incorrect $repr").asLeft
    }
  }

  val variableReader: NonTerminalReader[NonTerminal.Variable] = new NonTerminalReader[NonTerminal.Variable] {
    override def read(ip: InputPointer): Either[Exception, (NonTerminal.Variable, InputPointer)] = {
      val repr = ip.takeWhile { c => c != '\n' && c != ' ' && c != '\t' }
      if (repr.forall { _.isLetterOrDigit } && repr.exists { _.isLetter }) (NonTerminal.Variable(Terminal.Variable(repr)) -> ip.step(repr.length)).asRight
      else new IllegalStateException("Error: NT variable").asLeft
    }
  }

  val primaryReader: NonTerminalReader[NonTerminal.Primary] = new NonTerminalReader[NonTerminal.Primary] {
    private def readNumeric(inputPointer: InputPointer): Option[(NonTerminal.Primary.NumericLiteral, InputPointer)] = for {
      (numeric, ip0) <- numericLiteralReader.read(inputPointer).toOption
    } yield NonTerminal.Primary.NumericLiteral(numeric) -> ip0

    private def readVariable(inputPointer: InputPointer): Option[(NonTerminal.Primary.Variable, InputPointer)] = for {
      (variable, ip0) <- variableReader.read(inputPointer).toOption
    } yield NonTerminal.Primary.Variable(variable) -> ip0

    private def readBracketExpr(inputPointer: InputPointer): Option[(NonTerminal.Primary.BracketExpr, InputPointer)] = for {
      firstChar <- inputPointer.getFirst()
      _ <- if (firstChar == '(') Some() else None
      ip0 = inputPointer.step(1)
      ip1 = ip0.skipEmpty()
      (expr, ip2) <- expressionReader.read(ip1).toOption
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

  val multiplierReader: NonTerminalReader[NonTerminal.Multiplier] = new NonTerminalReader[NonTerminal.Multiplier] {
    private def readAbs(inputPointer: InputPointer): Option[(NonTerminal.Multiplier.Abs, InputPointer)] = for {
      (_, ip0) <- readTerminal(Terminal.Abs, inputPointer)
      ip1 <- ip0.skipEmptyNeccessarily().toOption
      (primary, ip2) <- primaryReader.read(ip1).toOption
    } yield NonTerminal.Multiplier.Abs(primary) -> ip2

    private def readNot(inputPointer: InputPointer): Option[(NonTerminal.Multiplier.Not, InputPointer)] = for {
      (_, ip0) <- readTerminal(Terminal.Not, inputPointer)
      ip1 <- ip0.skipEmptyNeccessarily().toOption
      (primary, ip2) <- primaryReader.read(ip1).toOption
    } yield NonTerminal.Multiplier.Not(primary) -> ip2

    private def readOnePower(inputPointer: InputPointer): Option[(NonTerminal.Primary, InputPointer)] = {
      val ip0 = inputPointer.skipEmpty()
      for {
        (_, ip1) <- readTerminal(Terminal.**, ip0)
        ip2 = ip1.skipEmpty()
        (primary, ip3) <- primaryReader.read(ip2).toOption
      } yield primary -> ip3
    }

    @tailrec
    private def readAllPowers(inputPointer: InputPointer, acc: List[NonTerminal.Primary]): (List[NonTerminal.Primary], InputPointer) = {
      readOnePower(inputPointer) match {
        case Some((primary, ipNext)) => readAllPowers(ipNext, acc :+ primary)
        case None => acc -> inputPointer
      }
    }

    private def readPowers(inputPointer: InputPointer): Option[(NonTerminal.Multiplier.Powers, InputPointer)] = for {
      (p1, ip0) <- primaryReader.read(inputPointer).toOption
      (otherPowers, ip1) = readAllPowers(ip0, List())
    } yield NonTerminal.Multiplier.Powers(p = p1, other = otherPowers) -> ip1

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

  val summandReader: NonTerminalReader[NonTerminal.Summand] = new NonTerminalReader[NonTerminal.Summand] {
    override def read(ip: InputPointer): Either[Exception, (NonTerminal.Summand, InputPointer)] = for {
      first <- ip.getFirstEither
      _ <- if (!NonTerminal.Summand.first(first)) new IllegalStateException(s"Wrong first [Summand]: $ip").asLeft else ().asRight
      (multiplier0, ip0) <- multiplierReader.read(ip)
      (other, ip1) = readContinuations(ip0, multiplicativeOperationReader, multiplierReader, List())
    } yield NonTerminal.Summand(firstMultiplier = multiplier0, other = other) -> ip1
  }

  val simpleExprReader: NonTerminalReader[NonTerminal.SimpleExpr] = new NonTerminalReader[NonTerminal.SimpleExpr] {
    override def read(ip: InputPointer): Either[Exception, (NonTerminal.SimpleExpr, InputPointer)] = for {
      first <- ip.getFirstEither
      _ <- if (!NonTerminal.SimpleExpr.first(first)) new IllegalStateException(s"Wrong first [SimpleExpr]: $ip").asLeft else ().asRight
      (unaryOp, ip0) <- unaryAdditiveOperationReader.read(ip) match {
        case Left(_) => (None -> ip).asRight
        case Right((unary, ip)) => (unary.some -> ip).asRight
      }
      (firstSummand, ip1) <- summandReader.read(ip0)
      (other, ip2) = readContinuations(ip1, binaryAdditiveOperationReader, summandReader, List())
    } yield NonTerminal.SimpleExpr(
      unaryAdditiveOperation = unaryOp,
      summand = firstSummand,
      otherSummands = other,
    ) -> ip2
  }

  val relationReader: NonTerminalReader[NonTerminal.Relation] = new NonTerminalReader[NonTerminal.Relation] {
    override def read(ip: InputPointer): Either[Exception, (NonTerminal.Relation, InputPointer)] = for {
      first <- ip.getFirstEither
      _ <- if (!NonTerminal.Relation.first(first)) new IllegalStateException(s"Wrong first [SimpleExpr]: $ip").asLeft else ().asRight
      (simpleExpr, ip0) <- simpleExprReader.read(ip)
      (cont, ip1) <- readOneContinuation(ip0, relationOperationReader, simpleExprReader) match {
        case Some((value, ip)) => (value.some -> ip).asRight
        case None => (None -> ip0).asRight
      }
    } yield NonTerminal.Relation(simpleExpr = simpleExpr, otherSimpleExpr = cont) -> ip1
  }


  val expressionReader: NonTerminalReader[NonTerminal.Expression] = new NonTerminalReader[NonTerminal.Expression] {
    override def read(ip: InputPointer): Either[Exception, (NonTerminal.Expression, InputPointer)] = for {
      first <- ip.getFirstEither
      _ <- if (!NonTerminal.Expression.first(first)) new IllegalStateException(s"Wrong first [Expression]: $ip").asLeft else ().asRight
      (rel0, ip0) <- relationReader.read(ip)
      (other, ip1) = readContinuations(ip0, logicalOperationReader, relationReader, List())
    } yield NonTerminal.Expression(relation = rel0, otherExpressions = other) -> ip1
  }
}
