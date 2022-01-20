package consultasBD;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import objetos.Administrador;
import objetos.Cargo;
import objetos.Empleado;
import objetos.Jornada;
import objetos.Registro;
import objetos.UOperativa;

/**
 *
 * @author Johan Tuarez
 */
//Clase encargada de realizar las consultas sobre los datos
//en la base de datos
//Requiere un objeto de la clase Connection en el construtor
//para ejecutar todas las peticiones
public class BuscarBD {

    private Connection cn; //Objeto para la conexión con la BD

    public BuscarBD(Connection cn) {

        this.cn = cn;
    }

    //Consulta a la tabla Administradores
    //Devuelve un objeto de la clase Administrador con la información
    //de la consulta
    public Administrador ConsultarAdmin(String parametros) {

        parametros = parametros.trim();
        String sql = "SELECT * FROM Administradores WHERE " + parametros;

        try {

            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                Administrador admin = new Administrador(
                        rs.getInt("ID_Admin"),
                        rs.getString("Apellidos"),
                        rs.getString("Usuario"),
                        rs.getString("Contrasena"),
                        rs.getString("Nombre"));

                rs.close();
                pst.close();

                return admin;

            } else {

                rs.close();
                pst.close();

                return null;
            }

        } catch (SQLException ex) {

            System.out.println("ERROR DE TIPO: " + ex);
            JOptionPane.showMessageDialog(null, "NO SE ENCUENTRA LA BASE DE DATOS");

            return null;
        }
    }

    //Consulta a la tabla Empleados
    //Devuelve un objeto de la clase Empleado con la información
    //de la consulta
    public Empleado ConsultarEmpleado(String parametros) {

        parametros = parametros.trim();
        String sql = "SELECT * FROM Empleados WHERE " + parametros;

        try {

            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                Empleado empleado = new Empleado(
                        rs.getInt("ID_Empleado"),
                        rs.getInt("ID_Unidad"),
                        rs.getInt("ID_Cargo"),
                        rs.getInt("ID_Jornada"),
                        rs.getString("Cedula"),
                        rs.getString("Nombres"),
                        rs.getString("Apellidos"),
                        rs.getString("Telefono"),
                        rs.getString("Correo"),
                        rs.getString("Estatus"));

                rs.close();
                pst.close();

                return empleado;

            } else {

                rs.close();
                pst.close();

                return null;
            }

        } catch (SQLException ex) {

            System.out.println("ERROR DE TIPO: " + ex);
            return null;
        }
    }

    //Consulta a la tabla UOperativa
    //Devuelve un objeto de la clase UOperativa con la información
    //de la consulta
    public UOperativa ConsultarUOperativa(String parametros) {

        parametros = parametros.trim();
        String sql = "SELECT * FROM UOperativas WHERE " + parametros;

        try {

            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                UOperativa uoperativa = new UOperativa(
                        rs.getInt("ID_Unidad"),
                        rs.getString("Nombre_Unidad"),
                        rs.getString("Dir_Unidad"),
                        rs.getString("Estatus"));

                rs.close();
                pst.close();

                return uoperativa;

            } else {

                rs.close();
                pst.close();

                return null;
            }

        } catch (SQLException ex) {

            System.out.println("ERROR DE TIPO: " + ex);
            return null;
        }
    }

    //Consulta a la tabla Registros
    //Devuelve un objeto de la clase Registro con la información
    //de la consulta
    public Registro ConsultarRegistro(String parametros) {

        parametros = parametros.trim();
        String sql = "SELECT * FROM Registros WHERE " + parametros;

        try {

            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                Registro registro = new Registro(
                        rs.getString("Fecha"),
                        rs.getString("Hora_Entrada"),
                        rs.getString("Hora_Salida"),
                        rs.getString("Almuerzo_Salida"),
                        rs.getString("Almuerzo_Entrada"),
                        rs.getInt("ID_Registro"),
                        rs.getInt("ID_Empleado"),
                        rs.getInt("ID_Unidad"));

                rs.close();
                pst.close();

                return registro;

            } else {

                rs.close();
                pst.close();

                return null;
            }

        } catch (SQLException ex) {

            System.out.println("ERROR DE TIPO: " + ex);
            return null;
        }
    }

    //Consulta a la tabla Cargos
    //Devuelve un objeto de la clase Cargo con la información
    //de la consulta
    public Cargo ConsultarCargo(String parametros) {

        parametros = parametros.trim();
        String sql = "SELECT * FROM Cargos WHERE " + parametros;

        try {

            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                Cargo cargo = new Cargo(
                        rs.getInt("ID_Cargo"),
                        rs.getString("Cargo"),
                        rs.getString("Estatus"));

                rs.close();
                pst.close();

                return cargo;

            } else {

                rs.close();
                pst.close();

                return null;
            }

        } catch (SQLException ex) {

            System.out.println("ERROR DE TIPO: " + ex);
            return null;
        }

    }

    //Consulta a la tabla Jornadas
    //Devuelve un objeto de la clase Jornada con la información
    //de la consulta
    public Jornada ConsultarJornada(String parametros) {

        parametros = parametros.trim();
        String sql = "SELECT * FROM Jornadas WHERE " + parametros;

        try {

            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                Jornada jornada = new Jornada(
                        rs.getInt("ID_Jornada"),
                        rs.getString("Hora_Entrada"),
                        rs.getString("Hora_Salida"),
                        rs.getString("Cedula"));

                rs.close();
                pst.close();

                return jornada;

            } else {

                rs.close();
                pst.close();

                return null;
            }

        } catch (SQLException ex) {

            System.out.println("ERROR DE TIPO: " + ex);
            return null;
        }

    }

    //Consulta a la tabla de Empleados
    //Devuelve un ArrayList con objetos de la clase Empleado
    //Este ArrayList tendrá todos los empleados que se encuentran
    //registrados en la base de datos
    public ArrayList<Empleado> getEmpleados() {

        ArrayList<Empleado> list_empleados = new ArrayList<>();

        String sql = "SELECT * FROM Empleados";

        try {

            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Empleado empleado = new Empleado(
                        rs.getInt("ID_Empleado"),
                        rs.getInt("ID_Unidad"),
                        rs.getInt("ID_Cargo"),
                        rs.getInt("ID_Jornada"),
                        rs.getString("Cedula"),
                        rs.getString("Nombres"),
                        rs.getString("Apellidos"),
                        rs.getString("Telefono"),
                        rs.getString("Correo"),
                        rs.getString("Estatus"));

                list_empleados.add(empleado);
            }

            rs.close();
            pst.close();

            return list_empleados;

        } catch (SQLException ex) {

            System.out.println("ERROR DE TIPO: " + ex);
            return null;
        }
    }

    //Consulta a la tabla de Registros
    //Devuelve un ArrayList con objetos de la clase Registro
    //Este ArrayList tendrá todos los registros que se encuentran
    //en la base de datos
    public ArrayList<Registro> getRegistros() {

        ArrayList<Registro> list_registros = new ArrayList<>();

        String sql = "SELECT * FROM Registros";

        try {

            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Registro registro = new Registro(
                        rs.getString("Fecha"),
                        rs.getString("Hora_Entrada"),
                        rs.getString("Hora_Salida"),
                        rs.getString("Almuerzo_Salida"),
                        rs.getString("Almuerzo_Entrada"),
                        rs.getInt("ID_Registro"),
                        rs.getInt("ID_Empleado"),
                        rs.getInt("ID_Unidad"));

                list_registros.add(registro);
            }

            rs.close();
            pst.close();

            return list_registros;

        } catch (SQLException ex) {

            System.out.println("ERROR DE TIPO: " + ex);
            return null;
        }
    }

    //Consulta a la tabla de Cargos
    //Devuelve un ArrayList con objetos de la clase Cargo
    //Este ArrayList tendrá todos los cargos que se encuentran
    //registrados en la base de datos
    public ArrayList<Cargo> getCargos() {

        ArrayList<Cargo> list_cargos = new ArrayList<>();

        String sql = "SELECT * FROM Cargos";

        try {

            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Cargo cargo = new Cargo(
                        rs.getInt("ID_Cargo"),
                        rs.getString("Cargo"),
                        rs.getString("Estatus"));

                list_cargos.add(cargo);
            }

            rs.close();
            pst.close();

            return list_cargos;

        } catch (SQLException ex) {

            System.out.println("ERROR DE TIPO: " + ex);
            return null;
        }
    }

    //Consulta a la tabla de UOperativas
    //Devuelve un ArrayList con objetos de la clase UOperativa
    //Este ArrayList tendrá todos las unidades operativas que se encuentran
    //registradas en la base de datos
    public ArrayList<UOperativa> getUnidades() {

        ArrayList<UOperativa> list_unidades = new ArrayList<>();

        String sql = "SELECT * FROM UOperativas";

        try {

            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                UOperativa unidad = new UOperativa(
                        rs.getInt("ID_Unidad"),
                        rs.getString("Nombre_Unidad"),
                        rs.getString("Dir_Unidad"),
                        rs.getString("Estatus"));

                list_unidades.add(unidad);
            }

            rs.close();
            pst.close();

            return list_unidades;

        } catch (SQLException ex) {

            System.out.println("ERROR DE TIPO: " + ex);
            return null;
        }
    }
}
