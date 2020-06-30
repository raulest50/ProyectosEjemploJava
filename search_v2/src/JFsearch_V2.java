

import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


public class JFsearch_V2 extends javax.swing.JFrame {
    
    
    Image img;
    
    String ind;

    public JFsearch_V2() {
        this.img = (new ImageIcon(getClass().getResource("/images/buscadorv2.png"))).getImage();
        initComponents();
        
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        
        
        Font fo = new Font("Arial", 1, 21);
        JTableHeader head = JT_result.getTableHeader();
        head.setFont(fo);
        JT_result.setTableHeader(head);
        
        JRbutton_group.add(JRBdescripcion);
        JRbutton_group.add(JRBcodigolast);
        
        JRBdescripcion.setSelected(true);
        
        JT_result.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JT_result.getColumnModel().getColumn(0).setPreferredWidth(720);
        JT_result.getColumnModel().getColumn(1).setPreferredWidth(120);
        JT_result.getColumnModel().getColumn(2).setPreferredWidth(120);
        JT_result.getColumnModel().getColumn(3).setPreferredWidth(100);
        JT_result.getColumnModel().getColumn(4).setPreferredWidth(270);

    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JRbutton_group = new javax.swing.ButtonGroup();
        JTbusqueda = new javax.swing.JTextField();
        Buscar = new javax.swing.JButton();
        JLtitulo = new javax.swing.JLabel();
        Borrar = new javax.swing.JButton();
        JLtipbusqueda = new javax.swing.JLabel();
        JRBdescripcion = new javax.swing.JRadioButton();
        JRBcodigolast = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        JT_result = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Buscador  de Productos Prototipo 2.0");
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(img);
        setName("buscador 1.0 prototipo"); // NOI18N

        JTbusqueda.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        JTbusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTbusquedaActionPerformed(evt);
            }
        });

        Buscar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Buscar.setText("Buscar");
        Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarActionPerformed(evt);
            }
        });

        JLtitulo.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        JLtitulo.setText("Buscador Productos Prototipo 2.0");

        Borrar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Borrar.setText("Borrar");
        Borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarActionPerformed(evt);
            }
        });

        JLtipbusqueda.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        JLtipbusqueda.setText("Tipo de busqueda");

        JRBdescripcion.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        JRBdescripcion.setText("Por Descripcion");
        JRBdescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JRBdescripcionActionPerformed(evt);
            }
        });

        JRBcodigolast.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        JRBcodigolast.setText("Por Ultimos Digitos Del Codigo");
        JRBcodigolast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JRBcodigolastActionPerformed(evt);
            }
        });

        JT_result.setBackground(new java.awt.Color(204, 204, 255));
        JT_result.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        JT_result.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Descripcion", "PV Publico", "PV Tienda", "Costo", "Last Update"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        JT_result.setRowHeight(37);
        JT_result.setRowMargin(2);
        JT_result.setSelectionBackground(new java.awt.Color(255, 204, 204));
        JT_result.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        JT_result.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(JT_result);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(366, 366, 366)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Borrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(JTbusqueda, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(JLtitulo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JRBdescripcion)
                    .addComponent(JLtipbusqueda)
                    .addComponent(JRBcodigolast))
                .addGap(0, 84, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(JLtitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(JTbusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(JLtipbusqueda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JRBdescripcion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JRBcodigolast)
                        .addGap(14, 14, 14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JTbusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTbusquedaActionPerformed
        buscar_mostrar();
    }//GEN-LAST:event_JTbusquedaActionPerformed

    private void BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarActionPerformed
        buscar_mostrar();
    }//GEN-LAST:event_BuscarActionPerformed

    private void BorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarActionPerformed
        JTbusqueda.setText("");
        JTbusqueda.requestFocus();
    }//GEN-LAST:event_BorrarActionPerformed

    private void JRBcodigolastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JRBcodigolastActionPerformed
        JTbusqueda.requestFocus();
    }//GEN-LAST:event_JRBcodigolastActionPerformed

    private void JRBdescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JRBdescripcionActionPerformed
        JTbusqueda.requestFocus();
    }//GEN-LAST:event_JRBdescripcionActionPerformed

    
    public void buscar_mostrar(){
        
        
        ArrayList lista_productos;
        String[] valor;
    
        if (JRBdescripcion.isSelected()) {
            lista_productos = BD_handler_imp.busqueda_improved(JTbusqueda.getText());
        }
        else lista_productos = BD_handler_imp.Busqueda_lastcod_only(JTbusqueda.getText());
        
        DefaultTableModel modelo = (DefaultTableModel) JT_result.getModel();
        modelo.setRowCount(lista_productos.size());
        
        
        for (int i = 0; i < lista_productos.size(); i++) {

            valor = (String[]) lista_productos.get(i);

            for (int k = 0; k < valor.length; k++) {

                JT_result.setValueAt(valor[k], i, k);

            }

        }

    }

    public static void main(String args[]) {
      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFsearch_V2().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Borrar;
    private javax.swing.JButton Buscar;
    private javax.swing.JLabel JLtipbusqueda;
    private javax.swing.JLabel JLtitulo;
    private javax.swing.JRadioButton JRBcodigolast;
    private javax.swing.JRadioButton JRBdescripcion;
    private javax.swing.ButtonGroup JRbutton_group;
    private javax.swing.JTable JT_result;
    private javax.swing.JTextField JTbusqueda;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
