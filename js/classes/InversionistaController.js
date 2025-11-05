class InversionistaController {
  constructor() {
    this.inversionistas = [];
  }

  agregarInversionista(i) { this.inversionistas.push(i); }
  listarInversionistas() { return this.inversionistas; }
  buscarPorId(id) { return this.inversionistas.find(i => i.id === id); }
}
