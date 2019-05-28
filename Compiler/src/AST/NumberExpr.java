package AST;
/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class NumberExpr extends Expr {
    private int value;
    
    public NumberExpr( int value ) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}
