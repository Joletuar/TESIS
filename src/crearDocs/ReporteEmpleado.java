package crearDocs;

import com.itextpdf.text.BaseColor;
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
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import objetos.MapeoDatos;
import objetos.Registro;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 *
 * @author Johan Tuarez
 */
//Clase encargada de generar un reporte en formato pdf de un empleado
//seleccionado.
//Este reporte contiene datos tales como: horas trabajadas por dia,
//fechas de entrada y salida, total de horas hechas en el mes seleccionado,
//media de la hora de entrada y salida, total de faltas, total de asistencias y
//atrasos
//Se utiliza la libreria iText5 para la creación de todos los archivos en formato pdf
public class ReporteEmpleado {

    private final String espaciado_1 = "            "; //Espaciador para los titulos
    private final String espaciado_2 = "   "; //Espacio entre los titulos y los textos
    private final MapeoDatos mapeo; //Objeto que contiene los datos que será mostrados en el reporte
    private File ruta;

    public ReporteEmpleado(String cedula, int mes, int anio, ArrayList<Registro> registros) {

        this.mapeo = new MapeoDatos(cedula, mes, anio, registros);

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

                ruta = seleccionador.getSelectedFile();

                //Creación del archivo
                PdfWriter.getInstance(reporte, new FileOutputStream(ruta + "/REPORTE-"
                        + this.mapeo.str_mes + "-" + mapeo.empleado_seleccionado.apellidos + ".pdf"));

                reporte.open();

                //Fuente para los textos y titulos del documento
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

                Font font5 = new Font();
                font5.setColor(BaseColor.RED);

                //Titulo dentro del documento
                Paragraph titulo = new Paragraph();
                titulo.setAlignment(Paragraph.ALIGN_CENTER);
                titulo.setFont(font1);
                titulo.add("REPORTE DE EMPLEADO");
                titulo.add(Chunk.NEWLINE);
                titulo.add(Chunk.NEWLINE);
                titulo.add(Chunk.NEWLINE);

                //Titulos o encabezados para la información básica del empleado
                Chunk tnombres = new Chunk();
                tnombres.setFont(font2);
                tnombres.append(espaciado_1 + "NOMBRES:");

                Chunk nombres = new Chunk();
                nombres.setFont(font3);
                nombres.append(espaciado_2 + mapeo.empleado_seleccionado.nombres);

                Chunk tapellidos = new Chunk();
                tapellidos.setFont(font2);
                tapellidos.append(espaciado_1 + "APELLIDOS:");

                Chunk apellidos = new Chunk();
                apellidos.setFont(font3);
                apellidos.append(espaciado_2 + mapeo.empleado_seleccionado.apellidos);

                Chunk tcedula = new Chunk();
                tcedula.setFont(font2);
                tcedula.append(espaciado_1 + "CÉDULA:");

                Chunk cedula = new Chunk();
                cedula.setFont(font3);
                cedula.append(espaciado_2 + mapeo.empleado_seleccionado.cedula);

                Chunk tcargo = new Chunk();
                tcargo.setFont(font2);
                tcargo.append(espaciado_1 + "CARGO:");

                Chunk cargo_c = new Chunk();
                cargo_c.setFont(font3);
                cargo_c.append(espaciado_2 + mapeo.cargo_empleado.nombre_cargo);

                Chunk tunidad = new Chunk();
                tunidad.setFont(font2);
                tunidad.append(espaciado_1 + "U.OPERATIVA:");

                Chunk unidad_c = new Chunk();
                unidad_c.setFont(font3);
                unidad_c.append(espaciado_2 + mapeo.unidad_empleado.nombre_unidad);

                Chunk tjornada = new Chunk();
                tjornada.setFont(font2);
                tjornada.append(espaciado_1 + "JORNADA DE TRABAJO:");

                Chunk jornada_c = new Chunk();
                jornada_c.setFont(font3);
                jornada_c.append(espaciado_2 + mapeo.hora_entrada.toString()
                        + " - " + mapeo.hora_salida.toString());

                //Se añaden los titulos y textos a un objeto de la clase Phrase
                //para maniuplar sus fuentes de manera individual
                Phrase frase_1 = new Phrase();
                frase_1.add(tnombres);
                frase_1.add(nombres);
                frase_1.add(Chunk.NEWLINE);

                Phrase frase_2 = new Phrase();
                frase_2.add(tapellidos);
                frase_2.add(apellidos);
                frase_2.add(Chunk.NEWLINE);

                Phrase frase_3 = new Phrase();
                frase_3.add(tcedula);
                frase_3.add(cedula);
                frase_3.add(Chunk.NEWLINE);

                Phrase frase_4 = new Phrase();
                frase_4.add(tcargo);
                frase_4.add(cargo_c);
                frase_4.add(Chunk.NEWLINE);

                Phrase frase_5 = new Phrase();
                frase_5.add(tunidad);
                frase_5.add(unidad_c);
                frase_5.add(Chunk.NEWLINE);

                Phrase frase_6 = new Phrase();
                frase_6.add(tjornada);
                frase_6.add(jornada_c);
                frase_6.add(Chunk.NEWLINE);
                frase_6.add(Chunk.NEWLINE);
                frase_6.add(Chunk.NEWLINE);

                //Subtitulo
                Paragraph titulo_2 = new Paragraph();
                titulo_2.setAlignment(Paragraph.ALIGN_CENTER);
                titulo_2.setFont(font4);
                titulo_2.add("REGISTRO DE ASISTENCIAS DEL MES - "
                        + mapeo.str_mes.trim() + " - " + mapeo.anio_seleccionado);
                titulo_2.add(Chunk.NEWLINE);
                titulo_2.add(Chunk.NEWLINE);
                titulo_2.add(Chunk.NEWLINE);

                //Se añaden todos los textos y datos creados al documento
                reporte.add(titulo);
                reporte.add(frase_1);
                reporte.add(frase_2);
                reporte.add(frase_3);
                reporte.add(frase_4);
                reporte.add(frase_5);
                reporte.add(frase_6);
                reporte.add(titulo_2);

                //Condición que verifica que existan datos mapeados
                if (mapeo.bandera) {

                    //Headers para la primera tabla
                    Paragraph header_1 = new Paragraph();
                    header_1.add("DÍA");

                    Paragraph header_2 = new Paragraph();
                    header_2.add("HORA ENTRADA");

                    Paragraph header_3 = new Paragraph();
                    header_3.add("ALMUERZO (SALIDA)");

                    Paragraph header_4 = new Paragraph();
                    header_4.add("ALMUERZO (ENTRADA)");

                    Paragraph header_5 = new Paragraph();
                    header_5.add("HORA SALIDA");

                    Paragraph header_6 = new Paragraph();
                    header_6.add("TOTAL HORAS");

                    //Creación de las celdas para la tabla con los headers
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

                    //Creación de la primera tabla
                    PdfPTable registros_tabla = new PdfPTable(6);
                    registros_tabla.setWidths(new int[]{10, 20, 20, 20, 20, 20});

                    //Insercción de las celdas con los headers a la tabla
                    registros_tabla.addCell(columna_1);
                    registros_tabla.addCell(columna_2);
                    registros_tabla.addCell(columna_3);
                    registros_tabla.addCell(columna_4);
                    registros_tabla.addCell(columna_5);
                    registros_tabla.addCell(columna_6);

                    //Iterador para añadir los datos a cada columna respectiva
                    for (Registro registro : mapeo.registros_empleado) {

                        //Se obtiene el dia en formato string
                        String dia = mapeo.getDia(registro.fecha);

                        //Horas trabajadas en el dia
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

                                } else {

                                    horas_al_dia = LocalTime.of(jornada_1.getHour() + jornada_2.getHour(),
                                            valor_m);

                                }

                            } else if (registro.hora_salida.equalsIgnoreCase("X")
                                    && !registro.hora_comida_e.equalsIgnoreCase("X")) {

                                horas_al_dia = MapeoDatos.CalcularHorasTrabajadas(registro.hora_entrada,
                                        registro.hora_comida_s);

                            } else if (registro.hora_salida.equalsIgnoreCase("X")
                                    && registro.hora_comida_e.equalsIgnoreCase("X")) {

                                horas_al_dia = MapeoDatos.CalcularHorasTrabajadas(registro.hora_entrada,
                                        registro.hora_comida_s);

                            }

                        }

