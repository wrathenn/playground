package com.wrathenn.compilers
package context

import context.LocalContext.Defining
import models._
import util.Aliases.{LlvmName, TinyScalaName}

import cats.syntax.all._

import scala.collection.{AbstractSeq, mutable}

class CodeStorage(
  val globalCode: StringBuilder = new StringBuilder(),
  val initCode: StringBuilder = new StringBuilder(),
  val mainCode: StringBuilder = new StringBuilder(),
  val localCode: StringBuilder = new StringBuilder(),
) {
  def readGlobalCode: AbstractSeq[Char] = this.globalCode
  def readLocalCode: AbstractSeq[Char] = this.localCode
  def readInitCode: AbstractSeq[Char] = this.initCode
  def readMainCode: AbstractSeq[Char] = this.mainCode

  def append(other: CodeStorage): Unit = {
    this.globalCode.append(other.globalCode)
    this.initCode.append(other.initCode)
    this.mainCode.append(other.mainCode)
    this.localCode.append(other.localCode)
  }
}

trait TranslationContext {
  val structDefinitions: collection.Map[TinyScalaName, StructDef]
  val globalVariables: collection.Map[TinyScalaName, VariableDef]
  val stringLiterals: collection.Map[TinyScalaName, LlvmName]
  val globalFunctions: collection.Map[TinyScalaName, FunctionDef]
  val code: CodeStorage

  def addStructDefinition(structDef: StructDef): Unit
  def addGlobalVariable(variableDef: VariableDef): Unit
  def addStringLiteral(str: String, llvmName: LlvmName): Unit
  def addGlobalFunction(functionDef: FunctionDef): Unit

  def getTypeTag(_type: Type): Int
  def getType(tag: Int): Type

  def findVariableById(id: TinyScalaName): Option[VariableDef]
  def findFunctionById(id: TinyScalaName): Option[FunctionDef]
  def genLocalVariableName(target: CodeTarget): LlvmName

  def addLocalVariable(variableDef: VariableDef): Unit
  def addLocalFunction(functionDef: FunctionDef): Unit
  def localContainsVariable(name: TinyScalaName): Boolean
  def createNewContext(defining: Option[LocalContext.Defining]): LocalContext
  def finishLocalContext(): LocalContext
  def inLocalContext[A](defining: Option[LocalContext.Defining])(f: => A): A
  def getDefiningObject: Option[LocalContext.Defining.Object]
  def getDefiningObjectOrDie: LocalContext.Defining.Object
  def getDefiningFunction: Option[LocalContext.Defining.Function]
  def getDefiningFunctionOrDie: LocalContext.Defining.Function

  def writeCode(target: CodeTarget)(codeStr: String): Unit
  def writeCodeLn(target: CodeTarget)(codeStr: String): Unit
  def >>(): Unit
  def <<(): Unit
}

