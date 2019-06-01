package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class FuncCall {
    //Atributos
    private String id;
    private ArrayList<Expr> exprs = new ArrayList();
    
    //Construtor
    public FuncCall(String id, ArrayList<Expr> exprs) {
        this.id = id;
        this.exprs = exprs;
    }
}
