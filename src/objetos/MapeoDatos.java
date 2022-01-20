package objetos;

import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import ventanas.Login;

/**
 *
 * @author Johan Tuarez
 */
//Clase encagada de realizar y generar todos los datos de los análisis
//cuantitativos de la registros de entrada y salida del empleado que se 
//seleccione.
//Esta clase corresponde a una parte fundamental para la realizar los
//reportes y gráficas de los datos por empleado o unidad.
public class MapeoDatos {

    public Empleado empleado_seleccionado; //Objeto empleado con las datos del mismo
    public Jornada jornada_empleado; //Objeto Jornada del empleado seleccionado
    public Cargo cargo_empleado; //Objeto Cargo del empleado seleccionado
    public UOperativa unidad_empleado; //Objeto unidad del empleado seleccionado
    public ArrayList<Registro> registros_empleado = new ArrayList<>(); //Registros del empleado en el mes y año desdeadp

    public int mes_seleccionado; //Valor int del mes seleccionado
    public int anio_seleccionado; //Año seleccionador
    public int dias_mes; //Dias totales del mes seleccionado
    public String str_mes; //Mes en formato string

    public int asistencia_total = 0; //Total de asistencias en el mes
    public int atrasos_total = 0; //Total de atrasos en el mes
    public int faltas_total = 0; //Total de faltas en el mes

    public LocalTime hora_entrada; // Hora de Entrada de su Jornada establecida
    public LocalTime hora_salida; // Hora de Salida de su Jornada establecida

    public double horas_mensuales; // Total de horas mensuales
    public LocalTime h_promedio_entrada; //Hora promedio de entrada mensual
    public LocalTime h_promedio_salida; //Hora promedio de salida mensual

    //Bandera que indica que existen registros del empleado en la fecha seleccionada
    //true si existen
    //false si no existen
    public boolean bandera = true;

    //Constructor de la clase
    public MapeoDatos() {

    }

    //Constructor de la clase
    public MapeoDatos(String cedula, int mes, int anio, ArrayList<Registro> registros) {

        this.mes_seleccionado = mes;
        this.anio_seleccionado = anio;
        this.str_mes = MapeoDatos.StrMes(this.mes_seleccionado); //Mes en formato string

        //Aqui se llaman a todos los métodos correspondientes para
        //asignar todos los parámetros y datos necesarios a las variables
        //de la clase
        SetEmpleado(cedula);
        SetJornada();
        SetCargo();
        SetUnidad();
        SetRegistros(registros);
        CalcularDiasMes();

        //Condición que verifica si exite o no registros del empleado
        //seleccionado
        if (!this.registros_empleado.isEmpty()) {

            MapeoValores();

        } else {

            this.bandera = false;

        }

    }

    //Método encargado de generar el objeto de la clase empleado con todos
    //los datos almacenados en la base de datos
    public void SetEmpleado(String cedula) {

        String parametro = "Cedula = '" + cedula + "'";

        this.empleado_seleccionado = Login.bs.ConsultarEmpleado(parametro);

    }

    //Método que se encarga de consultar la jornada de trabajo del empleado 
    //seleccionado
    public void SetJornada() {

        String parametro = "Cedula = '" + this.empleado_seleccionado.cedula + "'";

        this.jornada_empleado = Login.bs.ConsultarJornada(parametro);

        this.hora_entrada = ConversionHora(this.jornada_empleado.hora_entrada);
        this.hora_salida = ConversionHora(this.jornada_empleado.hora_salida);

    }

    //Método encargado de consultar el cargo del empleado
    public void SetCargo() {

        String parametro = "ID_Cargo = " + this.empleado_seleccionado.id_cargo;

        this.cargo_empleado = Login.bs.ConsultarCargo(parametro);

    }

    //Método encargado de consultar la unidad operativa del empleado
    public void SetUnidad() {

        String parametro = "Id_Unidad = " + this.empleado_seleccionado.id_unidad;

        this.unidad_empleado = Login.bs.ConsultarUOperativa(parametro);

    }

    //Método encargado de buscar y filtrar los registros de asistencia del
    //empleado en las fechas elegidas
    public void SetRegistros(ArrayList<Registro> registros) {

        for (Registro registro : registros) {

            int m = Integer.parseInt(registro.fecha.split("/")[1]);
            int y = Integer.parseInt(registro.fecha.split("/")[2]);

            if (registro.id_empleado == empleado_seleccionado.id_empleado
                    && m == mes_seleccionado && y == anio_seleccionado) {

                this.registros_empleado.add(registro);

            }

        }

    }

    //Método que calcula los dias dispobles en el mes seleccionado
    public void CalcularDiasMes() {

        YearMonth yearMonthObject = YearMonth.of(this.anio_seleccionado, this.mes_seleccionado);
        this.dias_mes = yearMonthObject.lengthOfMonth();

    }

