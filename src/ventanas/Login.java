package ventanas;

import consultasBD.ActualizarBD;
import consultasBD.BuscarBD;
import consultasBD.InsertarBD;
import funciones.ConexionBD;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import objetos.Administrador;
import java.sql.*;

/**
 *
 * @author Johan Tuarez
 */
public class Login extends javax.swing.JFrame implements Runnable {

    public static ConexionBD conexion;

    //Componentes de consultas hacia la base de datos
    public static Connection cn; //Conexión a la BD
    public static Administrador admin; //Objeto que contiene los datos del administrador
    public static BuscarBD bs; //Objeto que permite ejecutar busquedas en la BD
    public static ActualizarBD ac; //Objeto que permite actualizar datos en la BD
    public static InsertarBD in; //Objeto que permite ingresar datos en la BD

    public Configuraciones ventana;
    public static String url_host;

    public Login() {

        initComponents();

        //Configuraciones iniciales del frame 
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("LOGIN");
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        conexion = new ConexionBD();
        conexion.Conectar();
        Login.cn = conexion.getConexion();

        //Inicialización de los objetos
        Login.bs = new BuscarBD(Login.cn);
        Login.in = new InsertarBD(Login.cn);
        Login.ac = new ActualizarBD(Login.cn);

        this.ventana = new Configuraciones(this);

    }

    @Override
    public void run() {

        this.setVisible(true);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        label_usuario = new javax.swing.JLabel();
        label_contrasena = new javax.swing.JLabel();
        txt_contrasena = new javax.swing.JPasswordField();
        txt_usuario = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        boton_ingresar = new javax.swing.JButton();
        boton_confi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(400, 300));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setAutoscrolls(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        label_usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/user_icon.png"))); // NOI18N
        label_usuario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));

        label_contrasena.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pass_icon.png"))); // NOI18N
        label_contrasena.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));

        txt_contrasena.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_contrasena.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txt_usuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_usuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(label_contrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(txt_contrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_contrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txt_contrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setText("Bienvenido");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Sistema de Control de Asistencias");

        boton_ingresar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        boton_ingresar.setText("INGRESAR");
        boton_ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_ingresarActionPerformed(evt);
            }
        });

        boton_confi.setBackground(new java.awt.Color(60, 63, 65));
        boton_confi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/configuraciones.png"))); // NOI18N
        boton_confi.setBorder(null);
        boton_confi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_confiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 17, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(15, 15, 15))
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(boton_confi, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boton_ingresar)))
                .addGap(25, 25, 25))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(boton_ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(boton_confi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boton_ingresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_ingresarActionPerformed

        //String de los datos ingresados por el usario
        String usuario, contrasena;
        usuario = txt_usuario.getText().trim().toUpperCase();
        contrasena = txt_contrasena.getText().trim().toUpperCase();

        //Comprobación de que los JTextFields no esten vacíos
        if (usuario.equals("") || contrasena.equals("")) {

            JOptionPane.showMessageDialog(null, "DEBE LLENAR TODOS LOS CAMPOS");

        } else {

            //Verificación de que el usuario se encuentre registro en la tabla Administradores
            String parametros = "Usuario = '" + usuario + "' AND Contrasena = '" + contrasena + "'";

            admin = Login.bs.ConsultarAdmin(parametros);

            if (admin != null) {

                //En caso de estar registrado, se abrirá mostrará un nuevo frame
                new PanelControl().setVisible(true);
                this.dispose();

            } else {

                JOptionPane.showMessageDialog(null, "CREDENCIALES INCORRECTAS");
                Limpiartxt();

            }
        }
    }//GEN-LAST:event_boton_ingresarActionPerformed

    private void boton_confiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_confiActionPerformed

        this.ventana.setVisible(true);

    }//GEN-LAST:event_boton_confiActionPerformed

    private void Limpiartxt() {

        //Método encargado de limpiar los cuadros de textos
        this.txt_usuario.setText("");
        this.txt_contrasena.setText("");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        Login login = new Login();
        Thread hilo_1 = new Thread(login);
        hilo_1.start();

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_confi;
    private javax.swing.JButton boton_ingresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel label_contrasena;
    private javax.swing.JLabel label_usuario;
    private javax.swing.JPasswordField txt_contrasena;
    private javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables
}
