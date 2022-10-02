package Enigma

trait Enigma[T] {
  def cryptAndStay(v: T): T
  def cryptAndGo(v: T): T
}

case class ByteEnigma(rotors: List[Rotor[Byte]], reflector: Reflector[Byte]) extends Enigma[Byte] {
  private def rotateRotors(stepsCount: Int): Unit = {
    rotors.foldLeft(stepsCount) {(next, it) => it.rotate(next)}
  }

  override def cryptAndStay(v: Byte): Byte = {
    val rotorsForward: Byte = rotors.foldLeft(v) {(res, r) => r.forward(res)}
    val reflectorPass: Byte = reflector.get(rotorsForward)
    val rotorsBackward: Byte = rotors.foldRight(reflectorPass) {(r, res) => r.backward(res)}
    rotorsBackward
  }

  override def cryptAndGo(v: Byte): Byte = {
    val r = cryptAndStay(v)
    rotateRotors(1)
    r
  }
}
