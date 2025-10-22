class UsuarioController {
  constructor(id, nombre, correo, ubicacion, contrasena, verificado) {
    if (typeof id === "number") {
      // Constructor con id (para obtener desde BD)
      this.id = id;
      this.nombre = nombre;
      this.correo = correo;
      this.ubicacion = ubicacion;
      this.contrasena = contrasena;
      this.verificado = verificado;
    } else {
      // Constructor sin id (para registrar)
      this.id = 0;
      this.nombre = id || "";
      this.correo = nombre || "";
      this.ubicacion = correo || "";
      this.contrasena = ubicacion || "";
      this.verificado = contrasena || false;
    }
  }

  // Getters y setters
  getId() {
    return this.id;
  }

  setId(id) {
    this.id = id;
  }

  getNombre() {
    return this.nombre;
  }

  setNombre(nombre) {
    this.nombre = nombre;
  }

  getCorreo() {
    return this.correo;
  }

  setCorreo(correo) {
    this.correo = correo;
  }

  getUbicacion() {
    return this.ubicacion;
  }

  setUbicacion(ubicacion) {
    this.ubicacion = ubicacion;
  }

  getContrasena() {
    return this.contrasena;
  }

  setContrasena(contrasena) {
    this.contrasena = contrasena;
  }

  isVerificado() {
    return this.verificado;
  }

  setVerificado(verificado) {
    this.verificado = Boolean(verificado);
  }
}
