/*
 */
package virtualadministrator.Controllers.Controllers_Threads;

import BD.Handlers.BDH_Productos;
import BD.Logica.validador;
import Definiciones.SptProducto;
import java.lang.reflect.MalformedParametersException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.application.Platform;
import virtualadministrator.Controllers.ModificarController;

/**
 * @author esteban
 * esta clase se instancia en la funcion de inicializacion de su respectivo controlador de fx
 * y se encarga de hacer todas las operaciones que usuario requiera mediante el respectivo
 * controlador. esta clase es poco reutilizable ya que solo sirve para fx y muy en particular 
 * el tipo de controlador que  se ha definido en este software. En esta clase se implementan 
 * las funcionalidades multihilo de la aplicacion referentes a la mnodificacion de productos.
 */
public class ModificarThreads {
    
    ModificarController modcon;
    BDH_Productos bdhp;
    validador val;
    
    String EB = "Buscando...", // buscando producto
            EN = "---------", // antes de hacer cualquier operacion
            ER = "Resultados de la busqueda", // cuando busqieda retorna resultados
            ESR = "Sin resultados"; // cuando no hay resultados de la busqueda
    

    public ModificarThreads(ModificarController modcon) {
        this.modcon = modcon;
        this.bdhp = new BDH_Productos();
        this.val = new validador();
    }
    
    public void setLabel(String text){
        Platform.runLater(() -> { // se usa para establecer el texto en la etiqueta
            modcon.setEstado(text); // de la gui de ModificarTab
        });
    }
    
    public void setTview(ArrayList<SptProducto> productos){
        Platform.runLater(() -> { // para poner los productos encontrados en la busqueda
            modcon.SetTableV(productos);  // de productos en la tabla de ModificarTab
        });
    }
    
    public void BusquedaDescripcion(String busqueda){
        Thread t = new Thread(){
            @Override
            public void run(){
                setLabel(EB);// se escribe que se esta buscando...
                ArrayList<SptProducto> rlista;// variable para almacenar los resultados
                try{
                    rlista = bdhp.BusquedaDescripcion(busqueda); // se ejecuta la busqueda en la bd
                    if(rlista.isEmpty()) setLabel(ESR); // se escribe que no hay resultados de la busqueda
                    else{
                        setLabel(ER); //se escribe que la busqueda arrojo resultados
                    }
                    setTview(rlista);
                }
                catch(MalformedParametersException | SQLException | ClassNotFoundException e){
                    ErrorDialog("exepcion de java", "descripcion:", "excepcion--->" + e.getMessage());
                    setLabel(EN);// se escribe ---- y se pone la excepcion de jaava que ocurrrio
                }
            }
        };
        t.start();// se arranca el hilo para buscar por descripcion
    }

    public void BusquedaLastCod(String busqueda) {
        Thread t = new Thread(){
            @Override
            public void run(){
                setLabel(EB);// se escribe que se esta buscando...
                ArrayList<SptProducto> rlista;// variable para almacenar los resultados
                try{
                    rlista = bdhp.BusquedaLastCod(busqueda); // se ejecuta la busqueda en la bd
                    if(rlista.isEmpty()) setLabel(ESR); // se escribe que no hay resultados de la busqueda
                    else{
                        setLabel(ER); //se escribe que la busqueda arrojo resultados
                    }
                    setTview(rlista);
                }
                catch(MalformedParametersException | SQLException | ClassNotFoundException e){
                    ErrorDialog("exepcion de java", "descripcion:", "excepcion--->" + e.getMessage());
                    setLabel(EN);// se escribe ---- y se pone la excepcion de jaava que ocurrrio
                }
            }
        };
        t.start();// se arranca el hilo para buscar por descripcion
    }

    public void BusquedaCodExact(String busqueda) {
        Thread t = new Thread(){
            @Override
            public void run(){
                setLabel(EB);// se escribe que se esta buscando...
                ArrayList<SptProducto> rlista;// variable para almacenar los resultados
                try{
                    rlista = bdhp.BusquedaCodExact(busqueda); // se ejecuta la busqueda en la bd
                    if(rlista.isEmpty()) setLabel(ESR); // se escribe que no hay resultados de la busqueda
                    else{
                        setLabel(ER); //se escribe que la busqueda arrojo resultados
                    }
                    setTview(rlista);
                }
                catch(MalformedParametersException | SQLException | ClassNotFoundException e){
                    ErrorDialog("exepcion de java", "descripcion:", "excepcion--->" + e.getMessage());
                    setLabel(EN);// se escribe ---- y se pone la excepcion de jaava que ocurrrio
                }
            }
        };
        t.start();// se arranca el hilo para buscar por descripcion
    }
    
    public void ErrorDialog(String title, String subtitle, String Message){
        Platform.runLater( () ->{
            modcon.ShowError(title, subtitle, Message);
        });
    }
    
    public void InfoDialog(String title, String subtitle, String Message){
        Platform.runLater( () ->{
            modcon.ShowInfo(title, subtitle, Message);
        });
    }
    
}
