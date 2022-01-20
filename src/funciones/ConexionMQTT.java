package funciones;

import java.awt.Color;
import java.awt.HeadlessException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
//import javax.swing.JOptionPane;
import objetos.Empleado;
import objetos.MensajeEntrada;
import objetos.MensajeSalida;
import objetos.Registro;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import ventanas.Login;
import ventanas.PanelControl;

/**
 *
 * @author Johan Tuarez
 */
//Clase encargada de ejecutar todas las operaciones de conexión y recepción
//de datos con el Bróker MQTT.
//Se hace uso de la librera Eclipse Paho MQTT para implementar todas las
//funciones necesarias.
public class ConexionMQTT {

    //Este objeto almacena en un ArrayList los mensajes recibidos
    public MensajesMQTT mensajes = new MensajesMQTT();
    //Interfaz Panel Control
    public PanelControl ventana;

    //URL/IP del Bróker al que se conectará
    private String url_host = "tcp://localhost:1883"; //tcp://localhost:1883 // 192.168.1.62
    private final String clienteId = "RECURSOS HUMANDOS"; //ID
    private final String topico_1 = "ENTRADA"; //Tópico 1
    private final String topico_2 = "SALIDA"; //Tópico 2

    private MqttClient cliente; //Objeto que proporciona los métodos y funciones para la conexión
    private MemoryPersistence persistencia; //Objeto para que el usuario sea de tipo persistente
    private MqttConnectOptions opciones; //Objeto con los parámetros para la conexión
    private MensajesRecibidos mensajes_recibidos; //Objeto de una sub-clase que se encarga de la
    //recepción de los mensajes 

    //Constructor de la clase principal
    public ConexionMQTT(PanelControl ventana, String host) {

        this.ventana = ventana;

        if (host != null) {

            this.url_host = host;

        }

        ConfiguracionConexion();
        ConectarServidor();

    }

    //Método encargado de configurar los parámetros que tendrá la sesión
    //con el Bróker MQTT.
    public final void ConfiguracionConexion() {

        try {

            mensajes_recibidos = new MensajesRecibidos();
            persistencia = new MemoryPersistence();
            opciones = new MqttConnectOptions();
            //Creación del cliente con los parámetros de la conexión
            cliente = new MqttClient(url_host, clienteId, persistencia);

            //Parámetro que indica que la sesión de la aplicación
            //no será borrada cuando se desconecte del Bróker.
            opciones.setCleanSession(false);
            //ID que tendrá la aplicación al conectarse al Bróker
            opciones.setUserName("RR.HH.");
            //opciones.setPassword();
            opciones.setConnectionTimeout(10);
            opciones.setKeepAliveInterval(90);
            cliente.setCallback(mensajes_recibidos);

        } catch (IllegalArgumentException | MqttException e) {

            //JOptionPane.showMessageDialog(null, "NO SE PUDO REALIZAR LAS CONFIGURACIONES DEL CLIENTE MQTT");
            System.out.println("ERROR DE TIPO: " + e);

        }
    }

    //Método encargado de ejecutar la conexión con el servidor luego
    //de que la sesión con el Bróker haya sido configurada
    //con los parámetros correspondientes.
    public final void ConectarServidor() {

        try {

            try {
                //Verifica si el cliente se encuentra o no conectado
                if (!cliente.isConnected()) {

                    System.out.println("############ INTENTANDO CONECTARSE AL SERVIDOR MQTT #############");
                    //En caso de no estar conectado, realiza las configuraciones
                    //para ejecutar la conexión.
                    cliente.connect(opciones);

                    System.out.println("############ CONEXIÓN CON EL SERVIDOR MQTT ESTABLECIDA #############");

                    //Cambio los textos y estados de la interfaz Panel de Control
                    //en función al estado de la conexión.
                    this.ventana.txt_estado_conexion.setText("BRÓKER: " + url_host);

                    this.ventana.indicador.setText("ONLINE");
                    this.ventana.indicador.setForeground(new Color(46, 203, 36));

                } else if (cliente.isConnected()) {

                    System.out.println("############ CONEXIÓN CON EL SERVIDOR MQTT ESTABLECIDA #############");

                    this.ventana.txt_estado_conexion.setText("BRÓKER: " + url_host);
                }

            } catch (Exception e) {

                //JOptionPane.showMessageDialog(null, "HUBO UN ERROR Y NO SE PUDO CONECTAR CON EL SERVIDOR");
                System.out.println("ERROR DE TIPO: " + e);

                this.ventana.indicador.setText("OFFLINE");
                this.ventana.indicador.setForeground(Color.RED);

            }

            //Ejecuta el método de suscripción a los tópicos
            SuscripcionTopicos();

        } catch (HeadlessException e) {

            System.out.println("ERROR DE TIPO: " + e);

        }

    }

    public void DesconectarServidor() {

        try {

            if (cliente != null) {

                cliente.disconnect();

                cliente.close();
            }

        } catch (MqttException e) {

            //JOptionPane.showMessageDialog(null, "HUBO UN ERROR AL DESCONECTARSE CON EL SERVIDOR");
            System.out.println("ERROR DE TIPO: " + e);

        }

    }

