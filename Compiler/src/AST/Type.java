
package AST;

/**
 *
 * @author gabriel
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
