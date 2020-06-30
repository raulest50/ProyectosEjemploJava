package dbf2sql_ng;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author esteban
 */
public class BD_Handler {
    
    
    /**
     * la base de datos de HYL instalada en el negocio NG se llama fashion.
     */
    private final String direccion="jdbc:mysql://127.0.0.1:3306/fashion";
    
    private String user;
    private String pass;
    private final String cfname="com.mysql.jdbc.Driver";
    private final String jlangc = "class java.lang.";
    
    // objetos para hacer transacciones con la bd Mysql
    public Connection link;
    public ResultSet rs;
    public PreparedStatement pst;
    
    // conexion a la bd fashion
    public void SetConnection() throws ClassNotFoundException, SQLException {
        Class.forName(this.cfname);
        this.link = DriverManager.getConnection(this.direccion, this.user, this.pass);
    }
    
    // se inicializan log credentials de mysql en constructor
    public BD_Handler(String user, String pass){
        this.user = user;
        this.pass = pass;
    }
    
    
    /**
     * metodo que inserta el cliente especificado en el argumento
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public void InsertCliente(
            int consecutivo, int tipide, String numide, // 1 2 3
            String razonsocial, String direccion, int codciu,  // 4 5 6  
            String telefono, String correoelectronico, String fechaing, // 7 8 9 
            int codtipovalor, String celular, String fechanac, // 10 11 12
            int genero, int unidadresidencial, String apartamento, // 13 14 15 
            String contacto, int rtfuente, int rtiva, // 16 17 18 
            int rtica, String precio, String plazo, // 19 20 21
            String desciudad, int idcliente, int cupo, // 22 23 24
            String usuarioven, int comisionporcen, String fechaenvcorr, // 25 26 27
            int disalertacartera, int esexcento, int esneto, // 28 29 30
            int diascredito, int tipo, int esautoretenedor, // 31 32 33  
            int esdeclarante, int omitetextocredfac, int esretenedor, // 34 35 36 
            int esproveedor, String observacion, int factoremialqui, // 37 38 39
            int clasecliente) // 40
            throws ClassNotFoundException, SQLException{
        
        this.SetConnection();
        this.pst = (PreparedStatement) this.link.prepareStatement(SQL_Handler.Insert_Cli);
        
        this.pst.setInt(1, consecutivo); // llave primaria autoincremento
        this.pst.setInt(2, tipide); // tipo identificacion
        this.pst.setString(3, numide); // numero de identificacion
        this.pst.setString(4, razonsocial); // nombre
        this.pst.setString(5, direccion);
        this.pst.setInt(6, codciu); // codigo de ciudad
        this.pst.setString(7, telefono);
        this.pst.setString(8, correoelectronico);
        this.pst.setString(9, fechaing); // fecha de ingreso
        this.pst.setInt(10, codtipovalor); // indica si es mayotista etc
        this.pst.setString(11, celular);
        this.pst.setNull(12, java.sql.Types.DATE);
        this.pst.setNull(13, java.sql.Types.INTEGER); // genero masculino o femenino
        this.pst.setNull(14, java.sql.Types.INTEGER); // unidad residencial
        this.pst.setNull(15, java.sql.Types.VARCHAR); // apartamento
        this.pst.setString(16, contacto);
        this.pst.setInt(17, rtfuente);
        this.pst.setInt(18, rtiva);
        this.pst.setInt(19, rtica);
        this.pst.setString(20, precio);
        this.pst.setString(21, plazo);
        this.pst.setString(22, desciudad);
        this.pst.setNull(23, java.sql.Types.INTEGER); // id cliente, solo el 8888 es 1, de resto la app los mete como null
        this.pst.setNull(24, java.sql.Types.INTEGER); // cupo 
        this.pst.setNull(25, java.sql.Types.VARCHAR); // usuarioven
        this.pst.setNull(26, java.sql.Types.INTEGER); // comisionporcen
        this.pst.setNull(27, java.sql.Types.DATE); //fechaenvcoorr
        this.pst.setInt(28, java.sql.Types.INTEGER); // dias alerta cartera
        this.pst.setInt(29, esexcento); 
        this.pst.setNull(30, java.sql.Types.INTEGER); // esneto
        this.pst.setNull(31, java.sql.Types.INTEGER); // dias credito
        this.pst.setNull(32, java.sql.Types.INTEGER); // tipo
        this.pst.setNull(33, java.sql.Types.INTEGER); // es autorretenedor
        this.pst.setNull(34, esdeclarante); // es delcarante
        this.pst.setNull(35, java.sql.Types.INTEGER); //omite texto credito
        this.pst.setNull(36, java.sql.Types.INTEGER); // es retenedor
        this.pst.setNull(37, java.sql.Types.INTEGER); // es proveedor
        this.pst.setString(38, observacion);
        this.pst.setNull(39, java.sql.Types.INTEGER); // factor remialqui
        this.pst.setNull(40, java.sql.Types.INTEGER); // clase cliente
        
        this.pst.executeUpdate();
        
        this.CerrarUpdate();
    }
    
    /**
     * metodo que toma 
     * @param nombre
     * @return 
     * @throws java.sql.SQLException 
     * @throws java.lang.ClassNotFoundException 
     */
    public ArrayList<MyCli> GetNit(String nombre) 
            throws SQLException, ClassNotFoundException{
        
        ArrayList<MyCli> lista_nits = new ArrayList();
        
        this.SetConnection();
        this.pst = (PreparedStatement) this.link.prepareStatement(SQL_Handler.Select_Nit);
        
        this.pst.setString(1, nombre);
        this.rs = this.pst.executeQuery();
        
        /**
         * se entrega el query con todos los nits que coincidan con 
         * el nombre especificado de forma que se pueda verificar los 3 casos.
         * 0, 1 o mas de uno.
         */
        while(rs.next()){
            lista_nits.add(new MyCli(rs.getString("numide"), rs.getInt("consecutivo")));
        }
        CerrarTodo(); // se cierran las conexiones
        return lista_nits;
    }
    
