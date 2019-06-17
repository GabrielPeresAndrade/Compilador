package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class ExprPrimary {
    //Atributos
    private String id;
    private FuncCall funcCall;
    private ExprLiteral exprLiteral;
     
    //Construtor
    public ExprPrimary(String id, FuncCall funcCall, ExprLiteral exprLiteral) {
        this.id = id;
        this.funcCall = funcCall;
        this.exprLiteral = exprLiteral;
    }
    
    public String getType () {
        return exprLiteral.getType();
    }
}
