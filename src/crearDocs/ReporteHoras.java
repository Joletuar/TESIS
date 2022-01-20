package crearDocs;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import objetos.Empleado;
import objetos.MapeoDatos;
import objetos.Registro;
import objetos.UOperativa;
import ventanas.Login;

/**
 *
 * @author Johan Tuarez
 */
//Clase encargada de la creación de un archivo en formato pdf con la 
//información de horas de los empleados.
//Esta información depende del mes, año y unidadad operativa elegida por el usario
//Se utiliza la libreria iText5 para la creación de todos los archivos en formato pdf
public class ReporteHoras {

    //Requiere como principales parámetros el año y mes de los cuales se
    //requiere que se genere el reporte.
    //Tambien requiere la lista de empleados y los registros que se encuentren
    //almacenados en la base de datos.
    private final String espaciado_1 = "            "; //Espaciador para los titulos principales
    private final String espaciado_2 = "   "; //Espacio entre los titulos y los textos
    private ArrayList<Empleado> empleados = new ArrayList<>(); //Lista con los empleados
    private final ArrayList<Registro> registros_seleccionados = new ArrayList<>(); //Registros filtrados
    private final int anio, mes; //Valor entero del mes y año elegido
    private final UOperativa unidad; //Objeto que almacena los datos de la unidad operativa elegida

    private int contador_empleados = 0; //Total de empleados

    private int h_entrada_total = 0; //Hora de entrada total
    private int m_entrada_total = 0; //Minutos de entrada total
    private int h_salida_total = 0; //Hora de salida total
    private int m_salida_total = 0; //Minutos de entrada total
    private float total_horas = 0; //Total de horas hechas por la unidad en el mes

    private float media_horas_total; //Promedio de horas en el mes hechas por la unidad
    private LocalTime media_entrada; //Media de hora de entrada en el mes
    private LocalTime media_salida; //Media de hora de salida en el mes

    public ReporteHoras(int anio, int mes, String unidad, ArrayList<Empleado> empleados, ArrayList<Registro> registros) {

        String parametro = "Nombre_Unidad = '" + unidad + "'";

        this.empleados = empleados;
        this.anio = anio;
        this.mes = mes;
        this.unidad = Login.bs.ConsultarUOperativa(parametro);

        //Filtro de los registros
        for (Registro registro : registros) {

            if (this.mes == Integer.parseInt(registro.fecha.split("/")[1])
                    && this.anio == Integer.parseInt(registro.fecha.split("/")[2])) {

                this.registros_seleccionados.add(registro);

            }

        }

    }

