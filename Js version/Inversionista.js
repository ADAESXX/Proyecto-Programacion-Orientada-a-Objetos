//clase revisada - validaciones completadas

class Inversionista extends Usuario {
    
    // Lista que contiene las inversiones realizadas por el inversionista
    constructor(nombre, correo, contrasena, ubicacion) {
        // Llamada al constructor de la clase padre (Usuario) para inicializar los atributos heredados
        super(nombre, correo, contrasena, ubicacion);
        
        // Inicializa la lista de inversiones como vacía
        this.inversiones = [];
    }

    // Método para registrar una nueva inversión en la lista
    setInvertir(inversion) {
        if (inversion == null || inversion.trim() === "") { 
            throw new Error("La inversión es inválida");
        }
        this.inversiones.push(inversion.trim());
    }

    // Otro getter que devuelve la lista de inversiones (funciona igual que getInversiones)
    getInvertir() { 
        return this.inversiones; 
    }

}

