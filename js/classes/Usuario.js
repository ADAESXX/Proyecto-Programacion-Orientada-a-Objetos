
class Usuario {
  constructor(id, nombre, correo, contrasena, tipoUsuario) {
    this.id = id;
    this.nombre = nombre;
    this.correo = correo;
    this.contrasena = contrasena;
    this.tipoUsuario = tipoUsuario;
  }

  // Getters
  getId() { return this.id; }
  getNombre() { return this.nombre; }
  getCorreo() { return this.correo; }
  getContrasena() { return this.contrasena; }
  getTipoUsuario() { return this.tipoUsuario; }

  // Setters
  setId(id) { this.id = id; }
  setNombre(nombre) { this.nombre = nombre; }
  setCorreo(correo) { this.correo = correo; }
  setContrasena(contrasena) { this.contrasena = contrasena; }
  setTipoUsuario(tipoUsuario) { this.tipoUsuario = tipoUsuario; }
}
