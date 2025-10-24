// Archivo: Notificaciones.js

export default class Notificaciones {

    // Campos privados
    #id;
    #usuario;
    #mensaje;
    #fecha;
    #leida;

    // Constructor para inicializar una notificación
    constructor(id, usuario, mensaje, fecha = new Date(), leida = false) {
        if (id == null || id <= 0) {
            throw new Error("El ID de la notificación debe ser un número válido y mayor que cero.");
        }
        if (!usuario) {
            throw new Error("El usuario receptor de la notificación es obligatorio.");
        }
        if (!mensaje || mensaje.trim() === "") {
            throw new Error("El mensaje de la notificación no puede estar vacío.");
        }

        this.#id = id;
        this.#usuario = usuario;
        this.#mensaje = mensaje.trim();
        this.#fecha = fecha instanceof Date ? fecha : new Date(fecha);
        this.#leida = Boolean(leida);
    }

    // Getter para obtener el ID de la notificación
    getId() {
        return this.#id;
    }

    // Getter para obtener el usuario asociado
    getUsuario() {
        return this.#usuario;
    }

    // Getter para obtener el mensaje
    getMensaje() {
        return this.#mensaje;
    }

    // Getter para obtener la fecha
    getFecha() {
        return this.#fecha;
    }

    // Getter para saber si está leída
    getLeida() {
        return this.#leida;
    }

    // Setter para modificar el usuario receptor
    setUsuario(usuario) {
        if (!usuario) {
            throw new Error("El usuario no puede ser nulo.");
        }
        this.#usuario = usuario;
    }

    // Setter para modificar el mensaje de la notificación
    setMensaje(mensaje) {
        if (!mensaje || mensaje.trim() === "") {
            throw new Error("El mensaje no puede estar vacío.");
        }
        this.#mensaje = mensaje.trim();
    }

    // Setter para modificar el estado de lectura
    setLeida(leida) {
        this.#leida = Boolean(leida);
    }

    // Marca la notificación como leída
    marcarLeida() {
        this.#leida = true;
    }

    // Devuelve una representación en texto de la notificación
    toString() {
        const fechaFormateada = this.#fecha.toLocaleString();
        const usuarioNombre =
            this.#usuario?.getNombre?.() || this.#usuario?.nombre || "Desconocido";
        return `Notificación #${this.#id} | Usuario: ${usuarioNombre} | Mensaje: "${this.#mensaje}" | Fecha: ${fechaFormateada} | Leída: ${this.#leida}`;
    }
}

