package virtualadministrator.Controllers.Controllers_Threads;

import BD.Handlers.BDH_Proveedores;
import Definiciones.SptProveedor;
import java.lang.reflect.MalformedParametersException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import virtualadministrator.Controllers.Dialogs.Popup_ModProveedorController;

/**
 *
 * @author esteban
 */
public class Popup_ModProveedorThreads {
    
    
    Popup_ModProveedorController popprovcon;
    BDH_Proveedores bdhprov;
    
    public Popup_ModProveedorThreads(Popup_ModProveedorController popprovcon) {
        this.popprovcon = popprovcon;
        this.bdhprov = new BDH_Proveedores();
    }
    
    public void ModificarProveedor(SptProveedor ssprov, String Acodigo){
        Thread t = new Thread(){
            @Override
            public void run(){
                ArrayList al = new ArrayList();
                try {
                    al = bdhprov.ModificarProveedor(ssprov, Acodigo);// se modifica proveedor de la base de datos
                } catch (ClassNotFoundException | SQLException | MalformedParametersException ex) {
                    Logger.getLogger(Popup_ModProveedorThreads.class.getName()).log(Level.SEVERE, null, ex);
                    InfoDialog("java exception", "descripcion", ex.getMessage()); // se muestra un mensaje
                    // en caso de ocurrir una excepcion de java
                }
                if ((boolean) al.get(0)){// si la modificacion fue exitosa
                    InfoDialog("Operacion Exitosa", "Proveedor Modificado", 
                            "El proveedor ha sido modificado exitosamente");
                    Cerrar(); // mensaje que indica que la operacion fue exitosa
                }
                else{// en caso contrario
                    ErrorDialog("No se Pudo Modificar Proveedor", "Descripcion del problema", 
                            (String) al.get(1)); // muestra el error de modificacion
                }
            }
        };
        t.start();
    }
    
    public void EliminarProveedor(String Codigo){
        Thread t = new Thread(){
            @Override
            public void run(){
                boolean r = false;
                try {
                    r = bdhprov.EliminarProveedor(Codigo);// se elimina el proveedor asociado a este codigo
                } catch (ClassNotFoundException | SQLException | MalformedParametersException ex) {
                    Logger.getLogger(Popup_ModProveedorThreads.class.getName()).log(Level.SEVERE, null, ex);
                    InfoDialog("java exception", "descripcion", ex.getMessage()); // se muestra un mensaje
                    // en caso de ocurrir una excepcion de java
                }
                if (r){// si la eliminacino fue exitosa
                    InfoDialog("Operacion Exitosa", "Proveedor Removidor", 
                            "El proveedor ha sido removido exitosamente");
                    Cerrar();
                }
                else{// en caso contrario
                    ErrorDialog("No se Pudo Eliminar", "", "El proveedor no pudo ser eliminado");
                }
            }
        };
        t.start();// se arranca el hilo
    }
    
    public void ErrorDialog(String title, String sub, String mess){// se muestra un mensaje de error
        // de tipo modal usando el controlador del dialogo de modifiacion de proveedores
        Platform.runLater(() -> { // para poner los proveedores encontrados en la busqueda
            popprovcon.ShowError(title, sub, mess);
        });
    }
    
    public void InfoDialog(String title, String sub, String mess){ // se muestra un mensaje informativo
        // de tipo modal usando el controlador del dialogo de modifiacion de proveedores
        Platform.runLater(() -> { // para poner los proveedores encontrados en la busqueda
            popprovcon.ShowInfo(title, sub, mess);
        });
    }
    
    public void Cerrar(){// cierra el dialogo de modificacion de proveedores
        Platform.runLater(() -> { // para poner los proveedores encontrados en la busqueda
            popprovcon.cerrar();
        });
    }
}
