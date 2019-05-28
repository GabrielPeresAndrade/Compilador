package AST;
/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class StringExpr extends Expr {
    private String value;
    
    public StringExpr( String value ) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
