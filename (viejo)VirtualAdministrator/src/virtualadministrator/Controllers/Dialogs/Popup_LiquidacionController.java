/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualadministrator.Controllers.Dialogs;

import BD.Handlers.BDH_Inventarios;
import BD.Handlers.BDH_Productos;
import Definiciones.SptConteo;
import Definiciones.SptProducto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import virtualadministrator.FXMLControllerSKL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * FXML Controller class
 *
 * @author esteban
 */
public class Popup_LiquidacionController extends FXMLControllerSKL {
    
    @FXML public Button BLimpiar, BLiquidar, BMostrarTodos, BMostrar767, 
            BAgregar767, BAsignarCostos, BOrdenar, BActualizarStock;
    @FXML public Label Ltablaliq, labelMult, labelTotIndex, labelActIndex;
    @FXML public TableView<SptConteo> TabConteos;
    @FXML public TableColumn<SptConteo, Double> ColN, ColCosto;
    @FXML public TableColumn<SptConteo, String> ColName, ColLugar, ColContador, ColTime;
    @FXML public TextArea TAInforme;
    
    public String tabla;
    @FXML public ObservableList<SptConteo> ObservableConteos;
    @FXML public ComboBox ComboOrden;
    
    /**
     * lista con todos los elementos de la tabla de inventarios actual
     */
    public ArrayList<SptConteo> lfull = new ArrayList<>();
    /**
     * lista con todos los elementos de la tabla de inventarios actual que
     * fueron ingresados como no codificados
     */
    public ArrayList<SptConteo> L767N = new ArrayList<>();
    /**
     * lista con los elementos actuales que se estan mostrando en la tabla
     */
    public ArrayList<SptConteo> swlist = new ArrayList<>();
    
    
    BD.Handlers.BDH_Inventarios bdhi;
    BD.Handlers.BDH_Productos bdhp;
    // criterios de orden para agregar en el combobox de orden
    String cbcosto = "Mayor Costo", cbN = "Mayor Cantidad Items", 
            cbutilidad = "Mayor Utilidad", cbcontador = "Contador", cblugar = "Lugar", cbtime = "Tiempo Ingreso";
    
    
    @FXML Callback<TableColumn<SptConteo, Double>, TableCell<SptConteo, Double>> cellFactory =
            new Callback<TableColumn<SptConteo, Double>, TableCell<SptConteo, Double>>(){
                @Override
                public TableCell call(TableColumn p) {
                    return new EditingCell();
                }
            };
    
    
    /**
     * Initializes the controller class.
     * se invoc automaticamente cuando la ui de este dialog es creada
     * no deberia ser invocada manualmente por el usuario. solo la api de java 
     * la invoca.
     */
    @FXML
    public void init() {
        Ltablaliq.setText(tabla); // nombre de la tabla de inventario actual
        this.Config(); // se configura el table view
        this.ConfigComboBox(); // se configuran las opciones del comboBox
        bdhi = new BDH_Inventarios();
        bdhp = new BDH_Productos();
        try {
            lfull = bdhi.getFullTableList(tabla);
            MostarListaTVConteos(lfull, false);
            L767N = getL767(lfull);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Popup_LiquidacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelTotIndex.setText(Integer.toString(lfull.size()));
    }
    
    @FXML
    public void onActionLimpiar(ActionEvent evt){
        this.CleanPrint();
    }
    
    @FXML
    public void onClickLimpiar(MouseEvent evt){
        this.CleanPrint();
    }
    
    @FXML
    public void onClickLiquidar(MouseEvent evt){
        HacerLiquidacion();
    }
    
    @FXML
    public void onActionLiquidar(ActionEvent evt){
        HacerLiquidacion();
    }
    
    
    @FXML
    public void OnActionMostrarTodos(ActionEvent evt){
        MostarListaTVConteos(lfull, false);
    }
    
    @FXML
    public void onClickMostrarTodos(MouseEvent evt){
        MostarListaTVConteos(lfull, false);
    }
    
    @FXML
    public void onClickOrdenar(MouseEvent evt){
        this.Ordenar(swlist);
    }
    
    @FXML
    public void onClickMostrar767(MouseEvent evt){
        Mostrar767(lfull);
    }
    
    
    
    @FXML
    public void onActionMostrar767(ActionEvent evt){
        Mostrar767(lfull);
    }
    
    @FXML
    public void onClickAsignar(MouseEvent evt){
        AsignarCostos(swlist);
    }
    
    @FXML
    public void onActionAsignar(ActionEvent evt){
        AsignarCostos(swlist);
    }
    
    /**
     * actualiza el valor de la etiqueta labelMult. esta muestra 
     * el resultado de la multiplicacion de cantidad con costo
     * para el item seleccionado de la tabla. el metodo opera
     * cuando se hace click en la tabla
     * @param evt 
     */
    @FXML
    public void OnClickedTable(MouseEvent evt){
        updateMultLabel();
    }
    
    /**
     * actualiza el valor de la etiqueta labelMult. esta muestra 
     * el resultado de la multiplicacion de cantidad con costo
     * para el item seleccionado de la tabla. el metodo se ejecuta
     * cada que se interactua con la tabla mediante el teclado
     * @param kevt 
     */
    @FXML
    public void OnKeyReleasedTabView(KeyEvent kevt){
        updateMultLabel();
    }
    
    @FXML
    public void onClickActualizarStock(MouseEvent mve){
        ActualizarStock();
    }
    
    /**
     * se configura el tableview de la ventana de liquidacion
     */
    public void Config(){
        ColName.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        ColContador.setCellValueFactory(new PropertyValueFactory<>("idContador"));
        ColLugar.setCellValueFactory(new PropertyValueFactory<>("Lugar"));
        ColTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        ColN.setCellValueFactory(new PropertyValueFactory<>("N"));
        ColCosto.setCellValueFactory(new PropertyValueFactory<>("Costo"));
        ColCosto.setCellFactory(cellFactory);
    }
    
    /**
     * En este metodo se pobla la tabla de conteos en la ventana de liquidacion
     * @param lc 
     * @param editable 
     */
    public void MostarListaTVConteos(ArrayList<SptConteo> lc, boolean editable){
        swlist = lc;
        ObservableConteos = FXCollections.observableArrayList(lc);
        TabConteos.setItems(ObservableConteos);
        TabConteos.setEditable(editable);// true premite editar la celda de costos en la tabla de inventarios
    }
    
    /**
     * se encarga de cargar en el combo box de criterio de orden los criterios
     * disponibles.
     */
    public void ConfigComboBox(){
        String[] loc = {cbcosto, cbN, cbutilidad, cbcontador, cblugar, cbtime};
        ObservableList<String> oloc = FXCollections.observableArrayList(loc); // se arma la lista observable
        ComboOrden.setItems(oloc);
        ComboOrden.setValue(oloc.get(0));
    }
    
    
    /**
     * En este metodo se ordena la tabla de conteos de acuerdo a un criterio seleccionado
     * en el combo box de orden
     * @param lc
     */
    public void Ordenar(ArrayList<SptConteo> lc){
        String criterio = ComboOrden.getValue().toString();
        Thread t = new Thread(){
            public ArrayList<SptConteo> lcc = lc;
            @Override
            public void run(){
                writeTArea("Ordenando los elementos de acuerdo al\n"
                        + "orden especificado...");
                // orden por costo unitario
                if(criterio.equals(cbcosto)){
                    Comparator comp = new Comparator() {
                        @Override
                        public int compare(Object o1, Object o2) {
                            SptConteo sp1 = (SptConteo) o1;
                            SptConteo sp2 = (SptConteo) o2;
                            int re=0;
                            if (sp1.getCosto()> sp2.getCosto()) re = 1;
                            if(sp1.getCosto()==sp2.getCosto()) re = 0;
                            if(sp1.getCosto()<sp2.getCosto()) re = -1;
                            return re;
                        }
                    };
                    lcc.sort(comp);
                    Collections.reverse(lcc);
                    MostarListaTVConteos(lcc, All767(lcc));
                }
                // orden por numero de elementos
                if(criterio.equals(cbN)){
                    Comparator comp = new Comparator() {
                        @Override
                        public int compare(Object o1, Object o2) {
                            SptConteo sp1 = (SptConteo) o1;
                            SptConteo sp2 = (SptConteo) o2;
                            int re=0;
                            if (sp1.getN() > sp2.getN()) re = 1;
                            if(sp1.getN()==sp2.getN()) re = 0;
                            if(sp1.getN()<sp2.getN()) re = -1;
                            return re;
                        }
                    };
                    lcc.sort(comp);
                    Collections.reverse(lcc);
                    MostarListaTVConteos(lcc, All767(lcc));
                }
                
                // orden por utilidad (N*costo)
                if(criterio.equals(cbutilidad)){
                    Comparator comp = new Comparator() {
                        @Override
                        public int compare(Object o1, Object o2) {
                            SptConteo sp1 = (SptConteo) o1;
                            SptConteo sp2 = (SptConteo) o2;
                            int re=0;
                            double p1 = sp1.getCosto()*sp1.getN();
                            double p2 = sp2.getCosto()*sp2.getN();
                            if (p1 > p2) re = 1;
                            if(p1==p2) re = 0;
                            if(p1<p2) re = -1;
                            return re;
                        }
                    };
                    lcc.sort(comp);
                    Collections.reverse(lcc);
                    MostarListaTVConteos(lcc, All767(lcc));
                }
                
                // orden por contador
                if(criterio.equals(cbcontador)){
                    Comparator comp = new Comparator() {
                        @Override
                        public int compare(Object o1, Object o2) {
                            SptConteo sp1 = (SptConteo) o1;
                            SptConteo sp2 = (SptConteo) o2;
                            return sp1.getIdContador().compareTo(sp2.getIdContador());
                        }
                    };
                    lcc.sort(comp);
                    MostarListaTVConteos(lcc, All767(lcc));
                }
                
                // ordenar por lugar de conteo
                if(criterio.equals(cblugar)){
                    Comparator comp = new Comparator() {
                        @Override
                        public int compare(Object o1, Object o2) {
                            SptConteo sp1 = (SptConteo) o1;
                            SptConteo sp2 = (SptConteo) o2;
                            return sp1.getLugar().compareTo(sp2.getLugar());
                        }
                    };
                    lcc.sort(comp);
                    MostarListaTVConteos(lcc, All767(lcc));
                }
                
                //ordernar por instante de tiempo de ingreso.
                if(criterio.equals(cbtime)){
                    DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    Comparator comp = new Comparator() {
                        @Override
                        public int compare(Object o1, Object o2) {
                            int r=0;
                            LocalDateTime dt1, dt2;
                            SptConteo sp1 = (SptConteo) o1;
                            SptConteo sp2 = (SptConteo) o2;
                            dt1 = LocalDateTime.parse(sp1.getTime().replace(".0", ""), formateador);
                            dt2 = LocalDateTime.parse(sp2.getTime().replace(".0", ""), formateador);
                            if(dt1.isBefore(dt2)) r=-1;
                            if(dt1.equals(dt2)) r=0;
                            if(dt1.isAfter(dt2)) r=1;
                            return r;
                        }
                    };
                    lcc.sort(comp);
                    MostarListaTVConteos(lcc, All767(lcc));
                }
                
                writeTArea("Orden establecido :)");
            }
        };
        
        t.start();
    }
    
    /**
     * muestra en el tableview solamente los items del inventario que se ingresa-
     * ron como no codificados y ademas permite editar los valores de las celdas
     * costo
     * @param lc
     */
    public void Mostrar767(ArrayList<SptConteo> lc){
        MostarListaTVConteos(getL767(lc), true);
    }
        

    public void CleanPrint() {
        this.TAInforme.setText("");
    }
    
    
    public ArrayList<SptConteo> getL767(ArrayList<SptConteo> lc){
        ArrayList<SptConteo> l767 = new ArrayList<>();
        for(int i=0; i<lc.size(); i++){
            if(lc.get(i).getCodigo().equals("767")) l767.add(lc.get(i));
        }
        return l767;
    }

    /**
     * asigna los costos de los elementos que se estan mostrando actualmente
     * en la tabla. esto actualiza tambien el arraylist lfull. todo esto es
     * hecho mediante un hilo separado del ui thread
     * @param lc
     */
    public void AsignarCostos(ArrayList<SptConteo> lc) {
        //BAsignarCostos.
        Thread t = new Thread(){
            @Override
            public void run(){
                writeTArea("asignando costo desde bd productos \n"
                        + "a tabla de inventarios....\n");
                SptProducto prod;
                for(int k=0; k< swlist.size(); k++){
                    try {
                        if(swlist.get(k).getCodigo().equals("767"))
                            bdhi.ModificarCostos(tabla, swlist.get(k).getId(), 
                                    swlist.get(k).getCosto());
                        else{
                            prod = bdhp.BusquedaCodExact(swlist.get(k).getCodigo()).get(0);
                            bdhi.ModificarCostos(tabla, swlist.get(k).getId(), 
                                    Double.parseDouble(prod.getCosto()));
                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(Popup_LiquidacionController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch(IndexOutOfBoundsException | NullPointerException ex){
                        System.out.println(swlist.get(k).getCodigo()+"--"+swlist.get(k).getNombre());
                    }
                }
                writeTArea("Proceso terminado");
                try {
                    lfull = bdhi.getFullTableList(tabla);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(Popup_LiquidacionController.class.getName()).log(Level.SEVERE, null, ex);
                }
                MostarListaTVConteos(lfull, false);
            }
        };
        t.start();
    }
    
    /**
     * se usa para escribir strings en el campo de texto.
     * este metodo usa la inrfaz plataform.runLater para que este metodo del 
     * controlador pueda ser usado por hilos separados del ui Thread (porsupuesto
     *  el mismo ui Thread tambien lo puede usar) permitiendo mas reusabilidad en
     * el codigo.
     * @param line 
     */
    public void writeTArea(String line){
        String past = TAInforme.getText();
        Platform.runLater(() -> {
            if(past==null || past.equals("")) TAInforme.setText(line);
            else TAInforme.setText(past+"\n"+line);
        });
    }
    
    /**
     * se extraen todos los conteos de la tabla de inventario y se 
     * procede a la multiplicacion y suma de todos los items obteniendo
     * finalmente el equivlente en dinero de la mercancia contada.
     * finalmente se imprime el resultado y las unidades contadas en el 
     * area de texto y se actualiza la tabla.
     */
    public void HacerLiquidacion(){
        
        Thread t = new Thread(){
            @Override
            public void run(){
                double Sum = 0;
                double Num = 0;
                
                try {
                    lfull = bdhi.getFullTableList(tabla);
                    MostarListaTVConteos(lfull, false);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(Popup_LiquidacionController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for (int j=0; j<lfull.size(); j++){
                    Sum = lfull.get(j).getN()*lfull.get(j).getCosto()+ Sum;
                    Num+= lfull.get(j).getN();
                }
                writeTArea("Efectivo En mercancia: "+ Double.toString(Sum)+"\n Total De Unidades Contadas: "
                        + Double.toString(Num));
            }
        };
        t.start();
    }
    
    /**
     * indica si todos los elementos de la lista argumento son 
     * o no productos ingresados como sin codificar
     * @param lc
     * @return 
     */
    public boolean All767(ArrayList<SptConteo> lc){
        boolean r = true;
        for(int i=0; i<lc.size(); i++){
            if(!(lc.get(i).getCodigo().equals("767"))) r=false;
        }
        return r;
    }
    
    public void updateMultLabel(){
        try{
        SptConteo spc = TabConteos.getSelectionModel().getSelectedItem();
        labelMult.setText(Double.toString(spc.getCosto()*spc.getN()));
        labelActIndex.setText(Integer.toString(TabConteos.getSelectionModel().getSelectedIndex()));
        }
        catch(NullPointerException ex){
            labelMult.setText("---");
        }
    }
    
    
    /**
     * toma todos los elementos contados con codigo asignado,
     * es decir que sea diferente a 767, y los acumula para luego
     * asignar estos valores a
     */
    public void ActualizarStock(){
        
        ArrayList<SptConteo> AlModStock = new ArrayList<>();
        ArrayList aux = new ArrayList();
        SptConteo AuxConteo;
        
        for(int i=0; i<lfull.size(); i++){ //se revisa todos los conteos
            if(lfull.get(i).getCodigo().equals("767")){
                // si son productos sin codigo asignado entonces no se hace nada
            }
            else{// si son productos con condigo se agregan o se acumulan a la lista de actualizacion de stock
                aux = AlreadyAdded(lfull.get(i).getCodigo(), AlModStock);
                if( (boolean) aux.get(0)){ // si ya fue agregado se acumula
                    AuxConteo = AlModStock.get((int) aux.get(1));
                    AuxConteo.setN(AuxConteo.getN()+lfull.get(i).getN());
                    AlModStock.set((int) aux.get(1), AuxConteo);
                }
                else{// de lo contrario se agrega
                    AlModStock.add(lfull.get(i));
                }
            }
        }
        for(int j=0; j<AlModStock.size() ; j++){
            try {
                Double auxd = (Double) AlModStock.get(j).getN();
                bdhp.EstablecerStock(AlModStock.get(j).getCodigo(), auxd.intValue());
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Popup_LiquidacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        writeTArea("Stock Actualizado!!!");
    }
    
    /**
     * Indicasi el producto idenficado con 'codigo' esta presente
     * o no en la lista. de estar presente retorna en el puesto 0 del 
     * arreglo true y en el segundo un entero con el indice donde esta presente
     * 
     * en caso contrario solo retorna false.
     * @return 
     */
    public ArrayList AlreadyAdded(String codigo, ArrayList<SptConteo> Lb){
        ArrayList res = new ArrayList();
        res.add(false);
        for(int i=0; i<Lb.size(); i++){
            if(Lb.get(i).getCodigo().equals(codigo)){
                res.set(0, true);
                res.add(i);
            }
        }
        return res;
    }
    
    
    
    
    public class EditingCell extends TableCell<SptConteo, Double> {
        private TextField textField;
        public EditingCell() {
        }
        @Override
        public void startEdit() {
            super.startEdit();
            if (textField == null) {
                createTextField();
            }
            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    textField.requestFocus();
                    textField.selectAll();
                }
            });
        }
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(String.valueOf(getItem()));
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
        @Override
        public void updateItem(Double item, boolean empty) {
            
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }
        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(Double.parseDouble(textField.getText()));
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    } else if (t.getCode() == KeyCode.TAB) {
                        commitEdit(Double.parseDouble(textField.getText()));
                        TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                        if (nextColumn != null) {
                            getTableView().edit(getTableRow().getIndex(), nextColumn);
                        }
                    }
                }
            });
            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!newValue && textField != null) {
                        
                        commitEdit(Double.parseDouble(textField.getText()));
                        int ind = getIndex();
                        SptConteo nspt = swlist.get(ind);
                        nspt.setCosto(Double.parseDouble(textField.getText()));
                        swlist.set(ind, nspt);
                        try{
                            bdhi.ModificarCostos(tabla, nspt.getId(), nspt.getCosto());
                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(Popup_LiquidacionController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
        }
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
        /**
         *
         * @param forward true gets the column to the right, false the column to the left of the current column
         * @return
         */
        private TableColumn<SptConteo, ?> getNextColumn(boolean forward) {
            List<TableColumn<SptConteo, ?>> columns = new ArrayList<>();
            for (TableColumn<SptConteo, ?> column : getTableView().getColumns()) {
                columns.addAll(getLeaves(column));
            }
            //There is no other column that supports editing.
            if (columns.size() < 2) {
                return null;
            }
            int currentIndex = columns.indexOf(getTableColumn());
            int nextIndex = currentIndex;
            if (forward) {
                nextIndex++;
                if (nextIndex > columns.size() - 1) {
                    nextIndex = 0;
                }
            } else {
                nextIndex--;
                if (nextIndex < 0) {
                    nextIndex = columns.size() - 1;
                }
            }
            return columns.get(nextIndex);
        }
        
        private List<TableColumn<SptConteo, ?>> getLeaves(TableColumn<SptConteo, ?> root) {
            List<TableColumn<SptConteo, ?>> columns = new ArrayList<>();
            if (root.getColumns().isEmpty()) {
                //We only want the leaves that are editable.
                if (root.isEditable()) {
                    columns.add(root);
                }
                return columns;
            } else {
                for (TableColumn<SptConteo, ?> column : root.getColumns()) {
                    columns.addAll(getLeaves(column));
                }
                return columns;
            }
        }
    }
    
}