    public void GenerarReporte() {

        Document reporte = new Document();

        try {

            //Ventana de elección de ruta de guardado para el usuario
            JFileChooser seleccionador = new JFileChooser();
            seleccionador.setDialogTitle("ESCOJA LA RUTA DE GUARDADO");
            seleccionador.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            seleccionador.setAcceptAllFileFilterUsed(false);

            int returnValue = seleccionador.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {

                File ruta = seleccionador.getSelectedFile();

                //Creación del archivo
                PdfWriter.getInstance(reporte, new FileOutputStream(ruta + "/REPORTE DE HORAS-"
                        + MapeoDatos.StrMes(this.mes) + ".pdf"));

                reporte.open();

                //Se definen diferentes fuentes para los textos dentro del pdf
                Font font1 = new Font();
                font1.setFamily(Font.FontFamily.HELVETICA.name());
                font1.setStyle(Font.BOLD);
                font1.setSize(24);

                Font font2 = new Font();
                font2.setFamily(Font.FontFamily.HELVETICA.name());
                font2.setStyle(Font.BOLD);
                font2.setSize(12);

                Font font3 = new Font();
                font3.setFamily(Font.FontFamily.HELVETICA.name());
                font3.setStyle(Font.NORMAL);
                font3.setSize(12);

                Font font4 = new Font();
                font4.setFamily(Font.FontFamily.HELVETICA.name());
                font4.setStyle(Font.BOLD);
                font4.setSize(14);

                //Titulo dentro del documento/reporte
                Paragraph titulo = new Paragraph();
                titulo.setAlignment(Paragraph.ALIGN_CENTER);
                titulo.setFont(font1);
                titulo.add("REPORTE DE HORAS");
                titulo.add(Chunk.NEWLINE);
                titulo.add(Chunk.NEWLINE);
                titulo.add(Chunk.NEWLINE);

                //Creación de las cabeceras con información relevante
                Chunk tanio = new Chunk();
                tanio.setFont(font2);
                tanio.append(espaciado_1 + "AÑO:");

                Chunk vanio = new Chunk();
                vanio.setFont(font3);
                vanio.append(espaciado_2 + String.valueOf(this.anio));

                Chunk tmes = new Chunk();
                tmes.setFont(font2);
                tmes.append(espaciado_1 + "MES:");

                Chunk vmes = new Chunk();
                vmes.setFont(font3);
                vmes.append(espaciado_2 + MapeoDatos.StrMes(this.mes));

                Chunk tunidad = new Chunk();
                tunidad.setFont(font2);
                tunidad.append(espaciado_1 + "U.OPERATIVA:");

                Chunk vunidad = new Chunk();
                vunidad.setFont(font3);
                vunidad.append(espaciado_2 + this.unidad.nombre_unidad);

                //Incrustación en un objeto de tipo Phrase para usar fuentes diferentes los datos
                Phrase frase_1 = new Phrase();
                frase_1.add(tanio);
                frase_1.add(vanio);
                frase_1.add(Chunk.NEWLINE);

                Phrase frase_2 = new Phrase();
                frase_2.add(tmes);
                frase_2.add(vmes);
                frase_2.add(Chunk.NEWLINE);

                Phrase frase_3 = new Phrase();
                frase_3.add(tunidad);
                frase_3.add(vunidad);
                frase_3.add(Chunk.NEWLINE);
                frase_3.add(Chunk.NEWLINE);
                frase_3.add(Chunk.NEWLINE);

                Paragraph titulo_2 = new Paragraph();
                titulo_2.setAlignment(Paragraph.ALIGN_CENTER);
                titulo_2.setFont(font4);
                titulo_2.add("REGISTRO DE HORAS DEL MES - " + MapeoDatos.StrMes(this.mes) + " - " + this.anio);
                titulo_2.add(Chunk.NEWLINE);
                titulo_2.add(Chunk.NEWLINE);
                titulo_2.add(Chunk.NEWLINE);

                reporte.add(titulo);
                reporte.add(frase_1);
                reporte.add(frase_2);
                reporte.add(frase_3);
                reporte.add(titulo_2);

                if (!this.empleados.isEmpty() && !this.registros_seleccionados.isEmpty()) {

                    //Creación de los headers para la tabla
                    Paragraph header_1 = new Paragraph();
                    header_1.add("CÉDULA");

                    Paragraph header_2 = new Paragraph();
                    header_2.add("APELLIDOS");

                    Paragraph header_3 = new Paragraph();
                    header_3.add("NOMBRES");

                    Paragraph header_4 = new Paragraph();
                    header_4.add("TOTAL HORAS");

                    Paragraph header_5 = new Paragraph();
                    header_5.add("ENTRADA PROMEDIO");

                    Paragraph header_6 = new Paragraph();
                    header_6.add("SALIDA PROMEDIO");

                    //Insercción de los headers por cada columna
                    PdfPCell columna_1 = new PdfPCell(header_1);
                    columna_1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    columna_1.setUseAscender(true);
                    columna_1.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell columna_2 = new PdfPCell(header_2);
                    columna_2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    columna_2.setUseAscender(true);
                    columna_2.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell columna_3 = new PdfPCell(header_3);
                    columna_3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    columna_3.setUseAscender(true);
                    columna_3.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell columna_4 = new PdfPCell(header_4);
                    columna_4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    columna_4.setUseAscender(true);
                    columna_4.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell columna_5 = new PdfPCell(header_5);
                    columna_5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    columna_5.setUseAscender(true);
                    columna_5.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell columna_6 = new PdfPCell(header_6);
                    columna_6.setHorizontalAlignment(Element.ALIGN_CENTER);
                    columna_6.setUseAscender(true);
                    columna_6.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    //Creación de la tabla
                    PdfPTable empleados_tabla = new PdfPTable(6);
                    empleados_tabla.setWidths(new int[]{20, 22, 20, 15, 22, 22});

                    //Insercción de los headers en la tabla
                    empleados_tabla.addCell(columna_1);
                    empleados_tabla.addCell(columna_2);
                    empleados_tabla.addCell(columna_3);
                    empleados_tabla.addCell(columna_4);
                    empleados_tabla.addCell(columna_5);
                    empleados_tabla.addCell(columna_6);

                    //Iteración para insertar los datos por cada fila y columna 
                    for (Empleado empleado : this.empleados) {

                        //Verificación de que el empleado este activo pertenezca a la unidad de interes
                        if (empleado.id_unidad == unidad.id_unidad && empleado.estatus.equals("ACTIVO")) {

                            this.contador_empleados += 1;

                            //Mapeo de los datos por empleado
                            MapeoDatos mapeo_empleado = new MapeoDatos(empleado.cedula, this.mes, this.anio, this.registros_seleccionados);

                            this.total_horas += mapeo_empleado.horas_mensuales;

                            Paragraph c1 = new Paragraph();
                            c1.getFont().setSize(10);
                            c1.add(empleado.cedula);

                            Paragraph c2 = new Paragraph();
                            c2.getFont().setSize(10);
                            c2.add(empleado.apellidos);

                            Paragraph c3 = new Paragraph();
                            c3.getFont().setSize(10);
                            c3.add(empleado.nombres);

                            Paragraph c4 = new Paragraph();
                            c4.getFont().setSize(10);
                            c4.add(String.valueOf((float) mapeo_empleado.horas_mensuales));

                            Paragraph c5 = new Paragraph();
                            c5.getFont().setSize(10);

                            Paragraph c6 = new Paragraph();
                            c6.getFont().setSize(10);

                            //Condicional que verifica que el empleado tenga un promedio de hora de entrada
                            if (mapeo_empleado.h_promedio_entrada != null) {

                                AnadirValoresEntrada(mapeo_empleado.h_promedio_entrada);
                                c5.add(mapeo_empleado.h_promedio_entrada.toString());

                            } else {

                                c5.add("X");

                            }

                            //Condicional que verifica que el empleado tenga un promedio de hora de salida
                            if (mapeo_empleado.h_promedio_salida != null) {

                                AnadirValoresSalida(mapeo_empleado.h_promedio_salida);
                                c6.add(mapeo_empleado.h_promedio_salida.toString());

                            } else {

                                c6.add("X");

                            }

                            //Creación de las celdas para la tabla
                            PdfPCell c11 = new PdfPCell(c1);
                            c11.setHorizontalAlignment(Element.ALIGN_CENTER);
                            c11.setUseAscender(true);
                            c11.setVerticalAlignment(Element.ALIGN_MIDDLE);

                            PdfPCell c22 = new PdfPCell(c2);
                            c22.setHorizontalAlignment(Element.ALIGN_CENTER);
                            c22.setUseAscender(true);
                            c22.setVerticalAlignment(Element.ALIGN_MIDDLE);

                            PdfPCell c33 = new PdfPCell(c3);
                            c33.setHorizontalAlignment(Element.ALIGN_CENTER);
                            c33.setUseAscender(true);
                            c33.setVerticalAlignment(Element.ALIGN_MIDDLE);

                            PdfPCell c44 = new PdfPCell(c4);
                            c44.setHorizontalAlignment(Element.ALIGN_CENTER);
                            c44.setUseAscender(true);
                            c44.setVerticalAlignment(Element.ALIGN_MIDDLE);

                            PdfPCell c55 = new PdfPCell(c5);
                            c55.setHorizontalAlignment(Element.ALIGN_CENTER);
                            c55.setUseAscender(true);
                            c55.setVerticalAlignment(Element.ALIGN_MIDDLE);

                            PdfPCell c66 = new PdfPCell(c6);
                            c66.setHorizontalAlignment(Element.ALIGN_CENTER);
                            c66.setUseAscender(true);
                            c66.setVerticalAlignment(Element.ALIGN_MIDDLE);

                            //Insercciónde las celdas con los datos en la tabla
                            empleados_tabla.addCell(c11);
                            empleados_tabla.addCell(c22);
                            empleados_tabla.addCell(c33);
                            empleados_tabla.addCell(c44);
                            empleados_tabla.addCell(c55);
                            empleados_tabla.addCell(c66);

                        }

                    }

                    //Ejecución del metodo Mapeo
                    Mapeo();

                    //Insercción de la tabla al documento y 2 espacios posteriores
                    reporte.add(empleados_tabla);
                    reporte.add(Chunk.NEWLINE);
                    reporte.add(Chunk.NEWLINE);

                    Paragraph titulo_3 = new Paragraph();
                    titulo_3.setAlignment(Paragraph.ALIGN_CENTER);
                    titulo_3.setFont(font4);
                    titulo_3.add("RESUMEN CUANTITATIVO");
                    titulo_3.add(Chunk.NEWLINE);
                    titulo_3.add(Chunk.NEWLINE);
                    titulo_3.add(Chunk.NEWLINE);

                    //Creación de los heahders para la segunda tabla de 
                    //resumen cuantitativo
                    Paragraph col11 = new Paragraph();
                    col11.add("MEDIA DE HORAS EN EL MES");

                    Paragraph col12 = new Paragraph();
                    col12.add(String.valueOf(this.media_horas_total));

                    Paragraph col21 = new Paragraph();
                    col21.add("MEDIA DE HORA ENTRADA");

                    Paragraph col22 = new Paragraph();
                    col22.add(this.media_entrada.toString());

                    Paragraph col31 = new Paragraph();
                    col31.add("MEDIA DE HORA DE SALIDA");

                    Paragraph col32 = new Paragraph();
                    col32.add(this.media_salida.toString());

                    //Creación de las celdas para la segunda columna
                    PdfPCell celda11 = new PdfPCell(col11);
                    celda11.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda11.setUseAscender(true);
                    celda11.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell celda12 = new PdfPCell(col12);
                    celda12.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda12.setUseAscender(true);
                    celda12.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell celda21 = new PdfPCell(col21);
                    celda21.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda21.setUseAscender(true);
                    celda21.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell celda22 = new PdfPCell(col22);
                    celda22.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda22.setUseAscender(true);
                    celda22.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell celda31 = new PdfPCell(col31);
                    celda31.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda31.setUseAscender(true);
                    celda31.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell celda32 = new PdfPCell(col32);
                    celda32.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda32.setUseAscender(true);
                    celda32.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    //Creación de la segunda tabla
                    PdfPTable datos_tabla = new PdfPTable(2);

                    //Insercción de las celdas a la segunda tabla con la información
                    datos_tabla.addCell(celda11);
                    datos_tabla.addCell(celda12);
                    datos_tabla.addCell(celda21);
                    datos_tabla.addCell(celda22);
                    datos_tabla.addCell(celda31);
                    datos_tabla.addCell(celda32);

                    //Insercción de la tabla 2 al documento
                    reporte.add(titulo_3);
                    reporte.add(datos_tabla);

                } else {

                    //Informacióm que mostrará el documento en el caso
                    //de que no exista información que mostrar
                    Paragraph alerta = new Paragraph();
                    alerta.setFont(font3);
                    alerta.add("NO HAY DATOS QUE MOSTRAR");
                    alerta.setAlignment(Element.ALIGN_CENTER);

                    reporte.add(alerta);

                }
                
                //Cerrado del documento
                reporte.close();

                JOptionPane.showMessageDialog(null, "REPORTE CREADO CORRECTAMENTE");

            }

        } catch (DocumentException | HeadlessException | FileNotFoundException e) {

            JOptionPane.showMessageDialog(null, "ERROR AL GENERAR EL REPORTE");
            reporte.close();

        }

    }

    public void AnadirValoresEntrada(LocalTime hora_e) {

        this.h_entrada_total += hora_e.getHour();
        this.m_entrada_total += hora_e.getMinute();

    }

    public void AnadirValoresSalida(LocalTime hora_e) {

        this.h_salida_total += hora_e.getHour();
        this.m_salida_total += hora_e.getMinute();

    }

    public void Mapeo() {
        
        //Metodo encargado de calcular media de horas en el mes de la unidad
        //El promedio de hora de entrada y salida en el mes
        
        this.media_entrada = LocalTime.of(Media(this.h_entrada_total), Media(this.m_entrada_total));
        this.media_salida = LocalTime.of(Media(this.h_salida_total), Media(this.m_salida_total));
        this.media_horas_total = Media(this.total_horas);

    }

    public int Media(int valor) {

        int media_valor = valor / this.contador_empleados;
        return media_valor;

    }

    public float Media(float valor) {

        float media_valor = valor / this.contador_empleados;
        return media_valor;

    }

}
