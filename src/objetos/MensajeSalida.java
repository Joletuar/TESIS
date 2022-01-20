package objetos;

/**
 *
 * @author Johan Tuarez
 */

//Clase encargada de almacenar los mensajes del tópico "SALIDA" recibidos 
//a traves del bróker MQTT
public class MensajeSalida {
    
    //Parámetros del mensaje
    public int id;
    public String fecha, hora_salida;

    public MensajeSalida(String mensaje) {
        
        //Se desempaqueta el mensaje a partir del siguiente formato:
        //"ID-Fecha-Hora"
        String[] valores = mensaje.trim().split("-");

        id = Integer.parseInt(valores[0]);
        fecha = valores[1];
        hora_salida = valores[2];

    }

}
