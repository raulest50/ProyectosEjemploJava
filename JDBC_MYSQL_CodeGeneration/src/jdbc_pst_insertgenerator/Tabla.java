
package jdbc_pst_insertgenerator;

import java.util.ArrayList;

/**
 *
 * @author esteban
 * 
 * clase que representa una tabla de sql
 */
public class Tabla {
    
    /**
     * nombre de la tabla
     */
    public String TableName;
    
    
    /**
     * columnas de la tabla.
     */
    ArrayList<Columna> cols;
    
    /**
     * sin el nombre de la base de datos asociada pues no es necesaria para
     * la logica
     * @param TableName
     * @param cols 
     */
    public Tabla(String TableName, ArrayList<Columna> cols){
        this.TableName = TableName;
        this.cols = cols;
    }
    
}
