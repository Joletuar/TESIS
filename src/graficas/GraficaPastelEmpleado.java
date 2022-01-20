package graficas;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
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

//Clase encargada de generar y graficar una gráfica de tipo pastel del empleado
//seleccionado.
//Para realizar la modelación y creación de esta gráfica se utiliza la libreria
//iTexT5 para todo lo que se requiera.
public class GraficaPastelEmpleado {

    private final MapeoDatos datos; //Objeto con los datos a graficar
    
    //Constructor de la clase
    public GraficaPastelEmpleado(String cedula, int mes, int anio, ArrayList<Registro> registros) {

        this.datos = new MapeoDatos(cedula, mes, anio, registros);

    }
    
    //Método encargado de realizar la gráfica en formato 2D
    public void Graficar2D() {
        
        //Bandera que verifica que existen registros del empleado
        if (this.datos.bandera) {
            
            //Dataset con la información que se va a graficar
            DefaultPieDataset dataset = new DefaultPieDataset();
            
            //Se añaden los datos al dataset creado
            //Cada dato se añade por categoría
            dataset.setValue("ASISTENCIAS (" + (int) this.datos.asistencia_total + ")", this.datos.asistencia_total);
            dataset.setValue("ATRASOS (" + this.datos.atrasos_total + ")", this.datos.atrasos_total);
            dataset.setValue("FALTAS (" + this.datos.faltas_total + ")", this.datos.faltas_total);
            
            //Se crea la gráfica en formato de pastel con el dataset creado
            //anteriormente
            JFreeChart chart = ChartFactory.createPieChart(this.datos.empleado_seleccionado.nombres + " "
                    + this.datos.empleado_seleccionado.apellidos + " - " + this.datos.str_mes, dataset, true, true, true);
            
            //Se especifica el formato y numero de decimales para los datos
            //de tipo decimal o float/double
            DecimalFormat df = new DecimalFormat("0.00%");
            NumberFormat nf = NumberFormat.getNumberInstance();
            StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0} {2}", nf, df);
            
                //Se configura ciertos parámetros de la gráfica
            PiePlot pieplot = (PiePlot) chart.getPlot();
            pieplot.setLabelGenerator(sp1);
            
            //Se genera la ventana donde se va a mostar la gráfica creada
            ChartFrame frame = new ChartFrame("JFreeChart", chart);
            frame.pack();
            frame.setVisible(true);

        } else {

            JOptionPane.showMessageDialog(null, "NO EXISTEN DATOS PARA GRAFICAR");

        }

    }
    
    //Método encargado de generar la gráfica en modelo 3D
    //Sigue el mismo esquema que el método para el modelo 2D
    public void Graficar3D() {

        if (this.datos.bandera) {

            DefaultPieDataset dataset = new DefaultPieDataset();

            dataset.setValue("ASISTENCIAS (" + (int) this.datos.asistencia_total + ")", this.datos.asistencia_total);
            dataset.setValue("ATRASOS (" + this.datos.atrasos_total + ")", this.datos.atrasos_total);
            dataset.setValue("FALTAS (" + this.datos.faltas_total + ")", this.datos.faltas_total);

            JFreeChart chart = ChartFactory.createPieChart3D(this.datos.empleado_seleccionado.nombres + " "
                    + this.datos.empleado_seleccionado.apellidos + " - " + this.datos.str_mes, dataset, true, true, true);

            DecimalFormat df = new DecimalFormat("0.00%");
            NumberFormat nf = NumberFormat.getNumberInstance();
            StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0} {2}", nf, df);

            PiePlot pieplot = (PiePlot) chart.getPlot();
            pieplot.setLabelGenerator(sp1);

            ChartFrame frame = new ChartFrame("JFreeChart", chart);
            
            //Parámetro de profundidad y vista para el modelo 3D de la gráfica
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
