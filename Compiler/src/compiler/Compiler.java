package compiler;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade  726517
 * @author Igor Inácio de Carvalho Silva 725804
 */

import AST.Param;
import AST.Func;
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
        
        Program program = program();
    }
    //Program ::= Func {Func}
    private Program program() {
        ArrayList<Func> arrayFunc = new ArrayList();
        
        while (lexer.token == Symbol.FUNCTION )
            arrayFunc.add(func());
            
        if (arrayFunc.isEmpty()) {
            System.out.println("Erro: nenhuma função");
        }
        Program program = new Program(arrayFunc);
        return program;
    }
    //Func ::= "function" Id [ "(" ParamList ")" ] ["->" Type ] StatList
    //ParamList ::= ParamDec {”, ”ParamDec}
    //ParamDec ::= Id ":" Type
    
    private Func func () {
        if (lexer.token == Symbol.FUNCTION) {
            lexer.nextToken();
            //verificar se é um terminal Id
            if (lexer.token == Symbol.IDENT) {
                lexer.nextToken();
                //verificar se é um '('
                if (lexer.token == Symbol.ABREPAR) {
                    //Cria o array de parâmetros
                    ArrayList<Param> arrayParam = paramList();
                    
                    //Verifica se há ao menos um parmâetro
                    if (arrayParam.isEmpty()) {
                        error.signal("Erro: Nenhum parâmetro encontrado");
                    }
                    
                    //Verifica de fechou o parenteses
                    if (lexer.token == Symbol.FECHAPAR) {
                        lexer.nextToken();
                        
                        //Verfica se tem a seta de definição de tipo
                        if (lexer.token == Symbol.ARROW) {
                            lexer.nextToken();
                    
                            //Verifica se o tipo é valido
                            type();
                        }
                        
                        //Verfica se abriu chaves
                        //StatList();
                        
                    }
                    // Trata o erro "Esperava ")" e não encontrou"
                    else {
                        
                    }
                }
                //Trata o erro "Esperava "(" e não econtrou"
                else {
                    
                }
            }
            //trata erro "Esperava um identifcador(nome da função) e não econtrou"
            else {
            
            }
        }
        //Exibe o erro "Esperava "function" e não encontrou"
        else {
            
        }
        return null;
    }
    
    //ParamList ::= ParamDec {”, ”P aramDec}
    private ArrayList<Param> paramList () {
        //Declara o vetor de parametros
        ArrayList<Param> arrayParam = new ArrayList();
        
        //Adiciona o parâmetro
        arrayParam.add(paramDec());
        lexer.nextToken();

        //Enquanto houver parâmetros, adiciona-os ao vetor
        while (lexer.token == Symbol.VIRGULA) {
            arrayParam.add(paramDec());
        }
        
        //Retorna o array de parâmetros
        return arrayParam;
    }
    
    //ParamDec ::= Id ":" Type
    private Param paramDec () {
        
    }
    
    //Type ::= "Int" | "Boolean" | "String"
    private Type type () {
        Type result;
        
        switch ( lexer.token ) {
            case INTEGER :
                result = Type.integerType;
            break;
            case BOOLEAN :
                result = Type.booleanType;
            break;
            case STRING :
                result = Type.stringType;
            break;
            default :
                error.signal("Erro: Tipo Inválido");
                result = Type.integerType;
            }
        lexer.nextToken();
        return result;  
    }
    
    //StatList ::= "{” {Stat} ”}"
    private ArrayList<Stat> statList() {
        ArrayList<Stat> statList = new ArrayList();
        
        if (lexer.token == Symbol.ABRECHAVE) {
            statList.add(stat());
        }
        //Trata o erro se não abrir chaves
        else {
            error.signal("Erro: '{' esperado");
        }
    }
    
    private Stat stat () {
        Stat result;
        
        switch ( lexer.token ) {
            case RETURN :
                result = Stat.returnStatType;
            break;
            case IF :
                result = Stat.ifStatType;
            break;
            case WHILE :
                result = Stat.whileStatType;
            break;
            case ASSIGN :
                result = Stat.assignExprStatType;
            break;
            case VAR :
                result = Stat.varDecStatType;
            break; 
            default :
                error.signal("Erro: Tipo Inválido");
                result =  Stat.ifStatType;
            }
        lexer.nextToken();
        return result;  
    }
    
}