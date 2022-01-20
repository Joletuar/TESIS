package funciones;

import java.util.ArrayList;

/**
 *
 * @author Johan Tuarez
 */

//Clase encargada de almacenar los mensajes recibidos por el puerto serial
//Dentro de esta clase se encuentra la lista donde se almacenan asu vez que
//van llegando.
public class MensajesArduino {

    public static ArrayList<String> mensajesArduino = new ArrayList<>();

    public MensajesArduino() {
    }

}
