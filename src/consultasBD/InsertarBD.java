package consultasBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Johan Tuarez
 */
//Clase encargada de insertar nuevos datos dentro de la base de datos
//Inserta datos en la mayoria de tablas
//Requiere un objeto de la clase Connection en el construtor
//para ejecutar todas las peticiones
public class InsertarBD {

    private Connection cn; //Objeto para la conexión con la BD

    public InsertarBD(Connection cn) {

        this.cn = cn;
    }

    //Método encargado de ingresar nuevos Empleados
    //Recibe como parámetros todos los datos que se encuentren
    //dentro de la tabla Empleados
    //Aqui se incluye el ID dado que este parámetro no es autoincrementable
    public void InsertarEmpleado(int id, int id_unidad, int id_cargo, int id_jornada, String cedula, String nombres, String apellidos, String telefono, String correo) {

        String sql = "INSERT INTO Empleados VALUES (?,?,?,?,?,?,?,?,?,?)";

        try {

            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setInt(1, id);
            pst.setInt(2, id_unidad);
            pst.setInt(3, id_cargo);
            pst.setInt(4, id_jornada);
            pst.setString(5, cedula);
            pst.setString(6, nombres);
            pst.setString(7, apellidos);
            pst.setString(8, telefono);
            pst.setString(9, correo);
            pst.setString(10, "ACTIVO");
            pst.executeUpdate();
            pst.close();

        } catch (SQLException ex) {

            System.out.println("ERROR DE TIPO: " + ex);

        }
    }

    //Método encargado de ingresar nuevos Administradores
    //Este método no es utilizado, dado que solo un administrador
    //estará registrado en la base de datos, es decir, nuestro cliente
    public void InsertarAdministrador(String nombre, String apellidos, String usuario, String contrasena) {

        String sql = "INSERT INTO Administradores VALUES (?,?,?,?,?)";

        try {

            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(2, nombre);
            pst.setString(3, apellidos);
            pst.setString(4, usuario);
            pst.setString(5, contrasena);
            pst.executeUpdate();
            pst.close();

        } catch (SQLException ex) {

            System.out.println("ERROR DE TIPO: " + ex);

        }
    }

    //Método encargado de insertar una nueva unidad operativa
    //Estas unidades corresponden a los diferentes centros de salud
    public void InsertarUOperativa(String nombre, String dir) {

        String sql = "INSERT INTO UOperativas VALUES (?,?,?,?)";

        try {

            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(2, nombre);
            pst.setString(3, dir);
            pst.setString(4, "ACTIVO");
            pst.executeUpdate();
            pst.close();

        } catch (SQLException ex) {

            System.out.println("ERROR DE TIPO: " + ex);

        }
    }

    //Método encargado de la insertar nuevos cargos/especialidades
    //Estos cargos serán utilizados en el registro de nuevos empleados
    public void InsertarCargo(String nombre_cargo) {

        String sql = "INSERT INTO Cargos VALUES (?,?,?)";

        try {

            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(2, nombre_cargo);
            pst.setString(3, "ACTIVO");
            pst.executeUpdate();
            pst.close();

        } catch (SQLException ex) {

            System.out.println("ERROR DE TIPO: " + ex);

        }

    }

    //Método encargado de ingresar nuevos registros de marcacíon de entrada
    //o salida
    //La información que ingresen estos registros dependerán de la
    //información contenida en los mensajes mqtt recibidos
    public void InsertarRegistro(int id_empleado, int id_unidad, String fecha,
            String hora_entrada, String hora_salida, String hora_comida_s,
            String hora_comida_e) {

        String sql = "INSERT INTO Registros VALUES (?,?,?,?,?,?,?,?)";

        try {

            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setInt(2, id_empleado);
            pst.setInt(3, id_unidad);
            pst.setString(4, fecha);
            pst.setString(5, hora_entrada);
            pst.setString(6, hora_salida);
            pst.setString(7, hora_comida_e);
            pst.setString(8, hora_comida_s);
            pst.executeUpdate();
            pst.close();

        } catch (SQLException ex) {

            System.out.println("ERROR DE TIPO: " + ex);

        }
    }

    //Método que inserta nuevas jorndas a la base de datos
    //Estás jorndas están ligadas al empleado desde el cual se registra
    public void InsertarJornada(String hora_entrada, String hora_salida, String cedula) {

        String sql = "INSERT INTO Jornadas VALUES (?,?,?,?)";

        try {

            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(2, hora_entrada);
            pst.setString(3, hora_salida);
            pst.setString(4, cedula);
            pst.executeUpdate();
            pst.close();

        } catch (SQLException e) {

            System.out.println("ERROR DE TIPO: " + e);

        }
    }
}
