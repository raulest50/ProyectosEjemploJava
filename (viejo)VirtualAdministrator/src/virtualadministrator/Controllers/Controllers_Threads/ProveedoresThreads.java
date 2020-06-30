/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualadministrator.Controllers.Controllers_Threads;

import BD.Handlers.BDH_Proveedores;
import Definiciones.SptProveedor;
import java.lang.reflect.MalformedParametersException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import virtualadministrator.Controllers.ProveedoresController;

/**
 *
 * @author esteban
 */
public class ProveedoresThreads {
    
    ProveedoresController prcon;
    BDH_Proveedores bdhprov;

    public ProveedoresThreads(ProveedoresController prcon) {
        this.prcon = prcon;
        bdhprov = new BDH_Proveedores();
    }
    
    public void setTview(ArrayList<SptProveedor> prov){
        Platform.runLater(() -> { // para poner los proveedores encontrados en la busqueda
            prcon.SetTableView(prov);
        });
    }

    public void BuscarProveedor(String b, String cb) {
        Thread t = new Thread(){// se arranca el hilo de busqueda de proveedor
            @Override
            public void run(){
                ArrayList<SptProveedor> Lprovs = new ArrayList<>();
                try {
                    Lprovs = bdhprov.BuscarProveedor(b, cb);
                } catch (SQLException | ClassNotFoundException | MalformedParametersException ex) {
                    Logger.getLogger(ProveedoresThreads.class.getName()).log(Level.SEVERE, null, ex);
                    ErrorDialog("exepcion", "", ex.getMessage());
                }
                prcon.SetTableView(Lprovs);
            }
        };
        t.start();
    }

    public void CodificarProveedor(SptProveedor spprov) {
        Thread t = new Thread(){// se arranca el hilo de codificacion de proveedor
            @Override
            public void run(){
                ArrayList al = new ArrayList();
                try {
                    al = bdhprov.IngresarProveedor(spprov);
                } catch (SQLException | ClassNotFoundException | MalformedParametersException ex) {
                    Logger.getLogger(ProveedoresThreads.class.getName()).log(Level.SEVERE, null, ex);
                    ErrorDialog("exepcion", "", ex.getMessage());
                }
                if((boolean) al.get(0)){ // si la codificacion fue exitosa:
                    InfoDialog("Operacion Exitosa", "Nuevo Proveedor Agregado", 
                            "El prooveedor ha sido codificado exitosamente");
                }
                else{ // si la codifiicacion no fue exitosa
                    InfoDialog("Hay Un Problema", "descripcion", (String) al.get(1));
                }
            }
        };
        t.start();
    }
    
    public void ErrorDialog(String title, String sub, String mess){
        Platform.runLater(() -> { // para poner los proveedores encontrados en la busqueda
            prcon.ShowError(title, sub, mess);
        });
    }
    
    public void InfoDialog(String title, String sub, String mess){
        Platform.runLater(() -> { // para poner los proveedores encontrados en la busqueda
            prcon.ShowInfo(title, sub, mess);
        });
    }
}
