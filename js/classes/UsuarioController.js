class UsuarioController {
  constructor() {
    this.usuarios = [];
  }

  registrarUsuario(usuario) {
    this.usuarios.push(usuario);
  }

  listarUsuarios() {
    return this.usuarios;
  }

  buscarPorCorreo(correo) {
    return this.usuarios.find(u => u.correo === correo);
  }
}
