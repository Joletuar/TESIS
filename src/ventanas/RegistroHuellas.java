package ventanas;

import funciones.ConexionArduino;
import java.awt.HeadlessException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import objetos.Cargo;
import objetos.Empleado;
import objetos.Jornada;
import objetos.UOperativa;

/**
 *
 * @author Johan Tuarez
 */
public class RegistroHuellas extends javax.swing.JFrame {

    private final ConexionArduino ino;
    public String id_elegido;
    private final ArrayList<Integer> ids_disponibles = new ArrayList<>();
    private final String[] textos = Empleados.valores;

    private UOperativa unidad;

    public boolean bandera = false;

    private Empleados ventana;

    public RegistroHuellas(Empleados ventana) {

        initComponents();

        this.setTitle("REGISTRO DE HUELLA");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.boton_registrar.setEnabled(false);
        this.cmb_id.setEnabled(false);

        this.txt_procesando.setEnabled(false);
        this.txt_animacion.setVisible(false);

        this.ventana = ventana;
        this.ino = new ConexionArduino(this, ventana);

        getIDs();
        CargarPuertos();
        GenerarIDs();

    }

    public void CargarPuertos() {

        DefaultComboBoxModel modelo = new DefaultComboBoxModel();

        for (String puerto : this.ino.getPuertos()) {

            modelo.addElement(puerto);

        }

        this.cmb_puertos.setModel(modelo);

    }

    public void getIDs() {

        ArrayList<Empleado> empleados = Login.bs.getEmpleados();

        String parametros_1 = "Nombre_Unidad = '" + textos[0] + "'";
        this.unidad = Login.bs.ConsultarUOperativa(parametros_1);

        for (Empleado empleado : empleados) {

            if (empleado.id_unidad == this.unidad.id_unidad) {

                this.ids_disponibles.add(empleado.id_empleado);

            }

        }

    }

    public boolean verificarIDs(int id) {

        if (!this.ids_disponibles.isEmpty()) {

            if (this.ids_disponibles.contains(id)) {

                return true;

            } else {

                return false;

            }

        } else {

            return false;

        }

    }

    public void GenerarIDs() {

        DefaultComboBoxModel modelo = new DefaultComboBoxModel();

        for (int i = 1; i < 128; i++) {

            if (i < 10) {

                if (!verificarIDs(i)) {

                    modelo.addElement("00" + String.valueOf(i));
                }

            } else if (i >= 10 && i < 100) {

                if (!verificarIDs(i)) {

                    modelo.addElement("0" + String.valueOf(i));
                }

            } else {

                if (!verificarIDs(i)) {

                    modelo.addElement(String.valueOf(i));
                }

            }

        }

        this.cmb_id.setModel(modelo);

    }

    public int convertirID() {

        int id_seleccionado = Integer.parseInt(this.cmb_id.getSelectedItem().toString());

        return id_seleccionado;

    }

    public void RegistrarEmpleado() {

        try {

            Login.in.InsertarJornada(textos[7] + ":" + textos[8] + ":00", textos[9] + ":" + textos[10] + ":00", textos[2]);

            String parametros_2 = "Cargo = '" + textos[1] + "'";
            String parametros_3 = "Cedula = '" + textos[2] + "'";

            Cargo cargo = Login.bs.ConsultarCargo(parametros_2);
            Jornada jornada = Login.bs.ConsultarJornada(parametros_3);

            Login.in.InsertarEmpleado(convertirID(), unidad.id_unidad, cargo.id_cargo, jornada.id_jornada,
                    textos[2], textos[3], textos[4], textos[5], textos[6]);

        } catch (Exception e) {

            System.out.println("ERROR AL REGISTRAR");

        }

    }

    public void limpiarCMB() {

        this.cmb_baudios.setSelectedIndex(0);
        this.cmb_puertos.setSelectedIndex(0);

    }

