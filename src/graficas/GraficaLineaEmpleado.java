package graficas;

import java.awt.Toolkit;
import java.time.LocalTime;
import java.util.ArrayList;
import objetos.MapeoDatos;
import objetos.Registro;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Johan Tuarez
 */
//Clase encargada de la generación de gráficas de lineas para los datos
//del empleado que se seleccione.
//Se utiliza la librería de iText5 para la creación y modelado de todas las
//de todas las gráficas.
public class GraficaLineaEmpleado {

    private final MapeoDatos datos; //Objeto con los datos a graficar

    //Constructor de la clase
    public GraficaLineaEmpleado(String cedula, int mes, int anio, ArrayList<Registro> registros) {

        this.datos = new MapeoDatos(cedula, mes, anio, registros);

    }

    //Método para que genera la gráfica en modelo 2D
    public void Graficar2D() {

        //Condición que verifica que existen registros y datos del empleado
        if (this.datos.bandera) {

            //Dataset con la información a graficar
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            //Se itera para añadir los datos al dataset
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

                            dataset.setValue(horas_al_dia.getHour(), "HORAS", registro.fecha);

                        } else {

                            horas_al_dia = LocalTime.of(jornada_1.getHour() + jornada_2.getHour(),
                                    valor_m);

                            dataset.setValue(horas_al_dia.getHour(), "HORAS", registro.fecha);

                        }

                    } else if (registro.hora_salida.equalsIgnoreCase("X")
                            && !registro.hora_comida_e.equalsIgnoreCase("X")) {

                        horas_al_dia = MapeoDatos.CalcularHorasTrabajadas(registro.hora_entrada,
                                registro.hora_comida_s);

                        dataset.setValue(horas_al_dia.getHour(), "HORAS", registro.fecha);

                    } else if (registro.hora_salida.equalsIgnoreCase("X")
                            && registro.hora_comida_e.equalsIgnoreCase("X")) {

                        horas_al_dia = MapeoDatos.CalcularHorasTrabajadas(registro.hora_entrada,
                                registro.hora_comida_s);

                        dataset.setValue(horas_al_dia.getHour(), "HORAS", registro.fecha);

                    }

                }
            }

            //Se crea la gráfica en formato de linea con el dataset
            JFreeChart chart = ChartFactory.createLineChart("TOTAL HORAS - " + this.datos.str_mes,
                    "FECHAS", "HORAS", dataset, PlotOrientation.VERTICAL,
                    true, true, false);

            //Se configuran ciertos parámetros para las etiquetas de los ejess
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setCategoryLabelPositions(
                    CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 3));

            //Se crea una ventana para mostrar la gráfica generada
            ChartFrame frame = new ChartFrame("JFreeChart", chart);
            frame.pack();
            frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 700,
                    Toolkit.getDefaultToolkit().getScreenSize().height - 450);
            frame.setVisible(true);

        }

    }

    //Método encargada de generar la gráfica en modelo 3D
    //Sigue la misma metodología que el método 2D
    public void Graficar3D() {

        if (this.datos.bandera) {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

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

                            dataset.setValue(horas_al_dia.getHour(), "HORAS", registro.fecha);

                        } else {

                            horas_al_dia = LocalTime.of(jornada_1.getHour() + jornada_2.getHour(),
                                    valor_m);

                            dataset.setValue(horas_al_dia.getHour(), "HORAS", registro.fecha);

                        }

                    } else if (registro.hora_salida.equalsIgnoreCase("X")
                            && !registro.hora_comida_e.equalsIgnoreCase("X")) {

                        horas_al_dia = MapeoDatos.CalcularHorasTrabajadas(registro.hora_entrada,
                                registro.hora_comida_s);

                        dataset.setValue(horas_al_dia.getHour(), "HORAS", registro.fecha);

                    } else if (registro.hora_salida.equalsIgnoreCase("X")
                            && registro.hora_comida_e.equalsIgnoreCase("X")) {

                        horas_al_dia = MapeoDatos.CalcularHorasTrabajadas(registro.hora_entrada,
                                registro.hora_comida_s);

                        dataset.setValue(horas_al_dia.getHour(), "HORAS", registro.fecha);

                    }

                }

            }

            JFreeChart chart = ChartFactory.createLineChart3D("TOTAL HORAS - " + this.datos.str_mes,
                    "FECHAS", "HORAS", dataset, PlotOrientation.VERTICAL,
                    true, true, false);

            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setCategoryLabelPositions(
                    CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 3));

            ChartFrame frame = new ChartFrame("JFreeChart", chart);
            frame.pack();
            frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 700,
                    Toolkit.getDefaultToolkit().getScreenSize().height - 450);
            frame.setVisible(true);

        }

    }

}
