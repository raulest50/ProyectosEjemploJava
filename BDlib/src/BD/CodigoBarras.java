/**
 * clase que se relaciona con la creacion de codigos de barras y su manipulacion
 */
package BD;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

/**
 *
 * @author esteban
 * 
 * dir -> directorio
 * code -> codigo de barras
 */
public class CodigoBarras {
    
    public boolean Png128(String dir, String code) {
        boolean r = true;
        Barcode barcode;
        try {
            barcode = BarcodeFactory.createCode128B(code);
            barcode.setBarHeight(77);
            barcode.setBarWidth(4);
            
            File imgFile = new File(dir+"\\"+code+".png");
            
            //Write the bar code to PNG file
            BarcodeImageHandler.savePNG(barcode, imgFile);
            
        } catch (BarcodeException | OutputException ex) {
            Logger.getLogger(CodigoBarras.class.getName()).log(Level.SEVERE, null, ex);
            r=false;
        }
        return r;
    }
    
}
