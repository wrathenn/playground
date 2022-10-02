import Enigma.ByteArrayReflectorExt.{byteArrayReflectorFileableInstance, byteArrayReflectorRandomGenerator}
import Enigma.{ByteArrayReflector, ByteArrayRotor, Rotor}
import Enigma.ByteArrayRotorExt.{rotorFileableInstance, rotorRandomGenerator}
import Ext.FileableSyntax.{ToFileOps, fromFile}
import Ext.Files.using

import java.io.FileWriter

object RotorTest extends App {
  val testRotor: ByteArrayRotor = ByteArrayRotor.gen
  testRotor.rotate(1)
  println
  println(testRotor.toString)
  testRotor.toFile("rotor1")
  println
  val read = fromFile[ByteArrayRotor]("rotor1")
}

object ReflTest extends App {
  val testRefl: ByteArrayReflector = ByteArrayReflector.gen
  println
  println(testRefl.toString)
  testRefl.toFile("refl1")
  val newRefl: Option[ByteArrayReflector] = fromFile[ByteArrayReflector]("refl1")
  println
}

object SpamToFile extends App {
  using(new FileWriter("test_file")) { f => {
    for (i <- Byte.MinValue to Byte.MaxValue) {
      f.write(i)
    }
  }
  }
}
