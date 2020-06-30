package virtualadministrator.Controllers;

import BD.Handlers.BDH_Relaciones;
import BD.Logica.validadorRelaciones;
import Definiciones.SptProducto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import virtualadministrator.FXMLControllerSKL;


/**
 * FXML Controller class
 *
 * @author esteban
 */
public class RelacionesController extends FXMLControllerSKL {

    @FXML
    public Slider SliderPormayor, SliderDetal; // los sliders para el porcentaje
    // de relacion de composicion rigida.
    
    @FXML 
    public TextField TFPormayor, TFDetal; // campos de texto editable para
    // los porcentajes de ganancia de los sliders.
    
    @FXML
    public ComboBox<String> ComboRelaciones;// muestra los tipos de relaciones
    // configurables.
    
    @FXML
    public TableView<SptProducto> TVProdRelaciones;
    
    @FXML
    public TableColumn<SptProducto, String> TColNombre;
    
    @FXML
    public TableColumn<SptProducto, Integer> TColCosto;
    
    @FXML
    public TableColumn<SptProducto, Integer> TColTienda;
    
    @FXML
    public TableColumn<SptProducto, Integer> TColPublico;
    
    ObservableList<SptProducto> ListaProductos;
    ObservableList<String> CmbRelaciones;
    
    /*
    ya no es necesario usar implements initializable.
    ni usar el @Override que ello implica (pasando lor parametros url etc.
    con fx8 solo basta con que la funcion se llame initialize() y tenga la
    etiqueta @FXML estos dos ultimos son super importantisimos.
    */
    @FXML
    public void initialize() {
        this.configSliders();
        this.ConfTColumns();
        this.ConfComboRelaciones();
    }    
    
    /*
    Este metodo hace la configuracion inicial necesaria para la pantalla de 
    configuracion. Este se corre en initialize solo se crea el metodo por organi-
    zacion de codigo.
    */
    public void configSliders(){
        // se configuran los sliders
        SliderDetal.setMin(0.0);
        SliderDetal.setMax(100.0);
        SliderPormayor.setMin(0.0);
        SliderPormayor.setMax(100.0);
        
        SliderDetal.setShowTickLabels(false);
        SliderDetal.setShowTickMarks(true);
        
        SliderPormayor.setShowTickLabels(false);
        SliderPormayor.setShowTickMarks(true);
        
        SliderDetal.setBlockIncrement(0.1);
        SliderPormayor.setBlockIncrement(0.1);
        
        SliderDetal.setMajorTickUnit(1);
        SliderDetal.setMinorTickCount(1);
        
        SliderPormayor.setMajorTickUnit(1);
        SliderPormayor.setMinorTickCount(1);
        
        SliderPormayor.setSnapToTicks(true);
        SliderDetal.setSnapToTicks(true);
        
        updateTFPormayor();
        updateTFDetal();
        
    }
    
    @FXML
    public void onMouseReleasedSliderPormayor(MouseEvent mve){
        updateTFPormayor();
    }
    
    @FXML
    public void onMouseReleasedSliderDetal(MouseEvent mve){
        updateTFDetal();
    }
    
    
    @FXML
    public void onKeyReleasedSliderPormayor(KeyEvent ke){
        updateTFPormayor();
    }
    
    @FXML
    public void onKeyReleasedSliderDetal(KeyEvent ke){
        updateTFDetal();
    }
    
    
    @FXML
    public void onReleasedTFPormayor(KeyEvent ke){
        UpdateSliderPormayor();
    }
    
    @FXML
    public void onReleasedTFDetal(KeyEvent ke){
        UpdateSliderDetal();
    }
    
    @FXML
    public void onClickRemover(MouseEvent mve){
        ListaProductos.removeAll(ListaProductos);
        TVProdRelaciones.setItems(ListaProductos);
    }
    
    @FXML
    public void onClickAplicarRelacion(MouseEvent mve){
        AplicarRelacion();
    }
    
    public void updateTFPormayor(){
        double valor = (double) SliderPormayor.getValue();
        TFPormayor.setText(Double.toString(valor));
    }
    
    public void updateTFDetal(){
        double valor = (double) SliderDetal.getValue();
        TFDetal.setText(Double.toString(valor));
    }
    
    public void UpdateSliderPormayor(){
        try{
            SliderPormayor.setValue(Double.parseDouble(TFPormayor.getText()));
        } catch ( NumberFormatException nfe){
            SliderPormayor.setValue(0.0);
        }
    }
    
    public void UpdateSliderDetal(){
        try{
            SliderDetal.setValue(Double.parseDouble(TFDetal.getText()));
        } catch ( NumberFormatException nfe){
            SliderDetal.setValue(0.0);
        }
    }
    
