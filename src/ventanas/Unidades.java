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
import objetos.UOperativa;

/**
 *
 * @author Johan Tuarez
 */

public class Unidades extends javax.swing.JFrame {

    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> modelo_ordenado;
    private boolean bandera;
    private int fila_seleccionada;
    private ArrayList<UOperativa> unidades;

    public Unidades() {

        initComponents();

        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("UNIDADES OPERATIVAS");

        this.lista_unidades.getTableHeader().setReorderingAllowed(false);

        this.boton_actualizar_unidad.setEnabled(false);
        this.boton_lista_completa.setEnabled(false);

        AlinearColumnas();
        MostrarTabla();

    }

    public void AlinearColumnas() {

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();

        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        this.lista_unidades.getColumnModel().getColumn(0).setCellRenderer(tcr);
        this.lista_unidades.getColumnModel().getColumn(1).setCellRenderer(tcr);
        this.lista_unidades.getColumnModel().getColumn(2).setCellRenderer(tcr);
        this.lista_unidades.getColumnModel().getColumn(3).setCellRenderer(tcr);

        ((DefaultTableCellRenderer) lista_unidades.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

    }

    public void MostrarTabla() {

        this.modelo = (DefaultTableModel) this.lista_unidades.getModel();
        this.modelo.setRowCount(0);

        this.unidades = Login.bs.getUnidades();

        Object[] fila = new Object[4];

        if (!this.unidades.isEmpty()) {

            for (UOperativa unidad : this.unidades) {

                fila[0] = unidad.id_unidad;
                fila[1] = unidad.nombre_unidad;
                fila[2] = unidad.dir_unidad;
                fila[3] = unidad.estatus;

                this.modelo.addRow(fila);

            }

        } else {

            JOptionPane.showMessageDialog(null, "NO HAY UNIDADES REGISTRADOS");

        }

        this.lista_unidades.setModel(modelo);
        this.modelo_ordenado = new TableRowSorter<>(this.modelo);
        this.lista_unidades.setRowSorter(this.modelo_ordenado);

    }

    private void limpiarTxT() {

        this.txt_unidad.setText("");
        this.txt_dir.setText("");
        this.cmb_estado.setSelectedIndex(0);

    }

    public String[] getTxT() {

        String unidad = "", dir = "", estado = "";
        String[] textos = new String[3];

        unidad = txt_unidad.getText().trim().toUpperCase();
        dir = txt_dir.getText().trim().toUpperCase();
        estado = this.cmb_estado.getSelectedItem().toString().trim().toUpperCase();

        textos[0] = unidad;
        textos[1] = dir;
        textos[2] = estado;

        return textos;

    }

    public void Filtrar() {

        String[] textos = getTxT();
        LinkedList<RowFilter<Object, Object>> lista = new LinkedList<>();

        if (textos[0].equalsIgnoreCase("") && textos[1].equalsIgnoreCase("") && textos[2].equalsIgnoreCase("---SELECCIONAR---")) {

            JOptionPane.showMessageDialog(null, "INGRESE DATOS EN ALGÚN CAMPO");

        } else {

            System.out.println("FILTRO REALIZADO");

            if (!textos[0].equalsIgnoreCase("")) {

                lista.add(RowFilter.regexFilter(textos[0], 1));
            }
            if (!textos[1].equalsIgnoreCase("")) {

                lista.add(RowFilter.regexFilter(textos[1], 2));
            }
            if (!textos[2].equalsIgnoreCase("---SELECCIONAR---")) {

                lista.add(RowFilter.regexFilter(textos[2], 3));
            }

            RowFilter filtroAnd = RowFilter.andFilter(lista);
            this.modelo_ordenado.setRowFilter(filtroAnd);
            this.boton_anadir_unidad.setEnabled(false);
            this.boton_lista_completa.setEnabled(true);

        }

    }

    public boolean VerificarUnidad(String nom_unidad) {

        String parametros = "Nombre_Unidad = '" + nom_unidad + "'";
        UOperativa temp_unidad = Login.bs.ConsultarUOperativa(parametros);

        if (temp_unidad == null) {

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
        lista_unidades = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_unidad = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_dir = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmb_estado = new javax.swing.JComboBox<>();
        boton_buscar = new javax.swing.JButton();
        boton_lista_completa = new javax.swing.JButton();
        boton_anadir_unidad = new javax.swing.JButton();
        boton_actualizar_unidad = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Lista de Unidades Operativas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        lista_unidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Unidad", "Dirección", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lista_unidades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lista_unidadesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lista_unidades);
        if (lista_unidades.getColumnModel().getColumnCount() > 0) {
            lista_unidades.getColumnModel().getColumn(0).setMinWidth(35);
            lista_unidades.getColumnModel().getColumn(0).setPreferredWidth(35);
            lista_unidades.getColumnModel().getColumn(0).setMaxWidth(35);
            lista_unidades.getColumnModel().getColumn(1).setResizable(false);
            lista_unidades.getColumnModel().getColumn(2).setResizable(false);
            lista_unidades.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Unidad:");

        txt_unidad.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_unidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Dirección:");

        txt_dir.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_dir.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Estatus:");

        cmb_estado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmb_estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---SELECCIONAR---", "ACTIVO", "INACTIVO" }));

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

        boton_anadir_unidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        boton_anadir_unidad.setText("AÑADIR UNIDAD");
        boton_anadir_unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_anadir_unidadActionPerformed(evt);
            }
        });

        boton_actualizar_unidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        boton_actualizar_unidad.setText("ACTUALIZAR UNIDAD");
        boton_actualizar_unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_actualizar_unidadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(29, 29, 29))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(14, 14, 14)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(25, 25, 25)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmb_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txt_dir, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                                .addComponent(txt_unidad)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boton_anadir_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boton_actualizar_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(boton_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(boton_lista_completa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_dir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmb_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_lista_completa, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(boton_anadir_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(boton_actualizar_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boton_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_buscarActionPerformed

        Filtrar();
    }//GEN-LAST:event_boton_buscarActionPerformed

    private void boton_lista_completaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_lista_completaActionPerformed

        this.boton_lista_completa.setEnabled(false);
        this.boton_anadir_unidad.setEnabled(true);
        MostrarTabla();
        limpiarTxT();

    }//GEN-LAST:event_boton_lista_completaActionPerformed

    private void boton_anadir_unidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_anadir_unidadActionPerformed

        String[] textos = getTxT();

        if (textos[0].equals("") || textos[1].equals("") || textos[2].equals("---SELECCIONAR---")) {

            JOptionPane.showMessageDialog(null, "COMPLETE TODO LOS CAMPOS");

        } else {

            if (VerificarUnidad(textos[0])) {

                Login.in.InsertarUOperativa(textos[0], textos[1]);

                JOptionPane.showMessageDialog(null, "UNIDAD AGREGADO CORRECTAMENTE");

                limpiarTxT();
                MostrarTabla();

            }else{
                
                JOptionPane.showMessageDialog(null, "YA EXISTE UNA U.OPERATIVA CON EL NOMBRE = "+textos[0]);
                
            }

        }
    }//GEN-LAST:event_boton_anadir_unidadActionPerformed

    private void boton_actualizar_unidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_actualizar_unidadActionPerformed

        if (bandera) {

            String[] textos = getTxT();

            if (textos[0].equals("") || textos[1].equals("") || textos[2].equals("---SELECCIONAR---")) {

                JOptionPane.showMessageDialog(null, "COMPLETE TODO LOS CAMPOS");

            } else {

                if (textos[2].equals("INACTIVO")) {

                    JOptionPane.showMessageDialog(null, "ATENCIÓN: VA A DESACTIVAR UNA UNIDAD, POR LO CUAL YA NO PODRÁ REGISTRAR USUARIOS CON ESTA UNIDAD",
                            "NOTIFICACIÓN", 2);

                }

                Login.ac.ActualizarUnidad((int) this.lista_unidades.getValueAt(this.fila_seleccionada, 0),
                        textos[0], textos[1], textos[2]);

                JOptionPane.showMessageDialog(null, "ACTUALIZACIÓN CORRECTA");

                limpiarTxT();
                MostrarTabla();

                this.boton_actualizar_unidad.setEnabled(false);
                this.boton_anadir_unidad.setEnabled(true);
                this.boton_lista_completa.setEnabled(false);

            }
        }
    }//GEN-LAST:event_boton_actualizar_unidadActionPerformed

