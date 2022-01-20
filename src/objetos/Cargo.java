package objetos;

/**
 *
 * @author Johan Tuarez
 */

//Clase encargada de almacenar todos los datos correspondientes
//a los elementos que pertenezcan a la tabla de Cargos
public class Cargo {
    
    //Parámetros que se encuentran en la tabla Cargos
    public int id_cargo;
    public String nombre_cargo, estatus;
    
    //Constructor de la clase
    public Cargo(int id_cargo, String nombre_cargo, String estatus) {
        this.id_cargo = id_cargo;
        this.nombre_cargo = nombre_cargo;
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "Cargo{" + "id_cargo=" + id_cargo + ", nombre_cargo=" + nombre_cargo + ", estatus=" + estatus + '}';
    }

}
