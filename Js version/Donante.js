// models/Donante.js
// programador: José Ignacio Guardado Larios
import Usuario from "./Usuario.js";

export default class Donante extends Usuario {
  constructor(id, nombre, correo, contrasena, tipoDonante, montoDisponible) {
    super(id, nombre, correo, contrasena, "Donante");
    this.tipoDonante = tipoDonante;
    this.montoDisponible = montoDisponible;
  }

  getTipoDonante() { return this.tipoDonante; }
  getMontoDisponible() { return this.montoDisponible; }

  setTipoDonante(tipoDonante) { this.tipoDonante = tipoDonante; }
  setMontoDisponible(montoDisponible) { this.montoDisponible = montoDisponible; }
}
