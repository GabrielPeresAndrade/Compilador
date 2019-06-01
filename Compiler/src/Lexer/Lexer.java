package Lexer;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804
 */
import java.util.*;

public class Lexer {
    // contains the keywords
    static private Hashtable<String, Symbol> keywordsTable;
    static 
    {       
        keywordsTable = new Hashtable();
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
    private int lastTokenPos;
    private int beforeLastTokenPos;
    private char []input;
    private int lineNumber;
    private CompilerError error;
    private static final int MaxValueInteger = 2147483647;

    public Lexer( char []input, CompilerError error ) 
    {
            this.input = input;
            // add an end-of-file label to make it easy to do the lexer
            input[input.length - 1] = '\0';
            // number of the current line
            lineNumber = 1;
            tokenPos = 0;
            lastTokenPos = 0;
            beforeLastTokenPos = 0;

            this.error = error;
    } 

    public void nextToken() 
    {

        char ch;

        while ( (ch = input[tokenPos]) == ' ' || ch == '\r' || ch == '\t' || ch == '\n') 
        {
            // count the number of lines
            if ( ch == '\n')
                lineNumber++;
            tokenPos++;
        }
        if ( ch == '\0')
            token = Symbol.EOF;
        else
            if ( input[tokenPos] == '/' && input[tokenPos + 1] == '/' ) {
                // comment found
                while ( input[tokenPos] != '\0'&& input[tokenPos] != '\n' )
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
                        case '+' :
                            token = Symbol.PLUS;
                        break;
                        case '-' :
                            switch (input[tokenPos]) 
                            {
                                case '>':
                                    tokenPos++;
                                    token = Symbol.ARROW;
                                break;
                                default:
                                    token = Symbol.MINUS;
                                break;
                            }
                        break;
                        case '*' :
                            token = Symbol.MULT;
                        break;
                        case '/' :
                            token = Symbol.DIV;
                        break;
                        case '<' :
                            switch (input[tokenPos]) 
                            {
                                case '=':
                                    tokenPos++;
                                    token = Symbol.LE;
                                break;
                                default:
                                    token = Symbol.LT;
                                break;
                            }
                        break;
                        case '>' :
                            if ( input[tokenPos] == '=' ) {
                                tokenPos++;
                                token = Symbol.GE;
                            }
                            else
                                token = Symbol.GT;
                        break;
                        case '=' :
                            if ( input[tokenPos] == '=' ) {
                                tokenPos++;
                                token = Symbol.EQ;
                            }
                            else
                                token = Symbol.ASSIGN;
                        break;
                        case '!' :
                            if ( input[tokenPos] == '=' ) {
                                tokenPos++;
                                token = Symbol.NEQ;
                            }
                            else
                                System.out.println("AAA tem um '!' do nada");
                        break;
                        case '(' :
                            token = Symbol.ABREPAR;
                        break;
                        case ')' :
                            token = Symbol.FECHAPAR;
                        break;
                        case ',' :
                            token = Symbol.VIRGULA;
                        break;
                        case ';' :
                            token = Symbol.PONTOVIRGULA;
                        break;
                        case ':' :
                            token = Symbol.DOISPONTOS;
                        break;
                        case '"' :
                            token = Symbol.CHARACTER;
                            StringBuffer ident = new StringBuffer();
                            ident.append('"');
                            while ( input[tokenPos] != '"' ) {
                                if(input[tokenPos] == '\0')
                                {
                                    error.signal("String não terminou");
                                }
                                ident.append(input[tokenPos]);
                                tokenPos++;
                            }
                            ident.append('"');
                            tokenPos++;
                            stringValue = ident.toString();
                            // if identStr is in the list of keywords, it is a keyword !
                            Symbol value = keywordsTable.get(stringValue);

                        break;
                        // the next four symbols are not used by the language
                        // but are returned to help the error treatment
                        case '{' :
                            token = Symbol.ABRECHAVE;
                        break;
                        case '}' :
                            token = Symbol.FECHACHAVE;
                        break;
                        default :
                            error.signal("Invalid Character: '" + ch + "'");
                    }
                }
            }
        }
        beforeLastTokenPos = lastTokenPos;
        lastTokenPos = tokenPos - 1;
    }
    
    public void skipBraces() 
    {
        // skip any of the symbols { } ( )
        if ( token == Symbol.ABRECHAVE || token == Symbol.FECHACHAVE )
            nextToken();
        if ( token == Symbol.EOF )
            error.signal("Unexpected EOF");
    }
    
    public void skipPunctuation() 
    {
        // skip any punctuation symbols
        while ( token != Symbol.EOF &&( token == Symbol.DOISPONTOS || token == Symbol.VIRGULA || token == Symbol.PONTOVIRGULA) )
            nextToken();
        if ( token == Symbol.EOF )
            error.signal("Unexpected EOF");
    }
    
    public void skipTo( Symbol []arraySymbol ) 
    {
        // skip till one of the characters of arraySymbol appears in the input
        while ( token != Symbol.EOF ) {
            int i = 0;
            while ( i < arraySymbol.length )
                if ( token == arraySymbol[i] )
                    return;
                else
                    i++;
            nextToken();
        }
        if ( token == Symbol.EOF )
            error.signal("Unexpected EOF");
    }
    
    public void skipToNextStatement() 
    {
        while ( token != Symbol.EOF && token != Symbol.ELSE &&  token != Symbol.PONTOVIRGULA )
            nextToken();
        if ( token == Symbol.PONTOVIRGULA )
            nextToken();
    }
    
    public int getLineNumber() 
    {
        return lineNumber;
    }
    
    public int getLineNumberBeforeLastToken() 
    {
        return getLineNumber( beforeLastTokenPos );
    }
    
    private int getLineNumber( int index ) 
    {
    // return the line number in which the character input[index] is
        int i= 0, n= 1, size;
        size = input.length;
        while ( i < size && i < index ) 
        {
            if ( input[i] == '\n' )
                n++;
            i++;
        }
        return n;
    }
    
    public String getCurrentLine() 
    {
        return getLine(lastTokenPos);
    }
    
    public String getLineBeforeLastToken() 
    {
        return getLine(beforeLastTokenPos);
    }
    
    private String getLine( int index ) 
    {
        // get the line that contains input[index]. Assume input[index] is at a token, not a white space or newline
        int i = index;
        if ( i == 0 )
            i = 1;
        else
            if ( i >= input.length )
                i = input.length;
        StringBuffer line = new StringBuffer();
        // go to the beginning of the line
        while ( i >= 1 && input[i] != '\n' )
            i--;
        if ( input[i] == '\n' )
            i++;
        // go to the end of the line putting it in variable line
        while ( input[i] != '\0' && input[i] != '\n' && input[i] != '\r' ) 
        {
            line.append( input[i] );
            i++;
        }
        return line.toString();
    }
    
    public String getStringValue() 
    {
        return stringValue;
    }
    
    public int getNumberValue() 
    {
        return numberValue;
    }
    
    public char getCharValue() 
    {
        return charValue;
    }

}
