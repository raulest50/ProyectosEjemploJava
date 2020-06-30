package dbf2sql_ng;

import dbf2sql_ng.BD_Handler.MyCli;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


/**
 *
 * @author esteban
 */
public class FXMLDocMainController {
    
    
    /**
     * campo de texto para ingresar el usurario de la base de datos
     * de mySQL
     */
    @FXML
    private TextField TF_user;

    /**
     * contraseña de usuario para base de datos mysql
     */
    @FXML
    private TextField TF_pass;

    
    /**
     * Campo de texto para ingresar la direccion o ruta del archivo de
     * excel que contiene la informacion de los clientes.
     */
    @FXML
    private TextField TF_clientes;

    
    /**
     * Campo de texto para ingresar la direccion o ruta del archivo de
     * excel que contiene la informacion con la cartera de los clientes
     */
    @FXML
    private TextField TF_cartera;

    
    /**
     * boton para cargar la informacion de los clientes.
     */
    @FXML
    private Button B_clientes;

    
    /**
     * boton para cargar la informacion de cartera en la base de datos de H&L
     */
    @FXML
    private Button B_cartera;
    
    
    /**
     * codigo del producto ficticio de cartera.
     */
    public final String COD_CARTERA = "cart0001";
    
    
    public final int consecutivo_prod = 1;
    
    /**
     * descripcion para el producto ficticio para ingresar la cartera.
     */
    public final String NOMBRE_CARTERA = "Cartera";
    
    
    /**
     * variable que indica el inicio de un saldo el sheet de excel
     */
    public static String SALDO_MARK = "CLIENTE: ";
    
    // para el insert de la tabla de clientes.
    public final int NIT=0, NOMBRE=1, TEL=11, DIRECCION=12;
    
    
    // para el insert de la tabla de consolidado cartera
    public final int FACTURA_COLNUM=7, ABONO_COLNUM=8, SALDO_COLNUM=9;
    
    
    /**
     * se calcula la fecha actual para su uso en el software 
     * no es realmente importante pero permite 
     * sabaer la fecha que se cargo la cartera al programa de HyL software.
     */
    public static LocalDate hoy = LocalDate.now();
    
    
    /**
     * fecha actual, al igual que la anterior se usa sol como refrencia 
     * pero no es necesaria. la unica diferencia respecto a hoy es 
     * que esta contiene un valor de hora para el caso de datatypes de sql
     * datetime, miestras que hoy se aplica cuando son soilo hoy.
     */
    public static LocalDateTime hoy_time = LocalDateTime.now();

