
package virtualadministrator.Controllers;

import BD.CodigoBarras;
import BD.Handlers.BDH_Productos;
import Configuracion.ConfiKeys;
import Configuracion.config;
import Definiciones.SptProducto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import virtualadministrator.Controllers.Controllers_Threads.ModificarThreads;
import virtualadministrator.Controllers.Dialogs.Popup_ModProductoController;
import virtualadministrator.FXMLControllerSKL;

/**
 * FXML Controller class
 * @author esteban
 */
public class ModificarController extends FXMLControllerSKL{
    
    //elementos gui
    
    @FXML 
    public ToggleGroup TogGroup;
    
    @FXML 
    public RadioButton RadioBDescripcion,RadioBFullcod,RadioBLastcod;
    
    @FXML 
    public Button BotonBorrar,BotonBuscar,BotonModificar;
    
    @FXML 
    public TextField TextFieldBuscar;
    
    @FXML 
    public TableView<SptProducto> TableVResultados;
    
    @FXML 
    public TableColumn<SptProducto, String> TCCodigo, TCDescripcion, TCLastup, 
            TCFamilia;
    
    @FXML
    public TableColumn<SptProducto, Integer> TCPvpublico, TCIva, TCPvtienda,
            TCCosto, TCStock;
    
    @FXML 
    public Label LabEstadoBusqueda;
    
    ProductosController ProductosParentController;
    
    //demas objetos para procesamiento
    
    public ObservableList<SptProducto> Arraylista_productos;
    public ModificarThreads mt;
    public ContextMenu rg_click_menu;
    public config MyConf = new config();
    
    
    
    //metodos:-->
    @FXML
    public void initialize() {
        mt = new ModificarThreads(this);
        FormarRadioBGroup(); // se configura los radio botones
        ConfigureTableColumns(); // se configura el tableview
        configMenuItem(); // configura el click derecho para las filas del table view.
    }
    
    
    @FXML 
    public void OnClickBotonBuscar(MouseEvent event){
        buscar_producto();
    }
    
    @FXML 
    public void OnActionTexbuscar(ActionEvent event){
        buscar_producto();
    }
    
    @FXML 
    public void OnClickBotonBorrar(MouseEvent event){
        TextFieldBuscar.setText("");
        TextFieldBuscar.requestFocus();
    }
    
    @FXML 
    public void OnClickBotonModificar(MouseEvent event){
        
        SptProducto sptp = TableVResultados.getSelectionModel().getSelectedItem(); // se obtiene el item seleccionado
        
        if(sptp == null) ShowInfo("Advertencia", "No ha seleccionado ningun producto", "Primero debe seleccionar un producto de la tabla. \n"
                    + "Cuando un producto esta seleccionado toda su fila se \n"
                + "se pone de color rosado");
        
        else abrir_mod_dialog(event, sptp);
    }
    
    @FXML
    public void OnClickRadioBdescri(MouseEvent event){
        TextFieldBuscar.requestFocus();
    }
    
    
    @FXML
    public void OnClickRadblastcod(MouseEvent event){
        TextFieldBuscar.requestFocus();
    }
    
    
    @FXML
    public void OnClickRadbcodeexact(MouseEvent event){
        TextFieldBuscar.requestFocus();
    }
    
    @FXML
    public void FormarRadioBGroup() {
        
        TogGroup = new ToggleGroup();
        RadioBDescripcion.setToggleGroup(TogGroup);
        RadioBDescripcion.setSelected(true);
        RadioBLastcod.setToggleGroup(TogGroup);
        RadioBFullcod.setToggleGroup(TogGroup);
    }
    
    @FXML
    public void ConfigureTableColumns(){
        TCCodigo.setCellValueFactory(new PropertyValueFactory<>("Codigo"));
        TCDescripcion.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
        TCPvpublico.setCellValueFactory(new PropertyValueFactory<>("Pvpublico"));
        TCPvtienda.setCellValueFactory(new PropertyValueFactory<>("Pvtienda"));
        TCCosto.setCellValueFactory(new PropertyValueFactory<>("Costo"));
        TCIva.setCellValueFactory(new PropertyValueFactory<>("Iva"));
        TCLastup.setCellValueFactory(new PropertyValueFactory<>("LastUp"));
        TCFamilia.setCellValueFactory(new PropertyValueFactory<>("Familia"));
        TCStock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
    }
    
