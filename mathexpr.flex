/* Scanner specification file for simple math expressions. */
/* Name: Maria Alejandra Molina */
/* Date: Feb 14, 2023 */
%%

%unicode
%line
%column
%standalone

%{

	// Returns current line number
	public int getLine()
	{
		return yyline;
	}

	// Returns current column number
	public int getCol()
	{
		return yycolumn;
	}
	
	// Holds value of current token
	public int value;

%}


letter = [A-Za-z]
digit = [0-9]

%%

{digit}+			{ value = Integer.parseInt(yytext());
					  return ParserTokens.NUMBER; }
"+"					{ return ParserTokens.ADDOP; }
"-"					{return ParserTokens.SUBOP;}
"*"					{return ParserTokens.MULOP;}
"/" 				{return ParserTokens.DIVOP;}
"("					{return ParserTokens.LPAR;}
")"					{return ParserTokens.RPAR;}
[\r\n]+				{ return ParserTokens.NEWLINE; }
[ \t]+				{/* do nothing-skip whitespace */}
<<EOF>>				{ return ParserTokens.ENDINPUT; }
.					{ return ParserTokens.error; }
