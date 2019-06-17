package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class ExprRel {
    //Atributos
    private ExprAdd exprAdd;
    private RelOp relOp;
    private ExprAdd exprAdd2;
    
    //Construtor
    public ExprRel(ExprAdd exprAdd, RelOp relOP, ExprAdd exprAdd2) {
        this.exprAdd = exprAdd;
        this.relOp = relOp;
        this.exprAdd2 = exprAdd2;
    }
    
}
