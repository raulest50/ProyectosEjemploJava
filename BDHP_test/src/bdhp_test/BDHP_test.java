package bdhp_test;

/**
 *
 * @author esteban
 * 
 * se hace prueba del BDhandler para hacer opeaciones sobre la tabla de 
 * ventas.
 */

import BD.Handlers.BDHVentas;
import BD.Handlers.BDH_Productos;

import Definiciones.SptProducto;
import Definiciones.SptVenta;

public class BDHP_test {

    
    
    
    public static void main(String[] args) {
        
        BDHVentas bdhv = new BDHVentas();
        BDH_Productos bdhp = new BDH_Productos();
        
        String descripcion = "crema de pollo la sopera 8  porciones";
        String pvpublico = "900.0";
        String pvtienda = "800.0";
        String costo = "744.0";
        String LastUp = "n/n";
        String Codigo = "13331";
        String iva = "0.16";
        String familia = "condimentos";
        String stock = "10";
        
        SptProducto p1 = new SptProducto(descripcion , pvpublico, pvtienda, costo, LastUp,
                Codigo, iva, familia, stock);
        
        int id = 0;
        int idconjunto = 15;
        int N = 10;
        int tipo_venta = 1;
        int cliente = 1001;
        String tiempo = "hoy";
        
        
        SptVenta venta = new SptVenta(id, idconjunto, N, cliente, tipo_venta, tiempo, p1);
        
        int idx = bdhv.GetConjIdx();
        
        bdhv.GuardarVenta(venta, idx);
        
    }
    
}
