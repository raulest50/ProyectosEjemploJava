

package bd_manager_v0;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.WARNING_MESSAGE;



public class validador {
    
    String repe="Ya exite un prodcto con este"
                    + " codigo de barras",
            
    nonum="Los siguientes campos deben contener numeros: codigo de barras,iva,costo,pv publico y pv tienda",
            
    vaci="advertencia, ninguno de los campos de "
            + "este panel pueden quedar vacios o sin especificar",
            
    prival="El costo y los valores de venta deben"
                    + " ser numeros positivos. Ademas los precios de venta deben ser mayores al costo";
    
    public boolean no_es_numero (String num){
        boolean r=false;
        double n;
                try{
                    n=Double.parseDouble(num);
                }
                
                catch(NumberFormatException e){
                    r=true;
                }
                return r;
    }
    
    
    
    public boolean repetido(String codigo){
        
        boolean nr=false;
        if(BD_handler.existe_codigo(codigo)) nr=true;
        return nr;
    }
    
    
    public boolean prices_correct(String costo,String pvp, String pvt){
        
        boolean r=false;
        double cos,pvti,pvpu;
        
        try{
            
            cos=Double.parseDouble(costo);
            pvti=Double.parseDouble(pvt);
            pvpu=Double.parseDouble(pvp);
            
            if((cos>0) && (pvpu>0) && (pvti>0) ){
                if(pvpu>cos && pvti>cos){
                    r=true;
                }
            }
        }
        
        catch(NumberFormatException e){
        }
        return r;
    }
    
 
    public boolean vacio(String cadena){
            
            boolean r = false;
            
            if(cadena==null || "".equals(cadena) || " ".equals(cadena)) r=true;
            
            return r;
    }
    

    
    public String obt_fecha(){
    String mifecha;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    mifecha=dateFormat.format(date); //2014/08/06 15:59:48
    return mifecha;
    }

    public validador() {
        
    }
    
    
    public boolean datos_para_ingreso_validos(String cod,String de, String cos, String pvp, String pvt, String iva){
        
        boolean r=true;
        
        String inf_err="";
        
        if(vacio(cod) || vacio(de) ||vacio(cos) ||vacio(pvp) ||vacio(pvt) ||vacio(iva)){
            r=false;
            inf_err+=vaci+"\n\n";
        }
        
        if(prices_correct(cos, pvp, pvt)== false){
            r=false;
            inf_err+=prival+"\n\n";
        }
        
        if(repetido(cod)){
            r=false;
            inf_err+=repe+"\n\n";
        }
        
        if(no_es_numero(cod) || no_es_numero(cos) || no_es_numero(pvp) ||no_es_numero(pvt) ||no_es_numero(iva)){
            r=false;
            inf_err+=nonum+"\n\n";
        }
        
        if(r==false) JOptionPane.showMessageDialog(null, inf_err, "Hay inconsistencias en los datos", WARNING_MESSAGE);
                
        
        return r;
        
        
    }
    
    
}
    
   