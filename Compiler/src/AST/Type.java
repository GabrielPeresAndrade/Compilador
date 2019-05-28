package AST;

/**
 *
 * @author Angelo Bezerra de Souza 726496
 * @author Gabriel Peres de Andrade  726517
 * @author Igor Inácio de Carvalho Silva 725804
 */
abstract public class Type {
    public Type( String name ) {
        this.name = name;
    }
    public static Type booleanType = new BooleanType();
    public static Type integerType = new IntegerType();
    public static Type stringType = new StringType();
    public static Type undefinedType = new UndefinedType();
    
    public String getName() {
        return name;
    }
    private String name;
}
