package Lexer;

/**
 *
  * @author Gabriel Peres de Andrade 726517
 */
public class Lexer {
	// contains the keywords
	static private Hashtable<String, Symbol> keywordsTable;
	static 
	{
		keywordsTable = new Hashtable<String, Symbol>();
		keywordsTable.put( "var", Symbol.VAR );
		keywordsTable.put( "if", Symbol.IF );
		keywordsTable.put( "else", Symbol.ELSE );
		keywordsTable.put( "Int", Symbol.INTEGER );
		keywordsTable.put( "Boolean", Symbol.BOOLEAN );
		keywordsTable.put( "String", Symbol.STRING );
		keywordsTable.put( "true", Symbol.TRUE );
		keywordsTable.put( "false", Symbol.FALSE );
		keywordsTable.put( "and", Symbol.AND );
		keywordsTable.put( "or", Symbol.OR );
		keywordsTable.put( "function", Symbol.FUNCTION );
		keywordsTable.put( "while", Symbol.WHILE );
		keywordsTable.put( "return", Symbol.RETURN );
	}
	public Symbol token;
	
	private String stringValue;
	private int numberValue;
	private char charValue;
	private int tokenPos;
	// input[lastTokenPos] is the last character of the last token found
	private int lastTokenPos;
	// input[beforeLastTokenPos] is the last character of the token before the last token found
	private int beforeLastTokenPos;
	// program given as input - source code
	private char []input;
	private int lineNumber;
	private CompilerError error;
	private static final int MaxValueInteger = 2147483647;

	public Lexer( char []input, CompilerError error ) {
		this.input = input;
		// add an end-of-file label to make it easy to do the lexer
		input[input.length - 1] = ’\0’;
		// number of the current line
		lineNumber = 1;
		tokenPos = 0;
		lastTokenPos = 0;
		beforeLastTokenPos = 0;

		this.error = error;
	} 

 	public void nextToken() {

		char ch;

		while ( (ch = input[tokenPos]) == ’ ’ || ch == ’\r’ || ch == ’\t’ || ch == ’\n’) 
		{
			// count the number of lines
			if ( ch == ’\n’)
				lineNumber++;
			tokenPos++;
		}
		if ( ch == ’\0’)
			token = Symbol.EOF;
		else
			if ( input[tokenPos] == ’/’ && input[tokenPos + 1] == ’/’ ) {
				// comment found
				while ( input[tokenPos] != ’\0’&& input[tokenPos] != ’\n’ )
					tokenPos++;
				nextToken();
			}
		else 
		{
			if ( Character.isLetter( ch ) ) {
				// get an identifier or keyword
				StringBuffer ident = new StringBuffer();
				while ( Character.isLetter( input[tokenPos] ) || Character.isDigit( input[tokenPos] ) ) {
					ident.append(input[tokenPos]);
					tokenPos++;
				}
				stringValue = ident.toString();
				// if identStr is in the list of keywords, it is a keyword !
				Symbol value = keywordsTable.get(stringValue);
				if ( value == null )
					token = Symbol.IDENT;
				else
					token = value;
			}
			else 
			{
				if ( Character.isDigit( ch ) ) 
				{
					// get a number
					StringBuffer number = new StringBuffer();
					while ( Character.isDigit( input[tokenPos] ) ) 
					{
						number.append(input[tokenPos]);
						tokenPos++;
					}
					token = Symbol.NUMBER;
					try {
						numberValue = Integer.valueOf(number.toString()).intValue();
					} catch ( NumberFormatException e ) {
						error.signal("Number out of limits");
					}
					if ( numberValue >= MaxValueInteger )
						error.signal("Number out of limits");
				}
				else 
				{
					tokenPos++;
					switch ( ch ) 
					{
						case ’+’ :
							token = Symbol.PLUS;
						break;
						case ’-’ :
							token = Symbol.MINUS;
						break;
						case ’*’ :
							token = Symbol.MULT;
						break;
						case ’/’ :
							token = Symbol.DIV;
						break;
						case ’%’ :
							token = Symbol.REMAINDER;
						break;
						case ’<’ :
							if ( input[tokenPos] == ’=’ ) {
								tokenPos++;
								token = Symbol.LE;
							}
							else 
								if ( input[tokenPos] == ’>’ ) {
									tokenPos++;
									token = Symbol.NEQ;
								}
						else
							token = Symbol.LT;
						break;
						case ’>’ :
							if ( input[tokenPos] == ’=’ ) {
								tokenPos++;
								token = Symbol.GE;
							}
							else
								token = Symbol.GT;
						break;
						case ’=’ :
							if ( input[tokenPos] == ’=’ ) {
								tokenPos++;
								token = Symbol.EQ;
							}
						else
							token = Symbol.ASSIGN;
						break;
						case ’(’ :
							token = Symbol.LEFTPAR;
						break;
						case ’)’ :
							token = Symbol.RIGHTPAR;
						break;
						case ’,’ :
							token = Symbol.COMMA;
						break;
						case ’;’ :
							token = Symbol.SEMICOLON;
						break;
						case ’:’ :
							token = Symbol.COLON;
						break;
						case ’\’’ :
							token = Symbol.CHARACTER;
							charValue = input[tokenPos];
							tokenPos++;
							if ( input[tokenPos] != ’\’’ )
								error.signal("Illegal literal character" + input[tokenPos-1] );
							tokenPos++;
						break;
						// the next four symbols are not used by the language
						// but are returned to help the error treatment
						case ’{’ :
							token = Symbol.CURLYLEFTBRACE;
						break;
						case ’}’ :
							token = Symbol.CURLYRIGHTBRACE;
						break;
						case ’[’ :
							token = Symbol.LEFTSQBRACKET;
						break;
						case ’]’ :
							token = Symbol.RIGHTSQBRACKET;
						break;
						default :
							error.signal("Invalid Character: ’" + ch + "’");
					}
				}
			}
		}
		beforeLastTokenPos = lastTokenPos;
		lastTokenPos = tokenPos - 1;
	}   
}
