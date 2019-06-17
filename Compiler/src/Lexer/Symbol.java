package Lexer;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public enum Symbol {
    FUNCTION("function"),
    IDENT("Ident"),
    ABREPAR("("),
    FECHAPAR(")"),
    ARROW("->"),
    VIRGULA(","),
    DOISPONTOS(":"),
    INTEGER("Int"),
    BOOLEAN("Boolean"),
    STRING("String"),
    ABRECHAVE("{"),
    FECHACHAVE("}"),
    ASSIGN("="),
    PONTOVIRGULA(";"),
    RETURN("return"),
    VAR("var"),
    IF("if"),
    ELSE("else"),
    WHILE("while"),
    OR("or"),
    AND("and"),
    LT("<"),
    LE("<="),
    GT(">"),
    GE(">="),
    EQ("=="),
    NEQ("!="),
    PLUS("+"),
    MINUS("-"),
    MULT("*"),
    DIV("/"),
    TRUE("true"),
    FALSE("false"),
    EOF("eof"),
    CHARACTER("character"),
    NUMBER("Number");
    
    Symbol(String name) 
    {
        this.name = name;
    }
    
    public String toString()
    {
        return name;
    }
    
    private String name;
}


