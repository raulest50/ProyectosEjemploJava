/**
 * codigo para obtener la direccion mac en el equipo.
 * se supone que debe ser cross plataform.
 */
package getmacmi;
/**
 *
 * @author esteban
 */


import com.sun.java.swing.plaf.windows.WindowsTreeUI;
import static java.lang.System.out;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import Configuracion.*;




public class GetMACmi {
    
    public static void main(String[] args) {
        
        Config miconf = new Config();
        miconf.Put_string(ConfiKeys.key_liz, "nada");
        
        try {
            
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            
            ArrayList<NetworkInterface> alnetworks = Collections.list(networks);
            
            String Lmac = "";
            
            String dspn;
            for(int i=0; i<alnetworks.size(); i++){
                dspn = alnetworks.get(i).getDisplayName();
                if(dspn.contains("usb") || dspn.contains("USB")) alnetworks.remove(i);
            }
            
            for(int i=0; i<alnetworks.size(); i++){
                Lmac += fromBy2Str(alnetworks.get(i).getHardwareAddress());
            }
            
            System.out.println("--------++++++-------");
            System.out.println(Lmac);
            
            System.out.println("--------++++++-------");
            System.out.println("--------++++++-------");
            String user = System.getProperty("user.dir");
            System.out.println(user);

//for (NetworkInterface netIf : Collections.list(networks)) {
//    out.printf("Display name: %s\n", netIf.getDisplayName());
//    out.printf("Name: %s\n", netIf.getName());
//    displaySubInterfaces(netIf);
//    out.printf("\n");
//}

//byte[] mac = network.getHardwareAddress();
        } catch (SocketException ex) {
            Logger.getLogger(GetMACmi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public static String fromBy2Str(byte[] mac){
        
        StringBuilder sb = new StringBuilder();
        String r;
        
        try{
        
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        r = sb.toString();
        } catch(NullPointerException e){
            out.println(e.getMessage());
            r="";
        }
        if(r.contains("00-00-00-00-00-00-00")) r = "";
        return r;
    }
    
    
    static void displaySubInterfaces(NetworkInterface netIf) throws SocketException {
        Enumeration<NetworkInterface> subIfs = netIf.getSubInterfaces();
        
        for (NetworkInterface subIf : Collections.list(subIfs)) {
            out.printf("\tSub Interface Display name: %s\n", subIf.getDisplayName());
            out.printf("\tSub Interface Name: %s\n", subIf.getName());
        }
     }
    
}
