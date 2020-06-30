package BD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author esteban
 */
public class CopiaSeguridad {
    
    
    public static boolean GenerarCopiaSeguridad(String dir){
        // genera archivo dump.sql en el escritorio con la base de datos
        String comando = "mysqldump -hlocalhost -uroot -p000012 granero > "+ dir+ "\\dump.sql";
        final String[] copiaescritorio = new String[] {"cmd.exe", "/c", comando};
        
        boolean r = true; // booleano que indica si la operacion resulta exitosa
        try {
            Process p=Runtime.getRuntime().exec(copiaescritorio);
            p.waitFor();
            BufferedReader reader=new BufferedReader(
                    new InputStreamReader(p.getInputStream())
            );
            String line;
            while((line = reader.readLine()) != null)
            {
                System.out.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(CopiaSeguridad.class.getName()).log(Level.SEVERE, null, ex);
            r = false;
        } catch (InterruptedException ex) {
            Logger.getLogger(CopiaSeguridad.class.getName()).log(Level.SEVERE, null, ex);
            r = false;
        }
        return r;
    }
    
    public static boolean EnviarCopiaACorreo(String email){
        boolean r = true;
        return r;
    }
}
