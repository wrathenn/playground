package com.wrathenn.compilers

import models.G5.{NonTerminal, Terminal}
import readers.InputPointer
import readers.instances._

import cats.effect.testing.scalatest.AsyncIOSpec
import com.wrathenn.compilers.readers.postfix.{expressionPostfixWriter, relationPostfixWriter}
import org.scalatest.Checkpoints.Checkpoint
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{Failed, Succeeded}


class ReversePolishNotationTests extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  def ipFromString(data: String) = new InputPointer(data = data.toCharArray.toList)
  val emptyEp = new InputPointer(data = List())

  "Reverse Polish Notation Tests" - {
    "-1" in {
      val ip = ipFromString("-1")
      val (r, _) = ExpressionReader.read(ip).getOrElse(throw new IllegalStateException("Can't parse expression"))
      assertResult("0 1 -")(expressionPostfixWriter.write(r))
    }

    "-1 + 6" in {
      val ip = ipFromString("-1 + 6")
      val (r, _) = ExpressionReader.read(ip).getOrElse(throw new IllegalStateException("Can't parse expression"))
      assertResult("0 1 - 6 +")(expressionPostfixWriter.write(r))
    }

    "a + b + c * d - e" in {
      val ip = ipFromString("a + b + c * d - e")
      val (r, _) = ExpressionReader.read(ip).getOrElse(throw new IllegalStateException("Can't parse expression"))
      assertResult("a b + c d * + e -")(expressionPostfixWriter.write(r))
    }

    "2 ** 2 > 3 ** 3 and 7 * 7 + 1 or 123" in {
      val ip = ipFromString("2 ** 2 > 3 ** 3 and 7 * 7 + 1 or 123")
      val (r, _) = ExpressionReader.read(ip).getOrElse(throw new IllegalStateException("Can't parse expression"))
      assertResult("2 2 ** 3 3 ** > 7 7 * 1 + and 123 or")(expressionPostfixWriter.write(r))
    }

    "(1 * 2 * 3 * 4 * 5)" in {
      val ip = ipFromString("1 2 * 3 * 4 * 5 *")
      val (r, _) = ExpressionReader.read(ip).getOrElse(throw new IllegalStateException("Can't parse expression"))
      assertResult("")(expressionPostfixWriter.write(r))
    }

    "a + (b + c) * d - e" in {
      val ip = ipFromString("a + (b + c) * d - e")
      val (r, _) = ExpressionReader.read(ip).getOrElse(throw new IllegalStateException("Can't parse expression"))
      assertResult("a b c + d * + e -")(expressionPostfixWriter.write(r))
    }

    "3 + 4 + 2 / 1 - 5  / 2 - 3" in {
      val ip = ipFromString("3 + 4 + 2 / 1 - 5  / 2 - 3")
      val (r, _) = ExpressionReader.read(ip).getOrElse(throw new IllegalStateException("Can't parse expression"))
      assertResult("3 4 + 2 1 / + 5 2 / - 3 -")(expressionPostfixWriter.write(r))
    }
  }
}
