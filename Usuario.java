class Usuario {
  constructor(nombre, correo, contrasena, ubicacion) {
    if (nombre !== undefined && correo !== undefined && contrasena !== undefined && ubicacion !== undefined) {
      this.setNombre(nombre);
      this.setCorreo(correo);
      this.setContrasena(contrasena);
      this.setUbicacion(ubicacion);
      this.verificado = false;
    } else {
      this.nombre = "";
      this.correo = "";
      this.contrasena = "";
      this.ubicacion = "";
      this.verificado = false;
    }
  }

  verificarCuenta() {
    this.verificado = true;
  }

  actualizarPerfil(nuevoNombre, nuevaUbicacion, nuevaContrasena, nuevoCorreo) {
    this.setNombre(nuevoNombre);
    this.setCorreo(nuevoCorreo);
    this.setContrasena(nuevaContrasena);
    this.setUbicacion(nuevaUbicacion);
  }

  getNombre() {
    return this.nombre;
  }

  setNombre(nombre) {
    if (!nombre || nombre.trim() === "") {
      throw new Error("El nombre no puede estar vacío");
    }
    this.nombre = nombre.trim();
  }

  getCorreo() {
    return this.correo;
  }

  setCorreo(correo) {
    const regex = /^[\w._%+-]+@[\w.-]+\.[a-zA-Z]{2,6}$/;
    if (!correo || !regex.test(correo)) {
      throw new Error("Correo inválido");
    }
    this.correo = correo.trim().toLowerCase();
  }

  getContrasena() {
    return this.contrasena;
  }

  setContrasena(contrasena) {
    if (!contrasena || contrasena.trim().length < 6) {
      throw new Error("La contraseña debe tener al menos 6 caracteres");
    }
    this.contrasena = contrasena.trim();
  }

  getUbicacion() {
    return this.ubicacion;
  }

  setUbicacion(ubicacion) {
    if (!ubicacion || ubicacion.trim() === "") {
      throw new Error("La ubicación no puede estar vacía");
    }
    this.ubicacion = ubicacion.trim();
  }

  getVerificado() {
    return this.verificado;
  }

  setVerificado(verificado) {
    this.verificado = Boolean(verificado);
  }

  equals(obj) {
    if (this === obj) return true;
    if (!obj || !(obj instanceof Usuario)) return false;
    return this.correo === obj.correo;
  }

  hashCode() {
    // Implementación simple de hash basada en el correo (no nativa de JS)
    if (!this.correo) return 0;
    let hash = 0;
    for (let i = 0; i < this.correo.length; i++) {
      hash = (hash << 5) - hash + this.correo.charCodeAt(i);
      hash |= 0; // a 32-bit integer
    }
    return hash;
  }

  toString() {
    return `Usuario{nombre='${this.nombre}', correo='${this.correo}', ubicacion='${this.ubicacion}', verificado=${this.verificado}}`;
  }
}
