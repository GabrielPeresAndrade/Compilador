package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class ParamList {
   //Atriubtos
   private ArrayList<ParamDec> arrayParamDec = new ArrayList<ParamDec>();
   
   //Construtor
   public ParamList (ArrayList<ParamDec> arrayParamDec) {
       this.arrayParamDec = arrayParamDec;
   }
   public int getSize() {
        return arrayParamDec.size();
    }
}
