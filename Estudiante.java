//clase revisada
//falta ver validaciones y comunicacion entre clases
import java.util.*;
//el extends permite que se hereden atributos y/o métodos de la clase Uusuario
public class Estudiante extends Usuario {
    private String carrera;
    // En Fase 2 “propuestas” aparece como String; lo manejamos como lista de títulos para ser útil.
    private ArrayList<String> propuestas = new ArrayList<>();

    public Estudiante(String nombre, String correo, String contrasena, String ubicacion, String carrera) {
        super(nombre, correo, contrasena, ubicacion);
        this.carrera=carrera;
    }

    // Acciones clave
    public void agregarPropuesta(String titulo) {
        if (titulo == null || titulo.isBlank()) throw new IllegalArgumentException("título vacío");
        propuestas.add(titulo);
    }

    // Getters/Setters
    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    // Compatibles con “setPropuestas / getPropuestas” de la fase
    public ArrayList<String> getPropuestas() { return propuestas; }
    public void setPropuestas(ArrayList<String> nuevas) {
        propuestas.clear();
        propuestas.addAll(nuevas);
    }
}

