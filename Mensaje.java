//clase revisada - validaciones completas
//falta ver funcionalidades extras
//falta comunicacion entre clases
public class Mensaje {
    
    // Usuario que envía el mensaje
    private Usuario emisor;
    
    // Usuario que recibe el mensaje
    private Usuario receptor;
    
    // Contenido del mensaje (texto enviado)
    private String contenido;

    // Constructor que inicializa el mensaje con un emisor, un receptor y el contenido
    public Mensaje(Usuario emisor, Usuario receptor, String contenido) {
        setEmisor(emisor);
        setReceptor(receptor);
        setContenido(contenido);
    }

    // Getter y Setter para el emisor
    public Usuario getEmisor() { return emisor; }
    public void setEmisor(Usuario emisor) { 
        if (emisor == null ){ 
                throw new IllegalArgumentException("No existe el emisor");
        }
        this.emisor = emisor; 
    }

    // Getter y Setter para el receptor
    public Usuario getReceptor() { return receptor; }
    public void setReceptor(Usuario receptor) { 
        if (receptor == null ){ 
                throw new IllegalArgumentException("No existe el receptor");
        }
        this.receptor = receptor; 
    }

    // Getter y Setter para el contenido del mensaje
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { 
        if (contenido == null || contenido.isBlank() ){ 
                throw new IllegalArgumentException("El contenido es nulo o en blanco");
        }
        this.contenido = contenido; 
    }

    // Método que simula el envío del mensaje mostrando un texto en consola
    public String enviar() {
        String cadena=
            "Mensaje enviado de " + emisor.getNombre() + 
            " a " + receptor.getNombre() + 
            ": " + contenido;
        return cadena;
    }
}

