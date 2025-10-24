// Programador: José Ignacio Guardado Larios
// Versión: donante-vercion.js

// Importamos la clase base "Usuario" para heredar sus propiedades y métodos
import Usuario from "./Usuario.js";

// Definición de la clase "Donante", que hereda de "Usuario"
export default class Donante extends Usuario {

  // Constructor de la clase Donante
  // Parámetros:
  // - id: Identificador único del usuario
  // - nombre: Nombre completo del donante
  // - correo: Correo electrónico del donante
  // - contrasena: Contraseña del usuario
  // - tipoDonante: Tipo o categoría del donante (por ejemplo: "Mensual", "Único", "Corporativo")
  // - montoDisponible: Monto disponible para donar
  constructor(id, nombre, correo, contrasena, tipoDonante, montoDisponible) {
    // Llamada al constructor de la clase padre (Usuario)
    super(id, nombre, correo, contrasena, "Donante");

    // Verifica que el tipo de donante sea una cadena de texto válida
    if (!tipoDonante || typeof tipoDonante !== "string") {
      throw new Error("El tipo de donante debe ser un texto válido.");
    }

    // Verifica que el monto disponible sea un número no negativo
    if (isNaN(montoDisponible) || montoDisponible < 0) {
      throw new Error("El monto disponible debe ser un número válido y no negativo.");
    }

    // Asignación de propiedades específicas de Donante
    this.tipoDonante = tipoDonante;
    this.montoDisponible = montoDisponible;
  }

  // Obtiene el tipo de donante
  getTipoDonante() {
    return this.tipoDonante;
  }

  // Obtiene el monto disponible del donante
  getMontoDisponible() {
    return this.montoDisponible;
  }

  // Modifica el tipo de donante
  setTipoDonante(tipoDonante) {
    if (!tipoDonante || typeof tipoDonante !== "string") {
      throw new Error("El tipo de donante debe ser un texto válido.");
    }
    this.tipoDonante = tipoDonante;
  }

  // Modifica el monto disponible del donante
  setMontoDisponible(montoDisponible) {
    if (isNaN(montoDisponible) || montoDisponible < 0) {
      throw new Error("El monto disponible debe ser un número válido y no negativo.");
    }
    this.montoDisponible = montoDisponible;
  }

  // Retorna una representación en texto del objeto Donante
  toString() {
    return `Donante: ${this.nombre} | Tipo: ${this.tipoDonante} | Monto disponible: Q${this.montoDisponible}`;
  }
}
