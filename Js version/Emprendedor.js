class Emprendedor extends Usuario {
  constructor(nombre, correo, contrasena, ubicacion, nombreProyecto) {
    super(nombre, correo, contrasena, ubicacion);
    this.setNombreProyecto(nombreProyecto);
    this.proyectos = [];
  }

  getNombreProyecto() {
    return this.nombreProyecto;
  }

  setNombreProyecto(nombreProyecto) {
    if (nombreProyecto == null || nombreProyecto.trim() === "") {
      throw new Error("El nombre del proyecto no puede estar vacío");
    }
    this.nombreProyecto = nombreProyecto.trim();
  }

  getProyectos() {
    return this.proyectos;
  }

  setProyectos(proyectos) {
    if (proyectos == null) {
      throw new Error("La lista de proyectos no puede estar vacía");
    }
    this.proyectos = proyectos;
  }

  agregarProyectos(proyecto) {
    if (proyecto == null || proyecto.trim() === "") {
      throw new Error("El proyecto no puede ser vacío o nulo");
    }
    this.proyectos.push(proyecto);
  }
}
