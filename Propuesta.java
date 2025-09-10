import java.util.ArrayList;
import java.util.Objects;

public class Propuesta {
    private String titulo;
    private String descripcion;
    private Usuario creador;
    private float progreso; 
    private final ArrayList<Inversionista> inversionistas = new ArrayList<>();
    private final ArrayList<Donante> donantes = new ArrayList<>();
    private final ArrayList<Voluntario> voluntarios = new ArrayList<>();

    public Propuesta(String titulo, String descripcion, Usuario creador) {
        setTitulo(titulo);
        setDescripcion(descripcion);
        setCreador(creador);
        this.progreso = 0f;
    }

    //métodos
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

    public ArrayList<Inversionista> getInversionistas() { return inversionistas; }
    public void setInversionistas(Inversionista inver) {inversionistas.add(inver); }

    public ArrayList<Donante> getDonantes() { return donantes; }
    public void setDonantes(Donante donan) { donantes.add(donan); }

    public ArrayList<Voluntario> getVoluntarios() { return voluntarios; }
    public void setVoluntarios(Voluntario volun) { voluntarios.add(volun); }

    public String toString() {
        return "Propuesta{" + "titulo='" + titulo + '\'' + ", progreso=" + progreso + "%, creador=" + creador.getCorreo() + '}';
    }
}
