public class Usuario {
    private String nombre;
    private String correo;
    private String contrasena;
    private String ubicacion;
    private boolean verificado;

    public Usuario() {
    }

    public Usuario(String nombre, String correo, String contrasena, String ubicacion) {
        setNombre(nombre);
        setCorreo(correo);
        setContrasena(contrasena);
        setUbicacion(ubicacion);
        this.verificado = false;
    }

    public void verificarCuenta() {
        this.verificado = true;
    }

    public void actualizarPerfil(String nuevoNombre, String nuevaUbicacion,
                                 String nuevaContrasena, String nuevoCorreo) {
        setNombre(nuevoNombre);
        setCorreo(nuevoCorreo);
        setContrasena(nuevaContrasena);
        setUbicacion(nuevaUbicacion);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo == null ||
                !correo.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new IllegalArgumentException("Correo inválido");
        }
        this.correo = correo.trim().toLowerCase();
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        if (contrasena == null || contrasena.trim().length() < 6) {
            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos 6 caracteres");
        }
        this.contrasena = contrasena.trim();
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        if (ubicacion == null || ubicacion.isEmpty()){
            throw new IllegalArgumentException("La ubicación no puede estar vacía");
        }
        this.ubicacion = ubicacion.trim();
    }

    public boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return correo != null ? correo.equals(usuario.correo) : usuario.correo == null;
    }

    @Override
    public int hashCode() {
        return correo != null ? correo.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", verificado=" + verificado +
                '}';
    }
}

