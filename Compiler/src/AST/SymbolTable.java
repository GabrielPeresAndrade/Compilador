/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

/**
 *
 * @author Gabriel Peres de Andrade 726517
 */
import java.util.*;

public class SymbolTable {

    private Hashtable  localTable, globalTable;
    
    public SymbolTable() {
        localTable = new Hashtable();
        globalTable = new Hashtable();
    }
    
    public Object putInLocal( String key, Object value ) 
    {
        return localTable.put(key, value);
    }
    
    public Object getInLocal( Object key ) 
    {
        return localTable.get(key);
    }
    
    public void removeLocalIdent() 
    {
        localTable.clear();
    }
    
    public Object putInGlobal( String key, Object value ) 
    {
        return globalTable.put(key, value);
    }
    
    public Object getInGlobal( Object key ) 
    {
        return globalTable.get(key);
    }
    
    public Object get( String key ) 
    {
        Object result;
        if ( (result = localTable.get(key)) != null ) 
        {
            return result;
        }
        else 
        {
            return globalTable.get(key);
        }
    }

}
