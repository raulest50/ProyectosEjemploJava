
package BD.Handlers;

import BD.Logica.validadorProducto;
import Definiciones.SptProducto;
import java.lang.reflect.MalformedParametersException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author esteban
 */

public class BDH_Productos extends BD.BDHandler {
    
    validadorProducto val;
    
    final String 
            QBDescripcion="select * from productos where", //query para buscar por descripcion
            QBLastCod = "select * from productos where codigo like ?",//query para buscar por ultimos del coodigo
            QBFullCod = "select * from productos where codigo = ?", // query para buscar por codigo completo
            QIProducto = "insert into productos (codigo,descripcion,costo,iva,pvpublico,pvtienda,ultima_actualizacion,familia,stock)" // query para ingresar producto
                    + "values (?,?,?,?,?,?,now(),?,?) ",
            QMProducto = "update productos set descripcion=?" // query para modificar producto
                    + " , costo=?, iva=?, pvpublico=?, pvtienda=?, ultima_actualizacion=now(), "
            + "familia=?, stock=? where codigo=?",
            QEProducto = "delete from productos where codigo=?", // query para eliminar producto
            QNtotProductos = "select count(*) from `granero`.`productos`;",// query para obtener el numero total de productos
            Qm1_stock = "UPDATE `granero`.`productos` \n" +
                "SET `stock` = `productos`.`stock`-? \n" +
                "WHERE `codigo` = ?;", // query para restar n elementos de en stock del producto p
            QsetStock ="UPDATE `granero`.`productos` \n SET `stock` = ?  \n" +
                        "WHERE `codigo` = ?";
            
                 
    public BDH_Productos(){
        val = new validadorProducto();
    }
    
    public ArrayList<SptProducto> Rs2AList(ResultSet rs) throws SQLException{
        // se convierte el resultset en ArrayList< obj SimpleStringProperties >
        // formato necesario para los table view de java FX
        ArrayList<SptProducto> Alist = new ArrayList<>();
        while (rs.next()) {
            Alist.add(new SptProducto(
                    rs.getString("descripcion"), //descripcion
                    Integer.toString(rs.getInt("pvpublico")), // pvpublico
                    Integer.toString(rs.getInt("pvtienda")), // pvtienda
                    Integer.toString(rs.getInt("costo")), //costo
                    rs.getString("ultima_actualizacion"), //Lastup
                    rs.getString("codigo"), //codigo
                    Integer.toString(rs.getInt("iva")), // iva
                    rs.getString("familia"), // familia
                    Integer.toString(rs.getInt("stock")))); // stock
        }
        return Alist;
    }
    
    public ArrayList<SptProducto> BusquedaDescripcion(String Busqueda)
            throws ClassNotFoundException, SQLException, MalformedParametersException {
        // realiza la busqueda de productos por su descripcion
        String[] key_words = Busqueda.split(" ");
        String frase = QBDescripcion;
        
        for (int j = 0; j < key_words.length; j++) {
            if (j == (key_words.length - 1)) {
                frase = frase + " descripcion like ?";
            } else {
                frase = frase + " descripcion like ? and";
            }
            key_words[j] = "%"+key_words[j]+"%";
        }
        ArrayList<SptProducto> ListaResultado;
        rs = HacerQuerySoloString(frase, key_words);
        ListaResultado = Rs2AList(rs);
        CerrarTodo();
        return ListaResultado;
    }
    
    public ArrayList<SptProducto> BusquedaLastCod(String Busqueda)
            throws ClassNotFoundException, SQLException{
        rs = HacerQuerySoloString(QBLastCod,new String[] {"%"+Busqueda} );
        ArrayList<SptProducto> ListaResultado = new ArrayList<>();
        ListaResultado = Rs2AList(rs);
        CerrarTodo();
        return ListaResultado;
    }
    
    
    public ArrayList<SptProducto> BusquedaCodExact(String Busqueda)
            throws MalformedParametersException, SQLException, ClassNotFoundException{
        rs = HacerQuerySoloString(QBFullCod,new String[] {Busqueda} );
        ArrayList<SptProducto> ListaResultado;
        ListaResultado = Rs2AList(rs);
        CerrarTodo();
        return ListaResultado;
    }
    
    public boolean existe_codigo(String Codigo){
        // esta funcion revisa si existe ya un producto con el codigo de barras
        // especificado. Se usa principalmente en la codificacion de productos.
        boolean r = false;
        try {
            rs = HacerQuerySoloString(QBFullCod, new String[] {Codigo} );
            if (rs.next()) r = true; // Si en rs hay algun valor entonces r=true
            super.CerrarTodo(); // se cierra las conexiones, resultsets etc...
            
        } catch (MalformedParametersException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            r = true;
        }
        return r;
    }
    
