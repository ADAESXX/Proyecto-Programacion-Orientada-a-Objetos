import java.util.ArrayList;

public class Propuesta {
    private String titulo;
    private String descripcion;
    private Usuario creador;
    private float progreso;
    private double meta;          // NUEVO: meta de dinero
    private double recaudado;     // NUEVO: dinero recaudado
    private final ArrayList<Inversionista> inversionistas = new ArrayList<>();
    private final ArrayList<Donante> donantes = new ArrayList<>();
    private final ArrayList<Voluntario> voluntarios = new ArrayList<>();

    public Propuesta(String titulo, String descripcion, Usuario creador) {
        setTitulo(titulo);
        setDescripcion(descripcion);
        setCreador(creador);
        this.progreso = 0f;
        this.meta = 0.0;         // NUEVO
        this.recaudado = 0.0;    // NUEVO
    }

    //métodos existentes
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isEmpty()){
            throw new IllegalArgumentException("Título vacío o nulo, por lo que es inválido");
        }
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isEmpty()){
            throw new IllegalArgumentException("Descripción vacía o nula, por lo que inválido");
        }
        this.descripcion = descripcion;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        if (creador == null){
            throw new IllegalArgumentException("No existe el creador");
        }
        this.creador = creador;
    }

    public float getProgreso() {
        return progreso;
    }

    public void setProgreso(float progreso) {
        if (progreso < 0 || progreso > 100){
            throw new IllegalArgumentException("El progreso debe de estar entre 0 y 100");
        }
        this.progreso = progreso;
    }

    // NUEVOS MÉTODOS para meta y recaudación
    public double getMeta() {
        return meta;
    }

    public void setMeta(double meta) {
        if (meta <= 0) {
            throw new IllegalArgumentException("La meta debe ser mayor a 0");
        }
        this.meta = meta;
    }

    public double getRecaudado() {
        return recaudado;
    }

    public void agregarRecaudacion(double cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        this.recaudado += cantidad;
    }

    // Métodos existentes
    public ArrayList<Inversionista> getInversionistas() {
        return inversionistas;
    }

    public void setInversionistas(Inversionista inver) {
        if (inver == null ){
            throw new IllegalArgumentException("Inversionista inválido");
        }
        inversionistas.add(inver);
    }

    public ArrayList<Donante> getDonantes() {
        return donantes;
    }

    public void setDonantes(Donante donan) {
        if (donan == null ){
            throw new IllegalArgumentException("Donante inválido");
        }
        donantes.add(donan);
    }

    public ArrayList<Voluntario> getVoluntarios() {
        return voluntarios;
    }

    public void setVoluntarios(Voluntario volun) {
        if (volun == null ){
            throw new IllegalArgumentException("Voluntario inválido");
        }
        voluntarios.add(volun);
    }

    public String toString() {
        return "Propuesta{" +
                "titulo='" + titulo + '\'' +
                ", progreso=" + progreso + "%, creador=" + creador.getCorreo() +
                ", meta=$" + meta + ", recaudado=$" + recaudado + '}';
    }
}
