package objetos;

/**
 * 
 * @author Johan Tuarez
 */

//Clase encargada de almacenar los mensajes del tópico "ENTRADA" recibidos 
//a traves del bróker MQTT
public class MensajeEntrada {
    
    //Parámetros del mensaje
    public int id;
    public String fecha ,hora_entrada;
    
    public MensajeEntrada(String mensaje){
        
        //Se desempaqueta el mensaje a partir del siguiente formato:
        //"ID-Fecha-Hora"
        String [] valores = mensaje.trim().split("-");
        
        id = Integer.parseInt(valores[0]);
        fecha = valores[1];
        hora_entrada = valores[2];
        
    }

}
