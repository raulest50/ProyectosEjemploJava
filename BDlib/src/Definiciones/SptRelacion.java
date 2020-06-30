package Definiciones;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author esteban
 * 
 * id coresponde a la llave primaria para identificar una relacion.
 * p1 y p2 corresponde a los codigos de los productos que integran la 
 * relacion. Estos corresponde a la tabla de produtos.
 * codgrupo corresponde a un codigo que identifica o agrupa a varias relaciones.
 * gracias a codgrupo se puede aplicar los cambios en cascada a todos los 
 * productos que integren el grupo relacionado con tan solo cambiar uno de ellos.
 * de esta forma no solo se ahorra tiempo en el manteniemiento de la base de
 * datos si no que tambien se minimizan posibilidades de error ya que cuando se
 * deben cambiar varios productos que tienen el mismo precio por error se puede 
 * omitir 1 o mas de ellos sin intencion. El nombre de la relacion.
 */
public class SptRelacion {
    
    public SimpleIntegerProperty id;
    
    public SimpleStringProperty codgrupo, p1, p2, nombre;

    public SptRelacion(SimpleIntegerProperty id, SimpleStringProperty codgrupo, 
            SimpleStringProperty p1, SimpleStringProperty p2, SimpleStringProperty nombre) {
        this.id = id;
        this.codgrupo = codgrupo;
        this.p1 = p1;
        this.p2 = p2;
        this.nombre = nombre;
    }
    
    public SptRelacion(Integer id, String codgrupo, 
            String p1, String p2, String nombre) {
        this.id = new SimpleIntegerProperty(id);
        this.codgrupo = new SimpleStringProperty(codgrupo);
        this.p1 = new SimpleStringProperty(p1);
        this.p2 = new SimpleStringProperty(p2);
        this.nombre = new SimpleStringProperty(nombre);
    }
    
    public void setId(Integer id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public void setCodgrupo(String codgrupo) {
        this.codgrupo = new SimpleStringProperty(codgrupo);
    }

    public void setP1(String p1) {
        this.p1 = new SimpleStringProperty(p1);
    }

    public void setP2(String p2) {
        this.p2 = new SimpleStringProperty(p2);
    }

    public void setNombre(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
    }

    public Integer getId() {
        return id.get();
    }

    public String getCodgrupo() {
        return codgrupo.get();
    }

    public String getP1() {
        return p1.get();
    }

    public String getP2() {
        return p2.get();
    }

    public String getNombre() {
        return nombre.get();
    }
    
}
