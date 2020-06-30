
package bd_manager_v0;

import java.awt.Font;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;


/**
 *
 * @author esteban
 */



public class BD_manager_principal extends javax.swing.JFrame {
 
    validador mival = new validador();

    String codigo = "", costo = "", descripcion = "", pvpublico = "", pvtienda = "", familia, iva;


   // se establecen algunos aprametros que en la gui de swing no se puede hacer facilmente
    public BD_manager_principal() {
        initComponents();
        
        
        //se pone la fecha actual en la etiqueta correspondiente en la pestaña de codificacion
        fecha.setText(mival.obt_fecha());
        
        //se establece el tiepo de fuente y tamaño de la cabecera de la tabla para mejorar
        //visualizacion
        Font fo = new Font("Arial", 1, 21);
        JTableHeader head = JTresults_mod.getTableHeader();
        head.setFont(fo);
        JTresults_mod.setTableHeader(head);

        // se agregan los radio buttons a un grupo para hacerlos mutaumente excluyentes
        //y obtener su seleccion
        JRbutton_group.add(JRcodigo_exacto);
        JRbutton_group.add(JRultimos_codigo);
        JRbutton_group.add(JRdescripcion);
        
        JRdescripcion.setSelected(true);
    }

 
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JRbutton_group = new javax.swing.ButtonGroup();
        JDialog_modificacion = new javax.swing.JDialog();
        jLabel3 = new javax.swing.JLabel();
        JBmodificar_mod = new javax.swing.JButton();
        JBcancelar_mod = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        JCfamilia_mod = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        JTpvpublico_mod = new javax.swing.JTextField();
        JTdescripcion_mod = new javax.swing.JTextField();
        JLpvpublico_mod = new javax.swing.JLabel();
        JLdescripcion_mod = new javax.swing.JLabel();
        JTpvtienda_mod = new javax.swing.JTextField();
        JTcodigo_mod = new javax.swing.JTextField();
        JTcosto_mod = new javax.swing.JTextField();
        JLcodigo_mod = new javax.swing.JLabel();
        JLcosto_mod = new javax.swing.JLabel();
        JLpvtienda_mod = new javax.swing.JLabel();
        JTiva_mod = new javax.swing.JTextField();
        JLiva_mod = new javax.swing.JLabel();
        JLfamilia_mod = new javax.swing.JLabel();
        JLfecha_mod = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        contenedor_pestanas = new javax.swing.JTabbedPane();
        pes_buscar = new javax.swing.JPanel();
        pes_nuevo = new javax.swing.JPanel();
        JTdescripcion = new javax.swing.JTextField();
        JLdescripcion = new javax.swing.JLabel();
        fecha = new javax.swing.JLabel();
        JSfamilia = new javax.swing.JComboBox();
        JLfamilia = new javax.swing.JLabel();
        title_INP = new javax.swing.JLabel();
        JTcodigo = new javax.swing.JTextField();
        JLcosto = new javax.swing.JLabel();
        JTiva = new javax.swing.JTextField();
        JLpvpublico = new javax.swing.JLabel();
        JTpvpublico = new javax.swing.JTextField();
        JLpvtienda = new javax.swing.JLabel();
        JTpvtienda = new javax.swing.JTextField();
        JLcodigo = new javax.swing.JLabel();
        JLfecha = new javax.swing.JLabel();
        JTcosto = new javax.swing.JTextField();
        JLpercent = new javax.swing.JLabel();
        JLiva = new javax.swing.JLabel();
        JBingresar = new javax.swing.JButton();
        JBborrar = new javax.swing.JButton();
        pes_modificar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JTresults_mod = new javax.swing.JTable();
        JBbuscar_mod = new javax.swing.JButton();
        JTbuscar_mod = new javax.swing.JTextField();
        JRcodigo_exacto = new javax.swing.JRadioButton();
        JRdescripcion = new javax.swing.JRadioButton();
        JRultimos_codigo = new javax.swing.JRadioButton();
        JBmodificar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        JDialog_modificacion.setAlwaysOnTop(true);
        JDialog_modificacion.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        JDialog_modificacion.setMinimumSize(new java.awt.Dimension(500, 500));
        JDialog_modificacion.setModal(true);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("------");

        JBmodificar_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        JBmodificar_mod.setText("Modificar");

