// clase revisada - validaciones completas
// el extends permite que se hereden atributos y/o métodos de la clase Usuario
class Estudiante extends Usuario {
  constructor(nombre, correo, contrasena, ubicacion, carrera) {
    super(nombre, correo, contrasena, ubicacion);
    this.setCarrera(carrera);
    // En Fase 2 “propuestas” aparece como String; lo manejamos como lista de títulos para ser útil.
    this.propuestas = [];
  }

  // Acciones clave
  agregarPropuesta(titulo) {
    if (!titulo || titulo.trim() === "") {
      throw new Error("título vacío, por lo que es inválido");
    }
    // el trim quita los espacios en blanco
    this.propuestas.push(titulo.trim());
  }

  // Getters/Setters
  getCarrera() {
    return this.carrera;
  }

  setCarrera(carrera) {
    if (!carrera || carrera.trim() === "") {
      throw new Error("Carrera vacía, por lo que es inválido");
    }
    this.carrera = carrera.trim();
  }

  // Compatibles con “setPropuestas / getPropuestas” de la fase
  getPropuestas() {
    return this.propuestas;
  }

  setPropuestas(nuevas) {
    if (nuevas == null) {
      throw new Error("La lista propuesta no puede ser nula");
    }
    if (!Array.isArray(nuevas)) {
      throw new Error("Las propuestas deben ser un arreglo");
    }
    this.propuestas = [...nuevas];
  }
}
