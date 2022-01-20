package graficas;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import objetos.Empleado;
import objetos.MapeoDatos;
import objetos.Registro;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

/**
 *
 * @author Johan Tuarez
 */
//Clase encargada de la generación de una gráfica de tipo pastel sobre
//los datos de la unidad seleccionada.
//Para la creación y modelo de esta gráfica, se utiliza la librería iText5
//para todo.
public class GraficaPastelUnidad {

    private final String mes; //String del mes elegido
    private final String unidad; //Unidad elegida

    private int asistencias_totales = 0; //Asistencia totales
    private int atrasos_totales = 0; //Atrasos totales 
    private int faltas_totales = 0; //Faltas totales

    private final boolean bandera; //Bandera indicando si existe o no registros

    //Constructor de la clase
    public GraficaPastelUnidad(String unidad, int mes, int anio, ArrayList<Empleado> empleados, ArrayList<Registro> registros) {

        this.mes = MapeoDatos.StrMes(mes);
        this.unidad = unidad.trim();

        //Se verifica que existan registros disponibles
        if (registros.isEmpty()) {

            this.bandera = false;

        } else {

            //Se filtran los registros, a solamente los deseados
            ArrayList<Registro> temp = new ArrayList<>();

            for (Registro temp_r : registros) {

                if (temp_r.fecha.split("/")[1].trim().equals(String.valueOf(mes)) && temp_r.fecha.split("/")[2].trim().equals(String.valueOf(anio))) {

                    temp.add(temp_r);

                }

            }

            //Se añaden las asistencia, faltas y atrasos que tiene cada
            //empleado
            if (!temp.isEmpty()) {

                this.bandera = true;

                for (Empleado empleado : empleados) {

                    MapeoDatos datos = new MapeoDatos(empleado.cedula, mes, anio, temp);

                    if (datos.unidad_empleado.nombre_unidad.equals(this.unidad)) {

                        this.asistencias_totales += datos.asistencia_total;
                        this.atrasos_totales += datos.atrasos_total;
                        this.faltas_totales += datos.faltas_total;

                    }

                }

            } else {

                this.bandera = false;

            }

        }

    }

    //Método encargada de generar la gráfica con modelo 2D
    public void Graficar2D() {

        //Se verifica si existieron registros con la bandera
        if (this.bandera) {

            //Se crea un dataset para agregar información
            DefaultPieDataset dataset = new DefaultPieDataset();

            //Se agregan los datos al dataset por categorias
            dataset.setValue("ASISTENCIAS (" + this.asistencias_totales + ")", this.asistencias_totales);
            dataset.setValue("ATRASOS (" + this.atrasos_totales + ")", this.atrasos_totales);
            dataset.setValue("FALTAS (" + this.faltas_totales + ")", this.faltas_totales);

            //Se crea la gráfica de pastel con el dataset
            JFreeChart chart = ChartFactory.createPieChart("RESUMEN DE " + this.unidad + " - " + this.mes, dataset, true, true, true);

            //Se da formato a los float/double
            DecimalFormat df = new DecimalFormat("0.00%");
            NumberFormat nf = NumberFormat.getNumberInstance();
            StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0} {2}", nf, df);

            PiePlot pieplot = (PiePlot) chart.getPlot();
            pieplot.setLabelGenerator(sp1);

            //Se crea una ventana para mostrar la gráfica creada
            ChartFrame frame = new ChartFrame("JFreeChart", chart);
            frame.pack();
            frame.setVisible(true);

        } else {

            JOptionPane.showMessageDialog(null, "NO EXISTEN DATOS PARA GRAFICAR");

        }

    }

    //Método encargada de generar la gráfica de pastel con modelo 3D
    //Sigue el mismo esquema que el método para el modelo 2D
    public void Graficar3D() {

        if (this.bandera) {

            DefaultPieDataset dataset = new DefaultPieDataset();

            dataset.setValue("ASISTENCIAS (" + this.asistencias_totales + ")", this.asistencias_totales);
            dataset.setValue("ATRASOS (" + this.atrasos_totales + ")", this.atrasos_totales);
            dataset.setValue("FALTAS (" + this.faltas_totales + ")", this.faltas_totales);

            JFreeChart chart = ChartFactory.createPieChart3D("RESUMEN DE " + this.unidad + " - " + this.mes, dataset, true, true, true);

            DecimalFormat df = new DecimalFormat("0.00%");
            NumberFormat nf = NumberFormat.getNumberInstance();
            StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0} {2}", nf, df);

            PiePlot pieplot = (PiePlot) chart.getPlot();
            pieplot.setLabelGenerator(sp1);

            ChartFrame frame = new ChartFrame("JFreeChart", chart);

            //Parámetros que modifican la perspectiva del gráfico 3D
            PiePlot3D pieplot3d = (PiePlot3D) chart.getPlot();
            pieplot3d.setDepthFactor(0.5);
            pieplot3d.setStartAngle(290D);
            pieplot3d.setDirection(Rotation.CLOCKWISE);
            pieplot3d.setForegroundAlpha(0.5F);

            frame.pack();
            frame.setVisible(true);

        } else {

            JOptionPane.showMessageDialog(null, "NO EXISTEN DATOS PARA GRAFICAR");

        }

    }

}
