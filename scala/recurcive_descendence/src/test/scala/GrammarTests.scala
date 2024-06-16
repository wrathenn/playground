package com.wrathenn.compilers


import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import cats.syntax.all._
import com.wrathenn.compilers.models.G5.{NonTerminal, Terminal}
import com.wrathenn.compilers.readers.instances.{BlockReader, ExpressionReader, IdentifierReader, MultiplicativeOperationReader, MultiplierReader, NumericLiteralReader, OperatorListReader, OperatorReader, PrimaryReader, ProgramReader, RelationReader, SimpleExprReader, SummandReader, VariableReader}
import com.wrathenn.compilers.readers.{InputPointer, NonTerminalReader}
import org.scalatest.Checkpoints.Checkpoint
import org.scalatest.{Failed, Succeeded}
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.xml.PrettyPrinter


class GrammarTests extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  def ipFromString(data: String) = new InputPointer(data = data.toCharArray.toList)
  val emptyEp = new InputPointer(data = List())

  "Multiplicative Operation Reader" - {
    "*" in {
      val ip = ipFromString("*")
      val r = MultiplicativeOperationReader.read(ip)
      println(r)

      r match {
        case Left(value) => assert(false, value.getMessage)
        case Right((value, ip)) => {
          val cp = new Checkpoint
          cp { value shouldBe NonTerminal.MultiplicativeOperation(Terminal.*)}
          cp { ip shouldBe emptyEp }
          cp.reportAll()
          Succeeded
        }
      }
    }
  }

  "Numeric Literal Reader" - {
    "positive" in {
      val ip = ipFromString("12364 7")
      val r = NumericLiteralReader.read(ip)
      println(r)

      r match {
        case Left(value) => assert(false, value.getMessage)
        case Right((value, ip)) => {
          val cp = new Checkpoint
          cp { value shouldBe NonTerminal.NumericLiteral(Terminal.NumericLiteral("12364"))}
          cp { ip shouldBe ipFromString(" 7") }
          cp.reportAll()
          Succeeded
        }
      }
    }
    "negative" in {
      val ip = ipFromString("-12364 7")
      val r = NumericLiteralReader.read(ip)
      println(r)

      r match {
        case Left(value) => assert(false, value.getMessage)
        case Right((value, ip)) => {
          val cp = new Checkpoint
          cp { value shouldBe NonTerminal.NumericLiteral(Terminal.NumericLiteral("-12364"))}
          cp { ip shouldBe ipFromString(" 7") }
          cp.reportAll()
          Succeeded
        }
      }
    }
  }

  "Variable Reader" - {
    "correct" in {
      val ip = ipFromString("123d456 7")
      val r = VariableReader.read(ip)
      println(r)

      r match {
        case Left(value) => assert(false, value.getMessage)
        case Right((value, ip)) => {
          val cp = new Checkpoint
          cp { value shouldBe NonTerminal.Variable(Terminal.Variable("123d456"))}
          cp { ip shouldBe ipFromString(" 7") }
          cp.reportAll()
          Succeeded
        }
      }
    }
  }

  "Primary Reader" - {
    "numeric literal" in {
      val ip = ipFromString("123456 7")
      val r = PrimaryReader.read(ip)
      println(r)

      r match {
        case Left(value) => assert(false, value.getMessage)
        case Right((value, ip)) => {
          val cp = new Checkpoint
          cp { value shouldBe NonTerminal.Primary.NumericLiteral(
            NonTerminal.NumericLiteral(
              Terminal.NumericLiteral("123456")
            )
          )}
          cp { ip shouldBe ipFromString(" 7") }
          cp.reportAll()
          Succeeded
        }
      }
    }

    "variable" in {
      val ip = ipFromString("asd 7")
      val r = PrimaryReader.read(ip)
      println(r)

      r match {
        case Left(value) => assert(false, value.getMessage)
        case Right((value, ip)) => {
          val cp = new Checkpoint
          cp { value shouldBe NonTerminal.Primary.Variable(
            NonTerminal.Variable(
              Terminal.Variable("asd")
            )
          )}
          cp { ip shouldBe ipFromString(" 7") }
          cp.reportAll()
          Succeeded
        }
      }
    }
  }

  "Multiplier Reader" - {
    "abs numeric" in {
      val ip = ipFromString("abs -123 7")
      val r = MultiplierReader.read(ip)
      println(r)

      r match {
        case Left(value) => Failed(value.getMessage).toSucceeded
        case Right((value, ip)) => {
          val cp = new Checkpoint
          cp { value shouldBe NonTerminal.Multiplier.Abs(
            p = NonTerminal.Primary.NumericLiteral(
              nonTerminal = NonTerminal.NumericLiteral(
                Terminal.NumericLiteral("-123")
              )
            )
          )}
          cp { ip shouldBe ipFromString(" 7") }
          cp.reportAll()
          Succeeded
        }
      }
    }
    "abs variable" in {
      val ip = ipFromString("abs test")
      val r = MultiplierReader.read(ip)
      println(r)

      r match {
        case Left(value) => Failed(value.getMessage).toSucceeded
        case Right((value, ip)) => {
          val cp = new Checkpoint
          cp { value shouldBe NonTerminal.Multiplier.Abs(
            p = NonTerminal.Primary.Variable(
              nonTerminal = NonTerminal.Variable(
                Terminal.Variable("test")
              )
            )
          )}
          cp { ip shouldBe ipFromString("") }
          cp.reportAll()
          Succeeded
        }
      }
    }
    "not variable" in {
      val ip = ipFromString("not test")
      val r = MultiplierReader.read(ip)
      println(r)

      r match {
        case Left(value) => Failed(value.getMessage).toSucceeded
        case Right((value, ip)) => {
          val cp = new Checkpoint
          cp { value shouldBe NonTerminal.Multiplier.Not(
            p = NonTerminal.Primary.Variable(
              nonTerminal = NonTerminal.Variable(
                Terminal.Variable("test")
              )
            )
          )}
          cp { ip shouldBe ipFromString("") }
          cp.reportAll()
          Succeeded
        }
      }
    }
  }

  "Summand reader" - {
    "single" in {
      val ip = ipFromString("abs 123")
      val r = SummandReader.read(ip)
      println(r)
      Succeeded
    }

    "with other" in {
      val ip = ipFromString("123 * not 12 * abs 727")
      val r = SummandReader.read(ip)
      println(r)
      Succeeded

    }
  }

  "SimpleExpr reader" - {
    "single" in {
      val ip = ipFromString("+asd ** 2")
      val r = SimpleExprReader.read(ip)
      println(r)
      Succeeded
    }

    "with other" in {
      val ip = ipFromString("asd & 2 & 3 + 132")
      val r = SimpleExprReader.read(ip)
      println(r)
      Succeeded
    }
  }

  "Relation reader" - {
    "single" in {
      val ip = ipFromString("2 ** 2 > 3 ** 3")
      val r = RelationReader.read(ip)
      println(r)
      Succeeded
    }
  }

  "Expression reader" - {
    "single" in {
      val ip = ipFromString("2 ** 2 > 3 ** 3 and 7 * 7 or 123")
      val r = ExpressionReader.read(ip)
      pprint.pprintln(r)
      println(r)
      Succeeded
    }
  }

  "Identifier reader" - {
    "single" in {
      val ip = ipFromString("val a = 2 ** 2 > 3 ** 3 and 7 * 7 or 123")
      val r = IdentifierReader.read(ip)
      pprint.pprintln(r)
      println(r)
      Succeeded
    }
  }

  "Operator reader" - {
    "single" in {
      val ip = ipFromString("val a = 2 ** 2 > 3 ** 3 and 7 * 7 or 123")
      val r = OperatorReader.read(ip)
      pprint.pprintln(r)
      println(r)
      Succeeded
    }
  }

  "OperatorList reader" - {
    "single" in {
      val ip = ipFromString("var a = 2; val b = 3")
      val r = OperatorListReader.read(ip)
      pprint.pprintln(r)
      println(r)
      Succeeded
    }
  }

  "Block reader" - {
    "single" in {
      val ip = ipFromString(
        """
           {
            var a = 2;
            val b = 3
           }
        """.stripMargin)
      val ip0 = ip.skipEmpty()
      val r = BlockReader.read(ip0)
      pprint.pprintln(r)
      println(r)
      Succeeded
    }
  }

  "Program reader" - {
    "show" in {
      val ip = ipFromString(
        """{
            var a = 2 < 5 and 3 or 5;
            val b = 3;
           }""")
      val ip0 = ip.skipEmpty()
      val r = ProgramReader.read(ip0)
      pprint.pprintln(r)
      println(r)
      Succeeded
    }
  }
}
