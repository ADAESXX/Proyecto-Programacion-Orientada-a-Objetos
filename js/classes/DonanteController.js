class DonanteController {
  constructor() {
    this.donantes = [];
  }

  agregarDonante(donante) {
    this.donantes.push(donante);
  }

  listarDonantes() {
    return this.donantes;
  }

  buscarPorId(id) {
    return this.donantes.find(d => d.id === id);
  }

  actualizarMonto(id, nuevoMonto) {
    const donante = this.buscarPorId(id);
    if (!donante) throw new Error("Donante no encontrado");
    donante.setMontoDisponible(nuevoMonto);
    return donante;
  }
}