    //Método encargado de realizar la suscripción a los tópicos
    //definidos al inicio.
    public void SuscripcionTopicos() {

        try {

            cliente.subscribe(topico_1);
            cliente.subscribe(topico_2);

            System.out.println("############ SUSCRIPCIÓN EXITÓSA A LOS TÓPICOS #############");
            System.out.println("############ ESPERANDO MENSAJES... #############");

        } catch (Exception e) {

            //JOptionPane.showMessageDialog(null, "HUBO UN ERROR AL SUSCRIBIRSE A LOS TÓPICOS");
            System.out.println("ERROR DE TIPO: " + e);

        }

    }

    //Método encargado de insertar los mensajes recibidos de los diferentes
    //tópicos.
    private void insertarMensajesMqtt(ArrayList<Registro> registros) {

        //Verifica que la cola de mensajes esté no vacía para ejecutar
        //el proceso de insercción de la información contenida
        //en los mensajes de cada tópico.
        if (!mensajes.getMensajesmqtt().isEmpty()) {

            //Se itera en la cola de menesajes disponibles
            for (Object mensaje : mensajes.getMensajesmqtt()) {

                //Caso para el caso de que el mensaje pertenezca
                //al tópico de entrada.
                if (mensaje instanceof MensajeEntrada) {

                    MensajeEntrada mensajeEntrada = (MensajeEntrada) mensaje;

                    String parametro_1 = "ID_Empleado = " + mensajeEntrada.id;
                    Empleado empleado = Login.bs.ConsultarEmpleado(parametro_1);

                    if (empleado != null) {

                        if (empleado.estatus.equalsIgnoreCase("ACTIVO")) {

                            //Bandera que indica si existe o no un registro ya
                            //activo dentro de la base de datos en la fecha
                            //recibida.
                            boolean bandera_1 = true;
                            boolean bandera_2 = false;
                            Registro registro_temp = null;

                            //Condición que verifica que el registro de entrada
                            //en una fecha determinada no se encuentre ya dentro
                            //de la base de datos.
                            for (Registro registro_1 : registros) {

                                try {

                                    String fecha_1 = mensajeEntrada.fecha;
                                    SimpleDateFormat sdf_1 = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date_1 = sdf_1.parse(fecha_1);
                                    LocalDate localDate_1 = date_1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                                    String fecha_2 = registro_1.fecha;
                                    SimpleDateFormat sdf_2 = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date_2 = sdf_2.parse(fecha_2);
                                    LocalDate localDate_2 = date_2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                                    if (registro_1.id_empleado == empleado.id_empleado
                                            && localDate_1.getDayOfMonth() == localDate_2.getDayOfMonth()
                                            && localDate_1.getMonthValue() == localDate_2.getMonthValue()
                                            && localDate_1.getYear() == localDate_2.getYear()) {

                                        bandera_1 = false;

                                    }

                                    if (registro_1.id_empleado == empleado.id_empleado
                                            && localDate_1.getDayOfMonth() == localDate_2.getDayOfMonth()
                                            && localDate_1.getMonthValue() == localDate_2.getMonthValue()
                                            && localDate_1.getYear() == localDate_2.getYear()
                                            && registro_1.hora_comida_e.equalsIgnoreCase("X")
                                            && !registro_1.hora_comida_s.equalsIgnoreCase("X")) {

                                        bandera_2 = true;
                                        registro_temp = registro_1;

                                    }

                                } catch (ParseException ex) {

                                    System.out.println("ERROR DE TIPO :" + ex);
                                }

                            }

                            if (bandera_1) {

                                Login.in.InsertarRegistro(empleado.id_empleado, empleado.id_unidad,
                                        mensajeEntrada.fecha, mensajeEntrada.hora_entrada,
                                        "X", "X", "X");

                            } else if (bandera_2) {

                                Login.ac.ActualizarRegistroAlmuerzo(mensajeEntrada.hora_entrada,
                                        registro_temp.id_registro, 1);

                            } else {

                                System.out.println("EL USUARIO YA MARCÓ ENTRADA Y HORA DE ALMUERZO (ENTRADA)");

                            }

                        } else {

                            System.out.println("UN EMPLEADO INACTIVO INTENTÓ MARCAR");

                        }

                    } else {

                        System.out.println("EL EMPLEADO NO SE ECUENTRA REGISTRADO");

                    }

                    //Caso para el caso de que el mensaje pertenezca
                    //al tópico de salida.
                } else if (mensaje instanceof MensajeSalida) {

                    MensajeSalida mensajeSalida = (MensajeSalida) mensaje;

                    String parametro_1 = "ID_Empleado = " + mensajeSalida.id;
                    Empleado empleado = Login.bs.ConsultarEmpleado(parametro_1);

                    if (empleado != null) {

                        if (empleado.estatus.equalsIgnoreCase("ACTIVO")) {

                            try {

                                String parametro_2 = "Fecha = '" + mensajeSalida.fecha
                                        + "' AND ID_Empleado = " + empleado.id_empleado;
                                Registro registro = Login.bs.ConsultarRegistro(parametro_2);

                                //Condición que verifica que el registro de entrada
                                //en una fecha determinada no se encuentre ya dentro
                                //de la base de datos.
                                if (registro != null) {

                                    String fecha_1 = mensajeSalida.fecha;
                                    SimpleDateFormat sdf_1 = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date_1 = sdf_1.parse(fecha_1);
                                    LocalDate localDate_1 = date_1.toInstant().
                                            atZone(ZoneId.systemDefault()).toLocalDate();

                                    String fecha_2 = registro.fecha;
                                    SimpleDateFormat sdf_2 = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date_2 = sdf_2.parse(fecha_2);
                                    LocalDate localDate_2 = date_2.toInstant().
                                            atZone(ZoneId.systemDefault()).toLocalDate();

                                    //Condición que verifica que el registro de entrada
                                    //en una fecha determinada no se encuentre ya dentro
                                    //de la base de datos.
                                    if (registro.id_empleado == empleado.id_empleado
                                            && registro.hora_comida_s.equalsIgnoreCase("X")
                                            && localDate_1.getDayOfMonth() == localDate_2.getDayOfMonth()
                                            && localDate_1.getMonthValue() == localDate_2.getMonthValue()
                                            && localDate_1.getYear() == localDate_2.getYear()) {

                                        Login.ac.ActualizarRegistroAlmuerzo(mensajeSalida.hora_salida,
                                                registro.id_registro, 2);

                                    } else if (registro.id_empleado == empleado.id_empleado
                                            && registro.hora_salida.equalsIgnoreCase("X")
                                            && localDate_1.getDayOfMonth() == localDate_2.getDayOfMonth()
                                            && localDate_1.getMonthValue() == localDate_2.getMonthValue()
                                            && localDate_1.getYear() == localDate_2.getYear()
                                            && !registro.hora_comida_e.equalsIgnoreCase("X")) {

                                        Login.ac.ActualizarRegistro(mensajeSalida.hora_salida,
                                                registro.id_registro);

                                    } else {

                                        System.out.println("EL USUARIO YA MARCÓ SALIDA Y HORA DE ALMUERZO");

                                    }
                                } else {

                                    System.out.println("ESTE EMPLEADO NO HA MARCADO ENTRADA: "
                                            + empleado.nombres + " " + empleado.apellidos);

                                }

                            } catch (ParseException ex) {

                                System.out.println("ERROR DE TIPO :" + ex);

                            }

                        } else {

                            System.out.println("UN USUARIO INACTIVO INTENTÓ MARCAR");

                        }

                    } else {

                        System.out.println("EL EMPLEADO NO SE ECUENTRA REGISTRADO");
                    }
                }

            }

        }

        //Se borran los mensajes de la cola luego de ser ingresados a la base
        //de datos.
        mensajes.getMensajesmqtt().clear();

    }

