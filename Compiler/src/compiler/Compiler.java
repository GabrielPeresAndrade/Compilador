package compiler;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade  726517
 * @author Igor Inácio de Carvalho Silva 725804
 */

import AST.*;
import java.util.*;
import Lexer.*; 
import java.io.*;

public class Compiler {
    
    private SymbolTable symbolTable;
    private Lexer lexer;
    private CompilerError error;
    
    public void compile( char []input, PrintWriter outError ) 
    {

        //Instacia classes
        symbolTable = new SymbolTable();
        error = new CompilerError( outError );
        lexer = new Lexer(input, error);
        error.setLexer(lexer);

        //Inicia a leitura dos tokens
        lexer.nextToken();
        
        //Inicia as chamadas da AST
        Program program = program();
    }
    
    //Program ::= Func {Func}
    private Program program() {
        //Mostra a chamada desse nó (Temporário)
        System.out.println("Program");
        
        //Inicio da função
        ArrayList<Func> arrayFunc = new ArrayList();
        
        //Adiciona 0 ou mais funções ao vetor de funções
        while (lexer.token == Symbol.EOF) {
            arrayFunc.add(func());
            lexer.nextToken();
        }
        
            
        //Se não houver nenhuma função, sinaliza o erro
        if (arrayFunc.isEmpty()) {
            error.signal("Erro: Nenhuma função encontrada");
        }
        return new Program(arrayFunc);
    }
    
    //Func ::= "function" Id [ "(" ParamList ")" ] ["->" Type ] StatList
    private Func func () {
        //Mostra a chamada desse nó (Temporário)
        System.out.println("Func");
        
        //Declaração de variáveis
        String id = "";
        ParamList paramList = null;
        Type type = null;
        StatList statList = null;
        
        // Verifica se há o "function" obrigatório
        if (lexer.token == Symbol.FUNCTION) {
            lexer.nextToken();
            //verificar se há um ID (Nome da função)
            if (lexer.token == Symbol.IDENT) {
                //Salva o nome da fução para enviar ao construtor de func
                id = "";//lexer.token
                lexer.nextToken();
                
                //Verifica se há parâmetros
                if (lexer.token == Symbol.ABREPAR) {
                    lexer.nextToken();
                    // Recebe lista de parâmetros
                    paramList = paramList();
                    
                    //Se o parenteses não foi fechado, mostra o erro
                    if (lexer.token != Symbol.FECHAPAR) {
                        error.signal("Erro: Esperando ')'");
                    }
                    //Se o parênteses foi fechado, chama o nextToken
                    else {
                        lexer.nextToken();
                    }
                }
                
                //Verifica se há declaração de tipo
                if (lexer.token == Symbol.ARROW) {
                    lexer.nextToken();
                    type = type();
                }
                
                //Recebe o statList
                statList = StatList();    
            }
            //Se não foi encontrado o nome da função
            else {
                error.signal("Erro Esperando um 'Identificador'");
            }
        }
        //Se não houver o "function" obrigatório
        else {
            error.signal("Erro: Esperando 'function'");
        }
        
        //Se todas as verificações foram validadas
        return new Func(id, paramList, type, statList);
    }
    
    //ParamList ::= ParamDec {”, ”P aramDec}
    private ParamList paramList () {
        //Declara o vetor de parametros
        ArrayList<ParamDec> arrayParam = new ArrayList();
        
        //Adiciona o parâmetro
        arrayParam.add(paramDec());
        lexer.nextToken();

        //Enquanto houver parâmetros, adiciona-os ao vetor
        while (lexer.token == Symbol.VIRGULA) {
            arrayParam.add(paramDec());
        }
        
        //Retorna o array de parâmetros
        return null;
    }
    
    //ParamDec ::= Id ":" Type
    private ParamDec paramDec () {
        //Precisamos definir como será a classe parâmetro. Por enquanto retornarei NULL
        String name = "";
        if (lexer.token.equals(Symbol.IDENT)){
            name = lexer.getStringValue();
            lexer.nextToken();
            if (lexer.token.equals(Symbol.DOISPONTOS)) {
                lexer.nextToken();
                Variable v = new Variable(name,type());
                symbolTable.putInLocal(name,v);
            }
            else
                error.signal("Erro: Esperava um ':'");     
        }
        else
            error.signal("Erro: Esperava um identificador");
        return new ParamDec(name, type());
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
        ArrayList<Stat> statList = null;
        
        if (lexer.token == Symbol.ABRECHAVE) {
            lexer.nextToken();
            statList = stat();
        }
        //Trata o erro se não abrir chaves
        else {
            error.signal("Erro: '{' esperado");
        }
        return statList;
    }
    //Stat ::= AssignExprStat | ReturnStat | VarDecStat | IfStat | WhileStat
    
