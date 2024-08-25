// Generated from /Users/wrathen/Documents/bmstu/playground/scala/tiny_scala/grammar/TinyScala.g4 by ANTLR 4.13.2
package com.wrathenn.compilers;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class TinyScalaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, Id=33, OpPrecedence1=34, OpPrecedence2=35, OpPrecedence3=36, 
		OpPrecedence4=37, OpPrecedence5=38, OpPrecedence6=39, OpPrecedence7=40, 
		OpPrecedence8=41, OpPrecedence9=42, BooleanLiteral=43, CharacterLiteral=44, 
		SymbolLiteral=45, IntegerLiteral=46, StringLiteral=47, FloatingPointLiteral=48, 
		Varid=49, BoundVarid=50, Paren=51, Delim=52, Minus=53, NL=54, NEWLINE=55, 
		WS=56, COMMENT=57, LINE_COMMENT=58;
	public static final int
		RULE_compilationUnit = 0, RULE_tmplDef = 1, RULE_templateBody = 2, RULE_templateStat = 3, 
		RULE_classParamClause = 4, RULE_classParams = 5, RULE_classParam = 6, 
		RULE_expr = 7, RULE_infixExpr = 8, RULE_prefixExpr = 9, RULE_newClassExpr = 10, 
		RULE_simpleExpr1 = 11, RULE_exprs = 12, RULE_argumentExprs = 13, RULE_args = 14, 
		RULE_blockExpr = 15, RULE_block = 16, RULE_blockStat = 17, RULE_resultExpr = 18, 
		RULE_def_ = 19, RULE_patVarDef = 20, RULE_patDef = 21, RULE_funDef = 22, 
		RULE_funSig = 23, RULE_paramClauses = 24, RULE_paramClause = 25, RULE_params = 26, 
		RULE_param = 27, RULE_paramType = 28, RULE_typeDef = 29, RULE_type_ = 30, 
		RULE_functionArgTypes = 31, RULE_simpleType = 32, RULE_types = 33, RULE_literal = 34, 
		RULE_ids = 35, RULE_stableId = 36, RULE_opNoPrecedence = 37, RULE_caseClauses = 38, 
		RULE_caseClause = 39, RULE_pattern = 40, RULE_pattern1 = 41, RULE_simplePattern = 42, 
		RULE_patterns = 43, RULE_enumerators = 44, RULE_generator = 45;
	private static String[] makeRuleNames() {
		return new String[] {
			"compilationUnit", "tmplDef", "templateBody", "templateStat", "classParamClause", 
			"classParams", "classParam", "expr", "infixExpr", "prefixExpr", "newClassExpr", 
			"simpleExpr1", "exprs", "argumentExprs", "args", "blockExpr", "block", 
			"blockStat", "resultExpr", "def_", "patVarDef", "patDef", "funDef", "funSig", 
			"paramClauses", "paramClause", "params", "param", "paramType", "typeDef", 
			"type_", "functionArgTypes", "simpleType", "types", "literal", "ids", 
			"stableId", "opNoPrecedence", "caseClauses", "caseClause", "pattern", 
			"pattern1", "simplePattern", "patterns", "enumerators", "generator"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'class'", "'object'", "'{'", "'}'", "';'", "'('", "')'", "','", 
			"'val'", "'var'", "':'", "'='", "'if'", "'else'", "'while'", "'do'", 
			"'for'", "'yield'", "'return'", "'_'", "'.'", "'match'", "'new'", "'def'", 
			"'type'", "'=>'", "'null'", "'this'", "'super'", "'case'", "'|'", "'<-'", 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, "'-'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, "Id", "OpPrecedence1", 
			"OpPrecedence2", "OpPrecedence3", "OpPrecedence4", "OpPrecedence5", "OpPrecedence6", 
			"OpPrecedence7", "OpPrecedence8", "OpPrecedence9", "BooleanLiteral", 
			"CharacterLiteral", "SymbolLiteral", "IntegerLiteral", "StringLiteral", 
			"FloatingPointLiteral", "Varid", "BoundVarid", "Paren", "Delim", "Minus", 
			"NL", "NEWLINE", "WS", "COMMENT", "LINE_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "TinyScala.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public TinyScalaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CompilationUnitContext extends ParserRuleContext {
		public List<TmplDefContext> tmplDef() {
			return getRuleContexts(TmplDefContext.class);
		}
		public TmplDefContext tmplDef(int i) {
			return getRuleContext(TmplDefContext.class,i);
		}
		public CompilationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compilationUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterCompilationUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitCompilationUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitCompilationUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompilationUnitContext compilationUnit() throws RecognitionException {
		CompilationUnitContext _localctx = new CompilationUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_compilationUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(92);
				tmplDef();
				}
				}
				setState(95); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__0 || _la==T__1 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TmplDefContext extends ParserRuleContext {
		public TerminalNode Id() { return getToken(TinyScalaParser.Id, 0); }
		public List<ClassParamClauseContext> classParamClause() {
			return getRuleContexts(ClassParamClauseContext.class);
		}
		public ClassParamClauseContext classParamClause(int i) {
			return getRuleContext(ClassParamClauseContext.class,i);
		}
		public TemplateBodyContext templateBody() {
			return getRuleContext(TemplateBodyContext.class,0);
		}
		public TmplDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tmplDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterTmplDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitTmplDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitTmplDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TmplDefContext tmplDef() throws RecognitionException {
		TmplDefContext _localctx = new TmplDefContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_tmplDef);
		try {
			int _alt;
			setState(113);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(97);
				match(T__0);
				setState(98);
				match(Id);
				setState(102);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(99);
						classParamClause();
						}
						} 
					}
					setState(104);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
				}
				setState(106);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
				case 1:
					{
					setState(105);
					templateBody();
					}
					break;
				}
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
				setState(108);
				match(T__1);
				setState(109);
				match(Id);
				setState(111);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
				case 1:
					{
					setState(110);
					templateBody();
					}
					break;
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TemplateBodyContext extends ParserRuleContext {
		public List<TemplateStatContext> templateStat() {
			return getRuleContexts(TemplateStatContext.class);
		}
		public TemplateStatContext templateStat(int i) {
			return getRuleContext(TemplateStatContext.class,i);
		}
		public List<TerminalNode> NL() { return getTokens(TinyScalaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TinyScalaParser.NL, i);
		}
		public List<TerminalNode> WS() { return getTokens(TinyScalaParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(TinyScalaParser.WS, i);
		}
		public TemplateBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_templateBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterTemplateBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitTemplateBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitTemplateBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TemplateBodyContext templateBody() throws RecognitionException {
		TemplateBodyContext _localctx = new TemplateBodyContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_templateBody);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(115);
				match(NL);
				}
				}
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(156);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(121);
				match(T__2);
				setState(125);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL || _la==WS) {
					{
					{
					setState(122);
					_la = _input.LA(1);
					if ( !(_la==NL || _la==WS) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					}
					setState(127);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(128);
				match(T__3);
				}
				break;
			case 2:
				{
				setState(129);
				match(T__2);
				setState(133);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL || _la==WS) {
					{
					{
					setState(130);
					_la = _input.LA(1);
					if ( !(_la==NL || _la==WS) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					}
					setState(135);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(144);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(136);
						templateStat();
						setState(138); 
						_errHandler.sync(this);
						_la = _input.LA(1);
						do {
							{
							{
							setState(137);
							_la = _input.LA(1);
							if ( !(_la==T__4 || _la==NL) ) {
							_errHandler.recoverInline(this);
							}
							else {
								if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
								_errHandler.reportMatch(this);
								consume();
							}
							}
							}
							setState(140); 
							_errHandler.sync(this);
							_la = _input.LA(1);
						} while ( _la==T__4 || _la==NL );
						}
						} 
					}
					setState(146);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				}
				setState(147);
				templateStat();
				setState(151);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__4 || _la==NL) {
					{
					{
					setState(148);
					_la = _input.LA(1);
					if ( !(_la==T__4 || _la==NL) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					}
					setState(153);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(154);
				match(T__3);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TemplateStatContext extends ParserRuleContext {
		public Def_Context def_() {
			return getRuleContext(Def_Context.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TemplateStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_templateStat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterTemplateStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitTemplateStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitTemplateStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TemplateStatContext templateStat() throws RecognitionException {
		TemplateStatContext _localctx = new TemplateStatContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_templateStat);
		try {
			setState(160);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
			case T__8:
			case T__9:
			case T__23:
			case T__24:
				enterOuterAlt(_localctx, 1);
				{
				setState(158);
				def_();
				}
				break;
			case T__5:
			case T__12:
			case T__14:
			case T__15:
			case T__16:
			case T__18:
			case T__19:
			case T__22:
			case T__26:
			case T__27:
			case T__28:
			case Id:
			case OpPrecedence1:
			case OpPrecedence2:
			case OpPrecedence3:
			case OpPrecedence4:
			case OpPrecedence5:
			case OpPrecedence6:
			case OpPrecedence7:
			case OpPrecedence8:
			case OpPrecedence9:
			case BooleanLiteral:
			case CharacterLiteral:
			case SymbolLiteral:
			case IntegerLiteral:
			case StringLiteral:
			case FloatingPointLiteral:
			case Minus:
				enterOuterAlt(_localctx, 2);
				{
				setState(159);
				expr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassParamClauseContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(TinyScalaParser.NL, 0); }
		public ClassParamsContext classParams() {
			return getRuleContext(ClassParamsContext.class,0);
		}
		public ClassParamClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classParamClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterClassParamClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitClassParamClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitClassParamClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassParamClauseContext classParamClause() throws RecognitionException {
		ClassParamClauseContext _localctx = new ClassParamClauseContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_classParamClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NL) {
				{
				setState(162);
				match(NL);
				}
			}

			setState(165);
			match(T__5);
			setState(167);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8589936128L) != 0)) {
				{
				setState(166);
				classParams();
				}
			}

			setState(169);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassParamsContext extends ParserRuleContext {
		public List<ClassParamContext> classParam() {
			return getRuleContexts(ClassParamContext.class);
		}
		public ClassParamContext classParam(int i) {
			return getRuleContext(ClassParamContext.class,i);
		}
		public ClassParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classParams; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterClassParams(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitClassParams(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitClassParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassParamsContext classParams() throws RecognitionException {
		ClassParamsContext _localctx = new ClassParamsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_classParams);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(171);
			classParam();
			setState(176);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(172);
				match(T__7);
				setState(173);
				classParam();
				}
				}
				setState(178);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassParamContext extends ParserRuleContext {
		public TerminalNode Id() { return getToken(TinyScalaParser.Id, 0); }
		public ParamTypeContext paramType() {
			return getRuleContext(ParamTypeContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ClassParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classParam; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterClassParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitClassParam(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitClassParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassParamContext classParam() throws RecognitionException {
		ClassParamContext _localctx = new ClassParamContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_classParam);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8 || _la==T__9) {
				{
				setState(179);
				_la = _input.LA(1);
				if ( !(_la==T__8 || _la==T__9) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(182);
			match(Id);
			setState(183);
			match(T__10);
			setState(184);
			paramType();
			setState(187);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(185);
				match(T__11);
				setState(186);
				expr();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> NL() { return getTokens(TinyScalaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TinyScalaParser.NL, i);
		}
		public EnumeratorsContext enumerators() {
			return getRuleContext(EnumeratorsContext.class,0);
		}
		public TerminalNode Id() { return getToken(TinyScalaParser.Id, 0); }
		public NewClassExprContext newClassExpr() {
			return getRuleContext(NewClassExprContext.class,0);
		}
		public SimpleExpr1Context simpleExpr1() {
			return getRuleContext(SimpleExpr1Context.class,0);
		}
		public InfixExprContext infixExpr() {
			return getRuleContext(InfixExprContext.class,0);
		}
		public CaseClausesContext caseClauses() {
			return getRuleContext(CaseClausesContext.class,0);
		}
		public ArgumentExprsContext argumentExprs() {
			return getRuleContext(ArgumentExprsContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_expr);
		int _la;
		try {
			setState(269);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(189);
				match(T__12);
				setState(190);
				match(T__5);
				setState(191);
				expr();
				setState(192);
				match(T__6);
				setState(196);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(193);
					match(NL);
					}
					}
					setState(198);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(199);
				expr();
				setState(202);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
				case 1:
					{
					setState(200);
					match(T__13);
					setState(201);
					expr();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(204);
				match(T__14);
				setState(205);
				match(T__5);
				setState(206);
				expr();
				setState(207);
				match(T__6);
				setState(211);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(208);
					match(NL);
					}
					}
					setState(213);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(214);
				expr();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(216);
				match(T__15);
				setState(217);
				expr();
				setState(218);
				match(T__14);
				setState(219);
				match(T__5);
				setState(220);
				expr();
				setState(221);
				match(T__6);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(223);
				match(T__16);
				setState(232);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__5:
					{
					setState(224);
					match(T__5);
					setState(225);
					enumerators();
					setState(226);
					match(T__6);
					}
					break;
				case T__2:
					{
					setState(228);
					match(T__2);
					setState(229);
					enumerators();
					setState(230);
					match(T__3);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(235);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__17) {
					{
					setState(234);
					match(T__17);
					}
				}

				setState(237);
				expr();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(239);
				match(T__18);
				setState(241);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
				case 1:
					{
					setState(240);
					expr();
					}
					break;
				}
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(252);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
				case 1:
					{
					setState(248);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
					case 1:
						{
						setState(243);
						newClassExpr();
						}
						break;
					case 2:
						{
						setState(244);
						simpleExpr1(0);
						setState(246);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==T__19) {
							{
							setState(245);
							match(T__19);
							}
						}

						}
						break;
					}
					setState(250);
					match(T__20);
					}
					break;
				}
				setState(254);
				match(Id);
				setState(255);
				match(T__11);
				setState(256);
				expr();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(257);
				infixExpr(0);
				setState(258);
				match(T__21);
				setState(259);
				match(T__2);
				setState(260);
				caseClauses();
				setState(261);
				match(T__3);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(263);
				infixExpr(0);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(264);
				simpleExpr1(0);
				setState(265);
				argumentExprs();
				setState(266);
				match(T__11);
				setState(267);
				expr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InfixExprContext extends ParserRuleContext {
		public PrefixExprContext prefixExpr() {
			return getRuleContext(PrefixExprContext.class,0);
		}
		public List<InfixExprContext> infixExpr() {
			return getRuleContexts(InfixExprContext.class);
		}
		public InfixExprContext infixExpr(int i) {
			return getRuleContext(InfixExprContext.class,i);
		}
		public TerminalNode OpPrecedence1() { return getToken(TinyScalaParser.OpPrecedence1, 0); }
		public TerminalNode NL() { return getToken(TinyScalaParser.NL, 0); }
		public TerminalNode OpPrecedence2() { return getToken(TinyScalaParser.OpPrecedence2, 0); }
		public TerminalNode OpPrecedence3() { return getToken(TinyScalaParser.OpPrecedence3, 0); }
		public TerminalNode OpPrecedence4() { return getToken(TinyScalaParser.OpPrecedence4, 0); }
		public TerminalNode OpPrecedence5() { return getToken(TinyScalaParser.OpPrecedence5, 0); }
		public TerminalNode OpPrecedence6() { return getToken(TinyScalaParser.OpPrecedence6, 0); }
		public TerminalNode OpPrecedence7() { return getToken(TinyScalaParser.OpPrecedence7, 0); }
		public TerminalNode OpPrecedence8() { return getToken(TinyScalaParser.OpPrecedence8, 0); }
		public TerminalNode OpPrecedence9() { return getToken(TinyScalaParser.OpPrecedence9, 0); }
		public InfixExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_infixExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterInfixExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitInfixExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitInfixExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InfixExprContext infixExpr() throws RecognitionException {
		return infixExpr(0);
	}

	private InfixExprContext infixExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		InfixExprContext _localctx = new InfixExprContext(_ctx, _parentState);
		InfixExprContext _prevctx = _localctx;
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_infixExpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(272);
			prefixExpr();
			}
			_ctx.stop = _input.LT(-1);
			setState(330);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(328);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
					case 1:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(274);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(275);
						match(OpPrecedence1);
						setState(277);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(276);
							match(NL);
							}
						}

						setState(279);
						infixExpr(11);
						}
						break;
					case 2:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(280);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(281);
						match(OpPrecedence2);
						setState(283);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(282);
							match(NL);
							}
						}

						setState(285);
						infixExpr(10);
						}
						break;
					case 3:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(286);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(287);
						match(OpPrecedence3);
						setState(289);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(288);
							match(NL);
							}
						}

						setState(291);
						infixExpr(9);
						}
						break;
					case 4:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(292);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(293);
						match(OpPrecedence4);
						setState(295);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(294);
							match(NL);
							}
						}

						setState(297);
						infixExpr(8);
						}
						break;
					case 5:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(298);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(299);
						match(OpPrecedence5);
						setState(301);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(300);
							match(NL);
							}
						}

						setState(303);
						infixExpr(7);
						}
						break;
					case 6:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(304);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(305);
						match(OpPrecedence6);
						setState(307);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(306);
							match(NL);
							}
						}

						setState(309);
						infixExpr(6);
						}
						break;
					case 7:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(310);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(311);
						match(OpPrecedence7);
						setState(313);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(312);
							match(NL);
							}
						}

						setState(315);
						infixExpr(5);
						}
						break;
					case 8:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(316);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(317);
						match(OpPrecedence8);
						setState(319);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(318);
							match(NL);
							}
						}

						setState(321);
						infixExpr(4);
						}
						break;
					case 9:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(322);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(323);
						match(OpPrecedence9);
						setState(325);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(324);
							match(NL);
							}
						}

						setState(327);
						infixExpr(3);
						}
						break;
					}
					} 
				}
				setState(332);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrefixExprContext extends ParserRuleContext {
		public NewClassExprContext newClassExpr() {
			return getRuleContext(NewClassExprContext.class,0);
		}
		public SimpleExpr1Context simpleExpr1() {
			return getRuleContext(SimpleExpr1Context.class,0);
		}
		public OpNoPrecedenceContext opNoPrecedence() {
			return getRuleContext(OpNoPrecedenceContext.class,0);
		}
		public PrefixExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterPrefixExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitPrefixExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitPrefixExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixExprContext prefixExpr() throws RecognitionException {
		PrefixExprContext _localctx = new PrefixExprContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_prefixExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(334);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8778913153024L) != 0)) {
				{
				setState(333);
				opNoPrecedence();
				}
			}

			setState(341);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				{
				setState(336);
				newClassExpr();
				}
				break;
			case 2:
				{
				setState(337);
				simpleExpr1(0);
				setState(339);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
				case 1:
					{
					setState(338);
					match(T__19);
					}
					break;
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NewClassExprContext extends ParserRuleContext {
		public TerminalNode Id() { return getToken(TinyScalaParser.Id, 0); }
		public List<ArgumentExprsContext> argumentExprs() {
			return getRuleContexts(ArgumentExprsContext.class);
		}
		public ArgumentExprsContext argumentExprs(int i) {
			return getRuleContext(ArgumentExprsContext.class,i);
		}
		public NewClassExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newClassExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterNewClassExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitNewClassExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitNewClassExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NewClassExprContext newClassExpr() throws RecognitionException {
		NewClassExprContext _localctx = new NewClassExprContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_newClassExpr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(343);
			match(T__22);
			setState(344);
			match(Id);
			setState(348);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(345);
					argumentExprs();
					}
					} 
				}
				setState(350);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SimpleExpr1Context extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public StableIdContext stableId() {
			return getRuleContext(StableIdContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NewClassExprContext newClassExpr() {
			return getRuleContext(NewClassExprContext.class,0);
		}
		public TerminalNode Id() { return getToken(TinyScalaParser.Id, 0); }
		public SimpleExpr1Context simpleExpr1() {
			return getRuleContext(SimpleExpr1Context.class,0);
		}
		public ArgumentExprsContext argumentExprs() {
			return getRuleContext(ArgumentExprsContext.class,0);
		}
		public SimpleExpr1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleExpr1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterSimpleExpr1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitSimpleExpr1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitSimpleExpr1(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleExpr1Context simpleExpr1() throws RecognitionException {
		return simpleExpr1(0);
	}

	private SimpleExpr1Context simpleExpr1(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		SimpleExpr1Context _localctx = new SimpleExpr1Context(_ctx, _parentState);
		SimpleExpr1Context _prevctx = _localctx;
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_simpleExpr1, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(363);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__26:
			case BooleanLiteral:
			case CharacterLiteral:
			case SymbolLiteral:
			case IntegerLiteral:
			case StringLiteral:
			case FloatingPointLiteral:
			case Minus:
				{
				setState(352);
				literal();
				}
				break;
			case T__27:
			case T__28:
			case Id:
				{
				setState(353);
				stableId(0);
				}
				break;
			case T__19:
				{
				setState(354);
				match(T__19);
				}
				break;
			case T__5:
				{
				setState(355);
				match(T__5);
				setState(356);
				expr();
				setState(357);
				match(T__6);
				}
				break;
			case T__22:
				{
				setState(359);
				newClassExpr();
				setState(360);
				match(T__20);
				setState(361);
				match(Id);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(375);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(373);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
					case 1:
						{
						_localctx = new SimpleExpr1Context(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_simpleExpr1);
						setState(365);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(367);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==T__19) {
							{
							setState(366);
							match(T__19);
							}
						}

						setState(369);
						match(T__20);
						setState(370);
						match(Id);
						}
						break;
					case 2:
						{
						_localctx = new SimpleExpr1Context(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_simpleExpr1);
						setState(371);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(372);
						argumentExprs();
						}
						break;
					}
					} 
				}
				setState(377);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprsContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ExprsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterExprs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitExprs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitExprs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprsContext exprs() throws RecognitionException {
		ExprsContext _localctx = new ExprsContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_exprs);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(378);
			expr();
			setState(383);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(379);
					match(T__7);
					setState(380);
					expr();
					}
					} 
				}
				setState(385);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentExprsContext extends ParserRuleContext {
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public BlockExprContext blockExpr() {
			return getRuleContext(BlockExprContext.class,0);
		}
		public TerminalNode NL() { return getToken(TinyScalaParser.NL, 0); }
		public ArgumentExprsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentExprs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterArgumentExprs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitArgumentExprs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitArgumentExprs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentExprsContext argumentExprs() throws RecognitionException {
		ArgumentExprsContext _localctx = new ArgumentExprsContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_argumentExprs);
		int _la;
		try {
			setState(394);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				setState(386);
				match(T__5);
				setState(387);
				args();
				setState(388);
				match(T__6);
				}
				break;
			case T__2:
			case NL:
				enterOuterAlt(_localctx, 2);
				{
				setState(391);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NL) {
					{
					setState(390);
					match(NL);
					}
				}

				setState(393);
				blockExpr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgsContext extends ParserRuleContext {
		public ExprsContext exprs() {
			return getRuleContext(ExprsContext.class,0);
		}
		public InfixExprContext infixExpr() {
			return getRuleContext(InfixExprContext.class,0);
		}
		public ArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_args; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgsContext args() throws RecognitionException {
		ArgsContext _localctx = new ArgsContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_args);
		int _la;
		try {
			setState(408);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(397);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9570141567950912L) != 0)) {
					{
					setState(396);
					exprs();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(402);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
				case 1:
					{
					setState(399);
					exprs();
					setState(400);
					match(T__7);
					}
					break;
				}
				setState(404);
				infixExpr(0);
				setState(406);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__10 || _la==T__19) {
					{
					setState(405);
					_la = _input.LA(1);
					if ( !(_la==T__10 || _la==T__19) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockExprContext extends ParserRuleContext {
		public CaseClausesContext caseClauses() {
			return getRuleContext(CaseClausesContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public BlockExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterBlockExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitBlockExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitBlockExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockExprContext blockExpr() throws RecognitionException {
		BlockExprContext _localctx = new BlockExprContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_blockExpr);
		try {
			setState(418);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(410);
				match(T__2);
				setState(411);
				caseClauses();
				setState(412);
				match(T__3);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(414);
				match(T__2);
				setState(415);
				block();
				setState(416);
				match(T__3);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public List<BlockStatContext> blockStat() {
			return getRuleContexts(BlockStatContext.class);
		}
		public BlockStatContext blockStat(int i) {
			return getRuleContext(BlockStatContext.class,i);
		}
		public ResultExprContext resultExpr() {
			return getRuleContext(ResultExprContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_block);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(421); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(420);
					blockStat();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(423); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,55,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(426);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9570141567950912L) != 0)) {
				{
				setState(425);
				resultExpr();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockStatContext extends ParserRuleContext {
		public Def_Context def_() {
			return getRuleContext(Def_Context.class,0);
		}
		public TmplDefContext tmplDef() {
			return getRuleContext(TmplDefContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public BlockStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockStat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterBlockStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitBlockStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitBlockStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockStatContext blockStat() throws RecognitionException {
		BlockStatContext _localctx = new BlockStatContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_blockStat);
		try {
			setState(431);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,57,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(428);
				def_();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(429);
				tmplDef();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(430);
				expr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ResultExprContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ResultExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_resultExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterResultExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitResultExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitResultExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ResultExprContext resultExpr() throws RecognitionException {
		ResultExprContext _localctx = new ResultExprContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_resultExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(433);
			expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Def_Context extends ParserRuleContext {
		public PatVarDefContext patVarDef() {
			return getRuleContext(PatVarDefContext.class,0);
		}
		public FunDefContext funDef() {
			return getRuleContext(FunDefContext.class,0);
		}
		public TypeDefContext typeDef() {
			return getRuleContext(TypeDefContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(TinyScalaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TinyScalaParser.NL, i);
		}
		public TmplDefContext tmplDef() {
			return getRuleContext(TmplDefContext.class,0);
		}
		public Def_Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_def_; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterDef_(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitDef_(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitDef_(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Def_Context def_() throws RecognitionException {
		Def_Context _localctx = new Def_Context(_ctx, getState());
		enterRule(_localctx, 38, RULE_def_);
		int _la;
		try {
			setState(447);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__8:
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(435);
				patVarDef();
				}
				break;
			case T__23:
				enterOuterAlt(_localctx, 2);
				{
				setState(436);
				match(T__23);
				setState(437);
				funDef();
				}
				break;
			case T__24:
				enterOuterAlt(_localctx, 3);
				{
				setState(438);
				match(T__24);
				setState(442);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(439);
					match(NL);
					}
					}
					setState(444);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(445);
				typeDef();
				}
				break;
			case T__0:
			case T__1:
				enterOuterAlt(_localctx, 4);
				{
				setState(446);
				tmplDef();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PatVarDefContext extends ParserRuleContext {
		public PatDefContext patDef() {
			return getRuleContext(PatDefContext.class,0);
		}
		public PatVarDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_patVarDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterPatVarDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitPatVarDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitPatVarDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PatVarDefContext patVarDef() throws RecognitionException {
		PatVarDefContext _localctx = new PatVarDefContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_patVarDef);
		try {
			setState(453);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__8:
				enterOuterAlt(_localctx, 1);
				{
				setState(449);
				match(T__8);
				setState(450);
				patDef();
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 2);
				{
				setState(451);
				match(T__9);
				setState(452);
				patDef();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PatDefContext extends ParserRuleContext {
		public Pattern1Context pattern1() {
			return getRuleContext(Pattern1Context.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Type_Context type_() {
			return getRuleContext(Type_Context.class,0);
		}
		public PatDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_patDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterPatDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitPatDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitPatDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PatDefContext patDef() throws RecognitionException {
		PatDefContext _localctx = new PatDefContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_patDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(455);
			pattern1();
			setState(458);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(456);
				match(T__10);
				setState(457);
				type_();
				}
			}

			setState(460);
			match(T__11);
			setState(461);
			expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunDefContext extends ParserRuleContext {
		public FunSigContext funSig() {
			return getRuleContext(FunSigContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Type_Context type_() {
			return getRuleContext(Type_Context.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode NL() { return getToken(TinyScalaParser.NL, 0); }
		public FunDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterFunDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitFunDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitFunDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunDefContext funDef() throws RecognitionException {
		FunDefContext _localctx = new FunDefContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_funDef);
		int _la;
		try {
			setState(479);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,64,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(463);
				funSig();
				setState(466);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__10) {
					{
					setState(464);
					match(T__10);
					setState(465);
					type_();
					}
				}

				setState(468);
				match(T__11);
				setState(469);
				expr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(471);
				funSig();
				setState(473);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NL) {
					{
					setState(472);
					match(NL);
					}
				}

				setState(475);
				match(T__2);
				setState(476);
				block();
				setState(477);
				match(T__3);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunSigContext extends ParserRuleContext {
		public TerminalNode Id() { return getToken(TinyScalaParser.Id, 0); }
		public ParamClausesContext paramClauses() {
			return getRuleContext(ParamClausesContext.class,0);
		}
		public FunSigContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funSig; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterFunSig(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitFunSig(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitFunSig(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunSigContext funSig() throws RecognitionException {
		FunSigContext _localctx = new FunSigContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_funSig);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(481);
			match(Id);
			setState(482);
			paramClauses();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamClausesContext extends ParserRuleContext {
		public List<ParamClauseContext> paramClause() {
			return getRuleContexts(ParamClauseContext.class);
		}
		public ParamClauseContext paramClause(int i) {
			return getRuleContext(ParamClauseContext.class,i);
		}
		public ParamClausesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramClauses; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterParamClauses(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitParamClauses(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitParamClauses(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamClausesContext paramClauses() throws RecognitionException {
		ParamClausesContext _localctx = new ParamClausesContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_paramClauses);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(487);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,65,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(484);
					paramClause();
					}
					} 
				}
				setState(489);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,65,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamClauseContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(TinyScalaParser.NL, 0); }
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public ParamClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterParamClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitParamClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitParamClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamClauseContext paramClause() throws RecognitionException {
		ParamClauseContext _localctx = new ParamClauseContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_paramClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(491);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NL) {
				{
				setState(490);
				match(NL);
				}
			}

			setState(493);
			match(T__5);
			setState(495);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Id) {
				{
				setState(494);
				params();
				}
			}

			setState(497);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamsContext extends ParserRuleContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public ParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterParams(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitParams(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_params);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(499);
			param();
			setState(504);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(500);
				match(T__7);
				setState(501);
				param();
				}
				}
				setState(506);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamContext extends ParserRuleContext {
		public TerminalNode Id() { return getToken(TinyScalaParser.Id, 0); }
		public ParamTypeContext paramType() {
			return getRuleContext(ParamTypeContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitParam(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_param);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(507);
			match(Id);
			setState(510);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(508);
				match(T__10);
				setState(509);
				paramType();
				}
			}

			setState(514);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(512);
				match(T__11);
				setState(513);
				expr();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamTypeContext extends ParserRuleContext {
		public Type_Context type_() {
			return getRuleContext(Type_Context.class,0);
		}
		public ParamTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterParamType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitParamType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitParamType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamTypeContext paramType() throws RecognitionException {
		ParamTypeContext _localctx = new ParamTypeContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_paramType);
		try {
			setState(519);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
			case T__27:
			case T__28:
			case Id:
				enterOuterAlt(_localctx, 1);
				{
				setState(516);
				type_();
				}
				break;
			case T__25:
				enterOuterAlt(_localctx, 2);
				{
				setState(517);
				match(T__25);
				setState(518);
				type_();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeDefContext extends ParserRuleContext {
		public TerminalNode Id() { return getToken(TinyScalaParser.Id, 0); }
		public Type_Context type_() {
			return getRuleContext(Type_Context.class,0);
		}
		public TypeDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterTypeDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitTypeDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitTypeDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeDefContext typeDef() throws RecognitionException {
		TypeDefContext _localctx = new TypeDefContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_typeDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(521);
			match(Id);
			setState(522);
			match(T__11);
			setState(523);
			type_();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Type_Context extends ParserRuleContext {
		public FunctionArgTypesContext functionArgTypes() {
			return getRuleContext(FunctionArgTypesContext.class,0);
		}
		public Type_Context type_() {
			return getRuleContext(Type_Context.class,0);
		}
		public SimpleTypeContext simpleType() {
			return getRuleContext(SimpleTypeContext.class,0);
		}
		public Type_Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterType_(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitType_(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitType_(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_Context type_() throws RecognitionException {
		Type_Context _localctx = new Type_Context(_ctx, getState());
		enterRule(_localctx, 60, RULE_type_);
		try {
			setState(530);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,72,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(525);
				functionArgTypes();
				setState(526);
				match(T__25);
				setState(527);
				type_();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(529);
				simpleType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionArgTypesContext extends ParserRuleContext {
		public SimpleTypeContext simpleType() {
			return getRuleContext(SimpleTypeContext.class,0);
		}
		public List<ParamTypeContext> paramType() {
			return getRuleContexts(ParamTypeContext.class);
		}
		public ParamTypeContext paramType(int i) {
			return getRuleContext(ParamTypeContext.class,i);
		}
		public FunctionArgTypesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionArgTypes; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterFunctionArgTypes(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitFunctionArgTypes(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitFunctionArgTypes(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionArgTypesContext functionArgTypes() throws RecognitionException {
		FunctionArgTypesContext _localctx = new FunctionArgTypesContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_functionArgTypes);
		int _la;
		try {
			setState(545);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,75,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(532);
				simpleType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(533);
				match(T__5);
				setState(542);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9462349888L) != 0)) {
					{
					setState(534);
					paramType();
					setState(539);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__7) {
						{
						{
						setState(535);
						match(T__7);
						setState(536);
						paramType();
						}
						}
						setState(541);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(544);
				match(T__6);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SimpleTypeContext extends ParserRuleContext {
		public StableIdContext stableId() {
			return getRuleContext(StableIdContext.class,0);
		}
		public TypesContext types() {
			return getRuleContext(TypesContext.class,0);
		}
		public SimpleTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterSimpleType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitSimpleType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitSimpleType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleTypeContext simpleType() throws RecognitionException {
		SimpleTypeContext _localctx = new SimpleTypeContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_simpleType);
		int _la;
		try {
			setState(556);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__27:
			case T__28:
			case Id:
				enterOuterAlt(_localctx, 1);
				{
				setState(547);
				stableId(0);
				setState(550);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__20) {
					{
					setState(548);
					match(T__20);
					setState(549);
					match(T__24);
					}
				}

				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 2);
				{
				setState(552);
				match(T__5);
				setState(553);
				types();
				setState(554);
				match(T__6);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypesContext extends ParserRuleContext {
		public List<Type_Context> type_() {
			return getRuleContexts(Type_Context.class);
		}
		public Type_Context type_(int i) {
			return getRuleContext(Type_Context.class,i);
		}
		public TypesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_types; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterTypes(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitTypes(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitTypes(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypesContext types() throws RecognitionException {
		TypesContext _localctx = new TypesContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_types);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(558);
			type_();
			setState(563);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(559);
				match(T__7);
				setState(560);
				type_();
				}
				}
				setState(565);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode IntegerLiteral() { return getToken(TinyScalaParser.IntegerLiteral, 0); }
		public TerminalNode Minus() { return getToken(TinyScalaParser.Minus, 0); }
		public TerminalNode FloatingPointLiteral() { return getToken(TinyScalaParser.FloatingPointLiteral, 0); }
		public TerminalNode BooleanLiteral() { return getToken(TinyScalaParser.BooleanLiteral, 0); }
		public TerminalNode CharacterLiteral() { return getToken(TinyScalaParser.CharacterLiteral, 0); }
		public TerminalNode StringLiteral() { return getToken(TinyScalaParser.StringLiteral, 0); }
		public TerminalNode SymbolLiteral() { return getToken(TinyScalaParser.SymbolLiteral, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_literal);
		int _la;
		try {
			setState(579);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,81,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(567);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Minus) {
					{
					setState(566);
					match(Minus);
					}
				}

				setState(569);
				match(IntegerLiteral);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(571);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Minus) {
					{
					setState(570);
					match(Minus);
					}
				}

				setState(573);
				match(FloatingPointLiteral);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(574);
				match(BooleanLiteral);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(575);
				match(CharacterLiteral);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(576);
				match(StringLiteral);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(577);
				match(SymbolLiteral);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(578);
				match(T__26);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IdsContext extends ParserRuleContext {
		public List<TerminalNode> Id() { return getTokens(TinyScalaParser.Id); }
		public TerminalNode Id(int i) {
			return getToken(TinyScalaParser.Id, i);
		}
		public IdsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ids; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterIds(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitIds(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitIds(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdsContext ids() throws RecognitionException {
		IdsContext _localctx = new IdsContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_ids);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(581);
			match(Id);
			setState(586);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(582);
				match(T__7);
				setState(583);
				match(Id);
				}
				}
				setState(588);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StableIdContext extends ParserRuleContext {
		public List<TerminalNode> Id() { return getTokens(TinyScalaParser.Id); }
		public TerminalNode Id(int i) {
			return getToken(TinyScalaParser.Id, i);
		}
		public StableIdContext stableId() {
			return getRuleContext(StableIdContext.class,0);
		}
		public StableIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stableId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterStableId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitStableId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitStableId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StableIdContext stableId() throws RecognitionException {
		return stableId(0);
	}

	private StableIdContext stableId(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		StableIdContext _localctx = new StableIdContext(_ctx, _parentState);
		StableIdContext _prevctx = _localctx;
		int _startState = 72;
		enterRecursionRule(_localctx, 72, RULE_stableId, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(601);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,85,_ctx) ) {
			case 1:
				{
				setState(590);
				match(Id);
				}
				break;
			case 2:
				{
				setState(593);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Id) {
					{
					setState(591);
					match(Id);
					setState(592);
					match(T__20);
					}
				}

				setState(599);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__27:
					{
					setState(595);
					match(T__27);
					}
					break;
				case T__28:
					{
					setState(596);
					match(T__28);
					setState(597);
					match(T__20);
					setState(598);
					match(Id);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(608);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,86,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new StableIdContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_stableId);
					setState(603);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(604);
					match(T__20);
					setState(605);
					match(Id);
					}
					} 
				}
				setState(610);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,86,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OpNoPrecedenceContext extends ParserRuleContext {
		public TerminalNode OpPrecedence1() { return getToken(TinyScalaParser.OpPrecedence1, 0); }
		public TerminalNode OpPrecedence2() { return getToken(TinyScalaParser.OpPrecedence2, 0); }
		public TerminalNode OpPrecedence3() { return getToken(TinyScalaParser.OpPrecedence3, 0); }
		public TerminalNode OpPrecedence4() { return getToken(TinyScalaParser.OpPrecedence4, 0); }
		public TerminalNode OpPrecedence5() { return getToken(TinyScalaParser.OpPrecedence5, 0); }
		public TerminalNode OpPrecedence6() { return getToken(TinyScalaParser.OpPrecedence6, 0); }
		public TerminalNode OpPrecedence7() { return getToken(TinyScalaParser.OpPrecedence7, 0); }
		public TerminalNode OpPrecedence8() { return getToken(TinyScalaParser.OpPrecedence8, 0); }
		public TerminalNode OpPrecedence9() { return getToken(TinyScalaParser.OpPrecedence9, 0); }
		public OpNoPrecedenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_opNoPrecedence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterOpNoPrecedence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitOpNoPrecedence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitOpNoPrecedence(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpNoPrecedenceContext opNoPrecedence() throws RecognitionException {
		OpNoPrecedenceContext _localctx = new OpNoPrecedenceContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_opNoPrecedence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(611);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 8778913153024L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CaseClausesContext extends ParserRuleContext {
		public List<CaseClauseContext> caseClause() {
			return getRuleContexts(CaseClauseContext.class);
		}
		public CaseClauseContext caseClause(int i) {
			return getRuleContext(CaseClauseContext.class,i);
		}
		public CaseClausesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseClauses; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterCaseClauses(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitCaseClauses(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitCaseClauses(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseClausesContext caseClauses() throws RecognitionException {
		CaseClausesContext _localctx = new CaseClausesContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_caseClauses);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(614); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(613);
				caseClause();
				}
				}
				setState(616); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__29 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CaseClauseContext extends ParserRuleContext {
		public PatternContext pattern() {
			return getRuleContext(PatternContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public CaseClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterCaseClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitCaseClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitCaseClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseClauseContext caseClause() throws RecognitionException {
		CaseClauseContext _localctx = new CaseClauseContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_caseClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(618);
			match(T__29);
			setState(619);
			pattern();
			setState(620);
			match(T__25);
			setState(621);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PatternContext extends ParserRuleContext {
		public List<Pattern1Context> pattern1() {
			return getRuleContexts(Pattern1Context.class);
		}
		public Pattern1Context pattern1(int i) {
			return getRuleContext(Pattern1Context.class,i);
		}
		public PatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pattern; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PatternContext pattern() throws RecognitionException {
		PatternContext _localctx = new PatternContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_pattern);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(623);
			pattern1();
			setState(628);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__30) {
				{
				{
				setState(624);
				match(T__30);
				setState(625);
				pattern1();
				}
				}
				setState(630);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Pattern1Context extends ParserRuleContext {
		public Type_Context type_() {
			return getRuleContext(Type_Context.class,0);
		}
		public TerminalNode BoundVarid() { return getToken(TinyScalaParser.BoundVarid, 0); }
		public TerminalNode Id() { return getToken(TinyScalaParser.Id, 0); }
		public SimplePatternContext simplePattern() {
			return getRuleContext(SimplePatternContext.class,0);
		}
		public Pattern1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pattern1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterPattern1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitPattern1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitPattern1(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Pattern1Context pattern1() throws RecognitionException {
		Pattern1Context _localctx = new Pattern1Context(_ctx, getState());
		enterRule(_localctx, 82, RULE_pattern1);
		int _la;
		try {
			setState(635);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,89,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(631);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1125908497825792L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(632);
				match(T__10);
				setState(633);
				type_();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(634);
				simplePattern();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SimplePatternContext extends ParserRuleContext {
		public TerminalNode Varid() { return getToken(TinyScalaParser.Varid, 0); }
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public StableIdContext stableId() {
			return getRuleContext(StableIdContext.class,0);
		}
		public PatternsContext patterns() {
			return getRuleContext(PatternsContext.class,0);
		}
		public SimplePatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simplePattern; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterSimplePattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitSimplePattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitSimplePattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimplePatternContext simplePattern() throws RecognitionException {
		SimplePatternContext _localctx = new SimplePatternContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_simplePattern);
		int _la;
		try {
			setState(653);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__19:
				enterOuterAlt(_localctx, 1);
				{
				setState(637);
				match(T__19);
				}
				break;
			case Varid:
				enterOuterAlt(_localctx, 2);
				{
				setState(638);
				match(Varid);
				}
				break;
			case T__26:
			case BooleanLiteral:
			case CharacterLiteral:
			case SymbolLiteral:
			case IntegerLiteral:
			case StringLiteral:
			case FloatingPointLiteral:
			case Minus:
				enterOuterAlt(_localctx, 3);
				{
				setState(639);
				literal();
				}
				break;
			case T__27:
			case T__28:
			case Id:
				enterOuterAlt(_localctx, 4);
				{
				setState(640);
				stableId(0);
				setState(646);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__5) {
					{
					setState(641);
					match(T__5);
					setState(643);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 11250212505911360L) != 0)) {
						{
						setState(642);
						patterns();
						}
					}

					setState(645);
					match(T__6);
					}
				}

				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 5);
				{
				setState(648);
				match(T__5);
				setState(650);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 11250212505911360L) != 0)) {
					{
					setState(649);
					patterns();
					}
				}

				setState(652);
				match(T__6);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PatternsContext extends ParserRuleContext {
		public PatternContext pattern() {
			return getRuleContext(PatternContext.class,0);
		}
		public PatternsContext patterns() {
			return getRuleContext(PatternsContext.class,0);
		}
		public PatternsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_patterns; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterPatterns(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitPatterns(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitPatterns(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PatternsContext patterns() throws RecognitionException {
		PatternsContext _localctx = new PatternsContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_patterns);
		int _la;
		try {
			setState(661);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,95,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(655);
				pattern();
				setState(658);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(656);
					match(T__7);
					setState(657);
					patterns();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(660);
				match(T__19);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EnumeratorsContext extends ParserRuleContext {
		public List<GeneratorContext> generator() {
			return getRuleContexts(GeneratorContext.class);
		}
		public GeneratorContext generator(int i) {
			return getRuleContext(GeneratorContext.class,i);
		}
		public EnumeratorsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumerators; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterEnumerators(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitEnumerators(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitEnumerators(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumeratorsContext enumerators() throws RecognitionException {
		EnumeratorsContext _localctx = new EnumeratorsContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_enumerators);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(664); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(663);
				generator();
				}
				}
				setState(666); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 11250212505911360L) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GeneratorContext extends ParserRuleContext {
		public List<Pattern1Context> pattern1() {
			return getRuleContexts(Pattern1Context.class);
		}
		public Pattern1Context pattern1(int i) {
			return getRuleContext(Pattern1Context.class,i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public GeneratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_generator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterGenerator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitGenerator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitGenerator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GeneratorContext generator() throws RecognitionException {
		GeneratorContext _localctx = new GeneratorContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_generator);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(668);
			pattern1();
			setState(669);
			match(T__31);
			setState(670);
			expr();
			setState(677);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,97,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(671);
					pattern1();
					setState(672);
					match(T__11);
					setState(673);
					expr();
					}
					} 
				}
				setState(679);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,97,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 8:
			return infixExpr_sempred((InfixExprContext)_localctx, predIndex);
		case 11:
			return simpleExpr1_sempred((SimpleExpr1Context)_localctx, predIndex);
		case 36:
			return stableId_sempred((StableIdContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean infixExpr_sempred(InfixExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 10);
		case 1:
			return precpred(_ctx, 9);
		case 2:
			return precpred(_ctx, 8);
		case 3:
			return precpred(_ctx, 7);
		case 4:
			return precpred(_ctx, 6);
		case 5:
			return precpred(_ctx, 5);
		case 6:
			return precpred(_ctx, 4);
		case 7:
			return precpred(_ctx, 3);
		case 8:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean simpleExpr1_sempred(SimpleExpr1Context _localctx, int predIndex) {
		switch (predIndex) {
		case 9:
			return precpred(_ctx, 2);
		case 10:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean stableId_sempred(StableIdContext _localctx, int predIndex) {
		switch (predIndex) {
		case 11:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001:\u02a9\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007\'\u0002"+
		"(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007,\u0002"+
		"-\u0007-\u0001\u0000\u0004\u0000^\b\u0000\u000b\u0000\f\u0000_\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0005\u0001e\b\u0001\n\u0001\f\u0001h\t\u0001"+
		"\u0001\u0001\u0003\u0001k\b\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0003\u0001p\b\u0001\u0003\u0001r\b\u0001\u0001\u0002\u0005\u0002u\b"+
		"\u0002\n\u0002\f\u0002x\t\u0002\u0001\u0002\u0001\u0002\u0005\u0002|\b"+
		"\u0002\n\u0002\f\u0002\u007f\t\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0005\u0002\u0084\b\u0002\n\u0002\f\u0002\u0087\t\u0002\u0001\u0002\u0001"+
		"\u0002\u0004\u0002\u008b\b\u0002\u000b\u0002\f\u0002\u008c\u0005\u0002"+
		"\u008f\b\u0002\n\u0002\f\u0002\u0092\t\u0002\u0001\u0002\u0001\u0002\u0005"+
		"\u0002\u0096\b\u0002\n\u0002\f\u0002\u0099\t\u0002\u0001\u0002\u0001\u0002"+
		"\u0003\u0002\u009d\b\u0002\u0001\u0003\u0001\u0003\u0003\u0003\u00a1\b"+
		"\u0003\u0001\u0004\u0003\u0004\u00a4\b\u0004\u0001\u0004\u0001\u0004\u0003"+
		"\u0004\u00a8\b\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0005\u0005\u00af\b\u0005\n\u0005\f\u0005\u00b2\t\u0005\u0001\u0006"+
		"\u0003\u0006\u00b5\b\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0003\u0006\u00bc\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0005\u0007\u00c3\b\u0007\n\u0007\f\u0007\u00c6"+
		"\t\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u00cb\b\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007"+
		"\u00d2\b\u0007\n\u0007\f\u0007\u00d5\t\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u00e9\b\u0007\u0001"+
		"\u0007\u0003\u0007\u00ec\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0003\u0007\u00f2\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003"+
		"\u0007\u00f7\b\u0007\u0003\u0007\u00f9\b\u0007\u0001\u0007\u0001\u0007"+
		"\u0003\u0007\u00fd\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007"+
		"\u010e\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b"+
		"\u0116\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u011c\b\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0003\b\u0122\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003"+
		"\b\u0128\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u012e\b\b\u0001\b"+
		"\u0001\b\u0001\b\u0001\b\u0003\b\u0134\b\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0003\b\u013a\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u0140\b\b"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u0146\b\b\u0001\b\u0005\b\u0149"+
		"\b\b\n\b\f\b\u014c\t\b\u0001\t\u0003\t\u014f\b\t\u0001\t\u0001\t\u0001"+
		"\t\u0003\t\u0154\b\t\u0003\t\u0156\b\t\u0001\n\u0001\n\u0001\n\u0005\n"+
		"\u015b\b\n\n\n\f\n\u015e\t\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u016c\b\u000b\u0001\u000b\u0001"+
		"\u000b\u0003\u000b\u0170\b\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0005\u000b\u0176\b\u000b\n\u000b\f\u000b\u0179\t\u000b\u0001\f"+
		"\u0001\f\u0001\f\u0005\f\u017e\b\f\n\f\f\f\u0181\t\f\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0003\r\u0188\b\r\u0001\r\u0003\r\u018b\b\r\u0001\u000e"+
		"\u0003\u000e\u018e\b\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e"+
		"\u0193\b\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u0197\b\u000e\u0003"+
		"\u000e\u0199\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u01a3\b\u000f\u0001"+
		"\u0010\u0004\u0010\u01a6\b\u0010\u000b\u0010\f\u0010\u01a7\u0001\u0010"+
		"\u0003\u0010\u01ab\b\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0003\u0011"+
		"\u01b0\b\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0005\u0013\u01b9\b\u0013\n\u0013\f\u0013\u01bc"+
		"\t\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u01c0\b\u0013\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0003\u0014\u01c6\b\u0014\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0003\u0015\u01cb\b\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0003\u0016\u01d3\b\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0003\u0016"+
		"\u01da\b\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0003\u0016"+
		"\u01e0\b\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0018\u0005\u0018"+
		"\u01e6\b\u0018\n\u0018\f\u0018\u01e9\t\u0018\u0001\u0019\u0003\u0019\u01ec"+
		"\b\u0019\u0001\u0019\u0001\u0019\u0003\u0019\u01f0\b\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0005\u001a\u01f7\b\u001a"+
		"\n\u001a\f\u001a\u01fa\t\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0003"+
		"\u001b\u01ff\b\u001b\u0001\u001b\u0001\u001b\u0003\u001b\u0203\b\u001b"+
		"\u0001\u001c\u0001\u001c\u0001\u001c\u0003\u001c\u0208\b\u001c\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001e"+
		"\u0001\u001e\u0001\u001e\u0003\u001e\u0213\b\u001e\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0005\u001f\u021a\b\u001f\n\u001f"+
		"\f\u001f\u021d\t\u001f\u0003\u001f\u021f\b\u001f\u0001\u001f\u0003\u001f"+
		"\u0222\b\u001f\u0001 \u0001 \u0001 \u0003 \u0227\b \u0001 \u0001 \u0001"+
		" \u0001 \u0003 \u022d\b \u0001!\u0001!\u0001!\u0005!\u0232\b!\n!\f!\u0235"+
		"\t!\u0001\"\u0003\"\u0238\b\"\u0001\"\u0001\"\u0003\"\u023c\b\"\u0001"+
		"\"\u0001\"\u0001\"\u0001\"\u0001\"\u0001\"\u0003\"\u0244\b\"\u0001#\u0001"+
		"#\u0001#\u0005#\u0249\b#\n#\f#\u024c\t#\u0001$\u0001$\u0001$\u0001$\u0003"+
		"$\u0252\b$\u0001$\u0001$\u0001$\u0001$\u0003$\u0258\b$\u0003$\u025a\b"+
		"$\u0001$\u0001$\u0001$\u0005$\u025f\b$\n$\f$\u0262\t$\u0001%\u0001%\u0001"+
		"&\u0004&\u0267\b&\u000b&\f&\u0268\u0001\'\u0001\'\u0001\'\u0001\'\u0001"+
		"\'\u0001(\u0001(\u0001(\u0005(\u0273\b(\n(\f(\u0276\t(\u0001)\u0001)\u0001"+
		")\u0001)\u0003)\u027c\b)\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0003"+
		"*\u0284\b*\u0001*\u0003*\u0287\b*\u0001*\u0001*\u0003*\u028b\b*\u0001"+
		"*\u0003*\u028e\b*\u0001+\u0001+\u0001+\u0003+\u0293\b+\u0001+\u0003+\u0296"+
		"\b+\u0001,\u0004,\u0299\b,\u000b,\f,\u029a\u0001-\u0001-\u0001-\u0001"+
		"-\u0001-\u0001-\u0001-\u0005-\u02a4\b-\n-\f-\u02a7\t-\u0001-\u0000\u0003"+
		"\u0010\u0016H.\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016"+
		"\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@BDFHJLNPRTVXZ\u0000\u0006\u0002"+
		"\u00006688\u0002\u0000\u0005\u000566\u0001\u0000\t\n\u0002\u0000\u000b"+
		"\u000b\u0014\u0014\u0001\u0000\"*\u0003\u0000\u0014\u0014!!22\u02f8\u0000"+
		"]\u0001\u0000\u0000\u0000\u0002q\u0001\u0000\u0000\u0000\u0004v\u0001"+
		"\u0000\u0000\u0000\u0006\u00a0\u0001\u0000\u0000\u0000\b\u00a3\u0001\u0000"+
		"\u0000\u0000\n\u00ab\u0001\u0000\u0000\u0000\f\u00b4\u0001\u0000\u0000"+
		"\u0000\u000e\u010d\u0001\u0000\u0000\u0000\u0010\u010f\u0001\u0000\u0000"+
		"\u0000\u0012\u014e\u0001\u0000\u0000\u0000\u0014\u0157\u0001\u0000\u0000"+
		"\u0000\u0016\u016b\u0001\u0000\u0000\u0000\u0018\u017a\u0001\u0000\u0000"+
		"\u0000\u001a\u018a\u0001\u0000\u0000\u0000\u001c\u0198\u0001\u0000\u0000"+
		"\u0000\u001e\u01a2\u0001\u0000\u0000\u0000 \u01a5\u0001\u0000\u0000\u0000"+
		"\"\u01af\u0001\u0000\u0000\u0000$\u01b1\u0001\u0000\u0000\u0000&\u01bf"+
		"\u0001\u0000\u0000\u0000(\u01c5\u0001\u0000\u0000\u0000*\u01c7\u0001\u0000"+
		"\u0000\u0000,\u01df\u0001\u0000\u0000\u0000.\u01e1\u0001\u0000\u0000\u0000"+
		"0\u01e7\u0001\u0000\u0000\u00002\u01eb\u0001\u0000\u0000\u00004\u01f3"+
		"\u0001\u0000\u0000\u00006\u01fb\u0001\u0000\u0000\u00008\u0207\u0001\u0000"+
		"\u0000\u0000:\u0209\u0001\u0000\u0000\u0000<\u0212\u0001\u0000\u0000\u0000"+
		">\u0221\u0001\u0000\u0000\u0000@\u022c\u0001\u0000\u0000\u0000B\u022e"+
		"\u0001\u0000\u0000\u0000D\u0243\u0001\u0000\u0000\u0000F\u0245\u0001\u0000"+
		"\u0000\u0000H\u0259\u0001\u0000\u0000\u0000J\u0263\u0001\u0000\u0000\u0000"+
		"L\u0266\u0001\u0000\u0000\u0000N\u026a\u0001\u0000\u0000\u0000P\u026f"+
		"\u0001\u0000\u0000\u0000R\u027b\u0001\u0000\u0000\u0000T\u028d\u0001\u0000"+
		"\u0000\u0000V\u0295\u0001\u0000\u0000\u0000X\u0298\u0001\u0000\u0000\u0000"+
		"Z\u029c\u0001\u0000\u0000\u0000\\^\u0003\u0002\u0001\u0000]\\\u0001\u0000"+
		"\u0000\u0000^_\u0001\u0000\u0000\u0000_]\u0001\u0000\u0000\u0000_`\u0001"+
		"\u0000\u0000\u0000`\u0001\u0001\u0000\u0000\u0000ab\u0005\u0001\u0000"+
		"\u0000bf\u0005!\u0000\u0000ce\u0003\b\u0004\u0000dc\u0001\u0000\u0000"+
		"\u0000eh\u0001\u0000\u0000\u0000fd\u0001\u0000\u0000\u0000fg\u0001\u0000"+
		"\u0000\u0000gj\u0001\u0000\u0000\u0000hf\u0001\u0000\u0000\u0000ik\u0003"+
		"\u0004\u0002\u0000ji\u0001\u0000\u0000\u0000jk\u0001\u0000\u0000\u0000"+
		"kr\u0001\u0000\u0000\u0000lm\u0005\u0002\u0000\u0000mo\u0005!\u0000\u0000"+
		"np\u0003\u0004\u0002\u0000on\u0001\u0000\u0000\u0000op\u0001\u0000\u0000"+
		"\u0000pr\u0001\u0000\u0000\u0000qa\u0001\u0000\u0000\u0000ql\u0001\u0000"+
		"\u0000\u0000r\u0003\u0001\u0000\u0000\u0000su\u00056\u0000\u0000ts\u0001"+
		"\u0000\u0000\u0000ux\u0001\u0000\u0000\u0000vt\u0001\u0000\u0000\u0000"+
		"vw\u0001\u0000\u0000\u0000w\u009c\u0001\u0000\u0000\u0000xv\u0001\u0000"+
		"\u0000\u0000y}\u0005\u0003\u0000\u0000z|\u0007\u0000\u0000\u0000{z\u0001"+
		"\u0000\u0000\u0000|\u007f\u0001\u0000\u0000\u0000}{\u0001\u0000\u0000"+
		"\u0000}~\u0001\u0000\u0000\u0000~\u0080\u0001\u0000\u0000\u0000\u007f"+
		"}\u0001\u0000\u0000\u0000\u0080\u009d\u0005\u0004\u0000\u0000\u0081\u0085"+
		"\u0005\u0003\u0000\u0000\u0082\u0084\u0007\u0000\u0000\u0000\u0083\u0082"+
		"\u0001\u0000\u0000\u0000\u0084\u0087\u0001\u0000\u0000\u0000\u0085\u0083"+
		"\u0001\u0000\u0000\u0000\u0085\u0086\u0001\u0000\u0000\u0000\u0086\u0090"+
		"\u0001\u0000\u0000\u0000\u0087\u0085\u0001\u0000\u0000\u0000\u0088\u008a"+
		"\u0003\u0006\u0003\u0000\u0089\u008b\u0007\u0001\u0000\u0000\u008a\u0089"+
		"\u0001\u0000\u0000\u0000\u008b\u008c\u0001\u0000\u0000\u0000\u008c\u008a"+
		"\u0001\u0000\u0000\u0000\u008c\u008d\u0001\u0000\u0000\u0000\u008d\u008f"+
		"\u0001\u0000\u0000\u0000\u008e\u0088\u0001\u0000\u0000\u0000\u008f\u0092"+
		"\u0001\u0000\u0000\u0000\u0090\u008e\u0001\u0000\u0000\u0000\u0090\u0091"+
		"\u0001\u0000\u0000\u0000\u0091\u0093\u0001\u0000\u0000\u0000\u0092\u0090"+
		"\u0001\u0000\u0000\u0000\u0093\u0097\u0003\u0006\u0003\u0000\u0094\u0096"+
		"\u0007\u0001\u0000\u0000\u0095\u0094\u0001\u0000\u0000\u0000\u0096\u0099"+
		"\u0001\u0000\u0000\u0000\u0097\u0095\u0001\u0000\u0000\u0000\u0097\u0098"+
		"\u0001\u0000\u0000\u0000\u0098\u009a\u0001\u0000\u0000\u0000\u0099\u0097"+
		"\u0001\u0000\u0000\u0000\u009a\u009b\u0005\u0004\u0000\u0000\u009b\u009d"+
		"\u0001\u0000\u0000\u0000\u009cy\u0001\u0000\u0000\u0000\u009c\u0081\u0001"+
		"\u0000\u0000\u0000\u009d\u0005\u0001\u0000\u0000\u0000\u009e\u00a1\u0003"+
		"&\u0013\u0000\u009f\u00a1\u0003\u000e\u0007\u0000\u00a0\u009e\u0001\u0000"+
		"\u0000\u0000\u00a0\u009f\u0001\u0000\u0000\u0000\u00a1\u0007\u0001\u0000"+
		"\u0000\u0000\u00a2\u00a4\u00056\u0000\u0000\u00a3\u00a2\u0001\u0000\u0000"+
		"\u0000\u00a3\u00a4\u0001\u0000\u0000\u0000\u00a4\u00a5\u0001\u0000\u0000"+
		"\u0000\u00a5\u00a7\u0005\u0006\u0000\u0000\u00a6\u00a8\u0003\n\u0005\u0000"+
		"\u00a7\u00a6\u0001\u0000\u0000\u0000\u00a7\u00a8\u0001\u0000\u0000\u0000"+
		"\u00a8\u00a9\u0001\u0000\u0000\u0000\u00a9\u00aa\u0005\u0007\u0000\u0000"+
		"\u00aa\t\u0001\u0000\u0000\u0000\u00ab\u00b0\u0003\f\u0006\u0000\u00ac"+
		"\u00ad\u0005\b\u0000\u0000\u00ad\u00af\u0003\f\u0006\u0000\u00ae\u00ac"+
		"\u0001\u0000\u0000\u0000\u00af\u00b2\u0001\u0000\u0000\u0000\u00b0\u00ae"+
		"\u0001\u0000\u0000\u0000\u00b0\u00b1\u0001\u0000\u0000\u0000\u00b1\u000b"+
		"\u0001\u0000\u0000\u0000\u00b2\u00b0\u0001\u0000\u0000\u0000\u00b3\u00b5"+
		"\u0007\u0002\u0000\u0000\u00b4\u00b3\u0001\u0000\u0000\u0000\u00b4\u00b5"+
		"\u0001\u0000\u0000\u0000\u00b5\u00b6\u0001\u0000\u0000\u0000\u00b6\u00b7"+
		"\u0005!\u0000\u0000\u00b7\u00b8\u0005\u000b\u0000\u0000\u00b8\u00bb\u0003"+
		"8\u001c\u0000\u00b9\u00ba\u0005\f\u0000\u0000\u00ba\u00bc\u0003\u000e"+
		"\u0007\u0000\u00bb\u00b9\u0001\u0000\u0000\u0000\u00bb\u00bc\u0001\u0000"+
		"\u0000\u0000\u00bc\r\u0001\u0000\u0000\u0000\u00bd\u00be\u0005\r\u0000"+
		"\u0000\u00be\u00bf\u0005\u0006\u0000\u0000\u00bf\u00c0\u0003\u000e\u0007"+
		"\u0000\u00c0\u00c4\u0005\u0007\u0000\u0000\u00c1\u00c3\u00056\u0000\u0000"+
		"\u00c2\u00c1\u0001\u0000\u0000\u0000\u00c3\u00c6\u0001\u0000\u0000\u0000"+
		"\u00c4\u00c2\u0001\u0000\u0000\u0000\u00c4\u00c5\u0001\u0000\u0000\u0000"+
		"\u00c5\u00c7\u0001\u0000\u0000\u0000\u00c6\u00c4\u0001\u0000\u0000\u0000"+
		"\u00c7\u00ca\u0003\u000e\u0007\u0000\u00c8\u00c9\u0005\u000e\u0000\u0000"+
		"\u00c9\u00cb\u0003\u000e\u0007\u0000\u00ca\u00c8\u0001\u0000\u0000\u0000"+
		"\u00ca\u00cb\u0001\u0000\u0000\u0000\u00cb\u010e\u0001\u0000\u0000\u0000"+
		"\u00cc\u00cd\u0005\u000f\u0000\u0000\u00cd\u00ce\u0005\u0006\u0000\u0000"+
		"\u00ce\u00cf\u0003\u000e\u0007\u0000\u00cf\u00d3\u0005\u0007\u0000\u0000"+
		"\u00d0\u00d2\u00056\u0000\u0000\u00d1\u00d0\u0001\u0000\u0000\u0000\u00d2"+
		"\u00d5\u0001\u0000\u0000\u0000\u00d3\u00d1\u0001\u0000\u0000\u0000\u00d3"+
		"\u00d4\u0001\u0000\u0000\u0000\u00d4\u00d6\u0001\u0000\u0000\u0000\u00d5"+
		"\u00d3\u0001\u0000\u0000\u0000\u00d6\u00d7\u0003\u000e\u0007\u0000\u00d7"+
		"\u010e\u0001\u0000\u0000\u0000\u00d8\u00d9\u0005\u0010\u0000\u0000\u00d9"+
		"\u00da\u0003\u000e\u0007\u0000\u00da\u00db\u0005\u000f\u0000\u0000\u00db"+
		"\u00dc\u0005\u0006\u0000\u0000\u00dc\u00dd\u0003\u000e\u0007\u0000\u00dd"+
		"\u00de\u0005\u0007\u0000\u0000\u00de\u010e\u0001\u0000\u0000\u0000\u00df"+
		"\u00e8\u0005\u0011\u0000\u0000\u00e0\u00e1\u0005\u0006\u0000\u0000\u00e1"+
		"\u00e2\u0003X,\u0000\u00e2\u00e3\u0005\u0007\u0000\u0000\u00e3\u00e9\u0001"+
		"\u0000\u0000\u0000\u00e4\u00e5\u0005\u0003\u0000\u0000\u00e5\u00e6\u0003"+
		"X,\u0000\u00e6\u00e7\u0005\u0004\u0000\u0000\u00e7\u00e9\u0001\u0000\u0000"+
		"\u0000\u00e8\u00e0\u0001\u0000\u0000\u0000\u00e8\u00e4\u0001\u0000\u0000"+
		"\u0000\u00e9\u00eb\u0001\u0000\u0000\u0000\u00ea\u00ec\u0005\u0012\u0000"+
		"\u0000\u00eb\u00ea\u0001\u0000\u0000\u0000\u00eb\u00ec\u0001\u0000\u0000"+
		"\u0000\u00ec\u00ed\u0001\u0000\u0000\u0000\u00ed\u00ee\u0003\u000e\u0007"+
		"\u0000\u00ee\u010e\u0001\u0000\u0000\u0000\u00ef\u00f1\u0005\u0013\u0000"+
		"\u0000\u00f0\u00f2\u0003\u000e\u0007\u0000\u00f1\u00f0\u0001\u0000\u0000"+
		"\u0000\u00f1\u00f2\u0001\u0000\u0000\u0000\u00f2\u010e\u0001\u0000\u0000"+
		"\u0000\u00f3\u00f9\u0003\u0014\n\u0000\u00f4\u00f6\u0003\u0016\u000b\u0000"+
		"\u00f5\u00f7\u0005\u0014\u0000\u0000\u00f6\u00f5\u0001\u0000\u0000\u0000"+
		"\u00f6\u00f7\u0001\u0000\u0000\u0000\u00f7\u00f9\u0001\u0000\u0000\u0000"+
		"\u00f8\u00f3\u0001\u0000\u0000\u0000\u00f8\u00f4\u0001\u0000\u0000\u0000"+
		"\u00f9\u00fa\u0001\u0000\u0000\u0000\u00fa\u00fb\u0005\u0015\u0000\u0000"+
		"\u00fb\u00fd\u0001\u0000\u0000\u0000\u00fc\u00f8\u0001\u0000\u0000\u0000"+
		"\u00fc\u00fd\u0001\u0000\u0000\u0000\u00fd\u00fe\u0001\u0000\u0000\u0000"+
		"\u00fe\u00ff\u0005!\u0000\u0000\u00ff\u0100\u0005\f\u0000\u0000\u0100"+
		"\u010e\u0003\u000e\u0007\u0000\u0101\u0102\u0003\u0010\b\u0000\u0102\u0103"+
		"\u0005\u0016\u0000\u0000\u0103\u0104\u0005\u0003\u0000\u0000\u0104\u0105"+
		"\u0003L&\u0000\u0105\u0106\u0005\u0004\u0000\u0000\u0106\u010e\u0001\u0000"+
		"\u0000\u0000\u0107\u010e\u0003\u0010\b\u0000\u0108\u0109\u0003\u0016\u000b"+
		"\u0000\u0109\u010a\u0003\u001a\r\u0000\u010a\u010b\u0005\f\u0000\u0000"+
		"\u010b\u010c\u0003\u000e\u0007\u0000\u010c\u010e\u0001\u0000\u0000\u0000"+
		"\u010d\u00bd\u0001\u0000\u0000\u0000\u010d\u00cc\u0001\u0000\u0000\u0000"+
		"\u010d\u00d8\u0001\u0000\u0000\u0000\u010d\u00df\u0001\u0000\u0000\u0000"+
		"\u010d\u00ef\u0001\u0000\u0000\u0000\u010d\u00fc\u0001\u0000\u0000\u0000"+
		"\u010d\u0101\u0001\u0000\u0000\u0000\u010d\u0107\u0001\u0000\u0000\u0000"+
		"\u010d\u0108\u0001\u0000\u0000\u0000\u010e\u000f\u0001\u0000\u0000\u0000"+
		"\u010f\u0110\u0006\b\uffff\uffff\u0000\u0110\u0111\u0003\u0012\t\u0000"+
		"\u0111\u014a\u0001\u0000\u0000\u0000\u0112\u0113\n\n\u0000\u0000\u0113"+
		"\u0115\u0005\"\u0000\u0000\u0114\u0116\u00056\u0000\u0000\u0115\u0114"+
		"\u0001\u0000\u0000\u0000\u0115\u0116\u0001\u0000\u0000\u0000\u0116\u0117"+
		"\u0001\u0000\u0000\u0000\u0117\u0149\u0003\u0010\b\u000b\u0118\u0119\n"+
		"\t\u0000\u0000\u0119\u011b\u0005#\u0000\u0000\u011a\u011c\u00056\u0000"+
		"\u0000\u011b\u011a\u0001\u0000\u0000\u0000\u011b\u011c\u0001\u0000\u0000"+
		"\u0000\u011c\u011d\u0001\u0000\u0000\u0000\u011d\u0149\u0003\u0010\b\n"+
		"\u011e\u011f\n\b\u0000\u0000\u011f\u0121\u0005$\u0000\u0000\u0120\u0122"+
		"\u00056\u0000\u0000\u0121\u0120\u0001\u0000\u0000\u0000\u0121\u0122\u0001"+
		"\u0000\u0000\u0000\u0122\u0123\u0001\u0000\u0000\u0000\u0123\u0149\u0003"+
		"\u0010\b\t\u0124\u0125\n\u0007\u0000\u0000\u0125\u0127\u0005%\u0000\u0000"+
		"\u0126\u0128\u00056\u0000\u0000\u0127\u0126\u0001\u0000\u0000\u0000\u0127"+
		"\u0128\u0001\u0000\u0000\u0000\u0128\u0129\u0001\u0000\u0000\u0000\u0129"+
		"\u0149\u0003\u0010\b\b\u012a\u012b\n\u0006\u0000\u0000\u012b\u012d\u0005"+
		"&\u0000\u0000\u012c\u012e\u00056\u0000\u0000\u012d\u012c\u0001\u0000\u0000"+
		"\u0000\u012d\u012e\u0001\u0000\u0000\u0000\u012e\u012f\u0001\u0000\u0000"+
		"\u0000\u012f\u0149\u0003\u0010\b\u0007\u0130\u0131\n\u0005\u0000\u0000"+
		"\u0131\u0133\u0005\'\u0000\u0000\u0132\u0134\u00056\u0000\u0000\u0133"+
		"\u0132\u0001\u0000\u0000\u0000\u0133\u0134\u0001\u0000\u0000\u0000\u0134"+
		"\u0135\u0001\u0000\u0000\u0000\u0135\u0149\u0003\u0010\b\u0006\u0136\u0137"+
		"\n\u0004\u0000\u0000\u0137\u0139\u0005(\u0000\u0000\u0138\u013a\u0005"+
		"6\u0000\u0000\u0139\u0138\u0001\u0000\u0000\u0000\u0139\u013a\u0001\u0000"+
		"\u0000\u0000\u013a\u013b\u0001\u0000\u0000\u0000\u013b\u0149\u0003\u0010"+
		"\b\u0005\u013c\u013d\n\u0003\u0000\u0000\u013d\u013f\u0005)\u0000\u0000"+
		"\u013e\u0140\u00056\u0000\u0000\u013f\u013e\u0001\u0000\u0000\u0000\u013f"+
		"\u0140\u0001\u0000\u0000\u0000\u0140\u0141\u0001\u0000\u0000\u0000\u0141"+
		"\u0149\u0003\u0010\b\u0004\u0142\u0143\n\u0002\u0000\u0000\u0143\u0145"+
		"\u0005*\u0000\u0000\u0144\u0146\u00056\u0000\u0000\u0145\u0144\u0001\u0000"+
		"\u0000\u0000\u0145\u0146\u0001\u0000\u0000\u0000\u0146\u0147\u0001\u0000"+
		"\u0000\u0000\u0147\u0149\u0003\u0010\b\u0003\u0148\u0112\u0001\u0000\u0000"+
		"\u0000\u0148\u0118\u0001\u0000\u0000\u0000\u0148\u011e\u0001\u0000\u0000"+
		"\u0000\u0148\u0124\u0001\u0000\u0000\u0000\u0148\u012a\u0001\u0000\u0000"+
		"\u0000\u0148\u0130\u0001\u0000\u0000\u0000\u0148\u0136\u0001\u0000\u0000"+
		"\u0000\u0148\u013c\u0001\u0000\u0000\u0000\u0148\u0142\u0001\u0000\u0000"+
		"\u0000\u0149\u014c\u0001\u0000\u0000\u0000\u014a\u0148\u0001\u0000\u0000"+
		"\u0000\u014a\u014b\u0001\u0000\u0000\u0000\u014b\u0011\u0001\u0000\u0000"+
		"\u0000\u014c\u014a\u0001\u0000\u0000\u0000\u014d\u014f\u0003J%\u0000\u014e"+
		"\u014d\u0001\u0000\u0000\u0000\u014e\u014f\u0001\u0000\u0000\u0000\u014f"+
		"\u0155\u0001\u0000\u0000\u0000\u0150\u0156\u0003\u0014\n\u0000\u0151\u0153"+
		"\u0003\u0016\u000b\u0000\u0152\u0154\u0005\u0014\u0000\u0000\u0153\u0152"+
		"\u0001\u0000\u0000\u0000\u0153\u0154\u0001\u0000\u0000\u0000\u0154\u0156"+
		"\u0001\u0000\u0000\u0000\u0155\u0150\u0001\u0000\u0000\u0000\u0155\u0151"+
		"\u0001\u0000\u0000\u0000\u0156\u0013\u0001\u0000\u0000\u0000\u0157\u0158"+
		"\u0005\u0017\u0000\u0000\u0158\u015c\u0005!\u0000\u0000\u0159\u015b\u0003"+
		"\u001a\r\u0000\u015a\u0159\u0001\u0000\u0000\u0000\u015b\u015e\u0001\u0000"+
		"\u0000\u0000\u015c\u015a\u0001\u0000\u0000\u0000\u015c\u015d\u0001\u0000"+
		"\u0000\u0000\u015d\u0015\u0001\u0000\u0000\u0000\u015e\u015c\u0001\u0000"+
		"\u0000\u0000\u015f\u0160\u0006\u000b\uffff\uffff\u0000\u0160\u016c\u0003"+
		"D\"\u0000\u0161\u016c\u0003H$\u0000\u0162\u016c\u0005\u0014\u0000\u0000"+
		"\u0163\u0164\u0005\u0006\u0000\u0000\u0164\u0165\u0003\u000e\u0007\u0000"+
		"\u0165\u0166\u0005\u0007\u0000\u0000\u0166\u016c\u0001\u0000\u0000\u0000"+
		"\u0167\u0168\u0003\u0014\n\u0000\u0168\u0169\u0005\u0015\u0000\u0000\u0169"+
		"\u016a\u0005!\u0000\u0000\u016a\u016c\u0001\u0000\u0000\u0000\u016b\u015f"+
		"\u0001\u0000\u0000\u0000\u016b\u0161\u0001\u0000\u0000\u0000\u016b\u0162"+
		"\u0001\u0000\u0000\u0000\u016b\u0163\u0001\u0000\u0000\u0000\u016b\u0167"+
		"\u0001\u0000\u0000\u0000\u016c\u0177\u0001\u0000\u0000\u0000\u016d\u016f"+
		"\n\u0002\u0000\u0000\u016e\u0170\u0005\u0014\u0000\u0000\u016f\u016e\u0001"+
		"\u0000\u0000\u0000\u016f\u0170\u0001\u0000\u0000\u0000\u0170\u0171\u0001"+
		"\u0000\u0000\u0000\u0171\u0172\u0005\u0015\u0000\u0000\u0172\u0176\u0005"+
		"!\u0000\u0000\u0173\u0174\n\u0001\u0000\u0000\u0174\u0176\u0003\u001a"+
		"\r\u0000\u0175\u016d\u0001\u0000\u0000\u0000\u0175\u0173\u0001\u0000\u0000"+
		"\u0000\u0176\u0179\u0001\u0000\u0000\u0000\u0177\u0175\u0001\u0000\u0000"+
		"\u0000\u0177\u0178\u0001\u0000\u0000\u0000\u0178\u0017\u0001\u0000\u0000"+
		"\u0000\u0179\u0177\u0001\u0000\u0000\u0000\u017a\u017f\u0003\u000e\u0007"+
		"\u0000\u017b\u017c\u0005\b\u0000\u0000\u017c\u017e\u0003\u000e\u0007\u0000"+
		"\u017d\u017b\u0001\u0000\u0000\u0000\u017e\u0181\u0001\u0000\u0000\u0000"+
		"\u017f\u017d\u0001\u0000\u0000\u0000\u017f\u0180\u0001\u0000\u0000\u0000"+
		"\u0180\u0019\u0001\u0000\u0000\u0000\u0181\u017f\u0001\u0000\u0000\u0000"+
		"\u0182\u0183\u0005\u0006\u0000\u0000\u0183\u0184\u0003\u001c\u000e\u0000"+
		"\u0184\u0185\u0005\u0007\u0000\u0000\u0185\u018b\u0001\u0000\u0000\u0000"+
		"\u0186\u0188\u00056\u0000\u0000\u0187\u0186\u0001\u0000\u0000\u0000\u0187"+
		"\u0188\u0001\u0000\u0000\u0000\u0188\u0189\u0001\u0000\u0000\u0000\u0189"+
		"\u018b\u0003\u001e\u000f\u0000\u018a\u0182\u0001\u0000\u0000\u0000\u018a"+
		"\u0187\u0001\u0000\u0000\u0000\u018b\u001b\u0001\u0000\u0000\u0000\u018c"+
		"\u018e\u0003\u0018\f\u0000\u018d\u018c\u0001\u0000\u0000\u0000\u018d\u018e"+
		"\u0001\u0000\u0000\u0000\u018e\u0199\u0001\u0000\u0000\u0000\u018f\u0190"+
		"\u0003\u0018\f\u0000\u0190\u0191\u0005\b\u0000\u0000\u0191\u0193\u0001"+
		"\u0000\u0000\u0000\u0192\u018f\u0001\u0000\u0000\u0000\u0192\u0193\u0001"+
		"\u0000\u0000\u0000\u0193\u0194\u0001\u0000\u0000\u0000\u0194\u0196\u0003"+
		"\u0010\b\u0000\u0195\u0197\u0007\u0003\u0000\u0000\u0196\u0195\u0001\u0000"+
		"\u0000\u0000\u0196\u0197\u0001\u0000\u0000\u0000\u0197\u0199\u0001\u0000"+
		"\u0000\u0000\u0198\u018d\u0001\u0000\u0000\u0000\u0198\u0192\u0001\u0000"+
		"\u0000\u0000\u0199\u001d\u0001\u0000\u0000\u0000\u019a\u019b\u0005\u0003"+
		"\u0000\u0000\u019b\u019c\u0003L&\u0000\u019c\u019d\u0005\u0004\u0000\u0000"+
		"\u019d\u01a3\u0001\u0000\u0000\u0000\u019e\u019f\u0005\u0003\u0000\u0000"+
		"\u019f\u01a0\u0003 \u0010\u0000\u01a0\u01a1\u0005\u0004\u0000\u0000\u01a1"+
		"\u01a3\u0001\u0000\u0000\u0000\u01a2\u019a\u0001\u0000\u0000\u0000\u01a2"+
		"\u019e\u0001\u0000\u0000\u0000\u01a3\u001f\u0001\u0000\u0000\u0000\u01a4"+
		"\u01a6\u0003\"\u0011\u0000\u01a5\u01a4\u0001\u0000\u0000\u0000\u01a6\u01a7"+
		"\u0001\u0000\u0000\u0000\u01a7\u01a5\u0001\u0000\u0000\u0000\u01a7\u01a8"+
		"\u0001\u0000\u0000\u0000\u01a8\u01aa\u0001\u0000\u0000\u0000\u01a9\u01ab"+
		"\u0003$\u0012\u0000\u01aa\u01a9\u0001\u0000\u0000\u0000\u01aa\u01ab\u0001"+
		"\u0000\u0000\u0000\u01ab!\u0001\u0000\u0000\u0000\u01ac\u01b0\u0003&\u0013"+
		"\u0000\u01ad\u01b0\u0003\u0002\u0001\u0000\u01ae\u01b0\u0003\u000e\u0007"+
		"\u0000\u01af\u01ac\u0001\u0000\u0000\u0000\u01af\u01ad\u0001\u0000\u0000"+
		"\u0000\u01af\u01ae\u0001\u0000\u0000\u0000\u01b0#\u0001\u0000\u0000\u0000"+
		"\u01b1\u01b2\u0003\u000e\u0007\u0000\u01b2%\u0001\u0000\u0000\u0000\u01b3"+
		"\u01c0\u0003(\u0014\u0000\u01b4\u01b5\u0005\u0018\u0000\u0000\u01b5\u01c0"+
		"\u0003,\u0016\u0000\u01b6\u01ba\u0005\u0019\u0000\u0000\u01b7\u01b9\u0005"+
		"6\u0000\u0000\u01b8\u01b7\u0001\u0000\u0000\u0000\u01b9\u01bc\u0001\u0000"+
		"\u0000\u0000\u01ba\u01b8\u0001\u0000\u0000\u0000\u01ba\u01bb\u0001\u0000"+
		"\u0000\u0000\u01bb\u01bd\u0001\u0000\u0000\u0000\u01bc\u01ba\u0001\u0000"+
		"\u0000\u0000\u01bd\u01c0\u0003:\u001d\u0000\u01be\u01c0\u0003\u0002\u0001"+
		"\u0000\u01bf\u01b3\u0001\u0000\u0000\u0000\u01bf\u01b4\u0001\u0000\u0000"+
		"\u0000\u01bf\u01b6\u0001\u0000\u0000\u0000\u01bf\u01be\u0001\u0000\u0000"+
		"\u0000\u01c0\'\u0001\u0000\u0000\u0000\u01c1\u01c2\u0005\t\u0000\u0000"+
		"\u01c2\u01c6\u0003*\u0015\u0000\u01c3\u01c4\u0005\n\u0000\u0000\u01c4"+
		"\u01c6\u0003*\u0015\u0000\u01c5\u01c1\u0001\u0000\u0000\u0000\u01c5\u01c3"+
		"\u0001\u0000\u0000\u0000\u01c6)\u0001\u0000\u0000\u0000\u01c7\u01ca\u0003"+
		"R)\u0000\u01c8\u01c9\u0005\u000b\u0000\u0000\u01c9\u01cb\u0003<\u001e"+
		"\u0000\u01ca\u01c8\u0001\u0000\u0000\u0000\u01ca\u01cb\u0001\u0000\u0000"+
		"\u0000\u01cb\u01cc\u0001\u0000\u0000\u0000\u01cc\u01cd\u0005\f\u0000\u0000"+
		"\u01cd\u01ce\u0003\u000e\u0007\u0000\u01ce+\u0001\u0000\u0000\u0000\u01cf"+
		"\u01d2\u0003.\u0017\u0000\u01d0\u01d1\u0005\u000b\u0000\u0000\u01d1\u01d3"+
		"\u0003<\u001e\u0000\u01d2\u01d0\u0001\u0000\u0000\u0000\u01d2\u01d3\u0001"+
		"\u0000\u0000\u0000\u01d3\u01d4\u0001\u0000\u0000\u0000\u01d4\u01d5\u0005"+
		"\f\u0000\u0000\u01d5\u01d6\u0003\u000e\u0007\u0000\u01d6\u01e0\u0001\u0000"+
		"\u0000\u0000\u01d7\u01d9\u0003.\u0017\u0000\u01d8\u01da\u00056\u0000\u0000"+
		"\u01d9\u01d8\u0001\u0000\u0000\u0000\u01d9\u01da\u0001\u0000\u0000\u0000"+
		"\u01da\u01db\u0001\u0000\u0000\u0000\u01db\u01dc\u0005\u0003\u0000\u0000"+
		"\u01dc\u01dd\u0003 \u0010\u0000\u01dd\u01de\u0005\u0004\u0000\u0000\u01de"+
		"\u01e0\u0001\u0000\u0000\u0000\u01df\u01cf\u0001\u0000\u0000\u0000\u01df"+
		"\u01d7\u0001\u0000\u0000\u0000\u01e0-\u0001\u0000\u0000\u0000\u01e1\u01e2"+
		"\u0005!\u0000\u0000\u01e2\u01e3\u00030\u0018\u0000\u01e3/\u0001\u0000"+
		"\u0000\u0000\u01e4\u01e6\u00032\u0019\u0000\u01e5\u01e4\u0001\u0000\u0000"+
		"\u0000\u01e6\u01e9\u0001\u0000\u0000\u0000\u01e7\u01e5\u0001\u0000\u0000"+
		"\u0000\u01e7\u01e8\u0001\u0000\u0000\u0000\u01e81\u0001\u0000\u0000\u0000"+
		"\u01e9\u01e7\u0001\u0000\u0000\u0000\u01ea\u01ec\u00056\u0000\u0000\u01eb"+
		"\u01ea\u0001\u0000\u0000\u0000\u01eb\u01ec\u0001\u0000\u0000\u0000\u01ec"+
		"\u01ed\u0001\u0000\u0000\u0000\u01ed\u01ef\u0005\u0006\u0000\u0000\u01ee"+
		"\u01f0\u00034\u001a\u0000\u01ef\u01ee\u0001\u0000\u0000\u0000\u01ef\u01f0"+
		"\u0001\u0000\u0000\u0000\u01f0\u01f1\u0001\u0000\u0000\u0000\u01f1\u01f2"+
		"\u0005\u0007\u0000\u0000\u01f23\u0001\u0000\u0000\u0000\u01f3\u01f8\u0003"+
		"6\u001b\u0000\u01f4\u01f5\u0005\b\u0000\u0000\u01f5\u01f7\u00036\u001b"+
		"\u0000\u01f6\u01f4\u0001\u0000\u0000\u0000\u01f7\u01fa\u0001\u0000\u0000"+
		"\u0000\u01f8\u01f6\u0001\u0000\u0000\u0000\u01f8\u01f9\u0001\u0000\u0000"+
		"\u0000\u01f95\u0001\u0000\u0000\u0000\u01fa\u01f8\u0001\u0000\u0000\u0000"+
		"\u01fb\u01fe\u0005!\u0000\u0000\u01fc\u01fd\u0005\u000b\u0000\u0000\u01fd"+
		"\u01ff\u00038\u001c\u0000\u01fe\u01fc\u0001\u0000\u0000\u0000\u01fe\u01ff"+
		"\u0001\u0000\u0000\u0000\u01ff\u0202\u0001\u0000\u0000\u0000\u0200\u0201"+
		"\u0005\f\u0000\u0000\u0201\u0203\u0003\u000e\u0007\u0000\u0202\u0200\u0001"+
		"\u0000\u0000\u0000\u0202\u0203\u0001\u0000\u0000\u0000\u02037\u0001\u0000"+
		"\u0000\u0000\u0204\u0208\u0003<\u001e\u0000\u0205\u0206\u0005\u001a\u0000"+
		"\u0000\u0206\u0208\u0003<\u001e\u0000\u0207\u0204\u0001\u0000\u0000\u0000"+
		"\u0207\u0205\u0001\u0000\u0000\u0000\u02089\u0001\u0000\u0000\u0000\u0209"+
		"\u020a\u0005!\u0000\u0000\u020a\u020b\u0005\f\u0000\u0000\u020b\u020c"+
		"\u0003<\u001e\u0000\u020c;\u0001\u0000\u0000\u0000\u020d\u020e\u0003>"+
		"\u001f\u0000\u020e\u020f\u0005\u001a\u0000\u0000\u020f\u0210\u0003<\u001e"+
		"\u0000\u0210\u0213\u0001\u0000\u0000\u0000\u0211\u0213\u0003@ \u0000\u0212"+
		"\u020d\u0001\u0000\u0000\u0000\u0212\u0211\u0001\u0000\u0000\u0000\u0213"+
		"=\u0001\u0000\u0000\u0000\u0214\u0222\u0003@ \u0000\u0215\u021e\u0005"+
		"\u0006\u0000\u0000\u0216\u021b\u00038\u001c\u0000\u0217\u0218\u0005\b"+
		"\u0000\u0000\u0218\u021a\u00038\u001c\u0000\u0219\u0217\u0001\u0000\u0000"+
		"\u0000\u021a\u021d\u0001\u0000\u0000\u0000\u021b\u0219\u0001\u0000\u0000"+
		"\u0000\u021b\u021c\u0001\u0000\u0000\u0000\u021c\u021f\u0001\u0000\u0000"+
		"\u0000\u021d\u021b\u0001\u0000\u0000\u0000\u021e\u0216\u0001\u0000\u0000"+
		"\u0000\u021e\u021f\u0001\u0000\u0000\u0000\u021f\u0220\u0001\u0000\u0000"+
		"\u0000\u0220\u0222\u0005\u0007\u0000\u0000\u0221\u0214\u0001\u0000\u0000"+
		"\u0000\u0221\u0215\u0001\u0000\u0000\u0000\u0222?\u0001\u0000\u0000\u0000"+
		"\u0223\u0226\u0003H$\u0000\u0224\u0225\u0005\u0015\u0000\u0000\u0225\u0227"+
		"\u0005\u0019\u0000\u0000\u0226\u0224\u0001\u0000\u0000\u0000\u0226\u0227"+
		"\u0001\u0000\u0000\u0000\u0227\u022d\u0001\u0000\u0000\u0000\u0228\u0229"+
		"\u0005\u0006\u0000\u0000\u0229\u022a\u0003B!\u0000\u022a\u022b\u0005\u0007"+
		"\u0000\u0000\u022b\u022d\u0001\u0000\u0000\u0000\u022c\u0223\u0001\u0000"+
		"\u0000\u0000\u022c\u0228\u0001\u0000\u0000\u0000\u022dA\u0001\u0000\u0000"+
		"\u0000\u022e\u0233\u0003<\u001e\u0000\u022f\u0230\u0005\b\u0000\u0000"+
		"\u0230\u0232\u0003<\u001e\u0000\u0231\u022f\u0001\u0000\u0000\u0000\u0232"+
		"\u0235\u0001\u0000\u0000\u0000\u0233\u0231\u0001\u0000\u0000\u0000\u0233"+
		"\u0234\u0001\u0000\u0000\u0000\u0234C\u0001\u0000\u0000\u0000\u0235\u0233"+
		"\u0001\u0000\u0000\u0000\u0236\u0238\u00055\u0000\u0000\u0237\u0236\u0001"+
		"\u0000\u0000\u0000\u0237\u0238\u0001\u0000\u0000\u0000\u0238\u0239\u0001"+
		"\u0000\u0000\u0000\u0239\u0244\u0005.\u0000\u0000\u023a\u023c\u00055\u0000"+
		"\u0000\u023b\u023a\u0001\u0000\u0000\u0000\u023b\u023c\u0001\u0000\u0000"+
		"\u0000\u023c\u023d\u0001\u0000\u0000\u0000\u023d\u0244\u00050\u0000\u0000"+
		"\u023e\u0244\u0005+\u0000\u0000\u023f\u0244\u0005,\u0000\u0000\u0240\u0244"+
		"\u0005/\u0000\u0000\u0241\u0244\u0005-\u0000\u0000\u0242\u0244\u0005\u001b"+
		"\u0000\u0000\u0243\u0237\u0001\u0000\u0000\u0000\u0243\u023b\u0001\u0000"+
		"\u0000\u0000\u0243\u023e\u0001\u0000\u0000\u0000\u0243\u023f\u0001\u0000"+
		"\u0000\u0000\u0243\u0240\u0001\u0000\u0000\u0000\u0243\u0241\u0001\u0000"+
		"\u0000\u0000\u0243\u0242\u0001\u0000\u0000\u0000\u0244E\u0001\u0000\u0000"+
		"\u0000\u0245\u024a\u0005!\u0000\u0000\u0246\u0247\u0005\b\u0000\u0000"+
		"\u0247\u0249\u0005!\u0000\u0000\u0248\u0246\u0001\u0000\u0000\u0000\u0249"+
		"\u024c\u0001\u0000\u0000\u0000\u024a\u0248\u0001\u0000\u0000\u0000\u024a"+
		"\u024b\u0001\u0000\u0000\u0000\u024bG\u0001\u0000\u0000\u0000\u024c\u024a"+
		"\u0001\u0000\u0000\u0000\u024d\u024e\u0006$\uffff\uffff\u0000\u024e\u025a"+
		"\u0005!\u0000\u0000\u024f\u0250\u0005!\u0000\u0000\u0250\u0252\u0005\u0015"+
		"\u0000\u0000\u0251\u024f\u0001\u0000\u0000\u0000\u0251\u0252\u0001\u0000"+
		"\u0000\u0000\u0252\u0257\u0001\u0000\u0000\u0000\u0253\u0258\u0005\u001c"+
		"\u0000\u0000\u0254\u0255\u0005\u001d\u0000\u0000\u0255\u0256\u0005\u0015"+
		"\u0000\u0000\u0256\u0258\u0005!\u0000\u0000\u0257\u0253\u0001\u0000\u0000"+
		"\u0000\u0257\u0254\u0001\u0000\u0000\u0000\u0258\u025a\u0001\u0000\u0000"+
		"\u0000\u0259\u024d\u0001\u0000\u0000\u0000\u0259\u0251\u0001\u0000\u0000"+
		"\u0000\u025a\u0260\u0001\u0000\u0000\u0000\u025b\u025c\n\u0002\u0000\u0000"+
		"\u025c\u025d\u0005\u0015\u0000\u0000\u025d\u025f\u0005!\u0000\u0000\u025e"+
		"\u025b\u0001\u0000\u0000\u0000\u025f\u0262\u0001\u0000\u0000\u0000\u0260"+
		"\u025e\u0001\u0000\u0000\u0000\u0260\u0261\u0001\u0000\u0000\u0000\u0261"+
		"I\u0001\u0000\u0000\u0000\u0262\u0260\u0001\u0000\u0000\u0000\u0263\u0264"+
		"\u0007\u0004\u0000\u0000\u0264K\u0001\u0000\u0000\u0000\u0265\u0267\u0003"+
		"N\'\u0000\u0266\u0265\u0001\u0000\u0000\u0000\u0267\u0268\u0001\u0000"+
		"\u0000\u0000\u0268\u0266\u0001\u0000\u0000\u0000\u0268\u0269\u0001\u0000"+
		"\u0000\u0000\u0269M\u0001\u0000\u0000\u0000\u026a\u026b\u0005\u001e\u0000"+
		"\u0000\u026b\u026c\u0003P(\u0000\u026c\u026d\u0005\u001a\u0000\u0000\u026d"+
		"\u026e\u0003 \u0010\u0000\u026eO\u0001\u0000\u0000\u0000\u026f\u0274\u0003"+
		"R)\u0000\u0270\u0271\u0005\u001f\u0000\u0000\u0271\u0273\u0003R)\u0000"+
		"\u0272\u0270\u0001\u0000\u0000\u0000\u0273\u0276\u0001\u0000\u0000\u0000"+
		"\u0274\u0272\u0001\u0000\u0000\u0000\u0274\u0275\u0001\u0000\u0000\u0000"+
		"\u0275Q\u0001\u0000\u0000\u0000\u0276\u0274\u0001\u0000\u0000\u0000\u0277"+
		"\u0278\u0007\u0005\u0000\u0000\u0278\u0279\u0005\u000b\u0000\u0000\u0279"+
		"\u027c\u0003<\u001e\u0000\u027a\u027c\u0003T*\u0000\u027b\u0277\u0001"+
		"\u0000\u0000\u0000\u027b\u027a\u0001\u0000\u0000\u0000\u027cS\u0001\u0000"+
		"\u0000\u0000\u027d\u028e\u0005\u0014\u0000\u0000\u027e\u028e\u00051\u0000"+
		"\u0000\u027f\u028e\u0003D\"\u0000\u0280\u0286\u0003H$\u0000\u0281\u0283"+
		"\u0005\u0006\u0000\u0000\u0282\u0284\u0003V+\u0000\u0283\u0282\u0001\u0000"+
		"\u0000\u0000\u0283\u0284\u0001\u0000\u0000\u0000\u0284\u0285\u0001\u0000"+
		"\u0000\u0000\u0285\u0287\u0005\u0007\u0000\u0000\u0286\u0281\u0001\u0000"+
		"\u0000\u0000\u0286\u0287\u0001\u0000\u0000\u0000\u0287\u028e\u0001\u0000"+
		"\u0000\u0000\u0288\u028a\u0005\u0006\u0000\u0000\u0289\u028b\u0003V+\u0000"+
		"\u028a\u0289\u0001\u0000\u0000\u0000\u028a\u028b\u0001\u0000\u0000\u0000"+
		"\u028b\u028c\u0001\u0000\u0000\u0000\u028c\u028e\u0005\u0007\u0000\u0000"+
		"\u028d\u027d\u0001\u0000\u0000\u0000\u028d\u027e\u0001\u0000\u0000\u0000"+
		"\u028d\u027f\u0001\u0000\u0000\u0000\u028d\u0280\u0001\u0000\u0000\u0000"+
		"\u028d\u0288\u0001\u0000\u0000\u0000\u028eU\u0001\u0000\u0000\u0000\u028f"+
		"\u0292\u0003P(\u0000\u0290\u0291\u0005\b\u0000\u0000\u0291\u0293\u0003"+
		"V+\u0000\u0292\u0290\u0001\u0000\u0000\u0000\u0292\u0293\u0001\u0000\u0000"+
		"\u0000\u0293\u0296\u0001\u0000\u0000\u0000\u0294\u0296\u0005\u0014\u0000"+
		"\u0000\u0295\u028f\u0001\u0000\u0000\u0000\u0295\u0294\u0001\u0000\u0000"+
		"\u0000\u0296W\u0001\u0000\u0000\u0000\u0297\u0299\u0003Z-\u0000\u0298"+
		"\u0297\u0001\u0000\u0000\u0000\u0299\u029a\u0001\u0000\u0000\u0000\u029a"+
		"\u0298\u0001\u0000\u0000\u0000\u029a\u029b\u0001\u0000\u0000\u0000\u029b"+
		"Y\u0001\u0000\u0000\u0000\u029c\u029d\u0003R)\u0000\u029d\u029e\u0005"+
		" \u0000\u0000\u029e\u02a5\u0003\u000e\u0007\u0000\u029f\u02a0\u0003R)"+
		"\u0000\u02a0\u02a1\u0005\f\u0000\u0000\u02a1\u02a2\u0003\u000e\u0007\u0000"+
		"\u02a2\u02a4\u0001\u0000\u0000\u0000\u02a3\u029f\u0001\u0000\u0000\u0000"+
		"\u02a4\u02a7\u0001\u0000\u0000\u0000\u02a5\u02a3\u0001\u0000\u0000\u0000"+
		"\u02a5\u02a6\u0001\u0000\u0000\u0000\u02a6[\u0001\u0000\u0000\u0000\u02a7"+
		"\u02a5\u0001\u0000\u0000\u0000b_fjoqv}\u0085\u008c\u0090\u0097\u009c\u00a0"+
		"\u00a3\u00a7\u00b0\u00b4\u00bb\u00c4\u00ca\u00d3\u00e8\u00eb\u00f1\u00f6"+
		"\u00f8\u00fc\u010d\u0115\u011b\u0121\u0127\u012d\u0133\u0139\u013f\u0145"+
		"\u0148\u014a\u014e\u0153\u0155\u015c\u016b\u016f\u0175\u0177\u017f\u0187"+
		"\u018a\u018d\u0192\u0196\u0198\u01a2\u01a7\u01aa\u01af\u01ba\u01bf\u01c5"+
		"\u01ca\u01d2\u01d9\u01df\u01e7\u01eb\u01ef\u01f8\u01fe\u0202\u0207\u0212"+
		"\u021b\u021e\u0221\u0226\u022c\u0233\u0237\u023b\u0243\u024a\u0251\u0257"+
		"\u0259\u0260\u0268\u0274\u027b\u0283\u0286\u028a\u028d\u0292\u0295\u029a"+
		"\u02a5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}