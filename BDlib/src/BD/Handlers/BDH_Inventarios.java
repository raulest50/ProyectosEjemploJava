package BD.Handlers;

/**
 *
 * @author esteban
 */

import BD.BDHandler;
import BD.Logica.validador;
import Definiciones.SptConteo;
import Definiciones.SptInventario;
import java.sql.SQLException;
import java.util.ArrayList;

public class BDH_Inventarios extends BDHandler{
    // para crear una nueva tabal sobre la que se anotan los conteos
    String crear_tabla = "CREATE TABLE `xxxx` (\n" +
            "  `id` int(20) NOT NULL AUTO_INCREMENT,\n" +
            "  `N` decimal(10,1) NOT NULL,\n" +
            "  `Codigo` varchar(100) COLLATE utf8_spanish_ci NOT NULL,\n" +
            "  `Nombre` varchar(200) COLLATE utf8_spanish_ci DEFAULT NULL,\n" +
            "  `idContador` varchar(40) COLLATE utf8_spanish_ci DEFAULT NULL,\n" +
            "  `Lugar` varchar(70) COLLATE utf8_spanish_ci DEFAULT NULL,\n" +
            "  `time` datetime DEFAULT NULL,\n" +
            "  `costo` decimal(10,1) DEFAULT '0.0',\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;",
            // eliminar una tabal sobre la que se han hecho conteos
            eliminar_tabla = "DROP TABLE `xxxx`",
            //get_estado permite saber si se esta haciendo o no un inventario
            get_estado = "select estado from propiedades where propiedad = 'Inventario';",
            // get_nomTab permite tener el nombre de la tabla
            get_nomTab = "select estado from propiedades where propiedad = 'InventarioNombre'",
            get_tables = "show tables", // query para mostrar todas los inventarios que hay en lla base de datos
            // set_estado permite cambiar la propiedad que indica si actualmente se esta haciendo o no un inventario
            set_estado = "UPDATE `granero`.`propiedades` SET `estado` = ? WHERE propiedad = 'Inventario';",
            // modifica el nombre actual de la tabla sobre la que se estan escribiendo slos conteos en los inventarios
            set_table_name = "UPDATE `granero`.`propiedades` SET `estado` = ? WHERE propiedad = 'InventarioNombre';",
            // inserta un conteo en una tabla de inventario
            InsertarConteo = "INSERT INTO `inventarios`.`xxxx`\n" +
            "(`id`,`N`,`Codigo`,`Nombre`,`idContador`,`Lugar`,`time`)\n" +
            "VALUES(0,?,?,?,?,?,now());",
            EliminarConteo = "DELETE FROM `inventarios`.`xxxx`\n" +
            "WHERE id = ?;",
            // para modificar un conteo.
            ModificarConteo = "UPDATE `inventarios`.`xxxx`\n" +
            "SET `N` = ?,`Codigo` = ?,`Nombre` = ?,`idContador` = ?,`Lugar` = ?,`time` = now() \n" +
            "WHERE `id` = ?;",
            TraerConteos = "SELECT * FROM `inventarios`.`xxxx`;",
            QModCosto = "UPDATE `inventarios`.`xxxx` SET `costo`=? WHERE `id`=?";
    
    String noInvAct = "No Hay Inventario Activo";
    String exito = "exito";
    
    validador val = new validador();
    
    /**
     * Metodo que crea una nueva tabla de inentario y ademas 
     * activa el estado de inventario.
     * @return boolean
     */
    public boolean iniciar_inventario(){// crea una nueva tabla en la bd inventarios
        // y modifica las propiedades correspondientes
        boolean r = false;
        String fecha = val.obt_fecha();
        String QCrearTabla = crear_tabla.replace("xxxx", fecha);
        try{
            // se crea la tabla
            FormPstInventario(QCrearTabla);
            pst.executeUpdate();
            CerrarUpdate();
            // Se modifica la propiedad de estado
            Modify_Estado(true);
            // Se modifica la propiedad de tabla actual de escritura.
            Modify_Table_Name(fecha);
        }
        catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
        return r;
    }
    
