package compiler;

/**
 *
 * @author Gabriel Peres de Andrade 726517
 */

import AST.*;
import java.util.*;
import java.lang.Character;
import Lexer.*; 
import java.io.*;

public class Compiler {
    
    private SymbolTable symbolTable;
    private Lexer lexer;
    private CompilerError error;
    
    public void compile( char []input, PrintWriter outError ) 
    {

		symbolTable = new SymbolTable();
		error = new CompilerError( outError );
		lexer = new Lexer(input, error);
		error.setLexer(lexer);
		lexer.nextToken();
	}
}