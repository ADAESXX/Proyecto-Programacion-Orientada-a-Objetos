// MensajesController.js
// Requiere que exista la clase Mensajes (modelo) en Mensajes.js

class MensajesController {
  constructor(prefix) {
    // encapsula la implementación de mensajes (localStorage)
    // si quieres, puedes usar distintos prefijos por prueba/producción
    this.model = new Mensajes(prefix);
  }

  // Enviar un mensaje (desde y hacia son objetos Usuario/Donante/etc.)
  enviarMensaje(fromUser, toUser, texto) {
    if (!fromUser || !toUser) throw new Error("Remitente o destinatario inválido");
    return this.model.send(fromUser, toUser, texto);
  }

  // Listar inbox de un usuario (devuelve array)
  listarInbox(uid) {
    return this.model.listInbox(uid);
  }

  // Listar enviados de un usuario
  listarEnviados(uid) {
    return this.model.listSent(uid);
  }

  // Marcar todos los mensajes como leídos en la bandeja de entrada
  marcarTodosLeidos(uid) {
    return this.model.markAllRead(uid);
  }

  // Método auxiliar: buscar un mensaje por id en el inbox (devuelve null si no encuentra)
  buscarMensajeEnInbox(uid, messageId) {
    const inbox = this.listarInbox(uid);
    return inbox.find(m => String(m.id) === String(messageId)) || null;
  }

  // Borrar un mensaje de la bandeja de entrada (persistencia manejada por el modelo a través de localStorage)
  borrarMensajeInbox(uid, messageId) {
    const inbox = this.listarInbox(uid).filter(m => String(m.id) !== String(messageId));
    this.model._saveInbox(uid, inbox);
    return true;
  }

  // Borrar un mensaje de enviados
  borrarMensajeEnviados(uid, messageId) {
    const sent = this.listarEnviados(uid).filter(m => String(m.id) !== String(messageId));
    this.model._saveSent(uid, sent);
    return true;
  }
}
