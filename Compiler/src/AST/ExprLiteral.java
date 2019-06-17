package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class ExprLiteral {
    //Atributos
    private int literalInt;
    private LiteralBoolean literalBoolean;
    private String literalString;
    private String type;
    
    //Construtor
    public ExprLiteral (int literalInt, LiteralBoolean literalBoolean, String literalString, String type) {
        this.literalInt = literalInt;
        this.literalBoolean = literalBoolean;
        this.literalString = literalString;
        this.type = type;
    }
    
    public String getType () {
        return this.type;
    }
    
}
