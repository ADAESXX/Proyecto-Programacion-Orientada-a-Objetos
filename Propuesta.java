import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Propuesta {
    private String titulo;
    private String descripcion;
    private Usuario creador;
    private float progreso; 
    private final List<String> inversionistas = new ArrayList<>();
    private final List<String> donantes = new ArrayList<>();
    private final List<String> voluntarios = new ArrayList<>();

    public Propuesta(String titulo, String descripcion, Usuario creador) {
        setTitulo(titulo);
        setDescripcion(descripcion);
        setCreador(creador);
        this.progreso = 0f;
    }

    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = Objects.requireNonNull(titulo, "titulo"); }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = Objects.requireNonNull(descripcion, "descripcion"); }

    public Usuario getCreador() { return creador; }
    public void setCreador(Usuario creador) { this.creador = Objects.requireNonNull(creador, "creador"); }

    public float getProgreso() { return progreso; }
    public void setProgreso(float progreso) {
        if (progreso < 0 || progreso > 100) throw new IllegalArgumentException("progreso 0..100");
        this.progreso = progreso;
    }

    public List<String> getInversionistas() { return Collections.unmodifiableList(inversionistas); }
    public void setInversionistas(List<String> lista) { reiniciar(inversionistas, lista); }

    public List<String> getDonantes() { return Collections.unmodifiableList(donantes); }
    public void setDonantes(List<String> lista) { reiniciar(donantes, lista); }

    public List<String> getVoluntarios() { return Collections.unmodifiableList(voluntarios); }
    public void setVoluntarios(List<String> lista) { reiniciar(voluntarios, lista); }

    // Helpers para registrar apoyos
    public void registrarInversionista(String nombre) { if (!nombre.isBlank()) inversionistas.add(nombre); }
    public void registrarDonante(String nombre) { if (!nombre.isBlank()) donantes.add(nombre); }
    public void registrarVoluntario(String nombre) { if (!nombre.isBlank()) voluntarios.add(nombre); }

    private static void reiniciar(List<String> destino, List<String> origen) {
        destino.clear();
        if (origen != null) destino.addAll(origen);
    }

    @Override public String toString() {
        return "Propuesta{" + "titulo='" + titulo + '\'' + ", progreso=" + progreso + "%, creador=" + creador.getCorreo() + '}';
    }
