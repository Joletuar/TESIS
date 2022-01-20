package graficas;

import java.awt.Toolkit;
import java.time.LocalTime;
import java.util.ArrayList;
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
//del empleado que se seleccione.
//Se utiliza la librería de iText5 para la creación y modelado de todas las
//de todas las gráficas.
public class GraficaBarrasEmpleado {

    private final MapeoDatos datos; //Objeto que contiene los datos a mostrar

    //Constructor de la clase
    public GraficaBarrasEmpleado(String cedula, int mes, int anio, ArrayList<Registro> registros) {

        this.datos = new MapeoDatos(cedula, mes, anio, registros);

    }

    //Método encargado de genear la gráfica de barras con un modelo 2D.
    public void Graficar2D() {

        //Bandera que indica que el empleado tiene registros almacenados.
        if (this.datos.bandera) {

            //Se definen los dataset con la información a graficar.
            DefaultCategoryDataset dataset_1 = new DefaultCategoryDataset();
            DefaultCategoryDataset dataset_2 = new DefaultCategoryDataset();

            //Se itera en los registros del empleado seleccionado
            for (Registro registro : this.datos.registros_empleado) {

                //Se obtiene la información relevante y se la agrega al
                //dataset con cada iteración.
                LocalTime horas_al_dia = null;

                if (!registro.hora_comida_s.equalsIgnoreCase("X")) {

                    if (!registro.hora_salida.equalsIgnoreCase("X")) {

                        LocalTime jornada_1 = MapeoDatos.CalcularHorasTrabajadas(registro.hora_entrada,
                                registro.hora_comida_s);

                        LocalTime jornada_2 = MapeoDatos.CalcularHorasTrabajadas(registro.hora_comida_e,
                                registro.hora_salida);

                        int valor_m = jornada_1.getMinute() + jornada_2.getMinute();

                        if (valor_m >= 60) {

                            int horas_extra = 1;
                            int minutos_new = valor_m - 60;

                            horas_al_dia = LocalTime.of(jornada_1.getHour() + jornada_2.getHour()
                                    + horas_extra, minutos_new);

                            dataset_1.setValue(horas_al_dia.getHour(), "HORAS", registro.fecha);

                        } else {

                            horas_al_dia = LocalTime.of(jornada_1.getHour() + jornada_2.getHour(),
                                    valor_m);

                            dataset_1.setValue(horas_al_dia.getHour(), "HORAS", registro.fecha);

                        }

                    } else if (registro.hora_salida.equalsIgnoreCase("X")
                            && !registro.hora_comida_e.equalsIgnoreCase("X")) {

                        horas_al_dia = MapeoDatos.CalcularHorasTrabajadas(registro.hora_entrada,
                                registro.hora_comida_s);

                        dataset_1.setValue(horas_al_dia.getHour(), "HORAS", registro.fecha);

                    } else if (registro.hora_salida.equalsIgnoreCase("X")
                            && registro.hora_comida_e.equalsIgnoreCase("X")) {

                        horas_al_dia = MapeoDatos.CalcularHorasTrabajadas(registro.hora_entrada,
                                registro.hora_comida_s);

                        dataset_1.setValue(horas_al_dia.getHour(), "HORAS", registro.fecha);

                    }

                }

            }

            dataset_2.setValue(this.datos.asistencia_total, "ASISTENCIAS", "ASISTENCIAS");
            dataset_2.setValue(this.datos.atrasos_total, "ATRASOS", "ATRASOS");
            dataset_2.setValue(this.datos.faltas_total, "FALTAS", "FALTAS");

            //Se crea la gráfica de barras con el dataset formado 1.
            JFreeChart chart_1 = ChartFactory.createBarChart("TOTAL HORAS - " + this.datos.str_mes, "FECHAS", "HORAS",
                    dataset_1, PlotOrientation.HORIZONTAL, true, true, false);

            //Se crea una ventana para mostrar la gráfica formada 1.
            ChartFrame frame_1 = new ChartFrame("JFreeChart", chart_1);
            frame_1.pack();
            frame_1.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 700, 0);
            frame_1.setVisible(true);

            //Se crea la gráfica de barras con el dataset formado 2.
            JFreeChart chart_2 = ChartFactory.createBarChart(this.datos.empleado_seleccionado.nombres + " "
                    + this.datos.empleado_seleccionado.apellidos + " - " + this.datos.str_mes, "DATOS", "VALORES",
                    dataset_2, PlotOrientation.VERTICAL, true, true, false);

            //Se crea una ventana para mostrar la gráfica formada 2.
            ChartFrame frame_2 = new ChartFrame("JFreeChart", chart_2);
            frame_2.pack();
            frame_2.setLocation(0, Toolkit.getDefaultToolkit().getScreenSize().height - 450);
            frame_2.setVisible(true);

        }

    }

