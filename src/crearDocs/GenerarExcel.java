package crearDocs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
//Clase encargada de generar un archivo en formato excel con todos
//los registros de entrada y salida almacenados en la base de datos
//hasta la fecha.
//El archivo de excel generado incluye en la hoja 8 columnas con:
//Unidad Operativa, Cedula, Cargo, Nombres, Apellidos, Fecha
//Hora Entrada y Hora de Salida
public class GenerarExcel {

    //Para generar el archivo requiere un ArrayList con la información
    //correspondiente a los 8 columnas
    private ArrayList<Object> data; //ArrayList con toda la data necesaria

    public GenerarExcel(ArrayList<Object> data) {

        this.data = data;
    }

    public void WriterExcel() {

        //Generación de las 8 columnas
        String[] encabezados = new String[]{
            "UNIDAD OPERATIVA",
            "CÉDULA",
            "CARGO",
            "NOMBRES",
            "APELLIDOS",
            "FECHA",
            "HORA ENTRADA",
            "HORA ALMUERZO (SALIDA)",
            "HORA ALMUERZO (ENTRADA)",
            "HORA SALIDA"
        };

        try {

            //Creación del libro/archivo y hoja de excel
            HSSFWorkbook libro = new HSSFWorkbook();
            HSSFSheet hoja = libro.createSheet();
            libro.setSheetName(0, "REGISTROS DE ASISTENCIAS");

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

            //Inserción de todos los datos almacenados en el ArrayList "data"
            //La información ingresada corresponde al tipo de datos que maneja
            //cada columna
            for (int i = 0; i < this.data.size(); ++i) {

                HSSFRow dataRow = hoja.createRow(i + 1);

                Object[] d = (Object[]) this.data.get(i);

                String unidad = (String) d[0];
                String cedula = (String) d[1];
                String cargo = (String) d[2];
                String nombres = (String) d[3];
                String apellidos = (String) d[4];
                String fecha = (String) d[5];
                String hora_entrada = (String) d[6];
                String hora_comida_s = (String) d[7];
                String hora_comida_e = (String) d[8];
                String hora_salida = (String) d[9];

                dataRow.createCell(0).setCellValue(unidad);
                dataRow.createCell(1).setCellValue(cedula);
                dataRow.createCell(2).setCellValue(cargo);
                dataRow.createCell(3).setCellValue(nombres);
                dataRow.createCell(4).setCellValue(apellidos);
                dataRow.createCell(5).setCellValue(fecha);
                dataRow.createCell(6).setCellValue(hora_entrada);
                dataRow.createCell(7).setCellValue(hora_comida_s);
                dataRow.createCell(8).setCellValue(hora_comida_e);
                dataRow.createCell(9).setCellValue(hora_salida);

            }

            //Ventana de elección de la ruta de guardado para el usuario
            JFileChooser seleccionador = new JFileChooser();
            seleccionador.setDialogTitle("ESCOJA LA RUTA DE GUARDADO");
            seleccionador.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            seleccionador.setAcceptAllFileFilterUsed(false);

            int returnValue = seleccionador.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {

                File ruta = seleccionador.getSelectedFile();

                try (FileOutputStream file = new FileOutputStream(ruta + "/REGISTRO.xls")) {

                    libro.write(file);
                    file.close();
                }

                JOptionPane.showMessageDialog(null, "ARCHIVO EXCEL CREADO CORRECTAMENTE");

            }

        } catch (IOException e) {

            System.out.println("ERROR AL GENERAR EL ARCHIVO EXCEL");

        }

    }

}
