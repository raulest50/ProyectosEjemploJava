package BD.Handlers;

import BD.BDHandler;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author esteban
 * 
 * conector a la tabla de configuracion.
 */
public class BDH_Configuracion extends BDHandler{
    
    /**
     * columna de la tabla de configuracion. llave primaria tipo String
     */
    public final String Col_idconf = "id";
    
    
    /**
     * columna de la tabal de configuracion. tipo String.
     */
    public final String Col_valor = "valor";
    
    
    /**
     * ide de la propiedad de iva por defecto.
     */
    public static final String PRT_Iva_Defecto = "iva x defecto";
    
    
    /**
     * String que especifica la ruta donde se deben guardar los
     * archivos png con el codigo de barras que se genreran  en la secciond e
     * modificacion de productos.
     */
    public static final String PRT_folder_cod128 = "ruta png 128";
    
    
    /**
     * id de la configuracion que especifica cuantos caracteres por linea 
     * caben en la factura.
     */
    public static final String PRT_Chars_por_Linea = "chars x linea";
    
    
    /**
     * id de la propiedad del nombre de la impresora de stickers en el OS.
     */
    public static final String PRT_Impresora_sticker = "Impresora_Stickers";
    
    
    /**
     * id de la propiedad del nombre de la impresora de facturas en el OS.
     */
    public static final String PRT_Impresora_factura = "Impresora_Factura";
    
    
    /**
     * id de la propiedad del nombre del String que se imprime como encabezado 
     * en la factura.
     */
    public static final String PRT_factura_head = "Factura_Head";
    
    
    /**
     * query de sql para seleccionar una fila de la tabla de configuracion.
     */
    public final String SQL_get_prt =
            "SELECT `configuracion`.`id`,\n" +
            "    `configuracion`.`valor`\n" +
            "FROM `granero`.`configuracion` where granero.configuracion.id = ?;";
    
    /**
     * query para actualizar un valor de la tabla de configuracion.
     */
    public final String SQL_update_prt =
            "UPDATE `granero`.`configuracion`\n" +
            "SET\n" +
            "`valor` = ?\n" +
            "WHERE `id` = ?;";
    
    
    /**
     * metodo para leer de la tabla de configuracion una propiedad.
     * @param idconf
     * @return 
     */
    public String GetConfiguracion(String idconf) 
            throws ClassNotFoundException, SQLException{
        String r="";
        super.link = super.GetConnectionGranero();
        super.pst = (PreparedStatement) super.link.prepareStatement(this.SQL_get_prt);
        super.pst.setString(1, idconf);
        super.rs = super.pst.executeQuery();
        while(super.rs.next()){
            r = rs.getString(this.Col_valor);
        }
        return r;
    }
    
    
    /**
     * metodo para modificar el valor de la configuracion especificada por
     * idconf. recordar que la llave primaria de la tabla de configuracion es
     * varchar.
     * @param idconf
     */
    public void UpdateConfiguracion(String idconf, String neuVal)
            throws ClassNotFoundException, SQLException{
        super.link = super.GetConnectionGranero();
        super.pst = (PreparedStatement) super.link.prepareStatement(this.SQL_update_prt);
        super.pst.setString(1, neuVal);
        super.pst.setString(2, idconf);
        super.pst.executeUpdate();
        super.CerrarUpdate();
    }
    
}


/*
se debe crear la tabla de configuracion.

CREATE TABLE `configuracion` (
  `id` varchar(100) NOT NULL,
  `valor` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='tabla en la que se guardan las configuraciones de la aplicacion.';


se deben ingresar todas las propiedades String static que estan en esta clase en la
tabla de configuracion.


INSERT INTO `granero`.`configuracion`
(`id`,
`valor`)
VALUES
("Impresora_Factura",
"");


*/