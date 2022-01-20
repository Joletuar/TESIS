package ventanas;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.LinkedList;
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
public class Reportes extends javax.swing.JFrame {

    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> modelo_ordenado;
    private boolean bandera;
    private int fila_seleccionada;
    private final ArrayList<Empleado> empleados;
    private final ArrayList<Registro> registros;

    public Reportes() {

        initComponents();

        this.setTitle("REPORTES Y GRÁFICAS CUANTITATIVAS");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.boton_lista_completa.setEnabled(false);

        this.tabla_empleados.getTableHeader().setReorderingAllowed(false);

        this.empleados = Login.bs.getEmpleados();
        this.registros = Login.bs.getRegistros();

        AlinearColumnas();
        cargarCargos();
        cargarUnidades();
        MostrarTabla();

    }

    public void AlinearColumnas() {

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();

        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        this.tabla_empleados.getColumnModel().getColumn(0).setCellRenderer(tcr);
        this.tabla_empleados.getColumnModel().getColumn(1).setCellRenderer(tcr);
        this.tabla_empleados.getColumnModel().getColumn(2).setCellRenderer(tcr);
        this.tabla_empleados.getColumnModel().getColumn(3).setCellRenderer(tcr);
        this.tabla_empleados.getColumnModel().getColumn(4).setCellRenderer(tcr);

        ((DefaultTableCellRenderer) tabla_empleados.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

    }

    public void cargarCargos() {

        this.cmb_cargo.addItem("---SELECCIONAR---");

        ArrayList<Cargo> cargos = new ArrayList<>();
        cargos = Login.bs.getCargos();

        for (Cargo cargo : cargos) {

            if (cargo.estatus.equalsIgnoreCase("ACTIVO")) {

                this.cmb_cargo.addItem(cargo.nombre_cargo);
            }

        }

    }

    public void cargarUnidades() {

        this.cmb_unidad.addItem("---SELECCIONAR---");

        ArrayList<UOperativa> unidades = new ArrayList<>();
        unidades = Login.bs.getUnidades();

        for (UOperativa unidad : unidades) {

            if (unidad.estatus.equalsIgnoreCase("ACTIVO")) {

                this.cmb_unidad.addItem(unidad.nombre_unidad);

            }

        }

    }

    private void limpiarTxT() {

        this.txt_cedula.setText("");
        this.txt_apellidos.setText("");
        this.txt_nombres.setText("");
        this.cmb_cargo.setSelectedIndex(0);
        this.cmb_unidad.setSelectedIndex(0);
        this.cmb_tipo.setSelectedIndex(0);

    }

    public void MostrarTabla() {

        this.modelo = (DefaultTableModel) this.tabla_empleados.getModel();
        this.modelo.setRowCount(0);

        Object[] fila = new Object[5];

        if (!this.empleados.isEmpty()) {

            for (Empleado empleado : this.empleados) {

                if (empleado.estatus.equalsIgnoreCase("ACTIVO")) {

                    String parametro_1 = "Id_Unidad = " + empleado.id_unidad;
                    String parametro_2 = "ID_Cargo = " + empleado.id_cargo;

                    UOperativa uoperativa = Login.bs.ConsultarUOperativa(parametro_1);
                    Cargo cargo = Login.bs.ConsultarCargo(parametro_2);

                    fila[0] = uoperativa.nombre_unidad;
                    fila[1] = cargo.nombre_cargo;
                    fila[2] = empleado.cedula;
                    fila[3] = empleado.nombres;
                    fila[4] = empleado.apellidos;

                    this.modelo.addRow(fila);
                }

            }

        } else {

            JOptionPane.showMessageDialog(null, "NO HAY EMPLEADOS REGISTRADOS");

        }

        this.tabla_empleados.setModel(modelo);
        this.modelo_ordenado = new TableRowSorter<>(this.modelo);
        this.tabla_empleados.setRowSorter(this.modelo_ordenado);
    }

    private String[] getTxT() {

        String cedula = "", apellidos = "", nombres = "", cargo = "", uoperativa = "";
        String[] textos = new String[5];

        cargo = this.cmb_cargo.getSelectedItem().toString().trim().toUpperCase();
        uoperativa = this.cmb_unidad.getSelectedItem().toString().trim().toUpperCase();
        cedula = this.txt_cedula.getText().trim().toUpperCase();
        apellidos = this.txt_apellidos.getText().trim().toUpperCase();
        nombres = this.txt_nombres.getText().trim().toUpperCase();

        textos[0] = uoperativa;
        textos[1] = cedula;
        textos[2] = cargo;
        textos[3] = nombres;
        textos[4] = apellidos;

        return textos;
    }

    public void Filtrar() {

        String[] textos = getTxT();
        LinkedList<RowFilter<Object, Object>> lista = new LinkedList<>();

        if (textos[0].equalsIgnoreCase("---SELECCIONAR---") && textos[1].equalsIgnoreCase("") && textos[2].equalsIgnoreCase("---SELECCIONAR---") && textos[3].equalsIgnoreCase("")) {

            JOptionPane.showMessageDialog(null, "INGRESE DATOS EN ALGÚN CAMPO");

        } else {

            System.out.println("FILTRO REALIZADO");

            if (!textos[0].equalsIgnoreCase("---SELECCIONAR---")) {

                lista.add(RowFilter.regexFilter(textos[0], 0));
            }
            if (!textos[1].equalsIgnoreCase("")) {

                lista.add(RowFilter.regexFilter(textos[1], 2));
            }
            if (!textos[2].equalsIgnoreCase("---SELECCIONAR---")) {

                lista.add(RowFilter.regexFilter(textos[2], 1));
            }
            if (!textos[3].equalsIgnoreCase("")) {

                lista.add(RowFilter.regexFilter(textos[3], 3));
            }
            if (!textos[4].equalsIgnoreCase("")) {

                lista.add(RowFilter.regexFilter(textos[4], 4));
            }

            RowFilter filtroAnd = RowFilter.andFilter(lista);
            this.modelo_ordenado.setRowFilter(filtroAnd);

        }
    }

    public void GenerarPDF() {

        String cedula = this.tabla_empleados.getValueAt(this.fila_seleccionada, 2).toString().trim().toUpperCase();
        String nombre_unidad = this.tabla_empleados.getValueAt(this.fila_seleccionada, 0).toString().trim().toUpperCase();
        String cargo = this.tabla_empleados.getValueAt(this.fila_seleccionada, 1).toString().trim().toUpperCase();

        new SeleccionFecha(cedula, cargo, nombre_unidad, empleados, registros).setVisible(true);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_empleados = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_cedula = new javax.swing.JTextField();
        txt_apellidos = new javax.swing.JTextField();
        txt_nombres = new javax.swing.JTextField();
        boton_buscar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        boton_reporte_empleado = new javax.swing.JButton();
        boton_reporte_horas = new javax.swing.JButton();
        boton_reporte_atrasos = new javax.swing.JButton();
        boton_reporte_faltas = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cmb_unidad = new javax.swing.JComboBox<>();
        cmb_cargo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        boton_lista_completa = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmb_tipo = new javax.swing.JComboBox<>();
        boton_grafica_empleado = new javax.swing.JButton();
        boton_grafica_unidad = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Lista de Empleados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(681, 330));

        tabla_empleados = new javax.swing.JTable(){

            @Override

            public boolean isCellEditable(int rowIndex, int colIndex){

                return false;
            }};
            tabla_empleados.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "Unidad", "Cargo", "Cédula", "Nombres", "Apellidos"
                }
            ) {
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false
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
                tabla_empleados.getColumnModel().getColumn(0).setResizable(false);
                tabla_empleados.getColumnModel().getColumn(1).setResizable(false);
                tabla_empleados.getColumnModel().getColumn(2).setResizable(false);
                tabla_empleados.getColumnModel().getColumn(3).setResizable(false);
                tabla_empleados.getColumnModel().getColumn(4).setResizable(false);
            }

            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1)
                    .addContainerGap())
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                    .addContainerGap())
            );

            jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Parámetros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

            jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            jLabel1.setText("Cédula:");

            jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            jLabel2.setText("Nombres:");

            jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            jLabel3.setText("Apellidos:");

            txt_cedula.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
            txt_cedula.setHorizontalAlignment(javax.swing.JTextField.CENTER);

            txt_apellidos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
            txt_apellidos.setHorizontalAlignment(javax.swing.JTextField.CENTER);

            txt_nombres.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
            txt_nombres.setHorizontalAlignment(javax.swing.JTextField.CENTER);

            boton_buscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            boton_buscar.setText("BUSCAR");
            boton_buscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            boton_buscar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    boton_buscarActionPerformed(evt);
                }
            });

            boton_reporte_empleado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            boton_reporte_empleado.setText("REPORTE DE EMPLEADO");
            boton_reporte_empleado.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    boton_reporte_empleadoActionPerformed(evt);
                }
            });

            boton_reporte_horas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            boton_reporte_horas.setText("REPORTE DE HORAS");
            boton_reporte_horas.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    boton_reporte_horasActionPerformed(evt);
                }
            });

            boton_reporte_atrasos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            boton_reporte_atrasos.setText("REPORTE DE ATRASOS");
            boton_reporte_atrasos.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    boton_reporte_atrasosActionPerformed(evt);
                }
            });

            boton_reporte_faltas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            boton_reporte_faltas.setText("REPORTE DE FALTAS");
            boton_reporte_faltas.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    boton_reporte_faltasActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
            jPanel3.setLayout(jPanel3Layout);
            jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(boton_reporte_empleado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addComponent(boton_reporte_horas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boton_reporte_atrasos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boton_reporte_faltas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(boton_reporte_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(boton_reporte_horas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(boton_reporte_atrasos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(boton_reporte_faltas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            );

            jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            jLabel6.setText("Unidad:");

            cmb_unidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

            cmb_cargo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

            jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            jLabel4.setText("Cargo:");

            javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
            jPanel4.setLayout(jPanel4Layout);
            jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6)
                        .addComponent(jLabel4))
                    .addGap(18, 24, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cmb_unidad, 0, 135, Short.MAX_VALUE)
                        .addComponent(cmb_cargo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(6, 6, 6))
            );
            jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(cmb_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(cmb_cargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            boton_lista_completa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            boton_lista_completa.setText("LISTA COMPLETA");
            boton_lista_completa.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    boton_lista_completaActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(30, 30, 30)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(boton_buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(boton_lista_completa)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel1)
                                .addComponent(jLabel3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_cedula)
                                .addComponent(txt_nombres)
                                .addComponent(txt_apellidos, javax.swing.GroupLayout.Alignment.TRAILING))))
                    .addGap(18, 18, 18)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(21, 21, 21))
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_cedula, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_nombres, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(boton_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(boton_lista_completa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Gráficas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

            jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            jLabel5.setText("Tipo:");

            cmb_tipo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            cmb_tipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2D", "3D" }));

            boton_grafica_empleado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            boton_grafica_empleado.setText("GRÁFICA DE EMPLEADO");
            boton_grafica_empleado.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    boton_grafica_empleadoActionPerformed(evt);
                }
            });

            boton_grafica_unidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
            boton_grafica_unidad.setText("GRÁFICA DE UNIDAD");
            boton_grafica_unidad.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    boton_grafica_unidadActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
            jPanel5.setLayout(jPanel5Layout);
            jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(18, 18, 18)
                            .addComponent(cmb_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(boton_grafica_empleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boton_grafica_unidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
            );
            jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmb_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addGap(18, 18, 18)
                    .addComponent(boton_grafica_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(boton_grafica_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void boton_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_buscarActionPerformed

        Filtrar();
        this.boton_lista_completa.setEnabled(true);
        
    }//GEN-LAST:event_boton_buscarActionPerformed

    private void boton_reporte_empleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_reporte_empleadoActionPerformed

        if (bandera) {

            GenerarPDF();
            limpiarTxT();
            MostrarTabla();

            this.bandera = false;

        } else {

            JOptionPane.showMessageDialog(null, "SELECCIONE UN ELEMENTO DE LA TABLA");

        }
    }//GEN-LAST:event_boton_reporte_empleadoActionPerformed

    private void tabla_empleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_empleadosMouseClicked

        if (this.tabla_empleados.getSelectedRow() < 0) {

            this.bandera = false;

        } else {

            this.fila_seleccionada = this.tabla_empleados.getSelectedRow();
            this.bandera = true;

        }
    }//GEN-LAST:event_tabla_empleadosMouseClicked

    private void boton_lista_completaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_lista_completaActionPerformed

        this.boton_lista_completa.setEnabled(false);
        MostrarTabla();
        limpiarTxT();

    }//GEN-LAST:event_boton_lista_completaActionPerformed

    private void boton_reporte_horasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_reporte_horasActionPerformed

        new SeleccionFechaHoras(empleados, registros).setVisible(true);
        limpiarTxT();
        MostrarTabla();

    }//GEN-LAST:event_boton_reporte_horasActionPerformed

    private void boton_reporte_atrasosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_reporte_atrasosActionPerformed

        new SeleccionFechaAtrasos(empleados, registros).setVisible(true);
        limpiarTxT();
        MostrarTabla();

    }//GEN-LAST:event_boton_reporte_atrasosActionPerformed

    private void boton_reporte_faltasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_reporte_faltasActionPerformed

        new SeleccionFechaFaltas(empleados, registros).setVisible(true);
        limpiarTxT();
        MostrarTabla();

    }//GEN-LAST:event_boton_reporte_faltasActionPerformed

    private void boton_grafica_empleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_grafica_empleadoActionPerformed

        if (bandera) {

            String cedula = this.tabla_empleados.getValueAt(this.fila_seleccionada, 2).toString().trim().toUpperCase();
            String tipo = this.cmb_tipo.getSelectedItem().toString().trim();

            new SeleccionFechaGraficaEmpleado(cedula, this.registros, tipo).setVisible(true);
            limpiarTxT();
            MostrarTabla();

            this.bandera = false;

        } else {

            JOptionPane.showMessageDialog(null, "SELECCIONE UN ELEMENTO DE LA TABLA");

        }

    }//GEN-LAST:event_boton_grafica_empleadoActionPerformed

    private void boton_grafica_unidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_grafica_unidadActionPerformed

        String tipo = this.cmb_tipo.getSelectedItem().toString().trim();

        new SeleccionFechaGraficaUnidad(tipo, empleados, registros).setVisible(true);
        limpiarTxT();
        MostrarTabla();

        this.bandera = false;

    }//GEN-LAST:event_boton_grafica_unidadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_buscar;
    private javax.swing.JButton boton_grafica_empleado;
    private javax.swing.JButton boton_grafica_unidad;
    private javax.swing.JButton boton_lista_completa;
    private javax.swing.JButton boton_reporte_atrasos;
    private javax.swing.JButton boton_reporte_empleado;
    private javax.swing.JButton boton_reporte_faltas;
    private javax.swing.JButton boton_reporte_horas;
    private javax.swing.JComboBox<String> cmb_cargo;
    private javax.swing.JComboBox<String> cmb_tipo;
    private javax.swing.JComboBox<String> cmb_unidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla_empleados;
    private javax.swing.JTextField txt_apellidos;
    private javax.swing.JTextField txt_cedula;
    private javax.swing.JTextField txt_nombres;
    // End of variables declaration//GEN-END:variables
}
