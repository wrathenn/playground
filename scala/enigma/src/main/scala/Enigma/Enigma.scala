package Enigma
//
//import RotorExtensions.rotorRandomGenerator
//import ReflectorExtensions.reflectorRandomGenerator

case class Enigma(rotors: List[Rotor[_]], reflector: Reflector[_]) {
//  def rotateRotors(): Unit = {
//    rotors.foldLeft(true) {(next, it) => it.rotate(next)}
//  }
//
//  def cryptInCurrentState(v: Char): Char = {
//    val rotorsForward: Char = rotors.foldLeft(v) {(res, r) => r.forward(res)}
//    val reflectorPass: Char = reflector.get(rotorsForward)
//    val rotorsBackward: Char = rotors.foldRight(reflectorPass) {(r, res) => r.backward(res)}
//    rotorsBackward
//  }
//
//  def cryptChar(v: Char): Char = {
//    val res = cryptInCurrentState(v)
//    rotateRotors()
//    res
//  }
}

object EnigmaTest extends App {
//  val enigma = Enigma(rotors = List(Rotor.gen, Rotor.gen), reflector = Reflector.gen)
////  enigma.rotors.head.rotate()
//  for (c <- Array[Char](1,2,3,4,5)) {
//    println(c)
//  }
}
