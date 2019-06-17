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
    protected String id;
    protected ParamList paramList;
    protected Type type;
    protected StatList statList;
    
    //Construtor
    public Func (String id, ParamList paramList, Type type, StatList statList) {
        this.id = id;
        this.paramList = paramList;
        this.type = type;
        this.statList = statList;
    }

    public Func(String id) {
        this.id = id;
    }

    public ParamList getParamList() {
        return paramList;
    }

    public void setParamList(ParamList paramList) {
        this.paramList = paramList;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public StatList getStatList() {
        return statList;
    }

    public void setStatList(StatList statList) {
        this.statList = statList;
    }
}
