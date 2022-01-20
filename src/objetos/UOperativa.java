package objetos;

/**
 *
 * @author Johan Tuarez
 */

//Clase encargada de almacenar todos los datos correspondientes
//a los elementos que pertenezcan a la tabla de UOperativas
public class UOperativa {
    
    //Parámetros que se encuentran en la tabla UOperativas
    public int id_unidad;
    public String nombre_unidad, dir_unidad, estatus;

    public UOperativa(int id_unidad, String nombre_unidad, String dir_unidad, String estatus) {
        this.id_unidad = id_unidad;
        this.nombre_unidad = nombre_unidad;
        this.dir_unidad = dir_unidad;
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "UOperativa{" + "id_unidad=" + id_unidad + ", nombre_unidad=" + nombre_unidad + ", dir_unidad=" + dir_unidad + ", estatus=" + estatus + '}';
    }

}