    @FXML
    public void OnClickBotonBarras(MouseEvent evt){
        // se revisa si hay elemento seleccionado
        SptProducto sptp = TableVResultados.getSelectionModel().getSelectedItem(); // se obtiene el item seleccionado
        
        // si no hay producto seleccionado de la tabla entonces se
        if(sptp == null){ // se muestra al usuario un promt para que ingrese un
            //codigo arbitrario
            String arbcod = this.ShowInput("Codigo Arbitrario 128",
                    "No puede dejar el campo vacio\n", "No ingresar mas de 19 caracteres\n "
                            + "solo ingresar numeros o letras, NO USAR CARACTERES EXTRAÑOS", "");
            if(arbcod== null || arbcod.equals("")){
                ShowError("Error", "No puede dejar el campo vacio", "");
            }
            else{ // en caso de que el codigo arbitrario sea valido
                
                CodigoBarras cb = new CodigoBarras();
                //si no hay un directorio por defecto configurado ->
                if ( MyConf.Get_string(ConfiKeys.keyDirCod).equals(ConfiKeys.nulo) ||
                        MyConf.Get_string(ConfiKeys.keyDirCod).equals("")){
                    // se lanza al usuario un filechooser. Se debe escoger la carpeta donde se
                    //guardara el png del codigo de barras del producto seleccionado (en ccaso de no tener directorio
                    // por defecto confifurado.
                    try{
                        DirectoryChooser dc = new DirectoryChooser();
                        File fi = dc.showDialog(null);
                        String dir = fi.toString();
                        if(cb.Png128(dir, arbcod)){
                            ShowInfo("Generacion exitosa", "",
                                    "El codigo de barras ha sido generado exitosamente");
                        }
                        else{
                            ShowError("Ha ocurrido un problema", "", "Ha ocurrido un problema a la"
                                    + "hora de generar el codigo. por favor comuniquese con"
                                    + "el pdesarrollador del programa");
                        }
                    } catch(NullPointerException ex){
                        ex.printStackTrace();
                    }
                    
                    if(cb.Png128(MyConf.Get_string(ConfiKeys.keyDirCod), arbcod)){
                        ShowInfo("Generacion exitosa", "",
                                "El codigo de barras ha sido generado exitosamente");
                    }
                    else{
                        ShowError("Ha ocurrido un problema", "", "Ha ocurrido un problema a la"
                                + "hora de generar el codigo. por favor comuniquese con"
                                + "el pdesarrollador del programa");
                    }
                }
                
                else{
                    if(cb.Png128(MyConf.Get_string(ConfiKeys.keyDirCod), arbcod)){
                        ShowInfo("Generacion exitosa", "",
                                "El codigo de barras ha sido generado exitosamente");
                    }
                    else{
                        ShowError("Ha ocurrido un problema", "", "Ha ocurrido un problema a la"
                                + "hora de generar el codigo. por favor comuniquese con"
                                + "el pdesarrollador del programa");
                    }
                }
                
            }
            /*
            ShowInfo("Advertencia", "No ha seleccionado ningun producto", "Primero debe seleccionar un producto de la tabla. \n"
            + "Cuando un producto esta seleccionado toda su fila se \n"
            + "se pone de color rosado");*/
        }
        else{
            
            CodigoBarras cb = new CodigoBarras();
            if ( MyConf.Get_string(ConfiKeys.keyDirCod).equals(ConfiKeys.nulo) ||
                    MyConf.Get_string(ConfiKeys.keyDirCod).equals("")){
                // se lanza al usuario un filechooser. Se debe escoger la carpeta donde se
                //guardara el png del codigo de barras del producto seleccionado
                try{
                    DirectoryChooser dc = new DirectoryChooser();
                    File fi = dc.showDialog(null);
                    String dir = fi.toString();
                    
                    if(cb.Png128(dir, sptp.getCodigo())){
                        ShowInfo("Generacion exitosa", "", "El codigo de barras ha sido generado exitosamente");
                    }
                    else{
                        ShowError("Ha ocurrido un problema", "", "Ha ocurrido un problema a la"
                                + "hora de generar el codigo. por favor comuniquese con"
                                + "el pdesarrollador del programa");
                    }
                } catch(NullPointerException ex){
                    ex.printStackTrace();
                }
            }
            
            else{
                if(cb.Png128(MyConf.Get_string(ConfiKeys.keyDirCod), sptp.getCodigo())){
                    ShowInfo("Generacion exitosa", "",
                            "El codigo de barras ha sido generado exitosamente");
                }
                else{
                    ShowError("Ha ocurrido un problema", "", "Ha ocurrido un problema a la"
                            + "hora de generar el codigo. por favor comuniquese con"
                            + "el pdesarrollador del programa");
                }
            }
        }
    }
    
    
    public void buscar_producto(){
        
        //obtencion Datos
        String seleccion = getRadioButtonSelected(TogGroup);
        String busqueda = TextFieldBuscar.getText();
        
        BDH_Productos bdh_productos = new BDH_Productos();//creacion objeto que ejecuta aciones sobre la bd
        // en cualqiera de los 3 casos se crea un nuevo hilo
        if (seleccion.equals("RadioBDescripcion")) mt.BusquedaDescripcion(busqueda);
        if(seleccion.equals("RadioBLastcod")) mt.BusquedaLastCod(busqueda);
        if(seleccion.equals("RadioBFullcod")) mt.BusquedaCodExact(busqueda);
        //cada hilo muestra los resultados empleado esta misma clase FXMLModificarProductosTabController
        //mediante el uso del la funcion Plataform.runLater(new runnable(){...});
    }
    
