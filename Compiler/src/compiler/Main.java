package compiler;

/**
 *
 * @author gabriel
 */

public class Main {
    public static void main( String []args ) {
        char []input = "(- (+ 5 4) 1)".toCharArray();
        Compiler compiler = new Compiler();
        compiler.compile(input);
    }
}
