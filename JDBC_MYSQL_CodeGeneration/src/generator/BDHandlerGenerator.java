package generator;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.sun.jdi.connect.Connector;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.lang.model.element.Modifier;

/**
 *
 * @author esteban
 * 
 * En el patron de diseño de este software se tiene para cada tabla de la bd
 * selccionada 2 clases. Una clase que sirver de conector a la tablaa respectiva
 * y otra clase que representa una fila de esa tabla.
 * 
 * para evitar redundancia, se crea una super clase para todos los conectores
 * que este programa genere. 
 * 
 * Esta clase se encarga de la generacion de dicha superclase, cuyos metodos
 * seran siempre los mismos. Sin embargo se crea porque es necesaria para
 * que el codigo generado en los conectores tenga setido.
 * 
 */
public class BDHandlerGenerator {
    
    //"jdbc:mysql://127.0.0.1:3306/negocio_r";
    
    /**
     * url para hacer conexion a la bd, normalmente es local host puerto 3306
     */
    public String url;
    /**
     * usuario para logearse en la base de datos
     */
    public String user;
    /**
     * contraseña para logearse en la base de datos.
     */
    public String pass;
    
    /**
     * atributo necesario cuando se hace la conexion una base de datos 
     * MySQL usando JDBC. Ya que estos valores no cambian, no se piden para
     * construir el codigo.
     * se ponenprivado unicamente porque solo son utiles en esta clase
     */
    private final String cfname="com.mysql.jdbc.Driver";
    
    
    
    /**
     * nombre de la super clase de todos los conectores.
     */
    public final String CLASS_NAME = "BDHandler";
    
    /**
     * VN = variable name. nombres de los atributos para la super clase handler.
     */
    public final String VN_USER = "user", VN_URL = "url", VN_PASS = "pass",
            VN_CNAME = "cname";
    
    /**
     * nombre de los atributos: Connection PreparedStatement y ResultSet
     */
    public final String VN_LINK = "link", VN_PST = "pst", VN_RS = "rs";
    
   /**
    * nombre del metodo para establecer una conexion.
    */ 
    public final String MN_SET_CONN = "SetConnection";
    /**
    * nombre del metodo para cerrar la conexion si se hizo un insert.
    */
    public final String MN_CERRAR_TODO = "SetConnection";
    /**
    * nombre del metodo para cerrar la conexion si se hizo un update
    */
    public final String MN_CERRAR_UP = "SetConnection";
    
    /**
     * constructorvacio
     * @param url : direccion del MySQL server (normalmente localhost en 3306)
     * @param user : nombre de usuario para logearse (usualmente root)
     * @param pass : contraseña para logearse.
     */
    public BDHandlerGenerator(String url, String user, String pass){
        this.url = url;
        this.user = user;
        this.pass = pass;
    }
    
    /**
     * genera el codigo de la superclase de los conectores a tablas SQL.
     * @return 
     */
    public TypeSpec GenerateBDHandSpec(){
        
        // lista de parametros de la super clase handler
        ArrayList<FieldSpec> fields = new ArrayList<>();
        fields.add(FieldSpec.builder(TypeName.get(String.class), VN_USER, Modifier.PUBLIC).build());
        fields.add(FieldSpec.builder(TypeName.get(String.class), VN_URL, Modifier.PUBLIC).build());
        fields.add(FieldSpec.builder(TypeName.get(String.class), VN_PASS, Modifier.PUBLIC).build());
        fields.add(FieldSpec.builder(TypeName.get(String.class), VN_CNAME, Modifier.PUBLIC)
                .initializer("$S", "com.mysql.jdbc.Driver")
                .build());
        
        // link pst y resultset
        fields.add(FieldSpec.builder(TypeName.get(Connector.class), VN_LINK, Modifier.PUBLIC).build());
        fields.add(FieldSpec.builder(TypeName.get(PreparedStatement.class), VN_PST, Modifier.PUBLIC).build());
        fields.add(FieldSpec.builder(TypeName.get(ResultSet.class), VN_RS, Modifier.PUBLIC).build());
        
        
        //lista de metodos de la super clase handler.
        ArrayList<MethodSpec> mts = new ArrayList<>();
        
        /**
         * metodo que inicializa el objeto link, a partir del cual se obtiene
         * el pst.
         */
        MethodSpec ms_set_connection = MethodSpec.methodBuilder(this.MN_SET_CONN)
                .addException(SQLException.class)
                .addException(ClassNotFoundException.class)
                .addStatement("Class.forName(this.cname)")
                .addStatement("this.link = $T.getConnection(url, user, pass)", DriverManager.class)
                .build();
        
        /**
         * al final de las operaciones con la base de datos se debe ejecutar
         * este metodo para cerrar los 3 objectos, link pst y resultset
         * si la operacion fue un query.
         */
        MethodSpec ms_cerrar_todo = MethodSpec.methodBuilder(this.MN_SET_CONN)
                .addException(SQLException.class)
                .addException(ClassNotFoundException.class)
                .addStatement("this.rs.close()")
                .addStatement("this.pst.close()")
                .addStatement("this.link.close()")
                .build();
        
        /**
         * si la operacion realizada fue un update, solo se deben cerrar el 
         * link y el pst, en cuyo caso se debe ejecutar este metodo en lugar
         * del cerrarTodo()
         */
        MethodSpec ms_cerrar_update = MethodSpec.methodBuilder(this.MN_SET_CONN)
                .addException(SQLException.class)
                .addException(ClassNotFoundException.class)
                .addStatement("this.pst.close()")
                .addStatement("this.link.close()")
                .build();
        
        // se meten los metodos en ArrayList para pasarlos como iterables
        mts.add(ms_set_connection);
        mts.add(ms_cerrar_todo);
        mts.add(ms_cerrar_update);
        
        // constructor para el typeSpec
        TypeSpec.Builder bdB = TypeSpec.classBuilder(this.CLASS_NAME);
        bdB.addFields(fields); // se agregan los atributos.
        bdB.addMethods(mts); // se agregan todos los metodos.
        
        // se contruye el TypeSpec con el builder usando build()
        TypeSpec bdh_tspec = bdB.build();
        return bdh_tspec;
    }
    
}