    public class MyCli{
        public String nit;
        public int consecutivo;
        public MyCli(String nit, int consecutivo) {
            this.nit = nit;
            this.consecutivo = consecutivo;
        }
        
    }
    
    /**
     * se debe ejecutar al final de un metodo en que se use un query
     * @throws SQLException 
     */
    public void CerrarTodo() throws SQLException{
        rs.close();
        pst.close();
        link.close();
    }
    
    /**
     * se debe ejeccutar a final de un metodo en el que se haga un sql
     * update.
     * @throws SQLException 
     */
    public void CerrarUpdate() throws SQLException{
        this.link.close();
        this.pst.close();
    }
    
    
    /**
     * Metodo que inserta un record en la tabla de pedcli, que es la tabla
     * donde se guarda el encabezado de una factura.
     */
    public int INSERT_INTO_pedcli(
            int consecutivo, int numfac, String fechagen, // 1 2 3
            int rangoinicial, int rangofinal, String fechaaut, // 4 5 6
            String usuariogen, String resolucion, int consecven, // 7 8 9
            int conseccli, int conseccede, double valfac, // 10 11 12
            int estadope, String fechagenhora, String fechacad, // 13 14 15
            int estadofac, double valpag, String causaanulacion, // 16 17 18 
            int originalcopia, int estadopago, String usuarioanu, // 19 20 21
            String fechaanu, double iva, int tipoventa, // 22 23 24
            int impresa, int pagocont, String fecremi, // 25 26 27
            int numremi, int porcendesc, double retefuente, // 28 29 30
            double reteiva, double reteica, double reteotros, // 31 32 33
            String prefijo, String obra, String observacion, // 34 35 36 
            int abierta, int codtipocomision, int comisionporcen, // 37 38 39
            int comisionvalor, int consecdirecc, double descuento, // 40 41 42
            int estado, String notaoculta, int chulopagocomis, // 43 44 45 
            int cierreparcial, String cuenta, String placa, // 46 47 48
            int marca, String nroordenserv, int consecaerolinea, // 49 50 51
            String destinoviaje, int esdomicilio, int encardomicilio, // 52 53 54
            String fechapagocomis, String fechaprogramacion, String correoenvioalqui, // 55 56 57
            String tipopago, double porcendcto // 58 59
    ) throws ClassNotFoundException, SQLException {
        
        this.SetConnection();
        this.pst = (PreparedStatement) this.link.prepareStatement(SQL_Handler.INSERTpedcli, Statement.RETURN_GENERATED_KEYS);
        this.pst.setInt(1, consecutivo);
        this.pst.setInt(2, numfac);
        this.pst.setString(3, fechagen);
        this.pst.setInt(4, rangoinicial);
        this.pst.setInt(5, rangofinal);
        this.pst.setString(6, fechaaut);
        this.pst.setString(7, usuariogen);
        this.pst.setString(8, resolucion);
        this.pst.setInt(9, consecven);
        this.pst.setInt(10, conseccli);
        this.pst.setInt(11, conseccede);
        this.pst.setDouble(12, valfac);
        this.pst.setInt(13, estadope);
        this.pst.setString(14, fechagenhora);
        this.pst.setString(15, fechacad);
        this.pst.setInt(16, estadofac);
        this.pst.setDouble(17, valpag);
        this.pst.setString(18, causaanulacion);
        this.pst.setInt(19, originalcopia);
        this.pst.setInt(20, estadopago);
        this.pst.setString(21, usuarioanu); // usuario anu
        this.pst.setNull(22, java.sql.Types.DATE); // fechaanu
        this.pst.setDouble(23, iva);
        this.pst.setInt(24, tipoventa);
        this.pst.setInt(25, impresa);
        this.pst.setInt(26, pagocont);
        this.pst.setString(27, fecremi);
        this.pst.setInt(28, numremi);
        this.pst.setInt(29, porcendesc);
        this.pst.setDouble(30, retefuente);
        this.pst.setDouble(31, reteiva);
        this.pst.setDouble(32, reteica);
        this.pst.setDouble(33, reteotros);
        this.pst.setString(34, prefijo);
        this.pst.setString(35, obra);
        this.pst.setString(36, observacion);
        this.pst.setInt(37, abierta);
        this.pst.setInt(38, codtipocomision);
        this.pst.setInt(39, comisionporcen);
        this.pst.setInt(40, comisionvalor);
        this.pst.setInt(41, consecdirecc);
        this.pst.setDouble(42, descuento);
        this.pst.setInt(43, estado);
        this.pst.setString(44, notaoculta);
        this.pst.setInt(45, chulopagocomis);
        this.pst.setInt(46, cierreparcial);
        this.pst.setString(47, cuenta);
        this.pst.setString(48, placa);
        this.pst.setInt(49, marca);
        this.pst.setString(50, nroordenserv);
        this.pst.setInt(51, consecaerolinea);
        this.pst.setString(52, destinoviaje);
        this.pst.setInt(53, esdomicilio);
        this.pst.setInt(54, encardomicilio);
        this.pst.setNull(55, java.sql.Types.DATE); // fechapagocomis
        this.pst.setNull(56, java.sql.Types.DATE); // fechaprogramacion
        this.pst.setString(57, correoenvioalqui);
        this.pst.setString(58, tipopago);
        this.pst.setDouble(59, porcendcto);
        this.pst.executeUpdate();
        
        // ya que el consecutivo es autoincremental
        // se debe leer con get Generated key el 
        // el valor asignado.
        this.rs = this.pst.getGeneratedKeys();
        int key=0;
        if(rs.next()){
            key = rs.getInt(1);
        }
        
        this.CerrarUpdate();
        
        return key; // se retorna el consecutivo.
    }
    
    
    
   
    /**
     * metodo que inserta un record en la tabla de detalle de la factura.
     * 
     */
    public void INSERT_INTO_detpedcli(
            int consecutivo, double cantidad, double valor, // 1 2 3
            double descuento, double valortotal, int consecprod, // 4 5 6
            int consecped, double valiva, int poriva, // 7 8 9
            int invsn, String descrip, String referencia, // 10 11 12
            double costototal, int mesgaran, int gratis, // 13 14 15
            String serial, int ordencampo, String observacion, // 16 17 18 
            int entregado, int marcaimp, String fecha, // 19 20 21
            String mecanico, double tarneta, double impcomb, //22 23 24
            double tasaero, double taradmin, double feedeemi, // 25 26 27
            double otrosimp, double tasaembar, double trmusd, // 28 29 30
            double trmeur, double imptotimbre, double ivaagenviaj, // 31 32 33
            int color, String fechainialq, String fechafinalq, // 34 35 36
            int esotrosi, int ventainternacional, String aniomesenviofacaut, // 37 38 39
            int nrocuotaalq, int enviealertavenalq, String fechacad, // 40 41 42
            String lote, String reginvima, String cut, // 43 44 45
            String unidmedida // 46
    ) throws ClassNotFoundException, SQLException {
        
        this.SetConnection();
        this.pst = (PreparedStatement) this.link.prepareStatement(SQL_Handler.INSERTdetpedcli);
        this.pst.setInt(1, consecutivo);
        this.pst.setDouble(2, cantidad);
        this.pst.setDouble(3, valor);
        this.pst.setDouble(4, descuento);
        this.pst.setDouble(5, valortotal);
        this.pst.setInt(6, consecprod);
        this.pst.setInt(7, consecped);
        this.pst.setDouble(8, valiva);
        this.pst.setInt(9, poriva);
        this.pst.setInt(10, invsn);
        this.pst.setString(11, descrip);
        this.pst.setString(12, referencia);
        this.pst.setDouble(13, costototal);
        this.pst.setInt(14, mesgaran);
        this.pst.setInt(15, gratis);
        this.pst.setString(16, serial);
        this.pst.setInt(17, ordencampo);
        this.pst.setString(18, observacion);
        this.pst.setInt(19, entregado);
        this.pst.setInt(20, marcaimp);
        this.pst.setString(21, fecha);
        this.pst.setString(22, mecanico);
        this.pst.setDouble(23, tarneta);
        this.pst.setDouble(24, impcomb);
        this.pst.setDouble(25, tasaero);
        this.pst.setDouble(26, taradmin);
        this.pst.setDouble(27, feedeemi);
        this.pst.setDouble(28, otrosimp);
        this.pst.setDouble(29, tasaembar);
        this.pst.setDouble(30, trmusd);
        this.pst.setDouble(31, trmeur);
        this.pst.setDouble(32, imptotimbre);
        this.pst.setDouble(33, ivaagenviaj);
        this.pst.setInt(34, color);
        this.pst.setNull(35, java.sql.Types.DATE); // fechainialq
        this.pst.setNull(36, java.sql.Types.DATE); // fechafinalq
        this.pst.setInt(37, esotrosi);
        this.pst.setInt(38, ventainternacional);
        this.pst.setString(39, aniomesenviofacaut);
        this.pst.setInt(40, nrocuotaalq);
        this.pst.setInt(41, enviealertavenalq);
        this.pst.setNull(42, java.sql.Types.DATE); // fechacad
        this.pst.setString(43, lote);
        this.pst.setString(44, reginvima);
        this.pst.setString(45, cut);
        this.pst.setString(46, unidmedida);
        this.pst.executeUpdate();
        this.CerrarUpdate();
    }    
    
}
