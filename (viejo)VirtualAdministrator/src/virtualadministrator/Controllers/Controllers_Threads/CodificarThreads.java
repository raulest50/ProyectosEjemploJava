package virtualadministrator.Controllers.Controllers_Threads;

import BD.Handlers.BDH_Familias;
import BD.Handlers.BDH_Productos;
import BD.Logica.validador;
import Definiciones.SptFamilia;
import Definiciones.SptProducto;

import java.lang.reflect.MalformedParametersException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import virtualadministrator.Controllers.CodificarController;

/**
 *
 * @author esteban
 */
public class CodificarThreads {
    /* esta clase posee los metodos y objetos necesarios para 
    hacer las operaciones correspondientes 
    en la pantalla de codificacion de productos en hilos separados de el
    hilo de GUI de FX
    */
    
    CodificarController codcon; // controlador de FX para poder acceder mediante
    // plataform.runLater() a la GUI de FX.
    BDH_Productos bdhp;
    BDH_Familias bdhf;
    validador val;
    
    // vanderas para frenar hilos while
    boolean flagTimeLabel; // bandera que permite que el hilo de
    // actualizacion de la etiqueta de la hora del sistema se siga
    // actualizando

    public CodificarThreads(CodificarController codcon) {
        this.codcon = codcon;
        this.bdhp = new BDH_Productos();
        this.bdhf = new BDH_Familias();
        this.val = new validador();
        this.flagTimeLabel = true;
    }
    
    public void UpdateTimeLabel(){
        // este metodo inicia un hilo que mantiene actualizada
        // con la hora del sistema en una etiqueta del controlador
        // Codificacion de productos.
        Thread t = new Thread(){
            @Override
            public void run(){
                final long waitTime = 10*1000;
                while(flagTimeLabel){
                    Platform.runLater(() -> { // se usa para establecer el texto en la etiqueta
                        codcon.SetTimeDate(val.obt_fecha()); // de la gui de fecha y hora
                    });
                    try {
                        Thread.sleep(waitTime);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(CodificarThreads.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        t.setDaemon(true); // esto asegura que este hilo termina tambien cuando el hilo principal termina
        t.start();// arranca este hilo independiente.
    }

    public void CodificarProducto(SptProducto ssp) {
        Thread t = new Thread(){
          @Override
          public void run(){
              ArrayList al = new ArrayList();
              try{
                  al = bdhp.Codificar(ssp);
              }
              catch(SQLException | MalformedParametersException | ClassNotFoundException e){
                  codcon.ShowError("A java exeption has occurred", "e.getMessage()", e.getMessage());
              }
              if((boolean) al.get(0)){
                  InfoDialog("Operacion Exitosa", "El producto codificado"
                          , "El producto ha sido codificado exitosamente ;)");
              } else{
                  ErrorDialog("El producto no pudo ser codificado", 
                          "Los errores se listan a continuacion", (String) al.get(1));
              }
              setLabelNtotProducts();
          }
        };
        t.start();
    }
    
    /**
     * carga las familias actuales a 
     */
    public void CargarComboFamilia(){
        Thread t = new Thread(){
            @Override
            public  void run(){
                setLabelNtotProducts();
                ArrayList<SptFamilia> fl = bdhf.TraerTodas(); // se traen todas las familias
                String[] fls = new String[fl.size()]; // arreglo de strings con el mismo tamalo como familias hay.
                for (int i=0 ; i<fl.size() ; i++){
                    fls[i] = fl.get(i).getId();
                }// se arman el String[]
                // se establecen las familias existentes en un combo box de fx
                Platform.runLater(() -> { 
                    codcon.SetComboFamilia(fls);
                });
            }
        };
        t.start();
    }
    
    public void ErrorDialog(String title, String subtitle, String Message){
        Platform.runLater( () ->{
            codcon.ShowError(title, subtitle, Message);
        });
    }
    
    public void InfoDialog(String title, String subtitle, String Message){
        Platform.runLater( () ->{
            codcon.ShowInfo(title, subtitle, Message);
        });
    }
    
    /**
     * establce mediante la interfaz plataform run later el numero
     * de producos registrados en la tabla de productos mediante la
     * librearia de BDlib
     */
    public void setLabelNtotProducts(){
        Platform.runLater( () ->{
            try{
                codcon.labelNProductos.setText(Integer.toString(bdhp.NumeroTotalProductos()));
            }
            catch(SQLException | MalformedParametersException | ClassNotFoundException ex){
                ex.printStackTrace();
            }
        });
    }
    
}
