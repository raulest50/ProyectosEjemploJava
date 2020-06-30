package virtualadministrator.Controllers.Dialogs;

import BD.Handlers.BDH_Proveedores;
import Definiciones.SptProveedor;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import virtualadministrator.Controllers.ProveedoresController;
import virtualadministrator.Controllers.Controllers_Threads.Popup_ModProveedorThreads;
import virtualadministrator.FXMLControllerSKL;

/**
 * FXML Controller class
 * @author esteban
 * Controlador de la ventana de dialogo para modificar proveedores.
 */

public class Popup_ModProveedorController extends FXMLControllerSKL{
    
    SptProveedor spp;
    ProveedoresController pc;
    
    @FXML TextField TFNombre, TFFax, TFDireccion, TFMail, TFWeb, TFTel1,
             TFTel2, TFTel3, TFTel4, TFVendedor, TFCodigo; // campos de texto
    
    @FXML Label LCodigo, LFax, LNombre, LDireccion, LMail, LWeb, LVendedor, 
            LTel1, LTel2, LTel3, LTel4, LCalificacion;// labels de JavaFX
    
    @FXML ComboBox ComboCalificacion; // coombo box
    
    @FXML TextArea TAClave; // area de texto donde estan las palabras clave
    // del proveedor que normalmente describen los porductos que este vende
    
    @FXML Button BGuardar, BReset, BCancelar, BEliminar; // botones de la ventana
    
    Popup_ModProveedorThreads pprovt;
    
    @FXML
    public void initialize() {
        pprovt = new Popup_ModProveedorThreads(this);
    }
    
    @FXML // equivalente al main para esta  ventana
    public void init() {
        SetFields();
        ConfigComboCalificacion();
    }
    
    @FXML//boton de guardar modificaciones
    public void ClickGuardar(MouseEvent event){
        
        pprovt.ModificarProveedor(this.getProveedor(), LCodigo.getText());
    }
    
    @FXML // boton de eliminar proveedor
    public void ClickEliminar(MouseEvent event){
        
        pprovt.EliminarProveedor(LCodigo.getText());
    }
    
    @FXML// boton de cancelar
    public void ClickCancelar(MouseEvent event){
        this.cerrar();
    }

    
    @FXML//  boton para reestablecer los Textfields con los valores actuales del
    // proveedor.
    public void ClickReset(MouseEvent event){
        TFNombre.setText(spp.getNombre());
        TFCodigo.setText(spp.getCodigo());
        TFFax.setText(spp.getFax());
        TFMail.setText(spp.getEmail());
        TAClave.setText(spp.getkeywords());
        TFTel1.setText(spp.getTelefono1());
        TFTel2.setText(spp.getTelefono2());
        TFTel3.setText(spp.getTelefono3());
        TFTel4.setText(spp.getTelefono4());
        TFVendedor.setText(spp.getVendedor());
        TFDireccion.setText(spp.getDireccion());
        TFWeb.setText(spp.getPaginaWeb());
    }
    
    public void setSpp(SptProveedor spp) {
        this.spp = spp;
    }
    
    public void cerrar(){
        Stage stage = (Stage) BCancelar.getScene().getWindow();
        // se cierra la ventana de modificacion de proveedores
        stage.close();
        pc.BuscarProv(); // se busca de nuevo al cerrar la ventana
        // y asi mostrar resultados actuaalzados.
    }

    private void ConfigComboCalificacion() {
        ArrayList<String> ACal = new ArrayList<>();
        ACal.add("1");
        ACal.add("2");
        ACal.add("3");
        ACal.add("4");
        ACal.add("5");
        ObservableList<String> oca = FXCollections.observableArrayList(ACal);
        ComboCalificacion.setItems(oca);
        ComboCalificacion.setValue(this.spp.getCalificacion());
    }
    
    public void setProv(SptProveedor spp, ProveedoresController pc){
        this.spp = spp;
        this.pc = pc;
    }
    
    public void SetFields(){
        // se establecen las etiqquetas la cuales sirven de recordatorio
        LCodigo.setText(spp.getCodigo());
        LNombre.setText(spp.getNombre());
        LFax.setText(spp.getFax());
        LDireccion.setText(spp.getDireccion());
        LMail.setText(spp.getEmail());
        LWeb.setText(spp.getPaginaWeb());
        LTel1.setText(spp.getTelefono1());
        LTel2.setText(spp.getTelefono2());
        LTel3.setText(spp.getTelefono3());
        LTel4.setText(spp.getTelefono4());
        LCalificacion.setText(spp.getCalificacion());
        LVendedor.setText(spp.getVendedor());
        
        //  establecen los capos de texto los cuales podrian ser modificados
        TFCodigo.setText(spp.getCodigo());
        TFNombre.setText(spp.getNombre());
        TFFax.setText(spp.getFax());
        TFDireccion.setText(spp.getDireccion());
        TFMail.setText(spp.getEmail());
        TFWeb.setText(spp.getPaginaWeb());
        TFTel1.setText(spp.getTelefono1());
        TFTel2.setText(spp.getTelefono2());
        TFTel3.setText(spp.getTelefono3());
        TFTel4.setText(spp.getTelefono4());
        TFVendedor.setText(spp.getVendedor());
        // se establecen las palabras clave
        TAClave.setText(spp.getkeywords());
    }
    
    public ProveedoresController getPC(){
        return this.pc;
    }

    private SptProveedor getProveedor() {
        return new SptProveedor(TFNombre.getText(), TFCodigo.getText(), TFFax.getText(), 
                TFTel1.getText(), TFTel2.getText(), TFTel3.getText(), 
                TFTel4.getText(), TFMail.getText(), TFDireccion.getText(),
                TFVendedor.getText(), TFWeb.getText(), ComboCalificacion.getValue().toString(), 
                TAClave.getText());
    }
    
    
}