    private ArrayList<Stat> stat(){
        //Verifica se é um if
        //IfStat ::= "if" Expr StatList [ "else" StatList ]
        ArrayList <Stat> stat=null;
        switch (lexer.token) {
            case IF:
                lexer.nextToken();
                //chama o expr
                orExpr();
                lexer.nextToken();
                //chama o statlist
               stat = statList();
                //else é opcional, portanto não deve conter tratativa de erro
                if (lexer.token.equals(Symbol.ELSE)){
                    lexer.nextToken();
                    statList();
                }
                break;
            case WHILE:
                lexer.nextToken();
                //chama o expr
                orExpr();
                lexer.nextToken();
                //chama o statList
                stat = statList();
                break;
            case VAR:
                lexer.nextToken();
                //verifica se é um id
                if (lexer.token.equals(Symbol.IDENT)){
                    lexer.nextToken();
                    //verifica se é um ':'
                    if (lexer.token.equals(Symbol.DOISPONTOS)){
                        lexer.nextToken();
                        //chama o type
                        Type t = type();
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
                break;
            case RETURN:
                lexer.nextToken();
                //chama o expr
                orExpr();
                lexer.nextToken();
                //verifica se não é um ';'
                if(!lexer.token.equals(Symbol.PONTOVIRGULA)){
                    error.signal("Erro: Esperava ';'");
                }   break;
            default:
                error.signal("Erro: declaração incorreta");
                break;
        }
        //trocar o retorno
        return stat;
    }
    private boolean checkBooleanExpr( Type left, Type right ) 
    {
        if ( left == Type.undefinedType || right == Type.undefinedType )
            return true;
        else
            return left == Type.booleanType && right == Type.booleanType;
    }
    
    private Expr orExpr()
    {
        //  Expr ::= ExprAnd {"or" ExprAnd}
        Expr left, right ;
        left = andExpr();
        while (lexer.token == Symbol.OR)
        {
            lexer.nextToken();
            right = andExpr();
            left = new CompositeExpr(left, Symbol.OR, right);
        }
        return left;
    }

    private Expr andExpr()
    {
        //  ExprAnd ::= ExprRel {"and" ExprRel}
        Expr left, right ;
        left = relExpr();
        while (lexer.token == Symbol.AND)
        {
            lexer.nextToken();
            right = relExpr();
            left = new CompositeExpr(left, Symbol.AND, right);
        }
        return left;
    }
    private Expr relExpr()
    {
        //  ExprRel ::= ExprAdd [ RelOp ExprAdd ]
        Expr left, right ;
        left = addExpr();
        if ((lexer.token == Symbol.LT)||
            (lexer.token == Symbol.LE)||
            (lexer.token == Symbol.GT)||
            (lexer.token == Symbol.GE)||
            (lexer.token == Symbol.EQ)||
            (lexer.token == Symbol.NEQ))             
        {
            lexer.nextToken();
            right = addExpr();
            left = new CompositeExpr(left, Symbol.AND, right);
        }
        return left;
    }
    private Expr addExpr()
    {
        // ExprAdd ::= ExprMult {(" + " | " - ")ExprMult}
        Symbol op;
        Expr left, right;
        left = multExpr();
        while ( (op = lexer.token) == Symbol.PLUS || op == Symbol.MINUS ) {
            lexer.nextToken();
            right = multExpr();
            left = new CompositeExpr( left, op, right );
        }
        return left;
    }
    
    private Expr multExpr()
    {
        // ExprMult ::= ExprUnary {("*" | "/")ExprUnary}
        Symbol op;
        Expr left, right;
        left = unaryExpr();
        while ( (op = lexer.token) == Symbol.MULT || op == Symbol.DIV ) {
            lexer.nextToken();
            right = unaryExpr();
            left = new CompositeExpr( left, op, right );
        }
        return left;
    }
    
    private Expr unaryExpr()
    {
        // ExprUnary ::= [ ( "+" | "-" ) ] ExprPrimary
        if ((lexer.token == Symbol.PLUS)||(lexer.token == Symbol.MINUS))
            lexer.nextToken();
        //DUVIDA A RESPEITO DO TIPO DO RETORNO
        return primExpr();    
    }
    
    private Expr primExpr() //PAREI NESSA FUNÇÃO FALTA PENSAR NOS RETORNOS E EXPRLITERAL
    {
        // ExprPrimary ::= Id | FuncCall | ExprLiteral
        if (lexer.token == Symbol.IDENT){
            //ID
            String name = lexer.getStringValue();
            lexer.nextToken();
            if(lexer.token == Symbol.ABREPAR )
            {
                //FUNCCALL
                lexer.nextToken();
                if( lexer.token != Symbol.FECHAPAR)
                {
                    orExpr();
                    lexer.nextToken();
                    while(lexer.token==Symbol.VIRGULA)
                    {
                        orExpr();
                        lexer.nextToken();
                    }
                    if(lexer.token != Symbol.FECHAPAR)
                        error.signal("Esperava um ')'");
                    lexer.nextToken();
                }
                else
                {
                    lexer.nextToken();
                }
                //return ;
            }
            else
            {
                Variable v = (Variable)symbolTable.get(name);
                if (v == null)
                    error.signal("Variavel NÃO criada");
                return new VariableExpr(v);
            }
        }
        else
        {
           //ExprLiteral
            return exprLiteral();
        }
        return new Expr();
    }
    private Expr exprLiteral()
    {
        return new Expr();
    }
}