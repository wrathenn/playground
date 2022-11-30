import scala.annotation.tailrec
import BigIntOps._

object Primes {
  private final val firstPrimes = List(
    2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31,
    37, 41, 43, 47, 53, 59, 61, 67, 71, 73,
    79, 83, 89, 97, 101, 103, 107, 109, 113,
    127, 131, 137, 139, 149, 151, 157, 163,
    167, 173, 179, 181, 191, 193, 197, 199,
    211, 223, 227, 229, 233, 239, 241, 251, 257
  )

  final val fermaNumbers: List[BigInt] = List(65537, 257, 17)

  private def isDivisibleByFirst(n: BigInt): Boolean =
    firstPrimes.foldLeft(false)((res, p) => res || (n / p == 0))

  // Faster calculation for n^p (mod m)
  def modPower(n: BigInt, power: BigInt, mod: BigInt): BigInt = {
    val powerBits = power.toBinary
    val modList = powerBits.tail.foldLeft(List(n % mod)) { (res, _) =>
      val nextMod = (res.head * res.head) % mod
      nextMod :: res
    }
    (modList zip powerBits).foldLeft(BigInt(1)) { case (res, (m, b)) =>
      if (b == 0) res
      else (res * m) % mod
    }
  }

  // Check if "a" is a witness of prime for {p - 1 = 2^s * d}
  def isPrimalityWitness(prime: BigInt, power2: Int, oddPart: BigInt, witness: BigInt): Boolean = {
    // if a^d == 1  (mod n)
    if (modPower(witness, oddPart, prime) == 1) return true
    // if exists r < s where a^(2^r * d) == -1  (mod n)
    val powers2: List[BigInt] = (1 until power2).foldLeft(List(BigInt(1))) { (res, _) =>
      res.head * 2 :: res
    }.reverse

    powers2.foldLeft(false) { (res, twoR) =>
      res || (modPower(witness, twoR * oddPart, prime) == (prime - 1))
    }
  }

  def sdDecomposition(n: BigInt): (Int, BigInt) = {
    @tailrec
    def sd(twoPower: Int, odd: BigInt): (Int, BigInt) =
      if ((odd & 1) != 0) (twoPower, odd)
      else sd(twoPower + 1, odd >> 1)

    if (n == 0) return (0, 0)
    sd(0, n)
  }

  def primalityTestMillerRabin(n: BigInt, roundCount: Int): Boolean = {
    if (isDivisibleByFirst(n)) return false
    val (s, d) = sdDecomposition(n - 1)

    val witnesses = (0 until roundCount).map { _ =>
      val r = generateBigInt(256)
      if (r < 1) BigInt(1) else r
    }

    witnesses.forall(witness => isPrimalityWitness(n, s, d, witness))
  }

  @tailrec
  def genRandomPrime(roundCount: Int = 10, r: BigInt = generateBigInt(1024) | 1): BigInt = {
    if (primalityTestMillerRabin(r, roundCount)) {println("generated"); r}
    else genRandomPrime(roundCount, r + 2)
  }
}
