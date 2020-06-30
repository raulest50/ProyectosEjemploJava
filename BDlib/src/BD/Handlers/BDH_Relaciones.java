package BD.Handlers;

import BD.BDHandler;
import Definiciones.SptProducto;
import Definiciones.SptRelacion;
import java.lang.reflect.MalformedParametersException;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Clase Encargada de gestionar las operaciones de la tabla de SQL de relaciones
 * entre productos. EN la tabla de relaciones se agrupan los productos que
 * tienen iguales valores de costo y precios de venta. Un ejemplo son
 * los suntea, que vienen en diferentes sabor y cada sabor con un codigo
 * a parte, pero que sus valores de costo y venta son iguales. Gracias 
 * a la tabla de relaciones es posible que al modificar un solo sabor de suntea
 * por ejemplo, el programa automaticamente actualiza los otros. De esta forma
 * no solo se ahorra trabajo sino tambien se logra mayor confiabilidad en 
 * el mantenimiento de la base de datos. Esta clase contiene todos los metodos 
 * necesarios para ejecutar la sentencias de SQL que involucren la tabla de 
 * relaciones
 * @author esteban
 */
public class BDH_Relaciones extends BDHandler{
    
    public String InsertRelacion = "INSERT INTO `granero`.`relaciones`\n" +
            "(`id`, `codgrupo`, `p1`, `p2`, `nombre`)\n" +
            "VALUES(?,?,?,?,?);",
            