//Método encargado de genear la gráfica de barras con un modelo 3D.
    public void Graficar3D() {

        //Bandera que indica que el empleado tiene registros almacenados.
        if (this.datos.bandera) {

            //Se definen los dataset con la información a graficar.
            DefaultCategoryDataset dataset_1 = new DefaultCategoryDataset();
            DefaultCategoryDataset dataset_2 = new DefaultCategoryDataset();

            //Se itera en los registros del empleado seleccionado
            for (Registro registro : this.datos.registros_empleado) {

                //Se obtiene la información relevante y se la agrega al
                //dataset con cada iteración.
                LocalTime horas_al_dia = null;

                if (!registro.hora_comida_s.equalsIgnoreCase("X")) {

                    if (!registro.hora_salida.equalsIgnoreCase("X")) {

                        LocalTime jornada_1 = MapeoDatos.CalcularHorasTrabajadas(registro.hora_entrada,
                                registro.hora_comida_s);

                        LocalTime jornada_2 = MapeoDatos.CalcularHorasTrabajadas(registro.hora_comida_e,
                                registro.hora_salida);

                        int valor_m = jornada_1.getMinute() + jornada_2.getMinute();

                        if (valor_m >= 60) {

                            int horas_extra = 1;
                            int minutos_new = valor_m - 60;

                            horas_al_dia = LocalTime.of(jornada_1.getHour() + jornada_2.getHour()
                                    + horas_extra, minutos_new);

                            dataset_1.setValue(horas_al_dia.getHour(), "HORAS", registro.fecha);

                        } else {

                            horas_al_dia = LocalTime.of(jornada_1.getHour() + jornada_2.getHour(),
                                    valor_m);

                            dataset_1.setValue(horas_al_dia.getHour(), "HORAS", registro.fecha);

                        }

                    } else if (registro.hora_salida.equalsIgnoreCase("X")
                            && !registro.hora_comida_e.equalsIgnoreCase("X")) {

                        horas_al_dia = MapeoDatos.CalcularHorasTrabajadas(registro.hora_entrada,
                                registro.hora_comida_s);

                        dataset_1.setValue(horas_al_dia.getHour(), "HORAS", registro.fecha);

                    } else if (registro.hora_salida.equalsIgnoreCase("X")
                            && registro.hora_comida_e.equalsIgnoreCase("X")) {

                        horas_al_dia = MapeoDatos.CalcularHorasTrabajadas(registro.hora_entrada,
                                registro.hora_comida_s);

                        dataset_1.setValue(horas_al_dia.getHour(), "HORAS", registro.fecha);

                    }

                }

            }

            dataset_2.setValue(this.datos.asistencia_total, "ASISTENCIAS", "ASISTENCIAS");
            dataset_2.setValue(this.datos.atrasos_total, "ATRASOS", "ATRASOS");
            dataset_2.setValue(this.datos.faltas_total, "FALTAS", "FALTAS");

            //Se crea la gráfica de barras con el dataset formado 1.
            JFreeChart chart = ChartFactory.createBarChart3D("TOTAL HORAS - " + this.datos.str_mes, "FECHAS", "HORAS",
                    dataset_1, PlotOrientation.HORIZONTAL, true, true, false);

            //Se crea una ventana para mostrar la gráfica formada 1.
            ChartFrame frame = new ChartFrame("JFreeChart", chart);
            frame.pack();
            frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 700, 0);
            frame.setVisible(true);

            //Se crea la gráfica de barras con el dataset formado 2.
            JFreeChart chart_2 = ChartFactory.createBarChart3D(this.datos.empleado_seleccionado.nombres + " "
                    + this.datos.empleado_seleccionado.apellidos + " - " + this.datos.str_mes, "DATOS", "VALORES",
                    dataset_2, PlotOrientation.VERTICAL, true, true, false);

            //Se crea una ventana para mostrar la gráfica formada 2.
            ChartFrame frame_2 = new ChartFrame("JFreeChart", chart_2);
            frame_2.pack();
            frame_2.setLocation(0, Toolkit.getDefaultToolkit().getScreenSize().height - 450);
            frame_2.setVisible(true);

        }

    }

}
