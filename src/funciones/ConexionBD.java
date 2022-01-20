package funciones;

import java.sql.*;

/**
 *
 * @author Johan Tuarez
 */

//Clase encargada de ejecutar la conexión hacia la base de datos SQLite
//Se utiliza la libreria JDBC para realizar todos las funciones
public class ConexionBD {

    //Se define el directorio donde se encuentra la base de datos
    private String driver = "jdbc:sqlite:C:/Users/" + System.getProperty("user.name") + "/Desktop/BD.db";
    private Connection cn;

    public ConexionBD() {
    }

    //Método encargado de ejecutar la conexión con la base de datos
    public void Conectar() {

        try {

            Class.forName("org.sqlite.JDBC");
            this.cn = DriverManager.getConnection(driver);
            System.out.println("Conexión Establecida");

        } catch (ClassNotFoundException | SQLException e) {

            System.out.println("Conexión No Establecida");

        }
    }

    //Método que devuelve un objeto de la clase Connection
    //con la conexión que se estableció con la BD.
    public Connection getConexion() {

        return this.cn;
    }

}
