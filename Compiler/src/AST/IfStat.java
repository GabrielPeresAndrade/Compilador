package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class IfStat {
    //Atriubtos
    private Expr expr;
    private StatList statList;
    private StatList statList2;
    
    //Construtor
    public IfStat (Expr expr, StatList statList, StatList statList2) {
        this.expr = expr;
        this.statList = statList;
        this.statList = statList2;
    }
}
