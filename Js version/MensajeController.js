//clase revisada - validaciones completas

class MensajeController {
  constructor() {
    this.mensajes = [];
  }

  enviarMensaje(mensaje) {
    //validacion para que el mensjae no este vacío
    if (mensaje == null) {
      throw new Error("Mensaje inválido, ya que no existe");
    }
    //validacion de que si exista el emisor y receptor
    if (mensaje.getEmisor() == null || mensaje.getReceptor() == null) {
      throw new Error("Inválido: emisor y receptor deben existir");
    }
    //validacion para que si exista el contenido del mensaje
    if (mensaje.getContenido() == null || mensaje.getContenido().trim() === "") {
      throw new Error("Mensaje inválido, por no tener contenido ");
    }
    this.mensajes.push(mensaje);
    mensaje.enviar();
  }

  // Ver mensajes entre dos usuarios
  verMensajes(usuario1, usuario2) {
    if (usuario1 == null || usuario2 == null) {
      throw new Error("Usuarios inválidos");
    }
    let mensajesFiltrados = [];
    for (let m of this.mensajes) {
      if (
        (m.getEmisor() === usuario1 && m.getReceptor() === usuario2) ||
        (m.getEmisor() === usuario2 && m.getReceptor() === usuario1)
      ) {
        mensajesFiltrados.push(m);
      }
    }
    return mensajesFiltrados;
  }

  getTodosMensajes() {
    return [...this.mensajes];
  }
}
