

package BD.Logica;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// en esta clase implementa toda la logica necesaria para validar los formularios
// y operaciones de la aplicacion.

public class validador {
    
    //mensajes de ayuda relacionados con las validaciones
    public final  String   
    producto_repetido="Ya exite un prodcto con este codigo de barras",
    no_es_numero="Los siguientes campos deben contener numeros: codigo de barras,iva,costo,pv publico y pv tienda",
    campo_vacio="Advertencia: ninguno de los campos de este panel pueden quedar vacios o sin especificar",
    precios_no_validos="El costo y los valores de venta deben ser numeros positivos. Ademas los precios de venta deben ser mayores al costo",
    stock_menor_q0="El Stock debe ser un numero ENTERO igual o mayor que cero",
    reservado_767="El codigo 767 es el unico codigo que esta reservado para el "
            + "correcto funcionamiento de este programa, porfavor asigne un codigo diferente a este producto.",
    codigo_reservado="767";
    
    public validador() {
        // contructor no parametrizado
    }
    
    //metodos base: usando estos metodos se hacen las validaciones de los distintos
    //formularios y operaciones de la aplicacion
    public boolean no_es_numero (String num){
        // este metodo determina si el String de argumento representa un
        // numero. si no representa un numero retorna true.
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
    
    /**
     *determina se usa para determinar si hay campos vacios
     * retorna true si el el campo esta vacio..
     * @param cadena
     * @return
     */
    public boolean vacio(String cadena){
        boolean r = false;
        if(cadena==null || "".equals(cadena) || " ".equals(cadena)) r=true;
        return r;
    }
    
    public String obt_fecha(){
        //se usa para obtenner la fecha del sistema como String
        String mifecha;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        mifecha=dateFormat.format(date); //2014/08/06 15:59:48
        return mifecha;
    }
    
}

   