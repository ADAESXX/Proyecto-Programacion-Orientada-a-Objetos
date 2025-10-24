// Clase revisada - validaciones completas
// Programador: José Ignacio Guardado Larios
// Archivo: NotificacionController.js

// Hereda la estructura del modelo de notificaciones
import Notificaciones from "./Notificaciones.js";

class NotificacionController {

    // Constructor: inicializa la lista de notificaciones vacía
    constructor() {
        this.notificaciones = [];
    }

    // Genera una nueva notificación para un receptor específico con su mensaje
    generarNotificacion(receptor, mensaje) {
        if (receptor == null) {
            throw new Error("El receptor de la notificación no puede ser nulo o indefinido.");
        }
        if (mensaje == null || mensaje.trim() === "") {
            throw new Error("El mensaje de la notificación no puede estar vacío.");
        }

        // Crea una nueva notificación con ID autoincremental
        const nuevaNotif = new Notificaciones(
            this.notificaciones.length + 1, // ID
            receptor,
            mensaje.trim()
        );

        // Agrega la nueva notificación a la lista general
        this.notificaciones.push(nuevaNotif);

        // Retorna un mensaje descriptivo de la creación
        const nombreReceptor = receptor.getNombre ? receptor.getNombre() : receptor.nombre;
        return `Notificación creada para ${nombreReceptor}: ${mensaje}`;
    }

    // Retorna todas las notificaciones que pertenecen a un receptor específico
    obtenerNotificaciones(receptor) {
        if (receptor == null) {
            throw new Error("Debe especificar un receptor válido.");
        }

        // Filtra las notificaciones según el usuario receptor
        return this.notificaciones.filter((n) => {
            const usuario = n.getUsuario ? n.getUsuario() : n.usuario;
            return (
                usuario === receptor ||
                (usuario.correo && usuario.correo === receptor.correo)
            );
        });
    }

    // Marca todas las notificaciones del receptor como leídas
    marcarTodasLeidas(receptor) {
        if (receptor == null) {
            throw new Error("Debe especificar un usuario receptor válido.");
        }

        for (const n of this.notificaciones) {
            const usuario = n.getUsuario ? n.getUsuario() : n.usuario;

            const correoUsuario = usuario.getCorreo
                ? usuario.getCorreo()
                : usuario.correo;

            const correoReceptor = receptor.getCorreo
                ? receptor.getCorreo()
                : receptor.correo;

            if (correoUsuario === correoReceptor) {
                // Verifica si la clase Notificaciones tiene los métodos adecuados
                if (n.marcarLeida) {
                    n.marcarLeida(true);
                } else if (n.setLeida) {
                    n.setLeida(true);
                } else {
                    // Si no existe método, crea la propiedad directamente
                    n.leida = true;
                }
            }
        }
    }
}

export default NotificacionController;
