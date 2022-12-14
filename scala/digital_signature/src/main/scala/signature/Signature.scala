package signature

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class Signature(
  name: String,
  org: String,
  hash: String,
)

object Signature {
  implicit val decoder = deriveDecoder[Signature]
  implicit val encoder = deriveEncoder[Signature]
}
