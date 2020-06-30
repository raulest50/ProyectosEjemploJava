package BD.Handlers;

import BD.BDHandler;
import BD.Logica.validadorProveedor;
import Definiciones.SptProveedor;
import java.lang.reflect.MalformedParametersException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author esteban
 * ejecuta operaciones sobre la tabla de proveedores en la base de datos granero
 * 
 */
public class BDH_Proveedores extends BDHandler{
    
    public String QIProveedor = "INSERT INTO `granero`.`proveedores` "
            + "(`Codigo`, `direccion`, `vendedor`, `telefonos`, `email`, "
            + "`web`, `calificacion`, `fecha`, `nombre`, `fax`, `keywords`) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, now(), ?, ?, ?)",
            
            QEProveedor = "DELETE FROM `granero`.`proveedores` WHERE `Codigo`=?",
            
            QExCodigo = "select * from proveedores where Codigo=?",
            
            QBProveedor = "select telefonos, nombre, Codigo, fax, email, direccion, "
            + "vendedor, web, calificacion, keywords, fecha from proveedores where ",
            
            QMProveedor = "UPDATE `granero`.`proveedores` SET `Codigo`=?, "
            + "`direccion`=?, `vendedor`=?, `telefonos`=?, `email`=?, `web`=?, "
            + "`calificacion`=?, fecha=now(), `nombre`=?, `fax`=?, "
            + "`keywords`=? WHERE `Codigo`=?";
    
    
    
    public BDH_Proveedores(){
    }
    
    /**
     * permite ingresar un proveedor a la base de datos de proveedores
     * @param spp
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws MalformedParametersException 
     */
    public ArrayList IngresarProveedor(SptProveedor spp)
            throws SQLException, ClassNotFoundException, MalformedParametersException{
        validadorProveedor v = new validadorProveedor();
        boolean r = false; // ingresa a un proveedor a la base de datos
        ArrayList al = v.ingresoValido(spp);
        if( (boolean) al.get(0)){
            
            FormPstGranero(QIProveedor);
            pst.setString(1, spp.getCodigo());
            pst.setString(2 , spp.getDireccion());
            pst.setString(3, spp.getVendedor());
            pst.setString(4, spp.getTelefonos());
            pst.setString(5, spp.getEmail());
            pst.setString(6, spp.getPaginaWeb());
            pst.setInt(7, Integer.parseInt(spp.getCalificacion()));
            pst.setString(8, spp.getNombre());
            pst.setString(9, spp.getFax());
            pst.setString(10, spp.getkeywords());
            pst.executeUpdate();
            r=true;
            CerrarUpdate();
            
        }
        return al;
    }
    
    /**
     * permite eliminar in proveedor en la base de datos de proveedores
     * usando la primary key Codigo, que es unico y arbitrario para proveedor
     * @param Codigo
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws MalformedParametersException 
     */
    public boolean EliminarProveedor(String Codigo)
    throws ClassNotFoundException, SQLException, MalformedParametersException {
        boolean r = false;// elimina un proveedor de la base de datos
        FormPstGranero(QEProveedor);
        pst.setString(1, Codigo);
        pst.executeUpdate();
        CerrarUpdate();
        r = true;// r indica si ha ocurrido una excepcion antes de llegar al return de este metodo
        return r;
    }
    
    /**
     * permite modifcar un proveedor, requiere el codigo anterior (codigoA)
     * el codigo nuevo debe estar encapsulado en la contruccion de sppr.
     * retoruna un ArrayList que en la primera posicion
     * @param sppr
     * @param CodigoA
     * @return ArrayList
     * @throws ClassNotFoundException
     * @throws MalformedParametersException
     * @throws SQLException 
     */
    public ArrayList ModificarProveedor(SptProveedor sppr, String CodigoA)
            throws ClassNotFoundException, MalformedParametersException, SQLException{
        // modifica un proveedor en la base de datos
        boolean r = false;
        validadorProveedor val = new validadorProveedor();
        ArrayList al = new ArrayList();
        if(CodigoA.equals(sppr.getCodigo())){
            al = val.DatosInteres(sppr);
            if((boolean) al.get(0)){
                SetnDoPstMod(sppr, CodigoA);
                r=true;
            }
        }
        else{
            al = val.ingresoValido(sppr);
            if((boolean) al.get(0)){
                SetnDoPstMod(sppr, CodigoA);
                r=true;
            }
        }
        return al;
    }
    
