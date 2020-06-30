package virtualadministrator.Controllers;

import BD.Handlers.BDH_Familias;
import Configuracion.ConfiKeys;
import Configuracion.config;
import Definiciones.SptFamilia;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import virtualadministrator.FXMLControllerSKL;


/**
 * FXML Controller class
 * @author esteban
 */
public class ConfigController extends FXMLControllerSKL{
    
    @FXML 
    public TableView<SptFamilia> TVFamilias;
    
    @FXML 
    public TableColumn TCFamilias;
    
    @FXML 
    public Button BAgregarFamilia, BEliminarFamilia;
    
    @FXML // campo de texto para establecer el valor de iva por defecto en la pestaña de codificacion
    public TextField TextFieldIvaxDefecto, TextFieldDirCod;
    
    @FXML // imageview para indicar notificacion de guardado de iva por defecto
    public ImageView ImViewIvaxDefecto, ImViewDirCod;
    
    @FXML // Check Box para habilitar o deshabilitar: agreagar descripcion al png generacion codigo de barras en pestaña modificar producto
    public CheckBox CheckBox128pDescri;
    
    
    public BDH_Familias bdfam = new BDH_Familias();
    
    public CodificarController cpt;
    
    public config MyConf;
    
    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        this.ConfTCols();
        this.CargarFamilias();
        this.MyConf = new config();
        
        if(MyConf.Get_string(ConfiKeys.keyIva).equals(ConfiKeys.nulo)) TextFieldIvaxDefecto.setText("");
        else TextFieldIvaxDefecto.setText(MyConf.Get_string(ConfiKeys.keyIva));
        
        if(MyConf.Get_string(ConfiKeys.keyDirCod).equals(ConfiKeys.nulo)) TextFieldDirCod.setText("");
        else TextFieldDirCod.setText(MyConf.Get_string(ConfiKeys.keyDirCod));
        
    }
    
    @FXML
    public void ClickAgregarFamilia(MouseEvent event){
        String nuevafam = this.ShowInput("Nueva Familia", "No puede dejar el campo vacio\n"
                + "No puede haber familias repetidas\n"
                + "No exeder 150 caraccteres", "Ingrese en el nombre de la nueva familia", "");
        if (bdfam.Insertar(nuevafam)){
            ShowInfo("Operacion Exitosa", "Familia agregada", "La familia "+nuevafam+" ha sido"
                    + "agregada satisfactoriamente");
        }
        else{
            ShowWarning("Hay Un Error","", "No puede repetir familias ni ingresar un espacio vacio como familia");
        }
        this.CargarFamilias();
    }
    
    @FXML
    public void ClickEliminarFamilia(MouseEvent event){
        SptFamilia fam = TVFamilias.getSelectionModel().getSelectedItem();
        try{
            if(bdfam.Eliminar(fam.getId())) ShowInfo("Eliminacion Exitosa", "", "El producto ha sido eliminado exitosamente :)");
        }
        catch(NullPointerException e){
            ShowWarning("No hay Elemento Seleccionado", "", "Debe seleccionar un elemento de la lista \n"
                    + "para poder eliminarlo");
        }
        CargarFamilias();
    }
    
    
    /**
     * este metodo permite configurar el nombre de la impresora
     * que se usara para imprimir recivos de caja.
     * esto se usa por ahora solo en windows.
     * el codigo desarrollado hasta ahora permite llevar a cabo la impresion
     * solamente con el nombre de la impresaroa asignado en el panel de 
     * impresoras y faxes.
     * @param mve 
     */
    @FXML
    public void onClickConfImpresora(MouseEvent mve){
        
    }
    
    /**
     * permimte cargar las familias configuradas.
     */
    public void CargarFamilias(){
        ObservableList<SptFamilia> Olistfam = FXCollections.observableArrayList(bdfam.TraerTodas());// se construye lista observable
        TVFamilias.setItems(Olistfam);// se establecen los elementos  de la tabla
    }
    
    /**
     * configura las table columns del table view de esta pantalla de configuracion.
     */
    public void ConfTCols(){
        TCFamilias.setCellValueFactory(new PropertyValueFactory("id")); // nombre del atributo del spt. etiquetado de columnas
    }
    
    
    /**
     * Metodo que se ejecuta cuando se preciona una tecla y el text field
     * de iva por defecto esta seleccionado.
     * cuando hay un cambio se notifica con un image view. e igual forma
     * con enter se actualiza en preferencias el nuevo valor de iva
     * y se notifica cuando se ha guardado.
    */
    @FXML
    public void OnKeyPressTFIvaDefecto(KeyEvent ke){
        
        if(ke.getCode().equals(KeyCode.ENTER)){
            File file = new File("src/virtualadministrator/check2.png");
            Image image = new Image(file.toURI().toString());
            ImViewIvaxDefecto.setImage(image);
            //se guarda el nuevo valor de iva como una preferencia de la aplicacion.
            MyConf.Put_string(ConfiKeys.keyIva, TextFieldIvaxDefecto.getText());
        }
        else{
            File file = new File("src/virtualadministrator/next.png");
            Image image = new Image(file.toURI().toString());
            ImViewIvaxDefecto.setImage(image);
        }
    }
    
    
    @FXML
    public void OnKeyPressDirCod(KeyEvent ke){
        
        if(ke.getCode().equals(KeyCode.ENTER)){
            File file = new File("src/virtualadministrator/check2.png");
            Image image = new Image(file.toURI().toString());
            ImViewDirCod.setImage(image);
            //se guarda el nuevo valor de iva como una preferencia de la aplicacion.
            MyConf.Put_string(ConfiKeys.keyDirCod, TextFieldDirCod.getText());
        }
        else{
            File file = new File("src/virtualadministrator/next.png");
            Image image = new Image(file.toURI().toString());
            ImViewDirCod.setImage(image);
        }
    }
    
}
