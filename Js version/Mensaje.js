// models/Mensaje.js
export default class Mensaje {
  constructor(id, remitente, destinatario, contenido, fecha = new Date()) {
    this.id = id;
    this.remitente = remitente;
    this.destinatario = destinatario;
    this.contenido = contenido;
    this.fecha = fecha;
  }

  getId() { return this.id; }
  getRemitente() { return this.remitente; }
  getDestinatario() { return this.destinatario; }
  getContenido() { return this.contenido; }
  getFecha() { return this.fecha; }

  setRemitente(r) { this.remitente = r; }
  setDestinatario(d) { this.destinatario = d; }
  setContenido(c) { this.contenido = c; }
}
