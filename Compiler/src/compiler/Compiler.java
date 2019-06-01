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
    
    private Lexer lexer;
    private CompilerError error;
    
    public Program compile( char []input, PrintWriter outError ) 
    {

        //Instacia classes
        error = new CompilerError( outError );
        lexer = new Lexer(input, error);
        error.setLexer(lexer);

        //Inicia a leitura dos tokens
        lexer.nextToken();
        
        //Inicia as chamadas da AST
        Program program = program();
        return program;
    }
    
    //Program ::= Func {Func}
    private Program program() {
        //Mostra a chamada desse nó (Temporário)
        System.out.println("Program");
        
        //Inicio da função
        ArrayList<Func> arrayFunc = new ArrayList();
        
        //Adiciona 0 ou mais funções ao vetor de funções
        while (!lexer.token.equals(Symbol.EOF)) {
            arrayFunc.add(func());
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
        if (lexer.token.equals(Symbol.FUNCTION)) {
            lexer.nextToken();
            //verificar se há um ID (Nome da função)
            if (lexer.token.equals(Symbol.IDENT)) {
                //Salva o nome da fução para enviar ao construtor de func
                id = lexer.getStringValue();//lexer.token
                lexer.nextToken();
                
                //Verifica se há parâmetros
                if (lexer.token.equals(Symbol.ABREPAR)) {
                    lexer.nextToken();
                    // Recebe lista de parâmetros
                    paramList = paramList();
                    
                    //Se o parenteses não foi fechado, mostra o erro
                    if (!lexer.token.equals(Symbol.FECHAPAR)) {
                        error.signal("Erro: Esperando ')'");
                    }
                    //Se o parênteses foi fechado, chama o nextToken
                    else {
                        
                        lexer.nextToken();
                    }
                }
                
                //Verifica se há declaração de tipo
                if (lexer.token.equals(Symbol.ARROW)) {
                    lexer.nextToken();
                    type = type();
                }
                
                //Recebe o statList
                statList = statList();    
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
        //Mostra a chamada desse nó (Temporário)
        System.out.println("paramList");
        
        //Declaração de variváeis
        ArrayList<ParamDec> paramList = new ArrayList();
        
        //Adiciona o parâmetro
        paramList.add(paramDec());

        //Enquanto houver parâmetros, adiciona-os ao vetor
        while (lexer.token.equals(Symbol.VIRGULA)) {
            lexer.nextToken();
            paramList.add(paramDec());
        }
        
        //Retorna o array de parâmetros
        return new ParamList(paramList);
    }
    
    //ParamDec ::= Id ":" Type
    private ParamDec paramDec () {    
        //Mostra a chamada desse nó (Temporário)
        System.out.println("paramDec");
        
        //Declaração de variáveis
        String id = "";
        Type type = null;
        
        //Se for o nome da variável
        if (lexer.token.equals(Symbol.IDENT)){
            id = lexer.getStringValue();
            lexer.nextToken();
            
            //Vefica se há os dois pontos
            if (lexer.token.equals(Symbol.DOISPONTOS)) {
                lexer.nextToken();
                type = type();
            }
            else {
                error.signal("Erro: Esperando um ':'");  
            }
        }
        //Se não houver um nome de variável
        else {
            error.signal("Erro: Esperando um 'identificador'");
        }
        
        return new ParamDec(id, type);
    }
    
    //Type ::= "Int" | "Boolean" | "String"
    private Type type () {        
        //Mostra a chamada desse nó (Temporário)
        System.out.println("Type");
        
        //Declaração de variáveis
        String name = "";
        
        switch (lexer.token) {
            case INTEGER :
                name = "Int";
            break;
            case BOOLEAN :
                name = "Boolean";
            break;
            case STRING :
                name = "String";
            break;
            default :
                error.signal("Erro: Tipo Inválido");
        }
        
        lexer.nextToken();
        
        return new Type(name);  
    }
    
    //StatList ::= "{"{Stat}"}"
    private StatList statList () {
        //Mostra a chamada desse nó (Temporário)
        System.out.println("statList");
        
        //Declaração de variáveis
        ArrayList<Stat> statList = new ArrayList();
        
        //Verfica se o token é um abre
        if (lexer.token.equals(Symbol.ABRECHAVE)) {
            lexer.nextToken();
            //Adiciona os stat na lista, se houver
            while(lexer.token.equals(Symbol.RETURN) ||
                  lexer.token.equals(Symbol.VAR) ||
                  lexer.token.equals(Symbol.IF) ||
                  lexer.token.equals(Symbol.WHILE) ||
                  lexer.token.equals(Symbol.IDENT)){ //IDENT É PARA QUANDO FOR ASSIGNEXPRSTAT
                statList.add(stat());
            }

            //Verifica se fechou as chaves
            if (!lexer.token.equals(Symbol.FECHACHAVE)) {
                error.signal("Erro: esperando um '}'");
            }
            lexer.nextToken();
        }
        //Se não for acusa erro
        else {
            error.signal("Erro: esperando um '{'");
        }
        return new StatList(statList);
    }
    
    //Stat ::= AssignExprStat | ReturnStat | VarDecStat | IfStat | WhileStat    
    private Stat stat(){
        //Mostra a chamada desse nó (Temporário)
        System.out.println("Stat");
                                        
        //Declaração de variáveis
        AssignExprStat assignExprStat = null;
        ReturnStat returnStat = null;
        VarDecStat varDecStat = null;
        IfStat ifStat = null;
        WhileStat whileStat = null;

        switch (lexer.token){
            case RETURN:
               returnStat = returnStat(); 
            break;
            case VAR:
                varDecStat = varDecStat();
            break;
            case IF:
                ifStat = ifStat();
            break;
            case WHILE:
                whileStat = whileStat();
            break;
            case IDENT:
                assignExprStat = assignExprStat();
            break;
            default: error.signal("Erro: comando inválido"); 
        }
        return new Stat(assignExprStat, returnStat, varDecStat, ifStat, whileStat);
    }
    
    //AssignExprStat ::= Expr [ "=" Expr ] ";"
    private AssignExprStat assignExprStat() {
        //Mostra a chamada desse nó (Temporário)
        System.out.println("assignExprStat");
        
        //Declaração de variáveis
        Expr expr = null;
        Expr expr2 = null;
        
        //Recebe o primeiro expr
        expr = expr();
        
        //Verfica se há uma atribuição;
        if (lexer.token.equals(Symbol.ASSIGN)) {
            lexer.nextToken();
            expr2 = expr();
        }
        
        //Verfica se há o ;
        if (!lexer.token.equals(Symbol.PONTOVIRGULA)) {
            error.signal("Erro: esperando ';'");
        }
        lexer.nextToken();
        
        return new AssignExprStat(expr, expr2);
    }
    
    //ReturnStat ::= "return" Expr ";"
    private ReturnStat returnStat() {
        //Mostra a chamada desse nó (Temporário)
        System.out.println("returnStat");
        
        //Declaração de variáveis
        Expr expr = null;

        //Verifica se há o return
        if (lexer.token.equals(Symbol.RETURN)) {
            lexer.nextToken();
            expr = expr();
            //Verfica se há o ;
            if (!lexer.token.equals(Symbol.PONTOVIRGULA)) {
                error.signal("Erro: esperando ';'");
            }
            lexer.nextToken();
        }
        //Se não encontrar o "return"
        else {
            error.signal("Erro: esperando 'return'");
        }
        
        return new ReturnStat(expr);
    }
    
    //VarDecStat ::= "var" Id ":" Type ";"
    private VarDecStat varDecStat() {
        //Mostra a chamada desse nó (Temporário)
        System.out.println("varDecStat");
        
        //Declaração de variáveis
        String id = null;
        Type type = null;
        
        //Verifica se há o return
        if (lexer.token.equals(Symbol.VAR)) {
            lexer.nextToken();
            
            //Verifica se é um identificador
            if (lexer.token.equals(Symbol.IDENT)) {
                id = lexer.getStringValue();
                lexer.nextToken();
                
                //Verfica se há o dois pontos
                if (lexer.token.equals(Symbol.DOISPONTOS)) {
                    lexer.nextToken();
                    type = type();
                    
                    //Verfica se há o ;
                    if (!lexer.token.equals(Symbol.PONTOVIRGULA)) {
                        error.signal("Erro: esperando ';'");
                    }
                    lexer.nextToken();
                }
                //Se não tiver os dois pontos
                else {
                    error.signal("Erro: esperando ':'");
                }
            }
            //Se não for um identificador
            else {
                error.signal("Erro: nome de variável inválido");
            }
        }
        //Se não encontrar o "var"
        else {
            error.signal("Erro: esperando 'var'");
        }
        
        return new VarDecStat(id, type);
    }
    
    //IfStat ::= "if" Expr StatList [ "else" StatList ]
    private IfStat ifStat() {
        //Mostra a chamada desse nó (Temporário)
        System.out.println("ifStat");
        
        //Declaração de variáveis
        Expr expr = null;
        StatList statList = null;
        StatList statList2 = null;
        
        //Verifica se há o if
        if (lexer.token.equals(Symbol.IF)) {
            lexer.nextToken();
            
            expr = expr();
            statList = statList();
            //Verifica se há um "else"
            if (lexer.token.equals(Symbol.ELSE)) {
                lexer.nextToken();
                statList2 = statList();
            }
        }
        //Se não houver o if
        else {
            error.signal("Erro: esperando 'if'");
        }
        
        return new IfStat(expr, statList, statList2);
    }
    
    //WhileStat ::= "while" Expr StatList
    private WhileStat whileStat() {
        //Mostra a chamada desse nó (Temporário)
        System.out.println("whileStat");
        
        //Declaração de variáveis
        Expr expr = null;
        StatList statList = null;
        
        //Verifica se há o while
        if (lexer.token.equals(Symbol.WHILE)) {
            lexer.nextToken();
            
            expr = expr();
            statList = statList();
        }
        //Se não houver o if
        else {
            error.signal("Erro: esperando 'while'");
        }
        
        return new WhileStat(expr, statList);
    }
    
    //Expr ::= ExprAnd {”or”ExprAnd}
    private Expr expr () {
        //Mostra a chamada desse nó (Temporário)
        System.out.println("Expr");
        
        //Declaração de variáveis
        ArrayList<ExprAnd> exprsAnd = new ArrayList();
        
        //Recebe a primeira expr
        exprsAnd.add(exprAnd());
        
        //While houver "or" adiciona um novo exprAnd
        while(lexer.token.equals(Symbol.OR)) {
            lexer.nextToken();
            exprsAnd.add(exprAnd());
        }
    
        return new Expr(exprsAnd); 
    }
    
    //ExprAnd ::= ExprRel {”and”ExprRel}
    private ExprAnd exprAnd () {
        //Mostra a chamada desse nó (Temporário)
        System.out.println("ExprAnd");
        
        //Declaração de variáveis
        ArrayList<ExprRel> exprsRel = new ArrayList();
        
        //Recebe a primeira expr
        exprsRel.add(exprRel());

        //Verifca se há um "and"
        while(lexer.token.equals(Symbol.AND)) {
            lexer.nextToken();
            exprsRel.add(exprRel());
        }

        return new ExprAnd(exprsRel); 
    }
    
    //ExprRel ::= ExprAdd [ RelOp ExprAdd ]
    private ExprRel exprRel () {
        //Mostra a chamada desse nó (Temporário)
        System.out.println("ExprRel");
        
        //Declaração de variáveis
        ExprAdd exprAdd = null;
        RelOp relOp = null;
        ExprAdd exprAdd2 = null;
        
        //Recebe a primeira expr
        exprAdd = exprAdd();

        if (lexer.token.equals(Symbol.LT) ||
              lexer.token.equals(Symbol.LE) ||
              lexer.token.equals(Symbol.GT) ||
              lexer.token.equals(Symbol.GE) ||
              lexer.token.equals(Symbol.EQ) ||
              lexer.token.equals(Symbol.NEQ)) {
            relOp = relOp();
            exprAdd2 = exprAdd();
        }   

        return new ExprRel(exprAdd, relOp, exprAdd2); 
    }
    
    //RelOp ::= "<" | "<=" | ">" | ">=" | "==" | "!="
    private RelOp relOp () {
        //Mostra a chamada desse nó (Temporário)
        System.out.println("RelOp");
        
        //Declaração de variáveis
        String op = "";
        
        switch (lexer.token) {
            case LT :
                op = "<";
            break;
            case LE :
                op = "<=";
            break;
            case GT :
                op = ">";
            break;
            case GE :
                op = ">=";
            break;
            case EQ :
                op = "<";
            break;
            case NEQ :
                op = "<";
            break;
            default :
                error.signal("Erro: Operador Inválido");
            }
        lexer.nextToken();
        return new RelOp(op);  
    }
    
    //ExprAdd ::= ExprMult {(” + ” | ” − ”)ExprMult}
    private ExprAdd exprAdd() {
        //Mostra a chamada desse nó (Temporário)
        System.out.println("ExprAdd");
        
        //Declaração de variáveis
        ArrayList<ExprMult> exprsMult = new ArrayList();
        
        //Chama o primeiro expr mult
        exprsMult.add(exprMult());
        
        //Verifica se há um + ou - 
        while (lexer.token.equals(Symbol.PLUS) || lexer.token.equals(Symbol.MINUS)) {
            lexer.nextToken();
            exprsMult.add(exprMult());
        }
        
        return new ExprAdd(exprsMult);
    }   
    
    //ExprMult ::= ExprUnary {(” ∗ ” | ”/”)ExprUnary}
    private ExprMult exprMult() {
        //Mostra a chamada desse nó (Temporário)
        ArrayList<ExprUnary> arrayExprUnary = new ArrayList<>();
        ArrayList<Symbol> arraySymbol = new ArrayList<>();
        
        System.out.println("exprMult");
        
        //Declaração de variáveis
        ExprUnary exprUnary = null;
        Symbol symbol;
        
        //Chama o primeiro expr unary
        arrayExprUnary.add(exprUnary());

        //Verifica se há um + ou - 
        while (lexer.token.equals(Symbol.MULT) || lexer.token.equals(Symbol.DIV)) {
            symbol = lexer.token;
            lexer.nextToken();
            arraySymbol.add(symbol);
            arrayExprUnary.add(exprUnary());
        }
        
        return new ExprMult(arrayExprUnary,arraySymbol);
    } 
    
    //ExprUnary ::= [ ( "+" | "-" ) ] ExprPrimary
    private ExprUnary exprUnary() {
        //Mostra a chamada desse nó (Temporário)
        System.out.println("exprUnary");
        
        //Declaração de variáveis
        ExprPrimary exprPrimary = null;
        
        //Verfica se há um + ou -
        if (lexer.token.equals(Symbol.PLUS) || lexer.token.equals(Symbol.MINUS)) {
            lexer.nextToken();
        }
        
        exprPrimary = exprPrimary();
        
        return new ExprUnary(exprPrimary);
    } 
    
    //ExprPrimary ::= Id | FuncCall | ExprLiteral
    private ExprPrimary exprPrimary() {
        //Mostra a chamada desse nó (Temporário)
        System.out.println("exprPrimary");
        
        //Declaração de variáveis
        String id = "";
        FuncCall funcCall = null;
        ExprLiteral exprLiteral = null;
        
        //Se for um identificador, ou é apenas um identifcador ou é uma FuncCall
        if(lexer.token.equals(Symbol.IDENT)){
            //Salva o valor do identificador
            id = lexer.getStringValue();
            lexer.nextToken();
            
            //Se o próximo token for um "(" é uma chamada de função
            if (lexer.token.equals(Symbol.ABREPAR)) {
                funcCall = funcCall(id);
            }
        }
        //Se não for um id, etnão chamada o ExprLiteral
        else {
            exprLiteral = exprLiteral();
        }
        
        return new ExprPrimary(id, funcCall, exprLiteral);
    }
    
    //ExprLiteral ::= LiteralInt | LiteralBoolean | LiteralString
    private ExprLiteral exprLiteral () {
        
        //Mostra a chamada desse nó (Temporário)
        System.out.println("exprLiteral");
        
        //Declaração de variáveis
        int literalInt = -1;
        String literalString = "";
        LiteralBoolean literalBool = null;
        
        switch (lexer.token) {
            case NUMBER:
                literalInt = lexer.getNumberValue();
                lexer.nextToken();
            break;
            case VERDADEIRO:
                literalBool = literalBoolean();
            break;
            case FALSO:
                literalBool = literalBoolean();
            break;
            case CHARACTER:
                literalString = lexer.getStringValue();
                lexer.nextToken();
                //Chamada o proximo token para ver se fehcou as aspas
                
            break;
            default: error.signal("Erro: literal inválido");
        }
        
        return new ExprLiteral(literalInt, literalBool, literalString);
    }
    
    //LiteralBoolean ::= "true" | "false"
    private LiteralBoolean literalBoolean() {
        //Mostra a chamada desse nó (Temporário)
        System.out.println("literalBoolean");
        
        //Declaração de variáveis
        String bool = "";
        
        switch (lexer.token) {
            case VERDADEIRO :
                bool = "true";
            break;
            case FALSO :
                bool = "false";
            break;
            default :
                error.signal("Erro: Boolean inválido");
            }
        lexer.nextToken();
        return new LiteralBoolean(bool);         
    }
    
    //FuncCall ::= Id "(" [ Expr {”, ”Expr} ] ")"
    private FuncCall funcCall (String id)    {     
        //Mostra a chamada desse nó (Temporário)
        System.out.println("funcCall");
        
        //Declaração de variáveis
        ArrayList<Expr> exprs = new ArrayList();
       
        //Verifica se abriu o parenteses
        if (lexer.token.equals(Symbol.ABREPAR)) {
            lexer.nextToken();
            exprs.add(expr());

            //Verfica se há uma virgula
            while (lexer.token.equals(Symbol.VIRGULA)) {
                lexer.nextToken();
                exprs.add(expr());
            }
            
            //Verifica se fechou o parenteses
            if (!lexer.token.equals(Symbol.FECHAPAR)) {
                error.signal("Erro: esperando um ')'");
            }
            lexer.nextToken();
        }
        //Se não abriu parênteses, acusa o erro
        else {
            error.signal("Erro: esprando um '('");
        }
        
        return new FuncCall(id, exprs);
        
    }
}