package Definiciones;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author esteban
 */
public class SptProducto {
    
    public SimpleStringProperty Descripcion, Costo, Pvtienda, Pvpublico, 
            LastUp,Codigo,Iva,Familia, Stock;

    
    
    public SptProducto(String Desrcipcion, String Pvpublico, String Pvtienda, String Costo, 
            String LastUp, String Codigo, String Iva, String Familia, String Stock){
        
        this.Descripcion = new SimpleStringProperty(Desrcipcion); // descripcion arbitraria del producto
        this.Costo = new SimpleStringProperty(Costo); 
        this.Pvpublico = new SimpleStringProperty(Pvpublico); // precio de venta al publico
        this.Pvtienda = new SimpleStringProperty(Pvtienda); // precio de venta al por mayor
        this.LastUp = new SimpleStringProperty(LastUp); //fecha de ultima actualizacion del producto
        this.Iva = new SimpleStringProperty(Iva); // iva del producto en porcentaje
        this.Familia = new SimpleStringProperty(Familia); // string arbitrario que desribe el segmento al que pertenece este producto
        this.Codigo = new SimpleStringProperty(Codigo); // identificacion UNICA del producto, SOLO NUMEROS
        this.Stock = new SimpleStringProperty(Stock); // cantidad de unidades del producto en bodega + punto de venta
    }
    
    
    public String getDescripcion() {
        return Descripcion.get();
    }

    public String getCosto() {
        return Costo.get();
    }

    public String getPvtienda() {
        return Pvtienda.get();
    }

    public String getPvpublico() {
        return Pvpublico.get();
    }

    public String getLastUp() {
        return LastUp.get();
    }

    public String getIva() {
        return Iva.get();
    }

    public String getCodigo() {
        return Codigo.get();
    }

    public String getFamilia() {
        return Familia.get();
    }
    
    public String getStock(){
        return Stock.get();
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = new SimpleStringProperty(descripcion);
    }

    public void setCosto(String costo) {
        this.Costo = new SimpleStringProperty(costo);
    }

    public void setPvtienda(String pvtienda) {
        this.Pvtienda = new SimpleStringProperty(pvtienda);
    }

    public void setPvpublico(String pvpublico) {
        this.Pvpublico = new SimpleStringProperty(pvpublico);
    }

    public void setUltima_actualizacion(String ultima_actualizacion) {
        this.LastUp = new SimpleStringProperty(ultima_actualizacion);
    }

    public void setIva(String iva) {
        this.Iva = new SimpleStringProperty(iva);
    }

    public void setCodigo(String codigo) {
        this.Codigo = new SimpleStringProperty(codigo);
    }

    public void setFamilia(String familia) {
        this.Familia = new SimpleStringProperty(familia) ;
    }
    
    public void setStock(String stock){
        this.Stock = new SimpleStringProperty(stock);
    }
    
}
