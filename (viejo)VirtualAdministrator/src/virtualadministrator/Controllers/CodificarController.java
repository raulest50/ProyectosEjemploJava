package virtualadministrator.Controllers;

import Configuracion.config;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import Definiciones.SptProducto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import virtualadministrator.Controllers.Controllers_Threads.CodificarThreads;
import virtualadministrator.FXMLControllerSKL;
import virtualadministrator.FXMLVirtualAdministratorController;

/**
 * FXML Controller class
 *
 * @author esteban
 */
public class CodificarController extends FXMLControllerSKL{

    //elementos de la GUI en la pesataña codificar producto
    @FXML 
    public Label LabelFecha, labelNProductos; // indica la fecha y hora actual del sistema
    
    @FXML 
    public TextField TextFieldCodBar, TextFieldDescri, TextFieldCosto,
            TextFieldPublico, TextFieldTienda, TextFieldIva, TextFieldStock; // campos de texto para
    
    // la codificacion de productos
    @FXML 
    public Button ButtonIngresarProducto, ButtonBorrarCampos; // botones para ingresar productos
    
    @FXML 
    public ComboBox ComboFamilia; // elemento para seleccionar la familia
    
    @FXML 
    public AnchorPane AnchorCodi; // representacion de objeto del contenedor de fx de todos los elementos
    
    
    FXMLVirtualAdministratorController mainController;
    
    public CodificarThreads codth;
    
    public config MyConf;
    
    //validador v = new validador(); // objeto para hacer validaciones
    //BDH_Productos bdh_p = new BDH_Productos(); // objeto para la manipulacion de la base de datos de productos
    //BDH_Familias bdfam = new BDH_Familias(); // 
            
            
    @FXML
    public void initialize() {
        codth = new CodificarThreads(this); // se construye al objeto que
        // arranca todos los hilos relacionados con la pantalla de codificacion
        codth.UpdateTimeLabel(); // se arranca un hilo continuo que acualiza la fechaa en una etiqueta  de fx.
        codth.CargarComboFamilia(); // se cargan las familias de productos configuradas en un combo box.
        
        MyConf = new config(); // objeo para acceder a las preferencias guardadas
        
        String prefIva = MyConf.Get_string("D_iva");
        if(prefIva.equals("default")) TextFieldIva.setText("");
        else TextFieldIva.setText(prefIva);// valor por defecto del iva
    }
    
    
    @FXML 
    public void OnClickButtonBorrarCampos(MouseEvent event){
        TextFieldCodBar.setText(""); // se establcen todos los campos como vacios
        TextFieldCosto.setText("");
        TextFieldDescri.setText("");
        TextFieldPublico.setText("");
        TextFieldTienda.setText("");
        String prefIva = MyConf.Get_string("D_iva");
        if(prefIva.equals("default")) TextFieldIva.setText("");
        else TextFieldIva.setText(prefIva);// valor por defecto del iva
        TextFieldStock.setText("");
    }
    
    @FXML 
    public void OnClickIngresar(MouseEvent event){
        CodificarProducto(); // se codifica un producto
    }
    
    @FXML 
    public void BarrasNext(ActionEvent event){
        TextFieldDescri.requestFocus();
    }
    
    @FXML 
    public void DescripcionNext(ActionEvent event){
        TextFieldCosto.requestFocus();
    }
    
    @FXML 
    public void CostoNext(ActionEvent event){
        TextFieldPublico.requestFocus();
    }
    
    @FXML 
    public void PublicoNext(ActionEvent event){
        TextFieldTienda.requestFocus();
    }
    
    @FXML 
    public void TiendaNext(ActionEvent event){
        TextFieldIva.requestFocus();
    }
    
    @FXML 
    public void IvaNext(ActionEvent event){
        ComboFamilia.requestFocus();
    }
    
    public void CodificarProducto(){
        // se recolectan los valores de los campos de texto.
        String Codigo, Descripcion, Costo, PvPublico,
                PvTienda, Iva, Familia, Stock;
        Codigo = TextFieldCodBar.getText();// se obtienenn los valores de los campos de texto
        Descripcion = TextFieldDescri.getText();
        Costo = TextFieldCosto.getText();
        PvPublico = TextFieldPublico.getText();
        PvTienda =  TextFieldTienda.getText();
        Iva = TextFieldIva.getText();
        Familia = ComboFamilia.getValue().toString(); // obtener valor seleccionado del combobox
        Stock = TextFieldStock.getText();
        // si el ampo de stock esta vacion entonces se asume que la cantidad es cero por default.
        if(Stock==null || Stock.equals("")){ 
            Stock = "0";
        }
        // luego se contruye el objeto producto
        SptProducto ssp = new SptProducto(Descripcion, PvPublico, PvTienda, Costo, Costo, Codigo, Iva, Familia, Stock);
        
        // se manda el producto para codificacion en un hilo aparte  de la GUI de fx.
        codth.CodificarProducto(ssp);
    }
    
    public void SetTimeDate(String s){
        this.LabelFecha.setText(s);
    }
    
    public void SetComboFamilia(String[] lista){ // metodo para establecer 
        // valores de un combo box
        ObservableList<String> ofls = FXCollections.observableArrayList(lista); // se arma la lista observable
        ComboFamilia.setItems(ofls);
        ComboFamilia.setValue(ofls.get(0));
    }
    
    public void ActualizarFamilias(){
        codth.CargarComboFamilia();
    }
    
}
