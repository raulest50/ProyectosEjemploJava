

package Servlets;

import JavaLogic.BusquedaProductos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//este servlet atiende todas las peticiones externas apoyandose en otra clase
// que se comunica con la base de datos

public class Vestibulo extends HttpServlet {

    String operacion, busqueda, tipo_busqueda, impresion_salida, reporte,
            codigo, conteo;
    
    ArrayList<String[]> lista_p;
    
    int IntOperacion, IntConteo;
    
    BusquedaProductos ejecutor;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            // se determina que operacion se desea realizar
            operacion = request.getParameter("operacion");
            IntOperacion = Integer.parseInt(operacion);
            
            
            //se realiza la operacion
            switch (IntOperacion){
                
                //operacion de busqueda de producto
                case 1:
                    busqueda=request.getParameter("busqueda");
                    tipo_busqueda=request.getParameter("tipo_busqueda");
                    
                    buscar_producto(out);
                    break;
                    
                    
                //Reportar Agotado (escribir sobre la bd de agotados)    
                case 2:
                    reporte = request.getParameter("reporte");
                    break;
                    
                //agregar conteo a inventario
                case 3:
                    IntConteo = Integer.parseInt(request.getParameter("conteo"));
                    codigo = request.getParameter("codigo");
                    
                    break;
                    
            }
            
            //se imprime el resultado de la operacion en el servlet para que sea leaida por quien
            //requirio la operacion
            out.println(impresion_salida);

        }
    }
    
    public void buscar_producto(PrintWriter out){
        
        ejecutor = new BusquedaProductos();
        
        int Int_tip_busqueda = Integer.parseInt(tipo_busqueda);
        
        switch (Int_tip_busqueda) {

            //busqueda por descripcion
            case 1:
                
                ejecutor.busqueda_descripcion(busqueda);
                lista_p = ejecutor.getLista_productos();
                
                
                break;

                
            //busqueda por ultimos de barras
            case 2:
                
                ejecutor.Busqueda_ultimos_codigo(busqueda);
                lista_p = ejecutor.getLista_productos();

                break;
                
                
                //este caso nunca deberia ocurrir
                
            default:
                
                

        }

        impresion_salida = ListaP2String(lista_p);

    }
    
    
    public String ListaP2String(ArrayList<String[]> lista){
        
        String converted="";
        
        if(lista.isEmpty()){
            
            converted="np";
        }
        
        else{
            int tam = lista.size();
            int tam2 = lista.get(0).length;
            
            for (int j = 0 ; j<tam-1 ; j++){
                
                for(int k =0; k<tam2-1 ; k++){
                    
                    converted += lista.get(j)[k] + "/-/";
                    
                }
                
                converted += lista.get(j)[tam2-1] +"--///--";
                
            }
            
            for(int k =0; k<tam2-1 ; k++){
                    
                    converted += lista.get(tam-1)[k] + "/-/";
                    
                }
            
            converted += lista.get(tam-1)[tam2-1];
            
        }
        
        return converted;
        
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
