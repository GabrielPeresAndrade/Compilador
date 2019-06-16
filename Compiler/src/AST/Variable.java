/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;
import java.util.*;

/**
 *
 * @author Gabriel Peres de Andrade 726517
 */
public class Variable {
    
    private String name;
    private Type type;
    
    public Variable( String name, Type type ) {
        this.name = name;
        this.type = type;
    }
    
    public Variable( String name ) {
        this.name = name;
    }
    
    public void setType( Type type ) {
        this.type = type;
    }
    
    public String getName() { 
        return name; 
    }
    
    public Type getType() {
        return type;
    }
}
