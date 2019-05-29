package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class Stat {
    private AssignExprStat assignExprStat;
    private ReturnStat returnStat;
    private VarDecStat varDecStat;
    private IfStat ifStat;
    private WhileStat whileStat;
    
    public Stat (AssignExprStat assingExprStat, ReturnStat retrunStat, VarDecStat varDecStat, IfStat ifstat, WhileStat whileStat) {
        this.assignExprStat = assignExprStat;
        this.returnStat = returnStat;
        this.varDecStat = varDecStat;
        this.ifStat = ifStat;
        this.whileStat = whileStat;
    }
}
