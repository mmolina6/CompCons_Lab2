import java.io.*;

/**
 * Math Expression Scanner driver program
 * Name: Maria Alejandra Molina
 * Date: Feb 14, 2023
 *
 */
public class MathExprScanner implements ParserTokens
{
	public MathExprScanner() throws IOException
	{
		scan();
	}

	public void scan() throws IOException
	{
		// Create the scanner object
		InputStreamReader in = new InputStreamReader(System.in);
		Yylex lexer = new Yylex(in);

		String expr = ""; // gather complete scanned expression
		
		// Flags to report error or more tokens available
		boolean hasError = false;
		boolean hasToken = true;
		boolean EndOfExpr = false; 
		boolean startofExpr = true; 
		boolean PrevOperator = false; 
		
		//Counters for Parenthesis & Double Operator
		int leftParen = 0; 
		int rightParen = 0;   
		int lastToken = 0; 
		
		while (hasToken)
		{
			// Get next token
			int token = lexer.yylex();
			hasToken = token != Yylex.YYEOF; // was end of file reached?
					
			// Gather up the current expression from each token
			expr += lexer.yytext() + " ";

			// ADD MORE BELOW to display all of the tokens your scanner recognizes
			switch (token)
			{
			case NUMBER:
				System.out.println(lexer.yytext() + " NUMBER " + lexer.value);
				EndOfExpr = false;
				lastToken = NUMBER; 
				break;
			case ADDOP: 
				System.out.println(lexer.yytext() + " ADDOP " + "");
				
				if (lastToken == ADDOP | lastToken == SUBOP | lastToken == MULOP | lastToken == DIVOP) { 
					PrevOperator = true; //sets the boolean true if there are two operators in a row 
					hasError = true; 
				}
				
				EndOfExpr = true; //sets the boolean as true so that an error will be returned if the expression ends with +
				lastToken = ADDOP;  //sets lastToken as ADDOP again
				break;
			case SUBOP: 
				System.out.println(lexer.yytext() + " SUBOP " + "");
				
				if (lastToken == SUBOP | lastToken == ADDOP | lastToken == MULOP | lastToken == DIVOP) { 
					PrevOperator = true; 
					hasError = true;  
				}
				EndOfExpr = true; 
				lastToken = SUBOP;
				break;
			case MULOP:
				System.out.println(lexer.yytext() + " MULOP " + "");
				
				if (lastToken == ADDOP | lastToken == SUBOP | lastToken == MULOP | lastToken == DIVOP) { 
					PrevOperator = true; 
					hasError = true; 
				}
				EndOfExpr = true;
				lastToken = MULOP; 
				break;
			case DIVOP:
				System.out.println(lexer.yytext() + " DIVOP " + "");
				
				if (lastToken == ADDOP | lastToken == SUBOP | lastToken == MULOP | lastToken == DIVOP) { 
					PrevOperator = true;
					hasError = true; 
				}
				EndOfExpr = true;
				lastToken = DIVOP; 
				break;
			case LPAR: 
				leftParen++;
				System.out.println(lexer.yytext() + " LPAR " + "");
				break;
			case RPAR:
				rightParen++; 
				System.out.println(lexer.yytext() + " RPAR " + "");
				break;
			case error:
				hasError = true;
				System.out.println("ERROR");
				break;
			case NEWLINE:
			case ENDINPUT:
			default: 
				//Given a certain condition, prints out the error message	
				if (leftParen != rightParen) { 
					System.out.println("Error: Mismatched Parenthesis: Left: " + leftParen + ", Right: " + rightParen); 
					hasError = true; 
				}
				if (PrevOperator == true) { 
					System.out.println("Error: Two Operators in a row!"); 
				}
				
				if (EndOfExpr == true) { 
					System.out.println("Error: Invalid Expression: Expression can't end in an operator!"); 
					hasError = true;
				} 
				if (hasError) { 
					System.out.println(expr.strip() + ": REJECT! :("); 
				}
				else { 
					System.out.println( expr.strip() + ": ACCEPT! :)"); 
				}
				System.out.println("--------------------------");
				expr = "";
				hasError = false;
				
				//Resets all the counters/booleans
				leftParen = 0; 
				rightParen = 0;
				PrevOperator = false;  
				lastToken = 0;
				break;

			}
		}
	}
	
	
	public static void main(String[] args) throws IOException
	{
		new MathExprScanner();
	}
}
