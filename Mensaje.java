class Mensaje {
    
    // Usuario que envía el mensaje
    private Usuario emisor;
    
    // Usuario que recibe el mensaje
    private Usuario receptor;
    
    // Contenido del mensaje (texto enviado)
    private String contenido;

    // Constructor que inicializa el mensaje con un emisor, un receptor y el contenido
    public Mensaje(Usuario emisor, Usuario receptor, String contenido) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.contenido = contenido;
    }

    // Getter y Setter para el emisor
    public Usuario getEmisor() { return emisor; }
    public void setEmisor(Usuario emisor) { this.emisor = emisor; }

    // Getter y Setter para el receptor
    public Usuario getReceptor() { return receptor; }
    public void setReceptor(Usuario receptor) { this.receptor = receptor; }

    // Getter y Setter para el contenido del mensaje
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    // Método que simula el envío del mensaje mostrando un texto en consola
    public void enviar() {
        System.out.println(
            "Mensaje enviado de " + emisor.getNombre() + 
            " a " + receptor.getNombre() + 
            ": " + contenido
        );
    }
}

