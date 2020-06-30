
package BD.Handlers;

import BD.BDHandler;
import Definiciones.SptCliente;
import java.util.ArrayList;

/**
 *
 * @author esteban
 */
public class BDH_Clientes extends BDHandler {
    
    String QCCliente = "",
            QBCliente = "",
            QECliente = "",
            QModCliente = "";
    
    
    
    public ArrayList<SptCliente> BuscarCliente(String tipo, String busqueda){
        ArrayList<SptCliente> spc = new ArrayList<>();
        return spc;
    }
    
    public String CodificarCliente(SptCliente spc){
        String r = "";
        return r;
    }
    
    public String EliminarCliente(String codigo){
        String r = "";
        return r;
    }
    
    public String ModificarCliente(SptCliente spc){
        String r = "";
        return r;
    }
    
}
