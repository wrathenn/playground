package com.wrathenn.exp
package lab4.algs.cnf

import lab4.model.Disjunct.{Const, Predicate, Term, Variable}

import scala.collection.mutable
import cats.syntax.all._

import scala.annotation.tailrec

object Unification {
  sealed trait Substitution[+A <: Term] { val from: Variable; val to: A }
  object Substitution {
    case class ToConst(from: Variable, to: Const) extends Substitution[Const]
    case class ToVar(from: Variable, to: Variable) extends Substitution[Variable]
  }

  sealed trait Result
  object Result {
    case class Success(
      substitutions: List[Substitution[Term]]
    ) extends Result

    object Failure extends Result
  }

  private sealed trait TermsResult
  private object TermsResult {
    sealed class Success extends TermsResult

    case class Subst(substitution: Substitution[Term]) extends Success
    object Subst {
      def apply(v: Variable, toConst: Const): Subst = Subst(Substitution.ToConst(v, toConst))
      def apply(v: Variable, toVar: Variable): Subst = Subst(Substitution.ToVar(v, toVar))
    }
    object Empty extends Success

    object Failure extends TermsResult
  }

  private def unifyTerms(t1: Term, t2: Term): TermsResult = {
    (t1, t2) match {
      case (c1: Const, c2: Const) =>
        if (c1 == c2) TermsResult.Empty
        else TermsResult.Failure
      case (v1: Variable, c2: Const) =>
        TermsResult.Subst(v1, c2)
      case (c1: Const, v2: Variable) =>
        TermsResult.Subst(v2, c1)
      case (v1: Variable, v2: Variable) =>
        TermsResult.Subst(v1, v2)
    }
  }

  private def spreadResult(onArgs: List[(Term, Term)], success: TermsResult.Success) : List[(Term, Term)] = {
    def trySubst(variable: Variable, const: Const, term: Term): Term = if (term == variable) const else term
    def tryBind(variable1: Variable, variable2: Variable, term: Term): Term = if (term == variable1) variable2 else variable1

    success match {
      case TermsResult.Subst(Substitution.ToConst(variable, const)) =>
        onArgs.map { case (t1, t2) => trySubst(variable, const, t1) -> trySubst(variable, const, t2)}
      case TermsResult.Subst(Substitution.ToVar(v1, v2)) =>
        onArgs.map { case (t1, t2) => tryBind(v1, v2, t1) -> tryBind(v1, v2, t2) }
      case TermsResult.Empty => onArgs
    }
  }

  private case class MutableContext(
    substitutions: mutable.ListBuffer[Substitution[Term]],
  )
  private object MutableContext {
    def apply(): MutableContext = MutableContext(mutable.ListBuffer())
  }

  @tailrec
  private def unifyAllArguments(
    args: List[(Term, Term)],
    res: List[Term] = List(),
  )(implicit context: MutableContext): Either[Result.Failure.type, List[Term]] = {
    if (args.isEmpty) return res.asRight

    val (t1, t2) = args.head
    val termUnification = unifyTerms(t1, t2)

    val success = termUnification match {
      case TermsResult.Failure => return Result.Failure.asLeft
      case success: TermsResult.Success => success
    }

    val nextArgs = spreadResult(args.tail, success)
    val thisArgsResult = success match {
      case subst: TermsResult.Subst => {
        context.substitutions.addOne(subst.substitution)
        subst.substitution match {
          case Substitution.ToConst(_, toConst) => toConst
          case Substitution.ToVar(_, v2) => v2
        }
      }
      case TermsResult.Empty => t1
    }

    unifyAllArguments(nextArgs, res :+ thisArgsResult)
  }

  def unify(a1: Predicate, a2: Predicate): Result = {
    if (a1.id != a2.id) return Result.Failure
    if (a1.isNegative != a2.isNegative) return Result.Failure
    if (a1.args.size != a2.args.size) return Result.Failure

    implicit val context: MutableContext = MutableContext()

    val argsUnificationResult = unifyAllArguments(a1.args zip a2.args)
    val unifiedArgs = argsUnificationResult match {
      case Left(failure) => return failure
      case Right(value) => value
    }

    Result.Success(
//      unified = Predicate(id = a1.id, args = unifiedArgs, isNegative = a1.isNegative),
      substitutions = context.substitutions.toList,
    )
  }
}
