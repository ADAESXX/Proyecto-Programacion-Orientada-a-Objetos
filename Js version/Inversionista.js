// models/Inversionista.js
// Programador: José Ignacio Guardado 
import Usuario from "./Usuario.js";

export default class Inversionista extends Usuario {
  constructor(id, nombre, correo, contrasena, empresa, capitalDisponible) {
    super(id, nombre, correo, contrasena, "Inversionista");
    this.empresa = empresa;
    this.capitalDisponible = capitalDisponible;
  }

  getEmpresa() { return this.empresa; }
  getCapitalDisponible() { return this.capitalDisponible; }

  setEmpresa(empresa) { this.empresa = empresa; }
  setCapitalDisponible(capitalDisponible) { this.capitalDisponible = capitalDisponible; }
}
