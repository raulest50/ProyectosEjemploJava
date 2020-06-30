
package bd_manager_v0;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

/**
 *
 * @author esteban
 */
public class BD_handler {
    
    public static String direccion="jdbc:mysql://127.0.0.1:3306/granero";
    public static String user="root";
    public static String pass="mysql";
    public static String cfname="com.mysql.jdbc.Driver";
    
    
    
    public static void Codificar(String codigo, String descripcion, String costo,
            String iva, String pvpublico, String pvtienda, String familia) {
        //esta funcion inserta productoos en la base de datos
        try {
            //establecimiento de la conxion
            Class.forName(cfname);
            Connection link = DriverManager.getConnection(direccion,user,pass);

            String frase = "insert into productos (codigo,descripcion,costo,iva,pvpublico,pvtienda,ultima_actualizacion,familia)"
                    + "values (?,?,?,?,?,?,now(),?) ";

            PreparedStatement pst;
            pst = (PreparedStatement) link.prepareStatement(frase);

            pst.setString(1, codigo);
            pst.setString(2, descripcion);
            pst.setDouble(3, Double.parseDouble(costo));
            pst.setDouble(4, Double.parseDouble(iva));
            pst.setDouble(5, Double.parseDouble(pvpublico));
            pst.setDouble(6, Double.parseDouble(pvtienda));
            pst.setString(7, familia);

            pst.executeUpdate();

            pst.close();
            link.close();
            JOptionPane.showMessageDialog(null,
                    "El producto fue ingresado corretamente :)", "Resumen de la operacion", INFORMATION_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "excepcion de clase no encontrada --- BD_handler Codificar"+e.toString());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "excepcion de sql----- BD_handler Codificar  ----> \n" + e.toString());
        }

    }

    
    
    public static void Modificar(String codigo, String descripcion, String costo,
            String iva, String pvpublico, String pvtienda, String familia,String codigo_nuevo){
// esta funcion modifica registros de la base de datos de productos
        try {

            //establecimiento de la conxion

            Class.forName(cfname);
            Connection link = DriverManager.getConnection(direccion, user, pass);

            String frase = "update productos set codigo=?, descripcion=?"
                    + " , costo=?, iva=?, pvpublico=?, pvtienda=?, ultima_actualizacion=now(), familia=? where codigo=?";

            PreparedStatement pst;
            pst = (PreparedStatement) link.prepareStatement(frase);

            pst.setString(1, codigo_nuevo);
            pst.setString(2, descripcion);
            pst.setDouble(3, Double.parseDouble(costo));
            pst.setDouble(4, Double.parseDouble(iva));
            pst.setDouble(5, Double.parseDouble(pvpublico));
            pst.setDouble(6, Double.parseDouble(pvtienda));
            pst.setString(7, familia);
            pst.setString(8, codigo);

            pst.executeUpdate();

            pst.close();
            link.close();
            JOptionPane.showMessageDialog(null,
                    "El producto fue ingresado modificado exitosamente :)", "Resumen de la operacion", INFORMATION_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "excepcion de clase no encontrada --- BD_handler modificar");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "excepcion de sql----- BD_handler modificar ----> \n" + e.toString());
        }
    }
    
    
    public static boolean existe_codigo(String codigo) {
        // esta funcion se usa para evitar la codificaicon de 
        // productos con el mismo codigo
        boolean existe = false;
        try {

            //establecimiento de la conxion
            Class.forName(cfname);
            Connection link = DriverManager.getConnection(direccion, user, pass);

            String frase = "select * from productos where codigo=?";

            PreparedStatement pst;
            pst = (PreparedStatement) link.prepareStatement(frase);

            pst.setString(1, codigo);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                existe = true;
            }

            pst.close();
            rs.close();
            link.close();
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "excepcion de clase no encontrada --- BD_handler existe codigo"+e.toString());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "excepcion de sql----- BD_handler existe codigo ----> \n" + e.toString());
        }
        return existe;
    } 
    
    
    public static void Eliminar_Producto(String codigo){
        try {

            //establecimiento de la conxion

            Class.forName(cfname);
            Connection link = DriverManager.getConnection(direccion, user, pass);

            String frase = "delete from produtos where codigo=?";

            PreparedStatement pst;
            pst = (PreparedStatement) link.prepareStatement(frase);

            pst.setString(1, codigo);


            pst.executeUpdate();

            pst.close();
            link.close();
            JOptionPane.showMessageDialog(null,
                    "El producto fue Eliminado exitosamente :)", "Resumen de la operacion", INFORMATION_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "excepcion de clase no encontrada --- BD_handler eliminar producto"+e.toString());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "excepcion de sql----- BD_handler eliminar producto ----> \n" + e.toString());
        }
        
    }
    
    
    public static ArrayList Busqueda_mod(String busqueda, int tpb) {

        String[] producto_datos =new String[8];
        ArrayList lista_productos = new ArrayList();

        try {

            Class.forName(cfname);
            Connection link = DriverManager.getConnection(direccion, user, pass);
            
            String frase;
            PreparedStatement pst;
            
            switch (tpb) {

                case 1:
                    frase = "select * from productos where codigo = ?";
                    break;

                case 2:
                    frase = "select * from productos where descripcion like ?";
                    busqueda = "%" + busqueda + "%";
                    break;

                case 3:

                    frase = "select * from productos where codigo like ?";
                    busqueda="%" + busqueda;
                    break;
                    
                default:
                    frase = "select * from productos where descripcion like ?";
                    break;
            }
            
            pst = (PreparedStatement) link.prepareStatement(frase);
            pst.setString(1,busqueda); 
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                
                producto_datos[0]=rs.getString("codigo");
                producto_datos[1]=rs.getString("descripcion");
                producto_datos[2]=rs.getString("costo");
                producto_datos[3]=rs.getString("pvpublico");
                producto_datos[4]=rs.getString("pvtienda");
                producto_datos[5]=rs.getString("iva");
                producto_datos[6]=rs.getString("familia");
                producto_datos[7]=rs.getString("ultima_actualizacion");
                
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
        
        //JOptionPane.showMessageDialog(null, Integer.toString(lista_productos.size()));

        return lista_productos;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getCfname() {
        return cfname;
    }
    
    
    
        
}






/* ultima frase que funciono


frase = "insert into productos (Codigo,Descripcion,Valor,Valor_Mayor,Costo,Iva,Ultima_Actualizacion,familia)"
                    + "values ('" + codigo + "','" + descripcion + "','" + Integer.parseInt(pvpublico) + "','" + Integer.parseInt(pvtienda)
                    + "','" + Integer.parseInt(costo) + "','" + Integer.parseInt(iva) + "',now(),'" + familia + "')";
*/