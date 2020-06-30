package BD.Handlers;

import BD.BDHandler;
import BD.Logica.validador;
import Definiciones.SptProducto;
import Definiciones.SptVenta;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author esteban
 */
public class BDHVentas extends BDHandler{
    
    validador val = new validador();
    BDH_Productos bdhp = new BDH_Productos();
    
    String insert_venta = "INSERT INTO `granero`.`ventas`\n" +
            "(`id`,`idconjunto`,`codigo`,`N`,`time`,`cliente`,`tipoventa`,`descripcion`,`pvpublico`,`pvtienda`,`costo`,`iva`)\n" +
            "VALUES(0,?,?,?,now(),?,?,?,?,?,?,?);",
            
            max_idx = "select ifnull(max((`ventas`.`idconjunto`))+1, 1) as a from ventas;",
            
            select_vthday = "SELECT * FROM ventas WHERE DATE(`ventas`.`time`) = ?;",
            
            select_numven_thday="select count(*) as num from (select count(idconjunto) from ventas WHERE DATE(`ventas`.`time`) = ? group by idconjunto) as counts;",
            
            eliminar_venta = "DELETE FROM `granero`.`ventas` WHERE idconjunto=?;",
            
            // permite obtener las ventas realizadas entre dos dias selecionados.
            select_intervalo_time = "SELECT * FROM ventas WHERE DATE(`ventas`.`time`) >= ? AND DATE(`ventas`.`time`) <= ? ;";
    
    
    
    /*
    int tipo:
    1 -> venta a publico
    0 -> venta a tienda
    
    venta: un array list con todos los productos de la venta.
    */
    public boolean GuardarVenta(SptVenta venta, int idx){
        boolean exito = false;
        try {
            FormPstGranero(insert_venta);
            pst.setInt(1, idx);
            pst.setString(2, venta.getProducto().getCodigo());
            pst.setInt(3, venta.getN());
            pst.setInt(4, venta.cliente);
            pst.setInt(5, venta.tipoVenta);
            pst.setString(6, venta.getProducto().getDescripcion());
            pst.setDouble(7, Double.parseDouble(venta.getProducto().getPvpublico()));
            pst.setDouble(8, Double.parseDouble(venta.getProducto().getPvtienda()));
            pst.setDouble(9, Double.parseDouble(venta.getProducto().getCosto()));
            pst.setDouble(10, Double.parseDouble(venta.getProducto().getIva()));
            pst.executeUpdate();
            CerrarUpdate();
            bdhp.RestarDeStock(venta.getProducto().getCodigo(), venta.getN());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BDHVentas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BDHVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exito;
    }
    
    public boolean EliminarVenta(int codigoVenta){
        boolean exito=false;
        try {
            FormPstGranero(eliminar_venta);
            pst.setInt(1, codigoVenta);
            pst.executeUpdate();
            CerrarUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BDHVentas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BDHVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exito;
    }
    
    public ArrayList<SptProducto> BuscarVenta(String codigoVenta){
        ArrayList<SptProducto> Venta = new ArrayList<>();
        return Venta;
    }
    
    public int GetNven_tday(String dia){
        int r=1;
        try {
            FormPstGranero(select_numven_thday);
            pst.setString(1, dia);
            rs = pst.executeQuery();
            while(rs.next()){
                r = rs.getInt("num");
            }
            CerrarTodo();
        } catch (SQLException ex) {
            Logger.getLogger(BDHVentas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BDHVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    
    
    /**
     * metodo que entrega una lista con las ventas ocurridas
     * el dia @param dia
     * @param dia String representando un dia ej: 2016-7-5
     * @return lv. una lista con las ventas ocurridas en @param dia
     */
   
    
    public ArrayList<SptVenta> TraerVentasDelDia(String dia){
        ArrayList<SptVenta> lv = new ArrayList<>();
        try {
            FormPstGranero(select_vthday);
            pst.setString(1, dia);
            rs = pst.executeQuery();
            while(rs.next()){
                lv.add(new SptVenta(rs.getInt("id"), rs.getInt("idconjunto"), rs.getInt("N"), 
                        rs.getInt("tipoventa"), rs.getInt("cliente"), rs.getString("time"),
                new SptProducto(rs.getString("descripcion"), rs.getString("pvpublico"),
                rs.getString("pvtienda"), rs.getString("costo"), "", rs.getString("codigo"), 
                        rs.getString("iva"), "", "")));
            }
            CerrarUpdate();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BDHVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lv;
    }




    
    /**
     * Este metodo entrega una lista de ventas de los productos que se 
     * vendieron en el inervalo de dias especificado.
     * @param init primer dia, se incluye en el intervalo
     * @param hasta ultimo dia, se sincluye en el intervalo
     * @return ArrayList de ventas
    */
    
    public ArrayList<SptVenta> TraerVentasIntervalo(String init, String hasta){
        ArrayList<SptVenta> lv = new ArrayList<>();
        try {
            FormPstGranero(select_intervalo_time);
            pst.setString(1, init);
            pst.setString(2, hasta);
            rs = pst.executeQuery();
            while(rs.next()){
                lv.add(new SptVenta(rs.getInt("id"), rs.getInt("idconjunto"), rs.getInt("N"), 
                        rs.getInt("tipoventa"), rs.getInt("cliente"), rs.getString("time"),
                new SptProducto(rs.getString("descripcion"), rs.getString("pvpublico"),
                rs.getString("pvtienda"), rs.getString("costo"), "", rs.getString("codigo"), 
                        rs.getString("iva"), "", "")));
            }
            CerrarUpdate();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BDHVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lv;
    }
    

    
    
    /**
     * este metodo permite obtener el numero mas alto registrado para
     * los conjuntos de ventas (idconjunto) que es el umero que indentifica
     * los productos que pertenecen a una misma operacion venta.
     * @return 
     */
    public int GetConjIdx(){
        int r=1;
        try {
            FormPstGranero(max_idx);
            rs = pst.executeQuery();
            while(rs.next()){
                r = rs.getInt("a");
            }
            CerrarTodo();
        } catch (SQLException ex) {
            Logger.getLogger(BDHVentas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BDHVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    
}
