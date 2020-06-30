/*
 * 
 */
package bcodetest2;

/**
 *
 * @author esteban
 */



import java.awt.image.BufferedImage;
import java.io.File;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;


public class BcodeTest2 {
    
    /**
     * @param args the command line arguments
     * 
     * En este snnipet muestra como modificar la etiqueta
     * del codigo de barras. con barbecue se genera un PNG con el codigo de barras
     * especificado. con set labet se modifica el texto ubicado abajo del codigo
     * en el png.
     */
    public static void main(String[] args) {
        
        String dir = "C:\\Users\\erich\\Desktop";
        String code = "22037";
        
        String lab = "MANTEQUILLA ALPINA x 125g";
        
        
        
        Barcode barcode;
        
        try {
            barcode = BarcodeFactory.createCode128B(code);
            
            barcode.setBarHeight(77);
            barcode.setBarWidth(4);
            barcode.setLabel("");
            //barcode.setName("mantequilla");
            barcode.setDrawingText(true);
            
            File imgFile = new File(dir+"\\"+code+".png");
            
            //Write the bar code to PNG file
            BarcodeImageHandler.savePNG(barcode, imgFile);
            BufferedImage bfim = BarcodeImageHandler.getImage(barcode);
            
            
            
        } catch (BarcodeException | OutputException ex) {   
            System.out.println("Excepcion barcode | output");
        }
        
    }
    
}
