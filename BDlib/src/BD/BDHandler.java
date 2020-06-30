
package BD;

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
public class BDHandler {
    
    // los metodos de esta superclase solo seran usadas por las clases hijas
    // solamente para obtener resultsets
    // en caso de hacer Update Queries se debe obtener el
    // Prepared statement en su respectivo BDH hijo.
    
    private final String direccionGranero="jdbc:mysql://127.0.0.1:3306/granero";
    private final String direccionInventarios="jdbc:mysql://127.0.0.1:3306/inventarios";
    private final String user="root";
    private final String pass="000012";
    private final String cfname="com.mysql.jdbc.Driver";
    private final String jlangc = "class java.lang.";
    
    public Connection link;
    public ResultSet rs;
    public PreparedStatement pst;
    
    public BDHandler(){
        
    }
    
    /**
     * Entrega un link a la base de datos granero. tener encuenta que la aplicacion
     * usa la basse de datos inventarios.
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public Connection GetConnectionGranero() throws ClassNotFoundException, SQLException { 
        Class.forName(this.getCfname());
        Connection lk = DriverManager.getConnection(getDireccionGranero(), getUser(), getPass());
        return lk;
    }
    
    /**
     * Entrega un link a la base de datos de Inventarios
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public Connection GetConnectionInventarios() throws ClassNotFoundException, SQLException {
        Class.forName(this.getCfname());
        Connection lk = DriverManager.getConnection(getDireccionInventarios(), getUser(), getPass());
        return lk;
    }
    
    /**
     * entrega un link a la base de datos de ventas
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public Connection GetConnectionVentas() throws ClassNotFoundException, SQLException {
        Class.forName(this.getCfname());
        Connection lk = DriverManager.getConnection(getDireccionGranero(), getUser(), getPass());
        return lk;
    }
    
    /**
     * Instancia el link de esta super clase con una conexion a la bd granero
     * y arma un prepared statement con el query especificado en el argumento.
     * @param query
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public void FormPstGranero(String query) throws ClassNotFoundException, SQLException {
        Class.forName(this.getCfname());
        Connection lk = DriverManager.getConnection(getDireccionGranero(), getUser(), getPass());
        this.link = lk;
        this.pst = (PreparedStatement) link.prepareStatement(query);
    }
    
    public void FormPstInventario(String query) throws ClassNotFoundException, SQLException {
        
        Class.forName(this.getCfname());
        Connection lk = DriverManager.getConnection(getDireccionInventarios(), getUser(), getPass());
        this.link = lk;
        this.pst = (PreparedStatement) link.prepareStatement(query);
    }
    
    public void FormPstVentas(String query) throws ClassNotFoundException, SQLException {
        Class.forName(this.getCfname());
        Connection lk = DriverManager.getConnection(getDireccionGranero(), getUser(), getPass());
        this.link = lk;
        this.pst = (PreparedStatement) link.prepareStatement(query);
    }
    
    
    public ResultSet HacerQuery(String query, ArrayList sets) throws ClassNotFoundException, SQLException{
        //se genera el prepared statement
        link = GetConnectionGranero();
        pst = (PreparedStatement) link.prepareStatement(query);
        
        // se termina de construir el query
        for (int j=0; j<sets.size(); j++){
            
            if(sets.get(j).getClass().equals(jlangc+"Double")) pst.setDouble(j+1, (Double) sets.get(j));
            else if(sets.get(j).getClass().equals(jlangc+"Integer")) pst.setInt(j+1, (Integer) sets.get(j));
            else if(sets.get(j).getClass().equals(jlangc+"String")) pst.setString(j+1,(String) sets.get(j));
            
        }
        
        rs = pst.executeQuery();
        return rs;
    }
    
    public ResultSet HacerQuerySoloString(String query, String[] key_words) throws ClassNotFoundException, SQLException{
        //se genera el prepared statement
        link = GetConnectionGranero();
        pst = (PreparedStatement) link.prepareStatement(query);
        // se termina de construir el query
        for (int j=0; j<key_words.length; j++){
            pst.setString(j+1, key_words[j]);
        }
        rs = pst.executeQuery();
        return rs;
    }
    
    public String getDireccionGranero() {
        return this.direccionGranero;
    }
    
    public String getDireccionInventarios(){
        return this.direccionInventarios;
    }
    
    public String getUser() {
        return this.user;
    }
    
    public String getPass() {
        return this.pass;
    }
    
    public String getCfname() {
        return this.cfname;
    }
    
    public void CerrarTodo() throws SQLException{
        rs.close();
        pst.close();
        link.close();
    }
    
    public void CerrarUpdate() throws SQLException{
        this.link.close();
        this.pst.close();
    }
    
}
