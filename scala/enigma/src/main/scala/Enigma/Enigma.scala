package Enigma

trait Enigma[T] {
  def cypherAndStay(v: T): T
  def cypherAndGo(v: T): T
}

case class ByteEnigma(rotors: List[Rotor[Byte]], reflector: Reflector[Byte]) extends Enigma[Byte] {
  private def rotateRotors(stepsCount: Int): Unit = {
    rotors.foldLeft(stepsCount) {(next, it) => it.rotate(next)}
  }

  override def cypherAndStay(v: Byte): Byte = {
    val rotorsForward: Byte = rotors.foldLeft(v) {(res, r) => r.forward(res)}
    val reflectorPass: Byte = reflector.get(rotorsForward)
    val rotorsBackward: Byte = rotors.foldRight(reflectorPass) {(r, res) => r.backward(res)}
    rotorsBackward
  }

  override def cypherAndGo(v: Byte): Byte = {
    val r = cypherAndStay(v)
    rotateRotors(1)
    r
  }
}
