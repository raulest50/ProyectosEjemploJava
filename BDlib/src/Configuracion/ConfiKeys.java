/**
 * 
 */
package Configuracion;

/**
 *
 * @author esteban
 * 
 * En este archivo se guardan los String keys que se usan para 
 * las preferencias de java de esta aplicacion
 * 
 * Este archivo solo se usa como una forma de ordenaar mas el codigo y las keys
 * pero tecnicamente no es necesario.
 */
public class ConfiKeys {
    
    
    /**
     * valor por defecto para el iva en la aplicacion
     */
    public static String keyIva = "va_D_iva";
    
    /**directorio por defecto para guardar los png de los codigos de barras
     *generados en la pestaña de modificar productos.
     */
    public static String keyDirCod = "va_directorio_defecto_Codigo";
    
    /**
     * nombre de la impresora de facturas en el OS
     */
    public static String keyImpresoraFacturas = "va_impr_factu_";
    
    /**
     * nombre de la impresora de adhesivos en el OS
     */
    public static String keyImpresoraStickers = "va_impr_stk";
    
    /**
     * encabezado de la factura 
     */
    public static String keyEncabezadoFactura = "va_factu_head_";
    
    /**
     * Numero de caracteres por linea que caben en la factura.
     */
    public static String key_charnum = "va_num_caracteres_rollp";
    
    /**
     * Numero de licencia obtenido de la funcion psi.
     */
    public static String key_liz = "va_llave_li";
    
    
    /**
     * se retorna cuando no hay nada guardado para cualquier key.
     */
    public static String nulo = "default";
    
    
    
}
