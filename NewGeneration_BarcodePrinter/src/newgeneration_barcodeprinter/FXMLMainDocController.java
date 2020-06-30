package newgeneration_barcodeprinter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;


import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;



/**
 *
 * @author esteban
 */
public class FXMLMainDocController {
    
    /**
     * String constant para poner en el label de impresora, indicando
     * que se ha guardado correctamente como una preferencia
     */
    public final String SAVED = "(guardado)";
    
    /**
     * String costant para poner en el label de impresora para indicar
     * que se ha modificado el nombre de la impresora y que se debe guardar
     * con enter.
     */
    public final String MOD = ("(modificado)");
    
    /**
     * clase para encapsular los metodos para guardar y cargar valores de
     * preferencias con la api de java preferences. Ademas encapsula como
     * final Strings los nombres de los keys.
     */
    public ConfigHandler cfg_han =  new ConfigHandler();
    
    /**
     * Objto que encapsula metodos y variables relacionados
     * con la logica de codigo siguiente y calculos de distancias
     * de los labels. pero la api para la generacion e impreison de los
     * codigos como png esta en este Doc Controller.
     */
    public BarCodeLogic bcl = new BarCodeLogic();
    
    
    /**
     * campo de texto con el codigo que se desea generar.
     */
    @FXML
    public TextField TF_Codigo;
    
    /**
     * campo de texto con el nombre del codigo que se desea imprimir
     */
    @FXML
    public TextField TF_Nombre;
    
    /**
     * campo de texto con el precio del producto
     */
    @FXML
    public TextField TF_Precio;
    
    /**
     * capo de texto en el que se ingresa el numero de copias a imprimir.
     */
    @FXML
    public TextField TF_NumCopias;
    
    /**
     * campo de texto para ingresar el nombre de la impresora de adhesivos
     * que se haya configurado en el OS (windows) (ver dispositios e impresoras)
     */
    @FXML
    public TextField TF_Printer;
    
    /**
     * campo de texto para estableceer el tamaño deseado para el tamaño de la letra.
     */
    @FXML
    public TextField TF_Fuente;
    
    
    /**
     * campo de texto para indicar si se ha guardado el nombre de la impresora.
     */
    @FXML
    public Label L_Printer;
    
    /**
     * Label que muestra el ultimo codigo que se mando a
     * impresion.
     */
    @FXML
    public Label L_LastCodeBar;
    
    /**
     * label que muestra cual seria el siguiente codigo de barras que se
     * imprimiria si se deja  el campo de texto de codigo de barras vacio.
     * En caso de estar vacio el software genera uno automaticamente.
     */
    @FXML
    public Label L_NextCodeBar;
    
    
    /**
     * label correspondiente a la notificacion de guardado del
     * tamaño de fuente de la letra.
     */
    @FXML
    public Label L_Fuente;
    
    
    /**
     * check box para indicar si sse guarda siempre el ultimo codigo usado
     * o si se guarda unicamente en caso de que el nuevo codigo sea mayor al 
     * que ya esta guardado.
     */
    @FXML
    public CheckBox CHB_Mantener;
    
    /**
     * metodo que se ejecuta cuado se inicializa el programa.
     * primero se carga el valor guardado con la ap de preferences
     * para el nombre de la impresora.
     *
     * luego se agrega un listener a cambios de valor para el campo de texto de
     * la impresora.
     */
    @FXML
    public void initialize() {
        this.TF_Printer.setText(this.LoadPrinterName());
        
        // se inicializa el valor de la fuente predeterminada en caso de que
        /// sea primera vez que se corre el programa.
        if(cfg_han.LoadConfig_Str(cfg_han.FUENTE_TEXT_SIZE_KEY).equals("")){
            cfg_han.SaveConfig_Str(cfg_han.FUENTE_TEXT_SIZE_KEY, "12");
        }
        TF_Fuente.setText(cfg_han.LoadConfig_Str(cfg_han.FUENTE_TEXT_SIZE_KEY));
        
        //cuando se detecta un cambio en el cmapo de texto se cambia el labol para
        // indicar que se debe presionar enter para guardar el cambio como
        // preferencia de java.
        this.TF_Printer.textProperty().addListener((observable, oldValue, newValues) ->{
            L_Printer.setText(this.MOD);
        });
        
        this.TF_Fuente.textProperty().addListener((observable, oldValue, newValues) ->{
            L_Fuente.setText(this.MOD);
        });
        
        this.UpdateCodeBarLabels();
        
        // se carga el valor del checkbox
        this.LoadMantener();
        
        // listener para guardar en preferences el nuevo valor de el checkbox
        // para la funcion de mantener. (Ver clase config handler.)
        this.CHB_Mantener.selectedProperty().addListener((observable, oldValue, newValues) ->{
            if(CHB_Mantener.isSelected()) this.cfg_han.SaveConfig_Str(cfg_han.MANTENER_MAYOR, cfg_han.SI); 
            else this.cfg_han.SaveConfig_Str(cfg_han.MANTENER_MAYOR, cfg_han.NO);
        });
    }
    
