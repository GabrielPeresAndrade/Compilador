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
   private ExprMult exprMult;
   private ExprMult exprMult2;
    
   //Construtor
   public ExprAdd (ExprMult exptMult, ExprMult exptMult2) {
       this.exprMult = exprMult;
       this.exprMult2 = exprMult2;
   }
}
