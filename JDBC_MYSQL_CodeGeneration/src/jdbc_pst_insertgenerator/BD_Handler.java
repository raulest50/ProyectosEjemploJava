/**
 * clase para leer los datos de la tabla de SQL.
 */
package jdbc_pst_insertgenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author esteban
 */
public class BD_Handler {
    
    /**
     * query para obtener la informacion de las columnas de una tabla dada
     * de mysql
     */
    public final String GET_TABLE_INFO = "show columns from ";
    
    
    /**
     * query para sacar los nombres de las tablas de una base de datos
     */
    public final String GET_TABLES_NAMES = "show tables from ";
    
    /**
     * nombre de la columna del result set 
     * donde se encuentra el nombre de la columna de la tabla
     */
    public final String COL_NOMBRE = "Field"; 
    
    /**
     * tipo de dato de la columna de la tabla
     */
    public final String COL_TIPO = "Type";
    
    /**
     * indica YES o NO dependiendo de si acepta valores nulos 
     */
    public final String COL_NULL = "Null";
    
    /**
     * puede estar vacia, contener PRI o MUL.
     */
    public final String COL_LLAVE_PRIM = "Key";
    
    /**
     * indica el valor por defecto de la columna
     */
    public final String COL_DEFAULT = "Default";
    
    /**
     * puede contener auto-increment
     */
    public final String COL_EXTRA = "Extra";
    
    /**
     * direccion a la base de datos
     */
    public String direccion="jdbc:mysql://127.0.0.1:3306/";
    
    /**
     * constantes para hacer conexion a la base de datos
     */
    public final String cfname="com.mysql.jdbc.Driver";
    public final String jlangc = "class java.lang.";
    
    
    public Connection link;
    public ResultSet rs;
    public PreparedStatement pst;
    
    public String user; // usuario de mysql
    public String pass; // password de mysql
    public String dbName; // nombre de la base de datos
    public String table; // tabla de la que se desea hacer insert

    public BD_Handler(String user, String pass, String schema) {
        this.user = user;
        this.pass = pass;
        this.dbName = schema;
        this.direccion = this.direccion + schema;
    }
    
    /**
     * Metodo que entrega un arraylist con los datos de mysql
     * @param tableName
     * @return 
     * @throws java.lang.ClassNotFoundException 
     * @throws java.sql.SQLException 
     */
    public ArrayList<Columna> GetTableInfo(String tableName) 
            throws ClassNotFoundException, SQLException{
        ArrayList<Columna> r = new ArrayList<>();
        
        this.SetConnection();
        
        this.pst = (PreparedStatement) this.link.prepareStatement(
                this.GET_TABLE_INFO + this.dbName + "." + tableName);
        
        this.rs = this.pst.executeQuery();
        
        while(this.rs.next()){
            r.add(new Columna(
                    rs.getString(this.COL_NOMBRE),
                    rs.getString(this.COL_TIPO),
                    rs.getString(this.COL_NULL),
                    rs.getString(this.COL_LLAVE_PRIM),
                    rs.getString(this.COL_DEFAULT),
                    rs.getString(this.COL_EXTRA)));
        }
        this.CerrarTodo();
        return r;
    }
    
    
    /**
     * metodo que obtiene os nombres de la tablas de la base de datos 
     * especificada en el constructor de esta clase.
     * @return 
     * @throws java.sql.SQLException 
     * @throws java.lang.ClassNotFoundException 
     */
    public ArrayList<String> GetTableNames() 
            throws SQLException, ClassNotFoundException{
        this.SetConnection();
        this.pst = (PreparedStatement) this.link.prepareStatement(this.GET_TABLES_NAMES + this.dbName);
        
        ArrayList<String> r = new ArrayList<>();
        this.rs = this.pst.executeQuery();
        while(this.rs.next()){
            r.add(this.rs.getString(1));
        }
        return r;
    }
    
    
    /**
     * metodo que construye un objeto tipo DataBaseSQL,
     * el cual encapsula, el nombre de la base de datos mysql que representa
     * y un arreglo de tablas, las cuales a su vez encapsulan el nombre de la
     * tabla que representan y un arreglo de columnas. Cada objeto columna
     * encapsula los datos relevantes de la columna para la generacion de codigo
     * que se hace con esta aplicacion.
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public DataBaseSQL GetDataBaseSQL() 
            throws SQLException, ClassNotFoundException{
        ArrayList<String> tbNames = this.GetTableNames();
        ArrayList<Tabla> Tables = new ArrayList<>();
        for(String tb : tbNames){
            Tables.add(new Tabla(tb, this.GetTableInfo(tb)));
        }
        DataBaseSQL db = new DataBaseSQL(dbName, Tables);
        return db;
    }
    
    
    // conexion a la bd
    public void SetConnection() 
            throws ClassNotFoundException, SQLException {
        Class.forName(this.cfname);
        this.link = DriverManager.getConnection(this.direccion, this.user, this.pass);
    }
    
    
    /**
     * se debe ejecutar al final de un metodo en que se use un query
     * @throws SQLException 
     */
    public void CerrarTodo() throws SQLException{
        rs.close();
        pst.close();
        link.close();
    }
    
    /**
     * se debe ejeccutar a final de un metodo en el que se haga un sql
     * update.
     * @throws SQLException 
     */
    public void CerrarUpdate() throws SQLException{
        this.link.close();
        this.pst.close();
    }
}
