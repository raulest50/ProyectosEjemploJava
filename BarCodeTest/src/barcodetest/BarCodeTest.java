/**
 * codigo para ensayar la libreria gratis  barbecue que genera 
 * codiogos de barras en varios formatos. entrega una imagen
 */
package barcodetest;

/**
 *
 * @author esteban
 * 
 * mis agradecimientos para el creador o creadores de abrbecue
 */
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.linear.code128.Code128Barcode;
import net.sourceforge.barbecue.output.OutputException;




public class BarCodeTest {

    public static void main(String[] args) {
        
        
        
        try {
            //Code128Barcode b = new Code128Barcode("1234567");
            BufferedImage img = BarcodeImageHandler.getImage(BarcodeFactory.createCode128("1234567"));
            File outputfile = new File("bcode.png");
            ImageIO.write(img, "png", outputfile);
        } catch (BarcodeException | IOException | OutputException ex) {
            Logger.getLogger(BarCodeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
