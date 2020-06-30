package javaprintprototipado;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

/**
 *
 * @author esteban
 */
public class JavaPrintPrototipado {

    
    public static String venta1="J-1 Y J-2*200:1:27000/-/"
            + "producto Ingresado Manualmenete:1:15200/-/"
            + "CALDO DOÑA GALLINAr:12:200/-/"
            + "AZUCAR BULTO*100:1:100000";
    
    
    public static String venta2="@ARROZ DIANA:2:40000/-/"
            + "J2:1248:170/-/"
            + "DISPLAY SUN TEA DUARZNO*12:4:14000";
    
    
    public static void main(String[] args) {
        
        
        //CODIGO servidor web
        
        // obtencion lista productos
        
        String[] ventax = venta1.split("/-/");
        
        String[] D = new String[ventax.length]; // liosta nombre productos
        String[] N = new String[ventax.length]; // lista numero de unidades de cada producto
        String[] V = new String[ventax.length]; // lista precio unitario de cada producto
        
        
        String[] aux = new String[3];
        for (int j=0; j<ventax.length; j++) {
            aux = ventax[j].split(":");
            D[j] = aux[0];
            N[j] = aux[1];
            V[j] = Integer.toString(Integer.parseInt(aux[2])*Integer.parseInt(aux[1]));
        }
        
        // algoritmo ajuste de impresion
        
        String impresion = "\n\n";
        
        for(int k=0; k<D.length; k++){
            
            boolean lineaNoAjustada = true;
            int lineas=1;
            
            do{
                if(D[k].length()-(-40+40*lineas)+N[k].length()+V[k].length()<=35){
                    
                    lineaNoAjustada = false;
                    String dv = D[k] + "---" + N[k] + "--" + V[k] + "\n";
                    for(int m=2; m<=lineas; m++){
                        dv = dv.substring(0, (m-1)*35)+"\n"+dv.substring((m-1)*35);
                    }
                    lineas=1;
                    impresion = impresion + dv;
                }else{
                    lineas++;
                }
                
            }while(lineaNoAjustada);
            
        }
        
        String totalventa = "TOTAL---->";
        
        int suma = 0;
        for(int y=0; y<V.length; y++){
            suma += Integer.parseInt(V[y]);
        }
        
        totalventa = totalventa.concat(Integer.toString(suma));
        
        impresion = impresion + totalventa + "\n\n\n\n\n\n\n\n\n\n";
        
        
        // inicio de la impresion
        String impresora = "tmu220pd";
        
        PrintService ps[] = PrintServiceLookup.lookupPrintServices(null, null);
        
        PrintService tmu220pd=ps[0];
        
        for (PrintService p : ps){
            if(p.getName().equals(impresora)) tmu220pd = p;
        }
        
        DocPrintJob dpj = tmu220pd.createPrintJob();
        
        DocFlavor docfav = DocFlavor.INPUT_STREAM.AUTOSENSE;
        
        
        
        InputStream is = new ByteArrayInputStream(impresion.getBytes());
        
        Doc docu = new SimpleDoc(is, docfav, null);
        
        try{
            dpj.print(docu, null);
        } catch(PrintException ex){
            
        }
        

        System.out.println(impresion);
        
    }
    
}
