class EmprendedorController {
  constructor() {
    this.emprendedores = [];
  }

  agregarEmprendedor(e) { this.emprendedores.push(e); }
  listarEmprendedores() { return this.emprendedores; }
  buscarPorId(id) { return this.emprendedores.find(e => e.id === id); }
}
