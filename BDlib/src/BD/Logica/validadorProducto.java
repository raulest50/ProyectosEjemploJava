package BD.Logica;

import BD.Handlers.BDH_Productos;
import java.util.ArrayList;

/**
 *
 * @author esteban
 */
public class validadorProducto extends validador{
    
    public validadorProducto(){
        
    }
    
        //validaciones: a partir de aqui estan los metodos para validaciones especificas.
    //estas validaciones se hacen con ayuda de los metodos base de esta clase
    public ArrayList datos_validos_ingreso_producto(String cod,String de, String cos, String iva,String pvp, String pvt, String stock){
        // la famila no se valida porque proviende de un combo box el cual no permite seleccion nula
        ArrayList al = new ArrayList();
        boolean r=true;
        
        String inf_err="";
        
        if(vacio(cod) || vacio(de) ||vacio(cos) ||vacio(pvp) ||vacio(pvt) ||vacio(iva)){
            r=false;
            inf_err+=campo_vacio+"\n\n";
        }
        
        if(prices_correct(cos, pvp, pvt)== false){
            r=false;
            inf_err+=precios_no_validos+"\n\n";
        }
        
        if(ProductoRepetido(cod)){
            r=false;
            inf_err+=producto_repetido+"\n\n";
        }
        
        if(no_es_numero(cod) || no_es_numero(cos) || no_es_numero(pvp) ||no_es_numero(pvt) ||no_es_numero(iva) || no_es_numero(stock)){
            r=false;
            inf_err+=no_es_numero+"\n\n";
        }
        
        if(es_negativo(stock)){
            r=false;
            inf_err+=stock_menor_q0+"\n\n";
        }
        
        if(cod.equals(super.codigo_reservado)){
            r=false;
            inf_err+=reservado_767+"\n\n";
        }
        
        al.add(r);
        if(r==false) al.add(inf_err);
        return al; 
    }
    
    public ArrayList datos_validos_modificacion_producto_branch(String de, String cos, String iva,String pvp, String pvt, String stock){
        // la famila no se valida porque proviende de un combo box el cual no permite seleccion nula
        ArrayList al = new ArrayList();
        boolean r=true;
        
        String inf_err="";
        
        if(vacio(de) ||vacio(cos) ||vacio(pvp) ||vacio(pvt) ||vacio(iva)){
            r=false;
            inf_err+=campo_vacio+"\n\n";
        }
        
        if(prices_correct(cos, pvp, pvt)== false){
            r=false;
            inf_err+=precios_no_validos+"\n\n";
        }
        
        if(no_es_numero(cos) || no_es_numero(pvp) ||no_es_numero(pvt) ||no_es_numero(iva)|| no_es_numero(stock)){
            r=false;
            inf_err+=no_es_numero+"\n\n";
        }
        
        if(es_negativo(stock)){
            r=false;
            inf_err+=stock_menor_q0+"\n\n";
        }
        
        al.add(r);
        if(r==false) al.add(inf_err);
        return al; 
    }
    
    public boolean ProductoRepetido(String codigo){
        return new BDH_Productos().existe_codigo(codigo); // se usa un metodo de BDH_Productos que no se
        //relaciona con la GUI. retorna true si el producto ya existe en la BD.
    }
    
    public boolean prices_correct(String costo,String pvp, String pvt){
        // determina si la relacion entre los costos y precio de venta es
        // permisible. en primer lugar no se admiten numeros negativos
        // y el precio de venta al publico y al pormayor debe ser mayor al costo
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
    
    public boolean es_negativo(String snum){
        boolean r = true;
        int thenum;
        
        try{
            thenum = Integer.parseInt(snum);
            r = thenum<0;
        }
        catch(NumberFormatException e){
        }
        return r;
    }
    
}
