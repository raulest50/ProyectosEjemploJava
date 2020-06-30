/**
 * 
 */
package virtualadministrator.Controllers.Controllers_Threads;

import BD.Handlers.BDH_Clientes;
import Definiciones.SptCliente;
import javafx.application.Platform;
import virtualadministrator.Controllers.ClientesController;

/**
 *
 * @author esteban
 * clase que genera hilos separaddos del hilo principal de fx para 
 * hacer las funciones de la pestana de clientes.
 */
public class ClientesThreads {
    
    ClientesController cc;
    BDH_Clientes bdhc;

    public ClientesThreads(ClientesController cc) {
        this.cc = cc;
        this.bdhc = new BDH_Clientes();
    }
    public void CodificarCliente(SptCliente ssc){
        Thread t = new Thread(){// se ingresa un cliente a la base de datos.
            @Override
            public void run(){
                
            }
        };
        t.start();// se arranca el hilo de ingreso de cliente.
    }
    
    public void BuscarCliente(String tipo, String Busqueda){
        Thread t = new Thread(){// se buscca un cliente en la base de datos
            @Override
            public void run(){
                SetLabel("Buscando.....");
                
            }
        };
    }
    
    public void SetLabel(String text){
        Platform.runLater( () ->{
            cc.SetLEstado(text);
        });
    }
}
