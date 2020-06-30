package Servlets;

import BD.Handlers.BDH_Productos;
import Definiciones.SptProducto;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.MalformedParametersException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author esteban
 * este es el servlet del servidor que se dedica solamente a servir informaacion sobre la base de datos
 * y no realiza operaciones de escritura sobre la misma.
 */
@WebServlet(name = "Info", urlPatterns = {"/info"})
public class Info extends HttpServlet {

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
            
            // numero de operacion
            int op = Integer.parseInt(request.getParameter("op"));
            
            switch(op){
                case 1: // busqueda de productos
                    String r = this.BuscarProducto(request);
                    response.getWriter().println(r);
                    break;
                case 2: //busqueda de cliente
                    
                    break;
                    
                case 3: // busqueda de proveedor
                    
                    break;
                    
            }
        }
    }
    
    public String BuscarProveedor(HttpServletRequest request){
        String r = "";
        return r;
    }
    
    public String BuscarCliente(HttpServletRequest request){
        String r = "";
        return r;
    }
    
    public String BuscarProducto(HttpServletRequest request){
        // se busca productos de la base de datos
        String r="", search;
        int type;
        search = request.getParameter("search");
        type = Integer.parseInt(request.getParameter("type"));
        BDH_Productos bdhp = new BDH_Productos();
        ArrayList<SptProducto> listaProductos = new ArrayList<>();
        
        switch(type){
            case 1:{
                try {// busqueda por descripcion
                    listaProductos = bdhp.BusquedaDescripcion(search);
                } catch (ClassNotFoundException | SQLException | MalformedParametersException ex) {
                    r=Signals.jve+ex.getMessage();
                }
            }
            break;
                
            case 2:{
                try {// busqueda por ultimos del codigo
                    listaProductos = bdhp.BusquedaLastCod(search);
                } catch (ClassNotFoundException | SQLException | MalformedParametersException ex) {
                    r=Signals.jve+ex.getMessage();
                }
            }
            break;
                
            case 3:{
                try {// busqueda por codigo exacto
                    listaProductos = bdhp.BusquedaCodExact(search);
                } catch (ClassNotFoundException | SQLException | MalformedParametersException ex) {
                    r=Signals.jve+ex.getMessage();
                }
            }
            break;
        }
        
        // si no ocurrio una excepcion y la lista esta vacia entonces imprime NNNRRR ningun resultado
        if(listaProductos.isEmpty() && !(r.contains("JavaException-->"))) r="NNNRRR";
        
        // no ocurrio una excepcion y la lista no esta vacia entonces se imprimen los resultados
        if(!listaProductos.isEmpty() && !(r.contains("JavaException-->"))){
            int t = listaProductos.size();
            for (int j = 0 ; j<t-1 ; j++){
                r += listaProductos.get(j).getCodigo()+ "/-/";
                r += listaProductos.get(j).getCosto()+ "/-/";
                r += listaProductos.get(j).getDescripcion()+ "/-/";
                r += listaProductos.get(j).getFamilia()+ "/-/";
                r += listaProductos.get(j).getIva() + "/-/";
                r += listaProductos.get(j).getLastUp() + "/-/";
                r += listaProductos.get(j).getPvpublico() + "/-/";
                r += listaProductos.get(j).getPvtienda() + "--///--";
            }
            r += listaProductos.get(t-1).getCodigo()+ "/-/";
                r += listaProductos.get(t-1).getCosto()+ "/-/";
                r += listaProductos.get(t-1).getDescripcion()+ "/-/";
                r += listaProductos.get(t-1).getFamilia()+ "/-/";
                r += listaProductos.get(t-1).getIva() + "/-/";
                r += listaProductos.get(t-1).getLastUp() + "/-/";
                r += listaProductos.get(t-1).getPvpublico() + "/-/";
                r += listaProductos.get(t-1).getPvtienda();
            
        }
        // si no entra a ninguno de los 2 if's entonces se imprime la descripcion de la excepcion.
        return r;
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

}
