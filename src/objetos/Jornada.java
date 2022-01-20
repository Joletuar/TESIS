package objetos;

/**
 *
 * @author Johan Tuarez
 */

//Clase encargada de almacenar todos los datos correspondientes
//a los elementos que pertenezcan a la tabla de Jornadas
public class Jornada {
    
    //Parámetros que se encuentran en la tabla Jornadas
    public int id_jornada;
    public String hora_entrada, hora_salida, cedula;
    
    //Constructor de la clase
    public Jornada(int id_jornada, String hora_entrada, String hora_salida, String cedula) {
        this.id_jornada = id_jornada;
        this.hora_entrada = hora_entrada;
        this.hora_salida = hora_salida;
        this.cedula = cedula;
    }

    @Override
    public String toString() {
        return "Jornada{" + "id_jornada=" + id_jornada + ", hora_entrada=" + hora_entrada + ", hora_salida=" + hora_salida + "cedula=" + cedula + '}';
    }

}
