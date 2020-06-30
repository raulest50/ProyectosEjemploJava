package Servlets;

import BD.Handlers.BDH_Productos;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author esteban
 * 
 * En este servlet estan todas las operaciones relacionadas con
 * la transformacion de los datos en la bd.
 * escribi, actualizar o eliminar
 */
public class Update extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        int op = Integer.parseInt(request.getParameter("op"));
        
        String codigo, descripcion, familia;
                int costo, pvpublico, pvtienda, iva, stock;
        
        switch(op){
            case 0: // codificar producto
                
                codigo = request.getParameter("c");
                descripcion = request.getParameter("d");
                costo = Integer.parseInt(request.getParameter("cos"));
                pvpublico = Integer.parseInt(request.getParameter("pvp"));
                pvtienda = Integer.parseInt(request.getParameter("pvt"));
                stock = Integer.parseInt(request.getParameter("s"));
                familia = request.getParameter("f");
                iva = Integer.parseInt(request.getParameter("iv"));
                
                CodificarProducto(codigo, descripcion, costo, pvpublico, pvtienda,
                        familia, stock, iva);
                
                break;
                
            case 1:// modificar producto
                codigo = request.getParameter("c");
                descripcion = request.getParameter("d");
                costo = Integer.parseInt(request.getParameter("cos"));
                pvpublico = Integer.parseInt(request.getParameter("pvp"));
                pvtienda = Integer.parseInt(request.getParameter("pvt"));
                stock = Integer.parseInt(request.getParameter("s"));
                familia = request.getParameter("f");
                iva = Integer.parseInt(request.getParameter("iv"));
                
                break;
                
            case 2:// eliminar producto
                break;
                
            case 3:// no determinado aun
                break;
                
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
    
    
    public String CodificarProducto(String codigo, String descripcion,
            int costo, int pvpublico, int pvtienda, String familia,
            int stock, int iv){
        
        BDH_Productos bdhp = new BDH_Productos();
        
        String ans = "";
        return ans;
    }
    
}
