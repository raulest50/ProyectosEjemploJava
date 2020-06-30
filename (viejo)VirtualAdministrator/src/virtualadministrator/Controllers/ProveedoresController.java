package virtualadministrator.Controllers;

import Definiciones.SptProveedor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import virtualadministrator.Controllers.Controllers_Threads.ProveedoresThreads;
import virtualadministrator.Controllers.Dialogs.Popup_ModProveedorController;
import virtualadministrator.FXMLControllerSKL;

/**
 * FXML Controller class
 *
 * @author esteban
 */
public class ProveedoresController extends FXMLControllerSKL {

    // tab ingresar proveedores
    @FXML
    public Label LTels, LFax, LDireccion, LVendedor,
            LMail, LUltimaMod, LComent, LCalificacion; // etiquetas en el acordeon de busqueda

    @FXML
    public Button BBuscar, BModifiar, BIngresar, BBorrar;

    @FXML
    public ComboBox ComboCriterio, ComboCalificacion;

    @FXML
    public TextField TFBuscar, TFNombre, TFCodigo, TFFax, TFTel1, TFTel2,// capos de texto en el acordeon de ingreso 
            TFTel3, TFTel4, TFMail, TFDireccion, TFVendedor, TFWeb;

    @FXML
    public TextArea TAClaves;

    @FXML
    public TableView<SptProveedor> TVProveedor;

    @FXML
    public TableColumn<SptProveedor, String> TCNombre, TCTel, TCCodigo;

    // grupos observables para los Combo box de calificacion y tipo de busqueda
    ArrayList<String> ALoca = new ArrayList<>(), ALcb = new ArrayList<>();
    
    ProveedoresThreads provt;

    //tab buscar
    @FXML
    public void initialize() {
        this.SetCombos();
        this.ConfigTableView();
        TVProveedor.getSelectionModel().selectedItemProperty().addListener((newSelection) -> {
            if (!(TVProveedor.getSelectionModel().getSelectedItem() == null)) {
                setSelectedData(TVProveedor.getSelectionModel().getSelectedItem());
            }
            else{
                
            }
        });
        this.provt = new ProveedoresThreads(this);
    }

    @FXML
    public void ClickBuscar(MouseEvent event) {// busqueda de proveedores
        BuscarProv();
    }
    
    @FXML
    public void ActTFBuscar(ActionEvent event){
        BuscarProv();
    }

    @FXML
    public void ClickIngresar(MouseEvent event) {
        // metodo para ingresar un proveedor a la BD
        // nombre codigo fax tel1 tel2 tel3 tel4 email direccion vendedor pgweb calificacion keywords
        SptProveedor spp = new SptProveedor(TFNombre.getText(), TFCodigo.getText(),
                TFFax.getText(), TFTel1.getText().replace(" ", ""),
                TFTel2.getText().replace(" ", ""), TFTel3.getText().replace(" ", ""),
                TFTel4.getText().replace(" ", ""), TFMail.getText(), TFDireccion.getText(),
                TFVendedor.getText(), TFWeb.getText(), ComboCalificacion.getValue().toString(),
                TAClaves.getText()); // los replace agregan comodidad para ingresar los telefonos
        provt.CodificarProveedor(spp);// se arranca el hilo separado de codificacion
        
    }

    @FXML
    public void ClickBorrar(MouseEvent event) {
        // se borran todos los campos de texto
        TFNombre.setText("");
        TFCodigo.setText("");
        TFFax.setText("");
        TFTel1.setText("");
        TFTel2.setText("");
        TFTel3.setText("");
        TFTel4.setText("");
        TFMail.setText("");
        TFDireccion.setText("");
        TFVendedor.setText("");
        TFWeb.setText("");
    }
    
    @FXML
    public void ClickModificar(MouseEvent event){// se abre el dialogo de modificacion
        SptProveedor spp = TVProveedor.getSelectionModel().getSelectedItem();
        if(spp == null) this.ShowInfo("No se puede abrir ventana de modificacion", 
                "No hay ningun proveedor seleccionado", "Primero debe seleccionar un "
                        + "proveedor de la tabla ( queda resaltado de color "
                        + "rojo) antes de abrir la ventana de moddificaciones");
        else abrir_dialog(event, spp);
        Deselect();
    }

