package jdbc_pst_insertgenerator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author esteban
 * 
 * Aplicacion de javaFX para generacion de codigo JDBC.
 * genera el codigo de un metodo java para hacer inserts en una tabla 
 * especificada usando PreparedStatement.
 */
public class JDBC_CodeGenerator extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocMain.fxml"));
        
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
