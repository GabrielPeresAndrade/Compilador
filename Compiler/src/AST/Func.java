package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class Func {
    //Atributos
    private String id;
    private ParamList paramList;
    private Type type;
    private StatList statList;
    
    //Construtor
    public Func (String id, ParamList paramList, Type type, StatList statList) {
        this.id = id;
        this.paramList = paramList;
        this.type = type;
        this.statList = statList;
    }
}
