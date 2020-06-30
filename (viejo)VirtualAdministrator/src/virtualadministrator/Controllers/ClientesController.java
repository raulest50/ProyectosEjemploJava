package virtualadministrator.Controllers;

import Definiciones.SptCliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import virtualadministrator.Controllers.Controllers_Threads.ClientesThreads;


/**
 * FXML Controller class
 *
 * @author esteban
 */
public class ClientesController {

    @FXML 
    public TextField TFCodigo, TFNombre, TFApodo, TFDireccion, TFFijo, TFCel;
    
    @FXML 
    public Button BBuscar, BAgregar, BBorrar, BModificar, BBrorar2;
    
    @FXML 
    public TableView TVClientes;
    
    @FXML 
    public TableColumn<SptCliente, String> TCCodigo, TCNombre, TCApodo, TCDireccion,
            TCFijo, TCCelular;
    @FXML 
    public Label LEstado;
    
    public ClientesThreads ct;
    
    @FXML
    public void initialize() {
        this.ct = new ClientesThreads(this);
    }
    
    @FXML
    public void ClickBuscar(MouseEvent event){
        
    }
    
    @FXML
    public void ClickModificar(MouseEvent event){
        
    }
    
    @FXML
    public void ClickBorrar2(MouseEvent event){
        
    }
    
    @FXML
    public void ClickBorrar(MouseEvent event){
        
    }
    
    @FXML
    public void ActionBuscar(ActionEvent event){
        
    }
    
    @FXML
    public void ClickAgregar(MouseEvent event){
        
    }
    
    @FXML
    public void ActionTFCodigo(ActionEvent event){
        
    }
    
    @FXML
    public void ActionTFNombre(ActionEvent event){
        
    }
    
    @FXML
    public void ActionTFApodo(ActionEvent event){
        
    }
    
    @FXML
    public void ActionTFDireccion(ActionEvent event){
        
    }
    
    @FXML
    public void ActionTFFijo(ActionEvent event){
        
    }
    
    @FXML
    public void ActionTFCel(ActionEvent event){
        
    }
    
    @FXML
    public void ClickCargar(MouseEvent event){
        
    }
    
    public void SetLEstado(String text){
        this.LEstado.setText(text);
    }
}