    /**
     * metodo que se ejecuta cuando presionan una tecla y el campo de texto
     * de printer name tiene focus.
     * @param ke
     */
    @FXML
    public void onKeyPress_tf_printer(KeyEvent ke){
        // solo si se presiono enter se guarda la preferencia y se
        // notifica al usuario con el label correspondiente. de lo contrario
        // no se hace nada.
        if(ke.getCode().equals(KeyCode.ENTER)){
            this.SavePrinterName(this.TF_Printer.getText());
            this.L_Printer.setText(SAVED);
        }
    }
    
    /**
     * cuando se presiona enter y el campo para establecer el tamaño
     * de fuente esta seleccionado entonces se guarda el tamalo de fuente
     * usando la API de java preferences siempre y cuando el valor numerico
     * establecido sea consistente.
     * @param ke
     */
    @FXML
    public void onKeyPress_tf_Fuene(KeyEvent ke){
        if(ke.getCode().equals(KeyCode.ENTER)){
            try{
                double fuente = Double.parseDouble(TF_Fuente.getText());
                if(fuente <= 0) throw new NumberFormatException();
                cfg_han.SaveConfig_Str(cfg_han.FUENTE_TEXT_SIZE_KEY, TF_Fuente.getText());
            } catch (NumberFormatException ex){
                TF_Fuente.setText(cfg_han.LoadConfig_Str(cfg_han.FUENTE_TEXT_SIZE_KEY));
            } finally{
                this.L_Fuente.setText(SAVED);
            }
        }
    }
    
    
    /**
     * cuando se hace click al boton imprimir, se debe generar un
     * png con el codigo de barras.
     * @param event
     */
    @FXML
    void onClick_Imprimir(MouseEvent event) {
        try{
            String codigo = this.TF_Codigo.getText(); // Sting con el codigo de barras que se desea generar.
            String nombre = this.TF_Nombre.getText();
            String precio = this.TF_Precio.getText();
            String precioCoded = this.bcl.Price2Text(Integer.parseInt(precio));
            
            // si se deja vacio el campo de codigo, se asigna uno de manera automatica.
            if(codigo.equals("")) codigo = this.L_NextCodeBar.getText();
            
            Integer.parseInt(codigo); // valida el codigo lanzando una excepcion de numberformat.
            // solo se aceptara numeros en el codigo
            
            // se obtiene el nombre del servicio de impresion que se desea usar (ver impresoras y escaneres
            // para obtener el nombre)
            String Printer = this.TF_Printer.getText();
            // numero de filas, minimo seran siempre 3 stickers ya que el rollo es de 3 columnas
            int copias = Integer.parseInt(this.TF_NumCopias.getText());
            
            this.Imprimir(Printer, copias, nombre, codigo, precioCoded); // se imprime el codigo de barras generado.
            //this.DebugPrint(codigo, nombre, precioCoded);
            // si mantener esta seleccionado entonces solo se guarda el
            // codigo usado unicamente si este es superior al ultimo 
            // codigo usado deacuerdo a la logica implementada en 
            // la clase BarcodeLogic.
            if(CHB_Mantener.isSelected()){
                if(this.bcl.Comp_Cods_A_may_B(codigo, L_LastCodeBar.getText())){
                    this.cfg_han.SaveConfig_Str(this.cfg_han.LAST_PRINTED_BARCODE_KEY, codigo);
                    this.UpdateCodeBarLabels();
                }
            } else{ // si no se mantiene el maor entonces se guarda el valor de codigo 
                // ver mantener en la clase de configuracion
                this.cfg_han.SaveConfig_Str(this.cfg_han.LAST_PRINTED_BARCODE_KEY, codigo);
                this.UpdateCodeBarLabels();
            }
            
        }catch(NullPointerException | NumberFormatException ex){
            Logger.getLogger(FXMLMainDocController.class.getName()).log(Level.SEVERE, null, ex);
            this.ShowError("JavaException", "Revise porfavor los valores ingresados. El codigo\n"
                    + "no puede contener espacios vacios, solo numeros de 0-9 y \n"
                    + "letras desde y hasta Aa-Zz\n"
                    + "si necesita asistencia contacte soporte tecnico: \n"
                    + "raulest50@gmail.com\n"
                    + "whatsapp: 313 515 86 11", ex.getMessage());
        }
        // posible ocurrendia durante el guardado del cod barras como png.
        
    }
    
    
    
                        
    /**
     * muestra un dialogo modal de error
     * @param titulo
     * @param subtitulo
     * @param mensaje
     */
    public void ShowError(String titulo, String subtitulo, String mensaje){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        
        alert.setTitle(titulo);
        alert.setHeaderText(subtitulo);
        alert.setContentText(mensaje);
        
        alert.showAndWait();
        //DialogPane Dpane = alert.getDialogPane();
        //Dpane.getStylesheets().add(VirtualAdministrator.class.getClass().getResource("estilo.css").toExternalForm());
    }
    