    public void Ejecutar() {

        if (bandera) {

            try {

                RegistrarEmpleado();

                JOptionPane.showMessageDialog(null, "REGISTRO COMPLETADO");

                this.ino.Desconectar();
                this.dispose();

            } catch (HeadlessException e) {

                JOptionPane.showMessageDialog(null, "HUBO UN PROBLEMA AL REGISTRAR EL EMPLEADO");

            }

        } else {

            JOptionPane.showMessageDialog(null, "HUBO UN PROBLEMA AL REGISTRAR LA HUELLA");

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cmb_puertos = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        boton_conectar_sensor = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cmb_baudios = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmb_id = new javax.swing.JComboBox<>();
        boton_registrar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        txt_estado = new javax.swing.JLabel();
        txt_procesando = new javax.swing.JLabel();
        txt_animacion = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Parámetros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        cmb_puertos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Puerto:");

        boton_conectar_sensor.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        boton_conectar_sensor.setText("CONECTAR");
        boton_conectar_sensor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_conectar_sensorActionPerformed(evt);
            }
        });

        jLabel4.setText("Baudios:");

        cmb_baudios.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmb_baudios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "9600 baudio", "115200 baudio" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmb_puertos, javax.swing.GroupLayout.Alignment.TRAILING, 0, 158, Short.MAX_VALUE)
                    .addComponent(boton_conectar_sensor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cmb_baudios, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmb_puertos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(12, 12, 12)
                .addComponent(cmb_baudios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boton_conectar_sensor, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REGISTRO DE HUELLA DACTILAR");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Opciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("ID:");

        cmb_id.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        boton_registrar.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        boton_registrar.setText("REGISTRAR HUELLA");
        boton_registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_registrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmb_id, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(boton_registrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmb_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(boton_registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Estado de Conexión", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        txt_estado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_estado.setForeground(new java.awt.Color(255, 0, 0));
        txt_estado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_estado.setText("CONEXIÓN NO ESTABLECIDA");
        txt_estado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txt_procesando.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_procesando.setForeground(new java.awt.Color(0, 0, 204));
        txt_procesando.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_procesando.setText("PROCESANDO...");
        txt_procesando.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txt_animacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ajax-loader.gif"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txt_procesando, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_animacion, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                .addGap(18, 18, 18))
            .addComponent(txt_estado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(txt_estado, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_procesando)
                    .addComponent(txt_animacion))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(12, 12, 12))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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

    private void boton_conectar_sensorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_conectar_sensorActionPerformed

        try {

            if (this.boton_conectar_sensor.getText().trim().equals("CONECTAR")) {

                String puerto = this.cmb_puertos.getSelectedItem().toString();
                int baudios = Integer.parseInt(this.cmb_baudios.getSelectedItem().toString().split(" ")[0]);
                this.ino.Conectar(puerto, baudios);

                if (this.ino.bandera) {

                    this.boton_registrar.setEnabled(true);
                    this.cmb_id.setEnabled(true);
                    this.cmb_baudios.setEnabled(false);
                    this.cmb_puertos.setEnabled(false);
                    this.txt_estado.setText("CONEXIÓN ESTABLECIDA");
                    this.boton_conectar_sensor.setText("DESCONECTAR");

                }

            } else if (this.boton_conectar_sensor.getText().trim().equals("DESCONECTAR")) {

                CargarPuertos();
                this.txt_estado.setText("CONEXIÓN NO ESTABLECIDA");
                this.boton_conectar_sensor.setText("CONECTAR");
                this.ino.Desconectar();
                this.boton_registrar.setEnabled(false);
                this.cmb_id.setEnabled(false);
                this.cmb_puertos.setEnabled(true);
                this.cmb_baudios.setEnabled(true);

                limpiarCMB();

            }

        } catch (NumberFormatException e) {

            System.out.println("ERROR DE TIPO: " + e);
            this.txt_estado.setText("CONEXIÓN NO ESTABLECIDA");

        }


    }//GEN-LAST:event_boton_conectar_sensorActionPerformed

    private void boton_registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_registrarActionPerformed

        String id = this.cmb_id.getSelectedItem().toString().trim();

        this.ino.enviarDatos(id + "-1"); //Enviar datos comunicacion serial
        
    }//GEN-LAST:event_boton_registrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_conectar_sensor;
    public javax.swing.JButton boton_registrar;
    private javax.swing.JComboBox<String> cmb_baudios;
    public javax.swing.JComboBox<String> cmb_id;
    private javax.swing.JComboBox<String> cmb_puertos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    public javax.swing.JLabel txt_animacion;
    public javax.swing.JLabel txt_estado;
    public javax.swing.JLabel txt_procesando;
    // End of variables declaration//GEN-END:variables
}
