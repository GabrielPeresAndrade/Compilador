package AST;
import java.util.*; import java.io.*;

public class Program {
    public Program( ArrayList<Func> procFunc ) {
        this.procFunc = procFunc;
    }
    
    private ArrayList<Func> procFunc;
}