package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class ExprAnd {
    //Atributos
    private ArrayList<ExprRel> exprsRel= new ArrayList();
    
    //Construtor
    public ExprAnd (ArrayList<ExprRel> exprsRel) {
        this.exprsRel = exprsRel;
    }     
}
