package j_exeltest1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 *
 * @author esteban
 * 
 * En este proyecto deseo hacer una prueba de las librerias para escribir 
 * archivos de exel en java. Esto par aimplementarmla funcionalidad de exportar
 * las ventas en un perido de tiempo seleccionado por el usuario a un archivo de
 * exel y de esta forma permitir al usuario visualizacion de los datos.
 * 
 */


public class J_exelTest1 {
    
    
    
    public static void main(String[] args) throws IOException {
        
        
        String dirfil = "C:\\Users\\erich\\Desktop\\JexTest.xlsx";
        
        ArrayList<Aerosol> ali = new ArrayList<>();
        
        ali.add(new Aerosol(0, 0, "11", "10"));
        ali.add(new Aerosol(200, -10, "11", "10"));
        ali.add(new Aerosol(14, 12, "11", "10"));
        ali.add(new Aerosol(21, 150, "11", "10"));
        ali.add(new Aerosol(02, 004, "11", "10"));
        
        
        
        SaveWorkBook(BuldExelObject(ali), dirfil);
        
        
        
    }
    
    public static void SaveWorkBook(XSSFWorkbook workbook, String Dir) 
            throws FileNotFoundException, IOException{
        
        //Create file system using specific name
        FileOutputStream out = new FileOutputStream(
                new File(Dir));
        
        //write operation workbook using file out object
        workbook.write(out);
        out.close();
        System.out.println(" .xlsx written successfully");
        
    }
    
    
    
    
    public static XSSFWorkbook BuldExelObject(ArrayList<Aerosol> ali){
        //Create Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook(); // representacion de un documento de exel
        
        XSSFSheet hoja = workbook.createSheet("hoja1"); // representacion de una hoja del documento de exel.
        
        XSSFRow fila; // representacion de una fila de la hoja.
        
        for (int rowid=0; rowid<ali.size(); rowid++){
            
            fila = hoja.createRow(rowid+1);
            
            Cell cell1 = fila.createCell(0);
            cell1.setCellValue(ali.get(rowid).cantidad);
            
            Cell cell2 = fila.createCell(1);
            cell2.setCellValue(ali.get(rowid).costo);
            
            Cell cell3 = fila.createCell(2);
            cell3.setCellValue(ali.get(rowid).marca);
            
            Cell cell4 = fila.createCell(3);
            cell4.setCellValue(ali.get(rowid).vendedor);
            
        }
        
        return workbook;
    }
    
    public static class Aerosol{
        int cantidad;
        int costo;
        String marca;
        String vendedor;
        
        public Aerosol(int ca, int co, String mar, String ven){
            cantidad = ca;
            costo = co;
            marca = mar;
            vendedor = ven;
        }
    }
    
    
}