    /**
     * muestra un dialogo modal de warning
     * @param titulo
     * @param subtitulo
     * @param mensaje
     */
    public void ShowWarning(String titulo, String subtitulo, String mensaje){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        
        alert.setTitle(titulo);
        alert.setHeaderText(subtitulo);
        alert.setContentText(mensaje);
        
        alert.showAndWait();
        //DialogPane Dpane = alert.getDialogPane();
        //Dpane.getStylesheets().add(VirtualAdministrator.class.getClass().getResource("estilo.css").toExternalForm());
    }
    
    /**
     * metodo para acrgar el nombre de la impresora con la API de java
     * preferences
     * @return
     */
    public String LoadPrinterName(){
        String r = "";
        r = this.cfg_han.LoadConfig_Str(cfg_han.PRINTER_SERVICE_NAME_KEY);
        return r;
    }
    
    /**
     * metodo para guardar el nombre de la impresora usado la api
     * de java preferences.
     * @param printerName
     */
    public void SavePrinterName(String printerName){
        this.cfg_han.SaveConfig_Str(cfg_han.PRINTER_SERVICE_NAME_KEY, printerName);
    }
    
                        
    /**
     * metodo que usa la libreria de TSC para enlazar con el .dll
     * que se debe guardar en system32 si es sitema es de 32bits o en syswow64 si es de 64
     * bits. Se hace uso unicamente del SDK de TSC para hacer la operacion de
     * impresion osea que no se hace como servicio de impresion por
     * medio del OS sino que se envian directamente los comandos a la
     * impresora. Ver la carpeta NG OPS para mas informacion.
     * @param printerName
     * @param copias
     * @param txtUp_pre
     * @param code
     * @param txtDown
     */
    public void Imprimir(String printerName, int copias, String txtUp_pre, String code, String txtDown){
        try {
            
            TscLibDll.INSTANCE.openport(printerName);
            TscLibDll.INSTANCE.setup("108", "27", "4", "8", "0", "0", "0");
            
            ArrayList txtUp = new ArrayList<>();
            txtUp = this.GetTxtUp(txtUp_pre);
            
            // se obtiene la fecha actual para agregar en los stickers.
            String fechaA = this.GetCurrentTime();
            
            boolean up2_go = (boolean) txtUp.get(0);
            
            // X, Y, fontType
            TscLibDll.INSTANCE.printerfont ("32", "32", "2", "0", "1", "1", (String) txtUp.get(1));
            if(up2_go) TscLibDll.INSTANCE.printerfont ("32", "56", "2", "0", "1", "1", (String) txtUp.get(2));
            TscLibDll.INSTANCE.barcode("32", "80", "128", "56", "1", "0", "2", "2", code);
            TscLibDll.INSTANCE.printerfont ("32", "160", "2", "0", "1", "1", txtDown);
            
            TscLibDll.INSTANCE.printerfont ("160", "160", "2", "0", "1", "1", fechaA);
            
            
            
            
            TscLibDll.INSTANCE.printerfont ("320", "32", "2", "0", "1", "1", (String) txtUp.get(1));
            if(up2_go) TscLibDll.INSTANCE.printerfont ("320", "56", "2", "0", "1", "1", (String) txtUp.get(2));
            TscLibDll.INSTANCE.barcode("320", "80", "128", "56", "1", "0", "2", "2", code);
            TscLibDll.INSTANCE.printerfont ("320", "160", "2", "0", "1", "1", txtDown);
            
            TscLibDll.INSTANCE.printerfont ("160", "160", "2", "0", "1", "1", fechaA);
            
            
            
            
            TscLibDll.INSTANCE.printerfont ("600", "32", "2", "0", "1", "1", (String) txtUp.get(1));
            if(up2_go) TscLibDll.INSTANCE.printerfont ("600", "56", "2", "0", "1", "1", (String) txtUp.get(2));
            TscLibDll.INSTANCE.barcode("600", "80", "128", "56", "1", "0", "2", "2", code);
            TscLibDll.INSTANCE.printerfont ("600", "160", "2", "0", "1", "1", txtDown);
            
            TscLibDll.INSTANCE.printerfont ("160", "160", "2", "0", "1", "1", fechaA);
            
            
            
            
            
            TscLibDll.INSTANCE.printlabel("1", Integer.toString(copias));
            TscLibDll.INSTANCE.clearbuffer();
            TscLibDll.INSTANCE.closeport();
        } catch (RuntimeException ex) {
            Logger.getLogger(FXMLMainDocController.class.getName()).log(Level.SEVERE, null, ex);
            this.ShowError("Java Exception", "No hay una impresora con ese nombre",
                    "por favor abra dispositivos e impresoras y e identifique \n"
                            + "el nombre de la impresora de adhesivos.");
        }
    }
    
