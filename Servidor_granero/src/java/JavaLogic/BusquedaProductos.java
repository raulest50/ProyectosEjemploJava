package JavaLogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

    
    
public class BusquedaProductos extends BDHandler{
    
    public ArrayList<String[]> lista_productos;
    
    
    public ArrayList<String[]> busqueda_descripcion(String schain) {

        lista_productos = new ArrayList();
        String[] busq_lis = schain.split(" ");

        String Frase;

        Frase = frase_builder(busq_lis.length);

        try {

            Class.forName(cfname);
            Connection link = DriverManager.getConnection(direccion, user, pass);
            PreparedStatement pst;
            pst = (PreparedStatement) link.prepareStatement(Frase);

            for (int j = 0; j < busq_lis.length; j++) {

                pst.setString(j + 1, "%" + busq_lis[j] + "%");

            }
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                String[] producto_datos = new String[5];

                producto_datos[0] = rs.getString("descripcion");
                producto_datos[1] = Integer.toString(rs.getInt("pvpublico"));
                producto_datos[2] = Integer.toString(rs.getInt("pvtienda"));
                producto_datos[3] = Integer.toString(rs.getInt("costo"));
                producto_datos[4] = rs.getString("ultima_actualizacion");

                lista_productos.add(producto_datos);
            }

            rs.close();
            pst.close();
            link.close();

        }
        catch (ClassNotFoundException e){
            
            JOptionPane.showMessageDialog(null
                    , "excepcion de clase no encontrada --- BD_handler busqueda_improved"+e.toString());
            
        }
        
        catch(SQLException e){
            
            JOptionPane.showMessageDialog(null
                    , "excepcion de sql----- BD_handler busqueda_improved ----> \n" + e.toString());
            
        }
        
        return lista_productos;
        
    }
    
    public String frase_builder(int tama){
        
        String frase_armada="select * from productos where";
        
        for (int j=0; j<tama ;j++){
            
        
            if(j==(tama-1)) frase_armada= frase_armada+" descripcion like ?";
            else frase_armada= frase_armada+" descripcion like ? and";
            
        }
        
        return frase_armada;
    }
    
    public ArrayList<String[]> Busqueda_ultimos_codigo(String busqueda) {

        
        lista_productos = new ArrayList();

        try {

            Class.forName(cfname);
            Connection link = DriverManager.getConnection(direccion, user, pass);
            
            String frase;
            PreparedStatement pst;

            frase = "select * from `granero`.`productos` where `codigo` like ?";
            busqueda = "%" + busqueda;

            pst = (PreparedStatement) link.prepareStatement(frase);
            pst.setString(1, busqueda);
            
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                
                String[] producto_datos = new String[5];

                producto_datos[0] = rs.getString("descripcion");
                producto_datos[1] = Integer.toString(rs.getInt("pvpublico"));
                producto_datos[2] = Integer.toString(rs.getInt("pvtienda"));
                producto_datos[3] = Integer.toString(rs.getInt("costo"));
                producto_datos[4] = rs.getString("ultima_actualizacion");

                lista_productos.add(producto_datos);
            }
            
            rs.close();
            pst.close();
            link.close();
            
            
        } catch (ClassNotFoundException e) {
            
            JOptionPane.showMessageDialog(null, "excepcion de clase no encontrada --- BD_handler Busqueda_mod"+e.toString());

        } catch (SQLException e) {
            
            JOptionPane.showMessageDialog(null, "excepcion de sql----- BD_handler Busqueda_mod ----> \n" + e.toString());

        }
        

        return lista_productos;
    }
    
    
    public BusquedaProductos(){
        
    }

    public ArrayList<String[]> getLista_productos() {
        return lista_productos;
    }
    
    public void AgregarConteo(String cod, int Num){
        // metodo para agregar  un conjunto contado en ell inventario
    }
    
}
