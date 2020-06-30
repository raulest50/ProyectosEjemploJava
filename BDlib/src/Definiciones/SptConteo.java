package Definiciones;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author esteban
 * Representacion de un Conteo.
 * este tipo de objeto esta concordancia con las necesidades de las
 * Tablas de java FX por lo que este objeto se puede usar directamente.
 * la relacion con SptProducto es solo el coddigo y el nombre.
 * apartir de este objeto se puede obtener un SptProducto por medio del 
 * codigo que la primary key de la tabla de productos.
 * 
 */
public class SptConteo {
    
    /**
     * 
     */
    SimpleStringProperty Codigo;// codigo del producto
    SimpleDoubleProperty N; // cantidad de elementos contados
    SimpleStringProperty Lugar; // lugar donde se hizo el conteo
    SimpleStringProperty idContador;
    SimpleStringProperty id; // llave primaria de la tabla, numero autoincremental
    SimpleStringProperty time; // instante de tiempo en que fue agregado a la base de datos
    SimpleStringProperty Nombre; // nombre - descripcion del producto contado
    SimpleDoubleProperty Costo; // precio de costo con iva incluido del producto

    public SptConteo(String Codigo, double N, String Lugar, String idContador, 
            String id, String time, String Nombre, double costo) {
        this.Codigo = new SimpleStringProperty(Codigo);
        this.N = new SimpleDoubleProperty(N);
        this.Lugar = new SimpleStringProperty(Lugar);
        this.idContador = new SimpleStringProperty(idContador);
        this.id = new SimpleStringProperty(id);
        this.time = new SimpleStringProperty(time);
        this.Nombre = new SimpleStringProperty(Nombre);
        this.Costo = new SimpleDoubleProperty(costo);
    }

    public String getCodigo() {
        return Codigo.get();
    }

    public double getN() {
        return N.get();
    }

    public String getLugar() {
        return Lugar.get();
    }

    public String getIdContador() {
        return idContador.get();
    }

    public String getId() {
        return id.get();
    }

    public String getTime() {
        return time.get();
    }

    public String getNombre() {
        return Nombre.get();
    }
    
    public double getCosto(){
        return Costo.get();
    }

    public void setCodigo(String Codigo) {
        this.Codigo = new SimpleStringProperty(Codigo);
    }

    public void setN(double N) {
        this.N = new SimpleDoubleProperty(N);
    }

    public void setLugar(String Lugar) {
        this.Lugar = new SimpleStringProperty(Lugar);
    }

    public void setIdContador(String idContador) {
        this.idContador = new SimpleStringProperty(idContador);
    }

    public void setId(String id) {
        this.id = new SimpleStringProperty(id);
    }

    public void setTime(String time) {
        this.time = new SimpleStringProperty(time);
    }

    public void setNombre(String Nombre) {
        this.Nombre = new SimpleStringProperty(Nombre);
    }
    
    public void setCosto(double Costo){
        this.Costo = new SimpleDoubleProperty(Costo);
    }
}
