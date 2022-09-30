package Enigma

import Ext.Files.using
import Ext.Fileable
import Ext.ReversibleByIndex._
import Ext.{Fileable, Generatable}

import java.io.{BufferedWriter, FileWriter}
import scala.util.Random.shuffle
import scala.language.reflectiveCalls

trait Rotor[T] {
  def forward(v: T): T
  def backward(v: T): T
  def rotate(i: Int): Int
}

case class ArrayRotor(
  var offset: Int,
  mapping: Array[Byte]
) extends Rotor[Byte] {
  private val size = mapping.length
  private val reverseMapping: Array[Byte] = mapping.reversedByIndex

  private def offsetIndex(i: Byte): Int = (i.toInt + offset) % size
  private def symbolToIndexWithOffset(i: Byte): Int = (i - Byte.MinValue + offset) % size

  override def forward(v: Byte): Byte = mapping(symbolToIndexWithOffset(v))

  override def backward(v: Byte): Byte = symbolToIndexWithOffset(reverseMapping(symbolToIndexWithOffset(v))).toByte

  override def rotate(i: Int): Int = {
    val fullRotations: Int = (offset + i) / size
    offset = (offset + i) / size
    fullRotations
  }
}

object ArrayRotor {
  def gen(implicit generator: Generatable[ArrayRotor]): ArrayRotor =
    generator.generate()
}

object ArrayRotorExt {
  implicit val rotorRandomGenerator: Generatable[ArrayRotor] = {
    () => ArrayRotor(
      offset = 0,
      mapping = Array.from(shuffle(for (v <- Byte.MinValue to Byte.MaxValue) yield v.toByte))
    )
  }

  implicit val rotorFileableInstance: Fileable[ArrayRotor] =
    new Fileable[ArrayRotor] {
      override def read(filename: String): Option[ArrayRotor] = {
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
          Some(ArrayRotor(offset, mapping))
        }}
      }

      override def write(value: ArrayRotor, filename: String): Unit = {
        using(new BufferedWriter(new FileWriter(filename))) {f => {
          f.write(s"${value.offset}\n")
          for  ((v, i) <- value.mapping.zipWithIndex) {
            f.write(s"$i -> $v\n")
          }
        }}
      }
    }
}