    public void ConfTColumns(){
        TColNombre.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
        TColCosto.setCellValueFactory(new PropertyValueFactory<>("Costo"));
        TColTienda.setCellValueFactory(new PropertyValueFactory<>("Pvpublico"));
        TColPublico.setCellValueFactory(new PropertyValueFactory<>("Pvtienda"));
    }
    
    
    /**
     * este metodo configura las opciones disponibles en el combobox de
     * relaciones.
     */
    public void ConfComboRelaciones(){
        CmbRelaciones = FXCollections.observableArrayList("Igualdad");
        CmbRelaciones.add("Composicion");
        CmbRelaciones.add("Offset Porcentual");
        CmbRelaciones.add("Offset Absoluto");
        ComboRelaciones.setItems(CmbRelaciones);
        ComboRelaciones.setValue(CmbRelaciones.get(0));
    }
    
    public void AddProd2Table(SptProducto pro){
        
        if(YaFueAgregado(pro)){
            ShowWarning("Advertencia", "El producto ya esta en la tabla de relaciones", "El producto seleccionado"
                    + "ya fue ingresado previamente a la tabla de relaciones, recuerde que solo puede"
                    + "agregarlo una vez a la tabla por cada relacion que configure.");
        }
        
        else{
            try{
                ListaProductos.add(pro);
                TVProdRelaciones.setItems(ListaProductos);
            } catch(NullPointerException ex){
                //ListaProductos = FXCollections.observableArrayList(new ArrayList<>());
                //ListaProductos.add(pro);
                ListaProductos = FXCollections.observableArrayList(pro);
                TVProdRelaciones.setItems(ListaProductos);
            }
        }
    }
    
    /**
     * Este metodo verifica que el producto trastalado desde la ventana de
     * modificacion a la ventana de relaciones no este presente en la tabla de
     * relaciones ya que no tiene sentido que este 2 o mas veces el 
     * mismo producto en filas diferentes y prodria conducir tambien a bugs
     * @param pro
     * @return 
     */
    public boolean YaFueAgregado(SptProducto pro){
        boolean r = false;
        
        if(ListaProductos == null){
            r=false;
        }
        else{
            for(int j=0; j< ListaProductos.size(); j++){
                if( ListaProductos.get(j).getCodigo().equals(pro.getCodigo())) r = true;
            }
        }
        return r;
    }
    
    public void AplicarRelacion(){
        
        BDH_Relaciones bdhr = new BDH_Relaciones(); //BD handler de relaciones
        validadorRelaciones vrel = new validadorRelaciones(); // se valida si los productos escogidos son valiidos apra crear una relacion
        ObservableList<SptProducto> olist = TVProdRelaciones.getItems(); // se toman los items de la tabla
        ArrayList<SptProducto> plist = new ArrayList<>(TVProdRelaciones.getItems());
        // se realiza validacion de los datos en la tabla
        ArrayList validacion = new ArrayList(); // variable en blanco para guardar la repuesta de validacion
        
        try {
            validacion = vrel.ValidoCrearNuevaRelacion(plist); // se valida la lista.
            if( (boolean) validacion.get(0) ){ // si la lista es valida: ...
                String nombreGrupo = super.ShowInput("Ingrese un nombre", "", 
                        "Ingrese porfavor el nombre del grupo \n a relacionar. recuerde que el nombre\n"
                                + "debe ser unico para cada grupo que cree.", "");
                if(nombreGrupo.equals("")){ // si el campo del nombre esta vacio...
                    super.ShowWarning("Error", "", "El nombre del grupo no puede quedar vacio");
                }
                else{ // si el usuario no dejo el campo de nombre vacio : ...
                    if(bdhr.ExisteNombreGrupo(nombreGrupo)) super.ShowError("Hay un problema", "",
                            "No se puede repetir nombre de grupo para las relaciones"); // si el nombre esta repetido
                    
                    else{ // si el nombre no esta repetido
                        // se crea la relacion:
                        bdhr.InsertarConjuntoRelaciones(plist, nombreGrupo); //se insertan en la tabla de relaciones
                    }
                }
            }
            
            else{
                super.ShowError("Hay un problema", "", (String) validacion.get(1)); // se muestra un dialogo explicando por que la valiacion ha resultado negativa.
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RelacionesController.class.getName()).log(Level.SEVERE, null, ex);
            super.ShowError("Java Exception", "Funcion AplicarRelacion()", "Ha"
                    + " ocurrido una execion de java en el fichero RelacionesController,"
                    + " favor comunicarse con el fabricante de este software.");
        }
    }
}
