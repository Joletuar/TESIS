package graficas;

import java.awt.Toolkit;
import java.util.ArrayList;
import objetos.Empleado;
import objetos.MapeoDatos;
import objetos.Registro;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Johan Tuarez
 */

//Clase encargada de la generación de gráficas de barras para los datos
//de la unidad que se seleccione.
//Se utiliza la librería de iText5 para la creación y modelado de todas las
//de todas las gráficas.
public class GraficaBarrasUnidad {

    private final String mes; //Mes elegido en string
    private final String unidad; //Unidad elegida

    private final int anio; //Año elegido
    private final int int_mes; //Valor entero del mes

    private final boolean bandera_general; //Bandera que indica que existen registros
    private final boolean bandera_atrasos; //Bandera que indica que existen atrasos
    private final boolean bandera_faltas; //Bandera que indica que existen faltas

    private final ArrayList<Empleado> empleados; //Lista de empleados
    private final ArrayList<Empleado> empleados_faltas; //Lista de empleados con faltas
    private final ArrayList<Empleado> empleados_atrasos; //Lista de empleados con atrasos
    private ArrayList<Registro> temp;
    
    //Constructor de la clase
    public GraficaBarrasUnidad(String unidad, int mes, int anio, ArrayList<Empleado> empleados, ArrayList<Registro> registros) {

        this.empleados = empleados;
        this.mes = MapeoDatos.StrMes(mes); //Conversión a string del mes
        this.unidad = unidad.trim();
        this.anio = anio;
        this.int_mes = mes;

        this.empleados_atrasos = new ArrayList<>();
        this.empleados_faltas = new ArrayList<>();
        
        //Se verifica que si existan registros guardados
        if (registros.isEmpty()) {

            this.bandera_general = false;

        } else {
            
            //Lista con los registros filtrados
            this.temp = new ArrayList<>();

            for (Registro temp_r : registros) {

                if (temp_r.fecha.split("/")[1].trim().equals(String.valueOf(mes)) && temp_r.fecha.split("/")[2].trim().equals(String.valueOf(anio))) {

                    this.temp.add(temp_r);

                }

            }
            
            //Se verifica que la lista no este vacía
            if (!this.temp.isEmpty()) {

                this.bandera_general = true;
                
                //Se comienza a llenar las listas de empleados con atrasos t
                //faltas
                for (Empleado empleado : empleados) {

                    MapeoDatos datos = new MapeoDatos(empleado.cedula, mes, anio, this.temp);

                    if (datos.faltas_total > 0) {

                        this.empleados_faltas.add(datos.empleado_seleccionado);

                    }

                    if (datos.atrasos_total > 0) {

                        this.empleados_atrasos.add(datos.empleado_seleccionado);

                    }

                }

            } else {

                this.bandera_general = false;

            }

        }

        this.bandera_atrasos = !this.empleados_atrasos.isEmpty();

        this.bandera_faltas = !this.empleados_faltas.isEmpty();

    }
    
