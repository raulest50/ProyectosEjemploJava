package createfileexplor;

/**
 *
 * @author esteban
 * 
 * en este ejecutable se hace una exploracion de como crear de una manera aceptable un
 * archivo a partir de un String o demas variables de programa de java.
 */


// para generar numeros aleatorios
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



public class CreateFileExplor {
    
    private static SecureRandom random = new SecureRandom();
    public static String ident = "5CD5093QJ2Intel(R)Core(TM)i7-5500UCPU@2.40GHz24MicrosoftWindows10HomeSingleLanguage|C:\\WINDOWS|\\Device\\Harddisk0\\Partition4F.3325102023PEWMD018J8E11DC:\\ProgramFiles\\Java\\jdk1.8.0_45\\jreerich1.8.0_45C:\\Users\\erich";
    
    public static void main(String[] args) {
        String random_str = new BigInteger(500, random).toString(32);
        
        String dir = System.getProperty("user.home")+File.separator+"co.lic";
        
        System.out.println(System.getProperty("user.home"));
        File file = new File(dir);
        
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter wri = new BufferedWriter(fw);
            wri.write(decode(ident));
            wri.close();
            file.createNewFile();
            
        } catch (IOException ex) {
            Logger.getLogger(CreateFileExplor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private String GetExecutionPath(){
        String absolutePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
        absolutePath = absolutePath.replaceAll("%20"," "); // Surely need to do this here
        return absolutePath;
    }
    
    
    /**
     * metodo que decodifica 
     * @param d
     * @return 
     */
    public static String decode(String d){
        char[] a = d.toCharArray();
        String r = "";
        for(int k =0; k< a.length; k++){
            r+=Integer.toString(((int)a[k])*((int)a[k]));
        }
        return r;
    }
    
    /**
     * write a file it is used for licenciation.
     * @param lic 
     */
    public void WriteLic(String lic){
        String dir = System.getProperty("user.home")+File.separator+"co.lic";
        File file = new File(dir);
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter wri = new BufferedWriter(fw);
            wri.write(lic);
            wri.close();
            fw.close();
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(CreateFileExplor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
