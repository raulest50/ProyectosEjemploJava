
package javafxconstraint_test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author esteban
 * 
 * Este proyecto se realizo para estudia porque no funcionaban las relaciones
 * de distancia entre los containers de java fx. Se descubrio que el hecho de usar
 * un include de javafx deshabilita estas relaciones. la solucion es incluir dentro del
 * include tamaños de anchor pane de 0, lo cual se muestra y se explica en el
 * fx principal de la aplicacion VirtualAdministrator.
 * 
 */
public class JavaFXConstraint_test extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
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
