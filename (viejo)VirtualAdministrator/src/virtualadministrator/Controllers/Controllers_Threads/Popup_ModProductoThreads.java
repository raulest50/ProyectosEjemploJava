/*
*
*
 */
package virtualadministrator.Controllers.Controllers_Threads;

import BD.Handlers.BDH_Familias;
import BD.Handlers.BDH_Productos;
import BD.Handlers.BDH_Relaciones;
import BD.Logica.validador;
import Definiciones.SptFamilia;
import Definiciones.SptProducto;
import java.lang.reflect.MalformedParametersException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import virtualadministrator.Controllers.Dialogs.Popup_ModProductoController;

/**
 * @author esteban
 * esta clase posee todos los metodos para generar hilos separados
 * del hilo principal de fx  que ejecutan todas las opeeraciones de la
 * ventana de dialogo de modificacion de productos
 */
public class Popup_ModProductoThreads {
    
    BDH_Productos bdhp;// objeto para acceder la tabla de productos.
    BDH_Familias bdhf; // objeto para acceder la tabla de familias.
    validador val;// objeto de validacion.
    Popup_ModProductoController popmodcon;

    public Popup_ModProductoThreads(Popup_ModProductoController popmodcon) {
        this.popmodcon = popmodcon;
        this.bdhf = new BDH_Familias();
        this.val = new validador();
        this.bdhp = new BDH_Productos();
    }
    
    public void EliminarProducto(String codigo) {
        Thread t = new Thread(){// eliminacion de un producto
            @Override
            public void run(){
                try {
                    bdhp.Eliminar(codigo);
                    InfoDialog("Operacion exitosa", "Producto Eliminado", "El producto ha sido eliminado "
                            + "satisfactoriamente de la base de datos");
                    popmodcon.getMpt().buscar_producto();
                    CerrarDialog();
                } catch (SQLException | MalformedParametersException | ClassNotFoundException ex) {
                    Logger.getLogger(Popup_ModProductoThreads.class.getName()).log(Level.SEVERE, null, ex);
                    ErrorDialog("java Exception", "descripcion:", ex.getMessage());
                }
            }
        };
        t.start();// se arranca el hilo
    }
    
    public void ModificarProducto(String codigo, SptProducto spp) {
        Thread t = new Thread(){
            @Override
            public void run(){
                ArrayList al;
                try {
                    al = bdhp.Modificar(spp, codigo);
                    
                    if((boolean) al.get(0)){
                        popmodcon.getMpt().buscar_producto(); // se refresca la tabla de buscar productos
                        //para evitar que quede el registro de este mismo producto 
                        //que se esta modificando pero desactualizado
                        
                        BDH_Relaciones bdhr = new BDH_Relaciones(); //sirve para revisar si el producto 
                        //que se esta modificando esta dentro de una relacion de productos
                        
                        String grupo = bdhr.GetGrupo(codigo); //numero de grupo del producto 
                        //para las relaciones (si no tiene ninguna relacion con otros prodcutos retorna "")
                        if(!grupo.equals("")){// si el producto tienen un grupo asociado 
                            InfoDialog("El producto tiene un grupo asociado", "",
                                "Se van a generar cambios en cascada, ya que el producto tiene\n "
                                        + "relaciones de precio configuradas.");
                            bdhr.AplicarCambiosPrecio(grupo, spp, codigo);
                        }
                        
                        popmodcon.getMpt().buscar_producto(); // se refresca la tabla de buscar productos
                        //para evitar que quede el registro de este mismo producto 
                        //que se esta modificando pero desactualizado
                        InfoDialog("Operacion Exitosa", "Producto Modificado",
                                "El producto ha sido modificado exitosamente en la base de datos :)");
                        CerrarDialog();// se cierra la ventana de dialogo de modificaicon de productos
                    }
                    else ErrorDialog("El producto No Ha Sido Modificado",
                            "Los errores se listan a continuacion:",
                            (String) al.get(1));
                    
                }catch (SQLException | MalformedParametersException | ClassNotFoundException ex) {
                    Logger.getLogger(Popup_ModProductoThreads.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        t.start();
    }

    public void CargarComboFamilia() {
        Thread t = new Thread(){
            @Override
            public void run(){
                ArrayList<SptFamilia> fam = bdhf.TraerTodas();
                SetComboFamilia(fam);
            }
        };
        t.start();
    }
    
    public void SetComboFamilia(ArrayList<SptFamilia> familias){
        String[] fls = new String[familias.size()];
        for (int i=0 ; i<familias.size() ; i++){
            fls[i] = familias.get(i).getId();
        }// se arman el String[]
        Platform.runLater( () ->{
            popmodcon.SetCombo(fls);
        });
    }
    
    
    public void ErrorDialog(String title, String subtitle, String Message){
        Platform.runLater( () ->{
            popmodcon.ShowError(title, subtitle, Message);
        });
    }
    
    public void InfoDialog(String title, String subtitle, String Message){
        Platform.runLater( () ->{
            popmodcon.ShowInfo(title, subtitle, Message);
        });
    }
    
    public void CerrarDialog(){
        Platform.runLater( () ->{
            popmodcon.cerrar();
        });
    }
    

    
}
