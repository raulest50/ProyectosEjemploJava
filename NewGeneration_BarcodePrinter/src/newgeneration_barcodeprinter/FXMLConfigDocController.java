package newgeneration_barcodeprinter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author esteban
 * 
 * Document Controller para la ventana de configuracion.
 * En esta ventana se condifuran las distancias entre los labels para
 * poder hacer ajuste a los diferentes rollos de adhesivos
 * temricos. Sin embargo la logica implementada se limita a aquellos 
 * rollos que sean de 3 solumnas. plantillas con un numero de columnas 
 * diferente de 3 no se puede ajustar con esta aplicacion.
 * 
 */
public class FXMLConfigDocController {

    /**
     * image view para mostrar la imagen que explica las dimensiones
     * a configurar para la impresion del labels
     */
    @FXML
    private ImageView IMV_Media;

    /**
     * campo de texto para el gap entre la frontera horizontal del rollo 
     * y el perimetro del primer label.
     */
    @FXML
    private TextField TF_GapFrontier;

    /**
     * label para el campo de texto de gapFrontier que indica cuanndo se
     * ha guardado la configuracion.
     */
    @FXML
    private Label L_GapFrontier;

    /**
     * campo para ingresar la distancia horizontal entre labels
     */
    @FXML
    private TextField TF_GapHorizontal;

    /**
     * todos los labels de javaFX en este FXML doc solo sirven para indicar
     * cuando se ha guardado la configuracion de su respectivo TF, por lo que 
     * de ahora en adelante no se comenta otro label de FX
     */
    @FXML
    private Label L_GapHorizontal;

    /**
     * campo de texto para la distancia vertical entre labels
     */
    @FXML
    private TextField TF_GapVertical;

    @FXML
    private Label L_GapVertical;

    /**
     * campo de texto para el ancho del adhesivo
     */
    @FXML
    private TextField TF_AnchoLabel;

    @FXML
    private Label L_AnchoLabel;

    /**
     * Campo de texto para la altura del label
     */
    @FXML
    private TextField TF_AlturaLabel;

    @FXML
    private Label L_AlturaLabel;
    
    /**
     * String constante que se asigna al respectivo label cuando se 
     * ha presionado enter para guardar la configuracion.
     */
    public final String SAVED = "(ok)";
    
    /**
     * constant String que se pone en en respectivo label para indicar
     * que se ha hecho una modificacion de la configuracion en cuestion
     * y que se debe presionar enter para guardarla.
     */
    public final String MOD = "(!)";
    
    /**
     * Config handler para hacer uso de la api de java preferences.
     */
    public ConfigHandler cfn_han = new ConfigHandler();

    /**
     * cuando se hace click al boton cerrar se cierra la ventana de configuracion
     * sin importar que se hayan o no guardado los cambios realizados
     * @param event 
     */
    @FXML
    void onClickCerrar(MouseEvent event) {
        Stage stg = (Stage) this.TF_AlturaLabel.getScene().getWindow();
        stg.close();
    }
    
    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        // con solo invocar el constructor se ponen los listeners y se inicializan
        // los campos de texto con los debidos valores por lo que no es necesario 
        // guardar la instancia del teamConfig.
        new ConfigTeam(TF_AlturaLabel, L_AlturaLabel, cfn_han.LABEL_HEIGHT_KEY);
        new ConfigTeam(TF_AnchoLabel, L_AnchoLabel, cfn_han.LABEL_WIDTH_KEY);
        new ConfigTeam(TF_GapFrontier, L_GapFrontier, cfn_han.MARGIN_HORIZ_GAP_KEY);
        new ConfigTeam(TF_GapHorizontal, L_GapHorizontal, cfn_han.HORIZ_GAP_KEY);
        new ConfigTeam(TF_GapVertical, L_GapVertical, cfn_han.VERTICAL_GAP_KEY);
    }    
    
    
    /**
     * clase que encapsula el conjunto de label textfield y su key string
     * de la api de java preferences.
     * 
     * en el constructor de esta clase se hace inicializacion del text field,
     * se agrega su onChange Listener y su onKeyPress Listener.
     * de esta forma el codigo no se tiene que escribir para cada TextField
     * sino que en el initialize de este DocController solo se invoca el
     * constructor y listo,, simplificando y haciendo mas limpio el codigo.
     */
    private class ConfigTeam{
        TextField campo;
        Label lab;
        String keyConfig;

        public ConfigTeam(TextField campo, Label lab, String keyConfig) {
            this.campo = campo;
            this.lab = lab;
            this.keyConfig = keyConfig;
            
            campo.setText(cfn_han.LoadConfig_Str(keyConfig));
            
            // se ponen ValueChange Listener
            this.campo.textProperty().addListener((observable, oldValue, newValues) ->{
                this.lab.setText(MOD); // se indica con el label que debe guardarse el cambio
            });
            
            // se pone el listener para captar el enter para guardar la configuracion.
            this.campo.setOnKeyPressed((event) -> {
                if(event.getCode().equals(KeyCode.ENTER)){
                    try{
                        Double.parseDouble(campo.getText()); // para validar con NumFormat Exception
                        cfn_han.SaveConfig_Str(keyConfig, campo.getText());
                    } catch (NumberFormatException ex){
                        campo.setText(cfn_han.LoadConfig_Str(keyConfig));
                    } finally{ // con o sin excepcion van a quedar actualizados.
                        lab.setText(SAVED);
                    }
                }
            });
        }
    }
}
