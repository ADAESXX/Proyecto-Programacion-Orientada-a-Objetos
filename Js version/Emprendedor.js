//clase revisada - validaciones completas

//hereda método y atributos de la clase Usuario
class Emprendedor extends Usuario {
    
    // Atributo para guardar el nombre principal del proyecto del emprendedor
    constructor(nombre, correo, contrasena, ubicacion, nombreProyecto) {
        // Llamada al constructor de la clase padre (Usuario) para inicializar los atributos heredados
        super(nombre, correo, contrasena, ubicacion);
        
        // Inicializa el nombre principal del proyecto
        this.setNombreProyecto(nombreProyecto);
        
        // Inicializa la lista de proyectos vacía
        this.proyectos = [];
    }

    // Getter para obtener el nombre principal del proyecto
    getNombreProyecto() { 
        return this.nombreProyecto; 
    }

    // Setter para modificar el nombre principal del proyecto
    setNombreProyecto(nombreProyecto) { 
        if (nombreProyecto == null || nombreProyecto.trim() === "") { 
            throw new Error("El nombre del proyecto no puede estar vacío");
        }
        this.nombreProyecto = nombreProyecto.trim(); 
    }

    // Getter para obtener la lista completa de proyectos
    getProyectos() { 
        return this.proyectos; 
    }

    // Setter para modificar la lista de proyectos (reemplazarla por otra lista)
    setProyectos(proyectos) {
        if (proyectos == null) { 
            throw new Error("La lista de proyectos no puede estar vacía");
        } 
        this.proyectos = proyectos; 
    }

    // Método para agregar un nuevo proyecto a la lista
    agregarProyectos(proyecto) {
        if (proyecto == null || proyecto.trim() === "") { 
            throw new Error("El proyecto no puede ser vacío o nulo");
        }
        this.proyectos.push(proyecto);
    }
}
