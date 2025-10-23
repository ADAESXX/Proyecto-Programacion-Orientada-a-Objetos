//clase revisada - validaciones completas

class Mensaje {
    
    // Usuario que envía el mensaje
    // Usuario que recibe el mensaje
    // Contenido del mensaje (texto enviado)
    constructor(emisor, receptor, contenido) {
        this.setEmisor(emisor);
        this.setReceptor(receptor);
        this.setContenido(contenido);
    }

    // Getter y Setter para el emisor
    getEmisor() { return this.emisor; }
    setEmisor(emisor) { 
        if (emisor == null) { 
            throw new Error("No existe el emisor");
        }
        this.emisor = emisor; 
    }

    // Getter y Setter para el receptor
    getReceptor() { return this.receptor; }
    setReceptor(receptor) { 
        if (receptor == null) { 
            throw new Error("No existe el receptor");
        }
        this.receptor = receptor; 
    }

    // Getter y Setter para el contenido del mensaje
    getContenido() { return this.contenido; }
    setContenido(contenido) { 
        if (contenido == null || contenido.trim() === "") { 
            throw new Error("El contenido es nulo o en blanco");
        }
        this.contenido = contenido; 
    }

    // Método que simula el envío del mensaje mostrando un texto en consola
    enviar() {
        let cadena = 
            "Mensaje enviado de " + this.emisor.getNombre() + 
            " a " + this.receptor.getNombre() + 
            ": " + this.contenido;
        return cadena;
    }
}
```
