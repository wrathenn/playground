package com.wrathenn.exp
package lab4.algs

import lab4.model.{Disjunct, Expr}

import com.wrathenn.exp.lab4.algs.cnf.AlgsCNF

import scala.annotation.tailrec


object Resolution {

  def resolve(data: List[Expr], conclusion: Expr): Boolean = {
    val notConclusion = Expr.~~(conclusion)
    val disjuncts = (data :+ notConclusion).flatMap { e => AlgsCNF.convertToCnf(e) }
    ???
  }

}
