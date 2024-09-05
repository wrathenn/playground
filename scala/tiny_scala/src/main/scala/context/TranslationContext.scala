package com.wrathenn.compilers
package context

import models.{CodeTarget, FunctionDef, StructDef, Type, VariableDef}
import util.Aliases.{LlvmName, TinyScalaName}

import cats.syntax.all._

import scala.collection.{AbstractSeq, mutable}

class TranslationContext(
  val structDefinitions: mutable.Map[TinyScalaName, StructDef],
  val globalVariables: mutable.Map[TinyScalaName, VariableDef],
  val stringLiterals: mutable.Map[TinyScalaName, LlvmName],
  val globalFunctions: mutable.Map[TinyScalaName, FunctionDef],

  private val globalCode: StringBuilder,
  val initCode: StringBuilder,
  val mainCode: StringBuilder,
  private val localCode: StringBuilder,

  val local: mutable.Stack[LocalContext],
) {
  private var mainCounter: Int = 0

  private def searchStack[A](f: LocalContext => Option[A]): Option[A] = {
    local.foreach { c =>
      f(c) match {
        case v: Some[A] => return v
        case None => {}
      }
    }
    None
  }

  /**
   * Поиск по tinyScala-представлению.
   */
  def findVariableById(id: String): Option[(String, Type)] = {
    if (globalVariables.contains(id)) {
      val global = globalVariables(id)
      (global.llvmNameRepr -> global._type).some
    }
    else searchStack { c =>
      c.variables.get(id)
        .map { l => l.llvmNameRepr -> l._type }
    }
  }

  /**
   * Поиск по tinyScala-представлению.
   */
  def findFunctionById(id: String): Option[FunctionDef] = {
    if (globalFunctions.contains(id)) {
      globalFunctions(id).some
    }
    else searchStack { c =>
      c.functions.get(id)
    }
  }

  /**
   * Сгенерировать новую локальную переменную в текущем локальном скоупе.
   * @return llvm-ir название переменной
   */
  def genLocalVariableName(target: CodeTarget): String = {
    target match {
      case CodeTarget.LOCAL => {
        val localContext = local.top
        val tempVal = s"%v_${localContext.counter}"
        localContext.counter += 1
        tempVal
      }
      case CodeTarget.INIT | CodeTarget.Main => {
        val tempVal = s"%v_${mainCounter}"
        mainCounter += 1
        tempVal
      }
      case CodeTarget.GLOBAL => throw new IllegalStateException("Trying to get local variable for global scope")
    }
  }

  def writeCode(target: CodeTarget)(code: String): Unit = {
    target match {
      case CodeTarget.LOCAL => localCode.append(" ".repeat(localWriteOffset) ++ code)
      case CodeTarget.INIT => initCode.append(code)
      case CodeTarget.GLOBAL => globalCode.append(code)
      case CodeTarget.Main => mainCode.append(code)
    }
  }

  def writeCodeLn(target: CodeTarget)(code: String): Unit = {
    writeCode(target)(code ++ "\n")
  }

  def localContext: LocalContext = this.local.top

  def finishLocalContext(): LocalContext = {
    val context = this.local.pop()
    context
  }

  def createNewContext(defining: Option[FunctionDef]): LocalContext = {
    val newContext = LocalContext(defining)
    this.local.push(newContext)
    newContext
  }

  def inLocalContext[A](defining: Option[FunctionDef])(f: => A): A = {
    this.createNewContext(defining)
    val ret = f // should work
    this.finishLocalContext()
    ret
  }

  // Для отступов:
  private var localWriteOffset: Int = 0
  def >>(): Unit = localWriteOffset += 2
  def <<(): Unit = localWriteOffset -= 2

  def readGlobalCode: AbstractSeq[Char] = this.globalCode
  def readLocalCode: AbstractSeq[Char] = this.localCode
}

object TranslationContext {
  def apply() = new TranslationContext(
    structDefinitions = mutable.HashMap(),
    globalVariables = mutable.HashMap(),
    stringLiterals = mutable.HashMap(),
    globalFunctions = mutable.HashMap(),

    globalCode = new StringBuilder(),
    initCode = new StringBuilder(),
    mainCode = new StringBuilder(),
    localCode = new StringBuilder(),

    local = new mutable.Stack[LocalContext]()
  )
}
