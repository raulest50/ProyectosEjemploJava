/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlltest;

import com.sun.jna.Library;
import com.sun.jna.Native;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author erich
 */
public class coment {
    
}


//package dlltest;
//import com.sun.jna.Library;
//import com.sun.jna.Native;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//
//public class Main {
//    
//    
//    public interface TscLibDll extends Library {
//        TscLibDll INSTANCE = (TscLibDll) Native.loadLibrary ("TSCLIB", TscLibDll.class);
//        int about ();
//        int openport (String pirnterName);
//        int closeport ();
//        int sendcommand (String printerCommand);
//        int setup (String width,String height,String speed,String density,String sensor,String vertical,String offset);
//        int downloadpcx (String filename,String image_name);
//        int barcode (String x,String y,String type,String height,String readable,String rotation,String narrow,String wide,String code);
//        int printerfont (String x,String y,String fonttype,String rotation,String xmul,String ymul,String text);
//        int clearbuffer ();
//        int printlabel (String set, String copy);
//        int formfeed ();
//        int nobackfeed ();
//        int windowsfont (int x, int y, int fontheight, int rotation, int fontstyle, int fontunderline, String szFaceName, String content);
//    }
//    
//    
//    public static final String EXIT = "salir()";
//    
//    public static void main(String[] args) {
//        
//        try {
//            String input="";
//            
//            TscLibDll.INSTANCE.openport("STICKER");
//            TscLibDll.INSTANCE.clearbuffer();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//            int c=1;
//            
//            input = reader.readLine();
//            
//            if(input.equals("1")){
//                TscLibDll.INSTANCE.setup("100", "25", "5", "8", "0", "0", "0");
//                System.out.println("CLI interface to test TSPL commands. <<< NEW GENERATION >>>");
//                while(c==1){
//                    
//                    System.out.println("ingrese un comando de TSPL porfavor:  (exit with: " + EXIT + ")");
//                    input = reader.readLine();
//                    if(input.equals(EXIT)){
//                        c=0;
//                    } else{
//                        TscLibDll.INSTANCE.sendcommand(input);
//                    }
//                }
//            } else {
//                
//                TscLibDll.INSTANCE.clearbuffer();
//                TscLibDll.INSTANCE.closeport();
//                
//                TscLibDll.INSTANCE.about();
//                TscLibDll.INSTANCE.openport("STICKER");
//                //TscLibDll.INSTANCE.openport("TSC TTP-2410M");
//                TscLibDll.INSTANCE.downloadpcx("C:\\UL.PCX", "UL.PCX");
//                TscLibDll.INSTANCE.sendcommand("REM ***** NEW GENERATION  *****");
//                TscLibDll.INSTANCE.setup("100", "25", "5", "8", "0", "0", "0");
//                TscLibDll.INSTANCE.clearbuffer();
//                //TscLibDll.INSTANCE.sendcommand("PUTPCX 550,10,\"UL.PCX\"");
//                //TscLibDll.INSTANCE.printerfont ("100", "10", "3", "0", "1", "1", "(JAVA) DLL Test!!");
//                TscLibDll.INSTANCE.barcode("5", "5", "EAN128", "30", "1", "0", "2", "2", "123456789");
//                TscLibDll.INSTANCE.barcode("43", "5", "EAN128", "30", "1", "0", "2", "2", "123456789");
//                TscLibDll.INSTANCE.barcode("86", "5", "EAN128", "30", "1", "0", "2", "2", "123456789");
//                //TscLibDll.INSTANCE.windowsfont(400, 200, 48, 0, 3, 1, "arial", "DEG 0");
//                //TscLibDll.INSTANCE.windowsfont(400, 200, 48, 90, 3, 1, "arial", "DEG 90");
//                //TscLibDll.INSTANCE.windowsfont(400, 200, 48, 180, 3, 1, "arial", "DEG 180");
//                //TscLibDll.INSTANCE.windowsfont(400, 200, 48, 270, 3, 1, "arial", "DEG 270");
//                TscLibDll.INSTANCE.printlabel("1", "1");
//                TscLibDll.INSTANCE.closeport();
//            }
//        } catch (IOException ex) {
//            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("Error: " + ex.getMessage());
//        }
//    }
//}
