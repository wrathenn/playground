package com.wrathenn.compilers

import util.TestGrammarReader

import cats.syntax.all._
import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import com.wrathenn.compilers.algs.GrammarExt._
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class GrammarTests extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "Deserialization" - {
    "G0" in {
      TestGrammarReader.getGrammar("g0.json")
        .assertNoException
    }

    "Grammar with Unreachable Rules" in {
      TestGrammarReader.getGrammar("eps-destroy-test.json")
        .assertNoException
    }

    "Grammar with Useless Rules" in {
       TestGrammarReader.getGrammar("useless-test.json")
         .assertNoException
    }

    "Grammar with Rules that lead to epsilon" in {
      TestGrammarReader.getGrammar("eps-destroy-test.json")
        .assertNoException
    }
  }

  // todo better tests
  "Algorithms" - {
    "Is Language empty" in {
      for {
        grammar <- TestGrammarReader.getGrammar("empty-lang-test.json")
        isEmpty = grammar.isLanguageEmpty
      } yield isEmpty
    }.asserting(_ shouldBe true)

    "Destroy Useless symbols" in {
      for {
        grammar <- TestGrammarReader.getGrammar("useless-test.json")
        result = grammar.destroyUselessSymbols
      } yield grammar.productions.size != result.productions.size
    }.asserting(_ shouldBe true)

    "Destroy Unreachable symbols" in {
      for {
        grammar <- TestGrammarReader.getGrammar("unreachable-test.json")
        result = grammar.destroyUselessSymbols
      } yield grammar.productions.size != result.productions.size
    }.asserting(_ shouldBe true)

    "Destroy Eps Rules" in {
      for {
        grammar <- TestGrammarReader.getGrammar("eps-destroy-test.json")
        result = grammar.destroyEpsRules
      } yield grammar.productions.size != result.productions.size
    }.asserting(_ shouldBe true)

    "Simple left recursion destroy" in {
      for {
        grammar <- TestGrammarReader.getGrammar("left-rec-simple-test.json")
        result = grammar.destroyLeftRecursion
        _ <- IO println result.show
      } yield ()
    }.assertNoException

    "Simple indirect left recursion destroy" in {
      for {
        grammar <- TestGrammarReader.getGrammar("left-rec-simple-indirect-test.json")
        result = grammar.destroyLeftRecursion
        _ <- IO println result.show
      } yield ()
    }.assertNoException

    "G0 left recursion destroy" in {
      for {
        grammar <- TestGrammarReader.getGrammar("g0.json")
        result = grammar.destroyLeftRecursion
        _ <- IO println result.show
      } yield ()
    }.assertNoException

    "Indirect left recursion test" in {
      for {
        grammar <- TestGrammarReader.getGrammar("left-rec-indirect-test.json")
        result = grammar.destroyLeftRecursion
        _ <- IO println result.show
      } yield ()
    }.assertNoException
  }
}
