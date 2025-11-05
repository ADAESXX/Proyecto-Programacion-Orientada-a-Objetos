class VoluntarioController {
  constructor() {
    this.voluntarios = [];
  }

  agregarVoluntario(v) { this.voluntarios.push(v); }
  listarVoluntarios() { return this.voluntarios; }
  buscarPorId(id) { return this.voluntarios.find(v => v.id === id); }
}
