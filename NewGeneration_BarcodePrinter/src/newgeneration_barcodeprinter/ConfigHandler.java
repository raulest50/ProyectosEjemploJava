
package newgeneration_barcodeprinter;

import java.util.prefs.Preferences;

/**
 *
 * @author esteban
 * 
 * Clase para gestionar las preferencias de java de la aplicacion.
 */
public class ConfigHandler {
    
    /**
     * key para el nombre de la impresora. corresponde al
     * nombre que se muestra cuando se abre impresoras y escaneres.
     */
    public final String PRINTER_SERVICE_NAME_KEY = "preferences_java_printer_key";
    
    /**
     * key para la distancia entre el inicio del papel y
     * el primer label. ver la imagen de configuracion, corresponde a 'A'
     */
    public final String MARGIN_HORIZ_GAP_KEY = "preferencias_java_ng_margin_horiz_gap";
    
    /**
     * key para la distancia horizontal entre labels.
     * En la imagen de la ventana de configuracion corresponde a la medida
     * 'B'
     */
    public final String HORIZ_GAP_KEY = "preferencias_java_ng_gap_horizontal";
    
    /**
     * key para la distancia vertical entre labels. dimension C en la imagen
     * de configuracion.
     */
    public final String VERTICAL_GAP_KEY = "preferencias_java_ng_vertical_gap";
    
    /**
     * key para el alto del label
     */
    public final String LABEL_HEIGHT_KEY = "preferencias_java_ng_altura_label";
    
    /**
     * key para el ancho del label
     */
    public final String LABEL_WIDTH_KEY = "preferencias_java_ng_ancho_label";
    
    /**
     * key para el ultimo codigo de barras impreso.
     */
    public final String LAST_PRINTED_BARCODE_KEY = "preferencia_java_ng_ultimo_cod_barras_impreso";
    
    /**
     * key para el tamaño de guente que usara Graphics2D para dibujar letras en el codigo de barras.
     */
    public final String FUENTE_TEXT_SIZE_KEY = "prefrencias_java_ng_tamano_fuente_letra";
    
    /**
     * preferencia de java que indica si se guarda el ultimo codigo solo si es mayor
     * al anterior o si se guarda sin importar si es o no mayor.
     */
    public final String MANTENER_MAYOR = "preferencias_java_ng_mantener_mayor_codigo";
    
    /**
     * para guardar en las preferencias de java configuracion que solo
     * admiten 2 opciones. 
     */
    public final String SI = "preferencias_java_ng_true";
    
    /**
     * para guardar en las preferencias de java configuracion que solo
     * admiten 2 opciones.
     */
    public final String NO = "preferencias_java_ng_false";
    
    /**
     * objeto de la api de preferencias que se usa para guardar y cargar configuraciones.
     */
    public Preferences pref = Preferences.userRoot().node(this.getClass().getName());
    
    
    public ConfigHandler(){}
    
    /**
     * para guardar una configuracion tipo String
     * @param Key
     * @param valor 
     */
    public void SaveConfig_Str(String Key, String valor){
        pref.put(Key, valor);
    }
    
    /**
     * Para guardar una configuracion tipo entero
     * @param Key
     * @param valor 
     */
    public void SaveConfig_Double(String Key, double valor){
        String str_val = Double.toString(valor);
        pref.put(Key, str_val);
    }
    
    /**
     * para cargar una configuracion tipo String
     * @param Key
     * @return 
     */
    public String LoadConfig_Str(String Key){
        String r = "";
        r = pref.get(Key, "");
        return r;
    }
    
    /**
     * para cargar una configuracion tipo entero.
     * @param Key
     * @return 
     */
    public double LoadConfig_Double(String Key){
        double r = 0;
        String aux = pref.get(Key, "0");
        r = Double.parseDouble(aux);
        return r;
    }
}
