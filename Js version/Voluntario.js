// models/Voluntario.js


import Usuario from "./Usuario.js";

export default class Voluntario extends Usuario {

    // Constructor inicializa atributos heredados y listas vacías
    constructor(id, nombre, correo, contrasena, ubicacion) {
        super(id, nombre, correo, contrasena, "Voluntario", ubicacion);

        this.habilidades = [];
        this.apoyos = [];
    }

    // Devuelve la lista de habilidades del voluntario (copia del array)
    getHabilidades() {
        return [...this.habilidades];
    }

    // Agrega una nueva habilidad
    setHabilidades(habilidad) {
        if (!habilidad || typeof habilidad !== "string" || habilidad.trim() === "") {
            throw new Error("No ingresó una habilidad válida");
        }

        const habilidadLimpia = habilidad.trim();

        if (this.habilidades.includes(habilidadLimpia)) {
            throw new Error("La habilidad ya existe en la lista");
        }

        this.habilidades.push(habilidadLimpia);
    }

    // Devuelve la lista de apoyos (copia del array)
    getApoyos() {
        return [...this.apoyos];
    }

    // Agrega un nuevo apoyo
    setApoyos(apoyo) {
        if (!apoyo || typeof apoyo !== "string" || apoyo.trim() === "") {
            throw new Error("Apoyo inválido o vacío");
        }

        const apoyoLimpio = apoyo.trim();

        if (this.apoyos.includes(apoyoLimpio)) {
            throw new Error("El apoyo ya está registrado");
        }

        this.apoyos.push(apoyoLimpio);
    }

    // Representación en texto del voluntario
    toString() {
        const habilidadesTexto = this.habilidades.length > 0 ? this.habilidades.join(", ") : "Ninguna";
        const apoyosTexto = this.apoyos.length > 0 ? this.apoyos.join(", ") : "Ninguno";

        return `Voluntario → Habilidades: ${habilidadesTexto} | Apoyos: ${apoyosTexto}`;
    }
}
