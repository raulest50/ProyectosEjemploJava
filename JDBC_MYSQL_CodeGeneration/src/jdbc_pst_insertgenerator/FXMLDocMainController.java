package jdbc_pst_insertgenerator;


import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import generator.BDHandlerGenerator;
import generator.classGen.TableClassGenerator;
import java.io.IOException;

import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


/**
 *
 * @author esteban
 */
public class FXMLDocMainController {
    
    /**
     * campo de texto para ingresarr el nombre de usuario 
     * en la base de datos de mysql
     */
    @FXML
    private TextField TF_user;

    /**
     * contraseña para acceder a la BD de my sql
     */
    @FXML
    private PasswordField TF_pass;

    /**
     * nombre del schema que contiene la tabla a la que se le desea generar
     * el insert
     */
    @FXML
    private TextField TF_BDname;

    
    /**
     * campo de texto para poner la ruta donde se desea guardar los archivos
     * de la generacion de codigo.
     */
    @FXML
    private TextField TF_Dir;
    
    
    @FXML
    public void initialize() {
        
    }    
    
    /**
     * cuando se hace click al bonton de limpiar, se borran
     * todos los campos de texto y el text areea para la generacion de codigo.
     * @param event 
     */
    @FXML
    void onClick_Clear(MouseEvent event) {
        this.TF_BDname.clear();
        this.TF_pass.clear();
        this.TF_user.clear();
    }
    
    
    /**
     * nombre de la carpeta que crera el programa para guardar el codigo fuente
     * generado.
     */
    public final String CODE_FOLDER_NAME = "BD_Interface";

    /**
     * metodo onClick del boton ara generar codigo
     * @param event 
     */
    @FXML
    void onClick_CodGen(MouseEvent event) {
        
        String user = this.TF_user.getText();
        String pass = this.TF_pass.getText();
        String db = this.TF_BDname.getText();
        
        // ruta para guardar el codigo generado.
        String dir = this.TF_Dir.getText(); 
        
        BD_Handler bdh = new BD_Handler(user, pass, db);
        
        // clase que genera BDHandler, la generalizacion de todos los conectores
        BDHandlerGenerator bdhGen = new BDHandlerGenerator("jdbc:mysql://127.0.0.1:3306/negocio_r", user, pass);
        
        // clase que genera la representacion y el conector para cada tabla
        TableClassGenerator tbGen = new TableClassGenerator();
        
        try{
            // se obtiene una representacion de la base de datos en java.
            DataBaseSQL dtbase = bdh.GetDataBaseSQL();
            
            // se crea la clase BDHandler que es una generalizaciion de las
            // clases conectoras, por lo que esta clase siempre es la misma
            // para cualquier BD y se crea primero antes que las demas clases.
            TypeSpec bdh_tspec = bdhGen.GenerateBDHandSpec();
            JavaFile.builder(this.CODE_FOLDER_NAME, bdh_tspec).build().writeTo(Paths.get(dir));
            
            ArrayList<TypeSpec> connectorClasses = new ArrayList<>();
            ArrayList<TypeSpec> repClasses = new ArrayList<>();
            
            // para cada tabla se crea una clase que la representa y su 
            // clase conectora que hace operaciones de select insert delete etc.
            for(Tabla tb : dtbase.tablas){
                repClasses.add(tbGen.GetObjTpSpec(tb.TableName, tb.cols));
            }
            
            // se escriben cada una de las clases en su respectivo archivo .java
            for(TypeSpec tp : connectorClasses){
                JavaFile.builder(this.CODE_FOLDER_NAME, tp).build().writeTo(Paths.get(dir));
            }
            
            for(TypeSpec tp : repClasses){
                JavaFile.builder(this.CODE_FOLDER_NAME, tp).build().writeTo(Paths.get(dir));
            }
            
        } catch(SQLException | ClassNotFoundException ex){
            System.out.println("Ha ocurrido una excepcion :" + ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
