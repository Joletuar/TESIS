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
import objetos.Empleado;
import objetos.Jornada;
import objetos.UOperativa;

/**
 *
 * @author Johan Tuarez
 */
public class Empleados extends javax.swing.JFrame {

    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> modelo_ordenado;
    private boolean bandera;
    private int fila_seleccionada;
    private ArrayList<Empleado> empleados;
    private int id_empleado_seleccionado;

    public static String[] valores;

    public Empleados() {

        initComponents();

        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("EMPLEADOS");

        this.tabla_empleados.getTableHeader().setReorderingAllowed(false);
        this.tabla_empleados.setFont(new java.awt.Font("Tahoma", 0, 11));

        this.boton_actualizar_empleado.setEnabled(false);

        AlinearColumnas();
        cargarCargos();
        cargarUnidades();
        MostrarTabla();

    }

    public final void AlinearColumnas() {

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();

        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        this.tabla_empleados.getColumnModel().getColumn(0).setCellRenderer(tcr);
        this.tabla_empleados.getColumnModel().getColumn(1).setCellRenderer(tcr);
        this.tabla_empleados.getColumnModel().getColumn(2).setCellRenderer(tcr);
        this.tabla_empleados.getColumnModel().getColumn(3).setCellRenderer(tcr);
        this.tabla_empleados.getColumnModel().getColumn(4).setCellRenderer(tcr);
        this.tabla_empleados.getColumnModel().getColumn(5).setCellRenderer(tcr);
        this.tabla_empleados.getColumnModel().getColumn(6).setCellRenderer(tcr);
        this.tabla_empleados.getColumnModel().getColumn(7).setCellRenderer(tcr);
        this.tabla_empleados.getColumnModel().getColumn(8).setCellRenderer(tcr);
        this.tabla_empleados.getColumnModel().getColumn(9).setCellRenderer(tcr);
        this.tabla_empleados.getColumnModel().getColumn(10).setCellRenderer(tcr);

        ((DefaultTableCellRenderer) tabla_empleados.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

    }

    public final void MostrarTabla() {

        this.modelo = (DefaultTableModel) this.tabla_empleados.getModel();
        this.modelo.setRowCount(0);

        Object[] fila = new Object[11];

        this.empleados = Login.bs.getEmpleados();

        if (!this.empleados.isEmpty()) {

            for (Empleado empleado : this.empleados) {

                String parametro_1 = "Id_Unidad = " + empleado.id_unidad;
                String parametro_2 = "ID_Cargo = " + empleado.id_cargo;
                String parametro_3 = "ID_Jornada = " + empleado.id_jornada;

                UOperativa uoperativa = Login.bs.ConsultarUOperativa(parametro_1);
                Cargo cargo = Login.bs.ConsultarCargo(parametro_2);
                Jornada jornada = Login.bs.ConsultarJornada(parametro_3);

                fila[0] = empleado.id_empleado;
                fila[1] = uoperativa.nombre_unidad;
                fila[2] = cargo.nombre_cargo;
                fila[3] = empleado.cedula;
                fila[4] = empleado.nombres;
                fila[5] = empleado.apellidos;
                fila[6] = empleado.telefono;
                fila[7] = empleado.correo;
                fila[8] = jornada.hora_entrada;
                fila[9] = jornada.hora_salida;
                fila[10] = empleado.estatus;

                this.modelo.addRow(fila);

            }

        } else {

            JOptionPane.showMessageDialog(null, "NO HAY EMPLEADOS REGISTRADOS");

        }

        this.tabla_empleados.setModel(modelo);
        this.modelo_ordenado = new TableRowSorter<>(this.modelo);
        this.tabla_empleados.setRowSorter(this.modelo_ordenado);
    }

    public final void cargarCargos() {

        this.cmb_cargo.addItem("---SELECCIONAR---");

        ArrayList<Cargo> cargos;
        cargos = Login.bs.getCargos();

        for (Cargo cargo : cargos) {

            if (cargo.estatus.equalsIgnoreCase("ACTIVO")) {

                this.cmb_cargo.addItem(cargo.nombre_cargo);
            }

        }

    }

    public final void cargarUnidades() {

        this.cmb_unidad.addItem("---SELECCIONAR---");

        ArrayList<UOperativa> unidades;
        unidades = Login.bs.getUnidades();

        for (UOperativa unidad : unidades) {

            if (unidad.estatus.equalsIgnoreCase("ACTIVO")) {

                this.cmb_unidad.addItem(unidad.nombre_unidad);

            }

        }

    }

    public String[] getTxT() {

        String[] textos = new String[12];

        String str_unidad = this.cmb_unidad.getSelectedItem().toString().trim().toUpperCase();
        String str_cargo = this.cmb_cargo.getSelectedItem().toString().trim().toUpperCase();
        String cedula = this.txt_cedula.getText().trim().toUpperCase();
        String nombres = this.txt_nombres.getText().trim().toUpperCase();
        String apellidos = this.txt_apellidos.getText().trim().toUpperCase();
        String telefono = this.txt_telefono.getText().trim().toUpperCase();
        String correo = this.txt_correo.getText().trim().toUpperCase();
        String hora_entrada_hora = this.cmb_entrada_hora.getSelectedItem().toString().trim().toUpperCase();// + ":" + this.cmb_entrada_minu.getSelectedItem().toString().trim().toUpperCase() + ":00";
        String hora_entrada_minuto = this.cmb_entrada_minu.getSelectedItem().toString().trim().toUpperCase();
        String hora_salida_hora = this.cmb_salida_hora.getSelectedItem().toString().trim().toUpperCase(); //+ ":" + this.cmb_salida_minu.getSelectedItem().toString().trim().toUpperCase() + ":00";
        String hora_salida_minuto = this.cmb_salida_minu.getSelectedItem().toString().trim().toUpperCase();
        String estado = this.cmb_estado.getSelectedItem().toString().trim().toUpperCase();

        textos[0] = str_unidad;
        textos[1] = str_cargo;
        textos[2] = cedula;
        textos[3] = nombres;
        textos[4] = apellidos;
        textos[5] = telefono;
        textos[6] = correo;
        textos[7] = hora_entrada_hora;
        textos[8] = hora_entrada_minuto;
        textos[9] = hora_salida_hora;
        textos[10] = hora_salida_minuto;
        textos[11] = estado;

        return textos;

    }

    public String verificarCampos() {

        int tipo_id = this.cmb_identificacion.getSelectedIndex();
        String mensaje = "";

        String[] lista = getTxT();

        if (tipo_id == 0) {

            if (!lista[2].matches("[0-9]+") || lista[2].length() != 10) {

                mensaje += "NÚMERO DE CÉDULA INCORRECTO\n";

            }

        } else {

            if (lista[2].length() != 9) {

                mensaje += "NÚMERO DE PASAPORTE INCORRECTO\n";

            }

        }

        if (lista[3].matches(".*[0-9].*")) {

            mensaje += "NOMBRES NO PUEDEN LLEVAR NÚMEROS\n";

        }

        if (lista[4].matches(".*[0-9].*")) {

            mensaje += "APELLIDOS NO PUEDEN LLEVAR NÚMEROS\n";

        }

        if (!lista[5].matches("[0-9]+") || lista[5].length() != 10) {

            mensaje += "NÚMERO DE TELÉFONO NO VÁLIDO\n";

        }

        if (!lista[6].contains("@") || !lista[6].contains(".")) {

            mensaje += "CORREO NO VÁLIDO\n";

        }

        return mensaje;

    }

    public void limpiarTxT() {

        this.txt_nombres.setText("");
        this.txt_apellidos.setText("");
        this.txt_cedula.setText("");
        this.txt_correo.setText("");
        this.txt_telefono.setText("");
        this.cmb_cargo.setSelectedIndex(0);
        this.cmb_unidad.setSelectedIndex(0);
        this.cmb_entrada_hora.setSelectedIndex(0);
        this.cmb_entrada_minu.setSelectedIndex(0);
        this.cmb_salida_hora.setSelectedIndex(0);
        this.cmb_salida_minu.setSelectedIndex(0);
        this.cmb_estado.setSelectedIndex(0);
        this.cmb_identificacion.setSelectedIndex(0);

    }

    public void Filtrar() {

        String[] textos = getTxT();
        LinkedList<RowFilter<Object, Object>> lista = new LinkedList<>();

        if (textos[0].equals("---SELECCIONAR---") && textos[1].equals("---SELECCIONAR---") && textos[11].equals("---SELECCIONAR---")
                && textos[7].equals("--") && textos[8].equals("--") && textos[9].equals("--") && textos[10].equals("--") && textos[2].equals("") && textos[3].equals("")
                && textos[4].equals("") && textos[5].equals("") && textos[6].equals("")) {

            JOptionPane.showMessageDialog(null, "INGRESE DATOS EN ALGÚN CAMPO");

        } else {

            System.out.println("FILTRO REALIZADO");

            if (!textos[0].equalsIgnoreCase("---SELECCIONAR---")) {

                lista.add(RowFilter.regexFilter(textos[0], 1));
            }
            if (!textos[1].equalsIgnoreCase("---SELECCIONAR---")) {

                lista.add(RowFilter.regexFilter(textos[1], 2));
            }
            if (!textos[2].equalsIgnoreCase("")) {

                lista.add(RowFilter.regexFilter(textos[2], 3));
            }
            if (!textos[3].equalsIgnoreCase("")) {

                lista.add(RowFilter.regexFilter(textos[3], 4));
            }
            if (!textos[4].equalsIgnoreCase("")) {

                lista.add(RowFilter.regexFilter(textos[4], 5));
            }
            if (!textos[5].equalsIgnoreCase("")) {

                lista.add(RowFilter.regexFilter(textos[5], 6));
            }
            if (!textos[6].equalsIgnoreCase("")) {

                lista.add(RowFilter.regexFilter(textos[6], 7));
            }
            if (!textos[7].equalsIgnoreCase("--") && !textos[8].equalsIgnoreCase("--")) {

                lista.add(RowFilter.regexFilter(textos[7] + ":" + textos[8] + ":00", 8));
            }
            if (!textos[9].equalsIgnoreCase("--") && !textos[10].equalsIgnoreCase("--")) {

                lista.add(RowFilter.regexFilter(textos[9] + ":" + textos[10] + ":00", 9));
            }
            if (!textos[11].equalsIgnoreCase("---SELECCIONAR---")) {

                lista.add(RowFilter.regexFilter(textos[11], 10));
            }

            RowFilter filtroAnd = RowFilter.andFilter(lista);
            this.modelo_ordenado.setRowFilter(filtroAnd);
            this.boton_anadir_empleado.setEnabled(false);

        }

    }

    public boolean VerificarEmpleado(String cedula) {

        String parametros = "Cedula = '" + cedula + "'";
        Empleado temp_empleado = Login.bs.ConsultarEmpleado(parametros);

        if (temp_empleado == null) {

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
        tabla_empleados = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cmb_entrada_hora = new javax.swing.JComboBox<>();
        cmb_entrada_minu = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cmb_salida_hora = new javax.swing.JComboBox<>();
        cmb_salida_minu = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        cmb_unidad = new javax.swing.JComboBox<>();
        cmb_cargo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txt_correo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_telefono = new javax.swing.JTextField();
        txt_cedula = new javax.swing.JTextField();
        txt_apellidos = new javax.swing.JTextField();
        txt_nombres = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        boton_buscar = new javax.swing.JButton();
        boton_lista_completa = new javax.swing.JButton();
        boton_anadir_empleado = new javax.swing.JButton();
        boton_actualizar_empleado = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        cmb_estado = new javax.swing.JComboBox<>();
        cmb_identificacion = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Lista de Empleados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        tabla_empleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Unidad", "Cargo", "C.I./Pasaporte", "Nombres", "Apellidos", "Teléfono", "Correo", "Entrada", "Salida", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_empleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_empleadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_empleados);
        if (tabla_empleados.getColumnModel().getColumnCount() > 0) {
            tabla_empleados.getColumnModel().getColumn(0).setMinWidth(35);
            tabla_empleados.getColumnModel().getColumn(0).setPreferredWidth(35);
            tabla_empleados.getColumnModel().getColumn(0).setMaxWidth(35);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Horario Entrada", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(195, 88));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        jLabel9.setText("Hora:");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 34, -1, -1));

        jLabel10.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        jLabel10.setText("Minutos:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(107, 34, -1, -1));

        cmb_entrada_hora.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmb_entrada_hora.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jPanel3.add(cmb_entrada_hora, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 55, 60, -1));

        cmb_entrada_minu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmb_entrada_minu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));
        jPanel3.add(cmb_entrada_minu, new org.netbeans.lib.awtextra.AbsoluteConstraints(107, 55, 60, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText(":");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 55, 10, 20));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Horario Salida", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(195, 88));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        jLabel11.setText("Hora:");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 34, -1, -1));

        jLabel12.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        jLabel12.setText("Minutos:");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(107, 34, -1, -1));

        cmb_salida_hora.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmb_salida_hora.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jPanel4.add(cmb_salida_hora, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 55, 60, -1));

