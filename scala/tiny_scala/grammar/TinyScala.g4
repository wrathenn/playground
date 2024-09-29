grammar TinyScala;

compilationUnit
    : (NL* tmplDef NL*)+
    ;

tmplDef
    : tmplDefCaseClass
    | tmplDefObject
    ;

tmplDefCaseClass : 'case class' Id classParamClause /*templateBody?*/ ;
tmplDefObject : 'object' Id objectIsMain? templateBody ;
objectIsMain : 'extends' 'App' ;

templateBody
    : NL* (
        '{' (NL | WS)* '}' |
        '{' (NL | WS)* (templateStat (NL | ';')+)* templateStat (NL | ';')* '}'
    )
    ;

templateStat
    : def_
    | expr
    | statement
    ;

// class:
classParamClause
    : NL? '(' classParams ')'
    ;

classParams
    : classParam (',' classParam)*
    ;

classParam
    : /*('val' | 'var')?*/ Id Colon type_
    ;

// ---------- EXPRESSIONS ----------
statement
    : 'while' '(' expr ')' NL* expr
    | 'do' expr 'while' '(' expr ')'
    ;

expr
    : 'if' '(' expr ')' NL* expr NL* ('else' expr)?
    | 'return' expr?
    | exprBlock
    | stableId '=' expr
    | infixExpr
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
    : opNoPrecedence? simpleExpr1
    | newClassExpr
    ;

newClassExpr
    : 'new' Id argumentExprs
    ;

// Dublicate lines to prevent left-recursive code.
// can't use (newClassExpr|simpleExpr1) '.' Id
simpleExpr1
    : literal
    | stableId argumentExprs
    | stableId
    | '(' expr ')'
    ;

argumentExprs
    : '(' exprs? ')'
    ;

exprs
    : expr (',' expr)*
    ;

exprBlock
    : '{' (NL | WS)* (exprBlockStat (NL | ';')+)* '}'
    ;

exprBlockStat
    : expr
    | patVarDef
    | statement
    ;

// ---------- DEFINITIONS ----------
def_
    : patVarDef
    | 'def' funDef
    ;

// val/var:
patVarDef
    : ('val' | 'var') patDef
    ;

patDef
    : Id Colon type_ '=' expr
    ;

// function:
funDef
    : funSig Colon type_ '=' expr
    ;

funSig
    : Id '(' params? ')'
    ;

params
    : param (',' param)*
    ;

param
    : Id Colon type_
    ;

// type:
type_
    : simpleType
    | arrayType
    ;

simpleType : stableId ;

arrayType : 'Array[' type_ ']' ;

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
    | 'null'
    ;

ids
    : Id (',' Id)*
    ;

stableId
    : Id
    | stableId '.' Id
    ;

// Precedence not needed for prefix expressions
opNoPrecedence: (
        OpPrecedence1 | OpPrecedence2 | OpPrecedence3 |
        OpPrecedence4 | OpPrecedence5 | OpPrecedence6 |
        OpPrecedence7 | OpPrecedence8 | OpPrecedence9
    );

// ---------- Patterns ----------

pattern
    : pattern1 ('|' pattern1)*
    ;

pattern1
    : (BoundVarid | '_' | Id) Colon type_
//    | simplePattern
//    | simplePattern (Id NL? simplePattern)*
    ;

//simplePattern
//    : '_'
//    | Varid
//    | literal
//    | stableId ('(' patterns? ')')?
//    | '(' patterns? ')'
//    ;

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
BooleanLiteral
    : 'true'
    | 'false'
    ;

Id
    : Plainid
    | '`' (CharNoBackQuoteOrNewline | CharEscapeSeq)+ '`'
    ;

OpPrecedence1: OpCharPrecedence1 OpCharAnyPrecedence* ;
OpPrecedence2: OpCharPrecedence2 OpCharAnyPrecedence* ;
OpPrecedence3: OpCharPrecedence3 OpCharAnyPrecedence+ ;
OpPrecedence4: OpCharPrecedence4 OpCharAnyPrecedence* ;
OpPrecedence5: OpCharPrecedence5 OpCharAnyPrecedence* ;
OpPrecedence6: OpCharPrecedence6 OpCharAnyPrecedence* ;
OpPrecedence7: OpCharPrecedence7 OpCharAnyPrecedence* ;
OpPrecedence8: OpCharPrecedence8 OpCharAnyPrecedence* ;
OpPrecedence9: OpCharPrecedence9 OpCharAnyPrecedence* ;

CharacterLiteral
    : '\'' (PrintableChar | CharEscapeSeq) '\''
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

Minus : '-' ;
Colon : ':' ;

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
    | Colon
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
fragment OpCharPrecedence3 : Colon ;
fragment OpCharPrecedence4 : '>' | '<' ;
fragment OpCharPrecedence5 : '=' | '!' ;
fragment OpCharPrecedence6 : '&' ;
fragment OpCharPrecedence7 : '^' ;
fragment OpCharPrecedence8 : '|' ;
fragment OpCharPrecedence9 : '$' | '_' ;

fragment Idrest
    : (Letter | Digit)*
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