    public ArrayList<SptProveedor> Rs2AList(ResultSet rs) throws SQLException{
        // se convierte el resultset en ArrayList< obj SimpleStringProperties >
        // formato necesario para los table view de java FX
        ArrayList<SptProveedor> Alist = new ArrayList<>();
        while (rs.next()) {
            String tels[] = rs.getString("telefonos").split("-");
            String nombre = rs.getString("nombre");
            String Codigo = rs.getString("Codigo");
            String fax = rs.getString("fax");
            String email = rs.getString("email");
            String direccion = rs.getString("direccion");
            String vendedor = rs.getString("vendedor");
            String web = rs.getString("web");
            String calificacion = Integer.toString(rs.getInt("calificacion"));
            String key = rs.getString("keywords");
            String UltMod = rs.getString("fecha");
            SptProveedor p = new SptProveedor(nombre, Codigo, fax, tels,
                    email, direccion, vendedor, web, calificacion, key, UltMod);
            Alist.add(p);
        }
        return Alist;
    }
    
    public boolean existe_codigo(String codigo){
        boolean r = false;// indica si el codigo ya se esta usando en la bd
        try{
            this.HacerQuerySoloString(QExCodigo, new String[] {codigo});
            if (rs.next()) r = true;
            super.CerrarTodo();
        }
        catch(SQLException | ClassNotFoundException ex){
            Logger.getLogger(BDH_Proveedores.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public ArrayList<SptProveedor> BuscarProveedor(String b, String cb) 
    throws SQLException, ClassNotFoundException, MalformedParametersException{
        // bussca un proveedor en la base de datos basado en unn criterio de busqueda
        ArrayList<SptProveedor> r = new ArrayList<>();
        String attr = "";
        String nQ = QBProveedor;
        // se realiza la operacion dependiendo del criterio de busqueda.
        switch ( cb){// cb es el criterio de busqueda
            case "Nombre":
                attr = "nombre ";
                break;
            case "Palabras Clave":
                attr = "keywords ";
                break;
            case "Codigo":
                attr = "Codigo ";
                break;
            case "Telefono":
                attr = "telefonos ";
                break;
        }
        
        String key_words[] = b.split(" "); // se termina de formar el query
        for (int j = 0; j < key_words.length; j++) {
            // segun el tamano de los parametros.
            if (j == (key_words.length - 1)) {
                nQ = nQ + attr + " like ?";
            }
            else {
                nQ = nQ + attr +" like ? and ";
            }
            key_words[j] = "%"+key_words[j]+"%";
        }
        
        HacerQuerySoloString(nQ, key_words);
        r = Rs2AList(rs);
        CerrarTodo();
        
        return r;
    }
    
    
    
    public void SetnDoPstMod(SptProveedor sppr, String CodigoA) 
            throws ClassNotFoundException, SQLException {
        FormPstGranero(QMProveedor);
        pst.setString(1, sppr.getCodigo());
        pst.setString(2, sppr.getDireccion());
        pst.setString(3, sppr.getVendedor());
        pst.setString(4, sppr.getTelefonos());
        pst.setString(5, sppr.getEmail());
        pst.setString(6, sppr.getPaginaWeb());
        pst.setInt(7, Integer.parseInt(sppr.getCalificacion()));
        pst.setString(8, sppr.getNombre());
        pst.setString(9, sppr.getFax());
        pst.setString(10, sppr.getkeywords());
        pst.setString(11, CodigoA);
        pst.executeUpdate();
        CerrarUpdate();
    }
    
}