                        //Creación de los textos que serán añadidos a la tabla
                        Paragraph c1 = new Paragraph();
                        c1.add(dia);

                        Paragraph c2 = new Paragraph();
                        Paragraph c3 = new Paragraph();
                        Paragraph c4 = new Paragraph();
                        Paragraph c5 = new Paragraph();
                        Paragraph c6 = new Paragraph();

                        //Condición que verifica si el empleado llegó atrasado
                        //el dia actual en el que se encuentra.
                        //Si es verdadero la fuente utilizada tendrá un color rojo
                        //dentro del documento
                        if (MapeoDatos.ConversionHora(registro.hora_entrada).isAfter(mapeo.hora_entrada)) {

                            c2.setFont(font5);
                            c2.add(registro.hora_entrada);

                        } else {

                            c2.add(registro.hora_entrada);

                        }

                        if (registro.hora_comida_s.equals("X")) {

                            c3.setFont(font5);
                            c3.add(registro.hora_comida_s);

                        } else {

                            c3.add(registro.hora_comida_s);

                        }

                        if (registro.hora_comida_e.equals("X")) {

                            c4.setFont(font5);
                            c4.add(registro.hora_comida_e);

                        } else {

                            c4.add(registro.hora_comida_e);

                        }

                        //Condición que verifica si el empleado salió antes de la
                        //hora de salida normal el dia actual en el que se encuentra.
                        //Si es verdadero la fuente utilizada tendrá un color rojo
                        //dentro del documento.
                        //Tambien se verifica el caso en el que no se marcó 
                        //salida.
                        if (!registro.hora_salida.equalsIgnoreCase("X")) {

                            if (MapeoDatos.ConversionHora(registro.hora_salida).isBefore(mapeo.hora_salida)) {

                                c5.setFont(font5);
                                c5.add(registro.hora_salida);

                            } else {

                                c5.add(registro.hora_salida);

                            }

                        } else {

                            c3.setFont(font5);

                            c5.setFont(font5);
                            c5.add(registro.hora_salida);

                        }

