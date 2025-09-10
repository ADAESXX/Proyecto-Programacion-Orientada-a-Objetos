class Inversionista extends Usuario {
    
    // Lista que contiene las inversiones realizadas por el inversionista
    private List<String> inversiones;

    // Constructor que recibe los datos del usuario y crea una lista vacía de inversiones
    public Inversionista(String nombre, String correo, String contrasena, String ubicacion) {
        // Llamada al constructor de la clase padre (Usuario) para inicializar los atributos heredados
        super(nombre, correo, contrasena, ubicacion);
        
        // Inicializa la lista de inversiones como vacía
        this.inversiones = new ArrayList<>();
    }

    // Getter para obtener la lista completa de inversiones
    public List<String> getInversiones() { 
        return inversiones; 
    }

    // Método para registrar una nueva inversión en la lista
    public void setInvertir(String inversion) {
        this.inversiones.add(inversion);
    }

    // Otro getter que devuelve la lista de inversiones (funciona igual que getInversiones)
    public List<String> getInvertir() { 
        return inversiones; 
    }
}