    public void SetTableV(ArrayList<SptProducto> result_LProductos){
        Arraylista_productos = FXCollections.observableArrayList(result_LProductos);
        TableVResultados.setItems(Arraylista_productos);
    }
    
    
    public String getRadioButtonSelected(ToggleGroup tempTog){
        
        RadioButton tempRadb = (RadioButton) TogGroup.getSelectedToggle();
        return tempRadb.getId();
    }
    
    public void abrir_mod_dialog(MouseEvent event, SptProducto sptp){
        
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("virtualadministrator/FXML/Dialogs/Popup_ModProducto.fxml"));
            Parent root = cargador.load(); // se usa el fxml para cargar tanto la gui como el controlador del dialogo de
            Stage st = new Stage();// modificacion
            st.setScene(new Scene(root));
            st.initModality(Modality.WINDOW_MODAL); // se fuerza el focus a al dialogo de modificacion
            st.initOwner( ((Node) event.getSource()).getScene().getWindow()); 
            Popup_ModProductoController c = cargador.<Popup_ModProductoController>getController(); // se obtiene el controlador
            c.setP(sptp, this);// se usa esta instancia del controlador para enviar el producto seleccionado
            c.init();// se inicializan los valores de la ventana
            st.show(); // se muestra la ventana
            
        } catch (IOException ex) {
            Logger.getLogger(ModificarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setEstado(String estado){// metodo para actualizar
        // el estado de la busqueda
        LabEstadoBusqueda.setText(estado);
    }
    
    
    /**
     * Este metodo configura el click derecho en las filas 
     * de la tabla de resultados de busqueda para poder realizar 
     * configuraciones de relacion en los productos.
     * y otras operaciones como copiar contenido al portapapeles.
     */
    public void configMenuItem(){
        
        rg_click_menu = new ContextMenu();
        
        MenuItem mi1 = new MenuItem("Eviar a tabla para relaciones");
        MenuItem mi2 = new MenuItem("Enviar a Ventana De Analisis");
        MenuItem mi3 = new MenuItem("Copiar Codigo"); // copia codigo barras producto seleccionado al portapapeles
        MenuItem mi4 = new MenuItem("Copiar Descripcioon"); // copia Descripcion producto seleccionado al portapapeles
        // a continucion se establece la accion a ejecutar cuando
        // se selecciona enviar a tabla para relaciones
        mi1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                SptProducto sptp = TableVResultados.getSelectionModel().getSelectedItem(); // se obtiene el item seleccionado
                
                if(sptp == null) ShowInfo("Advertencia", "No ha seleccionado ningun producto", "Primero debe seleccionar un producto de la tabla. \n"
                        + "Cuando un producto esta seleccionado toda su fila se \n"
                        + "se pone de color rosado");
                
                else ProductosParentController.relacionesController.AddProd2Table(sptp);
            }
        });
        
        mi3.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                SptProducto sptp = TableVResultados.getSelectionModel().getSelectedItem(); // se obtiene el item seleccionado
                
                if(sptp == null) ShowInfo("Advertencia", "No ha seleccionado ningun producto", "Primero debe seleccionar un producto de la tabla. \n"
                        + "Cuando un producto esta seleccionado toda su fila se \n"
                        + "se pone de color rosado");
                
                else{ //ProductosParentController.relacionesController.AddProd2Table(sptp);
                    final Clipboard clipboard = Clipboard.getSystemClipboard();
                    final ClipboardContent content = new ClipboardContent();
                    content.putString(sptp.getCodigo());
                    clipboard.setContent(content);
                    
                }
            }
        });
        
        // copiar descripcion al portapapeles
        mi4.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                SptProducto sptp = TableVResultados.getSelectionModel().getSelectedItem(); // se obtiene el item seleccionado
                
                if(sptp == null) ShowInfo("Advertencia", "No ha seleccionado ningun producto", "Primero debe seleccionar un producto de la tabla. \n"
                        + "Cuando un producto esta seleccionado toda su fila se \n"
                        + "se pone de color rosado");
                
                else{ //ProductosParentController.relacionesController.AddProd2Table(sptp);
                    final Clipboard clipboard = Clipboard.getSystemClipboard();
                    final ClipboardContent content = new ClipboardContent();
                    content.putString(sptp.getDescripcion());
                    clipboard.setContent(content);
                    
                }
            }
        });
        
        rg_click_menu.getItems().addAll(mi1, mi2, mi3, mi4);
        TableVResultados.setContextMenu(rg_click_menu);
        
        rg_click_menu.getStyleClass().add("rgcmenu");
    }

    /**
     * SetProductosParentController
     * Este metodo es usado por el controlador del tab productos.
     * gracias a que la pantalla de modificar fue injectado
     * en el fxml por el tag  include, el controlador de productos
     * tiene acceso al controlador de nmodificar, como una relacion
     * child parent. para poder acceder de un controlador paarent a un
     * controlador child se debe seguir la siguiente notacion:
     * //@FXML JavaClassNameController fx:id+Controller;
     * 
     * declarando de esta manera, el obtejo es inicializado automaticamente 
     * por fx.
     * @param aThis ProductosController (el ParentController) 
     */
    void SetProductosParentController(ProductosController aThis) {
        this.ProductosParentController = aThis;
    }
    
    
}
