/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualadministrator.Controllers;

import BD.Handlers.BDHVentas;
import BD.Handlers.BDH_Productos;
import Definiciones.SptVenta;
import java.lang.reflect.MalformedParametersException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author esteban
 */
public class VentasTabController{
    
    BDHVentas bdhv = new BDHVentas();
    
    @FXML
    public Label Ltest, LVentaDia, LGanancia, LNCompras, LNItems;
    
    @FXML
    public DatePicker DatePik;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        
    }
    
    @FXML
    public void onClickBexe(MouseEvent mev){
        try{
            LocalDate ld = DatePik.getValue();
            ArrayList<SptVenta> lv = bdhv.TraerVentasDelDia(ld.toString());
            ArrayList<Integer> vcn = CalcularTodo(lv);
            int v = vcn.get(0);
            int c = vcn.get(1);
            int n = vcn.get(2);
            int g = v-c;
            int nv = bdhv.GetNven_tday(ld.toString());
            
            LVentaDia.setText(Integer.toString(v));
            LNItems.setText(Integer.toString(n));
            LGanancia.setText(Integer.toString(g));
            LNCompras.setText(Integer.toString(nv));
            
            
        }
        catch(NullPointerException ex){
            Logger.getLogger(VentasTabController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Integer> CalcularTodo(ArrayList<SptVenta> lvs){
        BDH_Productos bdhp = new BDH_Productos();
        ArrayList<Integer> r = new ArrayList<>();
        int v = 0;
        int c = 0;
        int n = 0;
        try{
            for (int m=0; m< lvs.size(); m++){
                if(lvs.get(m).getTipoVenta()==1){
                    v+=Integer.parseInt(bdhp.BusquedaCodExact(lvs.get(m).getProducto().getCodigo()).get(0).getPvpublico())*lvs.get(m).getN();
                    c+=Integer.parseInt(bdhp.BusquedaCodExact(lvs.get(m).getProducto().getCodigo()).get(0).getCosto())*lvs.get(m).getN();
                    n+=lvs.get(m).getN();
                }
                else{
                    v+=Integer.parseInt(bdhp.BusquedaCodExact(lvs.get(m).getProducto().getCodigo()).get(0).getPvtienda())*lvs.get(m).getN();
                    c+=Integer.parseInt(bdhp.BusquedaCodExact(lvs.get(m).getProducto().getCodigo()).get(0).getCosto())*lvs.get(m).getN();
                    n+=lvs.get(m).getN();
                }
            }
        }
        catch(ClassNotFoundException | MalformedParametersException | SQLException | NullPointerException ex){
            Logger.getLogger(VentasTabController.class.getName()).log(Level.SEVERE, null, ex);
        }
        r.add(v);
        r.add(c);
        r.add(n);
        return r;
    }
    
    
}
