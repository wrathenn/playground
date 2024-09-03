// Generated from /Users/wrathen/Documents/bmstu/playground/scala/tiny_scala/grammar/TinyScala.g4 by ANTLR 4.13.2
package com.wrathenn.compilers;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TinyScalaParser}.
 */
public interface TinyScalaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompilationUnit(TinyScalaParser.CompilationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompilationUnit(TinyScalaParser.CompilationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#tmplDef}.
	 * @param ctx the parse tree
	 */
	void enterTmplDef(TinyScalaParser.TmplDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#tmplDef}.
	 * @param ctx the parse tree
	 */
	void exitTmplDef(TinyScalaParser.TmplDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#tmplDefCaseClass}.
	 * @param ctx the parse tree
	 */
	void enterTmplDefCaseClass(TinyScalaParser.TmplDefCaseClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#tmplDefCaseClass}.
	 * @param ctx the parse tree
	 */
	void exitTmplDefCaseClass(TinyScalaParser.TmplDefCaseClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#tmplDefObject}.
	 * @param ctx the parse tree
	 */
	void enterTmplDefObject(TinyScalaParser.TmplDefObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#tmplDefObject}.
	 * @param ctx the parse tree
	 */
	void exitTmplDefObject(TinyScalaParser.TmplDefObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#objectIsMain}.
	 * @param ctx the parse tree
	 */
	void enterObjectIsMain(TinyScalaParser.ObjectIsMainContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#objectIsMain}.
	 * @param ctx the parse tree
	 */
	void exitObjectIsMain(TinyScalaParser.ObjectIsMainContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#templateBody}.
	 * @param ctx the parse tree
	 */
	void enterTemplateBody(TinyScalaParser.TemplateBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#templateBody}.
	 * @param ctx the parse tree
	 */
	void exitTemplateBody(TinyScalaParser.TemplateBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#templateStat}.
	 * @param ctx the parse tree
	 */
	void enterTemplateStat(TinyScalaParser.TemplateStatContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#templateStat}.
	 * @param ctx the parse tree
	 */
	void exitTemplateStat(TinyScalaParser.TemplateStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#classParamClause}.
	 * @param ctx the parse tree
	 */
	void enterClassParamClause(TinyScalaParser.ClassParamClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#classParamClause}.
	 * @param ctx the parse tree
	 */
	void exitClassParamClause(TinyScalaParser.ClassParamClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#classParams}.
	 * @param ctx the parse tree
	 */
	void enterClassParams(TinyScalaParser.ClassParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#classParams}.
	 * @param ctx the parse tree
	 */
	void exitClassParams(TinyScalaParser.ClassParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#classParam}.
	 * @param ctx the parse tree
	 */
	void enterClassParam(TinyScalaParser.ClassParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#classParam}.
	 * @param ctx the parse tree
	 */
	void exitClassParam(TinyScalaParser.ClassParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(TinyScalaParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(TinyScalaParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#infixExpr}.
	 * @param ctx the parse tree
	 */
	void enterInfixExpr(TinyScalaParser.InfixExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#infixExpr}.
	 * @param ctx the parse tree
	 */
	void exitInfixExpr(TinyScalaParser.InfixExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#prefixExpr}.
	 * @param ctx the parse tree
	 */
	void enterPrefixExpr(TinyScalaParser.PrefixExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#prefixExpr}.
	 * @param ctx the parse tree
	 */
	void exitPrefixExpr(TinyScalaParser.PrefixExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#newClassExpr}.
	 * @param ctx the parse tree
	 */
	void enterNewClassExpr(TinyScalaParser.NewClassExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#newClassExpr}.
	 * @param ctx the parse tree
	 */
	void exitNewClassExpr(TinyScalaParser.NewClassExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#simpleExpr1}.
	 * @param ctx the parse tree
	 */
	void enterSimpleExpr1(TinyScalaParser.SimpleExpr1Context ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#simpleExpr1}.
	 * @param ctx the parse tree
	 */
	void exitSimpleExpr1(TinyScalaParser.SimpleExpr1Context ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#argumentExprs}.
	 * @param ctx the parse tree
	 */
	void enterArgumentExprs(TinyScalaParser.ArgumentExprsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#argumentExprs}.
	 * @param ctx the parse tree
	 */
	void exitArgumentExprs(TinyScalaParser.ArgumentExprsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#exprs}.
	 * @param ctx the parse tree
	 */
	void enterExprs(TinyScalaParser.ExprsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#exprs}.
	 * @param ctx the parse tree
	 */
	void exitExprs(TinyScalaParser.ExprsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#def_}.
	 * @param ctx the parse tree
	 */
	void enterDef_(TinyScalaParser.Def_Context ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#def_}.
	 * @param ctx the parse tree
	 */
	void exitDef_(TinyScalaParser.Def_Context ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#patVarDef}.
	 * @param ctx the parse tree
	 */
	void enterPatVarDef(TinyScalaParser.PatVarDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#patVarDef}.
	 * @param ctx the parse tree
	 */
	void exitPatVarDef(TinyScalaParser.PatVarDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#patDef}.
	 * @param ctx the parse tree
	 */
	void enterPatDef(TinyScalaParser.PatDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#patDef}.
	 * @param ctx the parse tree
	 */
	void exitPatDef(TinyScalaParser.PatDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#funDef}.
	 * @param ctx the parse tree
	 */
	void enterFunDef(TinyScalaParser.FunDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#funDef}.
	 * @param ctx the parse tree
	 */
	void exitFunDef(TinyScalaParser.FunDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(TinyScalaParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(TinyScalaParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#funSig}.
	 * @param ctx the parse tree
	 */
	void enterFunSig(TinyScalaParser.FunSigContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#funSig}.
	 * @param ctx the parse tree
	 */
	void exitFunSig(TinyScalaParser.FunSigContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#params}.
	 * @param ctx the parse tree
	 */
	void enterParams(TinyScalaParser.ParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#params}.
	 * @param ctx the parse tree
	 */
	void exitParams(TinyScalaParser.ParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(TinyScalaParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(TinyScalaParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#type_}.
	 * @param ctx the parse tree
	 */
	void enterType_(TinyScalaParser.Type_Context ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#type_}.
	 * @param ctx the parse tree
	 */
	void exitType_(TinyScalaParser.Type_Context ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#simpleType}.
	 * @param ctx the parse tree
	 */
	void enterSimpleType(TinyScalaParser.SimpleTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#simpleType}.
	 * @param ctx the parse tree
	 */
	void exitSimpleType(TinyScalaParser.SimpleTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#arrayType}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(TinyScalaParser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#arrayType}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(TinyScalaParser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#types}.
	 * @param ctx the parse tree
	 */
	void enterTypes(TinyScalaParser.TypesContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#types}.
	 * @param ctx the parse tree
	 */
	void exitTypes(TinyScalaParser.TypesContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(TinyScalaParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(TinyScalaParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#ids}.
	 * @param ctx the parse tree
	 */
	void enterIds(TinyScalaParser.IdsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#ids}.
	 * @param ctx the parse tree
	 */
	void exitIds(TinyScalaParser.IdsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#stableId}.
	 * @param ctx the parse tree
	 */
	void enterStableId(TinyScalaParser.StableIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#stableId}.
	 * @param ctx the parse tree
	 */
	void exitStableId(TinyScalaParser.StableIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#opNoPrecedence}.
	 * @param ctx the parse tree
	 */
	void enterOpNoPrecedence(TinyScalaParser.OpNoPrecedenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#opNoPrecedence}.
	 * @param ctx the parse tree
	 */
	void exitOpNoPrecedence(TinyScalaParser.OpNoPrecedenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#pattern}.
	 * @param ctx the parse tree
	 */
	void enterPattern(TinyScalaParser.PatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#pattern}.
	 * @param ctx the parse tree
	 */
	void exitPattern(TinyScalaParser.PatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#pattern1}.
	 * @param ctx the parse tree
	 */
	void enterPattern1(TinyScalaParser.Pattern1Context ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#pattern1}.
	 * @param ctx the parse tree
	 */
	void exitPattern1(TinyScalaParser.Pattern1Context ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#patterns}.
	 * @param ctx the parse tree
	 */
	void enterPatterns(TinyScalaParser.PatternsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#patterns}.
	 * @param ctx the parse tree
	 */
	void exitPatterns(TinyScalaParser.PatternsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#enumerators}.
	 * @param ctx the parse tree
	 */
	void enterEnumerators(TinyScalaParser.EnumeratorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#enumerators}.
	 * @param ctx the parse tree
	 */
	void exitEnumerators(TinyScalaParser.EnumeratorsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyScalaParser#generator}.
	 * @param ctx the parse tree
	 */
	void enterGenerator(TinyScalaParser.GeneratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyScalaParser#generator}.
	 * @param ctx the parse tree
	 */
	void exitGenerator(TinyScalaParser.GeneratorContext ctx);
}