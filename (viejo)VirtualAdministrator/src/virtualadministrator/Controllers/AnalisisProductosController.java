package virtualadministrator.Controllers;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;

/**
 * FXML Controller class
 *
 * @author esteban
 */
public class AnalisisProductosController implements Initializable {
    
    @FXML
    public LineChart<Number, Number> LChartInfoproducto;
    
    @FXML
    public NumberAxis Xaxis;
    
    @FXML
    public NumberAxis Yaxis;
    
    public XYChart.Series curva1, curva2;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        curva1 = new XYChart.Series();
        curva2 = new XYChart.Series();
        
        Xaxis.setLabel("Dias");
        Yaxis.setLabel("U. Vendidas");
        
        LChartInfoproducto.setTitle("Seguimiento Ventas Productos :)");
        
        curva1.getData().add(new XYChart.Data<>(1, 10));
        curva1.getData().add(new XYChart.Data<>(2, 12));
        curva1.getData().add(new XYChart.Data<>(3, 15));
        curva1.getData().add(new XYChart.Data<>(4, 30));
        curva1.getData().add(new XYChart.Data<>(5, 32));
        curva1.getData().add(new XYChart.Data<>(6, 29));
        curva1.getData().add(new XYChart.Data<>(7, 18));
        curva1.getData().add(new XYChart.Data<>(8, 15));
        curva1.getData().add(new XYChart.Data<>(9, 7));
        curva1.getData().add(new XYChart.Data<>(10, 2));
        curva1.getData().add(new XYChart.Data<>(11, 1));
        curva1.getData().add(new XYChart.Data<>(12, 2));
        curva1.getData().add(new XYChart.Data<>(13, 10));
        curva1.getData().add(new XYChart.Data<>(14, 12));
        
        
        curva2.getData().add(new XYChart.Data<>(1, 20));
        curva2.getData().add(new XYChart.Data<>(2, 21));
        curva2.getData().add(new XYChart.Data<>(3, 22));
        curva2.getData().add(new XYChart.Data<>(4, 15));
        curva2.getData().add(new XYChart.Data<>(5, 25));
        curva2.getData().add(new XYChart.Data<>(6, 20));
        curva2.getData().add(new XYChart.Data<>(7, 18));
        curva2.getData().add(new XYChart.Data<>(8, 16));
        curva2.getData().add(new XYChart.Data<>(9, 22));
        curva2.getData().add(new XYChart.Data<>(10, 14));
        curva2.getData().add(new XYChart.Data<>(11, 17));
        curva2.getData().add(new XYChart.Data<>(12, 19));
        curva2.getData().add(new XYChart.Data<>(13, 24));
        curva2.getData().add(new XYChart.Data<>(14, 25));
        
        LChartInfoproducto.getData().add(curva1);
        LChartInfoproducto.getData().add(curva2);
        
        LChartInfoproducto.setLegendVisible(false);
    }
    
}
