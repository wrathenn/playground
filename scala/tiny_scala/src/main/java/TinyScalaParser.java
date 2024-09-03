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
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, Id=30, OpPrecedence1=31, 
		OpPrecedence2=32, OpPrecedence3=33, OpPrecedence4=34, OpPrecedence5=35, 
		OpPrecedence6=36, OpPrecedence7=37, OpPrecedence8=38, OpPrecedence9=39, 
		BooleanLiteral=40, CharacterLiteral=41, IntegerLiteral=42, StringLiteral=43, 
		FloatingPointLiteral=44, Varid=45, BoundVarid=46, Paren=47, Delim=48, 
		Minus=49, Colon=50, NL=51, NEWLINE=52, WS=53, COMMENT=54, LINE_COMMENT=55;
	public static final int
		RULE_compilationUnit = 0, RULE_tmplDef = 1, RULE_tmplDefCaseClass = 2, 
		RULE_tmplDefObject = 3, RULE_objectIsMain = 4, RULE_templateBody = 5, 
		RULE_templateStat = 6, RULE_classParamClause = 7, RULE_classParams = 8, 
		RULE_classParam = 9, RULE_expr = 10, RULE_infixExpr = 11, RULE_prefixExpr = 12, 
		RULE_newClassExpr = 13, RULE_simpleExpr1 = 14, RULE_argumentExprs = 15, 
		RULE_exprs = 16, RULE_def_ = 17, RULE_patVarDef = 18, RULE_patDef = 19, 
		RULE_funDef = 20, RULE_block = 21, RULE_funSig = 22, RULE_params = 23, 
		RULE_param = 24, RULE_type_ = 25, RULE_simpleType = 26, RULE_arrayType = 27, 
		RULE_types = 28, RULE_literal = 29, RULE_ids = 30, RULE_stableId = 31, 
		RULE_opNoPrecedence = 32, RULE_pattern = 33, RULE_pattern1 = 34, RULE_patterns = 35, 
		RULE_enumerators = 36, RULE_generator = 37;
	private static String[] makeRuleNames() {
		return new String[] {
			"compilationUnit", "tmplDef", "tmplDefCaseClass", "tmplDefObject", "objectIsMain", 
			"templateBody", "templateStat", "classParamClause", "classParams", "classParam", 
			"expr", "infixExpr", "prefixExpr", "newClassExpr", "simpleExpr1", "argumentExprs", 
			"exprs", "def_", "patVarDef", "patDef", "funDef", "block", "funSig", 
			"params", "param", "type_", "simpleType", "arrayType", "types", "literal", 
			"ids", "stableId", "opNoPrecedence", "pattern", "pattern1", "patterns", 
			"enumerators", "generator"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'case class'", "'object'", "'extends'", "'App'", "'{'", "'}'", 
			"';'", "'('", "')'", "','", "'if'", "'else'", "'while'", "'do'", "'for'", 
			"'yield'", "'return'", "'='", "'new'", "'def'", "'val'", "'var'", "'Array['", 
			"']'", "'null'", "'.'", "'|'", "'_'", "'<-'", null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, "'-'", "':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, "Id", "OpPrecedence1", "OpPrecedence2", 
			"OpPrecedence3", "OpPrecedence4", "OpPrecedence5", "OpPrecedence6", "OpPrecedence7", 
			"OpPrecedence8", "OpPrecedence9", "BooleanLiteral", "CharacterLiteral", 
			"IntegerLiteral", "StringLiteral", "FloatingPointLiteral", "Varid", "BoundVarid", 
			"Paren", "Delim", "Minus", "Colon", "NL", "NEWLINE", "WS", "COMMENT", 
			"LINE_COMMENT"
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
		public List<TerminalNode> NL() { return getTokens(TinyScalaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TinyScalaParser.NL, i);
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
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(89); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(79);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(76);
					match(NL);
					}
					}
					setState(81);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(82);
				tmplDef();
				setState(86);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(83);
						match(NL);
						}
						} 
					}
					setState(88);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
				}
				}
				}
				setState(91); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 2251799813685254L) != 0) );
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
		public TmplDefCaseClassContext tmplDefCaseClass() {
			return getRuleContext(TmplDefCaseClassContext.class,0);
		}
		public TmplDefObjectContext tmplDefObject() {
			return getRuleContext(TmplDefObjectContext.class,0);
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
			setState(95);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(93);
				tmplDefCaseClass();
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
				setState(94);
				tmplDefObject();
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
	public static class TmplDefCaseClassContext extends ParserRuleContext {
		public TerminalNode Id() { return getToken(TinyScalaParser.Id, 0); }
		public ClassParamClauseContext classParamClause() {
			return getRuleContext(ClassParamClauseContext.class,0);
		}
		public TmplDefCaseClassContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tmplDefCaseClass; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterTmplDefCaseClass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitTmplDefCaseClass(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitTmplDefCaseClass(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TmplDefCaseClassContext tmplDefCaseClass() throws RecognitionException {
		TmplDefCaseClassContext _localctx = new TmplDefCaseClassContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_tmplDefCaseClass);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(T__0);
			setState(98);
			match(Id);
			setState(99);
			classParamClause();
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
	public static class TmplDefObjectContext extends ParserRuleContext {
		public TerminalNode Id() { return getToken(TinyScalaParser.Id, 0); }
		public TemplateBodyContext templateBody() {
			return getRuleContext(TemplateBodyContext.class,0);
		}
		public ObjectIsMainContext objectIsMain() {
			return getRuleContext(ObjectIsMainContext.class,0);
		}
		public TmplDefObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tmplDefObject; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterTmplDefObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitTmplDefObject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitTmplDefObject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TmplDefObjectContext tmplDefObject() throws RecognitionException {
		TmplDefObjectContext _localctx = new TmplDefObjectContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_tmplDefObject);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			match(T__1);
			setState(102);
			match(Id);
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(103);
				objectIsMain();
				}
			}

			setState(106);
			templateBody();
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
	public static class ObjectIsMainContext extends ParserRuleContext {
		public ObjectIsMainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_objectIsMain; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterObjectIsMain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitObjectIsMain(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitObjectIsMain(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjectIsMainContext objectIsMain() throws RecognitionException {
		ObjectIsMainContext _localctx = new ObjectIsMainContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_objectIsMain);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			match(T__2);
			setState(109);
			match(T__3);
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
		enterRule(_localctx, 10, RULE_templateBody);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(111);
				match(NL);
				}
				}
				setState(116);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(152);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(117);
				match(T__4);
				setState(121);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL || _la==WS) {
					{
					{
					setState(118);
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
					setState(123);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(124);
				match(T__5);
				}
				break;
			case 2:
				{
				setState(125);
				match(T__4);
				setState(129);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL || _la==WS) {
					{
					{
					setState(126);
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
					setState(131);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(140);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(132);
						templateStat();
						setState(134); 
						_errHandler.sync(this);
						_la = _input.LA(1);
						do {
							{
							{
							setState(133);
							_la = _input.LA(1);
							if ( !(_la==T__6 || _la==NL) ) {
							_errHandler.recoverInline(this);
							}
							else {
								if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
								_errHandler.reportMatch(this);
								consume();
							}
							}
							}
							setState(136); 
							_errHandler.sync(this);
							_la = _input.LA(1);
						} while ( _la==T__6 || _la==NL );
						}
						} 
					}
					setState(142);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				}
				setState(143);
				templateStat();
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6 || _la==NL) {
					{
					{
					setState(144);
					_la = _input.LA(1);
					if ( !(_la==T__6 || _la==NL) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					}
					setState(149);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(150);
				match(T__5);
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
		enterRule(_localctx, 12, RULE_templateStat);
		try {
			setState(156);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__19:
			case T__20:
			case T__21:
				enterOuterAlt(_localctx, 1);
				{
				setState(154);
				def_();
				}
				break;
			case T__7:
			case T__10:
			case T__12:
			case T__13:
			case T__14:
			case T__16:
			case T__18:
			case T__24:
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
			case IntegerLiteral:
			case StringLiteral:
			case FloatingPointLiteral:
			case Minus:
				enterOuterAlt(_localctx, 2);
				{
				setState(155);
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
		public ClassParamsContext classParams() {
			return getRuleContext(ClassParamsContext.class,0);
		}
		public TerminalNode NL() { return getToken(TinyScalaParser.NL, 0); }
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
		enterRule(_localctx, 14, RULE_classParamClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NL) {
				{
				setState(158);
				match(NL);
				}
			}

			setState(161);
			match(T__7);
			setState(162);
			classParams();
			setState(163);
			match(T__8);
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
		enterRule(_localctx, 16, RULE_classParams);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			classParam();
			setState(170);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(166);
				match(T__9);
				setState(167);
				classParam();
				}
				}
				setState(172);
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
		public TerminalNode Colon() { return getToken(TinyScalaParser.Colon, 0); }
		public Type_Context type_() {
			return getRuleContext(Type_Context.class,0);
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
		enterRule(_localctx, 18, RULE_classParam);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			match(Id);
			setState(174);
			match(Colon);
			setState(175);
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
		public StableIdContext stableId() {
			return getRuleContext(StableIdContext.class,0);
		}
		public InfixExprContext infixExpr() {
			return getRuleContext(InfixExprContext.class,0);
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
		enterRule(_localctx, 20, RULE_expr);
		int _la;
		try {
			setState(236);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(177);
				match(T__10);
				setState(178);
				match(T__7);
				setState(179);
				expr();
				setState(180);
				match(T__8);
				setState(184);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(181);
					match(NL);
					}
					}
					setState(186);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(187);
				expr();
				setState(190);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
				case 1:
					{
					setState(188);
					match(T__11);
					setState(189);
					expr();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(192);
				match(T__12);
				setState(193);
				match(T__7);
				setState(194);
				expr();
				setState(195);
				match(T__8);
				setState(199);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(196);
					match(NL);
					}
					}
					setState(201);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(202);
				expr();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(204);
				match(T__13);
				setState(205);
				expr();
				setState(206);
				match(T__12);
				setState(207);
				match(T__7);
				setState(208);
				expr();
				setState(209);
				match(T__8);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(211);
				match(T__14);
				setState(220);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__7:
					{
					setState(212);
					match(T__7);
					setState(213);
					enumerators();
					setState(214);
					match(T__8);
					}
					break;
				case T__4:
					{
					setState(216);
					match(T__4);
					setState(217);
					enumerators();
					setState(218);
					match(T__5);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(223);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__15) {
					{
					setState(222);
					match(T__15);
					}
				}

				setState(225);
				expr();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(227);
				match(T__16);
				setState(229);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
				case 1:
					{
					setState(228);
					expr();
					}
					break;
				}
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(231);
				stableId(0);
				setState(232);
				match(T__17);
				setState(233);
				expr();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(235);
				infixExpr(0);
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
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_infixExpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(239);
			prefixExpr();
			}
			_ctx.stop = _input.LT(-1);
			setState(297);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(295);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
					case 1:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(241);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(242);
						match(OpPrecedence1);
						setState(244);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(243);
							match(NL);
							}
						}

						setState(246);
						infixExpr(11);
						}
						break;
					case 2:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(247);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(248);
						match(OpPrecedence2);
						setState(250);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(249);
							match(NL);
							}
						}

						setState(252);
						infixExpr(10);
						}
						break;
					case 3:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(253);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(254);
						match(OpPrecedence3);
						setState(256);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(255);
							match(NL);
							}
						}

						setState(258);
						infixExpr(9);
						}
						break;
					case 4:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(259);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(260);
						match(OpPrecedence4);
						setState(262);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(261);
							match(NL);
							}
						}

						setState(264);
						infixExpr(8);
						}
						break;
					case 5:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(265);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(266);
						match(OpPrecedence5);
						setState(268);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(267);
							match(NL);
							}
						}

						setState(270);
						infixExpr(7);
						}
						break;
					case 6:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(271);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(272);
						match(OpPrecedence6);
						setState(274);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(273);
							match(NL);
							}
						}

						setState(276);
						infixExpr(6);
						}
						break;
					case 7:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(277);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(278);
						match(OpPrecedence7);
						setState(280);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(279);
							match(NL);
							}
						}

						setState(282);
						infixExpr(5);
						}
						break;
					case 8:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(283);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(284);
						match(OpPrecedence8);
						setState(286);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(285);
							match(NL);
							}
						}

						setState(288);
						infixExpr(4);
						}
						break;
					case 9:
						{
						_localctx = new InfixExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_infixExpr);
						setState(289);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(290);
						match(OpPrecedence9);
						setState(292);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NL) {
							{
							setState(291);
							match(NL);
							}
						}

						setState(294);
						infixExpr(3);
						}
						break;
					}
					} 
				}
				setState(299);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
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
		public SimpleExpr1Context simpleExpr1() {
			return getRuleContext(SimpleExpr1Context.class,0);
		}
		public OpNoPrecedenceContext opNoPrecedence() {
			return getRuleContext(OpNoPrecedenceContext.class,0);
		}
		public NewClassExprContext newClassExpr() {
			return getRuleContext(NewClassExprContext.class,0);
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
		enterRule(_localctx, 24, RULE_prefixExpr);
		int _la;
		try {
			setState(305);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
			case T__24:
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
			case IntegerLiteral:
			case StringLiteral:
			case FloatingPointLiteral:
			case Minus:
				enterOuterAlt(_localctx, 1);
				{
				setState(301);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1097364144128L) != 0)) {
					{
					setState(300);
					opNoPrecedence();
					}
				}

				setState(303);
				simpleExpr1();
				}
				break;
			case T__18:
				enterOuterAlt(_localctx, 2);
				{
				setState(304);
				newClassExpr();
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
	public static class NewClassExprContext extends ParserRuleContext {
		public TerminalNode Id() { return getToken(TinyScalaParser.Id, 0); }
		public ArgumentExprsContext argumentExprs() {
			return getRuleContext(ArgumentExprsContext.class,0);
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
		enterRule(_localctx, 26, RULE_newClassExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(307);
			match(T__18);
			setState(308);
			match(Id);
			setState(309);
			argumentExprs();
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
		public ArgumentExprsContext argumentExprs() {
			return getRuleContext(ArgumentExprsContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
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
		SimpleExpr1Context _localctx = new SimpleExpr1Context(_ctx, getState());
		enterRule(_localctx, 28, RULE_simpleExpr1);
		try {
			setState(320);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(311);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(312);
				stableId(0);
				setState(313);
				argumentExprs();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(315);
				stableId(0);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(316);
				match(T__7);
				setState(317);
				expr();
				setState(318);
				match(T__8);
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
	public static class ArgumentExprsContext extends ParserRuleContext {
		public ExprsContext exprs() {
			return getRuleContext(ExprsContext.class,0);
		}
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
		enterRule(_localctx, 30, RULE_argumentExprs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(322);
			match(T__7);
			setState(324);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 598133286037760L) != 0)) {
				{
				setState(323);
				exprs();
				}
			}

			setState(326);
			match(T__8);
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
		enterRule(_localctx, 32, RULE_exprs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(328);
			expr();
			setState(333);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(329);
				match(T__9);
				setState(330);
				expr();
				}
				}
				setState(335);
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
	public static class Def_Context extends ParserRuleContext {
		public PatVarDefContext patVarDef() {
			return getRuleContext(PatVarDefContext.class,0);
		}
		public FunDefContext funDef() {
			return getRuleContext(FunDefContext.class,0);
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
		enterRule(_localctx, 34, RULE_def_);
		try {
			setState(339);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__20:
			case T__21:
				enterOuterAlt(_localctx, 1);
				{
				setState(336);
				patVarDef();
				}
				break;
			case T__19:
				enterOuterAlt(_localctx, 2);
				{
				setState(337);
				match(T__19);
				setState(338);
				funDef();
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
		enterRule(_localctx, 36, RULE_patVarDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(341);
			_la = _input.LA(1);
			if ( !(_la==T__20 || _la==T__21) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(342);
			patDef();
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
		public TerminalNode Id() { return getToken(TinyScalaParser.Id, 0); }
		public TerminalNode Colon() { return getToken(TinyScalaParser.Colon, 0); }
		public Type_Context type_() {
			return getRuleContext(Type_Context.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
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
		enterRule(_localctx, 38, RULE_patDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(344);
			match(Id);
			setState(345);
			match(Colon);
			setState(346);
			type_();
			setState(347);
			match(T__17);
			setState(348);
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
		public TerminalNode Colon() { return getToken(TinyScalaParser.Colon, 0); }
		public Type_Context type_() {
			return getRuleContext(Type_Context.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
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
		enterRule(_localctx, 40, RULE_funDef);
		int _la;
		try {
			setState(368);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(350);
				funSig();
				setState(353);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Colon) {
					{
					setState(351);
					match(Colon);
					setState(352);
					type_();
					}
				}

				setState(355);
				match(T__17);
				setState(356);
				expr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(358);
				funSig();
				setState(361);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Colon) {
					{
					setState(359);
					match(Colon);
					setState(360);
					type_();
					}
				}

				setState(363);
				match(T__17);
				setState(364);
				match(T__4);
				setState(365);
				block();
				setState(366);
				match(T__5);
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
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
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
		enterRule(_localctx, 42, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(371); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(370);
				expr();
				}
				}
				setState(373); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 598133286037760L) != 0) );
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
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
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
		enterRule(_localctx, 44, RULE_funSig);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(375);
			match(Id);
			setState(376);
			match(T__7);
			setState(378);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Id) {
				{
				setState(377);
				params();
				}
			}

			setState(380);
			match(T__8);
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
		enterRule(_localctx, 46, RULE_params);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(382);
			param();
			setState(387);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(383);
				match(T__9);
				setState(384);
				param();
				}
				}
				setState(389);
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
		public TerminalNode Colon() { return getToken(TinyScalaParser.Colon, 0); }
		public Type_Context type_() {
			return getRuleContext(Type_Context.class,0);
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
		enterRule(_localctx, 48, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(390);
			match(Id);
			setState(391);
			match(Colon);
			setState(392);
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
		public SimpleTypeContext simpleType() {
			return getRuleContext(SimpleTypeContext.class,0);
		}
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
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
		enterRule(_localctx, 50, RULE_type_);
		try {
			setState(396);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Id:
				enterOuterAlt(_localctx, 1);
				{
				setState(394);
				simpleType();
				}
				break;
			case T__22:
				enterOuterAlt(_localctx, 2);
				{
				setState(395);
				arrayType();
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
	public static class SimpleTypeContext extends ParserRuleContext {
		public StableIdContext stableId() {
			return getRuleContext(StableIdContext.class,0);
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
		enterRule(_localctx, 52, RULE_simpleType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(398);
			stableId(0);
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
	public static class ArrayTypeContext extends ParserRuleContext {
		public Type_Context type_() {
			return getRuleContext(Type_Context.class,0);
		}
		public ArrayTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).enterArrayType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyScalaListener ) ((TinyScalaListener)listener).exitArrayType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TinyScalaVisitor ) return ((TinyScalaVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayTypeContext arrayType() throws RecognitionException {
		ArrayTypeContext _localctx = new ArrayTypeContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_arrayType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(400);
			match(T__22);
			setState(401);
			type_();
			setState(402);
			match(T__23);
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
		enterRule(_localctx, 56, RULE_types);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(404);
			type_();
			setState(409);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(405);
				match(T__9);
				setState(406);
				type_();
				}
				}
				setState(411);
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
		enterRule(_localctx, 58, RULE_literal);
		int _la;
		try {
			setState(424);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(413);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Minus) {
					{
					setState(412);
					match(Minus);
					}
				}

				setState(415);
				match(IntegerLiteral);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(417);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==Minus) {
					{
					setState(416);
					match(Minus);
					}
				}

				setState(419);
				match(FloatingPointLiteral);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(420);
				match(BooleanLiteral);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(421);
				match(CharacterLiteral);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(422);
				match(StringLiteral);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(423);
				match(T__24);
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
		enterRule(_localctx, 60, RULE_ids);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(426);
			match(Id);
			setState(431);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(427);
				match(T__9);
				setState(428);
				match(Id);
				}
				}
				setState(433);
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
		public TerminalNode Id() { return getToken(TinyScalaParser.Id, 0); }
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
		int _startState = 62;
		enterRecursionRule(_localctx, 62, RULE_stableId, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(435);
			match(Id);
			}
			_ctx.stop = _input.LT(-1);
			setState(442);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,51,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new StableIdContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_stableId);
					setState(437);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(438);
					match(T__25);
					setState(439);
					match(Id);
					}
					} 
				}
				setState(444);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,51,_ctx);
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
		enterRule(_localctx, 64, RULE_opNoPrecedence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(445);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1097364144128L) != 0)) ) {
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
		enterRule(_localctx, 66, RULE_pattern);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(447);
			pattern1();
			setState(452);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__26) {
				{
				{
				setState(448);
				match(T__26);
				setState(449);
				pattern1();
				}
				}
				setState(454);
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
		public TerminalNode Colon() { return getToken(TinyScalaParser.Colon, 0); }
		public Type_Context type_() {
			return getRuleContext(Type_Context.class,0);
		}
		public TerminalNode BoundVarid() { return getToken(TinyScalaParser.BoundVarid, 0); }
		public TerminalNode Id() { return getToken(TinyScalaParser.Id, 0); }
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
		enterRule(_localctx, 68, RULE_pattern1);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(455);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 70370086354944L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(456);
			match(Colon);
			setState(457);
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
		enterRule(_localctx, 70, RULE_patterns);
		try {
			setState(465);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(459);
				pattern();
				setState(462);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
				case 1:
					{
					setState(460);
					match(T__9);
					setState(461);
					patterns();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(464);
				match(T__27);
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
		enterRule(_localctx, 72, RULE_enumerators);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(468); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(467);
				generator();
				}
				}
				setState(470); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 70370086354944L) != 0) );
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
		enterRule(_localctx, 74, RULE_generator);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(472);
			pattern1();
			setState(473);
			match(T__28);
			setState(474);
			expr();
			setState(481);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,56,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(475);
					pattern1();
					setState(476);
					match(T__17);
					setState(477);
					expr();
					}
					} 
				}
				setState(483);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,56,_ctx);
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
		case 11:
			return infixExpr_sempred((InfixExprContext)_localctx, predIndex);
		case 31:
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
	private boolean stableId_sempred(StableIdContext _localctx, int predIndex) {
		switch (predIndex) {
		case 9:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u00017\u01e5\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
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
		"#\u0007#\u0002$\u0007$\u0002%\u0007%\u0001\u0000\u0005\u0000N\b\u0000"+
		"\n\u0000\f\u0000Q\t\u0000\u0001\u0000\u0001\u0000\u0005\u0000U\b\u0000"+
		"\n\u0000\f\u0000X\t\u0000\u0004\u0000Z\b\u0000\u000b\u0000\f\u0000[\u0001"+
		"\u0001\u0001\u0001\u0003\u0001`\b\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003i\b"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0005\u0005q\b\u0005\n\u0005\f\u0005t\t\u0005\u0001\u0005\u0001"+
		"\u0005\u0005\u0005x\b\u0005\n\u0005\f\u0005{\t\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0005\u0005\u0080\b\u0005\n\u0005\f\u0005\u0083\t\u0005"+
		"\u0001\u0005\u0001\u0005\u0004\u0005\u0087\b\u0005\u000b\u0005\f\u0005"+
		"\u0088\u0005\u0005\u008b\b\u0005\n\u0005\f\u0005\u008e\t\u0005\u0001\u0005"+
		"\u0001\u0005\u0005\u0005\u0092\b\u0005\n\u0005\f\u0005\u0095\t\u0005\u0001"+
		"\u0005\u0001\u0005\u0003\u0005\u0099\b\u0005\u0001\u0006\u0001\u0006\u0003"+
		"\u0006\u009d\b\u0006\u0001\u0007\u0003\u0007\u00a0\b\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0005\b\u00a9"+
		"\b\b\n\b\f\b\u00ac\t\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n"+
		"\u0001\n\u0001\n\u0001\n\u0005\n\u00b7\b\n\n\n\f\n\u00ba\t\n\u0001\n\u0001"+
		"\n\u0001\n\u0003\n\u00bf\b\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0005"+
		"\n\u00c6\b\n\n\n\f\n\u00c9\t\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n"+
		"\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u00dd\b\n\u0001\n\u0003\n\u00e0"+
		"\b\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u00e6\b\n\u0001\n\u0001\n"+
		"\u0001\n\u0001\n\u0001\n\u0003\n\u00ed\b\n\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u00f5\b\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u00fb\b\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u0101\b\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u0107\b\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u010d\b\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u0113\b\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u0119\b\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u011f\b\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u0125\b\u000b\u0001"+
		"\u000b\u0005\u000b\u0128\b\u000b\n\u000b\f\u000b\u012b\t\u000b\u0001\f"+
		"\u0003\f\u012e\b\f\u0001\f\u0001\f\u0003\f\u0132\b\f\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u0141\b\u000e"+
		"\u0001\u000f\u0001\u000f\u0003\u000f\u0145\b\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010\u014c\b\u0010\n\u0010"+
		"\f\u0010\u014f\t\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0003\u0011"+
		"\u0154\b\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0003\u0014\u0162\b\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0003\u0014\u016a\b\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0003\u0014\u0171\b\u0014"+
		"\u0001\u0015\u0004\u0015\u0174\b\u0015\u000b\u0015\f\u0015\u0175\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0003\u0016\u017b\b\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0005\u0017\u0182\b\u0017\n"+
		"\u0017\f\u0017\u0185\t\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0001\u0019\u0001\u0019\u0003\u0019\u018d\b\u0019\u0001\u001a\u0001"+
		"\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0005\u001c\u0198\b\u001c\n\u001c\f\u001c\u019b\t\u001c"+
		"\u0001\u001d\u0003\u001d\u019e\b\u001d\u0001\u001d\u0001\u001d\u0003\u001d"+
		"\u01a2\b\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0003\u001d\u01a9\b\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0005\u001e"+
		"\u01ae\b\u001e\n\u001e\f\u001e\u01b1\t\u001e\u0001\u001f\u0001\u001f\u0001"+
		"\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0005\u001f\u01b9\b\u001f\n"+
		"\u001f\f\u001f\u01bc\t\u001f\u0001 \u0001 \u0001!\u0001!\u0001!\u0005"+
		"!\u01c3\b!\n!\f!\u01c6\t!\u0001\"\u0001\"\u0001\"\u0001\"\u0001#\u0001"+
		"#\u0001#\u0003#\u01cf\b#\u0001#\u0003#\u01d2\b#\u0001$\u0004$\u01d5\b"+
		"$\u000b$\f$\u01d6\u0001%\u0001%\u0001%\u0001%\u0001%\u0001%\u0001%\u0005"+
		"%\u01e0\b%\n%\f%\u01e3\t%\u0001%\u0000\u0002\u0016>&\u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \""+
		"$&(*,.02468:<>@BDFHJ\u0000\u0005\u0002\u00003355\u0002\u0000\u0007\u0007"+
		"33\u0001\u0000\u0015\u0016\u0001\u0000\u001f\'\u0003\u0000\u001c\u001c"+
		"\u001e\u001e..\u0209\u0000Y\u0001\u0000\u0000\u0000\u0002_\u0001\u0000"+
		"\u0000\u0000\u0004a\u0001\u0000\u0000\u0000\u0006e\u0001\u0000\u0000\u0000"+
		"\bl\u0001\u0000\u0000\u0000\nr\u0001\u0000\u0000\u0000\f\u009c\u0001\u0000"+
		"\u0000\u0000\u000e\u009f\u0001\u0000\u0000\u0000\u0010\u00a5\u0001\u0000"+
		"\u0000\u0000\u0012\u00ad\u0001\u0000\u0000\u0000\u0014\u00ec\u0001\u0000"+
		"\u0000\u0000\u0016\u00ee\u0001\u0000\u0000\u0000\u0018\u0131\u0001\u0000"+
		"\u0000\u0000\u001a\u0133\u0001\u0000\u0000\u0000\u001c\u0140\u0001\u0000"+
		"\u0000\u0000\u001e\u0142\u0001\u0000\u0000\u0000 \u0148\u0001\u0000\u0000"+
		"\u0000\"\u0153\u0001\u0000\u0000\u0000$\u0155\u0001\u0000\u0000\u0000"+
		"&\u0158\u0001\u0000\u0000\u0000(\u0170\u0001\u0000\u0000\u0000*\u0173"+
		"\u0001\u0000\u0000\u0000,\u0177\u0001\u0000\u0000\u0000.\u017e\u0001\u0000"+
		"\u0000\u00000\u0186\u0001\u0000\u0000\u00002\u018c\u0001\u0000\u0000\u0000"+
		"4\u018e\u0001\u0000\u0000\u00006\u0190\u0001\u0000\u0000\u00008\u0194"+
		"\u0001\u0000\u0000\u0000:\u01a8\u0001\u0000\u0000\u0000<\u01aa\u0001\u0000"+
		"\u0000\u0000>\u01b2\u0001\u0000\u0000\u0000@\u01bd\u0001\u0000\u0000\u0000"+
		"B\u01bf\u0001\u0000\u0000\u0000D\u01c7\u0001\u0000\u0000\u0000F\u01d1"+
		"\u0001\u0000\u0000\u0000H\u01d4\u0001\u0000\u0000\u0000J\u01d8\u0001\u0000"+
		"\u0000\u0000LN\u00053\u0000\u0000ML\u0001\u0000\u0000\u0000NQ\u0001\u0000"+
		"\u0000\u0000OM\u0001\u0000\u0000\u0000OP\u0001\u0000\u0000\u0000PR\u0001"+
		"\u0000\u0000\u0000QO\u0001\u0000\u0000\u0000RV\u0003\u0002\u0001\u0000"+
		"SU\u00053\u0000\u0000TS\u0001\u0000\u0000\u0000UX\u0001\u0000\u0000\u0000"+
		"VT\u0001\u0000\u0000\u0000VW\u0001\u0000\u0000\u0000WZ\u0001\u0000\u0000"+
		"\u0000XV\u0001\u0000\u0000\u0000YO\u0001\u0000\u0000\u0000Z[\u0001\u0000"+
		"\u0000\u0000[Y\u0001\u0000\u0000\u0000[\\\u0001\u0000\u0000\u0000\\\u0001"+
		"\u0001\u0000\u0000\u0000]`\u0003\u0004\u0002\u0000^`\u0003\u0006\u0003"+
		"\u0000_]\u0001\u0000\u0000\u0000_^\u0001\u0000\u0000\u0000`\u0003\u0001"+
		"\u0000\u0000\u0000ab\u0005\u0001\u0000\u0000bc\u0005\u001e\u0000\u0000"+
		"cd\u0003\u000e\u0007\u0000d\u0005\u0001\u0000\u0000\u0000ef\u0005\u0002"+
		"\u0000\u0000fh\u0005\u001e\u0000\u0000gi\u0003\b\u0004\u0000hg\u0001\u0000"+
		"\u0000\u0000hi\u0001\u0000\u0000\u0000ij\u0001\u0000\u0000\u0000jk\u0003"+
		"\n\u0005\u0000k\u0007\u0001\u0000\u0000\u0000lm\u0005\u0003\u0000\u0000"+
		"mn\u0005\u0004\u0000\u0000n\t\u0001\u0000\u0000\u0000oq\u00053\u0000\u0000"+
		"po\u0001\u0000\u0000\u0000qt\u0001\u0000\u0000\u0000rp\u0001\u0000\u0000"+
		"\u0000rs\u0001\u0000\u0000\u0000s\u0098\u0001\u0000\u0000\u0000tr\u0001"+
		"\u0000\u0000\u0000uy\u0005\u0005\u0000\u0000vx\u0007\u0000\u0000\u0000"+
		"wv\u0001\u0000\u0000\u0000x{\u0001\u0000\u0000\u0000yw\u0001\u0000\u0000"+
		"\u0000yz\u0001\u0000\u0000\u0000z|\u0001\u0000\u0000\u0000{y\u0001\u0000"+
		"\u0000\u0000|\u0099\u0005\u0006\u0000\u0000}\u0081\u0005\u0005\u0000\u0000"+
		"~\u0080\u0007\u0000\u0000\u0000\u007f~\u0001\u0000\u0000\u0000\u0080\u0083"+
		"\u0001\u0000\u0000\u0000\u0081\u007f\u0001\u0000\u0000\u0000\u0081\u0082"+
		"\u0001\u0000\u0000\u0000\u0082\u008c\u0001\u0000\u0000\u0000\u0083\u0081"+
		"\u0001\u0000\u0000\u0000\u0084\u0086\u0003\f\u0006\u0000\u0085\u0087\u0007"+
		"\u0001\u0000\u0000\u0086\u0085\u0001\u0000\u0000\u0000\u0087\u0088\u0001"+
		"\u0000\u0000\u0000\u0088\u0086\u0001\u0000\u0000\u0000\u0088\u0089\u0001"+
		"\u0000\u0000\u0000\u0089\u008b\u0001\u0000\u0000\u0000\u008a\u0084\u0001"+
		"\u0000\u0000\u0000\u008b\u008e\u0001\u0000\u0000\u0000\u008c\u008a\u0001"+
		"\u0000\u0000\u0000\u008c\u008d\u0001\u0000\u0000\u0000\u008d\u008f\u0001"+
		"\u0000\u0000\u0000\u008e\u008c\u0001\u0000\u0000\u0000\u008f\u0093\u0003"+
		"\f\u0006\u0000\u0090\u0092\u0007\u0001\u0000\u0000\u0091\u0090\u0001\u0000"+
		"\u0000\u0000\u0092\u0095\u0001\u0000\u0000\u0000\u0093\u0091\u0001\u0000"+
		"\u0000\u0000\u0093\u0094\u0001\u0000\u0000\u0000\u0094\u0096\u0001\u0000"+
		"\u0000\u0000\u0095\u0093\u0001\u0000\u0000\u0000\u0096\u0097\u0005\u0006"+
		"\u0000\u0000\u0097\u0099\u0001\u0000\u0000\u0000\u0098u\u0001\u0000\u0000"+
		"\u0000\u0098}\u0001\u0000\u0000\u0000\u0099\u000b\u0001\u0000\u0000\u0000"+
		"\u009a\u009d\u0003\"\u0011\u0000\u009b\u009d\u0003\u0014\n\u0000\u009c"+
		"\u009a\u0001\u0000\u0000\u0000\u009c\u009b\u0001\u0000\u0000\u0000\u009d"+
		"\r\u0001\u0000\u0000\u0000\u009e\u00a0\u00053\u0000\u0000\u009f\u009e"+
		"\u0001\u0000\u0000\u0000\u009f\u00a0\u0001\u0000\u0000\u0000\u00a0\u00a1"+
		"\u0001\u0000\u0000\u0000\u00a1\u00a2\u0005\b\u0000\u0000\u00a2\u00a3\u0003"+
		"\u0010\b\u0000\u00a3\u00a4\u0005\t\u0000\u0000\u00a4\u000f\u0001\u0000"+
		"\u0000\u0000\u00a5\u00aa\u0003\u0012\t\u0000\u00a6\u00a7\u0005\n\u0000"+
		"\u0000\u00a7\u00a9\u0003\u0012\t\u0000\u00a8\u00a6\u0001\u0000\u0000\u0000"+
		"\u00a9\u00ac\u0001\u0000\u0000\u0000\u00aa\u00a8\u0001\u0000\u0000\u0000"+
		"\u00aa\u00ab\u0001\u0000\u0000\u0000\u00ab\u0011\u0001\u0000\u0000\u0000"+
		"\u00ac\u00aa\u0001\u0000\u0000\u0000\u00ad\u00ae\u0005\u001e\u0000\u0000"+
		"\u00ae\u00af\u00052\u0000\u0000\u00af\u00b0\u00032\u0019\u0000\u00b0\u0013"+
		"\u0001\u0000\u0000\u0000\u00b1\u00b2\u0005\u000b\u0000\u0000\u00b2\u00b3"+
		"\u0005\b\u0000\u0000\u00b3\u00b4\u0003\u0014\n\u0000\u00b4\u00b8\u0005"+
		"\t\u0000\u0000\u00b5\u00b7\u00053\u0000\u0000\u00b6\u00b5\u0001\u0000"+
		"\u0000\u0000\u00b7\u00ba\u0001\u0000\u0000\u0000\u00b8\u00b6\u0001\u0000"+
		"\u0000\u0000\u00b8\u00b9\u0001\u0000\u0000\u0000\u00b9\u00bb\u0001\u0000"+
		"\u0000\u0000\u00ba\u00b8\u0001\u0000\u0000\u0000\u00bb\u00be\u0003\u0014"+
		"\n\u0000\u00bc\u00bd\u0005\f\u0000\u0000\u00bd\u00bf\u0003\u0014\n\u0000"+
		"\u00be\u00bc\u0001\u0000\u0000\u0000\u00be\u00bf\u0001\u0000\u0000\u0000"+
		"\u00bf\u00ed\u0001\u0000\u0000\u0000\u00c0\u00c1\u0005\r\u0000\u0000\u00c1"+
		"\u00c2\u0005\b\u0000\u0000\u00c2\u00c3\u0003\u0014\n\u0000\u00c3\u00c7"+
		"\u0005\t\u0000\u0000\u00c4\u00c6\u00053\u0000\u0000\u00c5\u00c4\u0001"+
		"\u0000\u0000\u0000\u00c6\u00c9\u0001\u0000\u0000\u0000\u00c7\u00c5\u0001"+
		"\u0000\u0000\u0000\u00c7\u00c8\u0001\u0000\u0000\u0000\u00c8\u00ca\u0001"+
		"\u0000\u0000\u0000\u00c9\u00c7\u0001\u0000\u0000\u0000\u00ca\u00cb\u0003"+
		"\u0014\n\u0000\u00cb\u00ed\u0001\u0000\u0000\u0000\u00cc\u00cd\u0005\u000e"+
		"\u0000\u0000\u00cd\u00ce\u0003\u0014\n\u0000\u00ce\u00cf\u0005\r\u0000"+
		"\u0000\u00cf\u00d0\u0005\b\u0000\u0000\u00d0\u00d1\u0003\u0014\n\u0000"+
		"\u00d1\u00d2\u0005\t\u0000\u0000\u00d2\u00ed\u0001\u0000\u0000\u0000\u00d3"+
		"\u00dc\u0005\u000f\u0000\u0000\u00d4\u00d5\u0005\b\u0000\u0000\u00d5\u00d6"+
		"\u0003H$\u0000\u00d6\u00d7\u0005\t\u0000\u0000\u00d7\u00dd\u0001\u0000"+
		"\u0000\u0000\u00d8\u00d9\u0005\u0005\u0000\u0000\u00d9\u00da\u0003H$\u0000"+
		"\u00da\u00db\u0005\u0006\u0000\u0000\u00db\u00dd\u0001\u0000\u0000\u0000"+
		"\u00dc\u00d4\u0001\u0000\u0000\u0000\u00dc\u00d8\u0001\u0000\u0000\u0000"+
		"\u00dd\u00df\u0001\u0000\u0000\u0000\u00de\u00e0\u0005\u0010\u0000\u0000"+
		"\u00df\u00de\u0001\u0000\u0000\u0000\u00df\u00e0\u0001\u0000\u0000\u0000"+
		"\u00e0\u00e1\u0001\u0000\u0000\u0000\u00e1\u00e2\u0003\u0014\n\u0000\u00e2"+
		"\u00ed\u0001\u0000\u0000\u0000\u00e3\u00e5\u0005\u0011\u0000\u0000\u00e4"+
		"\u00e6\u0003\u0014\n\u0000\u00e5\u00e4\u0001\u0000\u0000\u0000\u00e5\u00e6"+
		"\u0001\u0000\u0000\u0000\u00e6\u00ed\u0001\u0000\u0000\u0000\u00e7\u00e8"+
		"\u0003>\u001f\u0000\u00e8\u00e9\u0005\u0012\u0000\u0000\u00e9\u00ea\u0003"+
		"\u0014\n\u0000\u00ea\u00ed\u0001\u0000\u0000\u0000\u00eb\u00ed\u0003\u0016"+
		"\u000b\u0000\u00ec\u00b1\u0001\u0000\u0000\u0000\u00ec\u00c0\u0001\u0000"+
		"\u0000\u0000\u00ec\u00cc\u0001\u0000\u0000\u0000\u00ec\u00d3\u0001\u0000"+
		"\u0000\u0000\u00ec\u00e3\u0001\u0000\u0000\u0000\u00ec\u00e7\u0001\u0000"+
		"\u0000\u0000\u00ec\u00eb\u0001\u0000\u0000\u0000\u00ed\u0015\u0001\u0000"+
		"\u0000\u0000\u00ee\u00ef\u0006\u000b\uffff\uffff\u0000\u00ef\u00f0\u0003"+
		"\u0018\f\u0000\u00f0\u0129\u0001\u0000\u0000\u0000\u00f1\u00f2\n\n\u0000"+
		"\u0000\u00f2\u00f4\u0005\u001f\u0000\u0000\u00f3\u00f5\u00053\u0000\u0000"+
		"\u00f4\u00f3\u0001\u0000\u0000\u0000\u00f4\u00f5\u0001\u0000\u0000\u0000"+
		"\u00f5\u00f6\u0001\u0000\u0000\u0000\u00f6\u0128\u0003\u0016\u000b\u000b"+
		"\u00f7\u00f8\n\t\u0000\u0000\u00f8\u00fa\u0005 \u0000\u0000\u00f9\u00fb"+
		"\u00053\u0000\u0000\u00fa\u00f9\u0001\u0000\u0000\u0000\u00fa\u00fb\u0001"+
		"\u0000\u0000\u0000\u00fb\u00fc\u0001\u0000\u0000\u0000\u00fc\u0128\u0003"+
		"\u0016\u000b\n\u00fd\u00fe\n\b\u0000\u0000\u00fe\u0100\u0005!\u0000\u0000"+
		"\u00ff\u0101\u00053\u0000\u0000\u0100\u00ff\u0001\u0000\u0000\u0000\u0100"+
		"\u0101\u0001\u0000\u0000\u0000\u0101\u0102\u0001\u0000\u0000\u0000\u0102"+
		"\u0128\u0003\u0016\u000b\t\u0103\u0104\n\u0007\u0000\u0000\u0104\u0106"+
		"\u0005\"\u0000\u0000\u0105\u0107\u00053\u0000\u0000\u0106\u0105\u0001"+
		"\u0000\u0000\u0000\u0106\u0107\u0001\u0000\u0000\u0000\u0107\u0108\u0001"+
		"\u0000\u0000\u0000\u0108\u0128\u0003\u0016\u000b\b\u0109\u010a\n\u0006"+
		"\u0000\u0000\u010a\u010c\u0005#\u0000\u0000\u010b\u010d\u00053\u0000\u0000"+
		"\u010c\u010b\u0001\u0000\u0000\u0000\u010c\u010d\u0001\u0000\u0000\u0000"+
		"\u010d\u010e\u0001\u0000\u0000\u0000\u010e\u0128\u0003\u0016\u000b\u0007"+
		"\u010f\u0110\n\u0005\u0000\u0000\u0110\u0112\u0005$\u0000\u0000\u0111"+
		"\u0113\u00053\u0000\u0000\u0112\u0111\u0001\u0000\u0000\u0000\u0112\u0113"+
		"\u0001\u0000\u0000\u0000\u0113\u0114\u0001\u0000\u0000\u0000\u0114\u0128"+
		"\u0003\u0016\u000b\u0006\u0115\u0116\n\u0004\u0000\u0000\u0116\u0118\u0005"+
		"%\u0000\u0000\u0117\u0119\u00053\u0000\u0000\u0118\u0117\u0001\u0000\u0000"+
		"\u0000\u0118\u0119\u0001\u0000\u0000\u0000\u0119\u011a\u0001\u0000\u0000"+
		"\u0000\u011a\u0128\u0003\u0016\u000b\u0005\u011b\u011c\n\u0003\u0000\u0000"+
		"\u011c\u011e\u0005&\u0000\u0000\u011d\u011f\u00053\u0000\u0000\u011e\u011d"+
		"\u0001\u0000\u0000\u0000\u011e\u011f\u0001\u0000\u0000\u0000\u011f\u0120"+
		"\u0001\u0000\u0000\u0000\u0120\u0128\u0003\u0016\u000b\u0004\u0121\u0122"+
		"\n\u0002\u0000\u0000\u0122\u0124\u0005\'\u0000\u0000\u0123\u0125\u0005"+
		"3\u0000\u0000\u0124\u0123\u0001\u0000\u0000\u0000\u0124\u0125\u0001\u0000"+
		"\u0000\u0000\u0125\u0126\u0001\u0000\u0000\u0000\u0126\u0128\u0003\u0016"+
		"\u000b\u0003\u0127\u00f1\u0001\u0000\u0000\u0000\u0127\u00f7\u0001\u0000"+
		"\u0000\u0000\u0127\u00fd\u0001\u0000\u0000\u0000\u0127\u0103\u0001\u0000"+
		"\u0000\u0000\u0127\u0109\u0001\u0000\u0000\u0000\u0127\u010f\u0001\u0000"+
		"\u0000\u0000\u0127\u0115\u0001\u0000\u0000\u0000\u0127\u011b\u0001\u0000"+
		"\u0000\u0000\u0127\u0121\u0001\u0000\u0000\u0000\u0128\u012b\u0001\u0000"+
		"\u0000\u0000\u0129\u0127\u0001\u0000\u0000\u0000\u0129\u012a\u0001\u0000"+
		"\u0000\u0000\u012a\u0017\u0001\u0000\u0000\u0000\u012b\u0129\u0001\u0000"+
		"\u0000\u0000\u012c\u012e\u0003@ \u0000\u012d\u012c\u0001\u0000\u0000\u0000"+
		"\u012d\u012e\u0001\u0000\u0000\u0000\u012e\u012f\u0001\u0000\u0000\u0000"+
		"\u012f\u0132\u0003\u001c\u000e\u0000\u0130\u0132\u0003\u001a\r\u0000\u0131"+
		"\u012d\u0001\u0000\u0000\u0000\u0131\u0130\u0001\u0000\u0000\u0000\u0132"+
		"\u0019\u0001\u0000\u0000\u0000\u0133\u0134\u0005\u0013\u0000\u0000\u0134"+
		"\u0135\u0005\u001e\u0000\u0000\u0135\u0136\u0003\u001e\u000f\u0000\u0136"+
		"\u001b\u0001\u0000\u0000\u0000\u0137\u0141\u0003:\u001d\u0000\u0138\u0139"+
		"\u0003>\u001f\u0000\u0139\u013a\u0003\u001e\u000f\u0000\u013a\u0141\u0001"+
		"\u0000\u0000\u0000\u013b\u0141\u0003>\u001f\u0000\u013c\u013d\u0005\b"+
		"\u0000\u0000\u013d\u013e\u0003\u0014\n\u0000\u013e\u013f\u0005\t\u0000"+
		"\u0000\u013f\u0141\u0001\u0000\u0000\u0000\u0140\u0137\u0001\u0000\u0000"+
		"\u0000\u0140\u0138\u0001\u0000\u0000\u0000\u0140\u013b\u0001\u0000\u0000"+
		"\u0000\u0140\u013c\u0001\u0000\u0000\u0000\u0141\u001d\u0001\u0000\u0000"+
		"\u0000\u0142\u0144\u0005\b\u0000\u0000\u0143\u0145\u0003 \u0010\u0000"+
		"\u0144\u0143\u0001\u0000\u0000\u0000\u0144\u0145\u0001\u0000\u0000\u0000"+
		"\u0145\u0146\u0001\u0000\u0000\u0000\u0146\u0147\u0005\t\u0000\u0000\u0147"+
		"\u001f\u0001\u0000\u0000\u0000\u0148\u014d\u0003\u0014\n\u0000\u0149\u014a"+
		"\u0005\n\u0000\u0000\u014a\u014c\u0003\u0014\n\u0000\u014b\u0149\u0001"+
		"\u0000\u0000\u0000\u014c\u014f\u0001\u0000\u0000\u0000\u014d\u014b\u0001"+
		"\u0000\u0000\u0000\u014d\u014e\u0001\u0000\u0000\u0000\u014e!\u0001\u0000"+
		"\u0000\u0000\u014f\u014d\u0001\u0000\u0000\u0000\u0150\u0154\u0003$\u0012"+
		"\u0000\u0151\u0152\u0005\u0014\u0000\u0000\u0152\u0154\u0003(\u0014\u0000"+
		"\u0153\u0150\u0001\u0000\u0000\u0000\u0153\u0151\u0001\u0000\u0000\u0000"+
		"\u0154#\u0001\u0000\u0000\u0000\u0155\u0156\u0007\u0002\u0000\u0000\u0156"+
		"\u0157\u0003&\u0013\u0000\u0157%\u0001\u0000\u0000\u0000\u0158\u0159\u0005"+
		"\u001e\u0000\u0000\u0159\u015a\u00052\u0000\u0000\u015a\u015b\u00032\u0019"+
		"\u0000\u015b\u015c\u0005\u0012\u0000\u0000\u015c\u015d\u0003\u0014\n\u0000"+
		"\u015d\'\u0001\u0000\u0000\u0000\u015e\u0161\u0003,\u0016\u0000\u015f"+
		"\u0160\u00052\u0000\u0000\u0160\u0162\u00032\u0019\u0000\u0161\u015f\u0001"+
		"\u0000\u0000\u0000\u0161\u0162\u0001\u0000\u0000\u0000\u0162\u0163\u0001"+
		"\u0000\u0000\u0000\u0163\u0164\u0005\u0012\u0000\u0000\u0164\u0165\u0003"+
		"\u0014\n\u0000\u0165\u0171\u0001\u0000\u0000\u0000\u0166\u0169\u0003,"+
		"\u0016\u0000\u0167\u0168\u00052\u0000\u0000\u0168\u016a\u00032\u0019\u0000"+
		"\u0169\u0167\u0001\u0000\u0000\u0000\u0169\u016a\u0001\u0000\u0000\u0000"+
		"\u016a\u016b\u0001\u0000\u0000\u0000\u016b\u016c\u0005\u0012\u0000\u0000"+
		"\u016c\u016d\u0005\u0005\u0000\u0000\u016d\u016e\u0003*\u0015\u0000\u016e"+
		"\u016f\u0005\u0006\u0000\u0000\u016f\u0171\u0001\u0000\u0000\u0000\u0170"+
		"\u015e\u0001\u0000\u0000\u0000\u0170\u0166\u0001\u0000\u0000\u0000\u0171"+
		")\u0001\u0000\u0000\u0000\u0172\u0174\u0003\u0014\n\u0000\u0173\u0172"+
		"\u0001\u0000\u0000\u0000\u0174\u0175\u0001\u0000\u0000\u0000\u0175\u0173"+
		"\u0001\u0000\u0000\u0000\u0175\u0176\u0001\u0000\u0000\u0000\u0176+\u0001"+
		"\u0000\u0000\u0000\u0177\u0178\u0005\u001e\u0000\u0000\u0178\u017a\u0005"+
		"\b\u0000\u0000\u0179\u017b\u0003.\u0017\u0000\u017a\u0179\u0001\u0000"+
		"\u0000\u0000\u017a\u017b\u0001\u0000\u0000\u0000\u017b\u017c\u0001\u0000"+
		"\u0000\u0000\u017c\u017d\u0005\t\u0000\u0000\u017d-\u0001\u0000\u0000"+
		"\u0000\u017e\u0183\u00030\u0018\u0000\u017f\u0180\u0005\n\u0000\u0000"+
		"\u0180\u0182\u00030\u0018\u0000\u0181\u017f\u0001\u0000\u0000\u0000\u0182"+
		"\u0185\u0001\u0000\u0000\u0000\u0183\u0181\u0001\u0000\u0000\u0000\u0183"+
		"\u0184\u0001\u0000\u0000\u0000\u0184/\u0001\u0000\u0000\u0000\u0185\u0183"+
		"\u0001\u0000\u0000\u0000\u0186\u0187\u0005\u001e\u0000\u0000\u0187\u0188"+
		"\u00052\u0000\u0000\u0188\u0189\u00032\u0019\u0000\u01891\u0001\u0000"+
		"\u0000\u0000\u018a\u018d\u00034\u001a\u0000\u018b\u018d\u00036\u001b\u0000"+
		"\u018c\u018a\u0001\u0000\u0000\u0000\u018c\u018b\u0001\u0000\u0000\u0000"+
		"\u018d3\u0001\u0000\u0000\u0000\u018e\u018f\u0003>\u001f\u0000\u018f5"+
		"\u0001\u0000\u0000\u0000\u0190\u0191\u0005\u0017\u0000\u0000\u0191\u0192"+
		"\u00032\u0019\u0000\u0192\u0193\u0005\u0018\u0000\u0000\u01937\u0001\u0000"+
		"\u0000\u0000\u0194\u0199\u00032\u0019\u0000\u0195\u0196\u0005\n\u0000"+
		"\u0000\u0196\u0198\u00032\u0019\u0000\u0197\u0195\u0001\u0000\u0000\u0000"+
		"\u0198\u019b\u0001\u0000\u0000\u0000\u0199\u0197\u0001\u0000\u0000\u0000"+
		"\u0199\u019a\u0001\u0000\u0000\u0000\u019a9\u0001\u0000\u0000\u0000\u019b"+
		"\u0199\u0001\u0000\u0000\u0000\u019c\u019e\u00051\u0000\u0000\u019d\u019c"+
		"\u0001\u0000\u0000\u0000\u019d\u019e\u0001\u0000\u0000\u0000\u019e\u019f"+
		"\u0001\u0000\u0000\u0000\u019f\u01a9\u0005*\u0000\u0000\u01a0\u01a2\u0005"+
		"1\u0000\u0000\u01a1\u01a0\u0001\u0000\u0000\u0000\u01a1\u01a2\u0001\u0000"+
		"\u0000\u0000\u01a2\u01a3\u0001\u0000\u0000\u0000\u01a3\u01a9\u0005,\u0000"+
		"\u0000\u01a4\u01a9\u0005(\u0000\u0000\u01a5\u01a9\u0005)\u0000\u0000\u01a6"+
		"\u01a9\u0005+\u0000\u0000\u01a7\u01a9\u0005\u0019\u0000\u0000\u01a8\u019d"+
		"\u0001\u0000\u0000\u0000\u01a8\u01a1\u0001\u0000\u0000\u0000\u01a8\u01a4"+
		"\u0001\u0000\u0000\u0000\u01a8\u01a5\u0001\u0000\u0000\u0000\u01a8\u01a6"+
		"\u0001\u0000\u0000\u0000\u01a8\u01a7\u0001\u0000\u0000\u0000\u01a9;\u0001"+
		"\u0000\u0000\u0000\u01aa\u01af\u0005\u001e\u0000\u0000\u01ab\u01ac\u0005"+
		"\n\u0000\u0000\u01ac\u01ae\u0005\u001e\u0000\u0000\u01ad\u01ab\u0001\u0000"+
		"\u0000\u0000\u01ae\u01b1\u0001\u0000\u0000\u0000\u01af\u01ad\u0001\u0000"+
		"\u0000\u0000\u01af\u01b0\u0001\u0000\u0000\u0000\u01b0=\u0001\u0000\u0000"+
		"\u0000\u01b1\u01af\u0001\u0000\u0000\u0000\u01b2\u01b3\u0006\u001f\uffff"+
		"\uffff\u0000\u01b3\u01b4\u0005\u001e\u0000\u0000\u01b4\u01ba\u0001\u0000"+
		"\u0000\u0000\u01b5\u01b6\n\u0001\u0000\u0000\u01b6\u01b7\u0005\u001a\u0000"+
		"\u0000\u01b7\u01b9\u0005\u001e\u0000\u0000\u01b8\u01b5\u0001\u0000\u0000"+
		"\u0000\u01b9\u01bc\u0001\u0000\u0000\u0000\u01ba\u01b8\u0001\u0000\u0000"+
		"\u0000\u01ba\u01bb\u0001\u0000\u0000\u0000\u01bb?\u0001\u0000\u0000\u0000"+
		"\u01bc\u01ba\u0001\u0000\u0000\u0000\u01bd\u01be\u0007\u0003\u0000\u0000"+
		"\u01beA\u0001\u0000\u0000\u0000\u01bf\u01c4\u0003D\"\u0000\u01c0\u01c1"+
		"\u0005\u001b\u0000\u0000\u01c1\u01c3\u0003D\"\u0000\u01c2\u01c0\u0001"+
		"\u0000\u0000\u0000\u01c3\u01c6\u0001\u0000\u0000\u0000\u01c4\u01c2\u0001"+
		"\u0000\u0000\u0000\u01c4\u01c5\u0001\u0000\u0000\u0000\u01c5C\u0001\u0000"+
		"\u0000\u0000\u01c6\u01c4\u0001\u0000\u0000\u0000\u01c7\u01c8\u0007\u0004"+
		"\u0000\u0000\u01c8\u01c9\u00052\u0000\u0000\u01c9\u01ca\u00032\u0019\u0000"+
		"\u01caE\u0001\u0000\u0000\u0000\u01cb\u01ce\u0003B!\u0000\u01cc\u01cd"+
		"\u0005\n\u0000\u0000\u01cd\u01cf\u0003F#\u0000\u01ce\u01cc\u0001\u0000"+
		"\u0000\u0000\u01ce\u01cf\u0001\u0000\u0000\u0000\u01cf\u01d2\u0001\u0000"+
		"\u0000\u0000\u01d0\u01d2\u0005\u001c\u0000\u0000\u01d1\u01cb\u0001\u0000"+
		"\u0000\u0000\u01d1\u01d0\u0001\u0000\u0000\u0000\u01d2G\u0001\u0000\u0000"+
		"\u0000\u01d3\u01d5\u0003J%\u0000\u01d4\u01d3\u0001\u0000\u0000\u0000\u01d5"+
		"\u01d6\u0001\u0000\u0000\u0000\u01d6\u01d4\u0001\u0000\u0000\u0000\u01d6"+
		"\u01d7\u0001\u0000\u0000\u0000\u01d7I\u0001\u0000\u0000\u0000\u01d8\u01d9"+
		"\u0003D\"\u0000\u01d9\u01da\u0005\u001d\u0000\u0000\u01da\u01e1\u0003"+
		"\u0014\n\u0000\u01db\u01dc\u0003D\"\u0000\u01dc\u01dd\u0005\u0012\u0000"+
		"\u0000\u01dd\u01de\u0003\u0014\n\u0000\u01de\u01e0\u0001\u0000\u0000\u0000"+
		"\u01df\u01db\u0001\u0000\u0000\u0000\u01e0\u01e3\u0001\u0000\u0000\u0000"+
		"\u01e1\u01df\u0001\u0000\u0000\u0000\u01e1\u01e2\u0001\u0000\u0000\u0000"+
		"\u01e2K\u0001\u0000\u0000\u0000\u01e3\u01e1\u0001\u0000\u0000\u00009O"+
		"V[_hry\u0081\u0088\u008c\u0093\u0098\u009c\u009f\u00aa\u00b8\u00be\u00c7"+
		"\u00dc\u00df\u00e5\u00ec\u00f4\u00fa\u0100\u0106\u010c\u0112\u0118\u011e"+
		"\u0124\u0127\u0129\u012d\u0131\u0140\u0144\u014d\u0153\u0161\u0169\u0170"+
		"\u0175\u017a\u0183\u018c\u0199\u019d\u01a1\u01a8\u01af\u01ba\u01c4\u01ce"+
		"\u01d1\u01d6\u01e1";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}