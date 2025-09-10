// se importa el java list y array
import java.util.ArrayList;
import java.util.List;
public class NotificacionController {
    private List<Notificacion> notificaciones;
//las notificaciones se agregan a una lista 
    public NotificacionController() {
        this.notificaciones = new ArrayList<>();
    }
    //se agrega la noficiacon del receptor y el mensaje 
    public void generarNotificacion(Usuario receptor, String mensaje) {
        Notificacion n = new Notificacion(receptor, mensaje);
        notificaciones.add(n);
        System.out.println(" Notificación creada para " 
                           + receptor.getNombre() + ": " + mensaje);
    }
    //se agrega en una lista para obtener las notificacion del receptor
    public List<Notificacion> obtenerNotificaciones(Usuario receptor) {
        List<Notificacion> lista = new ArrayList<>();
        for (Notificacion n : notificaciones) {
            if (n.getReceptorN().equals(receptor)) {
                lista.add(n);
            }
        }
        return lista; 
    }
}
// se obtiene las notificaciones de el usuario por medio de una list 
// en generarNotificacion se usa el receptor y el mensaje, mas tambien lo agrega en una lista
