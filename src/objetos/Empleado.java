package objetos;

/**
 *
 * @author Johan Tuarez
 */

//Clase encargada de almacenar todos los datos correspondientes
//a los elementos que pertenezcan a la tabla de Empleados
public class Empleado {
    
    //Parámetros que se encuentran en la tabla Empleados
    public int id_empleado, id_unidad, id_cargo, id_jornada;
    public String cedula, nombres, apellidos, telefono, correo, estatus;
    
    //Constructor de la clase
    public Empleado(int id_empleado, int id_unidad, int id_cargo, int id_jornada, String cedula, String nombres, String apellidos, String telefono, String correo, String estatus) {

        this.id_empleado = id_empleado;
        this.id_unidad = id_unidad;
        this.id_cargo = id_cargo;
        this.id_jornada = id_jornada;
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "Empleado{" + "id_empleado=" + id_empleado + ", id_unidad=" + id_unidad + ", id_cargo=" + id_cargo + ", id_jornada=" + id_jornada + ", cedula=" + cedula + ", nombres=" + nombres + ", apellidos=" + apellidos + ", telefono=" + telefono + ", correo=" + correo + ", estatus=" + estatus + '}';
    }

}