    public void SetCombos() {
// se configuran los combo box de calificacion 
        ALoca.add("1");
        ALoca.add("2");
        ALoca.add("3");
        ALoca.add("4");
        ALoca.add("5");
// y de criterio de busqueda
        ALcb.add("Nombre");
        ALcb.add("Palabras Clave");
        ALcb.add("Codigo");
        ALcb.add("Telefono");

        ObservableList<String> oca = FXCollections.observableArrayList(ALoca); // se arma la lista observable
        // a partir de un ArraList<String>
        ComboCalificacion.setItems(oca); // se usa el arraylist observable
        ComboCalificacion.setValue(oca.get(0)); // se establece como seleccionado el primer elemento del grupo
        // para que  no haya posibilidad de usar valor nulo en algun ingreso de producto.

        ObservableList<String> cb = FXCollections.observableArrayList(ALcb); // se arma la lista observable
        ComboCriterio.setItems(cb);
        ComboCriterio.setValue(cb.get(0));
    }
    
    public void ConfigTableView() { // metodo necesario apra configurar
        // el tableview de esta pestaña.
        TCTel.setCellValueFactory(new PropertyValueFactory<>("Telefono1"));
        TCNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        TCCodigo.setCellValueFactory(new PropertyValueFactory<>("Codigo"));
    }
    
    public void SetTableView(ArrayList<SptProveedor> ListaProveedores){
        // metodo para establecer los proveeedores en el tableview
        ObservableList<SptProveedor> Array_Proveedores = FXCollections.observableArrayList(ListaProveedores);
        TVProveedor.setItems(Array_Proveedores);
    }
    
    public void setSelectedData(SptProveedor spp){
        // metodo para mostrar la informacion del proveedor seleccionado en la tabla de los resultados de busqueda
        LTels.setText(spp.getTelefonos());
        LFax.setText(spp.getFax());
        LDireccion.setText(spp.getDireccion());
        LVendedor.setText(spp.getVendedor());
        LMail.setText(spp.getEmail());
        LUltimaMod.setText(spp.getUltMod());
        LCalificacion.setText(spp.getCalificacion());
    }
    
    
    public void Deselect(){
        // se hace reset a la informacion de las etiquetas de info de proveedores
        LTels.setText("-----");
        LFax.setText("-----");
        LDireccion.setText("-----");
        LVendedor.setText("-----");
        LMail.setText("-----");
        LUltimaMod.setText("-----");
        LCalificacion.setText("-----");
    }
    
    public void BuscarProv(){// busqueda de proveedores
        String b = TFBuscar.getText();// se captura la busqueda
        String cb = ComboCriterio.getValue().toString(); // se captura el tipo de busqueda
        provt.BuscarProveedor(b, cb); // se arranca ell hilo separado de busqueda
        Deselect();// se deselecciona de la tabla
    }
    
    public void abrir_dialog(MouseEvent event, SptProveedor spp){
        try {// se abre el dialogo de modificacion de proveedores.
            FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("virtualadministrator/FXML/Dialogs/Popup_ModProveedor.fxml"));
            Parent root = cargador.load(); // se usa el fxml para cargar tanto la gui como el controlador del dialogo de
            Stage st = new Stage();// modificacion
            st.setScene(new Scene(root));
            st.initModality(Modality.WINDOW_MODAL); // se fuerza el focus a al dialogo de modificacion
            st.initOwner( ((Node) event.getSource()).getScene().getWindow()); 
            Popup_ModProveedorController c = cargador.<Popup_ModProveedorController>getController(); // se obtiene el controlador
            c.setProv(spp, this);// se usa esta instancia del controlador para enviar el producto seleccionado
            c.init();// se inicializan los valores de la ventana
            st.show(); // se muestra la ventana
            
        } catch (IOException ex) {
            Logger.getLogger(ModificarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void TFCodigoAction(ActionEvent event){
        TFNombre.requestFocus();
    }
    
    @FXML
    public void TFNombreAction(ActionEvent event){
        TFFax.requestFocus();
    }
    
    @FXML
    public void TFFaxAction(ActionEvent event){
        TFTel1.requestFocus();
    }
    
    @FXML
    public void TFTel1Action(ActionEvent event){
        TFTel2.requestFocus();
    }
    
    @FXML
    public void TFTel2Action(ActionEvent event){
        TFTel3.requestFocus();
    }
    
    @FXML
    public void TFTel3Action(ActionEvent event){
        TFTel4.requestFocus();
    }
    
    @FXML
    public void TFTel4Action(ActionEvent event){
        TFMail.requestFocus();
    }
    
    @FXML
    public void TFMailAction(ActionEvent event){
        TFDireccion.requestFocus();
    }
    
    @FXML
    public void TFDireccionAction(ActionEvent event){
        TFVendedor.requestFocus();
    }
    
    @FXML
    public void TFVendedorAction(ActionEvent event){
        TFWeb.requestFocus();
    }
    
    @FXML
    public void TFWebAction(ActionEvent event){
        TAClaves.requestFocus();
    }
    
}
