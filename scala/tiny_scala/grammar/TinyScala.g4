grammar TinyScala;

compilationUnit
    : (NL* tmplDef NL*)+
    ;

tmplDef
    : tmplDefCaseClass
    | tmplDefObject
    ;

tmplDefCaseClass : 'case class' Id classParamClause templateBody? ;
tmplDefObject : 'object' Id ('extends' 'App')? templateBody? ;

templateBody
    : NL* (
        '{' (NL | WS)* '}' |
        '{' (NL | WS)* (templateStat (NL | ';')+)* templateStat (NL | ';')* '}'
    )
    ;

templateStat
    : def_
    | expr
    ;

// class:
classParamClause
    : NL? '(' classParams ')'
    ;

classParams
    : classParam (',' classParam)*
    ;

classParam
    : /*('val' | 'var')?*/ Id ':' paramType /*('=' expr)?*/
    ;

// ---------- EXPRESSIONS ----------
expr
    : 'if' '(' expr ')' NL* expr ('else' expr)?
    | 'while' '(' expr ')' NL* expr
    | 'do' expr 'while' '(' expr ')'
    | 'for' ('(' enumerators ')' | '{' enumerators '}') 'yield'? expr
    | 'return' expr?
    | ((newClassExpr | simpleExpr1 '_'?) '.')? Id '=' expr
    | infixExpr 'match' '{' caseClauses '}'
    | infixExpr /* ascription? */
    | simpleExpr1 argumentExprs '=' expr
    ;

infixExpr
    : infixExpr OpPrecedence1 NL? infixExpr
    | infixExpr OpPrecedence2 NL? infixExpr
    | infixExpr OpPrecedence3 NL? infixExpr
    | infixExpr OpPrecedence4 NL? infixExpr
    | infixExpr OpPrecedence5 NL? infixExpr
    | infixExpr OpPrecedence6 NL? infixExpr
    | infixExpr OpPrecedence7 NL? infixExpr
    | infixExpr OpPrecedence8 NL? infixExpr
    | infixExpr OpPrecedence9 NL? infixExpr
    | prefixExpr
    ;

prefixExpr
    : opNoPrecedence? (newClassExpr | simpleExpr1 '_'?)
    ;

newClassExpr
    : 'new' Id argumentExprs*
    ;

// Dublicate lines to prevent left-recursive code.
// can't use (newClassExpr|simpleExpr1) '.' Id
simpleExpr1
    : literal
    | stableId
    | '_'
    | '(' expr ')'
    | newClassExpr '.' Id // ?
    | simpleExpr1 '_'? '.' Id // ?
    | simpleExpr1 argumentExprs
    ;

exprs
    : expr (',' expr)*
    ;

argumentExprs
    : '(' args ')'
    | NL? blockExpr
    ;

args
    : exprs?
    | (exprs ',')? infixExpr (':' | '_')?
    ;

blockExpr
    : '{' caseClauses '}'
    | '{' block '}'
    ;

block
    : blockStat+ resultExpr?
    ;

blockStat
    : def_
    | tmplDef
    | expr
    ;

resultExpr
    : expr
    ;

// ---------- DEFINITIONS ----------
def_
    : patVarDef
    | 'def' funDef
//    | 'type' NL* typeDef
//    | tmplDef
    ;

// val/var:
patVarDef
    : 'val' patDef
    | 'var' patDef
    ;

patDef
    : pattern1 (':' type_)? '=' expr
    ;

// function:
funDef
    : funSig (':' type_)? '=' expr
    | funSig NL? '{' block '}'
    ;

funSig
    : Id paramClauses
    ;

paramClauses
    : paramClause*
    ;

paramClause
    : NL? '(' params? ')'
    ;

params
    : param (',' param)*
    ;

param
    : Id (':' paramType)? ('=' expr)?
    ;

paramType
    : type_
    | '=>' type_
    ;

// type:
typeDef
    : Id '=' type_
    ;

type_
    : functionArgTypes '=>' type_
    | simpleType
    ;

functionArgTypes
    : simpleType
    | '(' (paramType (',' paramType)*)? ')'
    ;

//infixType
//    : simpleType (Id simpleType)*
//    ;

simpleType
    : stableId
    | '(' types ')'
    ;

types
    : type_ (',' type_)*
    ;

// ---------- REST ----------

literal
    : Minus? IntegerLiteral
    | Minus? FloatingPointLiteral
    | BooleanLiteral
    | CharacterLiteral
    | StringLiteral
    | SymbolLiteral
    | 'null'
    ;

ids
    : Id (',' Id)*
    ;

stableId
    : Id
    | stableId '.' Id
    | (Id '.')? ('this' | 'super' '.' Id)
    ;

// Precedence not needed for prefix expressions
opNoPrecedence: (
        OpPrecedence1 | OpPrecedence2 | OpPrecedence3 |
        OpPrecedence4 | OpPrecedence5 | OpPrecedence6 |
        OpPrecedence7 | OpPrecedence8 | OpPrecedence9
    );

// ---------- Case clauses ----------
caseClauses
    : caseClause+
    ;

caseClause
    : 'case' pattern '=>' block
    ;

// ---------- Patterns ----------

pattern
    : pattern1 ('|' pattern1)*
    ;

pattern1
    : (BoundVarid | '_' | Id) ':' type_
    | simplePattern
//    | simplePattern (Id NL? simplePattern)*
    ;

simplePattern
    : '_'
    | Varid
    | literal
    | stableId ('(' patterns? ')')?
    | '(' patterns? ')'
    ;

