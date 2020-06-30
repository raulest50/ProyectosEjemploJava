package newgeneration_barcodeprinter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author esteban
 * programa para la generacion de codigos para NG en baranquilla
 * de tia betty.
 * 
 * se requeire imprimir codigos con una estructura partoicular
 * que el software de Helman no permte. El precio del producto debe aparecer 
 * abajo del codigo de barras pero codificado con letras para que solo
 * los trabajadores del almacen sepan el precio, mientras que el cliente no.
 */
public class NewGeneration_BarcodePrinter extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMainDoc.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
