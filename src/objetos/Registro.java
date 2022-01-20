package objetos;

/**
 *
 * @author Johan Tuarez
 */
//Clase encargada de almacenar todos los datos correspondientes
//a los elementos que pertenezcan a la tabla de Registros
public class Registro {

    //Parámetros que se encuentran en la tabla Registros
    public String fecha, hora_entrada, hora_salida, hora_comida_s, hora_comida_e;
    public int id_registro, id_empleado, id_unidad;

    //Constructor de la clase
    public Registro(String fecha, String hora_entrada, String hora_salida,
            String hora_comida_s, String hora_comida_e, int id_registro,
            int id_empleado, int id_unidad) {

        this.fecha = fecha;
        this.hora_entrada = hora_entrada;
        this.hora_salida = hora_salida;
        this.hora_comida_s = hora_comida_s;
        this.hora_comida_e = hora_comida_e;
        this.id_registro = id_registro;
        this.id_empleado = id_empleado;
        this.id_unidad = id_unidad;
    }

    @Override
    public String toString() {
        return "Registro{" + "fecha=" + fecha + ", hora_entrada=" + hora_entrada + ", hora_salida=" + hora_salida + ", hora_comida_s=" + hora_comida_s + ", hora_comida_e=" + hora_comida_e + ", id_registro=" + id_registro + ", id_empleado=" + id_empleado + ", id_unidad=" + id_unidad + '}';
    }

}