        JBcancelar_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        JBcancelar_mod.setText("Cancelar");
        JBcancelar_mod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBcancelar_modActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("codigo:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setText("descripcion:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setText("costo");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("iva");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel13.setText("pv tienda");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel15.setText("pv publico");

        JCfamilia_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        JCfamilia_mod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel17.setText("Valor Actual:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel18.setText("Valor Actual:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel19.setText("Valor Actual:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel20.setText("Valor Actual:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel21.setText("Valor Actual:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel22.setText("Valor Actual:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel23.setText("Valor Actual:");

        JTpvpublico_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        JTdescripcion_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        JLpvpublico_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        JLpvpublico_mod.setText("---------");

        JLdescripcion_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        JLdescripcion_mod.setText("---------");

        JTpvtienda_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        JTcodigo_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        JTcosto_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        JLcodigo_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        JLcodigo_mod.setText("---------");

        JLcosto_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        JLcosto_mod.setText("---------");

        JLpvtienda_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        JLpvtienda_mod.setText("---------");

        JTiva_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        JLiva_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        JLiva_mod.setText("---------");

        JLfamilia_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        JLfamilia_mod.setText("---------");

        JLfecha_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        JLfecha_mod.setText("------------");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel5.setText("Modificando:");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel25.setText("Ultima Modificacion:");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel26.setText("Familia");

        javax.swing.GroupLayout JDialog_modificacionLayout = new javax.swing.GroupLayout(JDialog_modificacion.getContentPane());
        JDialog_modificacion.getContentPane().setLayout(JDialog_modificacionLayout);
        JDialog_modificacionLayout.setHorizontalGroup(
            JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                        .addGap(263, 263, 263)
                        .addComponent(JBmodificar_mod))
                    .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(JLfecha_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                        .addGap(1016, 1016, 1016)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JCfamilia_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(263, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDialog_modificacionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JLdescripcion_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(JBcancelar_mod))
                    .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                        .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel4))
                                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(JLpvpublico_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(JTpvpublico_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(JLcodigo_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(JTcodigo_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(163, 163, 163)
                                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JDialog_modificacionLayout.createSequentialGroup()
                                        .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel23))
                                        .addGap(18, 18, 18)
                                        .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(JTpvtienda_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(JLpvtienda_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JDialog_modificacionLayout.createSequentialGroup()
                                        .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel20)
                                            .addComponent(jLabel10))
                                        .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(JTcosto_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JDialog_modificacionLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(JLcosto_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JDialog_modificacionLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(JTdescripcion_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(JLfamilia_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JLiva_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(JTiva_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
        );
        JDialog_modificacionLayout.setVerticalGroup(
            JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel25)
                    .addComponent(JLfecha_mod))
                .addGap(41, 41, 41)
                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(JTdescripcion_mod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(JLdescripcion_mod)
                        .addComponent(JCfamilia_mod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                        .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(JTpvtienda_mod)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel23)
                                    .addComponent(JLpvtienda_mod))
                                .addGap(46, 46, 46)
                                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(JTcosto_mod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(JLfamilia_mod))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(JTiva_mod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel22)
                                    .addComponent(JLiva_mod))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(JLcosto_mod))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 165, Short.MAX_VALUE)
                        .addComponent(JBcancelar_mod))
                    .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                        .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JDialog_modificacionLayout.createSequentialGroup()
                                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(JTcodigo_mod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(JLcodigo_mod))
                                .addGap(46, 46, 46)
                                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(JTpvpublico_mod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(JDialog_modificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel21)
                                    .addComponent(JLpvpublico_mod)))
                            .addComponent(JBmodificar_mod))
                        .addContainerGap())))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("BD_manager_frame"); // NOI18N

        contenedor_pestanas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        contenedor_pestanas.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        pes_buscar.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        javax.swing.GroupLayout pes_buscarLayout = new javax.swing.GroupLayout(pes_buscar);
        pes_buscar.setLayout(pes_buscarLayout);
        pes_buscarLayout.setHorizontalGroup(
            pes_buscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1345, Short.MAX_VALUE)
        );
        pes_buscarLayout.setVerticalGroup(
            pes_buscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 617, Short.MAX_VALUE)
        );

        contenedor_pestanas.addTab("Buscar Producto", pes_buscar);

        JTdescripcion.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        JTdescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTdescripcionActionPerformed(evt);
            }
        });

        JLdescripcion.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        JLdescripcion.setText("Descripcion");

        fecha.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        fecha.setText("--------------------");

        JSfamilia.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        JSfamilia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aseo", "Licores", "Salsamentaria", "Abarrote", "Remates" }));
        JSfamilia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JSfamiliaActionPerformed(evt);
            }
        });

        JLfamilia.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        JLfamilia.setText("Familia o grupo");

        title_INP.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        title_INP.setText("CODIFICACION DE PRODUCTOS");

        JTcodigo.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        JTcodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTcodigoActionPerformed(evt);
            }
        });

        JLcosto.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        JLcosto.setText("Costo");

        JTiva.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        JTiva.setText("16");
        JTiva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTivaActionPerformed(evt);
            }
        });

        JLpvpublico.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        JLpvpublico.setText("Precio Venta Publico");

        JTpvpublico.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        JTpvpublico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTpvpublicoActionPerformed(evt);
            }
        });

        JLpvtienda.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        JLpvtienda.setText("Precio Venta Tienda ");

        JTpvtienda.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        JTpvtienda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTpvtiendaActionPerformed(evt);
            }
        });

        JLcodigo.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        JLcodigo.setText("Codigo De Barras");

        JLfecha.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        JLfecha.setText("Fecha Del Sistema:");

        JTcosto.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        JTcosto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTcostoActionPerformed(evt);
            }
        });

        JLpercent.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        JLpercent.setText("%");

        JLiva.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        JLiva.setText("Porcentje De Iva:");

        JBingresar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        JBingresar.setText("Ingresar Producto");
        JBingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBingresarActionPerformed(evt);
            }
        });

        JBborrar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        JBborrar.setText("Borrar Campos");
        JBborrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBborrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pes_nuevoLayout = new javax.swing.GroupLayout(pes_nuevo);
        pes_nuevo.setLayout(pes_nuevoLayout);
        pes_nuevoLayout.setHorizontalGroup(
            pes_nuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pes_nuevoLayout.createSequentialGroup()
                .addGroup(pes_nuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pes_nuevoLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(pes_nuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pes_nuevoLayout.createSequentialGroup()
                                .addComponent(JLcosto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JTcosto, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(JLpvpublico)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JTpvpublico, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(JLpvtienda)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JTpvtienda, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pes_nuevoLayout.createSequentialGroup()
                                .addComponent(JLdescripcion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JTdescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pes_nuevoLayout.createSequentialGroup()
                        .addGap(363, 363, 363)
                        .addComponent(title_INP, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pes_nuevoLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(pes_nuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pes_nuevoLayout.createSequentialGroup()
                                .addComponent(JLfecha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fecha))
                            .addGroup(pes_nuevoLayout.createSequentialGroup()
                                .addComponent(JLcodigo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(JTcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JLfamilia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JSfamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pes_nuevoLayout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(JLiva)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTiva, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JLpercent)
                        .addGap(174, 174, 174)
                        .addComponent(JBingresar)
                        .addGap(61, 61, 61)
                        .addComponent(JBborrar, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(148, Short.MAX_VALUE))
        );
        pes_nuevoLayout.setVerticalGroup(
            pes_nuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pes_nuevoLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(title_INP, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(pes_nuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JLfecha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(pes_nuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JTcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JLfamilia)
                    .addComponent(JSfamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JLcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(pes_nuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JTdescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JLdescripcion))
                .addGap(37, 37, 37)
                .addGroup(pes_nuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JLcosto)
                    .addComponent(JLpvpublico)
                    .addComponent(JTpvpublico, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JLpvtienda)
                    .addComponent(JTpvtienda, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTcosto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(129, 129, 129)
                .addGroup(pes_nuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JTiva, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JLpercent)
                    .addComponent(JLiva)
                    .addComponent(JBingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JBborrar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        contenedor_pestanas.addTab("Introducir Nuevo Producto", pes_nuevo);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Modificacion o Eliminacion De Productos");

        JTresults_mod.setBackground(new java.awt.Color(204, 204, 255));
        JTresults_mod.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        JTresults_mod.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        JTresults_mod.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Descripcion", "Costo", "PV publico", "PV tienda", "Iva", "Familia", "Update"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        JTresults_mod.setRowHeight(37);
        JTresults_mod.setRowMargin(2);
        JTresults_mod.setSelectionBackground(new java.awt.Color(255, 204, 204));
        JTresults_mod.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(JTresults_mod);

        JBbuscar_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        JBbuscar_mod.setText("Buscar");
        JBbuscar_mod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBbuscar_modActionPerformed(evt);
            }
        });

        JTbuscar_mod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        JRcodigo_exacto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        JRcodigo_exacto.setText("Codigo De Barras Exacto");

        JRdescripcion.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        JRdescripcion.setText("Descripcion");

        JRultimos_codigo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        JRultimos_codigo.setText("Ultimos Numeros Del Codigo");
        JRultimos_codigo.setActionCommand("Ultimos Numeros \\n Del Codigo De Barras");

        JBmodificar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        JBmodificar.setText("Modificar");
        JBmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBmodificarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Busqueda por:");

        javax.swing.GroupLayout pes_modificarLayout = new javax.swing.GroupLayout(pes_modificar);
        pes_modificar.setLayout(pes_modificarLayout);
        pes_modificarLayout.setHorizontalGroup(
            pes_modificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pes_modificarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(297, 297, 297))
            .addGroup(pes_modificarLayout.createSequentialGroup()
                .addGroup(pes_modificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pes_modificarLayout.createSequentialGroup()
                        .addGap(554, 554, 554)
                        .addComponent(JBbuscar_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(JBmodificar, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pes_modificarLayout.createSequentialGroup()
                        .addGap(469, 469, 469)
                        .addComponent(JTbuscar_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(pes_modificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JRultimos_codigo)
                    .addComponent(JRcodigo_exacto, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JRdescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(85, Short.MAX_VALUE))
            .addGroup(pes_modificarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        pes_modificarLayout.setVerticalGroup(
            pes_modificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pes_modificarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(pes_modificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pes_modificarLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JRcodigo_exacto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JRdescripcion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JRultimos_codigo))
                    .addGroup(pes_modificarLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(JTbuscar_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addGroup(pes_modificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JBmodificar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JBbuscar_mod, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        contenedor_pestanas.addTab("Modificar ó Eliminar Producto ", pes_modificar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedor_pestanas, javax.swing.GroupLayout.PREFERRED_SIZE, 1352, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedor_pestanas)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    
    
//se borran los campos del formulario de codificacion
    private void JBborrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBborrarActionPerformed

        JTcodigo.setText("");
        JTcosto.setText("");
        JTdescripcion.setText("");
        JTpvpublico.setText("");
        JTpvtienda.setText("");

    }//GEN-LAST:event_JBborrarActionPerformed





//en este metodo se invocan los algoritmos para codificar un producto   
    private void JBingresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBingresarActionPerformed

        codigo = JTcodigo.getText();
        costo = JTcosto.getText();
        descripcion = JTdescripcion.getText();
        pvpublico = JTpvpublico.getText();
        pvtienda = JTpvtienda.getText();
        familia = JSfamilia.getSelectedItem().toString();
        iva = JTiva.getText();

        boolean b = mival.datos_para_ingreso_validos(codigo, descripcion, costo, pvpublico, pvtienda, iva);

        if (b) {

            fecha.setText(mival.obt_fecha());
            BD_handler.Codificar(codigo, descripcion, costo, iva, pvpublico, pvtienda, familia);

        }


    }//GEN-LAST:event_JBingresarActionPerformed

    
    
    
    
    
// manejo de focus cursor en el formulario de ingreso de productos    
    
    private void JTcodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTcodigoActionPerformed
        JTdescripcion.requestFocus();
    }//GEN-LAST:event_JTcodigoActionPerformed

    private void JTdescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTdescripcionActionPerformed
        JTcosto.requestFocus();
    }//GEN-LAST:event_JTdescripcionActionPerformed

    private void JTcostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTcostoActionPerformed
        JTiva.requestFocus();
    }//GEN-LAST:event_JTcostoActionPerformed

    private void JTpvpublicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTpvpublicoActionPerformed
        JTpvtienda.requestFocus();
    }//GEN-LAST:event_JTpvpublicoActionPerformed

    private void JTivaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTivaActionPerformed
        JTpvpublico.requestFocus();
    }//GEN-LAST:event_JTivaActionPerformed

    private void JTpvtiendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTpvtiendaActionPerformed
        JSfamilia.requestFocus();
    }//GEN-LAST:event_JTpvtiendaActionPerformed

    private void JSfamiliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JSfamiliaActionPerformed
        
    }//GEN-LAST:event_JSfamiliaActionPerformed

    private void JBbuscar_modActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBbuscar_modActionPerformed
        
        
        String busqueda=JTbuscar_mod.getText();
        int tipo = Get_search_type_mod_stage();
        ArrayList lista_productos = BD_handler.Busqueda_mod(busqueda,tipo);
        
        
        DefaultTableModel modelo = (DefaultTableModel) JTresults_mod.getModel();
        modelo.setRowCount(lista_productos.size());
        
        String[] valor;
   
        for(int i =0; i<lista_productos.size() ; i++){
            
            valor = (String[]) lista_productos.get(i);
            
            for(int k=0; k<=7 ; k++){  
                
                JTresults_mod.setValueAt(valor[k], i, k);
                
            }
            
            
        }

    }//GEN-LAST:event_JBbuscar_modActionPerformed

    private void JBmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBmodificarActionPerformed

        JDialog_modificacion.setVisible(true);
        
    }//GEN-LAST:event_JBmodificarActionPerformed

    private void JBcancelar_modActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBcancelar_modActionPerformed
        JDialog_modificacion.dispose();
    }//GEN-LAST:event_JBcancelar_modActionPerformed

    
    
    
    public int Get_search_type_mod_stage(){
        
        int busq=2;
        if(JRcodigo_exacto.isSelected()) busq=1;
        if(JRdescripcion.isSelected()) busq=2;
        if(JRultimos_codigo.isSelected()) busq=3;
        
        return busq;
    }
    
    
    
    
    
    
    
    public static void main(String args[]) {
   
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());

                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BD_manager_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BD_manager_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BD_manager_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BD_manager_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BD_manager_principal().setVisible(true);
            }
        });
    }
    
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JBborrar;
    private javax.swing.JButton JBbuscar_mod;
    private javax.swing.JButton JBcancelar_mod;
    private javax.swing.JButton JBingresar;
    private javax.swing.JButton JBmodificar;
    private javax.swing.JButton JBmodificar_mod;
    private javax.swing.JComboBox JCfamilia_mod;
    private javax.swing.JDialog JDialog_modificacion;
    private javax.swing.JLabel JLcodigo;
    private javax.swing.JLabel JLcodigo_mod;
    private javax.swing.JLabel JLcosto;
    private javax.swing.JLabel JLcosto_mod;
    private javax.swing.JLabel JLdescripcion;
    private javax.swing.JLabel JLdescripcion_mod;
    private javax.swing.JLabel JLfamilia;
    private javax.swing.JLabel JLfamilia_mod;
    private javax.swing.JLabel JLfecha;
    private javax.swing.JLabel JLfecha_mod;
    private javax.swing.JLabel JLiva;
    private javax.swing.JLabel JLiva_mod;
    private javax.swing.JLabel JLpercent;
    private javax.swing.JLabel JLpvpublico;
    private javax.swing.JLabel JLpvpublico_mod;
    private javax.swing.JLabel JLpvtienda;
    private javax.swing.JLabel JLpvtienda_mod;
    private javax.swing.ButtonGroup JRbutton_group;
    private javax.swing.JRadioButton JRcodigo_exacto;
    private javax.swing.JRadioButton JRdescripcion;
    private javax.swing.JRadioButton JRultimos_codigo;
    private javax.swing.JComboBox JSfamilia;
    private javax.swing.JTextField JTbuscar_mod;
    private javax.swing.JTextField JTcodigo;
    private javax.swing.JTextField JTcodigo_mod;
    private javax.swing.JTextField JTcosto;
    private javax.swing.JTextField JTcosto_mod;
    private javax.swing.JTextField JTdescripcion;
    private javax.swing.JTextField JTdescripcion_mod;
    private javax.swing.JTextField JTiva;
    private javax.swing.JTextField JTiva_mod;
    private javax.swing.JTextField JTpvpublico;
    private javax.swing.JTextField JTpvpublico_mod;
    private javax.swing.JTextField JTpvtienda;
    private javax.swing.JTextField JTpvtienda_mod;
    public javax.swing.JTable JTresults_mod;
    private javax.swing.JTabbedPane contenedor_pestanas;
    private javax.swing.JLabel fecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pes_buscar;
    private javax.swing.JPanel pes_modificar;
    private javax.swing.JPanel pes_nuevo;
    private javax.swing.JLabel title_INP;
    // End of variables declaration//GEN-END:variables
}


