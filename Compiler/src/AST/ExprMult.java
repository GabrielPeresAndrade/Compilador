package AST;
import java.util.*;
import Lexer.Symbol;
/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade 726517
 * @author Igor Inácio de Carvalho Silva 725804

 */
public class ExprMult {
   //Atributos
   private ArrayList<ExprUnary> exprUnary = new ArrayList<>();
   private ArrayList<Symbol> symbol = new ArrayList<>();
   //Construtor
   public ExprMult(ArrayList<ExprUnary> exprUnary,ArrayList<Symbol>symbol) {
       this.exprUnary = exprUnary;
       this.symbol = symbol;
   }
}
