//clase revisada
//faltan validaciones y revisar funciones extras y comunicacion entre clases
import java.util.ArrayList;

public class Propuesta {
    private String titulo;
    private String descripcion;
    private Usuario creador;
    private float progreso; 
    private final ArrayList<Inversionista> inversionistas = new ArrayList<>();
    private final ArrayList<Donante> donantes = new ArrayList<>();
    private final ArrayList<Voluntario> voluntarios = new ArrayList<>();

    public Propuesta(String titulo, String descripcion, Usuario creador) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.creador = creador;
        this.progreso = 0f;
    }

    //métodos
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo =titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Usuario getCreador() { return creador; }
    public void setCreador(Usuario creador) { this.creador = creador; }

    public float getProgreso() { return progreso; }
    public void setProgreso(float progreso) {
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
