package AST;
/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class BoolExpr extends Expr {
    
    public static BoolExpr True = new BoolExpr(true);
    public static BoolExpr False = new BoolExpr(false);
    private boolean value;
    
    public BoolExpr( boolean value ) {
        this.value = value;
    } 
}
