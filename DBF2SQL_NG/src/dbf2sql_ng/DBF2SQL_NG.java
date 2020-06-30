package dbf2sql_ng;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author esteban
 * 
 * globalsoft maneja la base de datos en formato DBF.
 * H&L soft emplea mysql para guardar la informacion.
 * 
 * Esta aplicacion en java pasa la tabla de clientes y la 
 */
public class DBF2SQL_NG extends Application {
    
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