    //Método encargado de generar las gráficas en 2D
    public void Graficar2D() {
        
        //Solo se genera las gráficas si existen registros
        //Esto se comprueba mediante la bandera general
        if (this.bandera_general) {
            
            //Se verifica si existen atrasos
            if (this.bandera_atrasos) {
                
                //Dataset 1 con la información a graficar
                DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
                
                //Se itera para añadir los datos al dataset correspondiente
                for (Empleado e1 : this.empleados_atrasos) {

                    MapeoDatos m1 = new MapeoDatos(e1.cedula, this.int_mes, this.anio, this.temp);

                    dataset1.setValue(m1.atrasos_total, "ATRASOS", m1.empleado_seleccionado.nombres
                            + " " + m1.empleado_seleccionado.apellidos);

                }
                
                //Se crea la primera gráfica de barras
                JFreeChart chart1 = ChartFactory.createBarChart("ATRASOS POR EMPLEADOS - " + this.mes + " - " + this.unidad,
                        "EMPLEADOS", "ATRASOS",
                        dataset1, PlotOrientation.HORIZONTAL, true, true, false);
                
                //Primer frame para mostrar la gráfica
                ChartFrame frame1 = new ChartFrame("JFreeChart", chart1);
                frame1.pack();
                frame1.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 700, 0);
                frame1.setVisible(true);

            }
            
            //Se verifica que existen faltas
            if (this.bandera_faltas) {
                
                //Dataset 2 con la información a graficar
                DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
                
                //Se itera para añadir los datos al dataset correspondiente
                for (Empleado e2 : this.empleados_faltas) {

                    MapeoDatos m2 = new MapeoDatos(e2.cedula, this.int_mes, this.anio, this.temp);

                    dataset2.setValue(m2.faltas_total, "FALTAS", m2.empleado_seleccionado.nombres
                            + " " + m2.empleado_seleccionado.apellidos);

                }
                
                //Se crea la segunda gráfica de barras
                JFreeChart chart2 = ChartFactory.createBarChart("FALTAS POR EMPLEADOS - " + this.mes + " - " + this.unidad,
                        "EMPLEADOS", "FALTAS",
                        dataset2, PlotOrientation.HORIZONTAL, true, true, false);
                
                //Segunda frame para mostrar la gráfica
                ChartFrame frame2 = new ChartFrame("JFreeChart", chart2);
                frame2.pack();
                frame2.setLocation(0, Toolkit.getDefaultToolkit().getScreenSize().height - 450);
                frame2.setVisible(true);

            }
            
            //Dataset 3 con la información a graficar
            DefaultCategoryDataset dataset3 = new DefaultCategoryDataset();
            
            //Se itera para añadir los datos al dataset
            for (Empleado e3 : this.empleados) {

                MapeoDatos m3 = new MapeoDatos(e3.cedula, this.int_mes, this.anio, this.temp);

                if (m3.horas_mensuales > 0) {

                    dataset3.setValue(m3.horas_mensuales, "HORAS", m3.empleado_seleccionado.nombres
                            + " " + m3.empleado_seleccionado.apellidos);
                }

            }
            
            //Creación de la tercera gráfica
            JFreeChart chart2 = ChartFactory.createBarChart("HORAS TOTALES POR EMPLEADOS - " + this.mes + " - " + this.unidad,
                    "EMPLEADOS", "HORAS",
                    dataset3, PlotOrientation.HORIZONTAL, true, true, false);
            
            //Tercer frame para gráfica 3
            ChartFrame frame3 = new ChartFrame("JFreeChart", chart2);
            frame3.pack();
            frame3.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 700,
                    Toolkit.getDefaultToolkit().getScreenSize().height - 450);
            frame3.setVisible(true);

        }

    }
    
    //Método encargado de generar las gráficas en 3D
    //Tiene la misma mecánica que el método 2D
    public void Graficar3D() {

        if (this.bandera_general) {

            if (this.bandera_atrasos) {

                DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();

                for (Empleado e1 : this.empleados_atrasos) {

                    MapeoDatos m1 = new MapeoDatos(e1.cedula, this.int_mes, this.anio, this.temp);

                    dataset1.setValue(m1.atrasos_total, "ATRASOS", m1.empleado_seleccionado.nombres
                            + " " + m1.empleado_seleccionado.apellidos);

                }

                JFreeChart chart1 = ChartFactory.createBarChart3D("ATRASOS POR EMPLEADOS - " + this.mes + " - " + this.unidad,
                        "EMPLEADOS", "ATRASOS",
                        dataset1, PlotOrientation.HORIZONTAL, true, true, false);

                ChartFrame frame1 = new ChartFrame("JFreeChart", chart1);
                frame1.pack();
                frame1.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 700, 0);
                frame1.setVisible(true);

            }

            if (this.bandera_faltas) {

                DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();

                for (Empleado e2 : this.empleados_faltas) {

                    MapeoDatos m2 = new MapeoDatos(e2.cedula, this.int_mes, this.anio, this.temp);

                    dataset2.setValue(m2.faltas_total, "FALTAS", m2.empleado_seleccionado.nombres
                            + " " + m2.empleado_seleccionado.apellidos);

                }

                JFreeChart chart2 = ChartFactory.createBarChart3D("FALTAS POR EMPLEADOS - " + this.mes + " - " + this.unidad,
                        "EMPLEADOS", "FALTAS",
                        dataset2, PlotOrientation.HORIZONTAL, true, true, false);

                ChartFrame frame2 = new ChartFrame("JFreeChart", chart2);
                frame2.pack();
                frame2.setLocation(0, Toolkit.getDefaultToolkit().getScreenSize().height - 450);
                frame2.setVisible(true);

            }

            DefaultCategoryDataset dataset3 = new DefaultCategoryDataset();

            for (Empleado e3 : this.empleados) {

                MapeoDatos m3 = new MapeoDatos(e3.cedula, this.int_mes, this.anio, this.temp);

                if (m3.horas_mensuales > 0) {

                    dataset3.setValue(m3.horas_mensuales, "HORAS", m3.empleado_seleccionado.nombres
                            + " " + m3.empleado_seleccionado.apellidos);
                }

            }

            JFreeChart chart2 = ChartFactory.createBarChart3D("HORAS TOTALES POR EMPLEADOS - " + this.mes + " - " + this.unidad,
                    "EMPLEADOS", "HORAS",
                    dataset3, PlotOrientation.HORIZONTAL, true, true, false);

            ChartFrame frame3 = new ChartFrame("JFreeChart", chart2);
            frame3.pack();
            frame3.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 700,
                    Toolkit.getDefaultToolkit().getScreenSize().height - 450);
            frame3.setVisible(true);

        }

    }

}
