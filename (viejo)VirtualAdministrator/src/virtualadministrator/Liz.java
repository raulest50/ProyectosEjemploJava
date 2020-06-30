 /**
  * Esta clase se encarga del licensiamiento
  * de la aplicacion.
  */
package virtualadministrator;

import BD.CopiaSeguridad;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author esteban
 */
public class Liz {
    
    /**
     * comandos del cmd que permite obtener informacion del sistemma mediante
     * wmic
     */
    public static String serial_number = "wmic bios get serialnumber",
            cpu_name = "wmic cpu get Name", cores="wmic cpu get NumberOfCores, NumberOfLogicalProcessors",
            os_full_name = "wmic os get Name", bios_name = "wmic bios get name",
            memorychip = "wmic memorychip get serialnumber", base_board = "wmic baseboard get serialnumber";
    
    
    
    
    
    /**
     * metodo que entrega un String que identifica de manera unica este equipo
     * @return
     */
    public static String Get_this_sys_win(){
        String r = "";
        r+= CmdWin(serial_number);
        r+= CmdWin(cpu_name);
        r+= CmdWin(cores);
        r+= CmdWin(os_full_name);
        r+= CmdWin(bios_name);
        r+= CmdWin(memorychip);
        r+= CmdWin(base_board);
        r+= System.getProperty("java.home");
        r+= System.getProperty("user.name");
        r+= System.getProperty("java.version");
        r+= System.getProperty("user.home");
        r= r.replace(" ", "");
        r= r.replace("SerialNumber", "");
        r= r.replace("Name", "");
        r= r.replace("NumberOfCores", "");
        r= r.replace("NumberOfLogicalProcessors", "");
        return r;
    }
    
    /**
     * tested using ubuntu 16
     * permite obtener un String que identifica manera unica
     * tanto usuario, sistema operativo y hardware, string que sera usado
     * posteriormente para el licenciamiento. (es como la huella digital pero del
     * computador)
     * @return 
     */
    public String Get_this_sys_ubuntu(){
        String r = "";
        
        return r;
    }
    
    /**
     * permite ejecutar comandos en la shell de linux y retorna la 
     * respuesta en formato String
     * @param c
     * @return 
     */
    static public String CmShell(String c){
        String r="";
        
        return r;
    }
    
