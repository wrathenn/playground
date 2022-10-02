package Enigma

import Ext.Files.using
import Ext.{Fileable, Generatable}
import Ext.ReversibleByIndex._

import java.io.{BufferedWriter, FileWriter => JavaFileWriter}
import scala.util.Random.shuffle
import scala.language.reflectiveCalls

trait Rotor[T] {
  def forward(v: T): T

  def backward(v: T): T

  def rotate(i: Int): Int
}

case class ByteArrayRotor(
  var offset: Int,
  mapping: Array[Byte]
) extends Rotor[Byte] {
  private val size = mapping.length
  private val reverseMapping: Array[Byte] = mapping.reversedByIndex

  private def symbolToIndexWithOffset(i: Byte): Int = (i - Byte.MinValue - offset + size) % size

  override def forward(v: Byte): Byte = ((mapping(symbolToIndexWithOffset(v)) + offset) % size).toByte

  override def backward(v: Byte): Byte = ((reverseMapping(symbolToIndexWithOffset(v)) + offset) % size).toByte

  override def rotate(i: Int): Int = {
    val fullRotations: Int = (offset + i) / size
    offset = (offset + i) % size
    fullRotations
  }
}

object ByteArrayRotor {
  def gen(implicit generator: Generatable[ByteArrayRotor]): ByteArrayRotor =
    generator.generate()
}

object ByteArrayRotorExt {
  implicit val rotorRandomGenerator: Generatable[ByteArrayRotor] = {
    () =>
      ByteArrayRotor(
        offset = 0,
        mapping = Array.from(shuffle(for (v <- Byte.MinValue to Byte.MaxValue) yield v.toByte))
      )
  }

  implicit val rotorFileableInstance: Fileable[ByteArrayRotor] =
    new Fileable[ByteArrayRotor] {
      override def read(filename: String): Option[ByteArrayRotor] = {
        using(io.Source.fromFile(filename)) { f => {
          val iter = f.getLines()
          val offsetPattern = "^([0-9]+)$".r
          val offset = offsetPattern.findFirstIn(iter.next()) match {
            case Some(v) => v.toInt
            case None => return None
          }

          val mappingPattern = "^([0-9]+) -> ([-+]?[0-9]+)".r
          val mapping: Array[Byte] = Array.ofDim[Byte](Byte.MaxValue - Byte.MinValue + 1)
          for (line <- iter) {
            val found = mappingPattern.findFirstMatchIn(line) match {
              case Some(v) => v.subgroups
              case None => return None
            }
            mapping(found.head.toInt) = found.tail.head.toByte
          }
          Some(ByteArrayRotor(offset, mapping))
        }
        }
      }

      override def write(value: ByteArrayRotor, filename: String): Unit = {
        using(new BufferedWriter(new JavaFileWriter(filename))) { f => {
          f.write(s"${value.offset}\n")
          for ((v, i) <- value.mapping.zipWithIndex) {
            f.write(s"$i -> $v\n")
          }
        }
        }
      }
    }
}
