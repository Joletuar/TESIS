package ventanas;

import funciones.ConexionMQTT;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Johan Tuarez
 */
public class PanelControl extends javax.swing.JFrame {

    public static ConexionMQTT mqtt;

    public String url_host = Login.url_host;

    public PanelControl() {

        initComponents();

        this.setTitle("PANEL DE CONTROL");
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        PanelControl.mqtt = new ConexionMQTT(this, url_host);

    }

    public void cerrar() {

        Object[] opciones = {"ACEPTAR", "CANCELAR"};
        int eleccion = JOptionPane.showOptionDialog(this, "¿ESTÁ SEGURO DE SALIR?", "Mensaje de Confirmacion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, "ACEPTAR");
        if (eleccion == JOptionPane.YES_OPTION) {
            try {
                Login.cn.close();
            } catch (SQLException ex) {
                System.out.println("ERROR AL CERRAR LA CONEXIÓN CON LA BASE DE DATOS");
            } finally {
                System.exit(0);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        label_titulo = new javax.swing.JLabel();
        boton_registrar_usuario = new javax.swing.JButton();
        boton_ver_registros = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        boton_reportes = new javax.swing.JButton();
        txt_estado_conexion = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        indicador = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                Cerrar(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));

        label_titulo.setBackground(new java.awt.Color(255, 255, 255));
        label_titulo.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        label_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_titulo.setText("PANEL PRINCIPAL");

        boton_registrar_usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/editar.png"))); // NOI18N
        boton_registrar_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_registrar_usuarioActionPerformed(evt);
            }
        });

        boton_ver_registros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/informacionUser.png"))); // NOI18N
        boton_ver_registros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_ver_registrosActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel1.setText("Añadir Datos");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel2.setText("Registros de Asistencia");

        boton_reportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/impresora.png"))); // NOI18N
        boton_reportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_reportesActionPerformed(evt);
            }
        });

        txt_estado_conexion.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        txt_estado_conexion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel4.setText("Datos Cuantitativos");

        indicador.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boton_registrar_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel1)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(boton_ver_registros, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(boton_reportes, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel2)
                                .addGap(52, 52, 52)
                                .addComponent(jLabel4))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(label_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(indicador, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(105, 105, 105)
                        .addComponent(txt_estado_conexion, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(label_titulo)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(boton_registrar_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boton_ver_registros, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boton_reportes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(indicador, javax.swing.GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE)
                    .addComponent(txt_estado_conexion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boton_registrar_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_registrar_usuarioActionPerformed

        new PanelSecundario().setVisible(true);

    }//GEN-LAST:event_boton_registrar_usuarioActionPerformed

    private void boton_ver_registrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_ver_registrosActionPerformed

        new RegistrosAsisencias().setVisible(true);

    }//GEN-LAST:event_boton_ver_registrosActionPerformed

    private void Cerrar(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_Cerrar

        cerrar();

    }//GEN-LAST:event_Cerrar

    private void boton_reportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_reportesActionPerformed

        new Reportes().setVisible(true);

    }//GEN-LAST:event_boton_reportesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_registrar_usuario;
    private javax.swing.JButton boton_reportes;
    private javax.swing.JButton boton_ver_registros;
    public javax.swing.JLabel indicador;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label_titulo;
    public javax.swing.JLabel txt_estado_conexion;
    // End of variables declaration//GEN-END:variables
}