    /**
     * Metodo para enviar comandos al cmd de winndows
     * recive y entrega un String
     * @param c
     * @return
     */
    static public String CmdWin(String c){
        String r="";
        String line="";
        final String[] strcm = new String[] {"cmd.exe", "/c", c};
        
        try {
            Process p=Runtime.getRuntime().exec(strcm);
            p.waitFor();
            BufferedReader reader=new BufferedReader(
                    new InputStreamReader(p.getInputStream())
            );
            while((line = reader.readLine()) != null)
            {
                r+=line;
                //System.out.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(CopiaSeguridad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(CopiaSeguridad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    
    public static void Self_Exploratory(){
        System.out.println(CmdWin(serial_number));
        
        System.out.println("----------\n");
        
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(Runtime.getRuntime().maxMemory());
        
        System.out.println("----------\n");
        
        System.getProperties().list(System.out);
        
        System.out.println("----------\n");
        
        System.out.println(System.getProperty("os.name"));
    }
    
    public static boolean isAdmin(){
        String user = System.getProperty("user.name");
        String cmr=CmdWin("net user "+user);
        return cmr.contains("Admin") || cmr.contains("admin");
    }
    
    public static String encode(String d){
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
    public static void WriteLic(String lic){
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
            Logger.getLogger(Liz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String ReadLic(String dir){
        BufferedReader br = null;
        String r = "";
        try {
            String sCurrentLine; 
            br = new BufferedReader(new FileReader(dir));
            
            while ((sCurrentLine = br.readLine()) != null) {
                //System.out.println(sCurrentLine);
                r+=sCurrentLine;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return r;
    }
    
    public static String GetThisSysUbun(){
        String ff="";
        try{
            String dmire = eComand("sudo dmidecode"); // manera de obtener algunos datos de hardware en linux
            String proc = ReadFile(File.separator+"proc"+File.separator+"cpuinfo"); // manera de obtener algunos datos del procesador en linux
            ff = TrDMI(dmire)+System.getProperty("java.home")+TrProc(proc)+System.getProperty("user.dir");
            
        } catch(IOException | InterruptedException e){
            System.out.println("Exception");
        }
        return ff;
    }
    
    
    public static String eComand(String comando) 
            throws IOException, InterruptedException{
        
        Process p;
        
        StringBuilder output = new StringBuilder();
        
        p = Runtime.getRuntime().exec(comando);
        p.waitFor();
        
        BufferedReader reader = 
                new BufferedReader(new InputStreamReader(p.getInputStream()));
        
        String line="";
        
        while((line = reader.readLine())!= null){
            output.append(line+"\n");
        }
        
        return output.toString();
    }
    
    /**
     * ejecuta un comando en la shell de linux o en cmd  de windows
     * pero retorna solo las lineas de respuesta de la shell que conntienen la
     * palabra "contains".
     * @param comando
     * @param contains
     * @return
     * @throws IOException
     * @throws InterruptedException 
     */
    public static String eComand(String comando, String contains) 
            throws IOException, InterruptedException{
        
        Process p;
        
        StringBuilder output = new StringBuilder();
        
        p = Runtime.getRuntime().exec(comando);
        p.waitFor();
        
        BufferedReader reader = 
                new BufferedReader(new InputStreamReader(p.getInputStream()));
        
        String line="";
        
        while((line = reader.readLine())!= null){
            if(line.contains(contains)){
                line = line.replace("  ", "");
                line = line.replace(" ", "");
                output.append(line+"\n");
            }
        }
        
        return output.toString();
    }
    
    
    /**
     * permite leer un archivo de texto.
     * @param dir
     * @return 
     */
    public static String ReadFile(String dir){
        BufferedReader br = null;
        String r = "";
        try{
            String sCurrentLine;
            br = new BufferedReader(new FileReader(dir));
            while((sCurrentLine = br.readLine())!= null){
                r+=sCurrentLine+"\n";
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally{
            if(br != null) try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Liz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return r;
    }
    
    /**
     * arregla el string de la respuesta de dmidecode para que sea mas apropiado
     * para la labor de lienciamiento, se quitan redundancias de caracteres 
     * entre otros.
     * @param dmi
     * @return 
     */
    public static String TrDMI(String dmi){
        String r="";
        
        String uuid, serial, manufacturer, sku, vendor;
        uuid = ExtLineCont(dmi, "UUID:").replace("UUID:", "");
        serial = ExtLineCont(dmi, "Serial Number:").replace("Serial Number:", "");
        manufacturer = ExtLineCont(dmi, "Manufacturer:").replace("Manufacturer:", "");
        sku = ExtLineCont(dmi, "SKU Number:").replace("SKU Number:", "");
        vendor = ExtLineCont(dmi, "Vendor:").replace("Vendor:", "");
        
        r=uuid+serial+manufacturer+sku+vendor;
        r=r.replace("	", "");
        r=r.replace(" ", "");
        r=r.replace("-", "");
        return r;
    }
    
    
    /**
     * toma la respuesta de leer el archivo proc y acomoda el string
     * para que sea mas a propiado para la labor de licenciamiento.
     * @param proc
     * @return 
     */
    public static String TrProc(String proc){
        String r = "";
        
        String vendor_id, cpufam, model, mhz, mips;
        
        vendor_id = ExtLineCont(proc, "vendor");
        cpufam = ExtLineCont(proc, "cpu").replace("\t", "");
        model = ExtLineCont(proc, "model");
        mhz = ExtLineCont(proc, "Mhz");
        mips = ExtLineCont(proc, "mips");
        
        r=strip2punt(vendor_id)+strip2punt(cpufam)+strip2punt(model)+strip2punt(mhz)+strip2punt(mips);
        
        return r;
    }
    
    /**
     * funcion auxiliar  usada por TRPROC, lo que hace es partir
     * un string dado usando el caracter :, y descarta los impares, correspon
     * dientes a caracteres redundantes
     * @return 
     */
    public static String strip2punt(String s){
        String r = "";
        String[] aux = s.split(":");
        for(int i=1; i<aux.length; i=i+2){
            r+=aux[i];
        }
        return r;
    }
    
    /**
     * extrae la linea que contiene la palabra x
     * y la retorna sin modificacion
     */
    public static String ExtLineCont(String txt, String x){
        String r="";
        String[] Lines = txt.split("\n");//se hace un array donde cada indice es una linea
        for(int k=0; k<Lines.length; k++){// se barre cada linea buscando la coincidencia
            if(Lines[k].contains(x)){
                r+=Lines[k];// se concatena la linea que contiene x a r
            }
        }
        return r;
    }
    
}
