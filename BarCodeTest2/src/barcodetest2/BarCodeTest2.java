
package barcodetest2;

import java.io.File;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

/**
 *
 * @author esteban
 */
public class BarCodeTest2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws OutputException, BarcodeException {
        Barcode barcode = BarcodeFactory.createCode128B("384");
        barcode.setBarHeight(70);
        barcode.setBarWidth(3);
        
        File imgFile = new File("C:\\Users\\erich\\Desktop\\canoa.png");
        
        //Write the bar code to PNG file
        BarcodeImageHandler.savePNG(barcode, imgFile);
    }
    
}
