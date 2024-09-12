package com.wrathenn.compilers
package context

import models.{CodeTarget, FunctionDef, StructDef, Type, VariableDef}
import util.Aliases.{LlvmName, TinyScalaName}

import cats.syntax.all._
import com.wrathenn.compilers.context

import scala.collection.{AbstractSeq, mutable}

trait TranslationContext {
  val structDefinitions: collection.Map[TinyScalaName, StructDef]
  val globalVariables: collection.Map[TinyScalaName, VariableDef]
  val stringLiterals: collection.Map[TinyScalaName, LlvmName]
  val globalFunctions: collection.Map[TinyScalaName, FunctionDef]

  def addStructDefinition(structDef: StructDef): Unit
  def addGlobalVariable(variableDef: VariableDef): Unit
  def addStringLiteral(str: String, llvmName: LlvmName): Unit
  def addGlobalFunction(functionDef: FunctionDef): Unit

  def getTypeTag(_type: Type): Int
  def getType(tag: Int): Type

  def findVariableById(id: String): Option[(String, Type)]
  def findFunctionById(id: String): Option[FunctionDef]
  def genLocalVariableName(target: CodeTarget): String

  def writeCode(target: CodeTarget)(code: String): Unit
  def writeCodeLn(target: CodeTarget)(code: String): Unit
  def >>(): Unit
  def <<(): Unit

  def localContext: LocalContext
  def inLocalContext[A](defining: Option[FunctionDef])(f: => A): A

  def readGlobalCode: AbstractSeq[Char]
  def readInitCode: AbstractSeq[Char]
  def readMainCode: AbstractSeq[Char]
  def readLocalCode: AbstractSeq[Char]
}

case class TranslationContextImpl(
  override val structDefinitions: mutable.Map[TinyScalaName, StructDef],
  override val globalVariables: mutable.Map[TinyScalaName, VariableDef],
  override val stringLiterals: mutable.Map[TinyScalaName, LlvmName],
  override val globalFunctions: mutable.Map[TinyScalaName, FunctionDef],

  private val globalCode: StringBuilder,
  private val initCode: StringBuilder,
  private val mainCode: StringBuilder,
  private val localCode: StringBuilder,

  private val local: mutable.Stack[LocalContext],
) extends TranslationContext {
  private var mainCounter: Int = 0

  private var typeTagCounter: Int = 0
  private val tagToType: mutable.Map[Int, Type] = mutable.Map()
  private val typeToTag: mutable.Map[Type, Int] = mutable.Map()

  {
    List(
      Type.Primitive._Unit,
      Type.Primitive._Boolean,
      Type.Primitive._Chr,
      Type.Primitive._Int,
      Type.Primitive._Long,
      Type.Primitive._Float,
      Type.Primitive._Double,
      Type.Ref._Any,
      Type.Ref._Null,
      Type.Ref._String,
    ).foreach(addType(_))
  }

  private def addType(_type: Type): Unit = {
    if (typeToTag.contains(_type)) {
      throw new IllegalStateException(s"Type ${_type} already exists")
    }
    val tag = typeTagCounter
    typeTagCounter += 1
    tagToType.addOne(tag -> _type)
    typeToTag.addOne(_type -> tag)
  }

  override def getTypeTag(_type: Type): Int = {
    typeToTag(_type)
  }

  override def getType(tag: Int): Type = {
    tagToType(tag)
  }

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
  override def findVariableById(id: String): Option[(String, Type)] = {
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
  override def findFunctionById(id: String): Option[FunctionDef] = {
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
  override def genLocalVariableName(target: CodeTarget): String = {
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

  override def writeCode(target: CodeTarget)(code: String): Unit = {
    target match {
      case CodeTarget.LOCAL => localCode.append(" ".repeat(localWriteOffset) ++ code)
      case CodeTarget.INIT => initCode.append(code)
      case CodeTarget.GLOBAL => globalCode.append(code)
      case CodeTarget.Main => mainCode.append(code)
    }
  }

  override def writeCodeLn(target: CodeTarget)(code: String): Unit = {
    writeCode(target)(code ++ "\n")
  }

  override def localContext: LocalContext = this.local.top

  def finishLocalContext(): LocalContext = {
    val context = this.local.pop()
    context
  }

  private def createNewContext(defining: Option[FunctionDef]): LocalContext = {
    val newContext = LocalContext(defining)
    this.local.push(newContext)
    newContext
  }

  override def inLocalContext[A](defining: Option[FunctionDef])(f: => A): A = {
    this.createNewContext(defining)
    val ret = f // should work
    this.finishLocalContext()
    ret
  }

  // Для отступов:
  private var localWriteOffset: Int = 0
  override def >>(): Unit = localWriteOffset += 2
  override def <<(): Unit = localWriteOffset -= 2

  override def readGlobalCode: AbstractSeq[Char] = this.globalCode
  override def readLocalCode: AbstractSeq[Char] = this.localCode
  override def readInitCode: AbstractSeq[Char] = this.initCode
  override def readMainCode: AbstractSeq[Char] = this.mainCode

  override def addStructDefinition(structDef: StructDef): Unit = ???

  override def addGlobalVariable(variableDef: VariableDef): Unit = ???

  override def addStringLiteral(str: LlvmName, llvmName: LlvmName): Unit = ???

  override def addGlobalFunction(functionDef: FunctionDef): Unit = ???


}

object TranslationContext {
  def create: TranslationContext = TranslationContextImpl(
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
