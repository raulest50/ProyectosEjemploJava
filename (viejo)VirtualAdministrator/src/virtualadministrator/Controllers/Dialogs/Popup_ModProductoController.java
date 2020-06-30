package virtualadministrator.Controllers.Dialogs;

import BD.Handlers.BDH_Productos;
import BD.Handlers.BDH_Relaciones;
import BD.Logica.validador;
import Definiciones.SptProducto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import virtualadministrator.Controllers.ModificarController;
import virtualadministrator.Controllers.Controllers_Threads.Popup_ModProductoThreads;
import virtualadministrator.FXMLControllerSKL;


/**
 * FXML Controller class
 * @author esteban
 */
public class Popup_ModProductoController extends FXMLControllerSKL{
    
    
    @FXML TextField TFDescripcion, TFCosto, TFCodigo, TFIva, TFPublico, TFTienda, TFStock;
    @FXML Label LDescripcion, LTienda, LCosto, LIva, LPublico, LFamilia, LCodigo,
            LUltimaAct, LStock;
    @FXML Button BCancelar, BReset, BGuardar;
    @FXML ComboBox ComboFamilia;
    
    BDH_Productos bhp;// para acceder a la base de datos de productos.
    SptProducto ssp;// producto seleccionado previamente de la tabla en la pestana de modificacion.
    validador v = new validador();
    ModificarController mpt; // representacion del controlador de la pestana de modificacion.
    Popup_ModProductoThreads popmt; // permite ejecutar las tareas de este dialogo en hilos separados.
    
    @FXML // equivalente al main para esta  ventana.
    public void init() {
        // inicializacion 
        this.popmt = new Popup_ModProductoThreads(this);
        SetFields();// se rellenan los campos de texto con los valores actuales del producto en la BD
        CargarComboFamilia();// se cargan las familias existentes en el combo box de FX.
    }
    
    @FXML // se cierra la ventana
    public void ClickedButtonCancelar(MouseEvent event){
        cerrar(); // metodo para cerrar la ventana
    }
    
    @FXML // se establecen los valores actuales del producto
    public void ClickedButtonReset(MouseEvent event){ // en los campos de texto
        SetFields();
    }
    
    @FXML
    public void ClickedButtonGuardar(MouseEvent event){
        
        // se guardan las modificaciones al producto.
        SptProducto npr = new  SptProducto(TFDescripcion.getText(), 
                TFPublico.getText(), TFTienda.getText(), TFCosto.getText(), 
                v.obt_fecha(), TFCodigo.getText(), TFIva.getText(), 
                ComboFamilia.getValue().toString(), TFStock.getText());
        /*
        ssp.setCodigo(TFCodigo.getText());
        ssp.setDescripcion(TFDescripcion.getText());
        ssp.setCosto(TFCosto.getText());
        ssp.setIva(TFIva.getText());
        ssp.setPvpublico(TFPublico.getText());
        ssp.setPvtienda(TFTienda.getText());
        ssp.setFamilia(ComboFamilia.getValue().toString());
        */
        
        popmt.ModificarProducto(LCodigo.getText(), npr); // se modifica el producto
        // si este produto pertenece a un grupo 
        //del mismo precio entonces se aplica el cambio de precios a todos los del grupo
        
    }
    
    @FXML
    public  void ClickedButtonEliminar(){// se elimina el producto previamente seleccionado de la tabla.
        popmt.EliminarProducto(LCodigo.getText());
    }

    public void setP(SptProducto p, ModificarController mpt){
        this.ssp = p;// metodo para guardar en este controlador el produccto seleccionado
        this.mpt = mpt;// previamente de la tabla y guardar
    }
    
    public void SetFields(){
        LDescripcion.setText(ssp.getDescripcion());// se establecen la etiquetas
        TFDescripcion.setText(ssp.getDescripcion());// de acuerdo al producto selccionado
        
        LCosto.setText(ssp.getCosto());
        TFCosto.setText(ssp.getCosto());
        
        LPublico.setText(ssp.getPvpublico());
        TFPublico.setText(ssp.getPvpublico());
        
        
        LTienda.setText(ssp.getPvtienda());
        TFTienda.setText(ssp.getPvtienda());
        
        LCodigo.setText(ssp.getCodigo());
        TFCodigo.setText(ssp.getCodigo());
        
        LIva.setText(ssp.getIva());
        TFIva.setText(ssp.getIva());
        
        TFStock.setText(ssp.getStock());
        LStock.setText(ssp.getStock());
        
        LUltimaAct.setText(ssp.getLastUp());
    }
    
    public void cerrar(){ // metodo para cerrar la ventana
        // se obtiene un handle de la ventana actual mediante un boton de la  misma ventana
        //en este caso el mismo asociado a la operacion de cancelar
        Stage stage = (Stage) BCancelar.getScene().getWindow();
        // se cierra la ventana de modificacion
        stage.close();
        mpt.buscar_producto();
    }
    
    public void CargarComboFamilia(){
        popmt.CargarComboFamilia();
    }
    
    public void SetCombo(String[] fls){
        ObservableList<String> ofls = FXCollections.observableArrayList(fls); // se arma la lista observable
        ComboFamilia.setItems(ofls);
        ComboFamilia.setValue(ofls.get(0));
    }

    public ModificarController getMpt() {
        return mpt;
    }
    
}
