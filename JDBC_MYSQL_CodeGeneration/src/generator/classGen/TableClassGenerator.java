
package generator.classGen;


import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import generator.CodeGenerator;

import java.util.ArrayList;
import javax.lang.model.element.Modifier;
import jdbc_pst_insertgenerator.Columna;

/**
 *
 * @author esteban
 * 
 * genrador de codigo del conector java para una tabla dada de SQL y
 * el codigo del objeto que representa una fila de la tabla.
 * 
 * Es deci que esta clase sirve para generar 2 clases java.
 * 
 * En el modelo que estoy implementando, hay una clase con los metodos de
 * insercion, seleccion actualizacion etc para una tabla dada y otra clase
 * cuyos atributos son las columnas de la tabla, es decir un objeto que pueda 
 * representar una fila de la tabla.
 */
public class TableClassGenerator extends CodeGenerator{
    
    /**
     * columnas de la tabla a la que se desea hacer el conector.
     */
    public ArrayList<Columna> cols;
    
    /**
     * nombre de la tabla.
     */
    public String TableName;
    
    
    /**
     * este metodo es que debe usarse para
     * @param TableName
     * @param cols
     * @return 
     */
    public TypeSpec GetObjTpSpec(String TableName, ArrayList<Columna> cols){
        ArrayList<FieldSpec> fields = this.BuildObjFields(cols);
        ArrayList<MethodSpec> mts = this.BuildObjMethods(cols, fields);
        return this.BuildObjTpSpec(TableName, mts, fields);
    }
    
    
    /**
     * metodo que genera el objeto que representa una fila de la tabla, usando
     * la libreria javaPoet.En esta libreria una clase esta representada por
     * la clase de javapoet, typeSpec (recordar que una clase java es a la vez
     * un tipo de dato).
     * @param mts  : lista de metodos de la clase.
     * @param fields
     * @return
     */
    private TypeSpec BuildObjTpSpec(String tableName, ArrayList<MethodSpec> mts, ArrayList<FieldSpec> fields){
        TypeSpec obj = TypeSpec.classBuilder("Spt_" + tableName)
                .addModifiers(Modifier.PUBLIC)
                .addMethods(mts)
                .addFields(fields)
                .build();
        return obj;
    }
    
    /**
     * metodo que forma los field spec para la clase tipo obj para una tabla
     * dada
     * @param cols
     * @return 
     */
    private ArrayList<FieldSpec> BuildObjFields(ArrayList<Columna> cols){
        ArrayList<FieldSpec> fields = new ArrayList<>();
        TypeName auxtn;
        for(Columna col : cols){
            auxtn = super.getSptTypeName(col);
            fields.add(FieldSpec.builder(auxtn, this.getSptVarName(col), Modifier.PUBLIC).build());
        }
        return fields;
    }
    
    
    /**
     * genera los metodos para la clase tipo obj
     * @param cols
     * @return 
     */
    private ArrayList<MethodSpec> BuildObjMethods(ArrayList<Columna> cols, ArrayList<FieldSpec> fields){
        ArrayList<MethodSpec> mts = new ArrayList<>();
        // se agregan los metodos get.
        for(Columna col : cols){
            mts.add(this.GenerateObjGetmethod(col));
        }
        // se agrega el constructor
        mts.add(super.EmptyConnstructor());
        mts.add(super.DefaultConnstructor(this.Field2Params(fields)));
        mts.add(super.SptConnstructor(cols, this.Field2Params(fields)));
        return mts;
    }
    
    /**
     * para el caso de los obj, se usa simple string property para hacerlo 
     * compatible con javaFX. Sin embargo esto requiere tambien que los get y los
     * set tenga cierta extructura y su presencia es obligatoria, por tanto
     * este generador de metodo get solo es compatible para un tipo obj no para
     * una clase conector.
     * @return 
     */
    private MethodSpec GenerateObjGetmethod(Columna col){
        MethodSpec.Builder mtb = MethodSpec.methodBuilder("Get" + this.getSptVarName(col));
        mtb.addStatement("return this.$N.get()", this.getSptVarName(col));
        mtb.addModifiers(Modifier.PUBLIC);
        mtb.returns(super.getTypeName(col));
        return mtb.build();
    }
    
    /**
     * este metodo puede parecer verbose pero se hizo para minimizar la 
     * posibilidad de errores en el codigo por errores en la digitacion.
     * @param col
     * @return 
     */
    private String getSptVarName(Columna col){
        return "Spt_" + col.Field;
    }
    
    
    /**
     * genera el codigo de la clase java para hacer las operaciones SQL desde
     * java, es decir, el conector a la tabla SQL.
     * @param mts
     * @return 
     */
    private TypeSpec GetHandlerTpSpec(ArrayList<MethodSpec> mts) 
            throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
//        TypeSpec obj = TypeSpec.classBuilder("BDH_" + this.TableName)
//                .addModifiers(Modifier.PUBLIC)
//                .addMethods(mts)
//                .build();
//        return obj;
    }
    
}
