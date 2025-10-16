//clase revisada - validaciones completas

import java.util.ArrayList;

public class MensajeController {
    private ArrayList<Mensaje> mensajes;

    public MensajeController() {
        mensajes = new ArrayList<>();
    }

    
    public void enviarMensaje(Mensaje mensaje) {
        //validacion para que el mensjae no este vacío
        if (mensaje == null) {
            throw new IllegalArgumentException("Mensaje inválido, ya que no existe");
        }
        //validacion de que si exista el emisor y receptor
        if (mensaje.getEmisor() == null || mensaje.getReceptor() == null) {
            throw new IllegalArgumentException("Inválido: emisor y receptor deben existir");
        }
        //validacion para que si exista el contenido del mensaje
        if (mensaje.getContenido() == null || mensaje.getContenido().isEmpty()) {
            throw new IllegalArgumentException("Mensaje inválido, por no tener contenido ");
        }
        mensajes.add(mensaje);
        mensaje.enviar();
    }

    // Ver mensajes entre dos usuarios
    public ArrayList<Mensaje> verMensajes(Usuario usuario1, Usuario usuario2) {
        if (usuario1 == null || usuario2 == null) {
            throw new IllegalArgumentException("Usuarios inválidos");
        }
        ArrayList<Mensaje> mensajesFiltrados = new ArrayList<>();
        for (Mensaje m : mensajes) {
            if ((m.getEmisor().equals(usuario1) && m.getReceptor().equals(usuario2)) ||
                (m.getEmisor().equals(usuario2) && m.getReceptor().equals(usuario1))) {
                mensajesFiltrados.add(m);
            }
        }
        return mensajesFiltrados;
    }
    public ArrayList<Mensaje> getTodosMensajes() {
        return new ArrayList<>(mensajes);
    }
}

