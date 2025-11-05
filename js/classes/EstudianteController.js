class EstudianteController {
  constructor() {
    this.estudiantes = [];
  }

  agregarEstudiante(e) { this.estudiantes.push(e); }
  listarEstudiantes() { return this.estudiantes; }
  buscarPorId(id) { return this.estudiantes.find(e => e.id === id); }
}
