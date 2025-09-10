//clase completa - validaciones completas

// se importa el java list y array
import java.util.ArrayList;

public class NotificacionController {
    private ArrayList<Notificaciones> notificaciones;
//las notificaciones se agregan a una lista 
    public NotificacionController() {
        this.notificaciones = new ArrayList<>();
    }
    //se agrega la noficiacon del receptor y el mensaje 
    public String generarNotificacion(Usuario receptor, String mensaje) {
        if (receptor == null) {
            throw new IllegalArgumentException("Receptor no existente");
        }
        if (mensaje == null || mensaje.isBlank()) {
            throw new IllegalArgumentException("Mensaje inválido¿");
        }
        Notificaciones n = new Notificaciones(receptor, mensaje);
        notificaciones.add(n);
        String cadena=" Notificación creada para " + receptor.getNombre() + ": " + mensaje;
        return cadena;
    }
    //se agrega en una lista para obtener las notificacion del receptor
    public ArrayList<Notificaciones> obtenerNotificaciones(Usuario receptor) {
        if (receptor == null) {
            throw new IllegalArgumentException("Receptor inválido");
        }

        ArrayList<Notificaciones> lista = new ArrayList<>();
        for (Notificaciones n : notificaciones) {
            if (n.getReceptorN().equals(receptor)) {
                lista.add(n);
            }
        }
        return lista; 
    }
    public void marcarTodasLeidas(Usuario receptor) {
        if (receptor == null) {
            throw new IllegalArgumentException("Usuario receptor inválido");
        }

        for (Notificaciones n : notificaciones) {
            if (n.getReceptorN().getCorreo().equals(receptor.getCorreo())) {
                n.marcarLeido(true);
            }
        }
    }
}
// se obtiene las notificaciones de el usuario por medio de una list 
// en generarNotificacion se usa el receptor y el mensaje, mas tambien lo agrega en una lista
