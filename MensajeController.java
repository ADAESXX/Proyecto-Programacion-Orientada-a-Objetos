//clase revisada
//falta ver funcionalidades extras, como validaciones entre otras cosas
//falta comunicacion entre clases
import java.util.ArrayList;

public class MensajeController {
    private ArrayList<Mensaje> mensajes;

    public MensajeController() {
        mensajes = new ArrayList<>();
    }

    // Enviar mensaje
    public void enviarMensaje(Mensaje mensaje) {
        mensajes.add(mensaje);
        mensaje.enviar();
    }

    // Ver mensajes entre dos usuarios
    public ArrayList<Mensaje> verMensajes(Usuario usuario1, Usuario usuario2) {
        ArrayList<Mensaje> mensajesFiltrados = new ArrayList<>();
        for (Mensaje m : mensajes) {
            if ((m.getEmisor().equals(usuario1) && m.getReceptor().equals(usuario2)) ||
                (m.getEmisor().equals(usuario2) && m.getReceptor().equals(usuario1))) {
                mensajesFiltrados.add(m);
            }
        }
        return mensajesFiltrados;
    }
}