        cmb_salida_minu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmb_salida_minu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));
        jPanel4.add(cmb_salida_minu, new org.netbeans.lib.awtextra.AbsoluteConstraints(107, 55, 60, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText(":");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 55, 10, 20));

        cmb_unidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        cmb_cargo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Cargo:");

        txt_correo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_correo.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Correo:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Teléfono:");

        txt_telefono.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_telefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txt_cedula.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_cedula.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txt_apellidos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_apellidos.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txt_nombres.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_nombres.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Unidad:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Apellidos:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Nombres:");

        boton_buscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        boton_buscar.setText("BUSCAR");
        boton_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_buscarActionPerformed(evt);
            }
        });

        boton_lista_completa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        boton_lista_completa.setText("REFRESCAR");
        boton_lista_completa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_lista_completaActionPerformed(evt);
            }
        });

        boton_anadir_empleado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        boton_anadir_empleado.setText("REGISTRAR EMPLEADO");
        boton_anadir_empleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_anadir_empleadoActionPerformed(evt);
            }
        });

        boton_actualizar_empleado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        boton_actualizar_empleado.setText("ACTUALIZAR EMPLEADO");
        boton_actualizar_empleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_actualizar_empleadoActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Estatus:");

        cmb_estado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmb_estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---SELECCIONAR---", "ACTIVO", "INACTIVO" }));

        cmb_identificacion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmb_identificacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cédula", "Pasaporte" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel14)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3))
                                        .addGap(36, 36, 36)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txt_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_nombres, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(cmb_identificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(cmb_estado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(cmb_cargo, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel4)
                                                .addGap(18, 18, 18)
                                                .addComponent(cmb_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txt_cedula, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(boton_buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(boton_lista_completa, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(boton_actualizar_empleado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(boton_anadir_empleado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nombres, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cedula, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_identificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cmb_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmb_cargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(cmb_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_anadir_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_actualizar_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_lista_completa, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boton_actualizar_empleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_actualizar_empleadoActionPerformed

        if (bandera) {

            String[] textos = getTxT();

            if (textos[0].equals("---SELECCIONAR---") || textos[1].equals("---SELECCIONAR---") || textos[11].equals("---SELECCIONAR---")
                    || textos[7].equals("--") || textos[8].equals("--") && textos[9].equals("--") || textos[10].equals("--") || textos[2].equals("") || textos[3].equals("")
                    || textos[4].equals("") || textos[5].equals("") || textos[6].equals("")) {

                JOptionPane.showMessageDialog(null, "COMPLETE TODO LOS CAMPOS");

            } else {

                if (textos[11].equals("INACTIVO")) {

                    JOptionPane.showMessageDialog(null, "ATENCIÓN: VA A DESACTIVAR UN EMPLEADO, POR LO CUAL YA NO PODRÁ MARCA ASISTENCIA",
                            "NOTIFICACIÓN", 2);

                }

                String mensaje = verificarCampos().trim();

                if (mensaje.equals("")) {

                    String parametros = "ID_Empleado = " + this.id_empleado_seleccionado;

                    Empleado empleado_seleccionado = Login.bs.ConsultarEmpleado(parametros);

                    String parametros_1 = "Nombre_Unidad = '" + textos[0] + "'";
                    String parametros_2 = "Cargo = '" + textos[1] + "'";
                    String parametros_3 = "ID_Jornada = " + empleado_seleccionado.id_jornada;

                    UOperativa unidad = Login.bs.ConsultarUOperativa(parametros_1);
                    Cargo cargo = Login.bs.ConsultarCargo(parametros_2);
                    Jornada jornada = Login.bs.ConsultarJornada(parametros_3);

                    Login.ac.ActualizarJornada(jornada.id_jornada, textos[7] + ":" + textos[8] + ":00", textos[9] + ":" + textos[10] + ":00",
                            textos[2]);

                    Login.ac.ActualizarEmpleado((int) this.tabla_empleados.getValueAt(this.fila_seleccionada, 0),
                            unidad.id_unidad, cargo.id_cargo, jornada.id_jornada, textos[2], textos[3], textos[4], textos[5], textos[6],
                            textos[11]);

                    JOptionPane.showMessageDialog(null, "ACTUALIZACIÓN CORRECTA");

                    limpiarTxT();
                    MostrarTabla();

                    this.boton_actualizar_empleado.setEnabled(false);
                    this.boton_anadir_empleado.setEnabled(true);
                    this.boton_buscar.setEnabled(true);

                } else {

                    JOptionPane.showMessageDialog(null, mensaje);

                }

            }
        }
    }//GEN-LAST:event_boton_actualizar_empleadoActionPerformed

    private void boton_anadir_empleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_anadir_empleadoActionPerformed

        String[] textos = getTxT();

        if (textos[0].equals("---SELECCIONAR---") || textos[1].equals("---SELECCIONAR---") || textos[11].equals("---SELECCIONAR---")
                || textos[7].equals("--") || textos[8].equals("--") && textos[9].equals("--") || textos[10].equals("--") || textos[2].equals("") || textos[3].equals("")
                || textos[4].equals("") || textos[5].equals("") || textos[6].equals("")) {

            JOptionPane.showMessageDialog(null, "COMPLETE TODO LOS CAMPOS");

        } else {

            String mensaje = verificarCampos().trim();

            if (mensaje.equals("")) {

                if (VerificarEmpleado(textos[2])) {

                    Empleados.valores = textos;

                    new RegistroHuellas(this).setVisible(true);

                } else {

                    JOptionPane.showMessageDialog(null, "YA EXISTE UN EMPLEADO CON LA CEDULA = " + textos[2]);

                }

            } else {

                JOptionPane.showMessageDialog(null, mensaje);

            }

        }
    }//GEN-LAST:event_boton_anadir_empleadoActionPerformed

    private void boton_lista_completaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_lista_completaActionPerformed

        MostrarTabla();
        limpiarTxT();
        this.boton_anadir_empleado.setEnabled(true);
        this.boton_buscar.setEnabled(true);
        this.boton_actualizar_empleado.setEnabled(false);

    }//GEN-LAST:event_boton_lista_completaActionPerformed

    private void boton_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_buscarActionPerformed

        Filtrar();

    }//GEN-LAST:event_boton_buscarActionPerformed

    private void tabla_empleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_empleadosMouseClicked

        if (this.tabla_empleados.getSelectedRow() < 0) {

            this.bandera = false;

        } else {

            this.fila_seleccionada = this.tabla_empleados.getSelectedRow();
            this.bandera = true;
            this.boton_buscar.setEnabled(false);
            this.boton_anadir_empleado.setEnabled(false);
            this.boton_actualizar_empleado.setEnabled(true);

            this.id_empleado_seleccionado = Integer.parseInt(this.tabla_empleados.getValueAt(this.fila_seleccionada, 0).toString());

            this.txt_nombres.setText(this.tabla_empleados.getValueAt(this.fila_seleccionada, 4).toString().trim().toUpperCase());
            this.txt_apellidos.setText(this.tabla_empleados.getValueAt(this.fila_seleccionada, 5).toString().trim().toUpperCase());

            this.txt_cedula.setText(this.tabla_empleados.getValueAt(this.fila_seleccionada, 3).toString().trim().toUpperCase());

            if (this.txt_cedula.getText().matches("[0-9]+") && this.txt_cedula.getText().length() == 10) {

                this.cmb_identificacion.setSelectedIndex(0);

            } else {

                this.cmb_identificacion.setSelectedIndex(1);

            }

            this.txt_telefono.setText(this.tabla_empleados.getValueAt(this.fila_seleccionada, 6).toString().trim().toUpperCase());
            this.txt_correo.setText(this.tabla_empleados.getValueAt(this.fila_seleccionada, 7).toString().trim().toUpperCase());
            this.cmb_cargo.setSelectedItem(this.tabla_empleados.getValueAt(this.fila_seleccionada, 2).toString().trim().toUpperCase());
            this.cmb_unidad.setSelectedItem(this.tabla_empleados.getValueAt(this.fila_seleccionada, 1).toString().trim().toUpperCase());
            this.cmb_estado.setSelectedItem(this.tabla_empleados.getValueAt(this.fila_seleccionada, 10).toString().trim().toUpperCase());

            String hora_entrada = this.tabla_empleados.getValueAt(this.fila_seleccionada, 8).toString().trim().toUpperCase().split(":")[0];
            String minu_entrada = this.tabla_empleados.getValueAt(this.fila_seleccionada, 8).toString().trim().toUpperCase().split(":")[1];
            String hora_salida = this.tabla_empleados.getValueAt(this.fila_seleccionada, 9).toString().trim().toUpperCase().split(":")[0];
            String minu_salida = this.tabla_empleados.getValueAt(this.fila_seleccionada, 9).toString().trim().toUpperCase().split(":")[1];

            this.cmb_entrada_hora.setSelectedItem(hora_entrada);
            this.cmb_entrada_minu.setSelectedItem(minu_entrada);
            this.cmb_salida_hora.setSelectedItem(hora_salida);
            this.cmb_salida_minu.setSelectedItem(minu_salida);

        }

    }//GEN-LAST:event_tabla_empleadosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_actualizar_empleado;
    private javax.swing.JButton boton_anadir_empleado;
    private javax.swing.JButton boton_buscar;
    private javax.swing.JButton boton_lista_completa;
    private javax.swing.JComboBox<String> cmb_cargo;
    private javax.swing.JComboBox<String> cmb_entrada_hora;
    private javax.swing.JComboBox<String> cmb_entrada_minu;
    private javax.swing.JComboBox<String> cmb_estado;
    private javax.swing.JComboBox<String> cmb_identificacion;
    private javax.swing.JComboBox<String> cmb_salida_hora;
    private javax.swing.JComboBox<String> cmb_salida_minu;
    private javax.swing.JComboBox<String> cmb_unidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla_empleados;
    private javax.swing.JTextField txt_apellidos;
    private javax.swing.JTextField txt_cedula;
    private javax.swing.JTextField txt_correo;
    private javax.swing.JTextField txt_nombres;
    private javax.swing.JTextField txt_telefono;
    // End of variables declaration//GEN-END:variables
}
