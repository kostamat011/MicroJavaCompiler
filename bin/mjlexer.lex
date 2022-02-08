
package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"program"   	{ return new_symbol(sym.PROG, yytext());}
"break"   		{ return new_symbol(sym.BREAK, yytext());}
"continue"   	{ return new_symbol(sym.CONT, yytext());}
"if"   			{ return new_symbol(sym.IF, yytext());}
"else"   		{ return new_symbol(sym.ELSE, yytext());}
"const"   		{ return new_symbol(sym.CONST, yytext());}
"class"   		{ return new_symbol(sym.CLASS, yytext());}
"do"   			{ return new_symbol(sym.DO, yytext());}
"while"   		{ return new_symbol(sym.WHILE, yytext());}
"new"   		{ return new_symbol(sym.NEW, yytext());}
"print"   		{ return new_symbol(sym.PRINT, yytext());}
"read"   		{ return new_symbol(sym.READ, yytext());}
"void"   		{ return new_symbol(sym.VOID, yytext());}
"return"   		{ return new_symbol(sym.RETURN, yytext());}
"record"   		{ return new_symbol(sym.RECORD, yytext());}
"enum"   		{ return new_symbol(sym.ENUM, yytext());}
"super"   		{ return new_symbol(sym.SUPER, yytext());}
"goto"   		{ return new_symbol(sym.GOTO, yytext());}
"this"   		{ return new_symbol(sym.THIS, yytext());}
"extends"   	{ return new_symbol(sym.EXT, yytext());}



"+" 			{ return new_symbol(sym.PLUS, yytext()); }
"-" 			{ return new_symbol(sym.MINUS, yytext()); }
"*" 			{ return new_symbol(sym.MUL, yytext()); }
"/" 			{ return new_symbol(sym.DIV, yytext()); }
"%" 			{ return new_symbol(sym.PCT, yytext()); }
"=" 			{ return new_symbol(sym.ASSIGN, yytext()); }
"==" 			{ return new_symbol(sym.EQUAL, yytext()); }
"!=" 			{ return new_symbol(sym.NOT_EQUAL, yytext()); }
">" 			{ return new_symbol(sym.GT, yytext()); }
">=" 			{ return new_symbol(sym.GTE, yytext()); }
"<" 			{ return new_symbol(sym.LS, yytext()); }
"<=" 			{ return new_symbol(sym.LSE, yytext()); }
"&&" 			{ return new_symbol(sym.AND, yytext()); }
"||" 			{ return new_symbol(sym.OR, yytext()); }
"++" 			{ return new_symbol(sym.PLUSPLUS, yytext()); }
"--" 			{ return new_symbol(sym.MINUSMINUS, yytext()); }
";" 			{ return new_symbol(sym.SEMICOLON, yytext()); }
"," 			{ return new_symbol(sym.COMMA, yytext()); }
"(" 			{ return new_symbol(sym.LPAREN, yytext()); }
")" 			{ return new_symbol(sym.RPAREN, yytext()); }
"[" 			{ return new_symbol(sym.LBRCK, yytext()); }
"]" 			{ return new_symbol(sym.RBRCK, yytext()); }
"{" 			{ return new_symbol(sym.LBRACE, yytext()); }
"}" 			{ return new_symbol(sym.RBRACE, yytext()); }
":" 			{ return new_symbol(sym.DBLDOT, yytext()); }
"." 			{ return new_symbol(sym.DOT, yytext()); }

"true" 			{ return new_symbol(sym.BOOL_CONST, new Boolean (yytext())); }
"false" 		{ return new_symbol(sym.BOOL_CONST, new Boolean (yytext())); }

[0-9]+  						{ return new_symbol(sym.NUMBER_CONST, new Integer (yytext())); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }
"'"[\040-\176]"'" 				{return new_symbol (sym.CHAR_CONST, new Character (yytext().charAt(1)));}

"//" 					{yybegin(COMMENT);}
<COMMENT> . 			{yybegin(COMMENT);}
<COMMENT> "\r\n" 		{ yybegin(YYINITIAL); }

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)); }










