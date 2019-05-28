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
                        statList();
                        
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
        //Precisamos definir como será a classe parâmetro. Por enquanto retornarei NULL
 
        if (lexer.token.equals(Symbol.IDENT)){
            lexer.nextToken();
            if (lexer.token.equals(Symbol.DOISPONTOS)) {
                type();
            }
            else
                error.signal("Erro: Esperava um ':'");     
        }
        else
            error.signal("Erro: Esperava um identificador");
        return null;
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
            lexer.nextToken();
            statList.add(stat());
        }
        //Trata o erro se não abrir chaves
        else {
            error.signal("Erro: '{' esperado");
        }
        return null;
    }
    //Stat ::= AssignExprStat | ReturnStat | VarDecStat | IfStat | WhileStat
    
    private Stat stat(){
        //Verifica se é um if
        //IfStat ::= "if" Expr StatList [ "else" StatList ]
        if (lexer.token.equals(Symbol.IF)){
            lexer.nextToken();
            //chama o expr
            expr();
            lexer.nextToken();
            //chama o statlist
            statList();
            lexer.nextToken();
            //else é opcional, portanto não deve conter tratativa de erro
            if (lexer.equals(Symbol.ELSE)){
                lexer.nextToken();
                statList();
            }
        }
        //Verifica se é um while
        //WhileStat ::= "while" Expr StatList
        else if(lexer.token.equals(Symbol.WHILE)){
            lexer.nextToken();
            //chama o expr
            expr();
            lexer.nextToken();
            //chama o statList
            statList();
        }
        //verifica se é um varDecStat
        //VarDecStat ::= "var" Id ":" Type ";"
        //verifica se é um 'var'
        else if(lexer.token.equals(Symbol.VAR)){
            lexer.nextToken();
            //verifica se é um id
            if (lexer.token.equals(Symbol.IDENT)){
                lexer.nextToken();
                //verifica se é um ':'
                if (lexer.token.equals(Symbol.DOISPONTOS)){
                    lexer.nextToken();
                    //chama o type
                    type();
                    lexer.nextToken();
                    //verifica se é diferente de um ';'
                    if (!lexer.token.equals(Symbol.PONTOVIRGULA))
                        error.signal("Erro: Esperava ';'");
                }
                else
                    error.signal("Erro: Esperava ':'");
            }
            else
                error.signal("Error: Esperava um identificador");
        }
        //verifica se é um return
        //ReturnStat ::= "return" Expr ";"
        else if(lexer.token.equals(Symbol.RETURN)){
            lexer.nextToken();
            //chama o expr
            expr();
            lexer.nextToken();
            //verifica se não é um ';'
            if(!lexer.token.equals(Symbol.PONTOVIRGULA)){
                error.signal("Erro: Esperava ';'");
            }
        }
        
        //verifica se é um assignExprStat
        //AssignExprStat ::= Expr [ "=" Expr ] ";"
        //TODO
        
        //tratamento de erro 
        else
            error.signal("Erro: declaração incorreta");
        //trocar o retorno
        return null;
    }
}