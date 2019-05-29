package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class StatList {
    private ArrayList<Stat> statArray = new ArrayList();
    
    public StatList (ArrayList<Stat> statArray) {
        this.statArray = statArray;
    }
}
