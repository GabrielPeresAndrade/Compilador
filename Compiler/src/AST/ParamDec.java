package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class ParamDec {
    private String id;
    private Type type;
    
    public ParamDec (String id, Type type) {
        this.id = id;
        this.type = type;
    }
}
