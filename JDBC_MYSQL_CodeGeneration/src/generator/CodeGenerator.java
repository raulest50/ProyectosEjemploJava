/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import java.util.ArrayList;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.lang.model.element.Modifier;
import jdbc_pst_insertgenerator.Columna;


/**
 *
 * @author esteban
 * 
 * La primera version de este programa se hizo para disponer de una herramienta
 * de generacion rapida de inserts con prepared statement. Se debia llevar a 
 * cabo un codigo en java que insertara desde un archivo de excel la base de 
 * datos de new genration (informacion de cartera principalmente). los inserts 
 * para la base de datos del nuevo software eral de alrrededor de 50 variables
 * para cada caso. 
 * 
 * En este remaake del proyecto, ya se hace una adaptacion para un caso mas general
 * que incluya mayor numero de statements en mysql e incluso la generacion
 * de la clase completa de conectores (como clases de java) y no solo los metodos
 * agilizando enormenente el desarrollo de aplicaciones basadas en java y mysql.
 * 
 * Ya que hay metodos comunes a las accines de insertar y select, se crea
 * esta superclase para la reutilizacion de variables y demas.
 */
public class CodeGenerator {
    
    
    /**
     * inicion de la declaracion del metodo.
     */
    public final String INIT_DELCR = "public void ";
    
    /**
     * String con la clausula throws, que debe ir en  la declaracion del metodo
     */
    public final String THROWS_ClAUSE = ") throws ClassNotFoundException, SQLException { \n\n";
    
    /**
     * String con las lineas de codigo corrspondientes para establecer la 
     * conexion de sql y construir el prepared statement.
     */
    public final String CONN_P_PST_BUILD = "this.SetConnection();\n" +
            "this.pst = (PreparedStatement) this.link.prepareStatement(SQL_Handler.";
    
    public final CodeBlock SET_CONN_N_PST_BUILD = CodeBlock.builder()
            .add("this.SetConnection()")
            .build();
            
    
    
    // tipos de datos para java poet.
    public static TypeName Tp_Int = TypeName.get(int.class);
    public static TypeName Tp_String = TypeName.get(String.class);
    public static TypeName Tp_Double = TypeName.get(double.class);
    
    public static TypeName Tp_Spt_Int = TypeName.get(SimpleIntegerProperty.class);
    public static TypeName Tp_Spt_String = TypeName.get(SimpleStringProperty.class);
    public static TypeName Tp_Spt_Double = TypeName.get(SimpleDoubleProperty.class);
    
    
    
    //constructor vacio.
    public CodeGenerator(){
        
    }
    
    /**
     * metodo que identifica el tipo de variable java
     * deacuerdo al tipo de variable de SQL (en formato String).
     * @param col
     * @return 
     */
    public TypeName getTypeName(Columna col){
        TypeName r = TypeName.get(int.class);
        if(col.Type.contains("int")) r = Tp_Int;
        if(col.Type.contains("varchar") ||
                col.Type.contains("text")) r = Tp_String;
        if(col.Type.contains("date")) r = Tp_String;
        if(col.Type.contains("double") ||
                col.Type.contains("decimal")) r = Tp_Double;
        return r;
    }
    
    
    /**
     * metodo que a partir de la columna retorna el tipo de dato pero 
     * para el caso de simple 'var' property que es el tipo de variable
     * requerido por las tablas de javaFX.
     * @param col
     * @return 
     */
    public TypeName getSptTypeName(Columna col){
        TypeName r = TypeName.get(int.class);
        if(col.Type.contains("int")) r = Tp_Spt_Int;
        
        if(col.Type.contains("varchar") || 
                col.Type.contains("text")) r = Tp_Spt_String;
        
        if(col.Type.contains("date")) r = Tp_Spt_String;
        
        if(col.Type.contains("double") || 
                col.Type.contains("decimal")) r = Tp_Spt_Double;
        
        return r;
    }
    
    
    /**
     * metodo que transforma una lista de columnas de una tabla en parameters
     * Spec para ser usados con javapoet. Por ejemplo para la creacion de un
     * metodo es necesario pasarle la lista de parametros como un iteraable, 
     * ArrayList implementa la interfaz iterable por ejemplo.
     * NoMod al final del nombre de este metodo indica que los parameters spec
     * se construyen sin ningun tipo de modificador.
     * @param TbCols
     * @return 
     */
    public ArrayList<ParameterSpec> Column2ParamSpecNoMod(ArrayList<Columna> TbCols){
        ArrayList<ParameterSpec> ps = new ArrayList<>();
        
        TbCols.forEach((col) -> {
            ps.add(ParameterSpec.builder(getTypeName(col), col.Field).build());
        });
        
        return ps;
    }
    
    /**
     * Metodo que construye el bloque de codigo con todos los statements para
     * hacer set de los parametros del prepared statement. el numero de statements
     * pude variar en funcion del numero de parametros y eso fue lo que motivo
     * la creacion de este metodo.
     * @param ps
     * @return 
     */
    public CodeBlock PstInsertCodeBlock(ArrayList<ParameterSpec> ps){
        CodeBlock.Builder cb = CodeBlock.builder();
        ps.forEach((p) -> {
            cb.addStatement("");
        });
        return cb.build();
    }
    
    
    /**
     * crea un costructor vacio
     * @return 
     */
    public MethodSpec EmptyConnstructor(){
        MethodSpec mt = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .build();
        return mt;
    }
    
    /**
     * crea un costructor que asigna los parametros nada mas.
     * @param params
     * @return 
     */
    public MethodSpec DefaultConnstructor(ArrayList<ParameterSpec> params){
        MethodSpec.Builder mtb = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameters(params);
        for(ParameterSpec p : params){
            mtb.addStatement("this.$N = $N" , p.name, p.name);
        }
        
        return mtb.build();
    }
    
    
    /**
     * crea un costructor que asigna los parametros nada mas.
     * @param cols
     * @param params
     * @return 
     */
    public MethodSpec SptConnstructor(ArrayList<Columna> cols, ArrayList<ParameterSpec> params){
        ArrayList<ParameterSpec> p2 = this.Column2ParamSpecNoMod(cols);
        MethodSpec.Builder mtb = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameters(p2);
        for(ParameterSpec p : params){
            mtb.addStatement("this.$N = new $T($N)", p.name, p.type);
        }
        
        return mtb.build();
    }
    
    public ArrayList<ParameterSpec> Field2Params(ArrayList<FieldSpec> fields){
        ArrayList<ParameterSpec> params = new ArrayList<>();
        for(FieldSpec f : fields){
            params.add(ParameterSpec.builder(f.type, f.name).build());
        }
        return params;
    }
    
    public ArrayList<FieldSpec> Params2Fields(ArrayList<ParameterSpec> params){
        ArrayList<FieldSpec> fields = new ArrayList<>();
        for(ParameterSpec p : params){
            fields.add(FieldSpec.builder(p.type, p.name).build());
        }
        return fields;
    }
    
    
}
