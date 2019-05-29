package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class WhileStat {
   private Expr expr;
   private StatList statList;
   
   public WhileStat (Expr expr, StatList statList) {
       this.expr = expr;
       this.statList = statList;
   }
}
