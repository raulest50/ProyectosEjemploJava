package virtualadministrator;

import BD.CopiaSeguridad;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.DirectoryChooser;
import virtualadministrator.Controllers.ClientesController;
import virtualadministrator.Controllers.ConfigController;
import virtualadministrator.Controllers.InventariosController;
import virtualadministrator.Controllers.ProductosController;
import virtualadministrator.Controllers.ProveedoresController;
import virtualadministrator.Controllers.ReportesController;
import virtualadministrator.Controllers.TrabajadoresController;

/**
 * FXML Controller class
 * @author esteban
 * 
 * gracias a los include de javafx se tiene acceso a los controladores
 * de los fxml injectados como se puede ver con productos controller
 * y el metodo coditabchan.
 * 
 * hay que notar sin embargo que esto no le da acceso a los subelementos
 * de productos controller como los controllers de codificacion modificacion
 * etc. ya que solo se tiene acceso a los doc de los include inmediatos.
 * por eso hay que usar nesting con los controllers. 
 * ej: para acceder al controllermodificar hay que accederlo como
 * atributo de productoscontroller.
 */
public class FXMLVirtualAdministratorController extends FXMLControllerSKL{
    
    // mensajes para el menu de ayuda
    public String aboutProgram = "Software desarrollado por Raul Esteban Alzate Aristizabal\n"
            + "Escrito completamente en java";
    
    public String aboutCopia = "Este comando genera un archivo dump con toda la informacion \n"
            + "de la  base de datos hasta al momento de generar la copia.\n"
            + "\n"
            + "Dicho archivo dump es de extension .sql y es mas facil restaurar \n"
            + "la informacion contenida en el dump usando la opcion import database\n"
            + "de MySQL WorkBench.";
    
    //anchor pane principal apra obtener objeto del tipo nodo y etc, funciona para labores como "auxiliar"
    @FXML
    public MenuBar BarraMenu;
    
    // controladores inyectados por include fxml
    @FXML
    public ClientesController clientesController;
    
    @FXML
    public InventariosController inventariosController;
    
    @FXML
    public ProductosController productosTabController;
    
    @FXML 
    public ProveedoresController proveedoresController;
    
    @FXML 
    public ReportesController reportesController;
    
    @FXML 
    public TrabajadoresController trabajadoresController;
    
    @FXML 
    public ConfigController configController;
    
    
    /** funcion de inicializacion, se arranca el asistente de licencia si es
     * caso o se continua con la ejecucion normal de la aplicion.
     */
    @FXML 
    public void initialize() {
        
    }
    
    @FXML
    public void AcercaPrograma(ActionEvent event){
        ShowInfo("Acerca Del Programa", "Virtual Administrator v 1.0", aboutProgram);
    }
    
    @FXML
    public void AcercaCopiaSeguridad(ActionEvent event){
        ShowInfo("Acerca de la copia de seguridad", "Que  hace este comando:", aboutCopia);
    }
    
    /**
     * genera un archivo dump con las bases de datos.
     */
    @FXML
    public void GenerarDump(){
        DirectoryChooser dc = new DirectoryChooser();
        File fi = dc.showDialog(null);
        String dir = fi.toString();
        if (CopiaSeguridad.GenerarCopiaSeguridad(dir)) ShowInfo("Copia Generada", ":)", "La copia se ha "
                + "generado exitosamente en el escritorio");
    }
    
    /**
     * metodo que se ejecuta cuando se hace click en el tab de productos
     * producto.
     * Actualiza la etiqueta de numero total de filas en la tabla de productos
     * y el combo box con las familias actuales. Esto es necesario ya que 
     * si se agrega o se elimana unaa familia en el tab de configuracion
     * y no se actualiza dicho cambio en la pantalla de codificacion
     * entonces no se podra codificar con familiass nuevas configuradas
     * o asignar familias que ya no existen (porque fueron eliminadas), es decir
     * ocurririan inconsistencias en bd.
     * @param event 
     */
    @FXML
    public void CodiTabChan(Event event){
        productosTabController.codificarTabController.ActualizarFamilias();
        productosTabController.codificarTabController.codth.setLabelNtotProducts();
    }
    
    /**
    public void OpenLiz(){
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("virtualadministrator/FXML_ModalLiz.fxml"));
            Parent root = cargador.load(); // se usa el fxml para cargar tanto la gui como el controlador del dialogo de
            Stage st = new Stage();// modificacion
            st.setScene(new Scene(root));
            st.initModality(Modality.WINDOW_MODAL); // se fuerza el focus a al dialogo de modificacion
            //st.initOwner(BarraMenu.getScene().getWindow());
            //st.initOwner( ((Node) event.getSource()).getScene().getWindow());
            st.show(); // se muestra la ventana
            
        } catch (IOException ex) {
            Logger.getLogger(ModificarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */ 
}
