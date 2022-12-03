package rsa

import cats.implicits.catsSyntaxOptionId

case class Key(
  exponent: BigInt,
  module: BigInt
) {
  override def toString: String = s"$exponent|$module"

  def blockByteSize(): Int =
    (module.bitLength - 1) / 8
}

object Key {
  def fromString(s: String): Option[Key] =
    s.split('|') match {
      case Array(exp, mod, _*) => Key(BigInt(exp), BigInt(mod)).some
      case _ => None
    }
}