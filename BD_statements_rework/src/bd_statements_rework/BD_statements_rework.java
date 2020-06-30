package bd_statements_rework;

import BD.Handlers.BDHCreator;
import BD.Handlers.BDHVentas;
import Definiciones.SptProducto;
import Definiciones.SptVenta;
import java.lang.reflect.MalformedParametersException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author esteban
 */
public class BD_statements_rework {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        BDHVentas bdhv = new BDHVentas();
        SptVenta spv = new SptVenta(0, 0, 12345, 77, 1, 12345, "");
        int idx = bdhv.GetConjIdx();
        bdhv.GuardarVenta(spv, idx);
        
    }
    
}
