package objetos;

/**
 *
 * @author Johan Tuarez
 */

//Clase encargada de almacenar todos los datos correspondientes
//a los elementos que pertenezcan a la tabla de Administradores
public class Administrador {

    //Parámetros que se encuentran en la tabla Administradores
    public int id_admin; 
    public String nombre, apellidos, usuario, contrasena;
    
    //Constructor de la clase
    public Administrador(int id_admin, String nombre, String apellidos, String usuario, String contrasena) {
        this.id_admin = id_admin;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Administrador{" + "id_admin=" + id_admin + ", nombre=" + nombre + ", apellidos=" + apellidos + ", usuario=" + usuario + ", contrasena=" + contrasena + '}';
    }

}
