//clase revisada - validaciones completas
//faltan revisar comunicacion entre clases y revisar funciones extras
import java.util.*;
//hereda método y atributos de la clase Uusuario
class Emprendedor extends Usuario {
    
    // Atributo para guardar el nombre principal del proyecto del emprendedor
    private String nombreProyecto;
    
    // Lista que contendrá todos los proyectos que el emprendedor vaya agregando
    private ArrayList<String> proyectos;

    // Constructor que recibe los datos del usuario y el nombre de su proyecto principal
    public Emprendedor(String nombre, String correo, String contrasena, String ubicacion, String nombreProyecto) {
        // Llamada al constructor de la clase padre (Usuario) para inicializar los atributos heredados
        super(nombre, correo, contrasena, ubicacion);
        
        // Inicializa el nombre principal del proyecto
        setNombreProyecto(nombreProyecto);
        
        // Inicializa la lista de proyectos vacía
        this.proyectos = new ArrayList<>();
    }

    // Getter para obtener el nombre principal del proyecto
    public String getNombreProyecto() { 
        return nombreProyecto; 
    }

    // Setter para modificar el nombre principal del proyecto
    public void setNombreProyecto(String nombreProyecto) { 
        if (nombreProyecto == null || nombreProyecto.isBlank()){ 
            throw new IllegalArgumentException("El nombre del proyecto no puede estar vacío");
        }
        this.nombreProyecto = nombreProyecto.trim(); 
    }

    // Getter para obtener la lista completa de proyectos
    public ArrayList<String> getProyectos() { 
        return proyectos; 
    }

    // Setter para modificar la lista de proyectos (reemplazarla por otra lista)
    public void setProyectos(ArrayList<String> proyectos) {
        if (proyectos == null){ 
            throw new IllegalArgumentException("La lista de proyectos no puede estar vacía");
        } 
        this.proyectos = proyectos; 
    }

    // Método para agregar un nuevo proyecto a la lista
    public void agregarProyectos(String proyecto) {
        if (proyecto == null || proyecto.isBlank()){ 
            throw new IllegalArgumentException("El proyecto no puede ser vacío o nulo");
        }
        this.proyectos.add(proyecto);
    }
}

