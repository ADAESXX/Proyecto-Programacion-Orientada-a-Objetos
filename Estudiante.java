public class Estudiante extends Usuario {
    private String carrera;
    // En Fase 2 “propuestas” aparece como String; lo manejamos como lista de títulos para ser útil.
    private final List<String> propuestas = new ArrayList<>();

    public Estudiante(String nombre, String correo, String contrasena, String ubicacion, String carrera) {
        super(nombre, correo, contrasena, ubicacion);
        setCarrera(carrera);
    }

    // Acciones clave
    public void agregarPropuesta(String titulo) {
        if (titulo == null || titulo.isBlank()) throw new IllegalArgumentException("título vacío");
        propuestas.add(titulo);
    }

    // Getters/Setters
    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = Objects.requireNonNull(carrera, "carrera"); }

    // Compatibles con “setPropuestas / getPropuestas” de la fase
    public List<String> getPropuestas() { return Collections.unmodifiableList(propuestas); }
    public void setPropuestas(List<String> nuevas) {
        propuestas.clear();
        if (nuevas != null) propuestas.addAll(nuevas);
    }
}

}
