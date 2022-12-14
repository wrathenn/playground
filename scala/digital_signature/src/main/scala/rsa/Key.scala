package rsa

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class Key(
  exponent: BigInt,
  module: BigInt
) {
  def blockByteSize(): Int =
    (module.bitLength - 1) / 8
}

object Key {
  implicit val decoder = deriveDecoder[Key]
  implicit val encoder = deriveEncoder[Key]
}