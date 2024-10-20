package com.wrathenn.compilers
package context

import context.LocalContext.Defining
import models._
import models.function.{FunctionDef, FunctionDefGeneric}
import models.struct.{StructDef, StructDefGeneric}
import util.Aliases.{LlvmName, TinyScalaName}

import cats.syntax.all._
import com.wrathenn.compilers.models.`type`.{Type, TypeName}

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
  val structDefinitions: collection.Map[TypeName, StructDef]
  val genericStructDefinitions: collection.Map[TinyScalaName, StructDefGeneric]
  val functionDefinitions: collection.Map[FunctionDef.Key, FunctionDef]
  val genericFunctionDefinitions: collection.Map[TinyScalaName, FunctionDefGeneric]

  val globalVariables: collection.Map[TinyScalaName, VariableDef]
  val stringLiterals: collection.Map[TinyScalaName, LlvmName]

  val code: CodeStorage
  // struct
  def addStructDefinition(structDef: StructDef): Unit
  def addGenericStructDefinition(genericStructDef: StructDefGeneric): Unit
  // functions
  def findFunctionByKey(key: FunctionDef.Key): Option[FunctionDef]
  def addFunctionDefinition(functionDef: FunctionDef): Unit
  def addLocalFunctionDefinition(functionDef: FunctionDef): Unit
  // generic functions
  def findGenericFunctionByName(name: TinyScalaName): Option[FunctionDefGeneric]
  def addGenericFunctionDefinition(genericFunctionDef: FunctionDefGeneric): Unit
  def addLocalGenericFunctionDefinition(genericFunctionDef: FunctionDefGeneric): Unit
  // variables
  def addGlobalVariable(variableDef: VariableDef): Unit
  def addLocalVariable(variableDef: VariableDef): Unit
  def findVariableById(id: TinyScalaName): Option[VariableDef]
  // other:
  def addStringLiteral(str: String, llvmName: LlvmName): Unit

  def genLocalVariableName(target: CodeTarget): LlvmName

  def localContainsVariable(name: TinyScalaName): Boolean
  def createNewContext(defining: Option[LocalContext.Defining]): LocalContext
  def finishLocalContext(): LocalContext
  def inLocalContext[A](defining: Option[LocalContext.Defining])(f: => A): A
  def getDefiningObject: Option[LocalContext.Defining.Object]
  def getDefiningObjectOrDie: LocalContext.Defining.Object
  def getDefiningFunction: Option[LocalContext.Defining.Function]
  def getDefiningFunctionOrDie: LocalContext.Defining.Function
  def collectDefiningWithConcreteGenerics(): Map[TinyScalaName, TypeName]

  def writeCode(target: CodeTarget)(codeStr: String): Unit
  def writeCodeLn(target: CodeTarget)(codeStr: String): Unit
  def >>(): Unit
  def <<(): Unit
}

case class TranslationContextImpl(
  override val structDefinitions: mutable.Map[TypeName, StructDef],
  override val genericStructDefinitions: mutable.Map[TinyScalaName, StructDefGeneric],
  override val functionDefinitions: mutable.Map[FunctionDef.Key, FunctionDef],
  override val genericFunctionDefinitions: mutable.Map[TinyScalaName, FunctionDefGeneric],

  override val globalVariables: mutable.Map[TinyScalaName, VariableDef],
  override val stringLiterals: mutable.Map[String, LlvmName],
  override val code: CodeStorage,

  private val local: mutable.Stack[LocalContext],
) extends TranslationContext {
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
  override def findVariableById(id: TinyScalaName): Option[VariableDef] = {
    globalVariables.get(id) orElse searchStack { c =>
      c.variables.get(id).orElse {
        for {
          defining <- c.defining
          r <- defining match {
            case Defining.Function(functionDef) => functionDef.params.find { p => p.tinyScalaName == id }
            case _ => None
          }
        } yield r
      }
    }
  }

  /**
   * Поиск по tinyScala-представлению.
   */
  override def findFunctionByKey(key: FunctionDef.Key): Option[FunctionDef] = {
    functionDefinitions.get(key) orElse searchStack { c =>
      c.functions.get(key)
    }
  }

  override def findGenericFunctionByName(name: TinyScalaName): Option[FunctionDefGeneric] = {
    genericFunctionDefinitions.get(name) orElse searchStack { c =>
      c.genericFunctions.get(name)
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
    localContext.variables.addOne(variableDef.tinyScalaName -> variableDef)
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

  override def collectDefiningWithConcreteGenerics(): Map[TinyScalaName, TypeName] = local.toList.flatMap { c =>
    for {
      defining <- c.defining
    } yield defining match {
      case Defining.WithConcreteGenerics(genericAliases) => genericAliases
      case _ => Map[TinyScalaName, TypeName]()
    }
  }.reduce { (a, b) => a ++ b}


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
    this.structDefinitions.addOne(structDef.typeName -> structDef)
  }

  override def addGenericStructDefinition(genericStructDef: StructDefGeneric): Unit = {
    this.genericStructDefinitions.addOne(genericStructDef.tinyScalaName -> genericStructDef)
  }

  override def addGlobalVariable(variableDef: VariableDef): Unit = {
    this.globalVariables.addOne(variableDef.tinyScalaName -> variableDef)
  }

  override def addFunctionDefinition(functionDef: FunctionDef): Unit = {
    this.functionDefinitions.addOne(functionDef.key -> functionDef)
  }

  override def addGenericFunctionDefinition(genericFunctionDef: FunctionDefGeneric): Unit = {
    this.genericFunctionDefinitions.addOne(genericFunctionDef.tinyScalaName -> genericFunctionDef)
  }

  override def addLocalFunctionDefinition(functionDef: FunctionDef): Unit = {
    val localContext = getLocalContextOrDie
    localContext.functions.addOne(functionDef.key -> functionDef)
  }
  override def addLocalGenericFunctionDefinition(genericFunctionDef: FunctionDefGeneric): Unit = {
    val localContext = getLocalContextOrDie
    localContext.genericFunctions.addOne(genericFunctionDef.tinyScalaName -> genericFunctionDef)
  }

  override def addStringLiteral(str: LlvmName, llvmName: LlvmName): Unit = {
    this.stringLiterals.addOne(str -> llvmName)
  }
}

object TranslationContext {
  def create: TranslationContext = TranslationContextImpl(
    structDefinitions = mutable.HashMap(),
    genericStructDefinitions = mutable.HashMap(),
    globalVariables = mutable.HashMap(),
    stringLiterals = mutable.HashMap(),
    functionDefinitions = mutable.HashMap(),
    genericFunctionDefinitions = mutable.HashMap(),
    code = new CodeStorage(),

    local = new mutable.Stack[LocalContext]()
  )
}
