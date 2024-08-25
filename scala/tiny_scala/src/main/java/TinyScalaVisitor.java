// Generated from /Users/wrathen/Documents/bmstu/playground/scala/tiny_scala/grammar/TinyScala.g4 by ANTLR 4.13.2
package com.wrathenn.compilers;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link TinyScalaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface TinyScalaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#compilationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompilationUnit(TinyScalaParser.CompilationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#tmplDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTmplDef(TinyScalaParser.TmplDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#templateBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplateBody(TinyScalaParser.TemplateBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#templateStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplateStat(TinyScalaParser.TemplateStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#classParamClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassParamClause(TinyScalaParser.ClassParamClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#classParams}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassParams(TinyScalaParser.ClassParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#classParam}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassParam(TinyScalaParser.ClassParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(TinyScalaParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#infixExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInfixExpr(TinyScalaParser.InfixExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#prefixExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixExpr(TinyScalaParser.PrefixExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#newClassExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewClassExpr(TinyScalaParser.NewClassExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#simpleExpr1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExpr1(TinyScalaParser.SimpleExpr1Context ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#exprs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprs(TinyScalaParser.ExprsContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#argumentExprs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentExprs(TinyScalaParser.ArgumentExprsContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(TinyScalaParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#blockExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockExpr(TinyScalaParser.BlockExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(TinyScalaParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#blockStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStat(TinyScalaParser.BlockStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#resultExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResultExpr(TinyScalaParser.ResultExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#def_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDef_(TinyScalaParser.Def_Context ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#patVarDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPatVarDef(TinyScalaParser.PatVarDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#patDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPatDef(TinyScalaParser.PatDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#funDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunDef(TinyScalaParser.FunDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#funSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunSig(TinyScalaParser.FunSigContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#paramClauses}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamClauses(TinyScalaParser.ParamClausesContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#paramClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamClause(TinyScalaParser.ParamClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(TinyScalaParser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(TinyScalaParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#paramType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamType(TinyScalaParser.ParamTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#typeDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDef(TinyScalaParser.TypeDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#type_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_(TinyScalaParser.Type_Context ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#functionArgTypes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionArgTypes(TinyScalaParser.FunctionArgTypesContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#simpleType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleType(TinyScalaParser.SimpleTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#types}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypes(TinyScalaParser.TypesContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(TinyScalaParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#ids}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIds(TinyScalaParser.IdsContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#stableId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStableId(TinyScalaParser.StableIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#opNoPrecedence}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpNoPrecedence(TinyScalaParser.OpNoPrecedenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#caseClauses}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaseClauses(TinyScalaParser.CaseClausesContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#caseClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaseClause(TinyScalaParser.CaseClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPattern(TinyScalaParser.PatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#pattern1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPattern1(TinyScalaParser.Pattern1Context ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#simplePattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimplePattern(TinyScalaParser.SimplePatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#patterns}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPatterns(TinyScalaParser.PatternsContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#enumerators}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumerators(TinyScalaParser.EnumeratorsContext ctx);
	/**
	 * Visit a parse tree produced by {@link TinyScalaParser#generator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGenerator(TinyScalaParser.GeneratorContext ctx);
}