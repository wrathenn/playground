//package com.wrathenn.exp
//package lab4.algs
//
//import lab4.model.{Disjunct, Expr, ExprCNF}
//
//import scala.annotation.tailrec
//
//
//object Resolution {
//
//  def resolve(data: List[Expr], conclusion: Expr): Boolean = {
//    val notConclusion = Expr.~~(conclusion)
//    val cnfs = (data :+ notConclusion).map { e => AlgsCNF.convertToCnf(e) }
//    val disjuncts = collectDisjuncts(cnfs)
//
//  }
//
//  private def collectDisjuncts(cnf: ExprCNF): List[Disjunct] = {
//    cnf match {
//      case ExprCNF.Term(id) => List(Disjunct.Term(id))
//      case ExprCNF.~~(e) => Disjunct.~~(coll)
//      case ExprCNF.|(e1, e2) => ???
//      case ExprCNF.&(e1, e2) => ???
//    }
//  }
//
//  private def collectDisjuncts(cnfs: List[ExprCNF]): List[Disjunct] = {
//    @tailrec
//    def _rec(cnfs: List[ExprCNF], prev: List[ExprCNF]): List[ExprCNF] = {
//      val current = cnfs.flatMap {
//        case ExprCNF.&(e1, e2) => List(e1, e2)
//        case e => List(e)
//      }
//      if (current == prev) current
//      else _rec(current, current)
//    }
//
//    _rec(cnfs, cnfs)
//  }
//
//  private def asdf(disjuncts: List[ExprCNF])
//}
