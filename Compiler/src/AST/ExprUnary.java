package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class ExprUnary {
    //Atributos
    private ExprPrimary exprPrimary;
    
    //Construtor
    public ExprUnary (ExprPrimary exprPrimary) {
        this.exprPrimary = exprPrimary;
    }
}
