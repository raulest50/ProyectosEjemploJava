package jdbc_pst_insertgenerator;

import java.util.ArrayList;

/**
 *
 * @author esteban
 * 
 * base de datos mysql
 */
public class DataBaseSQL {
    
    /**
     * nombre de la base de datos
     */
    public String dbName;
    
    /**
     * tablas que componen la base de datos.
     */
    public ArrayList<Tabla> tablas;

    
    public DataBaseSQL(String dbName, ArrayList<Tabla> tablas) {
        this.dbName = dbName;
        this.tablas = tablas;
    }
    
}
