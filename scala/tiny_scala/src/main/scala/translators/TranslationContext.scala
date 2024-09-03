package com.wrathenn.compilers
package translators

import models.{FunctionDef, StructDef, Type, VariableDef}

import cats.syntax.all._

import scala.collection.mutable

class TranslationContext(
  val structDefinitions: mutable.Map[String, StructDef],
  val globalVariables: mutable.Map[String, VariableDef],
  val stringLiterals: mutable.Map[String, String], // tinyScala -> llvm
  val globalFunctionsDefinitions: mutable.Map[String, FunctionDef],

  val globalCode: StringBuilder,
  val initCode: StringBuilder,
  val mainCode: StringBuilder,

  var localObject: Option[String],
  val localVariables: mutable.Map[String, VariableDef],
  val localCode: StringBuilder,
  var localCounter: Int,
  val localFunctionsDefinitions: mutable.Map[String, FunctionDef],
) {
  def findVariableById(id: String): Option[(String, Type)] = {
    if (globalVariables.contains(id)) {
      val global = globalVariables(id)
      (global.llvmNameRepr -> global._type).some
    }
    else if (localVariables.contains(id)) {
      val local = localVariables(id)
      (local.llvmNameRepr -> local._type).some
    }
    else None
  }

  def writeCode(isGlobal: Boolean)(code: String): Unit = {
    if (isGlobal) { initCode.append(code) }
    else { localCode.append(code) }
  }

  def writeCodeLn(isGlobal: Boolean)(code: String): Unit = {
    writeCode(isGlobal)(code ++ "\n")
  }
}

object TranslationContext {
  def apply() = new TranslationContext(
    structDefinitions = mutable.HashMap(),
    globalVariables = mutable.HashMap(),
    stringLiterals = mutable.HashMap(),
    globalFunctionsDefinitions = mutable.HashMap(),

    globalCode = new StringBuilder(),
    initCode = new StringBuilder(),
    mainCode = new StringBuilder(),

    localObject = None,
    localVariables = mutable.HashMap(),
    localCode = new StringBuilder(),
    localCounter = 0,
    localFunctionsDefinitions = mutable.HashMap(),
  )
}
