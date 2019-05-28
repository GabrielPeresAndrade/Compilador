package AST;

/**
 *
 * @author v-gamer
 */
public class VariableExpr extends Expr{
    private Variable v;
    public VariableExpr (Variable v)
    {
        this.v = v;
    }
}
