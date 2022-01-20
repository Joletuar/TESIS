package consultasBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Johan Tuarez
 */
//Clase con los métodos necesarios para ejecutar actualizaciones
//a todas las tablas de la BD
//Requiere un objeto de la clase Connection en el construtor
//para ejecutar todas las peticiones
public class ActualizarBD {

    private Connection cn; //Objeto para la conexión con la BD

    public ActualizarBD(Connection cn) {

        this.cn = cn;
    }

    public void ActualizarRegistro(String hora_salida, int id_registro) {

        //Metodo que permite actualizar la "Hora_Salida" de la tabla Registros
        //Requiere como parametro el ID del registro a actualizar
        String sql = "UPDATE Registros SET Hora_Salida = ? WHERE ID_Registro = " + id_registro;

        try {

            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(1, hora_salida);
            pst.executeUpdate();
            pst.close();

        } catch (SQLException e) {

            System.out.println("ERROR DE TIPO: " + e);
        }

    }

    public void ActualizarRegistroAlmuerzo(String hora_comida, int id_registro, int tipo) {
        
        String columna = "";
        
        if (tipo == 1){
            
            columna = "Almuerzo_Entrada";
            
        }else if (tipo == 2){
            
            columna = "Almuerzo_Salida";
            
        }
        
        String sql = "UPDATE Registros SET "+columna+" = ? WHERE ID_Registro = " + id_registro;

        try {

            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(1, hora_comida);
            pst.executeUpdate();
            pst.close();

        } catch (SQLException e) {

            System.out.println("ERROR DE TIPO: " + e);
        }
    }

    public void ActualizarCargo(int id_cargo, String cargo, String estatus) {

        //Metodo que permite actualizar el "Cargo y su Estatus" de la tabla Cargos
        //Requiere como parametro el ID del cargo a actualizar
        String sql = "UPDATE Cargos SET Cargo = ? , Estatus = ? WHERE ID_Cargo = " + id_cargo;

        try {

            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(1, cargo);
            pst.setString(2, estatus);
            pst.executeUpdate();
            pst.close();

        } catch (SQLException e) {

            System.out.println("ERROR DE TIPO: " + e);
        }

    }

    public void ActualizarUnidad(int id_unidad, String unidad, String dir, String estatus) {

        //Metodo que permite actualizar los datos de la tabla Unidad
        //Requiere como parametro el ID de la unidad a actualizar, el nombre de la unidad
        //direccion y su estatus
        String sql = "UPDATE UOperativas SET Nombre_Unidad = ? , Dir_Unidad = ?, Estatus = ? WHERE ID_Unidad = " + id_unidad;

        try {

            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(1, unidad);
            pst.setString(2, dir);
            pst.setString(3, estatus);
            pst.executeUpdate();
            pst.close();

        } catch (SQLException e) {

            System.out.println("ERROR DE TIPO: " + e);
        }

    }

    public void ActualizarEmpleado(int id_empleado, int id_unidad, int id_cargo, int id_jornada, String cedula, String nombres,
            String apellidos, String telefono, String correo, String estatus) {

        //Metodo que permite actualizar los datos de la tabla Empleados
        //Requiere como parametro el ID del empleado a actualizar y todos los datos de la tabla
        String sql = "UPDATE Empleados SET ID_Unidad = ?, ID_Cargo = ?, ID_Jornada = ?, "
                + "Cedula = ?, Nombres= ?, Apellidos = ?, Telefono = ?, Correo = ?, Estatus = ? WHERE ID_Empleado = " + id_empleado;

        try {

            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setInt(1, id_unidad);
            pst.setInt(2, id_cargo);
            pst.setInt(3, id_jornada);
            pst.setString(4, cedula);
            pst.setString(5, nombres);
            pst.setString(6, apellidos);
            pst.setString(7, telefono);
            pst.setString(8, correo);
            pst.setString(9, estatus);
            pst.executeUpdate();
            pst.close();

        } catch (SQLException e) {

            System.out.println("ERROR DE TIPO: " + e);
        }

    }

    public void ActualizarJornada(int id_jornada, String hora_entrada, String hora_salida, String cedula) {

        //Metodo que permite actualizar los datos de la tabla Jornadas
        //Requiere como parametro el ID de la jornada a actualizar y todos los datos de la tabla
        String sql = "UPDATE Jornadas SET Hora_Entrada = ? , Hora_Salida = ?, Cedula = ? WHERE ID_Jornada = " + id_jornada;

        try {

            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(1, hora_entrada);
            pst.setString(2, hora_salida);
            pst.setString(3, cedula);
            pst.executeUpdate();
            pst.close();

        } catch (SQLException e) {

            System.out.println("ERROR DE TIPO: " + e);
        }

    }

}
