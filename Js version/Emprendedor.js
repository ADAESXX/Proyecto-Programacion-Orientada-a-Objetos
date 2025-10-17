// models/Emprendedor.js
// progamador: José Ignacio Guardado
import Usuario from "./Usuario.js";

export default class Emprendedor extends Usuario {
  constructor(id, nombre, correo, contrasena, empresa, sector) {
    super(id, nombre, correo, contrasena, "Emprendedor");
    this.empresa = empresa;
    this.sector = sector;
  }

  getEmpresa() { return this.empresa; }
  getSector() { return this.sector; }

  setEmpresa(empresa) { this.empresa = empresa; }
  setSector(sector) { this.sector = sector; }
}
