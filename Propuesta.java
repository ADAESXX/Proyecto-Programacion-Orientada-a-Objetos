class Propuesta {
  constructor(titulo, descripcion, creador) {
    this.setTitulo(titulo);
    this.setDescripcion(descripcion);
    this.setCreador(creador);
    this.progreso = 0;     // 0 a 100
    this.meta = 0.0;       // meta de dinero
    this.recaudado = 0.0;  // dinero recaudado

    this.inversionistas = [];
    this.donantes = [];
    this.voluntarios = [];
  }

  // --- Métodos existentes ---
  getTitulo() {
    return this.titulo;
  }

  setTitulo(titulo) {
    if (!titulo || typeof titulo !== "string" || titulo.trim() === "") {
      throw new Error("Título vacío o nulo, por lo que es inválido");
    }
    this.titulo = titulo.trim();
  }

  getDescripcion() {
    return this.descripcion;
  }

  setDescripcion(descripcion) {
    if (!descripcion || typeof descripcion !== "string" || descripcion.trim() === "") {
      throw new Error("Descripción vacía o nula, por lo que inválido");
    }
    this.descripcion = descripcion.trim();
  }

  getCreador() {
    return this.creador;
  }

  setCreador(creador) {
    if (!creador) {
      throw new Error("No existe el creador");
    }
    this.creador = creador;
  }

  getProgreso() {
    return this.progreso;
  }

  setProgreso(progreso) {
    if (typeof progreso !== "number" || progreso < 0 || progreso > 100) {
      throw new Error("El progreso debe de estar entre 0 y 100");
    }
    this.progreso = progreso;
  }

  // --- NUEVOS MÉTODOS: meta y recaudación ---
  getMeta() {
    return this.meta;
  }

  setMeta(meta) {
    if (typeof meta !== "number" || meta <= 0) {
      throw new Error("La meta debe ser mayor a 0");
    }
    this.meta = meta;
  }

  getRecaudado() {
    return this.recaudado;
  }

  agregarRecaudacion(cantidad) {
    if (typeof cantidad !== "number" || cantidad <= 0) {
      throw new Error("La cantidad debe ser mayor a 0");
    }
    this.recaudado += cantidad;
  }

  // --- Colecciones ---
  getInversionistas() {
    return this.inversionistas;
  }

  setInversionistas(inver) {
    if (!inver) {
      throw new Error("Inversionista inválido");
    }
    this.inversionistas.push(inver);
  }

  getDonantes() {
    return this.donantes;
  }

  setDonantes(donan) {
    if (!donan) {
      throw new Error("Donante inválido");
    }
    this.donantes.push(donan);
  }

  getVoluntarios() {
    return this.voluntarios;
  }

  setVoluntarios(volun) {
    if (!volun) {
      throw new Error("Voluntario inválido");
    }
    this.voluntarios.push(volun);
  }

  toString() {
    const correoCreador = (this.creador && typeof this.creador.getCorreo === "function")
      ? this.creador.getCorreo()
      : (this.creador && this.creador.correo) ? this.creador.correo : "N/A";

    return `Propuesta{titulo='${this.titulo}', progreso=${this.progreso}%, creador=${correoCreador}, meta=$${this.meta}, recaudado=$${this.recaudado}}`;
    }
}
