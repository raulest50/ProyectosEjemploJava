package Definiciones;

/**
 *
 * @author esteban
 */
public class Telefonos {
    
    public int IntTels[] = new int[4];
    public String[]Tels = new String[4];
    
    public Telefonos(String tels){
        Tels = tels.split("-"); // este constructor se usa para armar 
        for (int i=0; i<4; i++){ // el telefono basado en la base de datos
            IntTels[i] =  Integer.parseInt(Tels[i]);
        }
    }
    
    
}
