package rsa.math

import rsa.math.BigIntOps._

import scala.annotation.tailrec

object Primes {
  // Первые простые числа для быстрой проверки
  private final val firstPrimes = List(
    2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31,
    37, 41, 43, 47, 53, 59, 61, 67, 71, 73,
    79, 83, 89, 97, 101, 103, 107, 109, 113,
    127, 131, 137, 139, 149, 151, 157, 163,
    167, 173, 179, 181, 191, 193, 197, 199,
    211, 223, 227, 229, 233, 239, 241, 251, 257
  )

  // 3 числа Ферма для быстрого нахождения взаимно простого
  final val fermaNumbers: List[BigInt] = List(65537, 257, 17)

  implicit class BigIntPrimeExtension(val n: BigInt) extends AnyVal {
    // Делится ли на первые простые числа
    def isDivisibleByFirst: Boolean =
      firstPrimes.foldLeft(false)((res, p) => res || (n / p == 0))

    // Оптимизация вычисления n^p (mod m)
    def modPower(power: BigInt, mod: BigInt): BigInt = {
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

    // Проверка, является ли n свидетелем простоты 'prime' for {p - 1 = 2^s * d}
    def isPrimalityWitness(prime: BigInt, power2: Int, oddPart: BigInt): Boolean = {
      // if a^d == 1  (mod n)
      if (n.modPower(oddPart, prime) == 1) return true
      // if exists r < s where a^(2^r * d) == -1  (mod n)
      val powers2: List[BigInt] = (1 until power2).foldLeft(List(BigInt(1))) { (res, _) =>
        res.head * 2 :: res
      }.reverse

      powers2.foldLeft(false) { (res, twoR) =>
        res || (n.modPower(twoR * oddPart, prime) == (prime - 1))
      }
    }

    // Разложить число 'n' в виде 2^s * d. S - Int, т.к. степень двойки, d - BigInt
    def sdDecomposition: (Int, BigInt) = {
      @tailrec
      def sd(twoPower: Int, odd: BigInt): (Int, BigInt) =
        if ((odd & 1) != 0) (twoPower, odd)
        else sd(twoPower + 1, odd >> 1)

      if (n == 0) return (0, 0)
      sd(0, n)
    }

    // Вероятностная оценка по тесту Миллера-Рабина
    def isPrime(roundCount: Int): Boolean = {
      if (n.isDivisibleByFirst) return false
      val (s, d) = (n-1).sdDecomposition

      val witnesses = (0 until roundCount).map { _ =>
        val r = generateBigInt(256)
        if (r < 1) BigInt(1) else r
      }

      witnesses.forall(_.isPrimalityWitness(n, s, d))
    }
  }

  // Генерация простого числа
  @tailrec
  def genRandomPrime(roundCount: Int = 10, r: BigInt = generateBigInt(1024) | 1): BigInt =
    if (r.isPrime(roundCount)) r
    else genRandomPrime(roundCount, r + 2)
}