    /**
     * metodo que se ejecuta cuando se hace click en el boton de cargar cartera.
     * 
     * se leen los records del archivo de excel correspondientes
     * y se hacen inserts en la
     * BD de H&L.
     * @param event 
     */
    @FXML
    void onClick_cartera(MouseEvent event) {
        try{
            // se abre el archivo de excel
            String Cartera_dir = this.TF_cartera.getText();
            
            // instacia de un archivo excel en formato workbook
            // no puede ser .xlsx, debe ser workbook .xls (98-2003)
            Workbook workbook = null;
            // construye el objeto usando la ruta donde esta ubicado el excel
            workbook = Workbook.getWorkbook(new File(Cartera_dir));
            // se leen las credenciales para acceder a la base de datos
            String user = this.TF_user.getText();
            String pass = this.TF_pass.getText();
            // se instancia el conector a la base de datos
            BD_Handler bdh = new BD_Handler(user, pass); // se inicializa conector a la BD
            Sheet sheet = workbook.getSheet(0); // se insancia la hoja 0 del archivo de excel
            int num_saldos = this.CountItemsCartera(sheet); // se cuenta el numero de saldos
            
            int cur_index = 0; // indice actual del barrido
            
            // ganar
            String aux_name = ""; // nombre correspondiete
            ArrayList<MyCli> r_cli = new ArrayList<>(); // arreglo para guardar los nit de cada cliente
            int aux_saldo = 0; // variable auxiliar para guardar el saldo de cada cliente en cada iteracion
            
            
            // consecutivo autoincremental asignado al producto ficticio cartera.
            //int consec_cartera=0;
            
            
            // previo a insertar la cartera es necesario insertar unproducto
            // ficticio en la tabla de productos, al cual se le llamara cartera
            // el cual es enecesario para insertar la cartera.
            
            
            
            // proceso de insercion de la cartera >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            
            // se intenta insertar num_saldos veces en la bd de HyL soft
            // es decir cada record de cartera consolidado.
            for(int k=0; k<=num_saldos-1; k++){
                try{
                    cur_index = this.getNext(sheet, cur_index); // siguiente saldo
                    aux_name = sheet.getCell(0, cur_index).getContents().replace(FXMLDocMainController.SALDO_MARK, "").trim();
                    r_cli = bdh.GetNit(aux_name);
                    
                    if(r_cli.isEmpty()){
                        System.out.println(" No se encontro ningun nit asociado al nombre en->");
                        System.out.println(" --- index: " + Integer.toString(cur_index) + "     (resolver manualmente)");
                    }
                    if(r_cli.size() > 1){
                        System.out.println("hay mas de un nit para el nombre asociado en->");
                        System.out.println(" --- index: " + Integer.toString(cur_index) + "     (resolver manualmente)");
                    } // si hay correpsondencia uno a uno entonces se procede a inyectar a la bd HyL
                    if(r_cli.size() == 1){
                        // se obtinene el saldo de el cliente correspondiente a esta iteracion
                        aux_saldo = Integer.parseInt(sheet.getCell(this.SALDO_COLNUM, this.getNetoRow(sheet, cur_index)).getContents().replace(".", ""));
                        
                        
                        int consecutivo_pedcli = 0;
                        
                        consecutivo_pedcli = bdh.INSERT_INTO_pedcli(
                                0, 500007131, hoy.toString(), // 1 2 3
                                1, 7, hoy.toString(), // 4 5 6
                                "full", "", 2, // 7 8 9   consecven=> consecutivo tabla vendedores
                                r_cli.get(0).consecutivo, 3, aux_saldo, // 10 11 12  conceccli => consecutivo en tab clientes  , consecsede => conscutivo tabla de sedes, valor factura
                                1642, hoy_time.toString(), hoy.toString(), // 13 14 15
                                1633, 0, "", // 16 17 18
                                1646, 1639, "", // 19 20 21
                                "", 0, 1, // 22 23 24  tipo venta debe ser 1, 
                                0, 0, hoy.toString(), // 25 26 27  impresa debe ser 0, pago cont debe ser 0.
                                500007132, 0, 0, // 28 29 30 todas las retes en 0.
                                0, 0, 0, // 31 32 33 // de 30 a 33 rete s.
                                "", "", "Insertado Por Script - cartera", // 34 35 36  
                                0, 0, 0, // 37 38 39  abierta debe ser 0, 
                                0, 0, 0, // 40 41 42
                                0, "", 0, 0, // 43 44 45
                                "", "", 0, // 46 47 48
                                "", 0, "", // 49 50 51
                                0, 0, "", // 52 53 54
                                "", "", "", // 55 56 57
                                0); // 58
                        
                        bdh.INSERT_INTO_detpedcli(
                                0, 1, aux_saldo, // 1 2 3
                                0, aux_saldo, this.consecutivo_prod, // 4 5 6 => consecutivo enlazando al producto
                                consecutivo_pedcli, 0, 0, // 7 8 9  => consecutivo enlazando a pedcli
                                1, "Cartera", this.COD_CARTERA, // 10 11 12
                                0, 0, 0, // 13 14 15
                                "", 1, "Insertado por Script - Cartera", // 16 17 18
                                0, 0, hoy.toString(), // 19 20 21
                                "", 0, 0, // 22 23 24
                                0, 0, 0, // 25 26 27
                                0, 0, 0, // 28 29 30
                                0, 0, 0, // 31 32 33
                                0, "", "", // 34 35 36
                                0, 0, "", // 37 38 39
                                0, 0, "", // 40 41 42
                                "", "", "", // 43 44 45
                                ""); // 46
                        
                        
                        //aux_saldo = Integer.parseInt(sheet.getCell(this.SALDO_COLNUM, this.getNetoRow(sheet, cur_index)).getContents().replace(".", ""));
                        //System.out.println(r_nit.get(0) + "  :::::  " + aux_name + "  :::::  " + aux_saldo);
                    }
                    
                } catch(SQLException | ClassNotFoundException ex){
                    //Logger.getLogger(FXMLDocMainController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(" **** Ha ocurrido una excepcion SQL : " + ex.getMessage());
                    System.out.println(" --- index: " + Integer.toString(cur_index+1));
                } catch(NumberFormatException ex ){
                    System.out.println(" **** Ha ocurrido una excepcion NumberFormat : " + ex.getMessage());
                    System.out.println(" --- index: " + Integer.toString(cur_index+1));
                }
            }
            
        } catch (IOException | BiffException ex) {
            Logger.getLogger(FXMLDocMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    /**
     * Metodo que se ejcuta cuando se hace click en el boton de cargar clientes.
     * 
     * se leen los records del archivo de excel correspondiente y se inserta en
     * la BD de H&L.
     * @param event 
     */
    @FXML
    void onClick_clientes(MouseEvent event) {
        try{
            // se abre el archivo de exel
            String Client_dir =this.TF_clientes.getText();
            Workbook workbook = null;
            workbook = Workbook.getWorkbook(new File(Client_dir));
            
            String user = this.TF_user.getText();
            String pass = this.TF_pass.getText();
            
            BD_Handler bdh = new BD_Handler(user, pass); // se inicializa cconector a la BD
            Sheet sheet = workbook.getSheet(0);
            
            // se obtiene el numero maximo de filas de la hoja
            int maxRow = sheet.getRows();
            
            String nombre_in, nit_in, telefono_in, direccion_in;
            
            for (int i=0; i<=maxRow-1; i++ ){
                try{                      //(col , fila)
                    nit_in = sheet.getCell(this.NIT, i).getContents();
                    // trim elimina white space al comienzo y al final del String
                    nombre_in = sheet.getCell(this.NOMBRE, i).getContents().trim();
                    telefono_in = sheet.getCell(this.TEL, i).getContents();
                    direccion_in = sheet.getCell(this.DIRECCION, i).getContents();
                    
                    bdh.InsertCliente(                             //nit 1631
                            0, 1631, nit_in, // consecutivo, tipo identificacion, num identificacion
                            nombre_in, direccion_in, 1628, // nombre, direccion, codciudad
                            telefono_in, "", hoy.toString(), // tel, correo, fechaing
                            1707, "", null, // cod tipo valor, celular, fecha nac
                                            //1707 normal 1708 mayor 1709 pref
                            0, 0, "", // genero hombre-1772, unidad residencial, apartamento
                            null, 0, 0, // contacto, rtfuente, triva
                            0, null, null, // rtica, precio, plazo
                            "Medellin", 2, 0, // desciudad, idcliente, cupo __(siempre pone meddlin aunq se usen codcius diferentes ¿?)
                            "", 0, null, // usuarioven, comision porcen, fecha env corr
                            3, 0, 0, // dias alerta cartera, es exento, es neto
                            30, 0, 0, // dias credito, tipo, es autoretenedor
                            0, 0, 0, // es declarante, omite texto credito fac, es retenedor
                            0, "insertado por script", 0, // es proveedor, observacion, factoremialqui
                            0); // clase cliente.
                    
                    //tynyInts 0-> unchecked, 1 -> checked.
                    
                } catch(ClassNotFoundException | SQLException ex){
                    //Logger.getLogger(FXMLDocMainController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(" **** Ha ocurrido una excepcion SQL :" + ex.getMessage());
                    System.out.println(" --- index:" + Integer.toString(i+1));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocMainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(FXMLDocMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * metodo que cuenta la ocurrencia del String Cliente: en la primera columna.
     * cada ocurrencia de este string indica un saldo a cargar. Se hace
     * este calculo por adelantado para evitar un overflow.
     * @param sheet 
     * @return  
     */
    public int CountItemsCartera(Sheet sheet){
        int cuenta = 0;
        // se barre todo el documento en busca del String Cliente
        for(int i=0; i<=sheet.getRows()-1; i++){
            if(sheet.getCell(0, i).getContents().contains(FXMLDocMainController.SALDO_MARK)) cuenta++;
        }
        return cuenta;
    }
    
    
    
    /**
     * Este metodo no se puede ejecutar cuando se trata del ultimo
     * saldo. por eso es necesario saber con antelacion cuatos saldos
     * hay en el doc de exel. Por este mismo motivo no se usa 
     * getNext()-1 para obtener la index de la fila del saldo, ya que
     * en el ultimo saldo puede quedar en loop infinito 
     * o generar index out of bound exception.
     * @param sheet
     * @param index
     * @return 
     */
    public int getNext(Sheet sheet, int index){
        int next_row = -1;
        int j = index +1;
        while(next_row==-1){
            if(sheet.getCell(0,j).getContents().contains(FXMLDocMainController.SALDO_MARK)) next_row=j;
            j++;
        }
        return next_row;
    }
    
    
    /**
     * metodo que debe recibir un indice de fila que contenga
     * "Cliente: ", como respuesta este metodo entrega el indice de fila que
     * contiene el saldo totalizado del cliente correspondiente de la
     * fila index en el argumento de este metodo.
     * 
     * @param sheet
     * @param index
     * @return 
     */
    public int getNetoRow(Sheet sheet, int index){
        int neto_row = -1;
        int j = index +1;
        while(neto_row==-1){ // la siguiente celda vacia despues de la ocurrencia de
            // un contains("Cliente: ") corresponde a la fila donde se encuentra
            // el saldo neto del cliente.
            if(sheet.getCell(0,j).getContents().equals("")) neto_row=j;
            j++;
        }
        return neto_row;
    }
    
    
    
    
    
    /**
     * se ejecuata al comienzo de la aplicacion
     */
    @FXML
    public void initialize() {
        
        
    }   
}
