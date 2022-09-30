import Enigma.{ArrayRotor, ByteReflector}
import Enigma.ArrayRotorExt.{rotorFileableInstance, rotorRandomGenerator}
import Enigma.ByteReflectorExt.{byteReflectorFileableInstance, reflectorRandomGenerator}
import Ext.FileableSyntax.{ToFileOps, fromFile}

object RotorTest extends App {
  val testRotor: ArrayRotor = ArrayRotor.gen
  println
  println(testRotor.toString)
  testRotor.toFile("rotor1")
  val newRotor: Option[ArrayRotor] = fromFile[ArrayRotor]("rotor1")
  println
}

object ReflTest extends App {
  val testRefl: ByteReflector = ByteReflector.gen
  println
  println(testRefl.toString)
  testRefl.toFile("refl1")
  val newRefl: Option[ByteReflector] = fromFile[ByteReflector]("refl1")
  println
}

