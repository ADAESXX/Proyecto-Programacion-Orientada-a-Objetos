// models/Notificaciones.js
export default class Notificaciones {
  constructor(id, usuario, mensaje, fecha = new Date(), leida = false) {
    this.id = id;
    this.usuario = usuario;
    this.mensaje = mensaje;
    this.fecha = fecha;
    this.leida = leida;
  }

  getId() { return this.id; }
  getUsuario() { return this.usuario; }
  getMensaje() { return this.mensaje; }
  getFecha() { return this.fecha; }
  getLeida() { return this.leida; }

  setUsuario(usuario) { this.usuario = usuario; }
  setMensaje(mensaje) { this.mensaje = mensaje; }
  setLeida(leida) { this.leida = leida; }
}
