// Usuario.java
public class UsuarioController {
    private int id;
    private String nombre;
    private String correo;
    private String ubicacion;
    private String contrasena;
    private boolean verificado;

    // Constructor vacío
    public UsuarioController() {}

    // Constructor sin id (para registrar)
    public UsuarioController(String nombre, String correo, String ubicacion, String contrasena, boolean verificado) {
        this.nombre = nombre;
        this.correo = correo;
        this.ubicacion = ubicacion;
        this.contrasena = contrasena;
        this.verificado = verificado;
    }

    // Constructor con id (para obtener desde BD)
    public UsuarioController(int id, String nombre, String correo, String ubicacion, String contrasena, boolean verificado) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.ubicacion = ubicacion;
        this.contrasena = contrasena;
        this.verificado = verificado;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public boolean isVerificado() { return verificado; }
    public void setVerificado(boolean verificado) { this.verificado = verificado; }
}
