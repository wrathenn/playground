package com.wrathenn.compilers
package util

import models.Operator.Infix
import com.wrathenn.compilers.models.`type`.Type.Primitive
import models.{Literal, Operator}
import com.wrathenn.compilers.models.`type`.Type
import com.wrathenn.compilers.models.`type`.Type.Ref._String

import com.wrathenn.compilers.util.Aliases.TinyScalaName
import org.antlr.v4.runtime.tree.TerminalNode

import java.nio.charset.StandardCharsets
import scala.collection.mutable.ArrayBuffer

object Util {
  def collect[C](collector: Int => C): List[C] = {
    val result = ArrayBuffer[C]()
    var i = 0

    var next = collector(0)
    while (next != null) {
      result.addOne(next)
      i += 1
      next = collector(i)
    }

    result.toList
  }

  // use this only for functions!
  def collectStableIdRepr(node: TinyScalaParser.StableIdContext): TinyScalaName = {
    if (node.stableId == null) node.Id.getText
    else collectStableIdRepr(node.stableId) ++ s".${node.Id.getText}"
  }

  def getLiteral(node: TinyScalaParser.LiteralContext): Literal = {
    lazy val sign = if (node.Minus != null) "-" else ""
      
    if (node.IntegerLiteral != null) {
      val lastSymbol = node.IntegerLiteral.toString.last
      lastSymbol match {
        case 'l' | 'L' => Literal.Value(llvmValue = s"$sign${node.IntegerLiteral}", _type = Type.Primitive._Long)
        case _ => Literal.Value(llvmValue = s"$sign${node.IntegerLiteral}", _type = Type.Primitive._Int)
      }

    }
    else if (node.FloatingPointLiteral != null) {
      val lastSymbol = node.FloatingPointLiteral.toString.last
      lastSymbol match {
        case 'f' | 'F' => Literal.Value(llvmValue = s"$sign${node.FloatingPointLiteral}", _type = Type.Primitive._Float)
        case _ => Literal.Value(llvmValue = s"$sign${node.FloatingPointLiteral}", _type = Type.Primitive._Double)
      }
    }
    else if (node.BooleanLiteral != null) {
      val repr = node.BooleanLiteral().getText match {
        case "true" => "1"
        case _ => "0"
      }
      Literal.Value(llvmValue = repr, _type = Type.Primitive._Boolean)
    }
    else if (node.CharacterLiteral != null) {
      Literal.Value(llvmValue = node.CharacterLiteral().getText, _type = Type.Primitive._Chr) // todo char to i8 table?
    }
    else if (node.StringLiteral != null) {
      Literal.Value(llvmValue = node.StringLiteral().getText, _type = _String)
    }
    else {
      Literal.Null
    }
  }

  def getOperator(text: String): Operator = {
    text match {
      case Operator.Not.repr => Operator.Not
      case Operator.Plus.repr => Operator.Plus
      case Operator.Minus.repr => Operator.Minus
      case Operator.Mul.repr => Operator.Mul
      case Operator.Div.repr => Operator.Div
      case Operator.Mod.repr => Operator.Mod
      case Operator.And.repr => Operator.And
      case Operator.Or.repr => Operator.Or
      case Operator.Less.repr => Operator.Less
      case Operator.LessOrEq.repr => Operator.LessOrEq
      case Operator.Greater.repr => Operator.Greater
      case Operator.GreaterOrEq.repr => Operator.GreaterOrEq
      case Operator.Equals.repr => Operator.Equals
      case _ => throw new IllegalStateException(s"Unknown operator $text")
    }
  }

  def getOperatorLlvm(op: Operator with Infix, commonType: Type): String = {
    op match {
      case Operator.Plus => commonType match {
        case Primitive._Int => "add"
        case Primitive._Long => "add"
        case Primitive._Chr => "add"
        case Primitive._Float => "fadd"
        case Primitive._Double => "fadd"
        case _ => ???
      }
      case Operator.Minus => commonType match {
        case Primitive._Int => "sub"
        case Primitive._Long => "sub"
        case Primitive._Chr => "sub"
        case Primitive._Float => "fsub"
        case Primitive._Double => "fsub"
        case _ => ???
      }
      case Operator.Mul => commonType match {
        case Primitive._Int => "mul"
        case Primitive._Long => "mul"
        case Primitive._Chr => "mul"
        case Primitive._Float => "fmul"
        case Primitive._Double => "fmul"
        case _ => ???
      }
      case Operator.Div => commonType match {
        case Primitive._Int => "sdiv"
        case Primitive._Long => "sdiv"
        case Primitive._Chr => "sdiv"
        case Primitive._Float => "fdiv"
        case Primitive._Double => "fdiv"
        case _ => ???
      }
      case Operator.Mod => commonType match {
        case Primitive._Int => "srem"
        case Primitive._Long => "srem"
        case Primitive._Chr => "srem"
        case _ => ???
      }
      case Operator.And => commonType match {
        case Primitive._Int
             |  Primitive._Long
             |  Primitive._Chr
             |  Primitive._Boolean => "and"
        case _ => ???
      }
      case Operator.Or => commonType match {
        case Primitive._Int
             |  Primitive._Long
             |  Primitive._Chr
             |  Primitive._Boolean => "or"
        case _ => ???
      }
      case Operator.Less => commonType match {
        case Primitive._Int
             |  Primitive._Long
             |  Primitive._Chr
             |  Primitive._Boolean => "icmp slt"
        case Primitive._Float => "fcmp ult"
        case Primitive._Double => "dcmp ult"
        case _ => ???
      }
      case Operator.LessOrEq => commonType match {
        case Primitive._Int
             |  Primitive._Long
             |  Primitive._Chr
             |  Primitive._Boolean => "icmp sle"
        case Primitive._Float => "fcmp ule"
        case Primitive._Double => "dcmp ule"
        case _ => ???
      }
      case Operator.Greater => commonType match {
        case Primitive._Int
             |  Primitive._Long
             |  Primitive._Chr
             |  Primitive._Boolean => "icmp sgt"
        case Primitive._Float => "fcmp ugt"
        case Primitive._Double => "dcmp ugt"
        case _ => ???
      }
      case Operator.GreaterOrEq => commonType match {
        case Primitive._Int
             |  Primitive._Long
             |  Primitive._Chr
             |  Primitive._Boolean => "icmp sge"
        case Primitive._Float => "fcmp uge"
        case Primitive._Double => "dcmp uge"
        case _ => ???
      }
      case Operator.Equals => commonType match {
        case Primitive._Int
             |  Primitive._Long
             |  Primitive._Chr
             |  Primitive._Boolean => "icmp eq"
        case Primitive._Float => "fcmp ueq"
        case Primitive._Double => "dcmp ueq"
        // non primitives -- pointers comparison
        case _ => "icmp eq"
      }
    }
  }

  def strToByteRepr(str: String): (String, Int)= {
    def decodeEscapeSequences(input: String): String = {
      input.replace("\\n", "\n")
        .replace("\\t", "\t")
        .replace("\\r", "\r")
        .replace("\\\\", "\\")
    }

    // Function to encode the string in LLVM format
    def toLLVMString(input: String): (String, Int) = {
      val byteArray = input.getBytes("UTF-8")
      val llvmEncoded = byteArray.map { b =>
        if (b >= 32 && b < 127 && b != 34 && b != 92) {
          b.toChar.toString
        } else {
          "\\%02X".format(b)
        }
      }.mkString

      llvmEncoded + "\\00" -> (byteArray.size + 1)
    }

    toLLVMString(decodeEscapeSequences(str))
  }
}
