//clase revisada - validacion completa
//falta ver funcionalidades extras
//falta comunicacion entre clases
import java.util.*;

public class UsuarioController {

    // “Persistencia” en memoria por ahora (clave = correo)
    private Map<String, Usuario> usuarios;

    public UsuarioController(){
        usuarios=new HashMap<>();
    }

    /** registraUsuario: registra un nuevo usuario */
    public boolean registraUsuario(Usuario u) {
        if (u == null) return false;
        if (usuarios.containsKey(u.getCorreo())) return false;
        // guardar contraseña como hash y escribir en BD
        usuarios.put(u.getCorreo(), u);
        return true;
    }

    // iniciarSecion (sic en el documento): autenticación simple
    public Optional<Usuario> iniciarSesion(String correo, String contrasena) {
        Usuario u = usuarios.get(correo);
        if (u != null && u.getContrasena().equals(contrasena)) { // TODO: verificar hash
            return Optional.of(u);
        }
        return Optional.empty();
    }

    /** editarPerfiles: edita perfil del usuario identificado por correo */
    public boolean editarPerfiles(String correo, String nuevoNombre, String nuevaUbicacion, String nuevaContrasena, String nuevoCorreo) {
        Usuario u = usuarios.get(correo);
        if (u == null) return false;
        if (!correo.equals(nuevoCorreo) && usuarios.containsKey(nuevoCorreo)){
             return false;
        }
        u.actualizarPerfil(nuevoNombre, nuevaUbicacion, nuevaContrasena, nuevoCorreo);
        //validacion por si cambia el correo, se actualixe la clave del mapa
        if (!correo.equals(nuevoCorreo)) {
            usuarios.remove(correo);
            usuarios.put(nuevoCorreo, u);
        }
        return true;
    }

    /** verificarUsuario: marca la cuenta como verificada */
    public boolean verificarUsuario(String correo) {
        Usuario u = usuarios.get(correo);
        if (u == null) return false;
        u.verificarCuenta();
        return true;
    }

    // Auxiliar para pruebas
    public Map<String, Usuario> getUsuarios() { return usuarios; }
}