/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GradleMorphiaTest;

/**
 *
 * @author Raul Alzate
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Producto p = new Producto("10040", "disco axion", "", 700.0, 800.0, 1000.0, 19.0);
        dbMorf db = new dbMorf();
        db.SaveProduct(p);
    }
}
