// models/Estudiante.js
// programado: José Ignacio Guardado
import Usuario from "./Usuario.js";

export default class Estudiante extends Usuario {
  constructor(id, nombre, correo, contrasena, carrera, universidad) {
    super(id, nombre, correo, contrasena, "Estudiante");
    this.carrera = carrera;
    this.universidad = universidad;
  }

  getCarrera() { return this.carrera; }
  getUniversidad() { return this.universidad; }

  setCarrera(carrera) { this.carrera = carrera; }
  setUniversidad(universidad) { this.universidad = universidad; }
}

