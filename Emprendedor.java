class Emprendedor extends Usuario {
    
    // Atributo para guardar el nombre principal del proyecto del emprendedor
    private String nombreProyecto;
    
    // Lista que contendrá todos los proyectos que el emprendedor vaya agregando
    private List<String> proyectos;

    // Constructor que recibe los datos del usuario y el nombre de su proyecto principal
    public Emprendedor(String nombre, String correo, String contrasena, String ubicacion, String nombreProyecto) {
        // Llamada al constructor de la clase padre (Usuario) para inicializar los atributos heredados
        super(nombre, correo, contrasena, ubicacion);
        
        // Inicializa el nombre principal del proyecto
        this.nombreProyecto = nombreProyecto;
        
        // Inicializa la lista de proyectos vacía
        this.proyectos = new ArrayList<>();
    }

    // Getter para obtener el nombre principal del proyecto
    public String getNombreProyecto() { 
        return nombreProyecto; 
    }

    // Setter para modificar el nombre principal del proyecto
    public void setNombreProyecto(String nombreProyecto) { 
        this.nombreProyecto = nombreProyecto; 
    }

    // Getter para obtener la lista completa de proyectos
    public List<String> getProyectos() { 
        return proyectos; 
    }

    // Setter para modificar la lista de proyectos (reemplazarla por otra lista)
    public void setProyectos(List<String> proyectos) { 
        this.proyectos = proyectos; 
    }

    // Método para agregar un nuevo proyecto a la lista
    public void agregarProyectos(String proyecto) {
        this.proyectos.add(proyecto);
    }
}