case class TranslationContextImpl(
  override val structDefinitions: mutable.Map[TinyScalaName, StructDef],
  override val globalVariables: mutable.Map[TinyScalaName, VariableDef],
  override val stringLiterals: mutable.Map[String, LlvmName],
  override val globalFunctions: mutable.Map[TinyScalaName, FunctionDef],
  override val code: CodeStorage,

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
  override def findVariableById(id: TinyScalaName): Option[VariableDef] = {
    globalVariables.get(id) orElse searchStack { c =>
      c.variables.get(id).orElse {
        for {
          defining <- c.defining
          r <- defining match {
            case Defining.Object(_) => None
            case Defining.Function(functionDef) => functionDef.params.find { p => p.tinyScalaRepr == id }
          }
        } yield r
      }
    }
  }

  /**
   * Поиск по tinyScala-представлению.
   */
  override def findFunctionById(id: TinyScalaName): Option[FunctionDef] = {
    globalFunctions.get(id) orElse searchStack { c =>
      c.functions.get(id)
    }
  }

  /**
   * Сгенерировать новую локальную переменную в текущем локальном скоупе.
   * @return llvm-ir название переменной
   */
  override def genLocalVariableName(target: CodeTarget): LlvmName = {
    target match {
      case CodeTarget.LOCAL => {
        val localContext = local.last
        val tempVal = s"%v_${localContext.counter}"
        localContext.counter += 1
        tempVal
      }
      case CodeTarget.INIT | CodeTarget.MAIN => {
        val tempVal = s"%v_${mainCounter}"
        mainCounter += 1
        tempVal
      }
      case CodeTarget.GLOBAL => throw new IllegalStateException("Trying to get local variable for global scope")
    }
  }

  def finishLocalContext(): LocalContext = {
    val context = this.local.pop()
    context
  }

  def createNewContext(defining: Option[LocalContext.Defining]): LocalContext = {
    val newContext = LocalContext(defining)
    this.local.push(newContext)
    newContext
  }

  override def inLocalContext[A](defining: Option[LocalContext.Defining])(f: => A): A = {
    this.createNewContext(defining)
    val ret = f // should work
    this.finishLocalContext()
    ret
  }

  private def getLocalContextOrDie: LocalContext = this.local.headOption.getOrElse {
    throw new IllegalStateException("No local context")
  }

  override def addLocalVariable(variableDef: VariableDef): Unit = {
    val localContext = getLocalContextOrDie
    localContext.variables.addOne(variableDef.tinyScalaRepr -> variableDef)
  }

  override def addLocalFunction(functionDef: FunctionDef): Unit = {
    val localContext = getLocalContextOrDie
    localContext.functions.addOne(functionDef.tinyScalaName -> functionDef)
  }

  override def localContainsVariable(name: TinyScalaName): Boolean = {
    val localContext = getLocalContextOrDie
    localContext.variables.contains(name)
  }

  override def getDefiningObject: Option[Defining.Object] = for {
    top <- this.local.headOption
    defining <- top.defining
    res: Defining.Object <- defining match {
      case obj: Defining.Object => obj.some
      case _ => None
    }
  } yield res

  override def getDefiningObjectOrDie: Defining.Object = getDefiningObject.getOrElse {
    throw new IllegalStateException("Not in object definition")
  }

  override def getDefiningFunction: Option[Defining.Function] = for {
    top <- this.local.headOption
    defining <- top.defining
    res: Defining.Function <- defining match {
      case fun: Defining.Function => fun.some
      case _ => None
    }
  } yield res

  override def getDefiningFunctionOrDie: Defining.Function = getDefiningFunction.getOrElse {
    throw new IllegalStateException("Not in function definition")
  }

  // Для отступов:
  private var localWriteOffset: Int = 0
  override def >>(): Unit = localWriteOffset += 2
  override def <<(): Unit = localWriteOffset -= 2

  override def writeCode(target: CodeTarget)(codeStr: String): Unit = {
    target match {
      case CodeTarget.LOCAL => code.localCode.append(" ".repeat(localWriteOffset) ++ codeStr)
      case CodeTarget.INIT => code.initCode.append(codeStr)
      case CodeTarget.GLOBAL => code.globalCode.append(codeStr)
      case CodeTarget.MAIN => code.mainCode.append(codeStr)
    }
  }

  override def writeCodeLn(target: CodeTarget)(codeStr: String): Unit = {
    writeCode(target)(codeStr ++ "\n")
  }

  override def addStructDefinition(structDef: StructDef): Unit = {
    this.structDefinitions.addOne(structDef.tinyScalaRepr -> structDef)
    this.addType(Type.Ref.Struct(structDef.tinyScalaRepr))
  }

  override def addGlobalVariable(variableDef: VariableDef): Unit = {
    this.globalVariables.addOne(variableDef.tinyScalaRepr -> variableDef)
  }

  override def addStringLiteral(str: LlvmName, llvmName: LlvmName): Unit = {
    this.stringLiterals.addOne(str -> llvmName)
  }

  override def addGlobalFunction(functionDef: FunctionDef): Unit = {
    this.globalFunctions.addOne(functionDef.tinyScalaName -> functionDef)
  }
}

object TranslationContext {
  def create: TranslationContext = TranslationContextImpl(
    structDefinitions = mutable.HashMap(),
    globalVariables = mutable.HashMap(),
    stringLiterals = mutable.HashMap(),
    globalFunctions = mutable.HashMap(),
    code = new CodeStorage(),

    local = new mutable.Stack[LocalContext]()
  )
}
