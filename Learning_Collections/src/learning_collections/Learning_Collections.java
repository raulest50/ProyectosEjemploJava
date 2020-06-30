package learning_collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.groupingBy;

/**
 *
 * @author esteban.
 * para aprender sobre la api de collections de java.
 * principalmente las funciones de agrupacion para haer reduccion 
 * de objetos repetidos en una lista y haccer merging de los datos.
 * 
 * Resumen, prueba exitosa:
 * Para hacer uso de la API collections apra unificar prodcutos,
 * se requiere del Objeto Map con la forma:
 * Map< [identiying data type], List< [Obj. type] >>
 * si lp es un [Obj. type] que contiene el atributo [identiying data type]
 * y un metodo [getAttribute] para obtener ese atributo entonces la siguiente
 * linea de codigo entrega un Map <[key] List<Obj. type>>
 * lp.stream().collect(groupingBy(Mipar::getCod));
 * 
 * En este mapa todos los elementos con la misma Key quedan en una lista.
 * 
 * <key_a, [obj1a, obj2a ....] >
 * <key_b, [obj1b, obj2b ....] >
 * <key_c, [obj1c, obj1c ....] >
 * <key_d, [obj1d, obj2d ....] >
 * .
 * .
 * .
 * 
 * Si se desea unificar cnatidades bastaria con sumar el atributo
 * cantidad de todos los Obj de la misma lista.
 */
public class Learning_Collections {

    public static void main(String[] args) {
        
        Learning_Collections lc = new Learning_Collections();
        lc.newMain(args);
    }
    
    public class Mipar{
        int qnt; // cantidad de items
        String cod; // identificador del item
        
        public Mipar(int qnt, String cod){
            this.qnt = qnt;
            this.cod = cod;
        }
        
        public String getCod(){
            return cod;
        }
        
        public int getQnt(){
            return qnt;
        }
    }
    
    public void newMain(String[] args){
        
        ArrayList<Mipar> lp = new ArrayList<>();
        lp.add(new Mipar(1, "p1"));
        lp.add(new Mipar(2, "p2"));
        lp.add(new Mipar(10, "p3"));
        lp.add(new Mipar(3, "p1"));
        lp.add(new Mipar(2, "p1"));
        lp.add(new Mipar(4, "p2"));
        
        PrintList(lp);
        
        Map<String, List<Mipar>> m = lp.stream().collect(groupingBy(Mipar::getCod));
        PrintMiMap(m);
        
    }
    
    /**
     * metodo que imprime los elementos recolectados del mapeo.
     * @param m 
     */
    public void PrintMiMap(Map<String, List<Mipar>> m){
        System.out.println("Mapa Recolectado:------");
        Set<String> keys = m.keySet();
        for(String k : keys){
            System.out.println("codigo: " + k + "   N:  " + this.SumLpar(m.get(k)));
        }
    }
    
    /**
     * recibe una lista de objetos Mipar
     * y suma sus cantidades. se usa por la funcion PrintMiMap
     * para hacer 
     * @param lpr
     * @return 
     */
    public int SumLpar(List<Mipar> lpr){
        int s = 0;
        for(Mipar mp : lpr){
            s += mp.getQnt();
        }
        return s;
    }
    
    
    /**
     * imprime la cantidad y el codigo de cada elemento dem la lista.
     * se usa para ver en consla la lista original y comparar con la
     * agrupacion de elementos hecha en el Map. se observa en esta prueva que efectivamente
     * funciona esta forma de agrupar como se desea.
     * @param li 
     */
    public void PrintList(List<Mipar> li){
        System.out.println("Lista Original:------");
       for(Mipar mp : li){
           System.out.println("Codigo: " + mp.cod + "   N: " + mp.qnt);
       }
        System.out.println("\n\n\n");
    }
    
}