    //Sub-clase necesaria para la implementación de los Listening que 
    //están a la espera de los mensajes MQTT.
    class MensajesRecibidos implements MqttCallback {

        //Método que es llamado en caso de que se pierda la conexión con la BD.
        @Override
        public void connectionLost(Throwable thrwbl) {

            System.out.println("############## LA CONEXÓN SE PERDIÓ, SE INTENTARÁ RECONECTARSE ##############");

            ventana.indicador.setText("OFFLINE");
            ventana.indicador.setForeground(Color.RED);

            while (true) {

                ConectarServidor();

                if (cliente.isConnected()) {

                    ventana.indicador.setText("ONLINE");
                    ventana.indicador.setForeground(new Color(46, 203, 36));
                    break;

                }

                try {

                    Thread.sleep(3000);

                } catch (InterruptedException ex) {

                    System.out.println("ERROR CON EL HILO");

                }

            }

        }

        //Método que gestiona los mensajes recibido durante la ejecución de la
        //aplicación.
        //Ingresa estos mensajes por tópicos a la lista de mensajes ya definida
        //anteriormente.
        @Override
        public void messageArrived(String topico, MqttMessage mm) {

            System.out.println("######################################################");
            System.out.println("TÓPICO: " + topico);
            System.out.println("MENSAJE RECIBIDO: " + mm.toString());

            try {

                if (topico != null && mm != null) {

                    if (topico.equals("ENTRADA")) {

                        mensajes.setMensajesmqtt(new MensajeEntrada(mm.toString()));

                    } else if (topico.equals("SALIDA")) {

                        mensajes.setMensajesmqtt(new MensajeSalida(mm.toString()));
                    }

                    insertarMensajesMqtt(Login.bs.getRegistros());

                }

            } catch (Exception e) {

                System.out.println("######### ERROR AL PROCESAR EL MENSAJE ##############");
                System.out.println("ERROR DE TIPO: " + e);

            }

        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

            System.out.println("ENTREGA COMPLETADA: " + token.isComplete());

        }

    }

}
