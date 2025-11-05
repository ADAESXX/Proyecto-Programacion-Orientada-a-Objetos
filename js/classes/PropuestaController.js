class PropuestaController {
  constructor() {
    this.propuestas = [];
  }

  agregarPropuesta(p) { this.propuestas.push(p); }
  listarPropuestas() { return this.propuestas; }
  buscarPorId(id) { return this.propuestas.find(p => p.id === id); }
  cambiarEstado(id, nuevoEstado) {
    const propuesta = this.buscarPorId(id);
    if (!propuesta) throw new Error("Propuesta no encontrada");
    propuesta.setEstado(nuevoEstado);
    return propuesta;
  }
}
