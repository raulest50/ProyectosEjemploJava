/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbf2sql_ng;

/**
 *
 * @author esteban
 */
public class SQL_Handler {
    
    
    /**
     * query para insertar un cliente en H&L software
     */
    public static String Insert_Cli = "INSERT INTO `fashion`.`clientes`\n" +
            "(`consecutivo`, `tipide`,`numide`,\n" + // 1 2 3
            "`razonsocial`, `direccion`, `codciu`,\n" + // 4 5 6
            "`telefono`, `correoelectronico`, `fechaing`,\n" + // 7 8 9
            "`codtipovalor`, `celular`, `fechanac`,\n" + // 10 11 12
            "`genero`, `unidadresidencial`, `apartamento`,\n" + // 13 14 15
            "`contacto`, `rtfuente`, `rtiva`,\n" + // 16 17 18
            "`rtica`, `precio`, `plazo`,\n" + // 19 20 21
            "`desciudad`, `idcliente`, `cupo`,\n" + //22 23 24
            "`usuarioven`, `comisionporcen`, `fechaenvcorr`,\n" + // 25 26 27
            "`diasalertacartera`, `esexento`, `esneto`,\n" + // 28 29 30
            "`diascredito`, `tipo`, `esautoretenedor`,\n" + // 31 32 33
            "`esdeclarante`, `omitetextocredfac`, `esretenedor`,\n" + // 34 35 36
            "`esproveedor`, `observacion`, `factoremialqui`,\n" + // 37 38 39
            "`clasecliente`)\n" + //  40
            "VALUES\n" +
            "(?, ?, ?,\n" + 
            "?, ?, ?,\n" + 
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?);";
    
    
    /**
     * query para seleccionar el nit de un cliente deacuerdo a su nombre.
     */
    public static String Select_Nit = "select numide, consecutivo from clientes where razonsocial=?";
    
    
    
    
    
    public static String INSERTpedcli = "INSERT INTO `fashion`.`pedcli`\n" +
            "(`consecutivo`, `numfac`, `fechagen`,\n" + // 1 2 3 
            "`rangoinicial`, `rangofinal`, `fechaaut`,\n" + // 4 5 6 
            "`usuariogen`, `resolucion`, `consecven`,\n" + // 7 8 9 
            "`conseccli`, `conseccede`, `valfac`,\n" + //  10 11 12 
            "`estadope`, `fechagenhora`, `fechacad`,\n" + // 13 14 15 
            "`estadofac`, `valpag`, `causaanulacion`,\n" + // 16 17 18
            "`originalcopia`, `estadopago`, `usuarioanu`,\n" + // 19 20 21
            "`fechaanu`, `iva`, `tipoventa`,\n" + // 22 23 24 
            "`impresa`, `pagocont`, `fecremi`,\n" + // 25 26 27
            "`numremi`, `porcendesc`, `retefuente`,\n" + // 28 29 30
            "`reteiva`, `reteica`, `reteotros`,\n" + // 31 32 33 
            "`prefijo`, `obra`, `observacion`,\n" + // 34 35 36 
            "`abierta`, `codtipocomision`, `comisionporcen`,\n" + // 37 38 39
            "`comisionvalor`, `consecdirecc`, `descuento`,\n" + // 40 41 42
            "`estado`, `notaoculta`, `chulopagocomis`,\n" + // 43 44 45
            "`cierreparcial`, `cuenta`, `placa`,\n" + // 46 47 48
            "`marca`, `nroordenserv`, `consecaerolinea`,\n" + // 49 50 51
            "`destinoviaje`, `esdomicilio`, `encardomicilio`,\n" + // 52 53 54
            "`fechapagocomis`, `fechaprogramacion`, `correoenvioalqui`, \n" + // 55 56 57
            "`tipopago`, `porcendcto`)\n" + // 58 59
            "VALUES\n" +
            "(\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?, \n" +
            "?, ?);";
    
    
    /**
     * query apra insertar un elemento en la tabla de encabezado de la factura
     * detpedcli.
     */
    public static String INSERTdetpedcli = "INSERT INTO `fashion`.`detpedcli`\n" +
            "(`consecutivo`, `cantidad`, `valor`,\n" + // 1 2 3 
            "`descuento`, `valortotal`, `consecprod`,\n" + // 4 5 6 
            "`consecped`, `valiva`, `poriva`,\n" + // 7 8 9 
            "`invsn`, `descrip`, `referencia`,\n" + // 10 11 12
            "`costototal`, `mesgaran`, `gratis`,\n" + // 13 14 15 
            "`serial`, `ordencampo`, `observacion`,\n" + // 16 17 18
            "`entregado`, `marcaimp`, `fecha`,\n" + // 19 20 21
            "`mecanico`, `tarneta`, `impcomb`,\n" + // 22 23 24
            "`tasaero`, `taradmin`, `feedeemi`,\n" + // 25 26 27
            "`otrosimp`, `tasaembar`, `trmusd`,\n" + // 28 29 30
            "`trmeur`, `imptotimbre`, `ivaagenviaj`,\n" + // 31 32 33
            "`color`, `fechainialq`, `fechafinalq`,\n" + // 34 35 36
            "`esotrosi`, `ventainternacional`, `aniomesenviofacaut`,\n" + // 37 38 39
            "`nrocuotaalq`, `enviealertavenalq`, `fechacad`,\n" + // 40 41 42
            "`lote`, `reginvima`, `cut`,\n" + // 43 44 45
            "`unidmedida`)\n" + // 46
            "VALUES(\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?);";
    
    /**
     * query de sql para insertar un producto.
     * en bd fashion.
     */
    public static String INSERTproducto=
            "INSERT INTO `fashion`.`producto`\n" +
            "(\n" +
            "`consecutivo`, `codpro`, `descripcion`,\n" + // 1 2 3
            "`valornormal`, `cantidad`, `valorsubtotal`,\n" + // 4 5 6 
            "`codcat`, `imagen`, `minimo`,\n" + // 7 8 9 
            "`conproser`, `conseciva`, `inventsn`,\n" + // 10 11 12  
            "`snreferencia`, `mesgaran`, `valornormal2`,\n" + // 13 14 15 
            "`valornormal3`, `activo`, `cantidadtempo`,\n" + // 16 17 18 
            "`codtipoprod`, `conseccli`, `adjunto1`,\n" + // 19 20 21 
            "`adjunto2`, `fecha`, `usuario`,\n" + // 22 23 24
            "`valornormal4`, `descategoria`, `tipomedida`,\n" + // 25 26 27
            "`subcategoria`, `empaque`, `marca`,\n" + // 28 29 30
            "`unidadempaque`, `equivalentegalon`, `cupo`,\n" + // 31 32 33 
            "`conteo1`, `conteo2`, `conteo3`,\n" + // 34 35 36
            "`costoinicial`, `porcenincre`, `cantinterna`,\n" + // 37 38 39
            "`color`, `consecprodgenerico`, `ubicacion`,\n" + // 40 41 42
            "`descempaque`, `ean`, `plu`,\n" + // 43 44 45 
            "`cantnormal`, `cantpedida`, `cantdisponible`,\n" + // 46 47 48
            "`reginvima`)\n" + // 49
            "VALUES(\n" +
            "?, ?, ?, \n" +
            "?, ?, ?, \n" +
            "?, ?, ?, \n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?, ?, ?,\n" +
            "?);";
    
    
    
    
    
}
