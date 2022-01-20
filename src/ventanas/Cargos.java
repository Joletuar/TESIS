package ventanas;

import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import objetos.Cargo;

/**
 *
 * @author Johan Tuarez
 */

public class Cargos extends javax.swing.JFrame {

    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> modelo_ordenado;
    private boolean bandera;
    private int fila_seleccionada;
    private ArrayList<Cargo> cargos;

    public Cargos() {

        initComponents();

        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("CARGOS");

        this.lista_cargos.getTableHeader().setReorderingAllowed(false);

        this.boton_actualizar_cargo.setEnabled(false);
        this.boton_lista_completa.setEnabled(false);

        AlinearColumnas();
        MostrarTabla();

    }

    public void AlinearColumnas() {

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();

        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        this.lista_cargos.getColumnModel().getColumn(0).setCellRenderer(tcr);
        this.lista_cargos.getColumnModel().getColumn(1).setCellRenderer(tcr);
        this.lista_cargos.getColumnModel().getColumn(2).setCellRenderer(tcr);

        ((DefaultTableCellRenderer) lista_cargos.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

    }

    public void MostrarTabla() {

        this.modelo = (DefaultTableModel) this.lista_cargos.getModel();
        this.modelo.setRowCount(0);

        this.cargos = Login.bs.getCargos();

        Object[] fila = new Object[3];

        if (!this.cargos.isEmpty()) {

            for (Cargo cargo : this.cargos) {

                fila[0] = cargo.id_cargo;
                fila[1] = cargo.nombre_cargo;
                fila[2] = cargo.estatus;

                this.modelo.addRow(fila);

            }

        } else {

            JOptionPane.showMessageDialog(null, "NO HAY CARGOS REGISTRADOS");

        }

        this.lista_cargos.setModel(modelo);
        this.modelo_ordenado = new TableRowSorter<>(this.modelo);
        this.lista_cargos.setRowSorter(this.modelo_ordenado);

    }

    private void limpiarTxT() {

        this.txt_cargo.setText("");
        this.cmb_estado.setSelectedIndex(0);

    }

    public String[] getTxT() {

        String cargo = "", estado = "";
        String[] textos = new String[2];

        cargo = txt_cargo.getText().trim().toUpperCase();
        estado = this.cmb_estado.getSelectedItem().toString().trim().toUpperCase();

        textos[0] = cargo;
        textos[1] = estado;

        return textos;

    }

    public void Filtrar() {

        String[] textos = getTxT();
        LinkedList<RowFilter<Object, Object>> lista = new LinkedList<>();

        if (textos[0].equalsIgnoreCase("") && textos[1].equalsIgnoreCase("---SELECCIONAR---")) {

            JOptionPane.showMessageDialog(null, "INGRESE DATOS EN ALGÚN CAMPO");

        } else {

            System.out.println("FILTRO REALIZADO");

            if (!textos[0].equalsIgnoreCase("")) {

                lista.add(RowFilter.regexFilter(textos[0], 1));
            }

            if (!textos[1].equalsIgnoreCase("---SELECCIONAR---")) {

                lista.add(RowFilter.regexFilter(textos[1], 2));
            }

            RowFilter filtroAnd = RowFilter.andFilter(lista);
            this.modelo_ordenado.setRowFilter(filtroAnd);
            this.boton_anadir_cargo.setEnabled(false);
            this.boton_lista_completa.setEnabled(true);
        }

    }

    public boolean VerificarCargo(String nom_cargo) {

        String parametros = "Cargo = '" + nom_cargo + "'";
        Cargo temp_cargo = Login.bs.ConsultarCargo(parametros);

        if (temp_cargo == null) {

            return true;

        } else {

            return false;

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lista_cargos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_cargo = new javax.swing.JTextField();
        cmb_estado = new javax.swing.JComboBox<>();
        boton_anadir_cargo = new javax.swing.JButton();
        boton_actualizar_cargo = new javax.swing.JButton();
        boton_buscar = new javax.swing.JButton();
        boton_lista_completa = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Lista de Cargos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        lista_cargos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cargo", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lista_cargos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lista_cargosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lista_cargos);
        if (lista_cargos.getColumnModel().getColumnCount() > 0) {
            lista_cargos.getColumnModel().getColumn(0).setMinWidth(35);
            lista_cargos.getColumnModel().getColumn(0).setPreferredWidth(35);
            lista_cargos.getColumnModel().getColumn(0).setMaxWidth(35);
            lista_cargos.getColumnModel().getColumn(1).setResizable(false);
            lista_cargos.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Cargo:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Estatus:");

        txt_cargo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_cargo.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        cmb_estado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmb_estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---SELECCIONAR---", "ACTIVO", "INACTIVO" }));

        boton_anadir_cargo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        boton_anadir_cargo.setText("AÑADIR CARGO");
        boton_anadir_cargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_anadir_cargoActionPerformed(evt);
            }
        });

        boton_actualizar_cargo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        boton_actualizar_cargo.setText("ACTUALIZAR CARGO");
        boton_actualizar_cargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_actualizar_cargoActionPerformed(evt);
            }
        });

        boton_buscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        boton_buscar.setText("BUSCAR");
        boton_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_buscarActionPerformed(evt);
            }
        });

        boton_lista_completa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        boton_lista_completa.setText("LISTA COMPLETA");
        boton_lista_completa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_lista_completaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_cargo)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cmb_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boton_anadir_cargo, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_actualizar_cargo, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boton_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(boton_lista_completa, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cargo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmb_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_lista_completa, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(boton_anadir_cargo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(boton_actualizar_cargo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boton_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_buscarActionPerformed

        Filtrar();

    }//GEN-LAST:event_boton_buscarActionPerformed

    private void boton_lista_completaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_lista_completaActionPerformed

        this.boton_lista_completa.setEnabled(false);
        this.boton_anadir_cargo.setEnabled(true);
        MostrarTabla();
        limpiarTxT();

    }//GEN-LAST:event_boton_lista_completaActionPerformed

    private void boton_actualizar_cargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_actualizar_cargoActionPerformed

        if (bandera) {

            String[] textos = getTxT();

            if (textos[0].equals("") || textos[1].equals("---SELECCIONAR---")) {

                JOptionPane.showMessageDialog(null, "COMPLETE TODO LOS CAMPOS");

            } else {

                if (textos[1].equals("INACTIVO")) {

                    JOptionPane.showMessageDialog(null, "ATENCIÓN: VA A DESACTIVAR UN CARGO, POR LO CUAL YA NO PODRÁ REGISTRAR USUARIOS CON ESTA ESPECIALIDAD",
                            "NOTIFICACIÓN", 2);

                }

                Login.ac.ActualizarCargo((int) this.lista_cargos.getValueAt(this.fila_seleccionada, 0),
                        textos[0], textos[1]);

                JOptionPane.showMessageDialog(null, "ACTUALIZACIÓN CORRECTA");

                limpiarTxT();
                MostrarTabla();

                this.boton_actualizar_cargo.setEnabled(false);
                this.boton_anadir_cargo.setEnabled(true);
                this.boton_lista_completa.setEnabled(false);

            }
        }
    }//GEN-LAST:event_boton_actualizar_cargoActionPerformed

    private void lista_cargosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lista_cargosMouseClicked

        if (this.lista_cargos.getSelectedRow() < 0) {

            this.bandera = false;

        } else {

            this.fila_seleccionada = this.lista_cargos.getSelectedRow();
            this.bandera = true;
            this.boton_actualizar_cargo.setEnabled(true);
            this.boton_anadir_cargo.setEnabled(false);

            this.txt_cargo.setText(this.lista_cargos.getValueAt(this.fila_seleccionada, 1).toString().trim().toUpperCase());
            this.cmb_estado.setSelectedItem(this.lista_cargos.getValueAt(this.fila_seleccionada, 2).toString().trim().toUpperCase());

        }
    }//GEN-LAST:event_lista_cargosMouseClicked

    private void boton_anadir_cargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_anadir_cargoActionPerformed

        String[] textos = getTxT();

        if (textos[0].equals("") || textos[1].equals("---SELECCIONAR---")) {

            JOptionPane.showMessageDialog(null, "COMPLETE TODO LOS CAMPOS");

        } else {

            if (VerificarCargo(textos[0])) {

                Login.in.InsertarCargo(textos[0]);

                JOptionPane.showMessageDialog(null, "CARGO AGREGADO CORRECTAMENTE");

                limpiarTxT();
                MostrarTabla();

            } else {

                JOptionPane.showMessageDialog(null, "YA EXISTE UN CARGO CON EL NOMBRE = "+textos[0]);
                
            }

        }

    }//GEN-LAST:event_boton_anadir_cargoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_actualizar_cargo;
    private javax.swing.JButton boton_anadir_cargo;
    private javax.swing.JButton boton_buscar;
    private javax.swing.JButton boton_lista_completa;
    private javax.swing.JComboBox<String> cmb_estado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable lista_cargos;
    private javax.swing.JTextField txt_cargo;
    // End of variables declaration//GEN-END:variables
}
