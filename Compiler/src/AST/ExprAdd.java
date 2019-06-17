package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class ExprAdd {
   //Atributos
   private ArrayList<ExprMult> exprsMult = new ArrayList();
    
   //Construtor
   public ExprAdd (ArrayList<ExprMult> exprsMult) {
       this.exprsMult = exprsMult;
   }
   
    public ArrayList<String> getTypes () {
        ArrayList<String>arrayTypes = new ArrayList(); 
       for (ExprMult exprsMult1 : this.exprsMult) {
           for (int j = 0; j < exprsMult1.getTypes().size(); j++) {
               arrayTypes.add(exprsMult1.getTypes().get(j));
           }
       }
        return arrayTypes;
    }
}
