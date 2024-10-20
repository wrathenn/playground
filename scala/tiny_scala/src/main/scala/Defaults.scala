package com.wrathenn.compilers

import models.{VariableDecl, VariableDef}
import models.`type`.{GenericProperty, Type, TypeName}
import models.function.{FunctionDef, FunctionDefGeneric}

object Defaults {
  val printfFunctionDefGeneric = FunctionDefGeneric(
    tinyScalaName = "print",
    typeParamAliases = List(),
    params = List(
      GenericProperty(
        name = "fmt",
        typeName = TypeName("String", List())
      )
    ),
    returns = TypeName("Int", List()),
    expression = null,
  )

  val printfFunctionDef = FunctionDef(
    tinyScalaName = "print",
    llvmName = "@printf",
    params = List(
      VariableDef(
        tinyScalaName = "fmt",
        llvmNameRepr = "%fmt",
        _type = Type.Ref._String,
        decl = VariableDecl.VAL,
        isFunctionParam = true,
      ),
    ),
    returns = Type.Primitive._Int,
    isVarArg = true,
    concreteGenericTypes = Map(),
  )
}
