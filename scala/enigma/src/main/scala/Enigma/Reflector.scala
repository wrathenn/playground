package Enigma

import Ext.Files.using
import Ext.{Fileable, Generatable}

import java.io.{BufferedWriter, FileWriter}
import scala.util.Random.shuffle

trait Reflector[T] {
  def get(v: T): T
}

case class ByteArrayReflector(mapping: Array[Byte]) extends Reflector[Byte]{
  def get(v: Byte): Byte = mapping(v - Byte.MinValue)
}

object ByteArrayReflector {
  def gen(implicit generator: Generatable[ByteArrayReflector]): ByteArrayReflector =
    generator.generate()
}

object ByteArrayReflectorExt {
  implicit val byteArrayReflectorRandomGenerator: Generatable[ByteArrayReflector] = {
    () => {
      val fullRandom: IndexedSeq[Byte] = shuffle(for (v <- Byte.MinValue to Byte.MaxValue) yield v.toByte)
      val firstHalf = fullRandom.slice(0, (fullRandom.length + 1) / 2 )
      val secondHalf = fullRandom.slice(fullRandom.length / 2, fullRandom.length).reverse

      val res = Array.ofDim[Byte](fullRandom.size)
      for ((e1, e2) <- firstHalf.zip(secondHalf)) {res(e1 - Byte.MinValue) = e2; res(e2 - Byte.MinValue) = e1}
      ByteArrayReflector(res)
    }
  }

  implicit val byteArrayReflectorFileableInstance: Fileable[ByteArrayReflector] =
    new Fileable[ByteArrayReflector] {
      override def read(filename: String): Option[ByteArrayReflector] = {
        using(io.Source.fromFile(filename)) { f => {
          val mappingPattern = "^([0-9]+) -> ([+-]?[0-9]+)$".r
          val mapping: Array[Byte] = Array.ofDim(Byte.MaxValue - Byte.MinValue + 1)
          for (line <- f.getLines()) {
            val found = mappingPattern.findFirstMatchIn(line) match {
              case Some(v) => v.subgroups
              case None => return None
            }
            mapping(found.head.toInt) = found(1).toByte
          }
          Some(ByteArrayReflector(mapping))
        }}
      }

      override def write(value: ByteArrayReflector, filename: String): Unit = {
        using(new BufferedWriter(new FileWriter(filename))) {f => {
          for  ((v, i) <- value.mapping.zipWithIndex) {
            f.write(s"$i -> $v\n")
          }
        }}
      }
    }
}
