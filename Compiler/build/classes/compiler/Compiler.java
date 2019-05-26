package compiler;

/**
 *
 * @author Gabriel Peres de Andrade 726517
 */
public class Compiler {

    public Program compile( char []input, PrintWriter outError ) 
    {

		symbolTable = new Hashtable<String, Variable>();
		error = new CompilerError( outError );
		lexer = new Lexer(input, error);
		error.setLexer(lexer);
		lexer.nextToken();
		
		return program();
	}

}