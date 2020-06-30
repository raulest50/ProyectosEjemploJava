package Javax_Print;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.*;
/**
 *
 * @author esteban
 * En este corto ejecutable de java se hice mi primera prueba pra imprimir
 * con la empson tm u220pd, configurada como generic text only.
 * funciona bien y no se desperdicia papel como cuando se imprime usando 
 * el software de windows. bloc de notas.
 * sin embargo cuando el texto se sale de la amrgen del papel a veces no lo 
 * imprime en lugar de imprimirlo en la linea siguiente.
 * 
 * Hay buena documentacion en la nube sobre las apis de java para imprimir.
 * sin embargo por la cantidad de objetos envueltos en el proceso de impresion
 * es necesario comprender las funciones de cada uno y hacer pruebas con las
 * clases primero para poderlas entender, sobre todo en el caso de javax.print
 * por lo que en ente ejecutable mas que la impresion esta es un ensayo con las 
 * clases de dicha API.
 */



public class Javax_Print {
    
    /**
     * @param args the command line arguments
     * 
     */
    public static void main(String[] args){
        
        // se imprimen los servicios de impresion
        /*
        PrintService representa a una impresora configurada en el panel de
        impresoras y faxes o como se llame.
        En este caso se usa la funcion 
        PrintServiceLookup.lookupPrintServices(null, null);
        que al tener como parametros null, null.
        retorna todos los servicios de impresion entre ellos el que busco que
        es tmu220pd.
        */
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for (int i = 0; i < services.length; i++) {
            System.out.println(services[i].getName());
        }
        
        //Imprimo los ---- para mejorar la visualizacion en la consola 
        //de netbeans
        System.out.println("----------\n--------------\n");
        
        /*
        DocFlavor representa el tipo de documento, los hay tipo html gif text 
        only etc. Para este caso en particular s eprobo con varios DocFlavors
        y el primero que funciono fue 
        DocFlavor sabor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        que es el que se usa en este ejecutable.
        */
        DocFlavor sabor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        
        /*
        Ahora a la hora de buscar los servicios de impresion paso como parametro
        el DocFlavor sabor, de esta forma se retorna solo aquellos servicios de 
        impresion que soportan el DocFlavor.
        */
        PrintService[] services2 = PrintServiceLookup.lookupPrintServices(sabor, null);
        for (int i = 0; i < services2.length; i++) {
            System.out.println(services2[i].getName());
        }
        
        // separador para mejorar la visualizacion en la consola.
        System.out.println("----------\n--------------\n");
        
        
        // se imprimen los docfalvor soportados por la tmu220pd
        /*
        El PrintService en services[0] corresponde a la tmu220pd al momento de
        creacion de este ejecutable. 
        a continuacions seobtienen y se
        imprimen los DocFlavors soportados por este servicio de 
        impresion.
        */
        DocFlavor docfav[] = services[0].getSupportedDocFlavors();
        
        //  se imprime el nombre del servicio de impresion que es un String
        //exactamente igual al nombre configurado en el panel faxes e impresoras
        // de windows.
        // se imprime nuevamente para claridad de interpretacion en la consola
        // de netbeans
        System.out.println(services[0].getName());
        
        // se imprimen los DocFlavors soportados por la tmu220pd
        for (int j=0; j<docfav.length; j++){
            System.out.println(docfav[j].getRepresentationClassName());
        }
        
        
        /*
        Se crea el trabajo de impresion.
        Este trabajo de impresion se crea a partir del servicio de immpresion
        que se desea emplear. Cada objeto DocPrintJob puede hacer solo una 
        immpresion a lo largo de su ciclo de vida por lo que si se desea 
        imprimir varias copias se debe configurar eso en el DocPrintJob o
        crear multiples DocPrintJob, para iprimir varios documentos 
        diferentes seria obligatorio crear multiples DocPrintJob.
        */
        DocPrintJob dpj = services[0].createPrintJob();
        
        
        /*
        se crea un String para imprimir.
        */
        String impre = "\n\nEnsayo De Impresion\n"
                + "Leche Colanta*1000 ------- 15500$\n"
                + "Mantequilla Premier ------ 3900$\n"
                + "Jamon Colanta *500g ------ 8200\n"
                + "Resma papel *500 oficio ---- 10000\n\n";
        
        
        //se debe convertir el String a imprimir en un InputStream
        InputStream is = new ByteArrayInputStream(impre.getBytes());
        
        /*
        El DocPrintJob imprime usandos la funcion print que recibe como 
        argumentos el documento a imprimir y los atributos de impresion.
        cuando los atributos son null se usan unos atributos por defecto.
        */
        
        
        /*
        para crear el documento a imprimir se usa un input Stream.
        se debe pasar tambien el DocFlavor.
        */
        Doc doc = new SimpleDoc(is, sabor, null);
        
        try {
            dpj.print(doc, null);
        } catch (PrintException ex) {
            Logger.getLogger(Javax_Print.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //se imprime nuevamente el servicio de imprecion por claridad en la
        // consola de netbeeans
        System.out.println(dpj.getPrintService().toString());
    }
}
