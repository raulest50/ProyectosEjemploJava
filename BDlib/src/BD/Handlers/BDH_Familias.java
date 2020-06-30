package BD.Handlers;

import BD.Logica.validador;
import Definiciones.SptFamilia;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * @author esteban
 * En esta clase se encuentran todos  los metodos necesarios para
 * manipular la tabla de familias
 */
public class BDH_Familias extends BD.BDHandler{
    
    validador val = new validador();
    
    final String 
            QTraerTodos = "select * from familias", //query para traer todos los elementos de la tabla
            QBuscarId = "select * from familias where id = ?", // para verificar si existe un id en la tabla
            QEliminar = "DELETE FROM `granero`.`familias` WHERE `id`=?", // eliminar un id especifico
            QInsertar = "INSERT INTO `granero`.`familias` (`id`) VALUES (?)", // insertar una familia en la tabla.
            QModificarFam = "", // modificacion de nombre de familia.
            QUpdateFam = ""; // se actualizan todos los productos con
            // el nuevo nombre de familia especificado.
    
    public BDH_Familias(){     
    }
    
    public ArrayList<SptFamilia> TraerTodas(){
        ArrayList<SptFamilia> resultados = new ArrayList<>();
        
        try{
            HacerQuerySoloString(QTraerTodos, new String[] {});
            while (rs.next()){
                resultados.add(new SptFamilia(rs.getString("id")));
            }
        }
        catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return resultados;
    }
    
    public boolean Insertar(String id){
        boolean r = false; // r indica si la insersion fue o no exitosa
        if(!ExisteId(id) && !val.vacio(id) && id.length()<=150){ // se verifica que la familia no exista y que 
            //id no este vacio o que sobrepase 150 caracteres
            try{ // se inserta familia a la tabla
                FormPstGranero(QInsertar); // se forma el prepared statement pst el cual es heredado de BD_Handler 
                pst.setString(1, id); // se establece el id del pst
                pst.executeUpdate(); // se ejecuta el pst
                CerrarUpdate(); // se cierra la conexion
                r = true; // se indica que la operacion fue exitosa y que hasta este punto no se ha
                //generado ninguna excepcion
            }
            catch(SQLException | ClassNotFoundException e){
                e.printStackTrace();// en caso de ocurrir una excepcion se indica con el booleano r
                r = false;// y se imprime el error.
            }
        }
        return r;
    }
    
    public boolean ExisteId(String id){ // verifica si existe  un familia
        boolean r = false; // r = true si el id existe en la tabla familias.
        try{
            HacerQuerySoloString(QBuscarId,new String[] {id});
            if (rs.next()) r = true;
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            r =  true;
        }
        return r;
    }
    
    public boolean Eliminar(String id){
        boolean r = false;
        if(!val.vacio(id)){
            try{// se elimina una familia de la tabla
                FormPstGranero(QEliminar);
                pst.setString(1, id);
                pst.executeUpdate();
                CerrarUpdate();
                r = true;
            }
            catch(ClassNotFoundException | SQLException e){
                e.printStackTrace();
                r=false;
            }
        }
        return r;
    }
    
    public boolean Modificar(String nuevoNombre){
        boolean r = false;
        // modifiicar tablas familias
        // modificar todos los productos de la tabla
        
        return r;
    }
    
}