    private void lista_unidadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lista_unidadesMouseClicked

        if (this.lista_unidades.getSelectedRow() < 0) {

            this.bandera = false;

        } else {

            this.fila_seleccionada = this.lista_unidades.getSelectedRow();
            this.bandera = true;
            this.boton_actualizar_unidad.setEnabled(true);
            this.boton_anadir_unidad.setEnabled(false);

            this.txt_unidad.setText(this.lista_unidades.getValueAt(this.fila_seleccionada, 1).toString().trim().toUpperCase());
            this.txt_dir.setText(this.lista_unidades.getValueAt(this.fila_seleccionada, 2).toString().trim().toUpperCase());
            this.cmb_estado.setSelectedItem(this.lista_unidades.getValueAt(this.fila_seleccionada, 3).toString().trim().toUpperCase());

        }

    }//GEN-LAST:event_lista_unidadesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_actualizar_unidad;
    private javax.swing.JButton boton_anadir_unidad;
    private javax.swing.JButton boton_buscar;
    private javax.swing.JButton boton_lista_completa;
    private javax.swing.JComboBox<String> cmb_estado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable lista_unidades;
    private javax.swing.JTextField txt_dir;
    private javax.swing.JTextField txt_unidad;
    // End of variables declaration//GEN-END:variables
}