    /**
     * Modifica el estado de inventario a activo o inactivo.
     * true: activar
     * false: desactivar
     * @param next_state
     * @return 
     */
    public boolean Modify_Estado(boolean next_state){
        boolean r = false;
        try{
            FormPstGranero(set_estado);
            if(next_state) pst.setString(1, "true");
            else pst.setString(1, "false");
            pst.executeUpdate();
            CerrarUpdate();
            r = true;
        }
        catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
        return r;
    }
    
    /**
     * Metodo que modifica el nombre de una tabla de inventario
     * @param name
     * @return boolean
     */
    public boolean Modify_Table_Name(String name){
        boolean r = false;
        try{
            // se verifica si el nombre esta vacio, de ser asi se lanza una excepcion
            if(name == null || name.equals("") || name.equals(" ")) throw new NullPointerException();
            else{
                FormPstGranero(set_table_name);
                pst.setString(1, name);
                pst.executeUpdate();
                CerrarUpdate();
                r = true;
            }
        }
        catch(ClassNotFoundException | SQLException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return r;
    }
    
     
    public ArrayList<SptInventario> getTablesInvt(){
        ArrayList<SptInventario> r = new ArrayList<>();
        // este metodo permite saber que inventarios se han hecho y lo
        // retorna en un ArrayList.
        try{
            FormPstInventario(get_tables);
            rs = pst.executeQuery();
            
            while(rs.next()){
                r.add(new SptInventario(rs.getString(1)));
            }
            
            CerrarTodo(); // se cierra el prepared statement el result-set y la conexion
        }
        catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
        return r;
    }
    
    /**
     * Metodo que retorna un booleano indicando si hay o no un inventario activo
     * @return boolean
     */
    public boolean getEstadoInvt(){
        String estado = null;
        // metodo para extrater el estado de inventario, true or false
        try{
            FormPstGranero(get_estado);
            rs = pst.executeQuery();
            while(rs.next()){
                estado = rs.getString("estado");
            }
            
            CerrarTodo(); // se cierra el prepared statement el result-set y la conexion
        }
        catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
        boolean Bool_estado = false;
        if( estado.equals("true")) Bool_estado = true;
        return Bool_estado;
    }
    
    
    /**
     * retorna un String con el nombre de la tabla de inventario que esta activa
     * el uso de este metodo debe ser precedido por el uso de getEstadoInvt() 
     * ya que este indica si hay o no un invetario activo.
     * @return String
     */
    public String getNombreTabAct(){
        String nombre = null;
        // metodo para extrater el nombre de la tabla  actual de inventrio objetivo
        try{
            FormPstGranero(get_nomTab);
            rs = pst.executeQuery();
            while(rs.next()){
                nombre = rs.getString("estado");
            }
            CerrarTodo(); // se cierra el prepared statement el result-set y la conexion
        }
        catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
        return nombre;
    }
    /**
     * Elimina una tabla de inventario seleccionada
     * para que la eliminacion sea ejecutada la tabla de inventario
     * seleccionada no puede estar activa. 
     * @param nombre
     * @return 
     */
    public boolean Eliminar_Inventario(String nombre){
        boolean r = false; // metodo para eliminar una tabla de la bd inventarios.
        try{
            String DelQuery = eliminar_tabla.replace("xxxx", nombre);
            FormPstInventario(DelQuery);
            pst.executeUpdate();
            CerrarUpdate();
            r = true;
        }
        
        catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
        return r;
    }
    
    /**
     * Inserta un conteo en la tabla de inventario que este activa en el momento
     * de hacer la operacion. 
     * @param c
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public String InsertarConteo(SptConteo c)// se ingresa un conteo a la respectiva tabla de inventario
            throws ClassNotFoundException, SQLException{
        String r;// resultado de la operacion
        if(this.getEstadoInvt()){ // si hay un inventario activo:
            String Qfinal = InsertarConteo.replace("xxxx", getNombreTabAct());
            FormPstInventario(Qfinal);
            pst.setDouble(1, c.getN());
            pst.setString(2, c.getCodigo());
            pst.setString(3, c.getNombre());
            pst.setString(4, c.getIdContador());
            pst.setString(5, c.getLugar());
            pst.executeUpdate();
            CerrarUpdate();
            r = this.exito;
        }
        else{// si no hay invetario activo:
            r = this.noInvAct;
        }
        return r;
    }
    
    /**
     * Elimina el conteo con el id especificado.
     * La eliminacion se hace en la tabla correspondiente al inventario activo
     * si no hay ningun invventario activo simplemente la operacion no tiene efecto
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public String EliminarConteo(int id) 
            throws ClassNotFoundException, SQLException{ 
        String r;
        if(this.getEstadoInvt()){// si hay algun inventario activo:
            String Qfinal = EliminarConteo.replace("xxxx", getNombreTabAct());
            FormPstInventario(Qfinal);
            pst.setInt(1, id);
            pst.executeUpdate();// se elimina el conteo
            CerrarUpdate();
            r=this.exito;
        }
        else {// si no hay ningun invetario activo:
            r = this.noInvAct;
        }
        return r;// se explica el resultado de la operacion en un string
    }
    
    
    /**
     * Metodo que permite modificar el conteo con el id especificado.
     * dicho id es buscado dentro del mismo objeto SptConteo el cual a su vez
     * tiene todos los parametros que se usaran para la modificacion.
     * @param c
     * @return String r: resultado de la operacion
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public String ModificarConteo(SptConteo c) // modificacion de un conteo.
            throws ClassNotFoundException, SQLException{
        String r; // resultado de la operacion
        if(this.getEstadoInvt()){ // si hay inventario activo:
            String Qfinal = ModificarConteo.replace("xxxx", getNombreTabAct());
            FormPstInventario(Qfinal);
            pst.setDouble(1, c.getN());
            pst.setString(2, c.getCodigo());
            pst.setString(3, c.getNombre());
            pst.setString(4, c.getIdContador());
            pst.setString(5, c.getLugar());
            pst.setString(6, c.getId());
            pst.executeUpdate();
            CerrarUpdate();
            r = this.exito;
        }
        else{// si no hay inventario activo
            r = this.noInvAct;
        }
        return r;
    }
    
    
    /**
     * Este metodo permite modificar solamente el costo unitario de un conteo
     * la diferencia respecto al metodo modificar conteo que podria servir para
     * lo mismo que este no necesita de una tabla de inventarios activa
     * asi que este metodo solo se usa para hacer liquidacion.
     * @param table
     * @param id
     * @param costo
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public void ModificarCostos(String table, String id, double costo) // modificacion de un conteo.
            throws ClassNotFoundException, SQLException{
        String r; // resultado de la operacion
        String Qfinal = QModCosto.replace("xxxx", table);
        FormPstInventario(Qfinal);
        pst.setDouble(1, costo);
        pst.setInt(2, Integer.parseInt(id));
        pst.executeUpdate();
        CerrarUpdate();
    }

    /**
     * Este metodo retorna un ArrayList<Conteo> Con todos los elementos del inventario
     * seleccionado. recuerde que los productos contados que no estan codificados
     * en la tabla productos se les asigna por defecto el codifo 767 que es un codigo reservado.
     * tambien por defecto a todos los productos contados se les asigna por
     * defecto el costo (tipo double) 0.0.
     * @param tabla
     * @return ArrayList<SptConteo>
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    
    public ArrayList<SptConteo> getFullTableList(String tabla) throws ClassNotFoundException, SQLException{
        ArrayList<SptConteo> ProContados = new ArrayList(); // variable de retorno del metodo
        // contiene los productos contados, separando los codificados de los no codificados
        String que = this.TraerConteos.replace("xxxx", tabla);
        super.FormPstInventario(que);
        super.rs = super.pst.executeQuery();
        String codigo;
        while(rs.next()){
            codigo = rs.getString("Codigo");
            ProContados.add(new SptConteo(codigo, Double.parseDouble(rs.getString("N")),
                    rs.getString("Lugar"), rs.getString("idContador"), rs.getString("id"),
                    rs.getString("time"), rs.getString("Nombre"), rs.getDouble("costo")));
        }
        return ProContados;
    }
    
    public ArrayList<SptConteo> ListaDelContador(String idcont, String tabla) 
            throws ClassNotFoundException, SQLException{
        
        ArrayList<SptConteo> lf = this.getFullTableList(tabla);
        ArrayList<SptConteo> lc = new ArrayList<>();
        for(int i=0; i< lf.size(); i++){
            if(lf.get(i).getIdContador().equals(idcont)) lc.add(lf.get(i));
        }
        return lc;
    }
}
