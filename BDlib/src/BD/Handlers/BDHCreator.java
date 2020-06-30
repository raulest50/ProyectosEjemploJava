package BD.Handlers;

import BD.BDHandler;
import java.sql.SQLException;

/**
 *
 * @author esteban
 * Cuando se corre el programa por primera vez aun no existen ninguna de 
 * las tablas para el ingreso de productos por lo que es necesario crearlas.
 * 
 * esta lase se encarg de inicializar el servidor de mysql con todas las
 * bases de datos y tablas necesarias para que funcione el software.
 */
public class BDHCreator extends BDHandler{
    
    
    public String crear_tabla_ventas = "CREATE TABLE `ventas` (\n" +
            "  `id` int(15) NOT NULL AUTO_INCREMENT,\n" +
            "  `idconjunto` int(15) DEFAULT NULL,\n" +
            "  `codigo` varchar(20) COLLATE utf8_spanish_ci DEFAULT NULL,\n" +
            "  `N` int(11) DEFAULT NULL,\n" +
            "  `time` datetime DEFAULT NULL,\n" +
            "  `cliente` int(11) DEFAULT NULL,\n" +
            "  `tipoventa` int(11) DEFAULT NULL,\n" +
            "  `descripcion` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,\n" +
            "  `pvpublico` decimal(19,4) DEFAULT '0.0000',\n" +
            "  `pvtienda` decimal(19,4) DEFAULT '0.0000',\n" +
            "  `costo` decimal(19,4) DEFAULT '0.0000',\n" +
            "  `iva` decimal(19,4) DEFAULT '0.0000',\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=1572 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;",
            
            crear_tabla_productos="CREATE TABLE if not exists `productos` (\n" +
            "  `codigo` varchar(20) NOT NULL,\n" +
            "  `descripcion` varchar(50) DEFAULT NULL,\n" +
            "  `pvpublico` decimal(19,4) NOT NULL DEFAULT '0.0000',\n" +
            "  `pvtienda` decimal(19,4) NOT NULL DEFAULT '0.0000',\n" +
            "  `costo` decimal(19,4) NOT NULL DEFAULT '0.0000',\n" +
            "  `iva` decimal(19,4) NOT NULL DEFAULT '0.0000',\n" +
            "  `ultima_actualizacion` datetime DEFAULT NULL,\n" +
            "  `Familia` varchar(30) DEFAULT NULL,\n"
            + "`stock` int(11) DEFAULT '0',\n" +
            "  PRIMARY KEY (`codigo`)\n" +
            ") ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;",
            
            crear_tabla_familias="CREATE TABLE if not exists `familias` (\n" +
            "  `id` varchar(150) NOT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;",
            
            crear_tabla_propiedades="CREATE TABLE if not exists `propiedades` (\n" +
            "  `propiedad` varchar(70) NOT NULL,\n" +
            "  `estado` varchar(20) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`propiedad`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;",
            
            crear_tabla_proveedores="CREATE TABLE if not exists `proveedores` (\n" +
            "  `Codigo` varchar(20) NOT NULL,\n" +
            "  `direccion` varchar(100) DEFAULT NULL,\n" +
            "  `vendedor` varchar(100) DEFAULT NULL,\n" +
            "  `telefonos` varchar(150) NOT NULL,\n" +
            "  `email` varchar(100) DEFAULT NULL,\n" +
            "  `web` varchar(100) DEFAULT NULL,\n" +
            "  `calificacion` int(11) DEFAULT NULL,\n" +
            "  `fecha` datetime DEFAULT NULL,\n" +
            "  `nombre` varchar(70) NOT NULL,\n" +
            "  `fax` varchar(20) DEFAULT NULL,\n" +
            "  `keywords` varchar(500) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`Codigo`)\n" +
            ") ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;",
            crear_tabla_relaciones = "CREATE TABLE if not exists `granero`.`relaciones` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `CodGrupo` VARCHAR(47) NOT NULL,\n" +
            "  `p1` VARCHAR(47) NOT NULL,\n" +
            "  `p2` VARCHAR(47) NOT NULL,\n" +
            "  PRIMARY KEY (`id`))\n" +
            "COMMENT = 'En esta tabla se guardan las relaciones de igualdad de precio entre productos. "
            + "Gracias a esta tabla es posible configurar modificaciones automaticas a los "
            + "productos de un grupo con solo cambiar uno de ellos. Esto es partucularmente "
            + "util cuando un producto biene en diferenes aromas o sabores pero eso no afecta "
            + "el precio. Cuando hay incrementos en el valor del producto, este incremento ocurre "
            + "por igual a todos los sabores pero a la hora de modificar el valor del precio basta "
            + "con cambiar uno solo de ellos si ya se configuro esta relacion de igualdad en la "
            + "presente tabla.';",
            
            crear_db_granero = "create database if not exists ´granero´;",
            
            crear_bd_invent = "create database if not exists ´inventarios´",
            insert_property1 = "INSERT ignore INTO `granero`.`propiedades` (`propiedad`, `estado`) VALUES('Inventario', 'false')",
            insert_property2 = "INSERT ignore INTO `granero`.`propiedades` (`propiedad`, `estado`) VALUES('InventarioNombre', 'x')";
    
            // ignore en mysql significa que si el dato ya existe en la tabla entonces se ignora el comando
    
    BDH_Inventarios bdhi = new BDH_Inventarios();
    
    public void Crear_Todas_Las_tablas(){
        eje(crear_db_granero);
        eje(crear_bd_invent);
        eje(crear_tabla_ventas);
        eje(crear_tabla_productos);
        eje(crear_tabla_familias);
        eje(crear_tabla_propiedades);
        eje(crear_tabla_proveedores);
        eje(insert_property1);
        eje(insert_property2);
        eje(crear_tabla_relaciones);
    }
    
    public void eje(String q){
        try{
            FormPstGranero(q);
            pst.executeUpdate();
            CerrarUpdate();
        }
        catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
}
