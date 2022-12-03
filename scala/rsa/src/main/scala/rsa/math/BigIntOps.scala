package rsa.math

import java.math.BigInteger
import java.util.Random
import scala.annotation.tailrec

object BigIntOps {
  def generateBigInt(size: Int): BigInt =
    BigInt.javaBigInteger2bigInt(new BigInteger(size, new Random()))

  case class ExtendedGCDRes(
    gcd: BigInt,
    aBezout: BigInt, bBezout: BigInt
  )

  // Если коэффициент Безо получился отрицательный - добавить модуль
  def extendedGCD(a: BigInt, b: BigInt): ExtendedGCDRes = {
    case class GCDRes(
      r0: BigInt, r1: BigInt,
      a0: BigInt, a1: BigInt,
      b0: BigInt, b1: BigInt,
    )

    @tailrec
    def iter(it: GCDRes): GCDRes =
      if (it.r1 == 0) it
      else {
        val quotient = it.r0 / it.r1
        iter(GCDRes(
          it.r1, it.r0 - it.r1 * quotient,
          it.a1, it.a0 - it.a1 * quotient,
          it.b1, it.b0 - it.b1 * quotient,
        ))
      }

    iter(GCDRes(
      a, b,
      1, 0,
      0, 1
    )) match {
      case GCDRes(oldR, _, oldS, _, oldT, _) =>
        ExtendedGCDRes(gcd = oldR, aBezout = oldS, bBezout = oldT)
    }
  }

  implicit class BigIntSyntax(val n: BigInt) extends AnyVal {
    def toBinary: List[Int] = {
      @tailrec
      def helper(n: BigInt, res: List[Int] = List()): List[Int] = {
        if (n == 0) res
        else helper(n >> 1, (n & 1).toInt :: res)
      }

      helper(n)
    }
  }
}
