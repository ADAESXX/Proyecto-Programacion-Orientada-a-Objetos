// models/Propuesta.js
export default class Propuesta {
  constructor(id, titulo, descripcion, montoSolicitado, creador, estado = "Pendiente") {
    this.id = id;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.montoSolicitado = montoSolicitado;
    this.creador = creador;
    this.estado = estado;
    this.inversionistas = [];
  }

  agregarInversionista(inversionista) {
    this.inversionistas.push(inversionista);
  }

  getInversionistas() {
    return this.inversionistas;
  }

  // Getters y setters
  getId() { return this.id; }
  getTitulo() { return this.titulo; }
  getDescripcion() { return this.descripcion; }
  getMontoSolicitado() { return this.montoSolicitado; }
  getCreador() { return this.creador; }
  getEstado() { return this.estado; }

  setTitulo(titulo) { this.titulo = titulo; }
  setDescripcion(descripcion) { this.descripcion = descripcion; }
  setMontoSolicitado(monto) { this.montoSolicitado = monto; }
  setCreador(creador) { this.creador = creador; }
  setEstado(estado) { this.estado = estado; }
}
