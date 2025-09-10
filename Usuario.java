//clase revisada - validaciones completadas
public class Usuario {
    private String nombre;
    private String correo;
    private String contrasena;
    private String ubicacion;
    private boolean verificado;

    public Usuario(){

    }
    public Usuario (String nombre, String correo, String contrasena, String ubicacion) {
        //se manda de esta manera para que las validaciones se manejen en los setters y getters
        setNombre(nombre);
        setCorreo(correo);
        setContrasena(contrasena);
        setUbicacion(ubicacion);
        this.verificado = false;
    }

    public void verificarCuenta() {
        this.verificado = true;
    }

    public void actualizarPerfil (String nuevoNombre, String nuevaUbicacion, String nuevaContrasena, String nuevoCorreo) {
        //se manda de esta manera para que las validaciones se manejen en los setters y getters
        setNombre(nombre);
        setCorreo(correo);
        setContrasena(contrasena);
        setUbicacion(ubicacion);
        
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()){ 
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        //el trim quita los espacio en blanco
        this.nombre =nombre.trim(); 
    }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) {
        if (!correo.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")){
             throw new IllegalArgumentException("Correo inválido");
        }
        //el lowerCase hace todo minuscula
        this.correo = correo.trim().toLowerCase();
    }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) {
        if (contrasena == null || contrasena.length() < 6){
             throw new IllegalArgumentException("Contraseña corta, ingreseuna con más de 6 caracteres");
        }
            this.contrasena = contrasena;
    }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) {
        if (ubicacion == null || ubicacion.isBlank()){
             throw new IllegalArgumentException("La ubicación no puede estar vacía");
        } 
        this.ubicacion = ubicacion.trim(); 
    }

    public boolean getVerificado() { return verificado; }
    public void setVerificado(boolean verificado) { this. verificado = verificado; }

    public String toString() {
        return "Usuario{" + "nombre='" + nombre + '\'' + ", correo='" + correo + '\'' +
               ", ubicacion='" + ubicacion + '\'' + ", verificado=" + verificado + '}';
    }

}
