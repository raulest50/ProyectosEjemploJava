
package jdbc_pst_insertgenerator;

/**
 *
 * @author esteban
 */
public class Columna {
    
    /**
         * nombre de la columna
         */
        public String Field;
        /**
         * tipo de dato almacenado en la columna
         */
        public String Type;
        /**
         * true or false si acepta o no valores nulos en esta columna
         */
        public String Null;
        /**
         * indica si es o no llave primaria
         */
        public String Key;
        /**
         * valor para insertar por defecto en esta columna
         */
        public String Default;
        /**
         * indica informacion adicional de la columna, por ejemplo si aplica
         * o no autoincremento.
         */
        public String Extra;

        public Columna(String Field, String Type, String Null, String Key, String Default, String Extra) {
            this.Field = Field;
            this.Type = Type;
            this.Null = Null;
            this.Key = Key;
            this.Default = Default;
            this.Extra = Extra;
        }
    
}
