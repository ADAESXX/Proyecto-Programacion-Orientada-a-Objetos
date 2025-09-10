//clase revisada
//se elimino algunas cosas de los métodos que hacían lo que debe de hacer el controllers
//falta ver funcionalidades extras, como validaciones entre otras cosas
//falta comunicacion entre clases
public class Usuario {
    private String nombre;
    private String correo;
    private String contrasena;
    private String ubicacion;
    private boolean verificado;

    public Usuario(){

    }
    public Usuario (String nombre, String correo, String contrasena, String ubicacion) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.ubicacion = ubicacion;
        this.verificado = false;
    }

    public void verificarCuenta() {
        this.verificado = true;
    }

    public void actualizarPerfil (String nuevoNombre, String nuevaUbicacion, String nuevaContrasena, String nuevoCorreo) {
        this.nombre = nuevoNombre;
        this.ubicacion = nuevaUbicacion;
        this.contrasena = nuevaContrasena;
        this.correo=nuevoCorreo;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {this.nombre =nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
    }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public boolean getVerificado() { return verificado; }
    public void setVerificado(boolean verificado) { this. verificado = verificado; }

    public String toString() {
        return "Usuario{" + "nombre='" + nombre + '\'' + ", correo='" + correo + '\'' +
               ", ubicacion='" + ubicacion + '\'' + ", verificado=" + verificado + '}';
    }

}
