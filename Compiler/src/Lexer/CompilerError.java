package Lexer;

import java.io.*;

public class CompilerError {

    private Lexer lexer;
    private PrintWriter out;
    private boolean thereWasAnError;
    private String arquivo;

    public CompilerError( PrintWriter out ,String arquivo) 
    {
        // output of an error is done in out
        this.out = out;
        this.arquivo = arquivo;
        thereWasAnError = false;
    }
    
    public void setLexer( Lexer lexer ) 
    {
        this.lexer = lexer;
    }
    
    public boolean wasAnErrorSignalled() 
    {
        return thereWasAnError;
    }
    
    public void show( String strMessage ) 
    {
        show( strMessage, true );
    }
    
    public void show( String strMessage, boolean goPreviousToken ) 
    {
        // is goPreviousToken is true, the error is signalled at the line of the
        // previous token, not the last one.
        if ( goPreviousToken ) 
        {
            out.print("\n"+this.arquivo +" : " + lexer.getLineNumberBeforeLastToken() + " : ");
            out.println( strMessage );
            out.println( lexer.getLineBeforeLastToken() );
        }
        else 
        {
            out.print("\n"+this.arquivo + " :" + lexer.getLineNumber() + " : ");
            out.println( strMessage );
            out.println(lexer.getCurrentLine());
        }
        out.flush();
        if ( out.checkError() )
            System.out.println("Error in signaling an error");
        thereWasAnError = true;
    }
    
    public void signal( String strMessage ) 
    {
        show(strMessage );
        out.flush();
        thereWasAnError = true;
        throw new RuntimeException();
    }
}