                        if (horas_al_dia != null) {

                            c6.add(horas_al_dia.toString());

                        } else {

                            c6.setFont(font5);
                            c6.add("X");

                        }

                        //Creación de las celdas para ingresar la información
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

                        //Insercción de las celdas en la tabla 1
                        registros_tabla.addCell(c11);
                        registros_tabla.addCell(c22);
                        registros_tabla.addCell(c33);
                        registros_tabla.addCell(c44);
                        registros_tabla.addCell(c55);
                        registros_tabla.addCell(c66);

                    }

                    //Insercción de la tabla creada con todas las celdas
                    reporte.add(registros_tabla);
                    reporte.add(Chunk.NEWLINE);
                    reporte.add(Chunk.NEWLINE);

                    //Subtitulo 2
                    Paragraph titulo_3 = new Paragraph();
                    titulo_3.setAlignment(Paragraph.ALIGN_CENTER);
                    titulo_3.setFont(font4);
                    titulo_3.add("RESUMEN CUANTITATIVO");
                    titulo_3.add(Chunk.NEWLINE);
                    titulo_3.add(Chunk.NEWLINE);
                    titulo_3.add(Chunk.NEWLINE);

                    //Textos que irán dentro de la segunda tabla
                    //headers.
                    //Aqui se añaden los datos del resumen cuantitativo del 
                    //objeto mapeo.
                    Paragraph thtrabajadas = new Paragraph();
                    thtrabajadas.add("TOTAL HORAS TRABAJADAS");

                    Paragraph valor_thtrabajadas = new Paragraph();
                    valor_thtrabajadas.add(String.valueOf((float) mapeo.horas_mensuales));

                    Paragraph tasistencia = new Paragraph();
                    tasistencia.add("TOTAL DE ASISTENCIA");

                    Paragraph valor_tasistencia = new Paragraph();
                    valor_tasistencia.add(String.valueOf(mapeo.asistencia_total));

                    Paragraph tatrasos = new Paragraph();
                    tatrasos.add("TOTAL DE ATRASOS");

                    Paragraph valor_tatrasos = new Paragraph();
                    valor_tatrasos.add(String.valueOf(mapeo.atrasos_total));

                    Paragraph promedio_entradat = new Paragraph();
                    promedio_entradat.add("HORA PROMEDIO DE ENTRADA");

                    Paragraph valor_epromedio = new Paragraph();
                    valor_epromedio.add(this.mapeo.h_promedio_entrada.toString());

                    Paragraph promedio_salidat = new Paragraph();
                    promedio_salidat.add("HORA PROMEDIO DE SALIDA");

                    Paragraph valor_spromedio = new Paragraph();
                    valor_spromedio.add(this.mapeo.h_promedio_salida.toString());

                    Paragraph tfaltas = new Paragraph();
                    tfaltas.add("TOTAL DE FALTAS");

                    Paragraph valor_tfaltas = new Paragraph();
                    valor_tfaltas.add(String.valueOf(mapeo.faltas_total));

                    //Creación de las celdas para la segunda tabla
                    PdfPCell celda11 = new PdfPCell(thtrabajadas);
                    celda11.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda11.setUseAscender(true);
                    celda11.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell celda12 = new PdfPCell(valor_thtrabajadas);
                    celda12.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda12.setUseAscender(true);
                    celda12.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell celda21 = new PdfPCell(tasistencia);
                    celda21.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda21.setUseAscender(true);
                    celda21.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell celda22 = new PdfPCell(valor_tasistencia);
                    celda22.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda22.setUseAscender(true);
                    celda22.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell celda31 = new PdfPCell(tatrasos);
                    celda31.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda31.setUseAscender(true);
                    celda31.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell celda32 = new PdfPCell(valor_tatrasos);
                    celda32.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda32.setUseAscender(true);
                    celda32.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell celda41 = new PdfPCell(promedio_entradat);
                    celda41.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda41.setUseAscender(true);
                    celda41.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell celda42 = new PdfPCell(valor_epromedio);
                    celda42.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda42.setUseAscender(true);
                    celda42.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell celda51 = new PdfPCell(promedio_salidat);
                    celda51.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda51.setUseAscender(true);
                    celda51.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell celda52 = new PdfPCell(valor_spromedio);
                    celda52.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda52.setUseAscender(true);
                    celda52.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell celda61 = new PdfPCell(tfaltas);
                    celda61.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda61.setUseAscender(true);
                    celda61.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    PdfPCell celda62 = new PdfPCell(valor_tfaltas);
                    celda62.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda62.setUseAscender(true);
                    celda62.setVerticalAlignment(Element.ALIGN_MIDDLE);

                    //Creación de la tabla 2
                    PdfPTable datos_tabla = new PdfPTable(2);

                    //Insercción de las celdas a la tabla 2
                    datos_tabla.addCell(celda11);
                    datos_tabla.addCell(celda12);
                    datos_tabla.addCell(celda21);
                    datos_tabla.addCell(celda22);
                    datos_tabla.addCell(celda61);
                    datos_tabla.addCell(celda62);
                    datos_tabla.addCell(celda31);
                    datos_tabla.addCell(celda32);
                    datos_tabla.addCell(celda41);
                    datos_tabla.addCell(celda42);
                    datos_tabla.addCell(celda51);
                    datos_tabla.addCell(celda52);

                    //Insercción de la tabla 2 al documento
                    reporte.add(titulo_3);
                    reporte.add(datos_tabla);

                } else {

                    //Informacióm que mostrará el documento en el caso
                    //de que no exista información que mostrar
                    Paragraph alerta = new Paragraph();
                    alerta.setFont(font3);
                    alerta.add("NO EXISTEN REGISTROS");
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

    public void GenerarExcel() {

        if (mapeo.bandera) {

            String[] encabezados = new String[]{
                "DÍA",
                "HORA ENTRADA",
                "ALMUERZO (SALIDA)",
                "ALMUERZO (ENTRADA)",
                "HORA SALIDA",
                "TOTAL HORAS",};

            try {

                //Creación del libro/archivo y hoja de excel
                HSSFWorkbook libro = new HSSFWorkbook();
                HSSFSheet hoja = libro.createSheet();
                libro.setSheetName(0, mapeo.empleado_seleccionado.nombres + " "
                        + mapeo.empleado_seleccionado.apellidos);

                //Seteo de la fuente de las headers
                CellStyle headerStyle = libro.createCellStyle();
                HSSFFont font = libro.createFont();
                font.setBold(true);
                headerStyle.setFont(font);

                //Creación de la fila para los headers
                HSSFRow headerRow = hoja.createRow(0);

                //Inserción de los headers en la hoja
                for (int i = 0; i < encabezados.length; ++i) {

                    String header = encabezados[i];
                    HSSFCell cell = headerRow.createCell(i);
                    cell.setCellStyle(headerStyle);
                    cell.setCellValue(header);

                }

                for (int i = 0; i < mapeo.registros_empleado.size(); i++) {

                    HSSFRow dataRow = hoja.createRow(i + 1);

                    Registro registro = mapeo.registros_empleado.get(i);
                    LocalTime horas_al_dia = null;

                    dataRow.createCell(0).setCellValue(mapeo.getDia(registro.fecha));
                    dataRow.createCell(1).setCellValue(registro.hora_entrada);
                    dataRow.createCell(2).setCellValue(registro.hora_comida_s);
                    dataRow.createCell(3).setCellValue(registro.hora_comida_e);
                    dataRow.createCell(4).setCellValue(registro.hora_salida);

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

                            } else {

                                horas_al_dia = LocalTime.of(jornada_1.getHour() + jornada_2.getHour(),
                                        valor_m);

                            }

                        } else if (registro.hora_salida.equalsIgnoreCase("X")
                                && !registro.hora_comida_e.equalsIgnoreCase("X")) {

                            horas_al_dia = MapeoDatos.CalcularHorasTrabajadas(registro.hora_entrada,
                                    registro.hora_comida_s);

                        } else if (registro.hora_salida.equalsIgnoreCase("X")
                                && registro.hora_comida_e.equalsIgnoreCase("X")) {

                            horas_al_dia = MapeoDatos.CalcularHorasTrabajadas(registro.hora_entrada,
                                    registro.hora_comida_s);

                        }

                    }

                    if (horas_al_dia == null) {

                        dataRow.createCell(5).setCellValue("X");

                    } else {

                        dataRow.createCell(5).setCellValue(horas_al_dia.toString());

                    }

                }

                try (FileOutputStream file = new FileOutputStream(ruta
                        + "/REGISTROS - " + mapeo.empleado_seleccionado.nombres
                        + " " + mapeo.empleado_seleccionado.apellidos + ".xls")) {

                    libro.write(file);
                    file.close();
                }

            } catch (IOException e) {

                System.out.println("ERROR AL GENERAR EL EXCEL DEL EMPLEADO");

            }

        }

    }

}
