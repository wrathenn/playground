import Primes._
import BigIntOps.extendedGCD
import cats.syntax.option._

import scala.annotation.tailrec

object RSA {
  case class RSAKey(
    exponent: BigInt,
    module: BigInt
  ) {
    override def toString: String = s"$exponent|$module"

    def blockByteSize(): Int =
      (module.bitLength - 1) / 8
  }

  object RSAKey {
    def fromString(s: String): Option[RSAKey] =
      s.split('|') match {
        case Array(exp, mod, _*) => RSAKey(BigInt(exp), BigInt(mod)).some
        case _ => None
      }
  }

  def generateKeys(): (RSAKey, RSAKey) = {
    val prime1 = genRandomPrime() // a
    val prime2 = genRandomPrime() // b
    val module = prime1 * prime2 // n
    val eiler = (prime1 - 1) * (prime2 - 1) // phi
    val publicExponent = coprimeLessThen(eiler) // e
    val privateExponent = inverse(publicExponent, eiler) // d
    val publicKey = RSAKey(publicExponent, module)
    val privateKey = RSAKey(privateExponent, module)
    publicKey -> privateKey
  }

  def cypher(msg: BigInt, key: RSAKey): BigInt =
    modPower(msg, key.exponent, key.module)


  private def coprimeLessThen(a: BigInt): BigInt = {
    @tailrec
    def recCoprime(a: BigInt, f: BigInt): BigInt =
      if (a <= f) 1
      else if (a.gcd(f) == 1) f
      else recCoprime(a, f + 2)

    val res = fermaNumbers.foldLeft(BigInt(0)) { (res, f) =>
      if (res == 0 && a > f && a.gcd(f) == 1) f
      else res
    }

    if (res != 0) res
    else recCoprime(a, 11)
  }

  private def inverse(a: BigInt, mod: BigInt): BigInt = {
    def ifNegative(i: BigInt) = if (i < 0) i + mod else i
    val gcd = extendedGCD(a, mod)
    if (a * gcd.aBezout + mod * gcd.bBezout == gcd.gcd) ifNegative(gcd.aBezout)
    else ifNegative(gcd.bBezout)
  }

  def main(args: Array[String]): Unit = {
    val p = BigInt(3557)
    val q = BigInt(2579)
    val n = p * q
    val eiler = (p-1)*(q-1)
    val e = 3
    val d = inverse(e, eiler)
    val k = Test.bezout(e, eiler)
    println()
  }
}

object Test extends App {
  def bezout(a: BigInt, b: BigInt): (BigInt, BigInt, BigInt) = {
    @tailrec
    def bezoutR(r0: BigInt=a, s0: BigInt=1, t0: BigInt=0,
      r1: BigInt=b, s1: BigInt=0, t1: BigInt=1):
    (BigInt, BigInt, BigInt) =
      if (r1 == BigInt(0)) (r0, s0, t0)
      else {
        val q = r0 / r1
        bezoutR(r1, s1, t1, r0 % r1, s0 - q * s1, t0 - q * t1)
      }
    bezoutR()
  }
}