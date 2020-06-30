/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Definiciones;

import BD.Handlers.BDH_Productos;


/**
 *
 * @author esteban
 */
public class SptVenta {
    
    public int id, idconjunto, tipoVenta, cliente, N;
    public String time;
    public SptProducto producto;
    
    BDH_Productos bdhp = new BDH_Productos();

    public SptVenta(int id, int idconjunto, int N,
            int tipoVenta, int cliente, String time, SptProducto producto){
        this.id = id;
        this.idconjunto = idconjunto;
        this.N = N;
        this.tipoVenta = tipoVenta;
        this.cliente = cliente;
        this.time = time;
        this.producto = producto;
    }

    public int getId() {
        return id;
    }

    public int getIdconjunto() {
        return idconjunto;
    }

    public int getN() {
        return N;
    }

    public int getTipoVenta() {
        return tipoVenta;
    }

    public int getCliente() {
        return cliente;
    }

    public String getTime() {
        return time;
    }

    public BDH_Productos getBdhp() {
        return bdhp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdconjunto(int idconjunto) {
        this.idconjunto = idconjunto;
    }

    public void setN(int N) {
        this.N = N;
    }

    public void setTipoVenta(int tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setBdhp(BDH_Productos bdhp) {
        this.bdhp = bdhp;
    }

    public SptProducto getProducto() {
        return producto;
    }

    public void setProducto(SptProducto producto) {
        this.producto = producto;
    }
    
}