    //Método encargado de calcular el total de horas trabajadas en un día
    public static LocalTime CalcularHorasTrabajadas(String hora_entrada, String hora_salida) {

        LocalTime h_entrada = ConversionHora(hora_entrada);
        LocalTime h_salida = ConversionHora(hora_salida);

        if (h_salida != null) {

            long diff = ChronoUnit.MINUTES.between(h_entrada, h_salida);

            Double horas = diff / 60.00;
            Long minutos = diff % 60;

            LocalTime h_trabajadas_dia = LocalTime.of(horas.intValue(), minutos.intValue());

            return h_trabajadas_dia;

        } else {

            return null;

        }

    }

    //Método que obtiene el día de una fecha de registro de asistencia
    public String getDia(String fecha) {

        String dia = fecha.trim().split("/")[0];

        return dia;

    }

    //Método encargado de realizar las conversiones de formato string de las
    //horas de registros guardadas a formato de hora de JAVA
    public static LocalTime ConversionHora(String hora) {

        LocalTime hora_convertida;

        if (hora.equals("X")) {

            return null;

        } else {

            hora_convertida = LocalTime.parse(hora);

            return hora_convertida;

        }

    }

    //Este es el método encargado de realizar los calculos y análisis 
    //de los registros de asistencias del empleado.
    public void MapeoValores() {

        double horas_totales = 0, minutos_totales = 0;
        int h_suma_entradas = 0, m_suma_entradas = 0, h_suma_salidas = 0, m_suma_salidas = 0;

        for (Registro registro : registros_empleado) {

            LocalTime h_trabajadas_dia_j1;
            LocalTime h_trabajadas_dia_j2;

            LocalTime temp_entrada = ConversionHora(registro.hora_entrada);
            LocalTime temp_comida_salida = ConversionHora(registro.hora_comida_s);
            LocalTime temp_comida_entrada = ConversionHora(registro.hora_comida_e);
            LocalTime temp_salida = ConversionHora(registro.hora_salida);

            h_suma_entradas += temp_entrada.getHour();
            m_suma_entradas += temp_entrada.getMinute();

            if (temp_comida_salida != null) {

                if (temp_salida != null) {

                    h_suma_salidas += temp_salida.getHour();
                    m_suma_salidas += temp_salida.getMinute();

                    h_trabajadas_dia_j1 = CalcularHorasTrabajadas(registro.hora_entrada, registro.hora_comida_s);
                    h_trabajadas_dia_j2 = CalcularHorasTrabajadas(registro.hora_comida_e, registro.hora_salida);

                    horas_totales += h_trabajadas_dia_j1.getHour();
                    minutos_totales += h_trabajadas_dia_j1.getMinute();

                    horas_totales += h_trabajadas_dia_j2.getHour();
                    minutos_totales += h_trabajadas_dia_j2.getMinute();

                } else if (temp_salida == null && temp_comida_entrada != null) {

                    h_suma_salidas += temp_comida_salida.getHour();
                    h_suma_salidas += temp_comida_salida.getMinute();

                    h_trabajadas_dia_j1 = CalcularHorasTrabajadas(registro.hora_entrada, registro.hora_comida_s);

                    horas_totales += h_trabajadas_dia_j1.getHour();
                    minutos_totales += h_trabajadas_dia_j1.getMinute();

                } else if (temp_salida == null && temp_comida_entrada == null) {

                    h_suma_salidas += temp_comida_salida.getHour();
                    h_suma_salidas += temp_comida_salida.getMinute();

                    h_trabajadas_dia_j1 = CalcularHorasTrabajadas(registro.hora_entrada, registro.hora_comida_s);

                    horas_totales += h_trabajadas_dia_j1.getHour();
                    minutos_totales += h_trabajadas_dia_j1.getMinute();

                }

            }

            this.asistencia_total += 1;

            if (temp_entrada.isAfter(this.hora_entrada)) {

                this.atrasos_total += 1;

            }

        }

        double horas_extra = minutos_totales / 60;
        horas_totales += horas_extra;

        h_suma_entradas = h_suma_entradas / this.asistencia_total;
        m_suma_entradas = m_suma_entradas / this.asistencia_total;
        h_suma_salidas = h_suma_salidas / this.asistencia_total;
        m_suma_salidas = m_suma_salidas / this.asistencia_total;

        this.horas_mensuales = horas_totales;
        this.h_promedio_entrada = LocalTime.of(h_suma_entradas, m_suma_entradas);
        this.h_promedio_salida = LocalTime.of(h_suma_salidas, m_suma_salidas);

        if (this.dias_mes > this.asistencia_total) {

            this.faltas_total = this.dias_mes - this.asistencia_total - 8;

            if (this.faltas_total < 0) {

                this.faltas_total = 0;

            }

        }

    }

    //Método encargado de transformar el valor entero del mes elegido
    //al valor string del mismo
    public static String StrMes(int mes_int) {

        switch (mes_int) {
            case 1:
                return "ENERO";
            case 2:
                return "FEBRERO";
            case 3:
                return "MARZO";
            case 4:
                return "ABRIL";
            case 5:
                return "MAYO";
            case 6:
                return "JUNIO";
            case 7:
                return "JULIO";
            case 8:
                return "AGOSTO";
            case 9:
                return "SEPTIEMBRE";
            case 10:
                return "OCTUBRE";
            case 11:
                return "NOVIEMBRE";
            case 12:
                return "DICIEMBRE";
            default:
                return null;
        }

    }

}
