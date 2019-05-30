package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class ExprAnd {
    //Atributos
    private ExprRel exprRel;
    private ExprRel exprRel2;
    
    //Construtor
    public ExprAnd (ExprRel exprRel, ExprRel exprRel2) {
        this.exprRel = exprRel;
        this.exprRel2 = exprRel2;
    }     
}
