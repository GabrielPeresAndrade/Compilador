package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class Program {
    //Atributos
    private ArrayList<Func> arrayFunc = new ArrayList<Func>();
    
    //Construtor
    public Program (ArrayList<Func> arrayFunc) {
        this.arrayFunc = arrayFunc;
    }
}
