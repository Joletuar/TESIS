package ventanas;

import crearDocs.GenerarExcel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import objetos.Empleado;
import objetos.Registro;
import objetos.UOperativa;

/**
 *
 * @author Johan Tuarez
 */
public class RegistrosAsisencias extends javax.swing.JFrame {

    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> modelo_ordenado;
    private ArrayList<Registro> registros;

    public RegistrosAsisencias() {

        initComponents();

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("REGISTROS DE ASISTENCIAS");
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.tabla_registros.getTableHeader().setReorderingAllowed(false);
        this.boton_lista_completa.setEnabled(false);

        AlinearColumnas();
        cargarCargos();
        cargarUnidades();
        llenarTabla();

    }

    public void AlinearColumnas() {

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();

        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        this.tabla_registros.getColumnModel().getColumn(0).setCellRenderer(tcr);
        this.tabla_registros.getColumnModel().getColumn(1).setCellRenderer(tcr);
        this.tabla_registros.getColumnModel().getColumn(2).setCellRenderer(tcr);
        this.tabla_registros.getColumnModel().getColumn(3).setCellRenderer(tcr);
        this.tabla_registros.getColumnModel().getColumn(4).setCellRenderer(tcr);
        this.tabla_registros.getColumnModel().getColumn(5).setCellRenderer(tcr);
        this.tabla_registros.getColumnModel().getColumn(6).setCellRenderer(tcr);
        this.tabla_registros.getColumnModel().getColumn(7).setCellRenderer(tcr);
        this.tabla_registros.getColumnModel().getColumn(8).setCellRenderer(tcr);
        this.tabla_registros.getColumnModel().getColumn(9).setCellRenderer(tcr);

        ((DefaultTableCellRenderer) tabla_registros.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

    }

    public void llenarTabla() {

        this.modelo = (DefaultTableModel) this.tabla_registros.getModel();
        this.modelo.setRowCount(0);

        Empleado empleado;
        UOperativa uoperativa;
        Cargo cargo;

        this.registros = Login.bs.getRegistros();

        if (this.registros == null) {

            System.out.println("NO HAY REGISTROS AÚN");

        } else {

            for (Registro registro : registros) {

                Object[] fila = new Object[10];

                String parametro_1 = "ID_Empleado = " + registro.id_empleado;
                empleado = Login.bs.ConsultarEmpleado(parametro_1);

                String parametro_2 = "Id_Unidad = " + empleado.id_unidad;
                uoperativa = Login.bs.ConsultarUOperativa(parametro_2);

                String parametro_3 = "ID_Cargo = " + empleado.id_cargo;
                cargo = Login.bs.ConsultarCargo(parametro_3);

                fila[0] = uoperativa.nombre_unidad;
                fila[1] = empleado.cedula;
                fila[2] = cargo.nombre_cargo;
                fila[3] = empleado.nombres;
                fila[4] = empleado.apellidos;

                for (int i = 0; i < 3; i++) {

                    fila[5] = registro.fecha;
                    fila[6] = registro.hora_entrada;

                    if (registro.hora_comida_s == null) {

                        fila[7] = "X";

                    } else {

                        fila[7] = registro.hora_comida_s;

                    }

                    if (registro.hora_comida_e == null) {

                        fila[8] = "X";

                    } else {

                        fila[8] = registro.hora_comida_e;

                    }

                    if (registro.hora_salida == null) {

                        fila[9] = "X";

                    } else {

                        fila[9] = registro.hora_salida;

                    }
                }

                this.modelo.addRow(fila);
            }

            this.tabla_registros.setModel(modelo);
        }

        this.modelo_ordenado = new TableRowSorter<>(this.modelo);
        this.tabla_registros.setRowSorter(this.modelo_ordenado);

    }

    public void cargarCargos() {

        this.cmb_cargo.addItem("---SELECCIONAR---");

        ArrayList<Cargo> cargos;
        cargos = Login.bs.getCargos();

        if (cargos != null) {

            for (Cargo cargo : cargos) {

                if (cargo.estatus.equalsIgnoreCase("ACTIVO")) {

                    this.cmb_cargo.addItem(cargo.nombre_cargo);

                }

            }

        }

    }

    public void cargarUnidades() {

        this.cmb_unidad.addItem("---SELECCIONAR---");

        ArrayList<UOperativa> unidades;
        unidades = Login.bs.getUnidades();

        if (unidades != null) {

            for (UOperativa unidad : unidades) {

                if (unidad.estatus.equalsIgnoreCase("ACTIVO")) {

                    this.cmb_unidad.addItem(unidad.nombre_unidad);

                }

            }

        }

    }

    private String getFecha() {

        Date fecha = this.calendario.getDate();
        SimpleDateFormat formato = new SimpleDateFormat("d/MM/yyyy");
        String str_fecha = formato.format(fecha);

        return str_fecha;
    }

    private void limpiarTxT() {

        this.txt_cedula.setText("");
        this.txt_apellidos.setText("");
        this.txt_nombres.setText("");
        this.cmb_cargo.setSelectedIndex(0);
        this.cmb_unidad.setSelectedIndex(0);
    }

    private String[] getTxT() {

        String cedula = "", apellidos = "", nombres = "", cargo = "", fecha = "", uoperativa = "";
        String[] textos = new String[6];

        cedula = this.txt_cedula.getText().trim().toUpperCase();
        apellidos = this.txt_apellidos.getText().trim().toUpperCase();
        nombres = this.txt_nombres.getText().trim().toUpperCase();
        cargo = this.cmb_cargo.getSelectedItem().toString().trim().toUpperCase();
        uoperativa = this.cmb_unidad.getSelectedItem().toString().trim().toUpperCase();
        fecha = getFecha();

        textos[0] = cedula;
        textos[1] = apellidos;
        textos[2] = nombres;
        textos[3] = cargo;
        textos[4] = fecha;
        textos[5] = uoperativa;

        return textos;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_apellidos = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_nombres = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_cedula = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        boton_buscar = new javax.swing.JButton();
        boton_lista_completa = new javax.swing.JButton();
        boton_actualizar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        calendario = new com.toedter.calendar.JCalendar();
        jLabel6 = new javax.swing.JLabel();
        boton_excel = new javax.swing.JButton();
        cmb_cargo = new javax.swing.JComboBox<>();
        cmb_unidad = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_registros = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Apellidos:");

        txt_apellidos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_apellidos.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Nombres:");

        txt_nombres.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_nombres.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Cedula:");

        txt_cedula.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_cedula.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Cargo:");

        boton_buscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        boton_buscar.setText("BUSCAR");
        boton_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_buscarActionPerformed(evt);
            }
        });

        boton_lista_completa.setFont(new java.awt.Font("Bahnschrift", 1, 13)); // NOI18N
        boton_lista_completa.setText("LISTA COMPLETA");
        boton_lista_completa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_lista_completaActionPerformed(evt);
            }
        });

        boton_actualizar.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        boton_actualizar.setText("ACTUALIZAR");
        boton_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_actualizarActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Seleccionar Fecha:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        calendario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(calendario, javax.swing.GroupLayout.PREFERRED_SIZE, 294, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 179, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(calendario, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Unidad:");

        boton_excel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        boton_excel.setText("EXCEL");
        boton_excel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_excelActionPerformed(evt);
            }
        });

        cmb_cargo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        cmb_unidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_apellidos, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_cedula, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_nombres, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmb_unidad, 0, 120, Short.MAX_VALUE)
                                    .addComponent(cmb_cargo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(42, 42, 42)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(boton_buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(boton_excel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(boton_actualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(boton_lista_completa, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_cedula, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nombres, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_excel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(cmb_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(cmb_cargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(boton_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_lista_completa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Lista de Registros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        tabla_registros = new javax.swing.JTable(){

            @Override

            public boolean isCellEditable(int rowIndex, int colIndex){

                return false;
            }};
            tabla_registros.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "Unidad", "Cédula", "Cargo", "Nombres", "Apellidos", "Fecha", "Hora Entrada", "Almuerzo (Salida)", "Almuerzo (Entrada)", "Hora Salida"
                }
            ) {
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false, false, false, false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            jScrollPane1.setViewportView(tabla_registros);
            if (tabla_registros.getColumnModel().getColumnCount() > 0) {
                tabla_registros.getColumnModel().getColumn(0).setPreferredWidth(90);
                tabla_registros.getColumnModel().getColumn(1).setPreferredWidth(100);
                tabla_registros.getColumnModel().getColumn(2).setPreferredWidth(100);
                tabla_registros.getColumnModel().getColumn(3).setPreferredWidth(125);
                tabla_registros.getColumnModel().getColumn(4).setPreferredWidth(125);
                tabla_registros.getColumnModel().getColumn(5).setPreferredWidth(75);
                tabla_registros.getColumnModel().getColumn(6).setPreferredWidth(100);
                tabla_registros.getColumnModel().getColumn(7).setPreferredWidth(125);
                tabla_registros.getColumnModel().getColumn(8).setPreferredWidth(135);
                tabla_registros.getColumnModel().getColumn(9).setPreferredWidth(100);
            }

            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE)
                    .addContainerGap())
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1)
                    .addContainerGap())
            );

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void boton_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_buscarActionPerformed

        this.boton_actualizar.setEnabled(false);

        String[] textos = getTxT();
        LinkedList<RowFilter<Object, Object>> lista = new LinkedList<>();

        if (!textos[3].equalsIgnoreCase("---SELECCIONAR---")) {

            lista.add(RowFilter.regexFilter(textos[3], 2));
        }
        if (!textos[1].equalsIgnoreCase("")) {

            lista.add(RowFilter.regexFilter(textos[1], 4));
        }
        if (!textos[2].equalsIgnoreCase("")) {

            lista.add(RowFilter.regexFilter(textos[2], 3));
        }
        if (!textos[4].equalsIgnoreCase("")) {

            lista.add(RowFilter.regexFilter(textos[4], 5));
        }
        if (!textos[0].equalsIgnoreCase("")) {

            lista.add(RowFilter.regexFilter(textos[0], 1));
        }
        if (!textos[5].equalsIgnoreCase("---SELECCIONAR---")) {

            lista.add(RowFilter.regexFilter(textos[5], 0));
        }

        RowFilter filtroAnd = RowFilter.andFilter(lista);

        this.modelo_ordenado.setRowFilter(filtroAnd);
        this.boton_lista_completa.setEnabled(true);
        this.boton_excel.setEnabled(false);
    }//GEN-LAST:event_boton_buscarActionPerformed

    private void boton_lista_completaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_lista_completaActionPerformed

        llenarTabla();
        limpiarTxT();

        this.boton_lista_completa.setEnabled(false);
        this.boton_actualizar.setEnabled(true);
        this.boton_excel.setEnabled(true);

    }//GEN-LAST:event_boton_lista_completaActionPerformed

    private void boton_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_actualizarActionPerformed

        llenarTabla();

    }//GEN-LAST:event_boton_actualizarActionPerformed

    private void boton_excelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_excelActionPerformed

        ArrayList<Object> data = new ArrayList<>();

        Empleado empleado;
        UOperativa uoperativa;
        Cargo cargo;

        registros = Login.bs.getRegistros();

        if (registros == null) {

            JOptionPane.showMessageDialog(null, "NO HAY REGISTROS AÚN");

        } else {

            for (Registro registro : registros) {

                Object[] fila = new Object[10];

                String parametro_1 = "ID_Empleado = " + registro.id_empleado;

                empleado = Login.bs.ConsultarEmpleado(parametro_1);

                String parametro_2 = "Id_Unidad = " + empleado.id_unidad;
                String parametro_3 = "ID_Cargo = " + empleado.id_cargo;

                uoperativa = Login.bs.ConsultarUOperativa(parametro_2);
                cargo = Login.bs.ConsultarCargo(parametro_3);

                fila[0] = uoperativa.nombre_unidad;
                fila[1] = empleado.cedula;
                fila[2] = cargo.nombre_cargo;
                fila[3] = empleado.nombres;
                fila[4] = empleado.apellidos;

                for (int i = 0; i < 3; i++) {

                    fila[5] = registro.fecha;
                    fila[6] = registro.hora_entrada;

                    if (registro.hora_comida_s == null) {

                        fila[7] = "X";

                    } else {

                        fila[7] = registro.hora_comida_s;

                    }

                    if (registro.hora_comida_e == null) {

                        fila[8] = "X";

                    } else {

                        fila[8] = registro.hora_comida_e;

                    }

                    if (registro.hora_salida == null) {

                        fila[9] = "X";

                    } else {

                        fila[9] = registro.hora_salida;

                    }
                }
                data.add(fila);

            }
            GenerarExcel excel = new GenerarExcel(data);
            excel.WriterExcel();
        }
    }//GEN-LAST:event_boton_excelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_actualizar;
    private javax.swing.JButton boton_buscar;
    private javax.swing.JButton boton_excel;
    private javax.swing.JButton boton_lista_completa;
    private com.toedter.calendar.JCalendar calendario;
    private javax.swing.JComboBox<String> cmb_cargo;
    private javax.swing.JComboBox<String> cmb_unidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla_registros;
    private javax.swing.JTextField txt_apellidos;
    private javax.swing.JTextField txt_cedula;
    private javax.swing.JTextField txt_nombres;
    // End of variables declaration//GEN-END:variables
}
