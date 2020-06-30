/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import BD.Handlers.BDHVentas;
import BD.Handlers.BDH_Productos;
import Definiciones.SptProducto;
import Definiciones.SptVenta;
import Impresion.LocalPrinter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.MalformedParametersException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author erich
 */
@WebServlet(name = "Ventas", urlPatterns = {"/v"})
public class Ventas extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, PrintException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            int op = Integer.parseInt(request.getParameter("op"));
            
            switch(op){
                
                //operacion 0: se desea imprimir la venta
                case 0:
                    String impresion = request.getParameter("venta");
                    LocalPrinter impresora = new LocalPrinter();
                    impresora.Imprimir(impresora.ReceiptStrGen(impresion));
                    break;
                    
                //operacion 1: se desea guardar una venta    
                case 1:
                    BDHVentas bdhv = new BDHVentas();
                    BDH_Productos bdhp = new BDH_Productos();
                    String Pventas = request.getParameter("venta");
                    String pr1[] = Pventas.split("/-/");
                    ArrayList<SptVenta> Lventas = new ArrayList<>();
                    int idx = bdhv.GetConjIdx();
                    for (int k=0; k<pr1.length ; k++){
                        String aux[] = pr1[k].split(":");
                        try{
                        SptProducto p = bdhp.BusquedaCodExact(aux[0]).get(0);
                        Lventas.add(new SptVenta(0, 0, Integer.parseInt(aux[1]), 1, 1, "", p));
                        bdhv.GuardarVenta(Lventas.get(k), idx);
                        }
                        catch(ClassNotFoundException | MalformedParametersException | SQLException e){
                            
                        }
                    }
                    break;
            }
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException | PrintException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException | PrintException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
