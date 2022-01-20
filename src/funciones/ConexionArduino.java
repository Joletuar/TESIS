package funciones;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.util.List;
import javax.swing.JOptionPane;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import ventanas.Empleados;
import ventanas.RegistroHuellas;

/**
 *
 * @author Johan Tuarez
 */

//Clase encargada de la conexión serial con el dispositivo Arduino
//Esta clase solo será utilizada cuando se lleve a cabo el registro de una
//huella dactitar.
//Se hace uso de la libreria Panamahitek_Arduino para llevar a cabo
//todas funciones necesarias para la conexión.

public class ConexionArduino {

    private PanamaHitek_Arduino ino; //Objeto que permite realizar la conexión serial
    public boolean bandera = false; //Bandera que indica si la conexió se estableció con el Arduino
    private RegistroHuellas ventana; //Objeto que contiene la interfaz donde se registran las huellas
    private Empleados ventana_2; //Objeto que contiene la interfaz de Empleados

    //Se define un Listening que estará a la espera de recibir algún
    //dato enviado a traves del puerto serial al que se conecta.
    //Aqui se cambia el texto que muestra los mensajes en la interfaz de
    //registro de huellas.
    
    private final SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {

            try {
                //Compruaba que existe un mensaje por leer
                if (ino.isMessageAvailable()) {
                    
                    //Se lee el mensaje
                    String mensaje_recibido = ino.printMessage();
                    MensajesArduino.mensajesArduino.add(mensaje_recibido);
                    
                    //Comprueba que la ventana de Registro de Huellas esté creada o abierta
                    if (ventana != null) {
                        
                        //Verifica que el mensaje recibido no sea un texto largo
                        if (MensajesArduino.mensajesArduino.get(0).length() < 5) {
                            
                            int valor = Integer.parseInt(MensajesArduino.mensajesArduino.get(0));
                            
                            //Switch con los diferentes casos en función al mensaje recibido
                            switch (valor) {

                                //Caso para el código #1
                                case 1:
                                    ventana.txt_animacion.setVisible(true);
                                    ventana.boton_registrar.setEnabled(false);
                                    ventana.cmb_id.setEnabled(false);
                                    ventana.txt_procesando.setEnabled(true);
                                    ventana.txt_estado.setText("PONGA SU DEDO");
                                    MensajesArduino.mensajesArduino.clear();
                                    break;
                                //Caso para el código #2
                                case 2:
                                    ventana.txt_estado.setText("QUITE SU DEDO");
                                    MensajesArduino.mensajesArduino.clear();
                                    break;
                                //Caso para el código #3
                                case 3:
                                    ventana.txt_estado.setText("VUELVA PONER SU DEDO");
                                    MensajesArduino.mensajesArduino.clear();
                                    break;
                                //Caso para el código #4
                                case 4:
                                    ventana.txt_estado.setText("QUITE SU DEDO");
                                    MensajesArduino.mensajesArduino.clear();
                                    break;
                                //Caso para el código #5
                                case 5:
                                    ventana.bandera = true;
                                    ventana.txt_procesando.setEnabled(false);
                                    ventana.Ejecutar();
                                    ventana.txt_estado.setText("REGISTRO COMPLETADO");
                                    ventana_2.MostrarTabla();
                                    ventana_2.limpiarTxT();
                                    MensajesArduino.mensajesArduino.clear();
                                    break;
                                //Caso para el código #6
                                case 6:
                                    ventana.txt_animacion.setVisible(false);
                                    ventana.boton_registrar.setEnabled(true);
                                    ventana.cmb_id.setEnabled(true);
                                    ventana.bandera = false;
                                    ventana.txt_procesando.setEnabled(false);
                                    ventana.txt_estado.setText("¡¡ERROR!!");
                                    JOptionPane.showMessageDialog(null, "HUELLAS NO COINCIDENTES");
                                    ventana.dispose();
                                    MensajesArduino.mensajesArduino.clear();
                                    break;
                            }

                        } else {

                            JOptionPane.showMessageDialog(null, "SELECCIONE EN SU DISPOSITIVO EL MODO REGISTRAR");
                            MensajesArduino.mensajesArduino.clear();

                        }

                    }

                }

            } catch (ArduinoException | SerialPortException e) {

                MensajesArduino.mensajesArduino.clear();

            }

        }

    };
    
    //Constructor de la clase que recibe los parámetros definos anteriormente
    public ConexionArduino(RegistroHuellas ventana, Empleados ventana_2) {

        this.ino = new PanamaHitek_Arduino();
        this.ventana_2 = ventana_2;
        this.ventana = ventana;

    }

    public ConexionArduino(RegistroHuellas aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //Método que permite realizar la conexión mediante un puerto serial
    public void Conectar(String puerto, int velocidadBaudios) {

        try {

            this.ino.arduinoRXTX(puerto, velocidadBaudios, this.listener);
            this.bandera = true;
            System.out.println("CONEXIÓN EXITÓSA");

        } catch (ArduinoException ex) {

            System.out.println("ERROR AL CONECTAR");

        }

    }
    
    //Método que realiza la desconexión del puerto serial
    public void Desconectar() {

        try {

            this.ino.killArduinoConnection();
            this.bandera = false;

        } catch (ArduinoException e) {

            System.out.println("ERROR AL DESCONECTAR");

        }

    }
    
    //Método que envía los datos en formato string
    //Se utiliza este para enviar los ids de los empleados al dispositivo
    public void enviarDatos(String valor) {

        try {

            this.ino.sendData(valor);

            System.out.println("ENVÍO CORRECTO");

        } catch (ArduinoException | SerialPortException e) {

            System.out.println("ERROR DE TIPO: " + e);

        }

    }
    
    //Método que realiza un escaneo de los puertos seriales 
    //disponibles en la pc.
    public List<String> getPuertos() {

        try {

            List<String> puertos = this.ino.getSerialPorts();

            return puertos;

        } catch (Exception e) {

            System.out.println("ERROR DE TIPO: " + e);

            return null;
        }

    }

}
