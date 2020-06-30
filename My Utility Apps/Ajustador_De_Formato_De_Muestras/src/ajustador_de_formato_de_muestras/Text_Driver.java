
package ajustador_de_formato_de_muestras;

import java.util.ArrayList;

/**
 *
 * @author esteban
 */
public class Text_Driver {
    
    
    String[] linea;
    
    public Text_Driver(){
        
    }
    
    public String extraer_valores(String field){

        linea = field.split("\n");
        
        String r = "";
        
        String[] aux1, aux2;
        
        for (int i = 0; i<linea.length ; i++) {
            
            aux1 = linea[i].split("]");
            aux2 = aux1[1].split("i");
            r += aux2[0]+"\n";
            
        }
        
        r = r.replace("\n", ",");
        r = r.replace("\t", "");
        r = r.substring(0, r.length()-1);
        r = "[" +r+ "]";
        r= r.replace(",", ", ");
        return r;
    }
    
}
