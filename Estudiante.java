//clase revisada - validaciones completas
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
        if (titulo == null || titulo.isEmpty()) {
            throw new IllegalArgumentException("título vacío, por lo que es inválido");
        }
        //el trim quita los espacios en blanco
        propuestas.add(titulo.trim());
    }

    // Getters/Setters
    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { 
        if (carrera == null || carrera.isEmpty()) {
            throw new IllegalArgumentException("Carrera vacía, por lo que es inválido");
        }
        this.carrera = carrera; 
    }

    // Compatibles con “setPropuestas / getPropuestas” de la fase
    public ArrayList<String> getPropuestas() { return propuestas; }
    public void setPropuestas(ArrayList<String> nuevas) {
        if (nuevas == null) {
            throw new IllegalArgumentException("La lista propuesta no puede ser nula");
        }
        propuestas.clear();
        propuestas.addAll(nuevas);
    }
}

