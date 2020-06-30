/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualadministrator.Controllers;

import javafx.fxml.FXML;
import virtualadministrator.FXMLControllerSKL;

/**
 * FXML Controller class
 *
 * @author erich
 */
public class ProductosController extends FXMLControllerSKL{

    
    
    @FXML public CodificarController codificarTabController;
    @FXML public ModificarController modificarTabController;
    @FXML public RelacionesController relacionesController;
    @FXML public AnalisisProductosController analisisProductosController;
    
    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        modificarTabController.SetProductosParentController(this);
    }    
    
}
