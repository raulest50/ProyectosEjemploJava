package virtualadministrator.Controllers;

import BD.Handlers.BDHCreator;
import BD.Handlers.BDH_Inventarios;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import virtualadministrator.FXMLControllerSKL;
import Definiciones.SptInventario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import virtualadministrator.Controllers.Dialogs.Popup_LiquidacionController;


/**
 * FXML Controller class
 *
 * @author esteban
 */
public class InventariosController extends FXMLControllerSKL{
    
    
    //objetos de java FX
    @FXML
    public Label LEInventario;
    
    @FXML
    public TableView<SptInventario> TVInventarios;
    
    @FXML
    public TableColumn<SptInventario, String> TCNombre;
    
    @FXML
    public Button BIniciar, BTerminar, BEliminar, BLiquidar, BCopiaEscritorio, BCopiaCorreo;
    
    @FXML 
    public TextField TFCorreo;
    
    
    public BDH_Inventarios bd_inv;
    
    public String invt_no = "No hay ningun inventario en proceso",
            invt_yes = "Inventario En Proceso:",
            inv_activo_warning = "Para poder eliminar una tabla de inventario no pude haber ningun inventario"
                    + "en proceso, primero debe dar click en el boton Terminar Inventario.";
    
    @FXML
    public void initialize() {
        // config table view
        this.ConfigTableView();
        // cargar inventarios existentes en table view
        bd_inv = new BDH_Inventarios();
        refresh_table();
        // cargar estado actual del inventario
        boolean estado_inventario = false;
        try{
            estado_inventario = bd_inv.getEstadoInvt();
        } catch(NullPointerException ex){
            BDHCreator bdhc = new BDHCreator();
            bdhc.eje(bdhc.insert_property1);
            bdhc.eje(bdhc.insert_property2);
        }
        if(estado_inventario) SetLabelEstado(invt_yes + bd_inv.getNombreTabAct());
        else this.SetLabelEstado(invt_no);
    }
    
    @FXML // liquidar el inventario seleccionado
    public void ClickLiquidar(MouseEvent event){
        // obtener item seleccioando
        SptInventario inv = TVInventarios.getSelectionModel().getSelectedItem();
        if(inv == null) this.ShowWarning("Hay un Problema", "No hay inventario Seleccionado", "Debe tener seleccionado un inventario de"
                + "la tabla para poder hacer una liquidacion de inventario");
        else{
            try {
                FXMLLoader cargador = new FXMLLoader(getClass().getClassLoader().getResource("virtualadministrator/FXML/Dialogs/Popup_Liquidacion.fxml"));
                Parent root = cargador.load(); // se usa el fxml para cargar tanto la gui como el controlador del dialogo de
                Stage st = new Stage();// modificacion
                st.setScene(new Scene(root));
                st.initModality(Modality.WINDOW_MODAL); // se fuerza el focus a al dialogo de modificacion
                st.initOwner( ((Node) event.getSource()).getScene().getWindow());
                Popup_LiquidacionController c = cargador.<Popup_LiquidacionController>getController(); // se obtiene el controlador
                c.tabla = inv.getNombreTabla();
                c.init();
                st.show(); // se muestra la ventana
            } catch (IOException ex) {
                Logger.getLogger(ModificarController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML // eliminar el inentario seleccionado
    public void ClickEliminar(MouseEvent event){
        // verificar estado de inventario
        if(bd_inv.getEstadoInvt()){
            ShowWarning("Hay Un Problema", "Hay Un inventario en proceso", "Para"
                    + " poder eliminar una tabla de inventario no pude haber ningun inventario"
                    + "en proceso, primero debe dar click en el boton Terminar Inventario");
        }
        else{
            SptInventario inv = TVInventarios.getSelectionModel().getSelectedItem();
            if(inv == null) this.ShowWarning("Hay un Problema", "No hay inventario Seleccionado", "Debe tener seleccionado un inventario de"
                    + "la tabla para eliminarlo");
            else{
                bd_inv.Eliminar_Inventario(inv.getNombreTabla());
                refresh_table();
                ShowInfo("Operacion Exitosa", "Inventario Eliminado", "El inventario:" + inv.getNombreTabla() + 
                        "ha sido eliminado.");
            }
        }
    }
    
    @FXML // iniciar inventario ( crea una nueva tabla en la base de datos inventarios)
    public void ClickIniciar(MouseEvent event){
        
        // verificar estado de inventario
        if(bd_inv.getEstadoInvt()){
            ShowWarning("Hay Un Problema", "Hay Un inventario en proceso", "Para"
                    + " poder iniciar una nueva tabla de inventario no pude haber ningun inventario"
                    + "en proceso, primero debe dar click en el boton Terminar Inventario");
        }
        else{
            bd_inv.iniciar_inventario();
            refresh_table();
            SetLabelEstado(invt_yes+bd_inv.getNombreTabAct());
        }
    }
    
    @FXML // terminar inventario - cierra la posibilidad de agregar nuevos conteos
    public void ClickTerminar(MouseEvent event){//a la tabla creada con iniciar inventario.
        bd_inv.Modify_Estado(false);
        SetLabelEstado(invt_no);
    }
    
    @FXML
    public void ClickReactivar(MouseEvent event){
        // obtener item seleccioando
        
         // verificar estado de inventario
        if(bd_inv.getEstadoInvt()){
            ShowWarning("Hay Un Problema", "Hay Un inventario en proceso", "Para"
                    + " poder reactivar una tabla de inventario no pude haber ningun inventario"
                    + "en proceso, primero debe dar click en el boton Terminar Inventario");
        }
        else{
            SptInventario inv = TVInventarios.getSelectionModel().getSelectedItem();
            if(inv == null) this.ShowWarning("Hay un Problema", "No hay inventario Seleccionado", "Debe tener seleccionado un inventario de"
                    + "la tabla para poder reactivarlo");
            else{
                bd_inv.Modify_Estado(true);
                bd_inv.Modify_Table_Name(inv.getNombreTabla());
                SetLabelEstado(invt_yes+bd_inv.getNombreTabAct());
            }
        }
    }
    
    @FXML // crea un dump de la tabla seleccinada de invetario en el escritorio
    public void ClickCopiaEscritorio(MouseEvent event){
        
    }
    
    @FXML // crea un dump de la tabla de inventario seleccionada y lo envia
    public void ClickCopiaCorreo(MouseEvent event){ // como archivo adjunto al correo especificado.
        
    }
    
    public void ConfigTableView(){
        TCNombre.setCellValueFactory(new PropertyValueFactory<>("nombreTabla"));
    }
    
    public void SetTableView(ArrayList<SptInventario> ListaInventarios){
        // metodo para agregar elemenos al tableview
        ObservableList<SptInventario> Array_Inventarios = FXCollections.observableArrayList(ListaInventarios);
        TVInventarios.setItems(Array_Inventarios);
    }
    
    public void SetLabelEstado(String estado){
        LEInventario.setText(estado);
    }
    
    public void refresh_table(){
        ArrayList<SptInventario> lista_inventarios = bd_inv.getTablesInvt();
            SetTableView(lista_inventarios);
    }
}
