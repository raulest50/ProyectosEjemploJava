package BD.Logica;

import BD.Handlers.BDH_Proveedores;
import Definiciones.SptProveedor;
import java.util.ArrayList;

/**
 *
 * @author esteban
 */
public class validadorProveedor extends validador{
    
    BDH_Proveedores bdhp = new BDH_Proveedores();
    
    String fn = "\n\n";
    // mensajes de explicacion de error de validacion.
    String error_vacios = "No pude dejar los siguientes campos vacios: "
            + "Nombre, El primer telefono ni el Codigo"+fn,
            
            error_numero_telefono="uno/unos de los telefonos ingresados no es/son validos"+fn,
            
            error_Nit = "El nit ingresado no es valido. Recuerde que: \n"
            + "El nit solo debe contener caracctees numericos a menos de que"
            + " emplee el digito de seguridad en cuyo caso se agreg al final "
            + "separado de un guion medio \"-\" \n"
            + "En caso de que desee agregar un nit fictisio se agrega como digito de"
            + "verificacion \"000\" \n"+fn,
            error_ya_existe = "El codigo asignado a este proveedor ya esta en uso, intente otro"
            + "codigo por vafor"+fn;
    
    public validadorProveedor(){
    }
    public ArrayList DatosInteres(SptProveedor spp){
        ArrayList r = new ArrayList<>();
        boolean valido = true;
        String error_info = "";
        
        if( (!vacio(spp.getTelefono1()) && no_es_numero(spp.getTelefono1())) ||
                (!vacio(spp.getTelefono2()) && no_es_numero(spp.getTelefono2()))  ||
                (!vacio(spp.getTelefono3()) && no_es_numero(spp.getTelefono3()))  ||
                (!vacio(spp.getTelefono4()) && no_es_numero(spp.getTelefono4())))  {
            valido = false;// en caso de que se hallan llenado algunos telefonos
            // con caracteres no numeericos en lugar de dejarsen vacios
            error_info += this.error_numero_telefono;
        }
        
        if( vacio(spp.getTelefono1()) || vacio(spp.getNombre()) || vacio(spp.getCodigo()) ){
            valido = false; // se revisan que los campos mas importantes
            // no esten vacios.
            error_info += this.error_vacios;
        }
        
        r.add(valido);
        r.add(error_info);
        return r;
    }
    
    public ArrayList ingresoValido(SptProveedor spp){
        ArrayList v1 = DatosInteres(spp);
        if( (boolean) v1.get(0)){
            if(bdhp.existe_codigo(spp.getCodigo())){
                v1.set(0, false);
                v1.set(1, (String) v1.get(1)+this.error_ya_existe);
            }
        }
        return v1;
    }
    
    public boolean NitNoValido(String nit){// este  metodo valida el rut
        // puede introducirse el nit con el ultimo digito de seguridad o no
        // este metodo verifica que
        boolean r = true; // boleano quee indica true si el ni es valido
        String n[] = nit.split("-");// se separa si hay digito de verificacion de seguridad
        
        try{
            if(this.no_es_numero(n[0]) || this.no_es_numero(n[1])) r = false;
            if(n.length >=3 ) r = false; // se verifica que se use el guion solo una vez
            // se verifica que tanto el numero de verificacion como el nit mismo
            // sean numero y que el digito de verificacion sea un solo  digito
            if(!(n[1].length()==1) && !(n[1].length()==3)) r = false;
            // hay un caso especial para el caso de registros sin nit
            // en el que se agrega como digito de seguridad 3 ceros
            if( !(n[1].length()==3) && !(n[1].equals("000"))) r = false;
        }
        catch(NullPointerException ex){ // si ocurrre esta excepcion es porque
            // el nit ingresado no posee separador "-" o si se incluyo entonces no
            // se puso ningun caracter seguido del "-"
            r = true;
            if(this.no_es_numero(nit)) r = false;
        }
        return r;
    }
    
}