            EliminarGrupo = "DELETE FROM `granero`.`relaciones` WHERE `codgrupo`=?;",
            EliminarRelacion = "DELETE FROM `granero`.`relaciones` WHERE `p1`=? and `p2`=?;",
            SelectGrupo = "select * from `granero`.`relaciones` where `codgrupo` = ?;",
            checkGrupo = "select exists (select * from relaciones where nombre = ?) as existe;", // para revisar si existe un record que contenga un nombre de grupo especificado
            getGrupo = "select codgrupo from relaciones where p1=? or p2=?;",
            getP1P2FromRelacion = "select p1, p2 from relaciones where codgrupo=?;",
            BuscarNombreGrupo="select nombre from relaciones where codgrupo=?;";
    
    
    /**
     * dado el codigo de un producto cualquiera (String codp)
     * este metodo retorna el codigo de grupo 
     * o "" si no esta asociado a ningun grupo de productos.
     * 
     * @param codp
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public String GetGrupo(String codp) 
            throws SQLException, ClassNotFoundException{
        
        String codGrupo = "";
        boolean r = false;
        super.HacerQuerySoloString(getGrupo, new String[] {codp, codp});
        if (rs.next()){
            codGrupo = rs.getString("codgrupo");
        }
        super.CerrarTodo();
        return codGrupo;
    }
    
    
    /**
     * Indica True si el nombre del grupo ya existe
     * @param px
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public boolean ExisteNombreGrupo(String nombreGrupo)
            throws SQLException, ClassNotFoundException{
        
        boolean r = false; // r indica si existe un grupo de relaciones con el nombre nombreGrupo
        int existe = 0;
        super.HacerQuerySoloString(checkGrupo, new String[] {nombreGrupo});
        if (rs.next()){
            existe = rs.getInt("existe");
        }
        if(existe == 1) r = true;
        
        super.CerrarTodo();
        return r;
    }
    
    
    /**
     * inserta un solo record en la tabla de relaciones
     * @param rel
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public void InsertarRelacion(SptRelacion rel) throws ClassNotFoundException, SQLException{
        FormPstGranero(InsertRelacion);
        pst.setInt(1, rel.getId());
        pst.setString(2, rel.getCodgrupo());
        pst.setString(3, rel.getP1());
        pst.setString(4, rel.getP2());
        pst.setString(5, rel.getNombre());
        pst.executeUpdate();
        CerrarUpdate();
    }
    
    /**
     * Inserta todos los records necesarios en la tabla de relaciones para 
     * guardar todo el grupo correspondiente a una relacion.
     * por defecto se toma como numero de grupo el producto con la
     * posicion 0 del arreglo (lo cual es aleatorio pero no importa porque
     * cualquier producto de la lista es representativo entonces es irrelevante
     * cual quede en la posicion 0). Las relaciones se configuran en pares.
     * Si hay que relacionar 7 productos entonces se insertan 6 records
     * en la tabla de relaciones donde todos tienen el mismo codigo de grupo
     * correspondiente al codigo del producto de la posicion 0 (esto asegura
     * que el codigo de grupo es unico y al mismo tiempo el usuario no debe preocuparse 
     * por ingresar este valor). Cada record corresponde a una pareja de productos.
     * cada pareja  o par estara formada por: 
     * [el producto de posicion 0, el producto de posicion k-esimo].
     * para el ejemplo de este comentario, que son 7 productos seria:
     * [0, 1]   [0, 2]  [0, 3]  [0, 4]  [0, 5]  [0, 6] 
     * (recuerdese que inicia numeracion en 0, es decir que 7 items se enumeran de 0 a 6)
     * donde los numeros representan el producto de esa posicion del arreglo.
     * @param plist
     */
    public void InsertarConjuntoRelaciones( ArrayList<SptProducto> plist, String nombreGrupo) 
            throws ClassNotFoundException, SQLException{
        // el id de cada record se pone en 0 ya que esa columna de la
        //tabla esta configurada como auto-incremental
        
        for (int i=0; i<plist.size()-1; i++){
            InsertarRelacion(new SptRelacion(0, plist.get(0).getCodigo(), 
                    plist.get(0).getCodigo(), plist.get(i+1).getCodigo(), nombreGrupo));
        }
        
    }
    
    
    /**
     * borra varias relaciones
     * @param codgrupo
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public void BorrarGrupo(String codgrupo) 
            throws SQLException, ClassNotFoundException{
        
        FormPstGranero(EliminarGrupo);
        pst.setString(1, codgrupo);
        pst.executeUpdate();
        CerrarUpdate();
    }
    
    
    // borra solo una relacion, la cual solo puede ser entre 2 productos
    public void BorrarRelacion(String p1, String p2) 
            throws ClassNotFoundException, SQLException{
        FormPstGranero(EliminarRelacion);
        pst.setString(1, p1);
        pst.setString(2, p2);
        pst.executeUpdate();
        CerrarUpdate();
    }
    
    
    /**
     * Dado un codigo de grupo, este metodo entrega todos los productos pertenencientes
     * a ese grupo. Todos estos productos que entrega este metodo 
     * estan relacionados por igualdad de precio que es el concepto de relaciones
     * entre productos en este programa. EJ: todos los frutiños tienen el mismo
     * precio de venta costo etc sin importar el sabor, asi que todos los sobres 
     * de frutiño pertenencen al mismo grupo. En este caso dado el codigo de grupo
     * este medoto retornaria todos los frutiños como obj sptproducto.
     * @param codGrupo
     * @return 
     * @throws java.lang.ClassNotFoundException 
     * @throws java.sql.SQLException 
     */
    public ArrayList<SptProducto> GetProductosDelGrupo(String codGrupo) 
            throws ClassNotFoundException, SQLException{
        ArrayList<SptProducto> lp = new ArrayList<>();
        BDH_Productos bdhp = new BDH_Productos();
        ArrayList<String> p_list = new ArrayList<>();
        
        super.HacerQuerySoloString(getP1P2FromRelacion, new String[] {codGrupo});
        
        String auxp1, auxp2;
        while (rs.next()){
            auxp1 = rs.getString("p1");// se saca la pareja de productos de cada record
            auxp2 = rs.getString("p2");// p1 deberia ser el mismo pero se hace 
            //el algoritmo mas general para mas robustez.
            if(!p_list.contains(auxp1)) p_list.add(auxp1);//if's para descartar duplicados
            if(!p_list.contains(auxp2)) p_list.add(auxp2);
        }
        
        for(int k=0; k<p_list.size(); k++){
            lp.add( bdhp.BusquedaCodExact(p_list.get(k)).get(0) );
        }
        
        return lp;
    }
    
    
    /**
     * Este metodo le aplica el cambio de precio todos los elementos de un grupo
     * Ej: todos los frutiños tienen el mismo precio de costo y venta etc sin importar
     * el sabor. Por esto lo mas comodo es que con cambiar el precio de un solo
     * frutiño se cambien en cascada y en igual magnitud el precio de los demas
     * frutiños, que es lo que precisamente hace este metodo.
     * dado un producto, se encuuentra su numero de grupo
     * y luego con ese numero de grupo se buscan todos los productos asociados
     * a esa relacion. finalmente se aplica ell mismo cambio de precio
     * a todos los productos que pertenezacan a la relacion.
     * 
     * codP representa el producto que al ser modificado dispara en cascada
     * la modificacion del precio de los otros. Por este motivo.
     * @param codGrupo : codigo de grupo de los productos relacionados a mismo precio.
     * @param PgenCambio : producto generador del cambio en cascada
     * @throws java.sql.SQLException 
     * @throws java.lang.ClassNotFoundException 
     */
    public void AplicarCambiosPrecio(String codGrupo, SptProducto PgenCambio, String NewCodPgenCambio)
            throws SQLException, MalformedParametersException, ClassNotFoundException{
        
        BDH_Productos bdhp = new BDH_Productos();// handler de productos
        
        ArrayList<SptProducto> lp = this.GetProductosDelGrupo(codGrupo); // se toman todos los productos del grupo
        String nombreGrupo = this.getNombreGrupo(codGrupo); // se busca el nombre del grupo asociado al codigo de grupo
        
        
        // en caso de que el producto no solo haya cambiado de precio
        // sino que tambien le hayan cambiado codigo es necesario
        // hacer la actualizacion en la tabala de relaciones para evitar inconsistencias
        int indexOfGen = this.EncontrarIndiceProducto(NewCodPgenCambio, lp); // indice del producto que genero el cambio
        PgenCambio.setCodigo(NewCodPgenCambio); // se le cambiaa el codigo
        lp.set(indexOfGen, PgenCambio); // se actualiza en la lista
        
        //una vez actualizado en los objetos de java hay que actualizar el 
        //posible cambio de codigo en la tabla de relaciones. para ello
        //se borran los recods asociados al grupo y se vuelven a crear.
        this.BorrarGrupo(codGrupo);
        this.InsertarConjuntoRelaciones(lp, nombreGrupo);
        
        //hasta aqui ya se asegura una actualizacion de la tabla de relaciones
        //para ambos casos que el producto que genera el cambio en cascada 
        //cambie o no de codigo en la tabla de productos.
        
        //a continuacion se generan en el for los respectivos cambios de precio en cascada
            
        for (int i=0; i<lp.size(); i++){
            if(!lp.get(i).getCodigo().equals(NewCodPgenCambio)){
                lp.get(i).setCosto(PgenCambio.getCosto());
                lp.get(i).setPvpublico(PgenCambio.getPvpublico());
                lp.get(i).setPvtienda(PgenCambio.getPvtienda());
                bdhp.Modificar(lp.get(i), lp.get(i).getCodigo()); // se hace la actualizacion
                //en la tabla de productos
            }
        }
        
    }

    
    /**
     * 
     * dado un codigo de grupo este metodo retorna el nombre del grupo
     * @param codGrupo: codigo de grupo en la tabla de relaciones.
     * @return
     */
    private String getNombreGrupo(String codGrupo) 
            throws SQLException, ClassNotFoundException {
        
        String nombre="";
        super.HacerQuerySoloString(this.BuscarNombreGrupo, new String[] {codGrupo});
        if (rs.next()){
            nombre = rs.getString("nombre");// se toma el nombre
        }
        return nombre;
    }
    
    public int EncontrarIndiceProducto(String cod, ArrayList<SptProducto> lp){
        int indexOfGen = -1;
        for(int k=0; k<lp.size(); k++){
            if(lp.get(k).getCodigo().equals(cod)) indexOfGen = k;
        }
        return indexOfGen;
    }
    
}
