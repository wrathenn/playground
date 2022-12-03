package rsa

import rsa.math.BigIntOps.extendedGCD
import rsa.math.Primes.{BigIntPrimeExtension, fermaNumbers, genRandomPrime}

import scala.annotation.tailrec

object Algorithm {
  def generateKeys(): (Key, Key) = {
    val prime1 = genRandomPrime() // a
    val prime2 = genRandomPrime() // b
    val module = prime1 * prime2 // n
    val eiler = (prime1 - 1) * (prime2 - 1) // phi
    val publicExponent = coprimeLessThen(eiler) // e
    val privateExponent = inverse(publicExponent, eiler) // d
    val publicKey = Key(publicExponent, module)
    val privateKey = Key(privateExponent, module)
    publicKey -> privateKey
  }

  def cypher(msg: BigInt, key: Key): BigInt =
    msg.modPower(key.exponent, key.module)


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
}
