package Definiciones;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author esteban
 */
public class SptProveedor {
    
    public SimpleStringProperty Nombre, Codigo, Fax,
            Telefono1, Telefono2, Telefono3, Telefono4,
            Email, Direccion, Vendedor, PaginaWeb,
            Calificacion, keywords, UltMod;
    
    public SptProveedor(String Nombre, String Codigo, String Fax, String Telefono1,
            String Telefono2, String Telefono3, String Telefono4,
            String Email, String Direccion, String Vendedor, 
            String PaginaWeb, String Calificacion, String keywords){
        
        if(Fax == null) Fax = ""; // se revisan todos los campos no obligatorios
        if(Telefono2 == null) Telefono2 = "";// para evitar variables con valor 
        if(Telefono3 == null) Telefono3 = "";// null
        if(Telefono4 == null) Telefono4 = "";// esto genera un exepcion
        if(Email == null) Email = "";// al hacer resultSet.getString(int index, String ccolumn)
        if(Direccion == null) Direccion = "";
        if(Vendedor == null) Vendedor = "";
        if(PaginaWeb == null) PaginaWeb = "";
        if(keywords ==  null) keywords = "";
        
        this.Nombre = new SimpleStringProperty(Nombre); // se extraen los valores de los campos de texto
        this.Codigo = new SimpleStringProperty(Codigo);
        this.Fax = new SimpleStringProperty(Fax);
        this.Telefono1 = new SimpleStringProperty(Telefono1);
        this.Telefono2 = new SimpleStringProperty(Telefono2);
        this.Telefono3 = new SimpleStringProperty(Telefono3);
        this.Telefono4 = new SimpleStringProperty(Telefono4);
        this.Email = new SimpleStringProperty(Email);
        this.Direccion = new SimpleStringProperty(Direccion);
        this.Vendedor = new SimpleStringProperty(Vendedor);
        this.PaginaWeb = new SimpleStringProperty(PaginaWeb);
        this.Calificacion = new SimpleStringProperty(Calificacion);
        this.keywords = new SimpleStringProperty(keywords);
    }
    
    public SptProveedor(String Nombre, String Codigo, String Fax, String Telefonos[],
            String Email, String Direccion, String Vendedor, 
            String PaginaWeb, String Calificacion, String keywords, String UltMod){
        
        switch(Telefonos.length){
            case 1:
                this.Telefono1 = new SimpleStringProperty(Telefonos[0]);
                this.Telefono2 = new SimpleStringProperty("");
                this.Telefono3 = new SimpleStringProperty("");
                this.Telefono4 = new SimpleStringProperty("");
                break;
            case 2:
                this.Telefono1 = new SimpleStringProperty(Telefonos[0]);
                this.Telefono2 = new SimpleStringProperty(Telefonos[1]);
                this.Telefono3 = new SimpleStringProperty("");
                this.Telefono4 = new SimpleStringProperty("");
                break;
            case 3:
                this.Telefono1 = new SimpleStringProperty(Telefonos[0]);
                this.Telefono2 = new SimpleStringProperty(Telefonos[1]);
                this.Telefono3 = new SimpleStringProperty(Telefonos[2]);
                this.Telefono4 = new SimpleStringProperty("");
                break;
            case 4: 
                this.Telefono1 = new SimpleStringProperty(Telefonos[0]);
                this.Telefono2 = new SimpleStringProperty(Telefonos[1]);
                this.Telefono3 = new SimpleStringProperty(Telefonos[2]);
                this.Telefono4 = new SimpleStringProperty(Telefonos[3]);
                break;
        }
        
        this.Nombre = new SimpleStringProperty(Nombre); // se extraen los valores de los campos de texto
        this.Codigo = new SimpleStringProperty(Codigo);
        this.Fax = new SimpleStringProperty(Fax);
        this.Email = new SimpleStringProperty(Email);
        this.Direccion = new SimpleStringProperty(Direccion);
        this.Vendedor = new SimpleStringProperty(Vendedor);
        this.PaginaWeb = new SimpleStringProperty(PaginaWeb);
        this.Calificacion = new SimpleStringProperty(Calificacion);
        this.keywords = new SimpleStringProperty(keywords);
        this.UltMod = new SimpleStringProperty(UltMod);
    }

    public String getNombre() {
        return Nombre.get();
    }

    public String getCodigo() {
        return Codigo.get();
    }

    public String getFax() {
        return Fax.get();
    }

    public String getTelefono1() {
        return Telefono1.get();
    }

    public String getTelefono2() {
        return Telefono2.get();
    }

    public String getTelefono3() {
        return Telefono3.get();
    }

    public String getTelefono4() {
        return Telefono4.get();
    }

    public String getEmail() {
        return Email.get();
    }
    
    public String getDireccion() {
        return Direccion.get();
    }

    public String getVendedor() {
        return Vendedor.get();
    }

    public String getPaginaWeb() {
        return PaginaWeb.get();
    }

    public String getCalificacion() {
        return Calificacion.get();
    }

    public String getkeywords() {
        return keywords.get();
    }

    public void setNombre(String Nombre) {
        this.Nombre = new SimpleStringProperty(Nombre);
    }

    public void setCodigo(String Nit) {
        this.Codigo = new SimpleStringProperty(Nit);
    }

    public void setFax(String Fax) {
        this.Fax = new SimpleStringProperty(Fax);
    }

    public void setTelefonos1(String Telefonos1) {
        this.Telefono1 = new SimpleStringProperty(Telefonos1);
    }

    public void setTelefono2(String Telefono2) {
        this.Telefono2 = new SimpleStringProperty(Telefono2);
    }

    public void setTelefono3(String Telefono3) {
        this.Telefono3 = new SimpleStringProperty(Telefono3);
    }

    public void setTelefono4(String Telefono4) {
        this.Telefono4 = new SimpleStringProperty(Telefono4);
    }

    public void setEmail(String Email) {
        this.Email = new SimpleStringProperty(Email);
    }

    public void setDireccion(String Direccion) {
        this.Direccion = new SimpleStringProperty(Direccion);
    }

    public void setVendedor(String Vendedor) {
        this.Vendedor = new SimpleStringProperty(Vendedor);
    }

    public void setPaginaWeb(String PaginaWeb) {
        this.PaginaWeb = new SimpleStringProperty(PaginaWeb);
    }

    public void setCalificacion(String Calificacion) {
        this.Calificacion = new SimpleStringProperty(Calificacion);
    }

    public void setkeywords(String PalabrasClave) {
        this.keywords = new SimpleStringProperty(PalabrasClave);
    }
    
    public String getTelefonos(){
        String r = Telefono1.getValue();
        if(!Telefono2.isEmpty().getValue())r += "-"+getTelefono2();
        if(!Telefono3.isEmpty().getValue())r += "-"+getTelefono3();
        if(!Telefono4.isEmpty().getValue())r += "-"+getTelefono4();
        return r;
    }

    public String getUltMod() {
        return UltMod.get();
    }

    public void setUltMod(String UltMod) {
        this.UltMod = new SimpleStringProperty(UltMod);
    }
    
}
