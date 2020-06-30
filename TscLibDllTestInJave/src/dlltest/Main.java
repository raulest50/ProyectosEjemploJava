package dlltest;
import com.sun.jna.Library;
import com.sun.jna.Native;
public class Main {
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

    public static void main(String[] args) {
        //TscLibDll.INSTANCE.about();
        TscLibDll.INSTANCE.openport("STICKER");
        //TscLibDll.INSTANCE.downloadpcx("C:\\UL.PCX", "UL.PCX");
        //TscLibDll.INSTANCE.sendcommand("REM ***** This is a test by JAVA. *****");
        TscLibDll.INSTANCE.setup("108", "27", "4", "8", "0", "0", "0");
        //TscLibDll.INSTANCE.clearbuffer();
        //TscLibDll.INSTANCE.sendcommand("PUTPCX 550,10,\"UL.PCX\"");
        //TscLibDll.INSTANCE.printerfont ("100", "10", "3", "0", "1", "1", "(JAVA) DLL Test!!");
        
        // X, Y, fontType, 
        TscLibDll.INSTANCE.printerfont ("32", "32", "2", "0", "1", "1", "123456789_1234567");
        TscLibDll.INSTANCE.printerfont ("32", "56", "2", "0", "1", "1", "ARRIBA2");
        TscLibDll.INSTANCE.barcode("32", "80", "128", "56", "1", "0", "2", "2", "12345678");
        TscLibDll.INSTANCE.printerfont ("32", "160", "2", "0", "1", "1", "ABAJO");
        
        TscLibDll.INSTANCE.printerfont ("320", "32", "2", "0", "1", "1", "ARRIBA");
        TscLibDll.INSTANCE.barcode("320", "80", "128", "56", "1", "0", "2", "2", "1000123891");
        TscLibDll.INSTANCE.printerfont ("320", "160", "2", "0", "1", "1", "ABAJO");
        
        TscLibDll.INSTANCE.printerfont ("320", "32", "2", "0", "1", "1", "ARRIBA");
        TscLibDll.INSTANCE.barcode("600", "80", "128", "56", "1", "0", "2", "2", "123456789012");
        TscLibDll.INSTANCE.printerfont ("320", "160", "2", "0", "1", "1", "ABAJO");
        
        //TscLibDll.INSTANCE.barcode("20", "2", "EAN128", "80", "1", "0", "2", "2", "1234567");
//        TscLibDll.INSTANCE.windowsfont(400, 200, 48, 0, 3, 1, "arial", "DEG 0");
//        TscLibDll.INSTANCE.windowsfont(400, 200, 48, 90, 3, 1, "arial", "DEG 90");
//        TscLibDll.INSTANCE.windowsfont(400, 200, 48, 180, 3, 1, "arial", "DEG 180");
//        TscLibDll.INSTANCE.windowsfont(400, 200, 48, 270, 3, 1, "arial", "DEG 270");
        TscLibDll.INSTANCE.printlabel("1", "1");
        TscLibDll.INSTANCE.clearbuffer();
        TscLibDll.INSTANCE.closeport();
    }
}