    public ArrayList Codificar(SptProducto ssp)
            throws SQLException, MalformedParametersException, ClassNotFoundException{
        // al es la respuesta de la validacion. al contiene un booleano indicando el
        // resultado de la validacion y un String haciendo presicion al error.
        ArrayList al = val.datos_validos_ingreso_producto(ssp.getCodigo(), ssp.getDescripcion(),
                ssp.getCosto(), ssp.getIva(), ssp.getPvpublico(), ssp.getPvtienda(), ssp.getStock());
        if( (boolean) al.get(0)){
            FormPstGranero(QIProducto);
            pst.setString(1, ssp.getCodigo());
            pst.setString(2, ssp.getDescripcion());
            pst.setDouble(3, Double.parseDouble(ssp.getCosto()));
            pst.setDouble(4, Double.parseDouble(ssp.getIva()));
            pst.setDouble(5, Double.parseDouble(ssp.getPvpublico()));
            pst.setDouble(6, Double.parseDouble(ssp.getPvtienda()));
            pst.setString(7, ssp.getFamilia());
            pst.setInt(8, Integer.parseInt(ssp.getStock()));
            pst.executeUpdate();
            CerrarUpdate();
        }
        return al;
    }
    
    public ArrayList Modificar(SptProducto ssp, String CurrentCod)
            throws SQLException, MalformedParametersException, ClassNotFoundException{
        // Esta funcion se usa para modificar productos de la base de datos
        boolean r;
        ArrayList al;
        
        if(ssp.getCodigo().equals(CurrentCod)){ // si no se ha modificado el codigo de barras
            al = val.datos_validos_modificacion_producto_branch(ssp.getDescripcion(),
                    ssp.getCosto(), ssp.getIva(), ssp.getPvpublico(), ssp.getPvtienda(), ssp.getStock());
            if((boolean) al.get(0)){ // si el validador retorna exito en el primer lugar del arraylist entonces entra
                FormPstGranero(QMProducto);// se forma la conexion y el prepared statement
                pst.setString(1, ssp.getDescripcion());
                pst.setDouble(2, Double.parseDouble(ssp.getCosto()));
                pst.setDouble(3, Double.parseDouble(ssp.getIva()));
                pst.setDouble(4, Double.parseDouble(ssp.getPvpublico()));
                pst.setDouble(5, Double.parseDouble(ssp.getPvtienda()));
                pst.setString(6, ssp.getFamilia());
                pst.setInt(7, Integer.parseInt(ssp.getStock()));
                pst.setString(8, ssp.getCodigo());
                pst.executeUpdate(); // se ejecuta la modificacion
                CerrarUpdate(); // se cierra la conexion y el prepared statement
                r = true; // indica si la operacion fue exitosa o no
            }
        }
        
        else{ // si se modifico el codigo de barras
            al = val.datos_validos_ingreso_producto(ssp.getCodigo(), ssp.getDescripcion(),
                    ssp.getCosto(), ssp.getIva(), ssp.getPvpublico(), ssp.getPvtienda(), ssp.getStock());
            if((boolean) al.get(0)){ // si el validador retorna exito en el primer lugar del arraylist entonces entra
                FormPstGranero(QIProducto);// se forma la conexion y el prepared statement
                pst.setString(1, ssp.getCodigo());
                pst.setString(2, ssp.getDescripcion());
                pst.setDouble(3, Double.parseDouble(ssp.getCosto()));
                pst.setDouble(4, Double.parseDouble(ssp.getIva()));
                pst.setDouble(5, Double.parseDouble(ssp.getPvpublico()));
                pst.setDouble(6, Double.parseDouble(ssp.getPvtienda()));
                pst.setString(7, ssp.getFamilia());
                pst.setInt(8, Integer.parseInt(ssp.getStock()));
                pst.executeUpdate(); // se ejecuta la modificacion.
                CerrarUpdate(); // se cierra la conexion y el prepared statement.
                r = true; // indica si la operacion fue exitosa o no.
                if (r == true) Eliminar(CurrentCod);
            }
        }
        return al; // al[0] -> boolean que indica el exito de la operacion.
        //al[1] -> String que indica los errores ocurridos.
    }
    
    public void Eliminar(String CurrentCode)
    throws SQLException, MalformedParametersException, ClassNotFoundException{
        // Esta funcion se usa para eliminar productoos de la bbase de datos
        boolean r = false;
        FormPstGranero(QEProducto);// se forma la conexion y el prepared statement
        pst.setString(1, CurrentCode);
        pst.executeUpdate(); // se ejecuta la modificacion
        CerrarUpdate(); // se cierra la conexion y el prepared statement
        r = true; // indica si la operacion fue exitosa o no
    }
    
    /**
     * retorna el numero de filas de la tablal de productos
     * @return 
     * @throws java.lang.ClassNotFoundException 
     * @throws java.sql.SQLException 
     */
    public int NumeroTotalProductos()
        throws ClassNotFoundException, SQLException, MalformedParametersException {
        FormPstGranero(QNtotProductos);
        rs = pst.executeQuery();
        rs.next();
        int N = rs.getInt("count(*)");
        CerrarTodo();
        return N;
    }
    
    /**
     * resta N unidades de stock del producto identificado con codigo.
     * @param codigo
     * @param N
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public void RestarDeStock(String codigo, int N) 
            throws ClassNotFoundException, SQLException{
        FormPstGranero(Qm1_stock);
        pst.setInt(1, N);
        pst.setString(2, codigo);
        pst.executeUpdate();
        CerrarUpdate();
    }
    
    /**
     * Establece un valor arbitrario para el stock del produto identificado con
     * 'codigo'
     * @param codigo
     * @param N
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public void EstablecerStock(String codigo, int N) 
            throws ClassNotFoundException, SQLException{
        FormPstGranero(QsetStock);
        pst.setInt(1, N);
        pst.setString(2, codigo);
        pst.executeUpdate();
        CerrarUpdate();
    }
}
