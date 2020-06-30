package virtualadministrator;

import BD.Handlers.BDHCreator;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author esteban
 */
public class VirtualAdministrator extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        /**
         * se cargan fuentes de letras especiales. 
         * la forma de usarlas es similar que en html5 con css3 pero 
         * en javafx para tener acceso a los fonts no basta con declrarlos en 
         * el css sino que hay que cargarlos previamente al inicio de la apli
         * cacion para usarla en el archivo css.
         **/
        
        Font.loadFont(VirtualAdministrator.class.getResource("Fonts/belligerent.ttf").toExternalForm(), 12);
        Font.loadFont(VirtualAdministrator.class.getResource("Fonts/beer_money.ttf").toExternalForm(), 12);
        Font.loadFont(VirtualAdministrator.class.getResource("Fonts/cac_champagne.ttf").toExternalForm(), 12);
        Font.loadFont(VirtualAdministrator.class.getResource("Fonts/plexifont.ttf").toExternalForm(), 12);
        Font.loadFont(VirtualAdministrator.class.getResource("Fonts/cac_champagne.ttf").toExternalForm(), 12);
        Font.loadFont(VirtualAdministrator.class.getResource("Fonts/AC_Brodie.otf").toExternalForm(), 12);
        Font.loadFont(VirtualAdministrator.class.getResource("Fonts/Exo_SemiBold.otf").toExternalForm(), 12);
        
        
        Parent root;
        
        root = FXMLLoader.load(getClass().getResource("FXMLVirtualAdministrator.fxml"));
                BDHCreator bdhc = new BDHCreator();
                bdhc.Crear_Todas_Las_tablas(); // se crea las tabla tablas y las bd en caso de que no existan.
                
        /*        
        root = FXMLLoader.load(getClass().getResource("FXML_ModalLiz.fxml"));
        String tos = System.getProperty("os.name");
        boolean val = false; // verbose obfuscation
        //if(tos.contains("Windows") || tos.contains("WINDOWS") || tos.contains("windows")){
           // if(Liz.ReadLic(System.getProperty("user.home")+
              //      File.separator+"co.lic").equals(Liz.encode(Liz.Get_this_sys_win()))){ 
                root = FXMLLoader.load(getClass().getResource("FXMLVirtualAdministrator.fxml"));
                BDHCreator bdhc = new BDHCreator();
                bdhc.Crear_Todas_Las_tablas(); // se crea las tabla tablas y las bd en caso de que no existan.
           // }
            //else root = FXMLLoader.load(getClass().getResource("FXML_ModalLiz.fxml"));
        } // se carga el fxml principal
        //else root = FXMLLoader.load(getClass().getResource("FXML_ModalLiz.fxml"));
        //getStylesheets().add(this.getClass().getResource("Estilos/Estilo_Ventana_Principal.css").toExt‌​ernalForm());
        
        if(tos.contains("Linux") || tos.contains("LINUX") || tos.contains("linux") 
                || tos.contains("ubuntu") ||tos.contains("Ubuntu")){
            //if(Liz.ReadLic(System.getProperty("user.home")+
           //         File.separator+"co.lic").equals(Liz.encode(Liz.GetThisSysUbun()))){ 
                root = FXMLLoader.load(getClass().getResource("FXMLVirtualAdministrator.fxml"));
                BDHCreator bdhc = new BDHCreator();
                bdhc.Crear_Todas_Las_tablas(); // se crea las tabla tablas y las bd en caso de que no existan.
            //}
            //else root = FXMLLoader.load(getClass().getResource("FXML_ModalLiz.fxml"));
        } // se carga el fxml principal
        //getStylesheets().add(this.getClass().getResource("Estilos/Estilo_Ventana_Principal.css").toExt‌​ernalForm());
        */
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png"))); // para establecer el icono de la aplicacion
        stage.show();
        
    }
    
    public static void main(String[] args) {
        launch(args); // aqui es donde se arranca la aplicacion.
        /*
        if(Liz.isAdmin()) System.out.println("si es administrador");
        else System.out.println("no es admin");
        System.out.println(Liz.CMDc("net user "+System.getProperty("user.name")));
        */ 
    }
    
    
}
