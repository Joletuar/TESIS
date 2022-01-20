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
//información de faltas de los empleados.
//Esta información depende del mes, año y unidadad operativa elegida por el usario
//Se utiliza la libreria iText5 para la creación de todos los archivos en formato pdf
public class ReporteFaltas {

    //Requiere como principales parámetros el año y mes de los cuales se
    //requiere que se genere el reporte.
    //Tambien requiere la lista de empleados y los registros que se encuentren
    //almacenados en la base de datos.
    private final String espaciado_1 = "            "; //Espaciador para los titulos principales
    private final String espaciado_2 = "   "; //Espacio entre los titulos y los textos
    private final UOperativa unidad; //Objeto que almacena los datos de la unidad operativa elegida
    private final int anio, mes; //Valor entero del mes y año elegido
    private int total_faltas = 0; //Total de faltas de la unidad

    private final ArrayList<MapeoDatos> empleados_faltas = new ArrayList<>();//Lista con los empleados que tienen faltas  >= 1
    private final ArrayList<Registro> registros_seleccionados = new ArrayList<>();//Registros filtrados. Solo se guardan los del mes y año eleigos
    private MapeoDatos empleado_falta;//Objeto que almacena al empleado con más faltas en el mes

    public ReporteFaltas(String unidad, int mes, int anio, ArrayList<Empleado> empleados, ArrayList<Registro> registros) {

        int temp_faltas = 0; //Variable que almacena de manera temporal el total de faltas por cada empleado

        String parametro = "Nombre_Unidad = '" + unidad + "'";

        this.unidad = Login.bs.ConsultarUOperativa(parametro);
        this.anio = anio;
        this.mes = mes;

        //Filtrado de los registros
        for (Registro registro : registros) {

            if (this.mes == Integer.parseInt(registro.fecha.split("/")[1])
                    && this.anio == Integer.parseInt(registro.fecha.split("/")[2])) {

                this.registros_seleccionados.add(registro);

            }

        }

        //Verificación que la lista de empleados no este vacía
        if (!empleados.isEmpty()) {

            for (Empleado empleado : empleados) {

                //Filtro de los empleados que estan activos y pertenecen la unidad elegida por el usuario
                if (empleado.id_unidad == this.unidad.id_unidad && empleado.estatus.equals("ACTIVO")) {

                    //Mapeo de la información
                    MapeoDatos mapeo_empleado = new MapeoDatos(empleado.cedula, mes, anio, this.registros_seleccionados);

                    if (mapeo_empleado.bandera && mapeo_empleado.faltas_total > 0) {

                        this.empleados_faltas.add(mapeo_empleado);

                        //Condición y proceso para el elección del empleado con más faltas en el mes
                        if (mapeo_empleado.faltas_total > temp_faltas) {

                            temp_faltas = mapeo_empleado.faltas_total;
                            this.empleado_falta = mapeo_empleado;

                        }

                    }

                }

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
                PdfWriter.getInstance(reporte, new FileOutputStream(ruta + "/REPORTE DE FALTAS-"
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
                titulo.add("REPORTE DE FALTAS");
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
                titulo_2.add("REGISTRO DE FALTAS DEL MES - " + MapeoDatos.StrMes(this.mes) + " - " + this.anio);
                titulo_2.add(Chunk.NEWLINE);
                titulo_2.add(Chunk.NEWLINE);
                titulo_2.add(Chunk.NEWLINE);

                reporte.add(titulo);
                reporte.add(frase_1);
                reporte.add(frase_2);
                reporte.add(frase_3);
                reporte.add(titulo_2);

                if (!this.empleados_faltas.isEmpty()) {

                    //Creación de los headers para la tabla
                    Paragraph header_1 = new Paragraph();
                    header_1.add("CÉDULA");

                    Paragraph header_2 = new Paragraph();
                    header_2.add("APELLIDOS");

                    Paragraph header_3 = new Paragraph();
                    header_3.add("NOMBRES");

                    Paragraph header_4 = new Paragraph();
                    header_4.add("TOTAL DE FALTAS");

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

                    PdfPTable empleados_tabla = new PdfPTable(4);

                    empleados_tabla.addCell(columna_1);
                    empleados_tabla.addCell(columna_2);
                    empleados_tabla.addCell(columna_3);
                    empleados_tabla.addCell(columna_4);

                    //Iteración de los empleados con faltas de la Lista de empleados faltas
                    //Insercción de todos los datos necesarios en cada columna de la tabla
                    for (MapeoDatos mapeo : this.empleados_faltas) {

                        Paragraph c1 = new Paragraph();
                        c1.getFont().setSize(10);
                        c1.add(mapeo.empleado_seleccionado.cedula);

                        Paragraph c2 = new Paragraph();
                        c2.getFont().setSize(10);
                        c2.add(mapeo.empleado_seleccionado.apellidos);

                        Paragraph c3 = new Paragraph();
                        c3.getFont().setSize(10);
                        c3.add(mapeo.empleado_seleccionado.nombres);

                        Paragraph c4 = new Paragraph();
                        c4.getFont().setSize(10);
                        c4.add(String.valueOf(mapeo.faltas_total));

                        this.total_faltas += mapeo.faltas_total;

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

                        empleados_tabla.addCell(c11);
                        empleados_tabla.addCell(c22);
                        empleados_tabla.addCell(c33);
                        empleados_tabla.addCell(c44);

                    }

                    //Se añade la tabla formulada al documento y 2 saltos de linea
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

                    //Creación de los headers para la segunda tabla
                    //Insercción de los datos para la tabla del resumen cuantitativo
                    Paragraph col11 = new Paragraph();
                    col11.add("MEDIA DE FALTAS EN EL MES");

                    Paragraph col12 = new Paragraph();
                    col12.getFont().setSize(10);
                    col12.add(String.valueOf((float) (this.total_faltas) / (float) (this.empleados_faltas.size())));

                    Paragraph col21 = new Paragraph();
                    col21.add("EMPLEADO CON MÁS FALTAS");

                    Paragraph col22 = new Paragraph();
                    col22.getFont().setSize(10);
                    col22.add(this.empleado_falta.empleado_seleccionado.apellidos + " " + this.empleado_falta.empleado_seleccionado.nombres);

                    Paragraph col31 = new Paragraph();
                    col31.add("TOTAL DE ATRASOS DEL EMPLEADO");

                    Paragraph col32 = new Paragraph();
                    col32.getFont().setSize(10);
                    col32.add(String.valueOf(this.empleado_falta.faltas_total));

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

                    //Creación de la tabla
                    PdfPTable datos_tabla = new PdfPTable(2);

                    //Insercción de los datos en la tabla
                    datos_tabla.addCell(celda11);
                    datos_tabla.addCell(celda12);
                    datos_tabla.addCell(celda21);
                    datos_tabla.addCell(celda22);
                    datos_tabla.addCell(celda31);
                    datos_tabla.addCell(celda32);

                    //Insercción de la tabla al documento
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

            }

            //Cerrado del documento
            reporte.close();

            JOptionPane.showMessageDialog(null, "REPORTE CREADO CORRECTAMENTE");

        } catch (DocumentException | HeadlessException | FileNotFoundException e) {

            JOptionPane.showMessageDialog(null, "ERROR AL GENERAR EL REPORTE");
            reporte.close();

        }

    }

}
