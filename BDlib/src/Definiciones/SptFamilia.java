package Definiciones;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author esteban
 */
public class SptFamilia {
    
    public SimpleStringProperty id;

    public SptFamilia(String id) {
        this.id = new SimpleStringProperty(id);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id = new SimpleStringProperty(id);
    }
    
}
