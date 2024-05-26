package com.wrathenn.compilers
package readers

import cats.syntax.all._

class InputPointer(
  private val data: List[Char],
) {
  override def toString: String = s"""InputPointer[${data.mkString(",")}]"""
  override def equals(obj: Any): Boolean = {
    if (!obj.isInstanceOf[InputPointer]) return false
    this.data == obj.asInstanceOf[InputPointer].data
  }

  def getNextN(n: Int): String = {
    data.take(n).mkString
  }

  def getFirst(): Option[Char] = {
    data.headOption
  }

  def getFirstEither: Either[Exception, Char] = {
    data.headOption match {
      case Some(value) => value.asRight
      case None => new IllegalStateException("Empty input while trying to get first").asLeft
    }
  }

  def step(n: Int): InputPointer = {
    new InputPointer(data.drop(n))
  }

  def getAndStep(n: Int): (String, InputPointer) = {
    getNextN(n) -> step(n)
  }

  def takeWhile(p: Char => Boolean): String = {
    data.takeWhile(p).mkString
  }

  def skipEmptyNeccessarily(): Either[Exception, InputPointer] = {
    val res = skipEmpty()
    if (res.data.length == this.data.length) new IllegalStateException("Expected empty symbol").asLeft
    else res.asRight
  }

  def skipEmpty(): InputPointer = new InputPointer(data.dropWhile { c => c == ' ' || c == '\n' || c == '\t' })
}
