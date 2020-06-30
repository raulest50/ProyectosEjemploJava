
package newgeneration_barcodeprinter;

import com.sun.jna.Library;
import com.sun.jna.Native;


/**
 * interfaz que se crea en el ejemplo de TSC. para hacer uso de la
 * libreria de TSC solo basta con crear esta interfaz tal como se ahce aca e
 * importat la libreria jna. con eso es suficiente. luego solo basta con 
 * usar la interfaz tal como se hace en el metodo imprimir(...) en el MainDoc
 * Controller.
 * 
 * para que funcionara en el windows server de new generation es necesario
 * que se copie el archivo .dll de TSC, que publica en la seccion de SDK
 * en la carpeta C:/syswow64. a la hora de abrir el spool hay que usar el 
 * nombre que tiene adignado la impresora en el os, en la seccion de impresoras
 * y escaneres.
 */
public interface TscLibDll extends Library {
    TscLibDll INSTANCE = (TscLibDll) Native.loadLibrary ("TSCLIB", TscLibDll.class);
    int about ();
    int openport (String pirnterName);
    int closeport ();
    int sendcommand (String printerCommand);
    int setup (String width,String height,String speed,String density,String sensor,String vertical,String offset);
    int downloadpcx (String filename,String image_name);
    int barcode (String x,String y,String type,String height,String readable,String rotation,String narrow,String wide,String code);
    int printerfont (String x,String y,String fonttype,String rotation,String xmul,String ymul,String text);
    int clearbuffer ();
    int printlabel (String set, String copy);
    int formfeed ();
    int nobackfeed ();
    int windowsfont (int x, int y, int fontheight, int rotation, int fontstyle, int fontunderline, String szFaceName, String content);
}