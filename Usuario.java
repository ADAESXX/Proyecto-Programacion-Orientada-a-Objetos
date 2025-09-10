import java.util.objects;

public class Usuario {
    private String nombre;
    private String correo;
    private String contrasena;
    private String ubicacion;
    private boolean verificado;

    public Usuario (String nombre, String correo, String contrasena, String ubicacion) {
        setNombre(nombre);
        setCorreo(correo);
        setContrasena(contrasena);
        setUbicacion(ubicacion);
        this.verificado = false;
    }

    public void verificarCuenta() {
        this.verificado = true;
    }

    public void ActualizarPerfil (String nuevoNombre, String nuevaUbicacion, String nuevaContrasena) {
        if (nuevoNombre != null && !nuevoNombre.isBlanck()) this.nombre = nuevoNombre;
        if (nuevaUbicacion != null && !nuevaUbicacion.isBlanck()) this.ubicacion = nuevaUbicacion;
        if (nuevaContrasena != null && !nuevaContrasena.isBlanck()) this.contrasena = nuevaContrasena;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {this.nombre = Objects.requireNonNull(nombre, "nombre"); }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) {
        if (correo = null || !correo.contains("@") throw new IllegalArgumentException("Correo invalido");
        this.correo = correo;
    }

    public String getCotrasena() { return contrasena; }
    public void setContrasena(String contrasena) {
        if (contrasena == null || contrasena.lenght() < 4) throw new IllegalArgumentException("contraseña débil")
            this.contrasena = contrasena;
    }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = (ubicacion == null ? "" : ubicacion); }

    public boolean getVerificado() { return verificado; }
    public void setVerificado(boolean verificado) { this. verificado = verificado; }

    public String toString() {
        return "Usuario{" + "nombre='" + nombre + '\'' + ", correo='" + correo + '\'' +
               ", ubicacion='" + ubicacion + '\'' + ", verificado=" + verificado + '}';
    }

    public boolean equals (object o) {
        if (this == o) return true
            if (!(o instanceof Usuario)) return false
            Usuario u = (Usuario) o;
        return Objects.equals(correo, u.correo);
    }

    public int hashCode() { return Objects.hash(correo); }
}
