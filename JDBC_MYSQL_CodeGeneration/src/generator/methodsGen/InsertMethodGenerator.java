
package generator.methodsGen;


import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import java.sql.SQLException;

import java.util.ArrayList;

import javax.lang.model.element.Modifier;


import generator.CodeGenerator;
import jdbc_pst_insertgenerator.Columna;

/**
 *
 * @author esteban
 *
 * Clase para generar el string  correspondiende a la generacion de codigo
 * del metodo de insersion con pst
 */
public class InsertMethodGenerator extends CodeGenerator{
    
    
    public InsertMethodGenerator(){}
    
    /**
     * metodo que hace la generacion del codigo en java.
     * @param tableName
     * @param Tab
     * @return
     */
    
    
    
    /**
     * metodo para generar el codigo de insersion de una fila para la tabla
     * specificada pero usando java poet.
     * @param tableName
     * @param TbColumns
     * @return
     */
    public String PoetMethodGen(String tableName, ArrayList<Columna> TbColumns){
        String r = ""; 
        
        String MethodName = "Insert_into_" + tableName; //  nombre del metodo
        
        // lista de parametros del metodo de insercion.
        ArrayList<ParameterSpec> Params = super.Column2ParamSpecNoMod(TbColumns);
        
        
        // se construye el metodo de insercion.
        MethodSpec Insert = MethodSpec.methodBuilder(MethodName)
                .addModifiers(Modifier.PUBLIC)
                .addParameters(Params)
                .addException(TypeName.get(ClassNotFoundException.class))
                .addException(TypeName.get(SQLException.class))
                .build();
        
        r = Insert.toString();
        
        return r;
    }
    
}
