import java.util.ArrayList;
import java.util.List;

public class MensajeController {
    private List<Mensaje> mensajes;

    public MensajeController() {
        mensajes = new ArrayList<>();
    }

    // Enviar mensaje
    public void enviarMensaje(Mensaje mensaje) {
        mensajes.add(mensaje);
        mensaje.enviar();
    }

    // Ver mensajes entre dos usuarios
    public List<Mensaje> verMensajes(Usuario usuario1, Usuario usuario2) {
        List<Mensaje> mensajesFiltrados = new ArrayList<>();
        for (Mensaje m : mensajes) {
            if ((m.getEmisor().equals(usuario1) && m.getReceptor().equals(usuario2)) ||
                (m.getEmisor().equals(usuario2) && m.getReceptor().equals(usuario1))) {
                mensajesFiltrados.add(m);
            }
        }
        return mensajesFiltrados;
    }
}