patterns
    : pattern (',' patterns)?
    | '_'
    ;

enumerators
    : generator+
    ;

generator
    : pattern1 '<-' expr (pattern1 '=' expr)*
    ;

// Lexer

Id
    : Plainid
    | '`' (CharNoBackQuoteOrNewline | CharEscapeSeq)+ '`'
    ;

OpPrecedence1: OpCharPrecedence1 OpCharAnyPrecedence* ;
OpPrecedence2: OpCharPrecedence2 OpCharAnyPrecedence* ;
OpPrecedence3: OpCharPrecedence3 OpCharAnyPrecedence* ;
OpPrecedence4: OpCharPrecedence4 OpCharAnyPrecedence* ;
OpPrecedence5: OpCharPrecedence5 OpCharAnyPrecedence* ;
OpPrecedence6: OpCharPrecedence6 OpCharAnyPrecedence* ;
OpPrecedence7: OpCharPrecedence7 OpCharAnyPrecedence* ;
OpPrecedence8: OpCharPrecedence8 OpCharAnyPrecedence* ;
OpPrecedence9: OpCharPrecedence9 OpCharAnyPrecedence* ;

BooleanLiteral
    : 'true'
    | 'false'
    ;

CharacterLiteral
    : '\'' (PrintableChar | CharEscapeSeq) '\''
    ;

SymbolLiteral
    : '\'' Plainid
    ;

IntegerLiteral
    : (DecimalNumeral | HexNumeral) ('L' | 'l')?
    ;

StringLiteral
    : '"' StringElement* '"'
    | '"""' MultiLineChars '"""'
    ;

FloatingPointLiteral
    : Digit+ '.' Digit+ ExponentPart? FloatType?
    | '.' Digit+ ExponentPart? FloatType?
    | Digit ExponentPart FloatType?
    | Digit+ ExponentPart? FloatType
    ;

Varid
    : Lower Idrest
    ;

BoundVarid
    : Varid
    | '`' Varid '`'
    ;

Paren
    : '('
    | ')'
    | '['
    | ']'
    | '{'
    | '}'
    ;

Delim
    : '`'
    | '\''
    | '"'
    | '.'
    | ';'
    | ','
    ;

Minus
    : '-'
    ;

//Semi
//    : (';' | (NL)+) -> skip
//    ;

NL
    : '\n'
    | '\r' '\n'?
    ;

// \u0020-\u0026 """ !"#$%"""
// \u0028-\u007E """()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~"""
fragment CharNoBackQuoteOrNewline
    : [\u0020-\u0026\u0028-\u007E]
    ;

// fragments

fragment WhiteSpace
    : '\u0020'
    | '\u0009'
//    | '\u000D'
//    | '\u000A'
    ;

fragment OpCharAnyPrecedence
    : '*' | '/' | '%'
    | '+' | Minus
    | ':'
    | '>' | '<'
    | '=' | '!'
    | '&'
    | '^'
    | '|'
    | '$' | '_' | 'a' .. 'z' | 'A' .. 'Z'
    ;
fragment OpAnyPrecedence : OpCharAnyPrecedence+ ;

fragment OpCharPrecedence1 : '*' | '/' | '%' ;
fragment OpCharPrecedence2 : '+' | Minus ;
fragment OpCharPrecedence3 : ':' ;
fragment OpCharPrecedence4 : '>' | '<' ;
fragment OpCharPrecedence5 : '=' | '!' ;
fragment OpCharPrecedence6 : '&' ;
fragment OpCharPrecedence7 : '^' ;
fragment OpCharPrecedence8 : '|' ;
fragment OpCharPrecedence9 : '$' | '_' ;

fragment Idrest
    : (Letter | Digit)* ('_' OpAnyPrecedence)?
    ;

fragment StringElement
    : '\u0020'
    | '\u0021'
    | '\u0023' .. '\u007F'
    | CharEscapeSeq
    ;

fragment MultiLineChars
    : (StringElement | NL)*
    ;

fragment HexDigit
    : '0' .. '9'
    | 'A' .. 'F'
    | 'a' .. 'f'
    ;

fragment FloatType
    : 'F'
    | 'f'
    | 'D'
    | 'd'
    ;

fragment Upper
    : 'A' .. 'Z'
    | '$'
    | '_'
    ;

fragment Lower
    : 'a' .. 'z'
    ;

fragment Letter
    : Upper
    | Lower
    ;

fragment ExponentPart
    : ('E' | 'e') ('+' | Minus)? Digit+
    ;

fragment PrintableChar
    : '\u0020' .. '\u007F'
    ;

fragment PrintableCharExceptWhitespace
    : '\u0021' .. '\u007F'
    ;

fragment CharEscapeSeq
    : '\\' ('b' | 't' | 'n' | 'f' | 'r' | '"' | '\'' | '\\')
    ;

fragment DecimalNumeral
    : '0'
    | NonZeroDigit Digit*
    ;

fragment HexNumeral
    : '0' 'x' HexDigit HexDigit+
    ;

fragment Digit
    : '0'
    | NonZeroDigit
    ;

fragment NonZeroDigit
    : '1' .. '9'
    ;

fragment VaridFragment
    : Varid
    ;

fragment Plainid
    : Upper Idrest
    | Lower Idrest
    ;

//
// Whitespace and comments
//
NEWLINE
    : NL+ -> skip
    ;

WS
    : WhiteSpace+ -> skip
    ;

COMMENT
    : '/*' (COMMENT | .)* '*/' -> skip
    ;

LINE_COMMENT
    : '//' (~[\r\n])* -> skip
    ;