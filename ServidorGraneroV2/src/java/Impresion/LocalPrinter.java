package Impresion;

import BD.Handlers.BDH_Configuracion;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import wagu.Board;
import wagu.Table;
import Configuracion.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author esteban
 */
public class LocalPrinter {
    
    
    
    public LocalPrinter(){
        
    }
    
    
    /**
     * recibe el string listo para enviar a la impresora.
     * el string ya debe estar en forma de tabla
     * @param venta 
     */
    public void Imprimir(String venta) 
            throws ClassNotFoundException, SQLException, PrintException{
        
        // conector a la tabla de configuracion.
        BDH_Configuracion bdhconf = new BDH_Configuracion();
        
        // nombre de la impresora en el OS.
        String printer_name = bdhconf.GetConfiguracion(BDH_Configuracion.PRT_Impresora_factura);
        
        // se obtienen todos los servicios de impresion.
        PrintService ps[] = PrintServiceLookup.lookupPrintServices(null, null);
        
        
        // se obtiene como PrintService la impresora tmu220pd epson
        for (PrintService p : ps){
            // si se encuentra una impresora con ese nombre en el OS
            if(p.getName().equals(printer_name)){
                // se realiza la impresion de la factura.
                PrintService printer = p;
                DocPrintJob dpj = printer.createPrintJob();
                // se crea un tipo de DocFlavor soportado por la tmu220pd.
                DocFlavor docfav = DocFlavor.INPUT_STREAM.AUTOSENSE;
                //40 caracteres.
                InputStream is = new ByteArrayInputStream(venta.getBytes());
                Doc docu = new SimpleDoc(is, docfav, null);
                dpj.print(docu, null);
            }
        }        
    }

    
    /**
     * toma la venta en forma String tal como se manda en el post.
     * connstruye la factura en forma de tabla y le concatena en el inicio
     * el encabezado que se establece en la seccion de configuracion
     * y la fecha de generacion.
     * @param rawVenta
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public String ReceiptStrGen(String rawVenta) 
            throws ClassNotFoundException, SQLException{
        
        BDH_Configuracion bdhconf = new BDH_Configuracion();
        
        // numero de caracteres por linea que caben en la factura.
        int maxNumLineas = Integer.parseInt(bdhconf.GetConfiguracion(BDH_Configuracion.PRT_Chars_por_Linea));
        
        String tabla = TableStrGen(FormarLista(rawVenta, maxNumLineas), maxNumLineas);
        
        String factura = tabla;
        
        return bdhconf.GetConfiguracion(BDH_Configuracion.PRT_factura_head)+ "\n\n" +
                LocalDateTime.now().toString() + '\n'+ tabla;
    }
    
    
    
    /**
     * Se hace uso de la libreria WAGU con licencia mit (codigo abierto
     * y uso libre para fines personales o comerciales.)
     * 
     * Se recibe una lista  de prodcutos y se genera un string con los datos 
     * @param rowsList
     * @param chsize
     * @return 
     */
    public String TableStrGen(List< List<String>> rowsList, int chsize){
        
        //Encabezado de la tabla
        List<String> headersList = Arrays.asList("Descripcion", "V.Unit.", "Cant.", "Sub.T");
        
        //List<Integer> alignlist = new ArrayList<Integer>(Collections.nCopies(rowsList.size(), Block.DATA_CENTER));
        
        Board board = new Board(chsize);
        
        Table tab = new Table(board, chsize, headersList, rowsList);
        List<Integer> ColWidth = Arrays.asList(chsize-26,7,7,7); //dimensiones columnas
        tab.setColWidthsList(ColWidth);
        tab.setGridMode(15);
        //tab.setColAlignsList(alignlist);
        
        String tableString = board.setInitialBlock(tab.tableToBlocks()).build().getPreview();
        
        return tableString;
    }
    
    public List<List<String>> FormarLista(String rawstr, int chsize){
        
        List<List<String>> rowList = new ArrayList<>(); // lista vacia
        
        String[] ventax = rawstr.split("/-/");
        
        //String[] D = new String[ventax.length]; // lista nombre productos
        //String[] N = new String[ventax.length]; // lista numero de unidades de cada producto
        //String[] V = new String[ventax.length]; // lista precio unitario de cada producto
        
        int Sum = 0;
        
        String[] aux;
        for (int j=0; j<ventax.length; j++){
            aux = ventax[j].split(":");
            //aux[0] descripcion del produccto j-esimo.
            //aux[1] cantidad del pproduucto j-esimo.
            //aux[2] es el valor unitario del producto j-esimo
            //sub total del producto j-esimo
            String V = Integer.toString(Integer.parseInt(aux[2])*Integer.parseInt(aux[1]));
            
            Sum += Integer.parseInt(V);
            
            int Dlen = aux[0].length();
            
            if(Dlen <= chsize-26){
                //                          D     Unit     N   SubTotal
                rowList.add(Arrays.asList(aux[0], aux[2], aux[1], V));
            }
            else{
                
                rowList.add(Arrays.asList(aux[0], aux[2], aux[1], V));
                //            D     Unit     N   SubTotal
                
                for(int i=0; i < Math.ceil(Dlen/16.0); i++){
                    rowList.add(Arrays.asList(" ", "       ", "       ", "       "));
                }
                
            }
            
            
        }
        
        rowList.add(Arrays.asList("Total -->", "----", "---", Integer.toString(Sum)));
        
        return rowList;
    }
    
    /*
    private class AuxVentaRow{
        String nombre, strNum, strUnitval, strNxUnit;
        int Num, UnitVal, NxUnit;

        public AuxVentaRow(String Descri, String Num, String UnitV) {
            this.nombre = Descri;
            this.strNum = Num;
            this.strUnitval = UnitV;
            
            this.Num = Integer.parseInt(Num);
            this.UnitVal = Integer.parseInt(UnitV);
            this.NxUnit = this.Num * this.UnitVal;   
        }  
    }
    */
}
