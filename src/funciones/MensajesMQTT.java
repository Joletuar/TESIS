package funciones;

import java.util.ArrayList;

/**
 * 
 * @author Johan Tuarez
 */

//Clase encargada de almacenar los mensajes recibidos a traves del Bróker MQTT
//Contiene la lista de mensajes recibidos, y la cual será iterada para leer la
//información de que cada mensaje, y luego será borrada al final.
public class MensajesMQTT {
    
    private ArrayList<Object> mensajesmqtt = new ArrayList<>();
    
    public MensajesMQTT(){}

    public ArrayList<Object> getMensajesmqtt() {
        
        return mensajesmqtt;
    }

    public void setMensajesmqtt(Object mensaje) {
        
        this.mensajesmqtt.add(mensaje);
    }
}
