package JavaLogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author esteban
 */
public class BDHandler {
    // super clase para hacer acceso a la base de datos.
    // permite declarar funciones y variables que se usan mucho
    // durante la ejecucion del web server
    
    public String direccion = "jdbc:mysql://127.0.0.1:3306/granero";
    public String user = "root";
    public String pass = "000012";
    public String cfname = "com.mysql.jdbc.Driver";
    
    Connection link;
    PreparedStatement pst;
    ResultSet rs;
    
    public Connection GetLink()// se obtiene un objeto de conexion a la base de datos
            throws ClassNotFoundException, SQLException{
        Class.forName(cfname);
        this.link = DriverManager.getConnection(direccion, user, pass);
        return link;
    }
    
    public PreparedStatement GetPst(String frase) // construye un pst basado en un query vallido de sql
            throws ClassNotFoundException, SQLException{
        this.GetLink();
        this.pst = (PreparedStatement) this.link.prepareStatement(frase);
        return this.pst;
    }
            
    public void cerrarQuery() throws SQLException{
        this.rs.close(); // se cierran los objetos relacionados con la coneccion
        this.pst.close();
        this.link.close();
    }
    
    public void CerrarUpdate() throws SQLException{
        this.pst.close();// se cierran los objetos relacionados con la conexion
        this.link.close();
    }
    
}
