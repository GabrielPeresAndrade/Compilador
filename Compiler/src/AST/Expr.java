package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class Expr {
    //Atributos
    private ExprAnd exprAnd;
    private ExprAnd exprAnd2;
    
    //Construtor
    public Expr (ExprAnd exprAnd, ExprAnd exprAnd2) {
        this.exprAnd = exprAnd;
        this.exprAnd2 = exprAnd2;
    }     
}
