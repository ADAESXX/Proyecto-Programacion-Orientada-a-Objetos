// NotificacionesController.js
// Requiere que exista la clase Notificaciones (modelo) en Notificaciones.js

class NotificacionesController {
  constructor(prefix) {
    // encapsula la implementación de notificaciones (localStorage)
    this.model = new Notificaciones(prefix);
  }

  // Añade una notificación para un usuario.
  // notif puede ser un objeto con { title, body, url, ... } (lo que tu UI necesite)
  agregarNotificacion(uid, notif) {
    if (!uid) throw new Error("Usuario inválido");
    return this.model.add(uid, notif);
  }

  // Lista todas las notificaciones de un usuario
  listarNotificaciones(uid) {
    return this.model.list(uid);
  }

  // Marca todas como leídas
  marcarTodasLeidas(uid) {
    return this.model.markAllRead(uid);
  }

  // Obtener solo las no leídas
  listarNoLeidas(uid) {
    return this.listarNotificaciones(uid).filter(n => !n.read);
  }

  // Borrar una notificación por id
  borrarNotificacion(uid, notifId) {
    const arr = this.listarNotificaciones(uid).filter(n => String(n.id) !== String(notifId));
    try {
      localStorage.setItem((this.model._key(uid)), JSON.stringify(arr));
      return true;
    } catch (e) {
      return false;
    }
  }
}
