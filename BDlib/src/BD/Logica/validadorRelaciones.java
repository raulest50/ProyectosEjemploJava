package BD.Logica;

import BD.Handlers.BDH_Relaciones;
import Definiciones.SptProducto;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author esteban
 */
public class validadorRelaciones {
    
    public String error_mas2_rel = "No es posible establcer una relacion entre "
            + "los productos especificados ya que actualmente ya existen relaciones"
            + "configuradas para ellos. Recuerde que para configurar una relacion"
            + "en un grupo de productos es necesario que ninguno de ellos "
            + "haya sido previamente relacionado";
    
    
    public boolean TabRelacionesVacia(){
        boolean r = true;
        return r;
    }
    
    
    /**
     * Este metodo recibe como entrada un conjunto de productos
     * y retorna como respuesta un boolean true (en el primer espacio del 
     * ArrayList de return) que indica si es correcto establecer una relacion
     * de igualdad entre los productos de la lista lp. En caso contrario
     * se retorna false en el primer indice del ArrayList de return y en el 
     * segundo indice se retorna un String que explica la inconsistencia para
     * que el usuario pueda resolverla.
     * @param lp
     * @return 
     */
    public ArrayList ValidoCrearNuevaRelacion(ArrayList<SptProducto> lp)
            throws ClassNotFoundException, SQLException{
        
        ArrayList r = new ArrayList();
        BDH_Relaciones bdhr = new BDH_Relaciones();
        
        int related = 0; // indica el numero de productos relacionados
        
        // para poder establecer una relaion entre productos ninguno de ellos puede tener realciones previas
        // que es lo que se revisa en este metodo
        for (SptProducto lp1 : lp) {
            if(!bdhr.GetGrupo(lp1.getCodigo()).equals("")){
                related++;// por cada producto que tenga una relacion activa se suma +1
                break; // se sale del ciclo
            }
        }
        if(related > 0){ // si hay mas de 1 producto relacionado entonces no se puede crear una nueva realcion
            r.add(false); // el primer campo del arreglo infica que no se puede ahcer la relacion
            r.add(error_mas2_rel); // el segundo explica el motivo
        }
        else{
            r.add(true);
            r.add("");
        }
        return r;
    }
    
}
