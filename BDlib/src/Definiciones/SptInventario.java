package Definiciones;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author esteban
 */
public class SptInventario {
    // definicion de un objeto tablla inventario para usar con un table view.
    
    SimpleStringProperty nombreTabla;
    
    public SptInventario(String nombreTabla){
        this.nombreTabla = new SimpleStringProperty(nombreTabla);
    }

    public String getNombreTabla() {
        return nombreTabla.get();
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = new SimpleStringProperty(nombreTabla);
    }
}
