package virtualadministrator;
import MiCOMS.Poster;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
/**
 * FXML Controller class
 *
 * @author esteban
 */
public class ModalLizController extends FXMLControllerSKL{
    
        
    @FXML
    public Button Descargar; // boton para iniciar la descarga
    
    @FXML
    public Label LAns; // etiqueta que muestra 
    
    @FXML
    public TextField TFUsuario;
    
    @FXML
    public TextField TFClave;
    
    public ArrayList<String> param_names, param_vals;
    public String web = "http://raulest50.pythonanywhere.com/anfrage_lizenz";
    
    @FXML
    public void initialize() {
        param_names = new ArrayList<>();
        param_vals= new ArrayList<>();
        param_names.add("konto");
        param_names.add("kenwort");
        param_names.add("anlage");
        
        //Liz.ReadLic(System.getProperty("user.home")+File.separator+"co.lic");
    }
    
    @FXML
    public void onClickLic(MouseEvent mve){
        val_rut();
    }
    
    
    public boolean checkAdmin(){
        boolean r = true;
        
        return r;
    }
    
    public void val_rut(){
        Poster p = new Poster();
        String tos = System.getProperty("os.name");
        
        //si el sistema operativo es windows
        if(tos.contains("Windows") || tos.contains("WINDOWS") || tos.contains("windows")){
            if(Liz.isAdmin()){
                param_vals= new ArrayList<>();
                param_vals.add(TFUsuario.getText());
                param_vals.add(TFClave.getText());
                param_vals.add(Liz.Get_this_sys_win());
                String Wans = p.HacerPost(param_names, param_vals, this.web);
                if(Wans.contains("Error") || Wans.equals("")) 
                    LAns.setText( "Ha ocurrido un problema en la comunicacion con el servidor\n" + Wans);
                else{
                    //System.out.println(Wans);
                    Liz.WriteLic(Wans);
                    LAns.setText(":) Ya se ha creado la licencia Reinicie el Programa porfavor");
                }
            }
            else{
                LAns.setText("**Error**El Programa debe ser instalado en una cuenta con privilegios de administrador");
            }
        }
        
        // si el sistema operativo es linux
        if(tos.contains("Linux") || tos.contains("LINUX") || tos.contains("linux")
                || tos.contains("ubuntu") ||tos.contains("Ubuntu")){
            if(System.getProperty("user.name").equals("root")){ // se verifica 'sudo'
                param_vals= new ArrayList<>();
                param_vals.add(TFUsuario.getText());
                param_vals.add(TFClave.getText());
                param_vals.add(Liz.GetThisSysUbun());
                String Wans = p.HacerPost(param_names, param_vals, this.web);
                if(Wans.contains("Error") || Wans.equals("")) 
                    LAns.setText( "Ha ocurrido un problema en la comunicacion con el servidor\n" + Wans);
                else{
                    //System.out.println(Wans);
                    Liz.WriteLic(Wans);
                    LAns.setText(":) Ya se ha creado la licencia Reinicie el Programa porfavor");
                }
            }
            
            else{ // en caso de no ser admministrador
                LAns.setText("**Error**El Programa debe ejecutarse con derechos de super usuario");
            }
        }
        
        //si es mac
        if(tos.contains("Mac") || tos.contains("MAC") || tos.contains("mac")){
            
        }
        
    }
    
}
