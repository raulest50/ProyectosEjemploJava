package virtualadministrator;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author esteban
 * 
 * esta clase sirve de padre para todos los controladores.
 * en esta clase se definen metodos para mostrar dialgogos modales de javafx, 
 * incluidos desde java 8_40, de forma tal que todos los controladores puedan
 * puedan hacer uso de los dialogos sin tener que reescribir el codigo en cada
 * clase
 */
public class FXMLControllerSKL {
    

    public FXMLControllerSKL() {
    }
    
    // mensaje de informacion.
    public void ShowInfo(String titulo, String subtitulo, String mensaje){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(subtitulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
        
        //DialogPane Dpane = alert.getDialogPane();
        //Dpane.getStylesheets().add(VirtualAdministrator.class.getClass().getResource("estilo.css").toExternalForm());
    }
    
    // mostrar mensaje de advertencia.
    public void ShowWarning(String titulo, String subtitulo, String mensaje){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(subtitulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
        
        //DialogPane Dpane = alert.getDialogPane();
        //Dpane.getStylesheets().add(VirtualAdministrator.class.getClass().getResource("estilo.css").toExternalForm());
    }
    
    public void ShowError(String titulo, String subtitulo, String mensaje){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(subtitulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
        //DialogPane Dpane = alert.getDialogPane();
        //Dpane.getStylesheets().add(VirtualAdministrator.class.getClass().getResource("estilo.css").toExternalForm());
    }
    
    public  String ShowInput(String titulo, String subtitulo, String mensaje, String valordefecto){
        String input = "";
        TextInputDialog dialog = new TextInputDialog(valordefecto);
        dialog.setTitle(titulo);
        dialog.setHeaderText(subtitulo);
        dialog.setContentText(mensaje);
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        //DialogPane Dpane = dialog.getDialogPane();
        //Dpane.getStylesheets().add(VirtualAdministrator.class.getClass().getResource("estilo.css").toExternalForm());
        if (result.isPresent()){
            input = result.get();
        }
        return input;
    }
}
