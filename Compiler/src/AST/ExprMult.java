package AST;
import java.util.*;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class ExprMult {
   //Atributos
   private ExprUnary exprUnary;
   private ExprUnary exprUnary2;
   
   //Construtor
   public ExprMult(ExprUnary exprUnary, ExprUnary exprUnary2) {
       this.exprUnary = exprUnary;
       this.exprUnary2 = exprUnary2;
   }
}