    /**
     * implementa la logica de partir en dos lineas el texto que va en la
     * parte de arriba del codigo de barras para usar 2 o una lineas segun la
     * longitud del texto.
     * @param txtUp
     * @return
     */
    public ArrayList GetTxtUp(String txtUp){
        ArrayList r = new ArrayList();
        if(txtUp.length() <= 17){
            r.add(false);
            r.add(txtUp);
        } else{ // tiene mas de 17 caracteres
            r.add(true);
            if(txtUp.length()>=34){ // trunca hasta 33 si tiene 34 o mas caracteres
                r.add(txtUp.substring(0, 16) + "-");
                r.add(txtUp.substring(17, 33));
            } else{
                r.add(txtUp.substring(0, 16) + "-");
                r.add(txtUp.substring(16, txtUp.length()));
            }
        }
        return r;
    }
    
    
    /**
     * Hay dos labels que sirven para mostrar el ultimo codigo de barras usado
     * y el siguiente que se generaria si se deja el campo de codigo de barras
     * vacio. Este metodo permite hacer set de estos valores en los labels si
     * es primera vez que se invoca o hacer update de los labels si se ejecuta
     * despues de hacer una impresion.
     */
    public void UpdateCodeBarLabels(){
        this.L_LastCodeBar.setText(this.cfg_han.LoadConfig_Str(this.cfg_han.LAST_PRINTED_BARCODE_KEY));
        this.L_NextCodeBar.setText(this.bcl.getNextProductoCodeBlinded(this.cfg_han.LoadConfig_Str(this.cfg_han.LAST_PRINTED_BARCODE_KEY)));
    }
    
    public void LoadMantener(){
        String mantener = this.cfg_han.LoadConfig_Str(cfg_han.MANTENER_MAYOR);
        
        // si es la primera vez que se ejecuta el soft, la preferencia
        // estara vacia, en cuyo caso se escribe true
        if(mantener.equals("")){
            this.cfg_han.SaveConfig_Str(cfg_han.MANTENER_MAYOR, cfg_han.SI);
            mantener = cfg_han.SI;
        }
        if(mantener.equals(this.cfg_han.SI)) CHB_Mantener.setSelected(true);
        else CHB_Mantener.setSelected(false);
    }
    
    
    /**
     * metodo que retorna la fecha actual como un string
     * @return 
     */
    public String GetCurrentTime(){
        //Get current date time
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Before : " + now);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(formatter);
    }
    
}