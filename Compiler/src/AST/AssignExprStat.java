package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class AssignExprStat {
    private Expr expr;
    private Expr expr2;
    
    public AssignExprStat (Expr expr, Expr expr2) {
        this.expr = expr;
        this.expr2 = expr2;
    }
}
