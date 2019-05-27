package AST;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade  726517
 * @author Igor Inácio de Carvalho Silva 725804
 */
abstract public class Stat {
    
    public Stat(String name ) {
        this.name = name;
    }
    public static Stat assignExprStatType = new AssignExprStatType();
    public static Stat returnStatType = new ReturnStatType();
    public static Stat varDecStatType = new VarDecStatType();
    public static Stat ifStatType = new IfStatType();
    public static Stat whileStatType = new WhileStatType();
    
    public String getName() {
        return name;
    }
    private String name;
    
}
