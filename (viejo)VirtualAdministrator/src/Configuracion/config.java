
package Configuracion;

import java.util.prefs.Preferences;

/**
 *
 * @author esteban
 */
public class config {
    
    Preferences prefs;
    String def_host;
    
    public config(){
        // obtencion del objeto prefs
        prefs = Preferences.userRoot();
        //prefs = Preferences.systemNodeForPackage(config.class);
    }
    
    // guardar String
    public void Put_string(String key, String value){  
        prefs.put(key, value);
    }
    
    
    //obtener String
    
    public String Get_string(String key){
        return prefs.get(key,"default");
    }
    
    
}
