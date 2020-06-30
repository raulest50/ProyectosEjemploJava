package Servlets;

import BD.Handlers.BDH_Inventarios;
import Definiciones.SptConteo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author esteban
 * maneja los requerimientos de las aplicaciones android
 * relacionadas con el inventario, sobretodo conteos
 */
@WebServlet(name = "Inventario", urlPatterns = {"/i"})
public class Inventario extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            
            // se obtiene el numero de operacion
            int op = Integer.parseInt(request.getParameter("op"));
            String r;
            switch(op){
                case 1: // agregar conteo
                    r = this.AgregarConteo(request);
                    response.getWriter().println(r);
                    break;
                    
                case 2: // eliminar conteo
                    r = this.BorrarConteo(request);
                    response.getWriter().println(r);
                    break;
                    
                case 3: // modificar conteo
                    r = this.ModificarConteo(request);
                    response.getWriter().println(r);
                    break;
                    // traer la lista del contador
                case 4:
                    r = this.TraerListaDelContador(request);
                    response.getWriter().println(r);
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
        processRequest(request, response);
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

    private String AgregarConteo(HttpServletRequest request) {
        // se agrega un conteo a la base de datos y se retorna el resultado de la operacion 
        String r;
        SptConteo cont = new SptConteo(request.getParameter("c"), 
                Double.parseDouble(request.getParameter("N")), 
                request.getParameter("lugar"), 
                request.getParameter("idcon"), 
                request.getParameter("id"), 
                "", 
                request.getParameter("name"), 0);
        
        BDH_Inventarios bdhi = new BDH_Inventarios();
        try {
            r = bdhi.InsertarConteo(cont);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
             r = Signals.jve + ex.getMessage();// se imprime por parte del servidor la excepcion ocurrida
        }
        return r;
    }
    
    private String BorrarConteo(HttpServletRequest request){// se elimina un conteo
        // y se retorna el resultado de la operacion
        String r;
        BDH_Inventarios bdhi = new BDH_Inventarios();
        try {
            r = bdhi.EliminarConteo(Integer.parseInt(request.getParameter("id")));// se elimina el conteo indicado
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
            r = Signals.jve + ex.getMessage();// se indica que ocurrio una excepcion de java
        }
        return r;
    }
    
    private String ModificarConteo(HttpServletRequest request){
        String r;// se modifica un conteo en la base de datos y se retorna el resultado de la operacion
        SptConteo cont = new SptConteo(request.getParameter("c"), 
                Double.parseDouble(request.getParameter("N")), 
                request.getParameter("lugar"), 
                request.getParameter("idcon"), 
                request.getParameter("id"), 
                "", 
                request.getParameter("name"), 0);
        BDH_Inventarios bdhi = new BDH_Inventarios();
        try {
            r = bdhi.ModificarConteo(cont);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
            r = Signals.jve + ex.getMessage();
        }
        return r;
    }

    private String TraerListaDelContador(HttpServletRequest request) {
        String r = "";
        BDH_Inventarios bdhi = new BDH_Inventarios();
        ArrayList<SptConteo> listaDelCont = new ArrayList<>();
        
        if(bdhi.getEstadoInvt()){
            String TablaActiva = bdhi.getNombreTabAct();
            
            try {
                listaDelCont = bdhi.ListaDelContador(request.getParameter("idcon"), TablaActiva);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
                r = Signals.jve + ex.getMessage();
            } catch (SQLException ex) {
                Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
                r = Signals.jve + ex.getMessage();
            }
            if(listaDelCont.isEmpty() && !(r.contains("JavaException-->"))) r="NNNRRR";
            
            // no ocurrio una excepcion y la lista no esta vacia entonces se imprimen los resultados
            if(!listaDelCont.isEmpty() && !(r.contains("JavaException-->"))){
                int t = listaDelCont.size();
                for (int j = 0 ; j<t-1 ; j++){
                    r += listaDelCont.get(j).getCodigo()+ "/-/";
                    r += listaDelCont.get(j).getCosto()+ "/-/";
                    r += listaDelCont.get(j).getNombre()+ "/-/";
                    r += listaDelCont.get(j).getId() + "/-/";
                    r += listaDelCont.get(j).getLugar() + "/-/";
                    r += Double.toString(listaDelCont.get(j).getN()) + "/-/";
                    r += listaDelCont.get(j).getTime() + "--///--";
                }
                r += listaDelCont.get(t-1).getCodigo()+ "/-/";
                r += listaDelCont.get(t-1).getCosto()+ "/-/";
                r += listaDelCont.get(t-1).getNombre()+ "/-/";
                r += listaDelCont.get(t-1).getId() + "/-/";
                r += listaDelCont.get(t-1).getLugar() + "/-/";
                r += Double.toString(listaDelCont.get(t-1).getN()) + "/-/";
                r += listaDelCont.get(t-1).getTime();
            }
            else{
                r ="No";
            }
        }
        return r;
    }
}
