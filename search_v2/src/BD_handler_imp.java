



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 *
 * @author samsung
 */
public class BD_handler_imp {
    
    public static String direccion="jdbc:mysql://localhost:3306/granero";
    public static String user="root";
    public static String pass="mysql";
    public static String cfname="com.mysql.jdbc.Driver";
    
    
    
    public static boolean Codificar(String codigo, String descripcion, String costo,
            String iva, String pvpublico, String pvtienda, String familia) {
        
        boolean v=false;

        try {

            //establecimiento de la conxion
            Class.forName(cfname);
            Connection link = DriverManager.getConnection(direccion,user,pass);

            String frase = "insert into productos (codigo,descripcion,costo,iva,pvpublico,"
                    + "pvtienda,ultima_actualizacion,familia)"
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
            v=true;
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "excepcion de clase no encontrada --- BD_handler Codificar"+e.toString());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "excepcion de sql----- BD_handler Codificar  ----> \n" + e.toString());
        }
        
        return v;

    }

    
    
    public static boolean Modificar(String codigo, String descripcion, String costo,
            String iva, String pvpublico, String pvtienda, String familia){
        boolean v=false;

        try {

            //establecimiento de la conxion

            Class.forName(cfname);
            Connection link = DriverManager.getConnection(direccion, user, pass);

            String frase = "update productos set descripcion=?"
                    + " , costo=?, iva=?, pvpublico=?, pvtienda=?, ultima_actualizacion=now(), familia=? where codigo=?";

            PreparedStatement pst;
            pst = (PreparedStatement) link.prepareStatement(frase);

            
            pst.setString(1, descripcion);
            pst.setDouble(2, Double.parseDouble(costo));
            pst.setDouble(3, Double.parseDouble(iva));
            pst.setDouble(4, Double.parseDouble(pvpublico));
            pst.setDouble(5, Double.parseDouble(pvtienda));
            pst.setString(6, familia);
            pst.setString(7, codigo);

            pst.executeUpdate();

            pst.close();
            link.close();
            v=true;
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "excepcion de clase no encontrada --- BD_handler modificar");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "excepcion de sql----- BD_handler modificar ----> \n" + e.toString());
        }
        
        return v;
    }
    
    
    public static boolean existe_codigo(String codigo) {

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
    
    
    public static boolean Eliminar_Producto(String codigo){
        
        boolean v=false;
        
        try {

            //establecimiento de la conxion

            Class.forName(cfname);
            Connection link = DriverManager.getConnection(direccion, user, pass);

            String frase = "delete from productos where codigo=?";

            PreparedStatement pst;
            pst = (PreparedStatement) link.prepareStatement(frase);

            pst.setString(1, codigo);


            pst.executeUpdate();

            pst.close();
            link.close();
            v=true;
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "excepcion de clase no encontrada --- BD_handler eliminar producto"+e.toString());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "excepcion de sql----- BD_handler eliminar producto ----> \n" + e.toString());
        }
        
        return v;
        
    }
    
    
    public static ArrayList Busqueda_mod(String busqueda, int tpb) {

        
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
                
                String[] producto_datos = new String[8];

                producto_datos[0] = rs.getString("codigo");
                producto_datos[1] = rs.getString("descripcion");
                producto_datos[2] = Integer.toString(rs.getInt("costo"));
                producto_datos[3] = Integer.toString(rs.getInt("pvpublico"));
                producto_datos[4] = Integer.toString(rs.getInt("pvtienda"));
                producto_datos[5] = Integer.toString(rs.getInt("iva"));
                producto_datos[6] = rs.getString("familia");
                producto_datos[7] = rs.getString("ultima_actualizacion");

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
    

    
    public static ArrayList Busqueda_lastcod_only(String busqueda) {

        
        ArrayList lista_productos = new ArrayList();

        try {

            Class.forName(cfname);
            Connection link = DriverManager.getConnection(direccion, user, pass);
            
            String frase;
            PreparedStatement pst;

            frase = "select * from productos where codigo like ?";
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
        
        //JOptionPane.showMessageDialog(null, Integer.toString(lista_productos.size()));

        return lista_productos;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static ArrayList busqueda_improved(String schain) {

        ArrayList lista_productos = new ArrayList();
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
    
    
 
    
    public static String frase_builder(int tama){
        
        String frase_armada="select * from productos where";
        
        for (int j=0; j<tama ;j++){
            
        
            if(j==(tama-1)) frase_armada= frase_armada+" descripcion like ?";
            else frase_armada= frase_armada+" descripcion like ? and";
            
        }
        
        return frase_armada;
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